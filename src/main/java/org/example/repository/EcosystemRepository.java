package org.example.repository;

import org.example.model.Ecosystem;

import java.util.List;

public interface EcosystemRepository {

    List<Ecosystem> getAllEcosystems();

    Ecosystem getEcosystemByName(String name);

    void createEcosystem(Ecosystem ecosystem);

    void deleteEcosystem(Ecosystem ecosystem);

    void updateEcosystem(Ecosystem ecosystem);

}
