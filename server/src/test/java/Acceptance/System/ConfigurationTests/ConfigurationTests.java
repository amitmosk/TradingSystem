
package Acceptance.System.ConfigurationTests;

import TradingSystem.server.DAL.HibernateUtils;
import TradingSystem.server.Domain.ExternalSystems.PaymentInfo;
import TradingSystem.server.Domain.ExternalSystems.SupplyInfo;
import TradingSystem.server.Domain.Facade.MarketFacade;
import TradingSystem.server.Domain.Utils.Exception.ExitException;
import TradingSystem.server.Domain.Utils.Response;
import TradingSystem.server.Service.MarketSystem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.LinkedList;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class ConfigurationTests {
    //server//src//test//java//Acceptance//System//ConfigurationTests//
    // services
        // good
    private static final String tests_external_services_path =  "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\external_services\\tests_external_services.txt";

    private static final String demo_external_services_path =  "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\external_services\\demo_external_services.txt";

    private static final String real_external_services_path =  "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\external_services\\real_external_services.txt";
        // bad
    private static final String bad_external_services_path =  "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\external_services\\bad_external_services.txt";

    private static final String denied_services_path = "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\external_services\\denied_external_services.txt";

    // instructions
        // good
    private static final String empty_test_path =  "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\good_instructions\\empty_test.txt";
    private static final String goodIns1_path = "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\good_instructions\\good_inst1.txt";
    private static final String goodIns2_path = "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\good_instructions\\good_inst2.txt";
    private static final String goodIns3_path = "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\good_instructions\\good_inst3.txt";
    private static final String goodIns4_path = "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\good_instructions\\good_inst4.txt";
    private static final String goodIns5_path = "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\good_instructions\\good_inst5.txt";
    private static final String goodIns6_path = "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\good_instructions\\good_inst6.txt";
        // bad
    private static final String wrong_format_test_path = "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\bad_instructions\\wrong_format_test.txt";
    private static final String wrong_instruction_test_path = "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\bad_instructions\\wrong_instruction_test.txt";
    private static final String wrong_order_test_path = "..\\server\\src\\test\\java\\Acceptance\\System\\ConfigurationTests\\bad_instructions\\wrong_order_test_path.txt";



    @AfterEach
    void tearDown(){
        HibernateUtils.set_tests_mode();
    }

    /**
     * this test check that the system will not initialize data because there are:
     * 1. wrong format of the data-configuration file instructions.
     * 2. instruction with bad parameters.
     * 3. wrong logic order of the instructions.
     */
    static Stream<Arguments> bad_demo_instructions() {
        return Stream.of(
                arguments(wrong_format_test_path),
                arguments(wrong_instruction_test_path),
                arguments(wrong_order_test_path)
        );
    }
    @ParameterizedTest
    @MethodSource("bad_demo_instructions")
    void bad_demo_instructions(String instructions_config_path){
        boolean answer =  false;
        try{

            MarketSystem marketSystem = new MarketSystem(demo_external_services_path, instructions_config_path);
        }
        catch (ExitException e){
            answer = true;
        }
        assertFalse(answer);
    }


    /**
     * this test check that the system will initialize data with demo status:
     * 1. empty_test_path -> with 0 instructions
     * 2. goodIns1 : register & logout
     * 3. goodIns2 : with spaces, register & logout & login
     * 4. goodIns3 : more then 1 user.
     */
    static Stream<Arguments> good_demo_instructions() {
        return Stream.of(
                arguments(empty_test_path),
                arguments(goodIns1_path),
                arguments(goodIns2_path),
                arguments(goodIns3_path),
                arguments(goodIns4_path),
                arguments(goodIns5_path),
                arguments(goodIns6_path)
        );
    }
    @ParameterizedTest
    @MethodSource("good_demo_instructions")
    void system_init_good_demo_instructions(String instructions_config_path){
        try{

            MarketSystem marketSystem = new MarketSystem(demo_external_services_path, instructions_config_path);
        }
        catch (ExitException e){
            fail();
        }
        assertTrue(true);
    }


    /**
     * this test check that the system will load successfully and initialize database in the next 3 modes:
     * 1. tests mode
     * 2. real mode
     * 3. demo mode
     */
    static Stream<Arguments> good_config_file() {
        return Stream.of(
                arguments(tests_external_services_path),
                arguments(real_external_services_path),
                arguments(demo_external_services_path)
        );
    }
    @ParameterizedTest
    @MethodSource("good_config_file")
    void system_good_config_file(String services_config_path){
        try{

            MarketSystem marketSystem = new MarketSystem(services_config_path, empty_test_path);
        }
        catch (ExitException e){
            fail();
        }
        assertTrue(true, "The system start running after reading a nice configuration file.");
    }


    /**
     * this test check that the system will not load when the config file is:
     * 1. with wrong format
     * 2.
     */
    static Stream<Arguments> bad_config_file() {
        return Stream.of(
                arguments(bad_external_services_path)
        );
    }
    @ParameterizedTest
    @MethodSource("bad_config_file")
    void system_bad_config_file_format(String services_config_path){
        boolean answer =  false;
        try{
            MarketSystem marketSystem = new MarketSystem(services_config_path, empty_test_path);
        }
        catch (ExitException e){
            answer = true;
        }
        assertTrue(answer, "The system stop running after reading bad configuration file.");
    }


    /**
     * this test check that the system is stop running if we got bad answer from the external services when we
     * try to handshake.
     */
    @Test
    void denied_external_systems(){
        boolean answer =  false;
        try{

            MarketSystem marketSystem = new MarketSystem(denied_services_path, empty_test_path);
        }
        catch (ExitException e){
            answer = true;
        }

        assertTrue(answer, "The Server is stop running after external services handshake denied.");
    }


    @Test
    void read_config_file(){
        String[] to_return = {"1", "1"};
        try{

            MarketSystem marketSystem = new MarketSystem(tests_external_services_path, empty_test_path);
            to_return = marketSystem.read_config_file(tests_external_services_path);
        }
        catch (ExitException e){
            fail();
        }
        assertEquals("external_services:tests", to_return[0]);
        assertEquals("database:tests", to_return[1]);
    }
}
