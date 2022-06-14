package TradingSystem.server.Domain.StoreModule.Policy.Discount.simple;


import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Map;

@Entity
@DiscriminatorValue("9")
public class simpleDiscountComponentByProduct extends simpleDiscountComponent {
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    Product ProductToDiscount;

    public simpleDiscountComponentByProduct(Product Product, double percentOfDiscount) throws WrongPermterException {
        super(percentOfDiscount);
        this.ProductToDiscount = Product;
    }

    public simpleDiscountComponentByProduct() {

    }

    @Override
    public double CalculateDiscount(Basket basket) {
        Map<Product, Integer> basketMap = basket.getProducts_and_quantities();
        try {
            int quantity = basketMap.get(ProductToDiscount);
            double price = ProductToDiscount.getOriginal_price();
            return quantity * price * this.percentOfDiscount;
        } catch (NullPointerException e) {
            return 0;
        }
    }
}
