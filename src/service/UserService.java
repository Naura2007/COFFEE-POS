package service;

import util.JsonDB;
import model.User;

public class UserService {

    private static final String PATH = "data/users.json";

    public static User authenticate(String u, String pw) {
        for (User x : JsonDB.load(PATH, User.class)) {
            if (x.getUsername().equals(u) && x.getPassword().equals(pw))
                return x;
        }
        return null;
    }
}
