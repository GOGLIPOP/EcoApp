package com.tpp.EcoApp.Services;

import com.tpp.EcoApp.Models.EcologicalProblem;
import com.tpp.EcoApp.Repository.EcologicalProblemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EcologicalProblemService {

    @Autowired
    private EcologicalProblemRepository ecologicalProblemRepository;

    public List<EcologicalProblem> getAllEcologicalProblems() {
        return ecologicalProblemRepository.findAll();
    }

    public Optional<EcologicalProblem> findEcologicalProblemById(Long id) {
        return ecologicalProblemRepository.findById(id);
    }

    public void saveEcologicalProblem(EcologicalProblem ecologicalProblem) {
        ecologicalProblemRepository.save(ecologicalProblem);
    }

    public void updateEcologicalProblem(EcologicalProblem updatedEcologicalProblem) {
        EcologicalProblem existingEcologicalProblem = ecologicalProblemRepository.findById(updatedEcologicalProblem.getId())
                .orElseThrow(() -> new IllegalArgumentException("Ecological problem not found"));
        existingEcologicalProblem.setDescription(updatedEcologicalProblem.getDescription());
        existingEcologicalProblem.setDistrictCity(updatedEcologicalProblem.getDistrictCity());
        ecologicalProblemRepository.save(existingEcologicalProblem);
    }

    public void deleteEcologicalProblemById(Long id) {
        ecologicalProblemRepository.deleteById(id);
    }
}
