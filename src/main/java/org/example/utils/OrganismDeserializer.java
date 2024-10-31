package org.example.utils;

import com.google.gson.*;
import org.example.model.*;

import java.lang.reflect.Type;

public class OrganismDeserializer implements JsonDeserializer<Organism> {
    @Override
    public Organism deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();

        String name = jsonObject.get("name").getAsString();
        double population = jsonObject.get("population").getAsDouble();

        if (jsonObject.has("growthRate")) {
            double growthRate = jsonObject.get("growthRate").getAsDouble();
            return new Plant(name, population, growthRate);
        } else {
            double foodConsumptionRate = jsonObject.get("foodConsumptionRate").getAsDouble();
            AnimalType animalType = AnimalType.valueOf(jsonObject.get("animalType").getAsString().toUpperCase());
            return new Animal(name, population, foodConsumptionRate, animalType);
        }
    }
}
