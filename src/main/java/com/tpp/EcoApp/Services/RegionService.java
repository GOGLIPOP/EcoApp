package com.tpp.EcoApp.Services;

import com.tpp.EcoApp.Models.Region;
import com.tpp.EcoApp.Repository.RegionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegionService {

    @Autowired
    private RegionRepository regionRepository;

    public List<Region> getAllRegions() {
        return regionRepository.findAll();
    }

    public Optional<Region> findRegionById(Long id) {
        return regionRepository.findById(id);
    }

    public void saveRegion(Region region) {
        regionRepository.save(region);
    }

    public void updateRegion(Region updatedRegion) {
        Region existingRegion = regionRepository.findById(updatedRegion.getId())
                .orElseThrow(() -> new IllegalArgumentException("Region not found"));

        existingRegion.setName(updatedRegion.getName());
        regionRepository.save(existingRegion);
    }

    public void deleteRegionById(Long id) {
        regionRepository.deleteById(id);
    }
}
