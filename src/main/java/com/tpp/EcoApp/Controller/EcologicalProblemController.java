package com.tpp.EcoApp.Controller;

import com.tpp.EcoApp.Models.EcologicalProblem;
import com.tpp.EcoApp.Models.DistrictCity;
import com.tpp.EcoApp.Services.EcologicalProblemService;
import com.tpp.EcoApp.Services.DistrictCityService;
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
@RequestMapping("/ecological-problems")
public class EcologicalProblemController {

    @Autowired
    private EcologicalProblemService ecologicalProblemService;

    @Autowired
    private DistrictCityService districtCityService;

    @GetMapping
    public String listEcologicalProblems(Model model) {
        model.addAttribute("ecologicalProblems", ecologicalProblemService.getAllEcologicalProblems());
        return "ecological-problems";
    }

    @GetMapping("/add")
    public String addEcologicalProblemForm(Model model, Authentication authentication) {
        checkAdminAccess(authentication);
        model.addAttribute("ecologicalProblem", new EcologicalProblem());
        model.addAttribute("districtCities", districtCityService.getAllDistrictCities());
        return "add-ecological-problem";
    }

    @PostMapping("/add")
    public String addEcologicalProblem(@Valid @ModelAttribute("ecologicalProblem") EcologicalProblem ecologicalProblem,
                                       BindingResult result,
                                       Model model,
                                       Authentication authentication) {
        checkAdminAccess(authentication);
        if (result.hasErrors()) {
            model.addAttribute("districtCities", districtCityService.getAllDistrictCities());
            return "add-ecological-problem";
        }
        ecologicalProblemService.saveEcologicalProblem(ecologicalProblem);
        return "redirect:/ecological-problems";
    }

    @GetMapping("/edit/{id}")
    public String editEcologicalProblemForm(@PathVariable("id") Long id,
                                            Model model,
                                            Authentication authentication) {
        checkAdminAccess(authentication);
        EcologicalProblem ecologicalProblem = ecologicalProblemService.findEcologicalProblemById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid ecological problem ID: " + id));
        model.addAttribute("ecologicalProblem", ecologicalProblem);
        model.addAttribute("districtCities", districtCityService.getAllDistrictCities());
        return "edit-ecological-problem";
    }

    @PostMapping("/edit/{id}")
    public String editEcologicalProblem(@PathVariable("id") Long id,
                                        @Valid @ModelAttribute("ecologicalProblem") EcologicalProblem ecologicalProblem,
                                        BindingResult result,
                                        Model model,
                                        Authentication authentication) {
        checkAdminAccess(authentication);
        if (result.hasErrors()) {
            model.addAttribute("districtCities", districtCityService.getAllDistrictCities());
            return "edit-ecological-problem";
        }
        ecologicalProblem.setId(id);
        ecologicalProblemService.updateEcologicalProblem(ecologicalProblem);
        return "redirect:/ecological-problems";
    }

    @GetMapping("/delete/{id}")
    public String deleteEcologicalProblem(@PathVariable("id") Long id,
                                          Authentication authentication) {
        checkAdminAccess(authentication);
        ecologicalProblemService.deleteEcologicalProblemById(id);
        return "redirect:/ecological-problems";
    }

    private void checkAdminAccess(Authentication authentication) {
        if (authentication == null ||
                !authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            throw new AccessDeniedException("Access denied");
        }
    }
}
