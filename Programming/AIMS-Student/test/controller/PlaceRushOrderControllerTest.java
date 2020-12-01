package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.order.RushOrder;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;

public class PlaceRushOrderControllerTest {
    private PlaceRushOrderController placeRushOrderController;
    private Media media;
    private RushOrder rushOrder;

    @BeforeEach
    void setUp() throws SQLException {
        placeRushOrderController = new PlaceRushOrderController();
        media = new Media();
        Cart.getCart().emptyCart();
        rushOrder = new RushOrder();
    }

    @Test
    public void validateMediaRushSupportTest1() throws SQLException {
        assertEquals(false, placeRushOrderController.validateMediaRushSupport());
    }

    @Test
    public void validateMediaRushSupportTest2() throws SQLException {
        Cart.getCart().emptyCart();
        CartMedia cartMedia = new CartMedia(media.getMediaById(39), Cart.getCart(), 1, 10);
        Cart.getCart().addCartMedia(cartMedia);

        assertEquals(false, placeRushOrderController.validateMediaRushSupport());
    }

    @Test
    public void validateMediaRushSupportTest3() throws SQLException {
        Cart.getCart().emptyCart();
        CartMedia cartMedia = new CartMedia(media.getMediaById(38), Cart.getCart(), 1, 10);
        cartMedia.getMedia().setRushSupported(true);
        Cart.getCart().addCartMedia(cartMedia);

        assertEquals(true, placeRushOrderController.validateMediaRushSupport());
    }

    @ParameterizedTest
    @CsvSource({
            "'Ha dong, Ha noi', true",
            "'so 24, hem 2, VTP, HN', true",
            "'so 15, dai van dong, HCM city', false",
            ", false",
            " ,false",
            "'wlrgj 4t9824 dgweth', false"
    })
    public void validateAddressRushSupportTest(String address, boolean expected) {
        assertEquals(expected, placeRushOrderController.validateAddressRushSupport(address));
    }

    @ParameterizedTest
    @CsvSource({
            "2025-11-25 22:09, true",
            ", false",
            "11223-525-42 55-22, false",
            "lertgo egije 6g5er 13$5, false",
            "2011-11-02 11:44, false"
    })
    public void validateDeliveryTimeTest(String deliveryTime, boolean expected) {
        assertEquals(expected, placeRushOrderController.validateDeliveryTime(deliveryTime));
    }

    @Test
    public void calculateShippingFeeTest1() throws SQLException {
        OrderMedia om1 = new OrderMedia(media.getMediaById(38), 1, 50000);
        OrderMedia om2 = new OrderMedia(media.getMediaById(39), 3, 25000);

        rushOrder.addOrderMedia(om1);
        rushOrder.addOrderMedia(om2);

        assertEquals(5 * (50000 * 110) / 100 / 100 + 10000, placeRushOrderController.calculateShippingFee(rushOrder));
    }

    @Test
    public void calculateShippingFeeTest2() throws SQLException {
        OrderMedia om1 = new OrderMedia(media.getMediaById(38), 1, 50000);
        OrderMedia om2 = new OrderMedia(media.getMediaById(39), 3, 25000);
        OrderMedia om3 = new OrderMedia(media.getMediaById(40), 2, 30000);

        rushOrder.addOrderMedia(om1);
        rushOrder.addOrderMedia(om2);
        rushOrder.addOrderMedia(om3);

        assertEquals(5 * ( (50000 + 2 * 30000) * 110) / 100 / 100 + 20000, placeRushOrderController.calculateShippingFee(rushOrder));
    }

    @Test
    public void calculateShippingFeeTest3() throws SQLException {
        assertEquals(0, placeRushOrderController.calculateShippingFee(rushOrder));
    }

}
