package com.tpp.EcoApp.Services;

import com.tpp.EcoApp.Models.DistrictCity;
import com.tpp.EcoApp.Repository.DistrictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DistrictCityService {

    @Autowired
    private DistrictRepository districtRepository;

    public List<DistrictCity> getAllDistrictCities() {
        return districtRepository.findAll();
    }

    public Optional<DistrictCity> findDistrictCityById(Long id) {
        return districtRepository.findById(id);
    }

    public void saveDistrictCity(DistrictCity districtCity) {
        districtRepository.save(districtCity);
    }

    public void updateDistrictCity(DistrictCity updatedDistrictCity) {
        DistrictCity existingDistrictCity = districtRepository.findById(updatedDistrictCity.getId())
                .orElseThrow(() -> new IllegalArgumentException("District city not found"));
        existingDistrictCity.setName(updatedDistrictCity.getName());
        existingDistrictCity.setRegion(updatedDistrictCity.getRegion());
        districtRepository.save(existingDistrictCity);
    }

    public void deleteDistrictCityById(Long id) {
        districtRepository.deleteById(id);
    }
}
