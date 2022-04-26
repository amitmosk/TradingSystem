package Domain.ExternSystems;

import Domain.ExternSystems.Proxy.ExternSupplySystemProxy;

public class SupplyAdapterImpl implements SupplyAdapter {
    private ExternSupplySystemProxy externSupplySystemProxy;

    @Override
    public boolean supply() {
        // TODO: logger
        return this.externSupplySystemProxy.supply();
    }
}
