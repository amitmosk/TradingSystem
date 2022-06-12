package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import java.util.Map;

public class simpleDiscountComponentByCategory extends simpleDiscountComponent {
    String CategoryOfDiscount = "";

    public simpleDiscountComponentByCategory(String nameOfCategory, double percentOfDiscount) throws WrongPermterException {
        super(percentOfDiscount);
        CategoryOfDiscount = nameOfCategory;
    }

    @Override
    public double CalculateDiscount(Basket basket) {
        double Discount = 0;
        Map<Product, Integer> basketMap = basket.getProducts_and_quantities();
        for (Map.Entry<Product, Integer> entry : basketMap.entrySet()) {
            Product product = entry.getKey();
            int quantities = entry.getValue();
            if (product.getCategory().equals(CategoryOfDiscount))
                Discount += quantities * product.getOriginal_price() * percentOfDiscount;
        }
        return Discount;
    }


}
