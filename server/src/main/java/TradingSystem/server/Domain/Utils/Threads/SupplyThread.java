package TradingSystem.server.Domain.Utils.Threads;

import TradingSystem.server.Domain.ExternSystems.PaymentAdapter;
import TradingSystem.server.Domain.ExternSystems.PaymentInfo;
import TradingSystem.server.Domain.ExternSystems.SupplyAdapter;
import TradingSystem.server.Domain.ExternSystems.SupplyInfo;

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

    public int get_value(){
        return this.value;
    }
}