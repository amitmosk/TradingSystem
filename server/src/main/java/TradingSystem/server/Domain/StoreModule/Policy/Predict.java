package TradingSystem.server.Domain.StoreModule.Policy;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.UserModule.User;

import java.time.LocalDateTime;
import java.util.Map;

public class Predict implements Ipredict {
    //on what
    private String catgorey;
    private Product product;

    //what type < > =
    private boolean above;//true=above false=below
    private boolean equql;//true=to regerd false=to not regerd

    //value
    private int num;

    //field
    private boolean price_constraint;
    private boolean quantity_constraint;
    private boolean age_constraint;
    private boolean time_constraint;
    private boolean category_constraint;
    private boolean product_constraint;

    //time
    private int year;
    private int month;
    private int day;

    public Predict(String catgorey, Product product, boolean above, boolean equql, int num, boolean price, boolean quantity, boolean age, boolean time, int year, int month, int day) {
        this.catgorey = catgorey;
        this.product = product;
        this.above = above;
        this.equql = equql;
        this.category_constraint = catgorey != "";
        this.product_constraint = product != null;
        this.num = num;
        this.price_constraint = price;
        this.quantity_constraint = quantity;
        this.age_constraint = age;
        this.time_constraint = time;
        this.year = year;
        this.month = month;
        this.day = day;
    }


    private boolean CanApply(int age, Product product, int quantity, double price) {
        String product_category = product.getCategory();
        boolean quantityCheck = this.check_valid_quantity(quantity);
        boolean CategoryCheck = this.check_valid_category(product_category);
        boolean TimeCheck = this.check_valid_time();
        boolean AgeCheck = this.check_valid_age(age);
        boolean PriceCheck = this.check_valid_price(price * quantity);
        boolean ProductCheck = this.checkProduct(product);
        return quantityCheck || CategoryCheck || TimeCheck || AgeCheck || PriceCheck || ProductCheck;
    }

    public boolean CanApply(int age, Basket b) {
        Map<Product, Integer> map = b.getProducts_and_quantities();
        for (Map.Entry<Product, Integer> entry : map.entrySet())
            if (CanApply(age, entry.getKey(), entry.getValue(), entry.getKey().getPrice()))
                return true;
        return false;
    }

    public boolean CanApply(Basket b) {
        Map<Product, Integer> map = b.getProducts_and_quantities();
        for (Map.Entry<Product, Integer> entry : map.entrySet())
            if (CanApply(entry.getKey(), entry.getValue(), entry.getKey().getPrice()))
                return true;
        return false;
    }

    private boolean CanApply(Product product, int quantity, double price) {
        String product_category = product.getCategory();
        boolean quantityCheck = this.check_valid_quantity(quantity);
        boolean CategoryCheck = this.check_valid_category(product_category);
        boolean TimeCheck = this.check_valid_time();
        boolean PriceCheck = this.check_valid_price(price * quantity);
        boolean ProductCheck = this.checkProduct(product);
        return quantityCheck || CategoryCheck || TimeCheck || PriceCheck || ProductCheck;
    }

    private boolean checkProduct(Product product) {
        if (product_constraint)
            if (equql)
                return product.getName().equals(this.product.getName());
            else
                return !product.getName().equals(this.product.getName());
        return false;
    }

    private boolean checkField(double numTocheck) {
        if (equql)
            return num == numTocheck;
        if (above)
            return numTocheck > num;
        else
            return numTocheck < num;
    }

    private boolean check_valid_age(int age) {
        if (age_constraint)
            return checkField(age);
        return false;
    }

    //TODO improve time formats allowed
    private boolean check_valid_time() {
        LocalDateTime localDateTime = LocalDateTime.now();
        int year = localDateTime.getYear();
        int month = localDateTime.getMonthValue();
        int day = localDateTime.getDayOfMonth();
        if (time_constraint) {
            if (equql)
                return year == this.year && month == this.month && this.day == day;
            else
                return year != this.year || month != this.month || this.day != day;
        }
        return false;
    }

    private boolean check_valid_category(String product_category) {
        if (category_constraint)
            if (equql)
                return product_category.equals(this.catgorey);
            else
                return !product_category.equals(this.catgorey);
        return false;
    }

    private boolean check_valid_quantity(int quantity) {
        if (quantity_constraint)
            return checkField(quantity);
        return false;
    }

    public boolean check_valid_price(double price) {
        if (price_constraint)
            return checkField(price);
        return false;
    }
}
