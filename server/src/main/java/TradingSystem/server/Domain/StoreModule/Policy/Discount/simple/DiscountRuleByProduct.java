package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;
import TradingSystem.server.Domain.StoreModule.Product.Product;

import java.util.Map;

public class DiscountRuleByProduct extends SimpleDiscountRule {
    Product ProductToDiscount;

    public DiscountRuleByProduct(Product Product, float percentOfDiscount) {
        super(percentOfDiscount);
        this.ProductToDiscount = Product;
    }

    @Override
    public double CalculatePriceAfterDiscount(Basket basket) {
        double TotalPrice = 0;
        Map<Product, Integer> basketMap = basket.getProducts_and_quantities();
        try {
            int quantity = basketMap.get(ProductToDiscount);
            double price = ProductToDiscount.getPrice();
            double Discount = quantity * price * this.percentOfDiscount;
            return Discount;
        } catch (NullPointerException e) {
            return basket.getTotal_price();
        }
    }
}
