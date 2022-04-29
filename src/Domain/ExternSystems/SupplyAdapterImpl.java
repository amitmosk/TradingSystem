package Domain.ExternSystems;

import Domain.ExternSystems.Proxy.ExternSupplySystemProxy;

public class SupplyAdapterImpl implements SupplyAdapter {
    private ExternSupplySystemProxy externSupplySystemProxy;

    // TODO: logger

    @Override
    public boolean supply(String supplyInfo) {
        return this.externSupplySystemProxy.supply(supplyInfo);
    }

    @Override
    public boolean connect_to_supply_system() {
        return this.externSupplySystemProxy.connect();
    }
}
