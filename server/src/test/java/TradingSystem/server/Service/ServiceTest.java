package TradingSystem.server.Service;

import TradingSystem.server.Config.SystemStartConfig;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceTest {


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
        boolean answer = SystemStartConfig.config_instructions_data(empty_test_path);
        assertTrue(answer);
    }

    @Test
    void Wrong_format_test_path(){
        boolean answer = SystemStartConfig.config_instructions_data(wrong_format_test_path);
        assertFalse(answer);
    }
}