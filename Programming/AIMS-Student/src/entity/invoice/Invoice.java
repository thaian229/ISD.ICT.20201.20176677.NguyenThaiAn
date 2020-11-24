package entity.invoice;

import entity.order.Order;
import entity.order.RushOrder;

public class Invoice {

    private Order order;
    private RushOrder rushOrder;
    private int amount;
    
    public Invoice(){

    }

    public Invoice(Order order){
        this.order = order;
    }

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

    public void saveInvoice(){
        
    }
}
