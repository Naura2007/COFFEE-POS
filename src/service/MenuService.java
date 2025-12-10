package service;

import util.JsonDB;
import model.MenuItemModel;
import java.util.List;

public class MenuService {
    private static final String PATH="data/menu.json";

    public static List<MenuItemModel> loadAll() {
        return JsonDB.load(PATH, MenuItemModel.class);
    }
}
