package com.tpp.EcoApp.Repository;

import com.tpp.EcoApp.Models.EcologicalProblem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EcologicalProblemRepository extends JpaRepository<EcologicalProblem, Long> {
}
