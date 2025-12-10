package service;

import util.JsonDB;
import model.Topping;
import java.util.List;

public class ToppingService {
    private static final String PATH="data/toppings.json";

    public static List<Topping> loadAll() {
        return JsonDB.load(PATH, Topping.class);
    }
}
