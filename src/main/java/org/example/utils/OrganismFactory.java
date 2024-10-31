package org.example.utils;

import org.example.model.Animal;
import org.example.model.AnimalType;
import org.example.model.Organism;
import org.example.model.Plant;

import java.util.Scanner;

public class OrganismFactory {
    private final Scanner scanner = new Scanner(System.in);
    private final InputValidator inputValidator = new InputValidator(scanner);

    public Organism createOrganism() {
        String type = chooseOrganismType();
        String name = getOrganismName();
        double population = getOrganismPopulation();

        if (type.equals("animal")) {
            return createAnimal(name, population);
        } else {
            return createPlant(name, population);
        }
    }

    private String chooseOrganismType() {
        String type = null;
        do {
            System.out.print("Выберите тип организма: \n1. Животное\n2. Растение\nВведите номер типа организма: ");

            switch (inputValidator.getValidIntegerInput()) {
                case 1 -> type = "animal";
                case 2 -> type = "plant";
                default -> System.out.println("Некорректный выбор. Попробуйте снова.");
            }
        } while (type == null);
        return type;
    }

    private Animal createAnimal(String name, double population) {
        System.out.print("Введите потребность в пище (от 1 до 5, где 1 — низкая, 5 — высокая): ");
        double foodConsumptionRate = inputValidator.getValidDoubleInput(1, 5);

        AnimalType animalType = null;
        while (animalType == null) {
            System.out.print("Выберите тип животного: \n1. Травоядное\n2. Плотоядное\n3. Всеядное\nВведите номер типа животного: ");
            switch (inputValidator.getValidIntegerInput()) {
                case 1 -> animalType = AnimalType.HERBIVORE;
                case 2 -> animalType = AnimalType.CARNIVORE;
                case 3 -> animalType = AnimalType.OMNIVORE;
                default -> System.out.println("Некорректный ввод. Пожалуйста, введите 1, 2 или 3.");
            }
        }
        return new Animal(name, population, foodConsumptionRate, animalType);
    }

    private Plant createPlant(String name, double population) {
        System.out.print("Введите потребность в воде (от 1 до 5, где 1 — низкая, 5 — высокая): ");
        double growthRate = inputValidator.getValidDoubleInput(1, 5);
        return new Plant(name, population, growthRate);
    }

    private String getOrganismName() {
        System.out.print("Введите имя организма: ");
        return scanner.nextLine();
    }

    private double getOrganismPopulation() {
        System.out.print("Введите популяцию: ");
        return inputValidator.getValidDoubleInput(0, Double.MAX_VALUE);
    }

}