package Acceptance.Store;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.ExternalSystems.PaymentAdapterImpl;
import TradingSystem.server.Domain.ExternalSystems.PaymentAdapterTests;
import TradingSystem.server.Domain.ExternalSystems.SupplyAdapterImpl;
import TradingSystem.server.Domain.ExternalSystems.SupplyAdapterTests;
import TradingSystem.server.Domain.Facade.MarketFacade;
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
import TradingSystem.server.Domain.StoreModule.Product.ProductInformation;
import TradingSystem.server.Domain.Utils.Exception.MarketException;
import TradingSystem.server.Domain.Utils.Exception.WrongPermterException;
import TradingSystem.server.Domain.Utils.Response;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class policyTestingAccptence {
    MarketFacade marketFacade;
    int store_id;
    int userid = 0;

    @BeforeEach
    void SetUp() throws MarketException {
        HibernateUtils.set_tests_mode();
        marketFacade = new MarketFacade(new PaymentAdapterTests(), new SupplyAdapterTests());
        marketFacade.register("amitgrumet" + userid + "@gmail.com", "12345678aA", "amit", "grumet", LocalDate.now().minusYears(30).toString());
        Response<Integer> resId = marketFacade.open_store("store1");
        store_id = resId.getValue();
        userid++;
    }


    @Test
    void DiscountByStore() {
        marketFacade.add_simple_store_discount_rule(store_id, 0.5, "byStore");
        List<String> list = ((List) marketFacade.get_discount_policy(store_id).getValue());
        assertEquals(1, ((List) marketFacade.get_discount_policy(store_id).getValue()).size());
        assertEquals(true, ((List<String>) (marketFacade.get_discount_policy(store_id).getValue())).get(0).equals("byStore"));
    }

    @Test
    void DiscontByProduct() throws MarketException {
        marketFacade.add_product_to_store(store_id, 100, "a", 100, "fruits", new LinkedList<>());
        marketFacade.add_simple_product_discount_rule(store_id, marketFacade.get_store(store_id).getInventory().keySet().iterator().next().getProduct_id(), 0.5, "byStore");
        assertEquals(1, ((List) marketFacade.get_discount_policy(store_id).getValue()).size());
        String s = ((List<String>) (marketFacade.get_discount_policy(store_id).getValue())).get(0);
        assertEquals(true, ((List<String>) (marketFacade.get_discount_policy(store_id).getValue())).get(0).equals("byStore"));
    }

    @Test
    void discountByCategory() {
        marketFacade.add_simple_category_discount_rule(store_id, "fruits", 0.5, "byStore");
        assertEquals(1, ((List) marketFacade.get_discount_policy(store_id).getValue()).size());
        String s = ((List<String>) (marketFacade.get_discount_policy(store_id).getValue())).get(0);
        assertEquals(true, ((List<String>) (marketFacade.get_discount_policy(store_id).getValue())).get(0).equals("byStore"));
    }

    @Test
    void ComplexDiscountAndPredict() {
        marketFacade.add_simple_category_discount_rule(store_id, "fruits", 0.5, "byStore");
        marketFacade.add_predict(store_id, "", -1, true, false, 100,
                true, false, false, false, 0, 0, 0, "predict");
        marketFacade.add_complex_discount_rule(store_id,"predict","byStore","complex");
        assertEquals(1, ((List) marketFacade.send_predicts(store_id).getValue()).size());
        assertEquals(true, ((List<String>) (marketFacade.get_discount_policy(store_id).getValue())).get(0).equals("complex"));
    }


    @Test
    void SimplePurchase() {
        marketFacade.add_predict(store_id, "", -1, true, false, 100,
                true, false, false, false, 0, 0, 0, "predict");
        marketFacade.add_simple_purchase_rule("predict", "purchase", store_id);
        assertEquals(1, ((List) marketFacade.get_purchase_policy(store_id).getValue()).size());
        assertEquals(true, ((List<String>) (marketFacade.get_purchase_policy(store_id).getValue())).get(0).equals("purchase"));
    }

    @Test
    void PurhcaseAnd() {
        marketFacade.add_predict(store_id, "", -1, true, false, 100,
                true, false, false, false, 0, 0, 0, "predict");
        marketFacade.add_predict(store_id, "", -1, true, false, 100,
                true, false, false, false, 0, 0, 0, "predictr");
        marketFacade.add_simple_purchase_rule("predict", "purchase", store_id);
        marketFacade.add_simple_purchase_rule("predictr", "purchaser", store_id);
        marketFacade.add_and_purchase_rule("purchase", "purchaser", store_id, "and");
        assertEquals(1, ((List) marketFacade.get_purchase_policy(store_id).getValue()).size());
        assertEquals(true, ((List<String>) (marketFacade.get_purchase_policy(store_id).getValue())).get(0).equals("and"));
    }

    @Test
    void PurchesOr() {
        marketFacade.add_predict(store_id, "", -1, true, false, 100,
                true, false, false, false, 0, 0, 0, "predict");
        marketFacade.add_predict(store_id, "", -1, true, false, 100,
                true, false, false, false, 0, 0, 0, "predictr");
        marketFacade.add_simple_purchase_rule("predict", "purchase", store_id);
        marketFacade.add_simple_purchase_rule("predictr", "purchaser", store_id);
        marketFacade.add_or_purchase_rule("purchase", "purchaser", store_id, "or");
        assertEquals(1, ((List) marketFacade.get_purchase_policy(store_id).getValue()).size());
        assertEquals(true, ((List<String>) (marketFacade.get_purchase_policy(store_id).getValue())).get(0).equals("or"));
    }


}