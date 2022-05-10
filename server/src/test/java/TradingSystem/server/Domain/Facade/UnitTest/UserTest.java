package TradingSystem.server.Domain.Facade.UnitTest;

import TradingSystem.server.Domain.UserModule.User;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDateTime;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class UserTest {

    private User user;
    private User guest;

    @BeforeEach
    void setUp(){
        user = new User();
        guest = new User();
        try{
            user.register("eylon@gmail.com", "pssw0rD", "Eylon", "Sade", LocalDateTime.now().minusYears(30).toString());
        }
        catch (Exception e){}

    }


    static Stream<Arguments> user_info_provider1() {
        return Stream.of(
                arguments("check1@email.com", "pass123Chec", "name","last"),
                arguments("check2@email.com", "pass1Chec", "name","last"),
                arguments("check@email.com", "Ch3ckPsw0rd", "checker", "checkcheck"),
                arguments("check@email.com", "Ch3ckPsw0rd1", "checkcheck", "check"),
                arguments("check@email.com", "Ch3ckP", "checker", "checkcheck")


        );
    }

    @ParameterizedTest
    @MethodSource("user_info_provider1")
    void register_success(String email, String pw, String name, String lastName) {
        boolean result;
        try{
            guest.register(email, pw, name, lastName,  LocalDateTime.now().minusYears(30).toString());
            result = true;
        }
        catch (Exception e){
            result = false;
        }
        assertTrue(result);
    }


    static Stream<Arguments> user_info_provider2() {
        return Stream.of(
                arguments("checkmail.com", "passCh3ck", "name","last"),
                arguments("check@mailcom", "passCh3ck", "name","last"),
                arguments("check@mail..com", "passCh3ck", "name","last"),
                arguments("check@@@mail.com", "passCh3ck", "name","last"),
                arguments("check@mail.com", "pa$$Ch3ck", "name","last"),
                arguments("check@mail.com", "passCh3ck1234", "name","last"),
                arguments("check@mail.com", "pCh3k", "name","last"),
                arguments("check@mail.com", "passch3ck", "name","last"),
                arguments("check@mail.com", "passChEck", "name","last"),
                arguments("check@mail.com", "passCh3ck", "name","lastnamebig"),
                arguments("check@mail.com", "passCh3ck", "nameistobig","last"),
                arguments("chec@mail.com", "pa$$123Check1", "name","last"),
                arguments("check2@email.com", "passwww", "name","last"),
                arguments("chec@gmail.com", "passCh3ck", "","last"),
                arguments("chec@gmail.com", "pass123Chec", "name","last1last2last3last4"),
                arguments("chec@gmail.com", "pass123Chec", "name","")


        );
    }

    @ParameterizedTest
    @MethodSource("user_info_provider2")
    void register_fail(String email, String pw, String name, String lastName) {
        boolean result;
        try{
            user.register(email, pw, name, lastName, LocalDateTime.now().minusYears(30).toString());
            result = true;
        }
        catch (Exception e){
            result = false;
        }
        assertEquals(false, result);
        result = false;
        try{
            guest.register(email, pw, name, lastName, LocalDateTime.now().minusYears(30).toString());
            result = true;
        }
        catch (Exception e){
            result = false;
        }
        assertEquals(false, result);
    }


    @Test
    void login() {

        try{
            user.login("pssw0rD");
            assertTrue(user.isLogged(),"user should be logged in");
            user.logout();
            user.login("pssw02rD");
            assertFalse(user.isLogged(),"logged in with invalid password");
            user.login("pssw0rD");
            assertTrue(user.isLogged(),"failed to log in offline user with his password");
        }
        catch (Exception e){}

    }

    @Test
    void edit_name(){

        try{
            user.edit_name("pssw0rD", "Gal");
            assertEquals("Gal", user.get_user_name());
            user.edit_name("psddsw0rD", "Eylon");
            assertEquals("Gal", user.get_user_name());
        }
        catch (Exception e){}
    }


}
