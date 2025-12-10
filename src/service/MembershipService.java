package service;

import util.JsonDB;
import model.Membership;
import java.util.List;

public class MembershipService {
    private static final String PATH="data/members.json";

    public static List<Membership> loadAll() {
        return JsonDB.load(PATH, Membership.class);
    }
}
