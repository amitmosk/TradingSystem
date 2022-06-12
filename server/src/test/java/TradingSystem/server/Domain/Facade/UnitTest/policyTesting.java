package TradingSystem.server.Domain.Facade.UnitTest;

import TradingSystem.server.Domain.StoreModule.Basket;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.ComplexDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.DiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent.OrCompositePredict;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.logicCompnent.andCompsoitePredict;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.numric.maxDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.numric.plusDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponentByCategory;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponentByProduct;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponentByStore;
import TradingSystem.server.Domain.StoreModule.Policy.Discount.simple.simpleDiscountComponent;
import TradingSystem.server.Domain.StoreModule.Policy.Predict;
import TradingSystem.server.Domain.StoreModule.Policy.Purchase.OrPurchaseRule;
import TradingSystem.server.Domain.StoreModule.Policy.Purchase.SimplePurchaseRule;
import TradingSystem.server.Domain.StoreModule.Policy.Purchase.PurchaseRule;
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

import static org.junit.jupiter.api.Assertions.*;

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
        apple = new Product("apple", 1,  100, "fruits", new LinkedList<>(),1);
        banana = new Product("banana", 1,  50, "fruits", new LinkedList<>(),1);
        grape = new Product("grape", 1,  75, "fruits", new LinkedList<>(),1);


        iphone = new Product("iphone", 1, 150, "tech", new LinkedList<>(),1);
        samsung = new Product("samsung", 1, 200, "tech", new LinkedList<>(),1);
        xiaomi = new Product("xiaomi", 1, 300, "tech", new LinkedList<>(),1);

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
        simpleDiscountComponentByCategory tech = new simpleDiscountComponentByCategory("tech", 0.5);
        simpleDiscountComponentByCategory fruits = new simpleDiscountComponentByCategory("fruits", 0.5);
        simpleDiscountComponentByCategory blah = new simpleDiscountComponentByCategory("blah", 0.5);
        return Stream.of(
                Arguments.arguments(tech, fruits), Arguments.arguments(fruits, tech), Arguments.arguments(blah, fruits)
        );
    }

    @ParameterizedTest
    @MethodSource("percents")
    void checkValidDisocunt(double percent) {
        try {
            simpleDiscountComponentByStore byStore = new simpleDiscountComponentByStore(percent);
        } catch (WrongPermterException e) {
            assertTrue(true);
        }
    }


    @Test
    void DiscountByStore() {
        try {
            simpleDiscountComponentByStore byStore = new simpleDiscountComponentByStore(0.5);
            double discount = byStore.CalculateDiscount(basket);
            assertTrue(discount == 4375.0);
        } catch (WrongPermterException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void DiscontByProduct() {
        try {
            simpleDiscountComponentByProduct byProduct = new simpleDiscountComponentByProduct(apple, 0.5);
            double discount = byProduct.CalculateDiscount(basket);
            assertTrue(discount == 500.0);
        } catch (WrongPermterException e) {
            fail(e.getMessage());
        }
    }

    @Test
    void DiscontByCategory() {
        try {
            simpleDiscountComponentByCategory Tech = new simpleDiscountComponentByCategory("tech", 0.5);
            simpleDiscountComponentByCategory fruits = new simpleDiscountComponentByCategory("fruits", 0.5);
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
        maxDiscountComponent max = new maxDiscountComponent(dis1, dis2);
        assertTrue(max.CalculateDiscount(basket) == Math.max(dis2.CalculateDiscount(basket), dis1.CalculateDiscount(basket)));
    }

    @ParameterizedTest
    @MethodSource("discounts")
    void DiscountAdd(DiscountComponent dis1, DiscountComponent dis2) {
        plusDiscountComponent plusDiscountComponent = new plusDiscountComponent(dis1, dis2);
        assertTrue(plusDiscountComponent.CalculateDiscount(basket) == dis2.CalculateDiscount(basket) + dis1.CalculateDiscount(basket));
    }

    static Stream<Arguments> Predicts() throws MarketException {
        Product apple = new Product("apple", 1, 100, "fruits", new LinkedList<>(),1);
        simpleDiscountComponentByStore ByStore = new simpleDiscountComponentByStore(0.5);


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
        Predict only_not_in_catgyorey_fruits = new Predict("fruits", null, false, false, 5,
                false, false, false, false, 0, 0, 0);


        return Stream.of(
                //SimpleDiscountComponent simpleDiscountComponent, String catgorey, Product product, boolean above, boolean equql,
                // int num, boolean price, boolean quantity, boolean age, boolean time, int year, int month, int day, double amountOfDiscount


                //by qunatity
                Arguments.arguments(ByStore, only_above_five_products, 0, TestBasket),
                Arguments.arguments(ByStore, only_below_five_products, 200, TestBasket),

                //by price
                Arguments.arguments(ByStore, only_above_hunderd_shkal, 200, TestBasket),
                Arguments.arguments(ByStore, only_below_hunderd_shkal, 0, TestBasket),

                //by catgeory
                Arguments.arguments(ByStore, only_not_in_catgyorey_fruits, 0, TestBasket),
                Arguments.arguments(ByStore, only_in_catgyorey_fruits, 200, TestBasket)

        );
    }

    @ParameterizedTest
    @MethodSource("Predicts")
    void ComplexDiscountPassing(simpleDiscountComponent simpleDiscountComponent, Predict p, double amountOfDiscount, Basket basket) {
        assertEquals(new ComplexDiscountComponent(simpleDiscountComponent, p).CalculateDiscount(basket), amountOfDiscount);
    }

    static Stream<Arguments> PredictsOnly() throws MarketException {

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
        Predict only_not_in_catgyorey_fruits = new Predict("fruits", null, false, false, 5,
                false, false, false, false, 0, 0, 0);

        Predict only_in_catgyorey_tech = new Predict("tech", null, false, true, 0,
                false, false, false, false, 0, 0, 0);
        Product apple = new Product("apple", 1, 100, "fruits", new LinkedList<>(),1);
        simpleDiscountComponentByStore ByStore = new simpleDiscountComponentByStore(0.5);


        Basket TestBasket = new Basket(1, "amit");
        TestBasket.addProduct(apple, 7);
        andCompsoitePredict above5above100 = new andCompsoitePredict(only_above_five_products, only_above_hunderd_shkal);
        andCompsoitePredict above5infruits = new andCompsoitePredict(only_above_five_products, only_in_catgyorey_fruits);

        OrCompositePredict onlyintechorabove100 = new OrCompositePredict(only_in_catgyorey_tech, only_above_hunderd_shkal);
        andCompsoitePredict above5above100inftuits = new andCompsoitePredict(above5above100, only_in_catgyorey_fruits);
        andCompsoitePredict above5above100infruitsorintech = new andCompsoitePredict(above5above100inftuits, only_above_hunderd_shkal);


        ComplexDiscountComponent c1 = new ComplexDiscountComponent(ByStore, above5above100);
        ComplexDiscountComponent c2 = new ComplexDiscountComponent(ByStore, above5infruits);
        ComplexDiscountComponent c3 = new ComplexDiscountComponent(ByStore, onlyintechorabove100);
        ComplexDiscountComponent c4 = new ComplexDiscountComponent(ByStore, above5above100inftuits);
        ComplexDiscountComponent c5 = new ComplexDiscountComponent(ByStore, above5above100infruitsorintech);

        return Stream.of(
                //SimpleDiscountComponent simpleDiscountComponent, String catgorey, Product product, boolean above, boolean equql,
                // int num, boolean price, boolean quantity, boolean age, boolean time, int year, int month, int day, double amountOfDiscount


                Arguments.arguments(c1, TestBasket, 350),
                Arguments.arguments(c2, TestBasket, 350),
                Arguments.arguments(c3, TestBasket, 350),
                Arguments.arguments(c4, TestBasket, 350),
                Arguments.arguments(c5, TestBasket, 350)
        );
    }

    static Stream<Arguments> purchase() throws MarketException {

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
        Predict only_not_in_catgyorey_fruits = new Predict("fruits", null, false, false, 5,
                false, false, false, false, 0, 0, 0);

        Predict only_in_catgyorey_tech = new Predict("tech", null, false, true, 0,
                false, false, false, false, 0, 0, 0);
        Product apple = new Product("apple", 1, 100, "fruits", new LinkedList<>(),1);
        simpleDiscountComponentByStore ByStore = new simpleDiscountComponentByStore(0.5);

        SimplePurchaseRule only_above_hunderd_shkals = new SimplePurchaseRule(only_below_hunderd_shkal);
        SimplePurchaseRule only_above_five_productss = new SimplePurchaseRule(only_below_hunderd_shkal);
        SimplePurchaseRule only_in_catgyorey_fruitss = new SimplePurchaseRule(only_below_hunderd_shkal);


        Basket TestBasket = new Basket(1, "amit");
        TestBasket.addProduct(apple, 7);
        OrPurchaseRule above5above100 = new OrPurchaseRule(only_above_hunderd_shkals, only_above_five_productss);
        OrPurchaseRule above5infruits = new OrPurchaseRule(only_in_catgyorey_fruitss, above5above100);

        OrPurchaseRule onlyintechorabove100 = new OrPurchaseRule(above5infruits, above5above100);
        OrPurchaseRule above5above100inftuits = new OrPurchaseRule(onlyintechorabove100, above5above100);
        OrPurchaseRule above5above100infruitsorintech = new OrPurchaseRule(above5above100inftuits, onlyintechorabove100);


        return Stream.of(
                //SimpleDiscountComponent simpleDiscountComponent, String catgorey, Product product, boolean above, boolean equql,
                // int num, boolean price, boolean quantity, boolean age, boolean time, int year, int month, int day, double amountOfDiscount


                Arguments.arguments(above5above100, TestBasket, false),
                Arguments.arguments(above5infruits, TestBasket, false),
                Arguments.arguments(onlyintechorabove100, TestBasket, false),
                Arguments.arguments(above5above100inftuits, TestBasket, false),
                Arguments.arguments(above5above100infruitsorintech, TestBasket, false)
        );
    }

    @ParameterizedTest
    @MethodSource("PredictsOnly")
    void ComplexDiscount(ComplexDiscountComponent rule, Basket b, double res) {
        double discount = rule.CalculateDiscount(b);
        assertEquals(rule.CalculateDiscount(b), res);
    }

    @ParameterizedTest
    @MethodSource("purchase")
    void purchaseRule(PurchaseRule rule, Basket b, boolean res) {
        boolean ans = rule.predictCheck(18, b);
        assertEquals(res, ans);
    }

}
