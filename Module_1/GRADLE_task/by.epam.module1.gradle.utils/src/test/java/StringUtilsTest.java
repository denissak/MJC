import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class StringUtilsTest {

    @Test
    void isTestPositiveNumber() {
        boolean trueResult = StringUtils.isPositiveNumber("12");
        Assertions.assertTrue(trueResult);
    }

    @Test
    void isTestNegativeNumber() {
        boolean trueResult = StringUtils.isPositiveNumber("-12");
        Assertions.assertFalse(trueResult);
    }

    @Test
    void isTestSomeWord() {
        boolean trueResult = StringUtils.isPositiveNumber("sd");
        Assertions.assertFalse(trueResult);
    }

}
