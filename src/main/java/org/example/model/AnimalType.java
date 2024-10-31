package org.example.model;

public enum AnimalType {
    HERBIVORE("Травоядное"),
    CARNIVORE("Плотоядное"),
    OMNIVORE("Всеядное");

    private final String name;

    AnimalType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}