package com.tpp.EcoApp.Models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.util.List;

@Entity
public class DistrictCity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Pattern(regexp = "^[a-zA-Zа-щА-ЩЬьЮюЯяЇїІіЄєҐґ0-9\\-\\s]+$", message = "Invalid name format. Only letters, digits, spaces, and '-' are allowed.")
    private String name;

    @NotNull(message = "Region is required")
    @ManyToOne
    @JoinColumn(name = "region_id", nullable = false)
    private Region region;

    @OneToMany(mappedBy = "districtCity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EcologicalProblem> ecologicalProblems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }
}


