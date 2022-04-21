package Domain;

import Service.iService;

public class Market implements iService {
    private StoreController store_controller;

    public Market() {
        this.store_controller = new StoreController();
    }


}