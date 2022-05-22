package TradingSystem.server.Domain.Facade.UnitTest;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.numric.MaxDiscountRule;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.numric.PlusDiscountRule;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.DiscountComponentByCategory;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.DiscountComponentByProduct;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.DiscountComponentByStore;
import TradingSystem.server.Domain.StoreModule.Product.Product;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class policyTesting {
    Basket basket;
    Product apple;
    Product banana;
    Product grape;
    Product iphone;
    Product samsung;
    Product xiaomi;

    @BeforeEach
    void SetUp() throws MarketException {
        basket = new Basket(1, "amit@gmail.com");
        apple = new Product("apple", 1, 1, 100, "fruits", new LinkedList<>());
        banana = new Product("banana", 1, 1, 50, "fruits", new LinkedList<>());
        grape = new Product("grape", 1, 1, 75, "fruits", new LinkedList<>());


        iphone = new Product("iphone", 1, 1, 150, "tech", new LinkedList<>());
        samsung = new Product("samsung", 1, 1, 200, "tech", new LinkedList<>());
        xiaomi = new Product("xiaomi", 1, 1, 300, "tech", new LinkedList<>());

        basket.addProduct(apple, 10);
        basket.addProduct(banana, 10);
        basket.addProduct(grape, 10);
        basket.addProduct(iphone, 10);
        basket.addProduct(samsung, 10);
        basket.addProduct(xiaomi, 10);


    }
    //purchase Testing


    //discount Testing
    static Stream<Arguments> percents() {
        return Stream.of(
                Arguments.arguments(1),
                Arguments.arguments(2),
                Arguments.arguments(-1),
                Arguments.arguments(-0.1)
        );
    }

    static Stream<Arguments> discounts() throws WrongPermterException {
        DiscountComponentByCategory tech = new DiscountComponentByCategory("tech", 0.5);
        DiscountComponentByCategory fruits = new DiscountComponentByCategory("fruits", 0.5);
        DiscountComponentByCategory blah = new DiscountComponentByCategory("blah", 0.5);
        return Stream.of(
                Arguments.arguments(tech, fruits), Arguments.arguments(fruits, tech), Arguments.arguments(blah, fruits)
        );
    }

    @ParameterizedTest
    @MethodSource("percents")
    void checkValidDisocunt(double percent) {
        try {
            DiscountComponentByStore byStore = new DiscountComponentByStore(percent);
        } catch (WrongPermterException e) {
            assertTrue(true);
        }
    }


    @Test
    void DiscountByStore() {
        try {
            DiscountComponentByStore byStore = new DiscountComponentByStore(0.5);
            double discount = byStore.CalculateDiscount(basket);
            assertTrue(discount == 4375.0);
        } catch (WrongPermterException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void DiscontByProduct() {
        try {
            DiscountComponentByProduct byProduct = new DiscountComponentByProduct(apple, 0.5);
            double discount = byProduct.CalculateDiscount(basket);
            assertTrue(discount == 500.0);
        } catch (WrongPermterException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void DiscontByCategory() {
        try {
            DiscountComponentByCategory Tech = new DiscountComponentByCategory("tech", 0.5);
            DiscountComponentByCategory fruits = new DiscountComponentByCategory("fruits", 0.5);
            double discountTech = Tech.CalculateDiscount(basket);
            double discountFruits = fruits.CalculateDiscount(basket);
            assertTrue(discountTech == 3250.0, "tech discount not equel 3250");
            assertTrue(discountFruits == 1125.0, "fruits discount not equel 1125");
        } catch (WrongPermterException e) {
            fail(e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("discounts")
    void DiscountMax(DiscountComponent dis1, DiscountComponent dis2) {
        MaxDiscountRule max = new MaxDiscountRule(dis1, dis2);
        assertTrue(max.CalculateDiscount(basket) == Math.max(dis2.CalculateDiscount(basket), dis1.CalculateDiscount(basket)));
    }

    @ParameterizedTest
    @MethodSource("discounts")
    void DiscountAdd(DiscountComponent dis1, DiscountComponent dis2) {
        PlusDiscountRule plusDiscountRule = new PlusDiscountRule(dis1, dis2);
        assertTrue(plusDiscountRule.CalculateDiscount(basket) == dis2.CalculateDiscount(basket)+dis1.CalculateDiscount(basket));
    }
        static Stream<Arguments> Predicts() throws MarketException {
        Product apple = new Product("apple", 1, 1, 100, "fruits", new LinkedList<>());
        DiscountComponentByStore ByStore = new DiscountComponentByStore(0.5);


        Basket TestBasket = new Basket(1, "amit");
        TestBasket.addProduct(apple, 4);
        //SimpleDiscountComponent simpleDiscountComponent, String catgorey, Product product, boolean above, boolean equql,
        // int num, boolean price, boolean quantity, boolean age, boolean time, int year, int month, int day, double amountOfDiscount

        Predict only_above_hunderd_shkal = new Predict("", null, true, false, 100,
                true, false, false, false, 0, 0, 0);
        Predict only_below_hunderd_shkal = new Predict("", null, false, false, 100,
                true, false, false, false, 0, 0, 0);

        Predict only_above_five_products = new Predict("", null, true, false, 5,
                false, true, false, false, 0, 0, 0);
        Predict only_below_five_products = new Predict("", null, false, false, 5,
                false, true, false, false, 0, 0, 0);

        Predict only_in_catgyorey_fruits = new Predict("fruits", null, false, true, 0,
                false, false, false, false, 0, 0, 0);
        Predict only_not_in_catgyorey_fruits = new Predict("", null, false, false, 5,
                false, true, false, false, 0, 0, 0);


        return Stream.of(
                //SimpleDiscountComponent simpleDiscountComponent, String catgorey, Product product, boolean above, boolean equql,
                // int num, boolean price, boolean quantity, boolean age, boolean time, int year, int month, int day, double amountOfDiscount


                //by qunatity
                Arguments.arguments(ByStore, only_above_five_products, 0,TestBasket),
                Arguments.arguments(ByStore, only_below_five_products, 200,TestBasket),

                //by price
                Arguments.arguments(ByStore, only_above_hunderd_shkal, 200,TestBasket),
                Arguments.arguments(ByStore, only_below_hunderd_shkal, 0,TestBasket),

                //by catgeory
                Arguments.arguments(ByStore, only_not_in_catgyorey_fruits, 0,TestBasket),
                Arguments.arguments(ByStore, only_in_catgyorey_fruits,TestBasket)

        );
    }
     @ParameterizedTest
    @MethodSource("Predicts")
    void ComplexDiscountPassing(SimpleDiscountComponent simpleDiscountComponent, Predict p, double amountOfDiscount,Basket basket) {
        assertEquals(new ComplexDiscountComponent(simpleDiscountComponent, p).CalculateDiscount(18,basket), amountOfDiscount);
    }
//    @ParameterizedTest
//    void ComplexDiscount(Predict) {
//        try {
//            DiscountRuleByStore byStore = new DiscountRuleByStore(0.5);
//            double discount = byStore.CalculateDiscount(basket);
//        } catch (WrongPermterException e) {
//            e.printStackTrace();
//        }
//    }


}
