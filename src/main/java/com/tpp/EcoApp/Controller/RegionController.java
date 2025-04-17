package com.tpp.EcoApp.Controller;

import com.tpp.EcoApp.Models.Region;
import com.tpp.EcoApp.Services.RegionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/regions")
public class RegionController {

    @Autowired
    private RegionService regionService;

    @GetMapping
    public String listRegions(Model model) {
        model.addAttribute("regions", regionService.getAllRegions());
        return "regions";
    }

    @GetMapping("/add")
    public String addRegionForm(Model model) {
        model.addAttribute("region", new Region());
        return "add-region";
    }

    @PostMapping("/add")
    public String addRegion(@Valid @ModelAttribute("region") Region region, BindingResult result) {
        if (result.hasErrors()) {
            return "add-region";
        }
        regionService.saveRegion(region);
        return "redirect:/regions";
    }

    @GetMapping("/edit/{id}")
    public String editRegionForm(@PathVariable("id") Long id, Model model) {
        Region region = regionService.findRegionById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid region ID: " + id));
        model.addAttribute("region", region);
        return "edit-region";
    }

    @PostMapping("/edit/{id}")
    public String editRegion(@PathVariable("id") Long id,
                             @Valid @ModelAttribute("region") Region region,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "edit-region";
        }
        region.setId(id);
        regionService.updateRegion(region);
        return "redirect:/regions";
    }

    @GetMapping("/delete/{id}")
    public String deleteRegion(@PathVariable("id") Long id) {
        regionService.deleteRegionById(id);
        return "redirect:/regions";
    }
}
