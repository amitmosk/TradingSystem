package TradingSystem.server.Service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {

    private final String external_services_path = "C:\\Users\\Amit\\Desktop\\SemF\\Sadna\\TradingSystem\\server\\src\\test\\java\\TradingSystem" +
            "\\server\\Service\\external_services.txt";

    private final String empty_test_path = "C:\\Users\\Amit\\Desktop\\SemF\\Sadna\\TradingSystem\\server\\src\\test\\java\\TradingSystem" +
            "\\server\\Service\\empty_test.txt";
    private final String wrong_format_test_path = "C:\\Users\\Amit\\Desktop\\SemF\\Sadna\\TradingSystem\\server\\src\\test\\java\\TradingSystem" +
            "\\server\\Service\\wrong_format_test.txt";
    private final String wrong_instruction_test_path = "C:\\Users\\Amit\\Desktop\\SemF\\Sadna\\TradingSystem\\server\\src\\test\\java\\TradingSystem" +
            "\\server\\Service\\wrong_instruction_test.txt";
    private final String wrong_order_test_path = "C:\\Users\\Amit\\Desktop\\SemF\\Sadna\\TradingSystem\\server\\src\\test\\java\\TradingSystem" +
            "\\server\\Service\\wrong_order_test_path.txt";

    @Test
    void Empty_test_path() {
        MarketSystem marketSystem = new MarketSystem(external_services_path, empty_test_path);
        int x = 5;
    }

    @Test
    void Wrong_format_test_path(){
        MarketSystem marketSystem = new MarketSystem(external_services_path, wrong_format_test_path);
        int x = 5;

    }
}