package TradingSystem.server.Domain.StoreModule.Policy.Purchase;


import TradingSystem.server.Domain.StoreModule.Basket;

public class OrPurchaseRule extends purchaseLogicComponent  {


        public OrPurchaseRule(PurchaseRule left, PurchaseRule right) {
            super(left, right);
        }

        @Override
        public boolean predictCheck(int age, Basket basket) {
            return left.predictCheck(age, basket) && right.predictCheck(age, basket);
        }
    }


