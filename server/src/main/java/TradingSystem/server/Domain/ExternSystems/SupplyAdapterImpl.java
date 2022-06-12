package TradingSystem.server.Domain.ExternSystems;

import TradingSystem.server.Domain.Utils.SystemLogger;
import TradingSystem.server.Domain.ExternSystems.Proxy.ExternSupplySystemProxy;

public class SupplyAdapterImpl implements SupplyAdapter {
    private ExternSupplySystemProxy externSupplySystemProxy;

    public SupplyAdapterImpl() {
        this.externSupplySystemProxy = new ExternSupplySystemProxy();
    }


    @Override
    public int supply(SupplyInfo supplyInfo) {
        return this.externSupplySystemProxy.supply(supplyInfo);
    }

    @Override
    public int cancel_supply(int transaction_id) {
        return this.externSupplySystemProxy.cancel_supply(transaction_id);
    }

    @Override
    public boolean handshake() {
        SystemLogger.getInstance().add_log("system connected to the supply system");
        return this.externSupplySystemProxy.handshake();
    }
}
