package Config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordHashTest {

    @Test
    void passwordHash2() {

        //given
        String password = "admin";
        PasswordHash service = new PasswordHash();

        //when
        String result = service.passwordHash2(password);

        //then
        Assertions.assertEquals(result, "21232f297a57a5a743894a0e4a801fc3");
    }
}