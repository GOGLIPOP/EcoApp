package com.tpp.EcoApp.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
public class EcologicalProblem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Zа-щА-ЩЬьЮюЯяЇїІіЄєҐґ0-9\\-\\s]+$", message = "Invalid name format. Only letters, digits, spaces, and '-' are allowed.")
    private String description;

    @NotNull(message = "DistrictCity is required")
    @ManyToOne
    @JoinColumn(name = "district_city_id", nullable = false)
    private DistrictCity districtCity;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DistrictCity getDistrictCity() {
        return districtCity;
    }

    public void setDistrictCity(DistrictCity districtCity) {
        this.districtCity = districtCity;
    }
}

