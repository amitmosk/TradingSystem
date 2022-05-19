package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.SimpleDiscountRule;
import TradingSystem.server.Domain.StoreModule.Product.Product;

import java.util.Map;

public class DiscountRuleByCategory extends SimpleDiscountRule {
    String CategoryOfDiscount = "";

    public DiscountRuleByCategory(String nameOfCategory, float percentOfDiscount) {
        super(percentOfDiscount);
        CategoryOfDiscount = nameOfCategory;
    }

    @Override
    public double CalculatePriceAfterDiscount(Basket basket) {
        double Discount = 0;
        Map<Product, Integer> basketMap = basket.getProducts_and_quantities();
        for (Map.Entry<Product, Integer> entry : basketMap.entrySet()) {
            Product product = entry.getKey();
            int quantities = entry.getValue();
            if (product.getCategory().equals(CategoryOfDiscount))
                Discount += quantities * product.getPrice() * percentOfDiscount;
        }
        return Discount;
    }
}
