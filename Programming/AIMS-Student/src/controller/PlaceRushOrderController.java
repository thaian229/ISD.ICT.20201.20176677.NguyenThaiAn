package controller;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.order.RushOrder;
import utils.RushBasicShippingFormula;
import utils.ShippingFeeCalculator;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

/**
 * controller to handle logic for Place Rush Order screen
 *
 * @author Nguyen Thai An
 * <p>
 * creted at: 30/11/2020
 * <p>
 * project name: AIMS-Student
 * <p>
 * teacher's name: Dr. Nguyen Thi Thu Trang
 * <p>
 * class name: TT.CNTT ICT 02 - K62
 */
public class PlaceRushOrderController extends PlaceOrderController {

    private ShippingFeeCalculator shippingFeeCalculator;
    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());

    @Override
    public void processDeliveryInfo(HashMap info) throws InterruptedException, IOException{
        LOGGER.info("Process Delivery Info");
        LOGGER.info(info.toString());

        this.validateDeliveryInfo(info);
    }

    public void setShippingFeeCalculator(ShippingFeeCalculator shippingFeeCalculator) {
        this.shippingFeeCalculator = shippingFeeCalculator;
    }

    public PlaceRushOrderController() {
        super();
        this.shippingFeeCalculator = new RushBasicShippingFormula();
    }

    /**
     * create order with both normal and rush media
     * @return The newly created Invoice
     * @throws SQLException database internal error
     */
    public Invoice createCombineOrder() throws SQLException {
        Order order = new Order();
        RushOrder rushOrder = new RushOrder();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            if (orderMedia.getMedia().isRushSupported()) {
                rushOrder.addOrderMedia(orderMedia);
            } else {
                order.addOrderMedia(orderMedia);
            }
        }
        return new Invoice(order, rushOrder);
    }

    public RushOrder createRushOrder() {
        RushOrder rushOrder = new RushOrder();
        for (Object object : Cart.getCart().getListMedia()) {
            CartMedia cartMedia = (CartMedia) object;
            OrderMedia orderMedia = new OrderMedia(cartMedia.getMedia(),
                    cartMedia.getQuantity(),
                    cartMedia.getPrice());
            rushOrder.getlstOrderMedia().add(orderMedia);
        }
        return rushOrder;
    }

    /**
     * make new invoice
     * @param order normal order
     * @param rushOrder rush order
     * @return new invoice
     */
    public Invoice createInvoice(Order order, RushOrder rushOrder) {
        return new Invoice(order, rushOrder);
    }

    /**
     * validate rush delivery info form
     * @param info the hashmap take from views
     * @throws InterruptedException Unexpected info
     * @throws IOException IO error
     */
    @Override
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {
        validateMediaRushSupport();
        validateAddressRushSupport(info.get("province"));
        validateDeliveryTime(info.get("deliveryTime"));
        validateName(info.get("name"));
        validateAddress(info.get("address"));
        validatePhoneNumber(info.get("phone"));
    }

    /**
     *
     * @return true if at least 1 media supports rush
     */
    public boolean validateMediaRushSupport() {
        try {
            if (Cart.getCart().getListRushMedia().size() == 0)
                return false;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * @param address delivery address
     * @return true if address is supported
     */
    public boolean validateAddressRushSupport(String address) {
        if (address == null) return false;
        if (address.isEmpty()) return false;
        return address.toUpperCase().contains("HANOI") || address.toUpperCase().contains("HN")
                || address.toUpperCase().contains("HA NOI");
    }

    /**
     *
     * @param deliveryTime wanted delivery time
     * @return true if valid time
     */
    public boolean validateDeliveryTime(String deliveryTime) {
        try {
            LocalDateTime time = LocalDateTime.parse(deliveryTime, RushOrder.formatter);
            if (time.compareTo(LocalDateTime.now()) <= 0) return false;
        } catch (DateTimeParseException | NullPointerException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     *
     * @param orderAmount
     * @param orderMediaList
     * @return the shipping fee
     */
    public int calculateShippingFee(int orderAmount, List orderMediaList) {
        return this.shippingFeeCalculator.calculateShippingFee(orderAmount, orderMediaList);
    }
}