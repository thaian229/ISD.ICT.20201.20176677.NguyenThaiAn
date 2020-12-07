package entity.invoice;

import entity.order.Order;
import entity.order.RushOrder;

/**
 * Model invoice to store information
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
public class Invoice {

    private Order order;
    private RushOrder rushOrder;
    private int amount;

    /**
     *
     */
    public Invoice(){

    }

    /**
     *
     * @param order non rush order
     */
    public Invoice(Order order){
        this.order = order;
    }

    public Invoice(RushOrder rushOrder){
        this.rushOrder = rushOrder;
    }

    /**
     * constructor for combine invoice
     * @param order normal order
     * @param rushOrder rush order
     */
    public Invoice(Order order, RushOrder rushOrder) {
        this.order = order;
        this.rushOrder = rushOrder;
    }

    public Order getOrder() {
        return order;
    }

    public RushOrder getRushOrder() {
        return rushOrder;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getAmount() {
        return amount;
    }

    public void saveInvoice() {
        
    }
}
