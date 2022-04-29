package Domain.ExternSystems;

import Domain.ExternSystems.Proxy.ExternSupplySystemProxy;

public class SupplyAdapterImpl implements SupplyAdapter {
    private ExternSupplySystemProxy externSupplySystemProxy;

    // TODO: logger

    @Override
    public boolean supply() {
        return this.externSupplySystemProxy.supply();
    }

    @Override
    public boolean connect() {
        return this.externSupplySystemProxy.connect();
    }
}
