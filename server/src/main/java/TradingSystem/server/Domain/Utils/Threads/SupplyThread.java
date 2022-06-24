package TradingSystem.server.Domain.Utils.Threads;

import TradingSystem.server.Domain.ExternalSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternalSystems.SupplyInfo;

public class SupplyThread implements Runnable {

    private int value;
    private SupplyInfo supplyInfo;
    private SupplyAdapter supplyAdapter;


    public SupplyThread(SupplyAdapter supplyAdapter, SupplyInfo supplyInfo) {
        this.supplyInfo = supplyInfo;
        this.supplyAdapter = supplyAdapter;
        this.value = -1;
    }

    @Override
    public void run(){
        value = this.supplyAdapter.supply(supplyInfo);
    }

    /**
     * Requirement 6
     * @return the answer we got from the external service.
     */
    public int get_value(){
        return this.value;
    }
}