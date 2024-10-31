package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Ecosystem  {
    private String name;
    private List<Organism> organisms;
    private double temperature;
    private double humidity;
    private double waterAvailability;

    public void removeOrganismByName(String name)
    {
        organisms.removeIf(organism -> organism.getName().equals(name));
    }
}
