package service;

import util.JsonDB;
import model.Discount;
import java.util.List;

public class DiscountService {
    private static final String PATH="data/discounts.json";

    public static List<Discount> loadAll() {
        return JsonDB.load(PATH, Discount.class);
    }
}
