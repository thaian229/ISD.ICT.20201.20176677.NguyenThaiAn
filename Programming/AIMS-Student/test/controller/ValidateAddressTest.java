package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ValidateAddressTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "'so 1, hem 2', true",
            ", false",
            "'#1, toa ABC, pho Edf', true",
            "53 hem 2! ngo% 134/12, false",
    })
    public void test(String address, boolean expected) {
        // when
        boolean isValid = placeOrderController.validateAddress(address);

        // then
        assertEquals(expected, isValid);
    }
}
