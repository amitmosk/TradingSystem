package Acceptance.System.ExternSystemsTests;

public class SupplyAdapterTests implements SupplyAdapter {

    public SupplyAdapterTests() {
    }

    @Override
    public int supply(SupplyInfo supplyInfo) {
        return 66000;
    }

    @Override
    public int cancel_supply(int transaction_id) {
        return 1;
    }

    @Override
    public boolean handshake() {
        return true;
    }
}
