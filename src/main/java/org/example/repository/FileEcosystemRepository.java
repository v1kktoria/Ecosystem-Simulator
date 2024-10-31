package org.example.repository;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.example.model.Ecosystem;
import org.example.model.Organism;
import org.example.utils.OrganismDeserializer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileEcosystemRepository implements EcosystemRepository {

    private static final Gson gson = new GsonBuilder()
            .registerTypeAdapter(Organism.class, new OrganismDeserializer())
            .setPrettyPrinting()
            .create();
    private static final String directory = "ecosystems";

    public FileEcosystemRepository() {
        File dir = new File(directory);
        if (!dir.exists()) {
            dir.mkdirs();
        }
    }

    @Override
    public void createEcosystem(Ecosystem ecosystem) {
        String filename = directory + "/" + ecosystem.getName() + "_info.txt";
        File file = new File(filename);
        Scanner scanner = new Scanner(System.in);
        while (file.exists()) {
            System.out.println("Экосистема с таким именем уже существует. Пожалуйста, введите новое имя:");
            String newName = scanner.nextLine();
            filename = directory + "/" + newName + "_info.txt";
            file = new File(filename);
            ecosystem.setName(newName);
        }
        try(FileWriter writer = new FileWriter(file)) {
            String json = gson.toJson(ecosystem);
            writer.write(json);
            System.out.println("Экосистема " + ecosystem.getName() + " успешно сохранена.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Ecosystem> getAllEcosystems() {
        List<Ecosystem> ecosystems = new ArrayList<>();
        File dir = new File(directory);
        File[] files = dir.listFiles((d, name) -> name.endsWith("_info.txt"));
        if (files != null) {
            for (File file : files) {
                try (FileReader reader = new FileReader(file)) {
                    Ecosystem ecosystem = gson.fromJson(reader, Ecosystem.class);
                    ecosystems.add(ecosystem);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return ecosystems;
    }

    @Override
    public Ecosystem getEcosystemByName(String name) {
        String fileName = directory + "/" + name + "_info.txt";
        Ecosystem ecosystem = null;

        try (FileReader reader = new FileReader(fileName)) {
            ecosystem = gson.fromJson(reader, Ecosystem.class);
        } catch (FileNotFoundException e) {
            System.out.println("Экосистема с именем \"" + name + "\" не найдена.");
        } catch (IOException e) {
            System.out.println("Произошла ошибка при чтении файла: " + e.getMessage());
        }

        return ecosystem;
    }


    @Override
    public void deleteEcosystem(Ecosystem ecosystem) {
        String fileName = directory + "/" + ecosystem.getName() + "_info.txt";
        File file = new File(fileName);
        if (file.delete()) {
            System.out.println("Экосистема " + ecosystem.getName() + " успешно удалена.");
        } else {
            System.out.println("Ошибка при удалении экосистемы " + ecosystem.getName());
        }
        File log_file = new File(directory + "/" + ecosystem.getName() + "_log.txt");
        log_file.delete();
    }

    @Override
    public void updateEcosystem(Ecosystem ecosystem) {
        String fileName = directory + "/" + ecosystem.getName() + "_info.txt";
        File file = new File(fileName);
        if (file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                String json = gson.toJson(ecosystem);
                writer.write(json);
                System.out.println("Экосистема " + ecosystem.getName() + " успешно обновлена.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Экосистема не найдена: " + ecosystem.getName());
        }
    }
}
