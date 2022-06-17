package Acceptance.System.ExternSystemsTests;

import TradingSystem.server.Domain.Utils.Logger.SystemLogger;

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
        SystemLogger.getInstance().add_log("Cancel Supply Of Transaction : " + transaction_id);
        return this.externSupplySystemProxy.cancel_supply(transaction_id);
    }

    @Override
    public boolean handshake() {
        SystemLogger.getInstance().add_log("System Connected To The Supply System");
        return this.externSupplySystemProxy.handshake();
    }
}
