package ru.topjava.restaurant_voting.model.converter;

import ru.topjava.restaurant_voting.model.Dish;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Converter
public class DishesListConverter implements AttributeConverter<List<Dish>, String> {

    private static final String PROPS_SEPARATOR = "\t";
    private static final String DISHES_SEPARATOR = "\t\t";

    @Override
    public String convertToDatabaseColumn(@Valid List<Dish> dishList) {
        if (dishList == null) return null;

        StringBuilder sb = new StringBuilder();
        dishList.forEach(dish -> {
            if (!dish.getName().isEmpty() && dish.getPriceInCents() >= 0) {
                sb.append(dish.getName());
                sb.append(PROPS_SEPARATOR);
                sb.append(dish.getPriceInCents());
                sb.append(DISHES_SEPARATOR);
            }
        });
        return sb.toString();
    }

    @Override
    public List<Dish> convertToEntityAttribute(String dbDishList) {
        if (dbDishList == null || dbDishList.isEmpty()) return null;

        String[] dishesSerialized = dbDishList.split(DISHES_SEPARATOR);

        if (dishesSerialized.length == 0) return null;

        List<Dish> result = new ArrayList<>();

        for (String dishSerialized: dishesSerialized) {
            String[] dishProps = dishSerialized.split(PROPS_SEPARATOR);
            if (dishProps.length == 0 || !dishSerialized.contains(PROPS_SEPARATOR)) continue;

            String dishName = dishProps[0].isEmpty() ? null : dishProps[0];
            Long dishPrice = dishProps[1].isEmpty() ? null : Long.parseLong(dishProps[1]);

            result.add(new Dish(dishName, dishPrice));
        }
        return result;
    }
}