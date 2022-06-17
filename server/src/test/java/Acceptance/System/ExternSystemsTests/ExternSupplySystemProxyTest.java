package Acceptance.System.ExternSystemsTests;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ExternSupplySystemProxyTest {

    private ExternSupplySystemProxy externSupplySystemProxy;


    @Test
    void handshake(){
        this.externSupplySystemProxy = new ExternSupplySystemProxy();
        boolean answer = this.externSupplySystemProxy.handshake();
        assertTrue(answer);

    }

    static Stream<Arguments> supply_input() {
        return Stream.of(
                arguments(9999),
                arguments(1),
                arguments(1000),
                arguments(5000),
                arguments(6000),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001),
                arguments(100001)
        );
    }


    @ParameterizedTest
    @MethodSource("supply_input")
    void supply(int x){
        this.externSupplySystemProxy = new ExternSupplySystemProxy();
        SupplyInfo supplyInfo = new SupplyInfo("1","2","3","4","5");
        int answer3 = this.externSupplySystemProxy.supply(supplyInfo);
        assertTrue(answer3-10000 > 0);
        assertTrue(answer3-100000 < 0);
    }



    static Stream<Arguments> cancel_supply_bad_provider() {
        return Stream.of(
                arguments(9999),
                arguments(1),
                arguments(1000),
                arguments(5000),
                arguments(6000),
                arguments(100001)
        );
    }


    @ParameterizedTest
    @MethodSource("cancel_supply_bad_provider")
    void cancel_supply_bad_input(int transaction_id){
        this.externSupplySystemProxy = new ExternSupplySystemProxy();
        int answer = this.externSupplySystemProxy.cancel_supply(transaction_id);
        assertEquals(answer, 1);
    }


    static Stream<Arguments> cancel_supply_good_provider() {
        return Stream.of(
                arguments(10000),
                arguments(50000),
                arguments(555555),
                arguments(456789),
                arguments(600500),
                arguments(1000000)
        );
    }


    @ParameterizedTest
    @MethodSource("cancel_supply_good_provider")
    void cancel_payment_good_input(int transaction_id){
        this.externSupplySystemProxy = new ExternSupplySystemProxy();
        int answer = this.externSupplySystemProxy.cancel_supply(transaction_id);
        assertEquals(answer, 1);
    }
}