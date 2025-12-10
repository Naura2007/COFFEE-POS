package util;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class JsonDB {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    public static <T> List<T> load(String path, Class<T> clazz) {
        try {
            String json = new String(Files.readAllBytes(Paths.get(path)));
            return gson.fromJson(json, TypeToken.getParameterized(List.class, clazz).getType());
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    public static void save(String path, List<?> data) {
        try (Writer w = new FileWriter(path)) {
            gson.toJson(data, w);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
