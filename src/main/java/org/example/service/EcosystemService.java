package org.example.service;

import org.example.model.Ecosystem;
import org.example.repository.EcosystemRepository;

import java.util.List;

public class EcosystemService {
    private final EcosystemRepository ecosystemRepository;
    private final InputService inputService;

    public EcosystemService(EcosystemRepository ecosystemRepository) {
        this.ecosystemRepository = ecosystemRepository;
        this.inputService = new InputService();
    }

    public Ecosystem createAndSaveEcosystem() {
        Ecosystem ecosystem = inputService.getEcosystemInput();
        ecosystemRepository.createEcosystem(ecosystem);
        return ecosystem;
    }

    public void handleUpdateEcosystem(Ecosystem ecosystem) {
        inputService.updateEcosystemInput(ecosystem);
        ecosystemRepository.updateEcosystem(ecosystem);
    }

    public void updateEcosystem(Ecosystem ecosystem)
    {
        ecosystemRepository.updateEcosystem(ecosystem);
    }

    public Ecosystem getEcosystemByName(String name) {
        return ecosystemRepository.getEcosystemByName(name);
    }

    public List<Ecosystem> getAllEcosystems() {
        return ecosystemRepository.getAllEcosystems();
    }

    public void deleteEcosystem(Ecosystem ecosystem) {
        ecosystemRepository.deleteEcosystem(ecosystem);
    }

}
