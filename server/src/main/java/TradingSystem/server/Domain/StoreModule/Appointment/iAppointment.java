package TradingSystem.server.Domain.StoreModule.Appointment;

import TradingSystem.server.Domain.StoreModule.Store.Store;
import TradingSystem.server.Domain.UserModule.AssignUser;

import java.util.HashMap;

public interface iAppointment {
    AssignUser getMember();
    AssignUser getAppointer();
    Store getStore();
    StoreManagerType getType();
    HashMap<StorePermission, Integer> getPermissions();
    boolean has_permission(StorePermission permission);
    boolean is_owner();
    boolean is_founder();
    boolean is_manager();
}
