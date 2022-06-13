package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.util.Map;
@Entity
@DiscriminatorValue("8")
public class simpleDiscountComponentByCategory extends simpleDiscountComponent {
    String CategoryOfDiscount = "";

    public simpleDiscountComponentByCategory(String nameOfCategory, double percentOfDiscount) throws WrongPermterException {
        super(percentOfDiscount);
        CategoryOfDiscount = nameOfCategory;
    }

    public simpleDiscountComponentByCategory() {

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
