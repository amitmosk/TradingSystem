package Domain.ExternSystems;

import Domain.ExternSystems.Proxy.ExternSupplySystemProxy;
import Domain.Utils.SystemLogger;

public class SupplyAdapterImpl implements SupplyAdapter {
    private ExternSupplySystemProxy externSupplySystemProxy;

    public SupplyAdapterImpl() {
        this.externSupplySystemProxy = new ExternSupplySystemProxy();
    }

    @Override
    public boolean supply(String supplyInfo) {
        return this.externSupplySystemProxy.supply(supplyInfo);
    }

    @Override
    public boolean connect_to_supply_system() {
        SystemLogger.getInstance().add_log("system connected to the supply system");
        return this.externSupplySystemProxy.connect();
    }
}
