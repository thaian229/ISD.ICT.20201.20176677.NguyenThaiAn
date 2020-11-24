package controller;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import entity.order.RushOrder;
import entity.shipping.Shipment;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Logger;

public class PlaceRushOrderController extends PlaceOrderController {

    private static Logger LOGGER = utils.Utils.getLogger(PlaceRushOrderController.class.getName());

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

    public Invoice createInvoice(Order order, RushOrder rushOrder) {
        return new Invoice(order, rushOrder);
    }

    @Override
    public void validateDeliveryInfo(HashMap<String, String> info) throws InterruptedException, IOException {
        // TODO
    }

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

    public boolean validateAddressRushSupport(String address) {
        if (address == null) return false;
        if (address.isEmpty()) return false;
        return address.toUpperCase().contains("HANOI") || address.toUpperCase().contains("HN")
                || address.toUpperCase().contains("HA NOI");
    }



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

    public int calculateShippingFee(RushOrder rushOrder) {
        int rushFees = 5 * rushOrder.getAmount() / 100;
        rushFees += 10000 * rushOrder.getlstOrderMedia().size();
        LOGGER.info("Order Amount: " + rushOrder.getAmount() + " -- Rush Shipping Fees: " + rushFees);
        rushOrder.setShippingFees(rushFees);
        return rushFees;
    }
}