package Unit.PasswordModule;

import TradingSystem.server.Domain.Utils.Password.PasswordManagerImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class PasswordManagerImplTest {
    private PasswordManagerImpl passwordManager;
    @BeforeEach
    void setUp() {
        this.passwordManager = new PasswordManagerImpl();

    }

    @Test
    void hash_2samePassowrds_notEqualsTokens() {
        String pass1 = this.passwordManager.hash("12345678");
        String pass2 = this.passwordManager.hash("12345678");
        assertNotEquals(pass1, pass2);
    }

    static Stream<Arguments> passwords_to_check() {
        return Stream.of(
                arguments(""),
                arguments("12345678"),
                arguments("AaAaAaAaAa"),
                arguments("123456AAA!!$!#$#!&"),
                arguments("!@#$%$&%&((&)&&$%%@"),
                arguments("asdafvssfg56565656565656565556566565656556")
        );
    }

    @ParameterizedTest
    @MethodSource("passwords_to_check")
    void hash_and_authenticate_passwords(String password) {
        String token = this.passwordManager.hash(password);
        boolean flag = this.passwordManager.authenticate(password, token);
        assertTrue(flag);
    }

    @Test
    void authenticate_2samePasswords() {
        String pass1 = this.passwordManager.hash("12345678");
        String pass2 = this.passwordManager.hash("12345678");
        boolean flag1 = this.passwordManager.authenticate("12345678", pass1);
        boolean flag2 = this.passwordManager.authenticate("12345678", pass2);
        assertNotEquals(pass1, pass2);
        assertTrue(flag1);
        assertTrue(flag2);
    }


    @Test
    void caseSensetiveCheck() {
        String pass1 = this.passwordManager.hash("aA12345678");
        boolean flag1 = this.passwordManager.authenticate("aa12345678", pass1);
        boolean flag2 = this.passwordManager.authenticate("AA12345678", pass1);
        boolean flag3 = this.passwordManager.authenticate("Aa12345678", pass1);
        assertFalse(flag1);
        assertFalse(flag2);
        assertFalse(flag3);

    }
}