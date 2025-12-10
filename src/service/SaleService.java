package service;

import util.JsonDB;
import model.SaleRecord;
import model.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class SaleService {

    private static final String PATH="data/sales.json";

    public static void addSale(String cashier, List<OrderItem> items, int total) {
        List<SaleRecord> all = JsonDB.load(PATH, SaleRecord.class);
        SaleRecord sr = new SaleRecord();
        sr.cashier = cashier;
        sr.items = new ArrayList<>(items);
        sr.total = total;
        sr.time = System.currentTimeMillis();
        all.add(sr);
        JsonDB.save(PATH, all);
    }
}
