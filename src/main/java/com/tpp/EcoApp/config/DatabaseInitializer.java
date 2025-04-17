package com.tpp.EcoApp.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class DatabaseInitializer {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @EventListener(ContextRefreshedEvent.class)
    public void initializeDatabase() {
        Integer userCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM app_user", Integer.class);
        Integer areaCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM region", Integer.class);
        Integer districtCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM district_city", Integer.class);
        Integer ecologicalProblemCount = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM ecological_problem", Integer.class);

        // Проверка, что база данных пустая
        if (Objects.equals(userCount, 0) && Objects.equals(areaCount, 0) &&
                Objects.equals(districtCount, 0) && Objects.equals(ecologicalProblemCount, 0)) {
            System.out.println("Database is empty. Initializing data...");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(getClass().getResourceAsStream("/data.sql"), StandardCharsets.UTF_8))) {

                String sql = reader.lines().collect(Collectors.joining("\n"));
                jdbcTemplate.execute(sql);

                resetSequences();

                System.out.println("Database initialized successfully.");
            } catch (Exception e) {
                System.err.println("Failed to initialize database: " + e.getMessage());
                e.printStackTrace();
            }
        } else {
            System.out.println("Database already contains data. Skipping initialization.");
        }
    }

    private void resetSequences() {
        try {
            // Виводимо всі послідовності у базі даних
            jdbcTemplate.query("SELECT sequence_name FROM information_schema.sequences WHERE sequence_schema = 'public'",
                            (rs, rowNum) -> rs.getString("sequence_name"))
                    .forEach(sequence -> System.out.println("!!!--- Found sequence ---!!!: " + sequence));

            // Скидаємо значення для всіх таблиць з автоінкрементом
            jdbcTemplate.execute("SELECT setval('user_id_seq', COALESCE((SELECT MAX(id) FROM app_user), 1))");
            jdbcTemplate.execute("SELECT setval('area_id_seq', COALESCE((SELECT MAX(id) FROM region), 1))");
            jdbcTemplate.execute("SELECT setval('district_id_seq', COALESCE((SELECT MAX(id) FROM district_city), 1))");
            jdbcTemplate.execute("SELECT setval('ecological_problem_id_seq', COALESCE((SELECT MAX(id) FROM ecological_problem), 1))");

            System.out.println("Sequences reset successfully.");
        } catch (Exception e) {
            System.err.println("Failed to reset sequences: " + e.getMessage());
            e.printStackTrace();
        }
    }
}