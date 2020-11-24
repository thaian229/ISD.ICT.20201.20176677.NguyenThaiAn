package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ValidatePhoneNumberTest {

	private PlaceOrderController placeOrderController;

	@BeforeEach
	void setUp() throws Exception {
		placeOrderController = new PlaceOrderController();
	}

	@ParameterizedTest
	@CsvSource({
			"0123456789,true",
			"0124,false",
			"abc856,false",
			"1234567890,false"
	})
	public void test(String phone, boolean expected) {
		// when
		boolean isValid = placeOrderController.validatePhoneNumber(phone);

		// then
		assertEquals(expected, isValid);
	}

}
