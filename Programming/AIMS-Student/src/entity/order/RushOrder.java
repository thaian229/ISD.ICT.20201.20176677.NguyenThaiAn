package entity.order;

import com.sun.scenario.effect.impl.state.LinearConvolveRenderState;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class RushOrder extends Order {

    private String deliveryTime;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public RushOrder() {
        super();
        this.lstOrderMedia = new ArrayList<>();
        this.deliveryTime = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(),
                LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getHour() + 1, LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond()).format(formatter);
    }

    public RushOrder(List lstOrderMedia) {
        super(lstOrderMedia);
        this.lstOrderMedia = lstOrderMedia;
    }

    public RushOrder(List lstOrderMedia, String deliveryTime) {
        super(lstOrderMedia);
        this.lstOrderMedia = lstOrderMedia;
        this.deliveryTime = deliveryTime;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    @Override
    public void addOrderMedia(OrderMedia om) {
        if (om.getMedia().isRushSupported()) {
            this.lstOrderMedia.add(om);
        }
    }

    @Override
    public void removeOrderMedia(OrderMedia om) {
        if (om.getMedia().isRushSupported()) {
            this.lstOrderMedia.remove(om);
        }
    }
}
