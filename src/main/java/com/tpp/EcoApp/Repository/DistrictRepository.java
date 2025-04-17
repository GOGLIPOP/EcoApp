package com.tpp.EcoApp.Repository;

import com.tpp.EcoApp.Models.DistrictCity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DistrictRepository extends JpaRepository<DistrictCity, Long> {
}
