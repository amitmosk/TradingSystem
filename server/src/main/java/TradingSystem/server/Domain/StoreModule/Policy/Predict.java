package TradingSystem.server.Domain.StoreModule.Policy;

import TradingSystem.server.Domain.StoreModule.Product.Product;

import java.time.LocalDate;

public class Predict {

    private Product product;
    private double price_constraint = -1;
    private String category_constraint = "";
    private int quantity_constraint = -1;
    private boolean less_quantity_constraint; // true: less then quantity, false: more then quantity
    private int age_constraint = -1;
    private boolean less_age_constraint; // true: less then age, false: more then age
    private boolean before_time_constraint; // true: before, false: after.
    private int year_constraint = -1;
    private int month_constraint = -1;
    private int day_constraint = -1;
    private int hour_constraint = -1;
    private boolean less_price_constraint;

    public Predict(Product product, String category_constraint, int quantity_constraint,
                   boolean less_quantity_constraint, int age_constraint, boolean less_age_constraint,
                   boolean before_time_constraint, int year, int month, int day, int hour, double price_constraint, boolean less_price_constraint) {

        this.product = product;
        this.price_constraint = price_constraint;
        this.category_constraint = category_constraint;
        this.quantity_constraint = quantity_constraint;
        this.less_quantity_constraint = less_quantity_constraint;
        this.age_constraint = age_constraint;
        this.less_age_constraint = less_age_constraint;
        this.less_price_constraint = less_price_constraint;
        this.before_time_constraint = before_time_constraint;
        this.year_constraint = year;
        this.month_constraint = month;
        this.day_constraint = day;
        this.hour_constraint = hour;
    }


    public boolean CanBuy(int user_age, Product product, int quantity, double price) {
        String product_category = product.getCategory();
        String date = LocalDate.now().toString();

        int age = user_age;
        boolean flag1 = this.check_valid_quantity(quantity);
        boolean flag2 = this.check_valid_category(product_category);
        boolean flag3 = this.check_valid_time(date);
        boolean flag4 = this.check_valid_age(age);
        boolean flag5 = this.check_valid_price(price);

        return flag1 || flag2 || flag3 || flag4 || flag5;
    }

    private boolean check_valid_age(int age) {
        if (this.age_constraint != -1) {
            // check that age < con_age
            if (less_age_constraint)
                return age <= this.age_constraint;
            else
                return age > this.age_constraint;
        }
        return true;
    }

    private boolean check_valid_time(String date) {
        // TODO: build vars from date
        int year = -1, month = -1, day = -1, hour = -1;
        if (before_time_constraint) {
            // cant buy before the given time
            if (year_constraint != -1 && year < year_constraint)
                return false;
            if (month_constraint != -1 && month < month_constraint)
                return false;
            if (day_constraint != -1 && day < day_constraint)
                return false;
            if (hour_constraint != -1 && hour < hour_constraint)
                return false;
        } else {
            // cant buy after the given time
            if (year_constraint != -1 && year > year_constraint)
                return false;
            if (month_constraint != -1 && month > month_constraint)
                return false;
            if (day_constraint != -1 && day > day_constraint)
                return false;
            if (hour_constraint != -1 && hour > hour_constraint)
                return false;

        }
        return true;

    }

    private boolean check_valid_category(String product_category) {
        // this.category_constraint cant be null! otherwise there will be null pointer exception
        return product_category.equals(this.category_constraint);

    }

    private boolean check_valid_quantity(int quantity) {
        if (this.quantity_constraint != -1) {
            // check that quantity < con_quantity
            if (less_quantity_constraint)
                return quantity <= this.quantity_constraint;
            else
                return quantity > this.quantity_constraint;
        }
        return true;
    }

    public boolean check_valid_price(double price) {
        if (this.price_constraint != -1) {
            // check that price < con_price
            if (less_price_constraint)
                return price <= this.price_constraint;
            else
                return price > this.price_constraint;
        }
        return true;
    }
}
