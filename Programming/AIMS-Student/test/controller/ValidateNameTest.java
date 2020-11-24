package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

public class ValidateNameTest {
    private PlaceOrderController placeOrderController;

    @BeforeEach
    void setUp() throws Exception {
        placeOrderController = new PlaceOrderController();
    }

    @ParameterizedTest
    @CsvSource({
            "Nguyen Thai An,true",
            ",false",
            "VuMinhHoang,true",
            "TuHoang99,false",
            "Big_Daddy_Vip#$,false"
    })
    public void test(String name, boolean expected) {
        // when
        boolean isValid = placeOrderController.validateName(name);

        // then
        assertEquals(expected, isValid);
    }
}
