package Acceptance.System.ExternSystemsTests;

import TradingSystem.server.Domain.ExternalSystems.ExternPaymentSystemProxy;
import TradingSystem.server.Domain.ExternalSystems.PaymentInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ExternPaymentSystemProxyTest {

    private ExternPaymentSystemProxy externPaymentSystemProxy;


    @Test
    void handshake(){
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        boolean answer = this.externPaymentSystemProxy.handshake();
        assertTrue(answer);

    }

    @Test
    void payment(){
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        PaymentInfo payment_info = new PaymentInfo("123","456","789","245","986","455");
        int answer3 = this.externPaymentSystemProxy.payment(500, payment_info);
        assertTrue(answer3-10000 > 0);
        assertTrue(answer3-100000 < 0);
    }

    static Stream<Arguments> cancel_payment_bad_provider() {
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
    @MethodSource("cancel_payment_bad_provider")
    void cancel_payment_bad_input(int transaction_id){
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        int answer = this.externPaymentSystemProxy.cancel_payment(transaction_id);
        assertEquals(answer, 1);
    }


    static Stream<Arguments> cancel_payment_good_provider() {
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
    @MethodSource("cancel_payment_good_provider")
    void cancel_payment_good_input(int transaction_id){
        this.externPaymentSystemProxy = new ExternPaymentSystemProxy();
        int answer = this.externPaymentSystemProxy.cancel_payment(transaction_id);
        assertEquals(answer, 1);
    }
}