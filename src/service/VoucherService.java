package service;

import util.JsonDB;
import model.Voucher;
import java.util.List;

public class VoucherService {
    private static final String PATH="data/vouchers.json";

    public static List<Voucher> loadAll() {
        return JsonDB.load(PATH, Voucher.class);
    }
}
