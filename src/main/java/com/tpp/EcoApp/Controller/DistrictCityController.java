package com.tpp.EcoApp.Controller;

import com.tpp.EcoApp.Models.DistrictCity;
import com.tpp.EcoApp.Models.Region;
import com.tpp.EcoApp.Services.DistrictCityService;
import com.tpp.EcoApp.Services.RegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/districts_cities")
public class DistrictCityController {

    @Autowired
    private DistrictCityService districtCityService;

    @Autowired
    private RegionService regionService;

    @GetMapping
    public String listDistrictCities(Model model) {
        model.addAttribute("districtCities", districtCityService.getAllDistrictCities());
        return "district-cities";
    }

    @GetMapping("/add")
    public String addDistrictCityForm(Model model, Authentication authentication) {
        model.addAttribute("districtCity", new DistrictCity());
        model.addAttribute("regions", regionService.getAllRegions());
        return "add-district-city";
    }

    @PostMapping("/add")
    public String addDistrictCity(@Valid @ModelAttribute("districtCity") DistrictCity districtCity,
                                  BindingResult result,
                                  Model model,
                                  Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("regions", regionService.getAllRegions());
            return "add-district-city";
        }
        districtCityService.saveDistrictCity(districtCity);
        return "redirect:/districts_cities";
    }

    @GetMapping("/edit/{id}")
    public String editDistrictCityForm(@PathVariable("id") Long id,
                                       Model model,
                                       Authentication authentication) {
        DistrictCity districtCity = districtCityService.findDistrictCityById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid district city ID: " + id));
        model.addAttribute("districtCity", districtCity);
        model.addAttribute("regions", regionService.getAllRegions());
        return "edit-district-city";
    }

    @PostMapping("/edit/{id}")
    public String editDistrictCity(@PathVariable("id") Long id,
                                   @Valid @ModelAttribute("districtCity") DistrictCity districtCity,
                                   BindingResult result,
                                   Model model,
                                   Authentication authentication) {
        if (result.hasErrors()) {
            model.addAttribute("regions", regionService.getAllRegions());
            return "edit-district-city";
        }
        districtCity.setId(id);
        districtCityService.updateDistrictCity(districtCity);
        return "redirect:/districts_cities";
    }

    @GetMapping("/delete/{id}")
    public String deleteDistrictCity(@PathVariable("id") Long id,
                                     Authentication authentication) {
        districtCityService.deleteDistrictCityById(id);
        return "redirect:/districts_cities";
    }
}
