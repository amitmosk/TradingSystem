package TradingSystem.server.Domain.ExternSystems;

public interface SupplyAdapter {
    boolean supply(String supplyInfo);
    boolean connect_to_supply_system();
}
