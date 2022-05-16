package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import java.util.Map;

public class DiscountRuleByProduct extends SimpleDiscountRule {
    Product ProductToDiscount;

    public DiscountRuleByProduct(Product Product, double percentOfDiscount) throws WrongPermterException {
        super(percentOfDiscount);
        this.ProductToDiscount = Product;
    }

    @Override
    public double CalculateDiscount(Basket basket) {
        Map<Product, Integer> basketMap = basket.getProducts_and_quantities();
        try {
            int quantity = basketMap.get(ProductToDiscount);
            double price = ProductToDiscount.getPrice();
            return quantity * price * this.percentOfDiscount;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
