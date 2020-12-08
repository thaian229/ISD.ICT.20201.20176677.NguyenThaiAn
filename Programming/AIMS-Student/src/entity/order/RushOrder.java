package entity.order;

import com.sun.scenario.effect.impl.state.LinearConvolveRenderState;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import entity.db.AIMSDB;

/**
 * Model to store information of rush order
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
public class RushOrder extends Order {

    private String deliveryTime;
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     *
     */
    public RushOrder() {
        super();
        this.lstOrderMedia = new ArrayList<>();
        this.deliveryTime = LocalDateTime.of(LocalDateTime.now().getYear(), LocalDateTime.now().getMonthValue(),
                LocalDateTime.now().getDayOfMonth(), LocalDateTime.now().getHour() + 1, LocalDateTime.now().getMinute(),
                LocalDateTime.now().getSecond()).format(formatter);
    }

    /**
     * @param lstOrderMedia list of media in order
     */
    public RushOrder(List lstOrderMedia) {
        super(lstOrderMedia);
        this.lstOrderMedia = lstOrderMedia;
    }

    /**
     * @param lstOrderMedia list of media in order
     * @param deliveryTime  wanted delivery time
     */
    public RushOrder(List lstOrderMedia, String deliveryTime) {
        super(lstOrderMedia);
        this.lstOrderMedia = lstOrderMedia;
        this.deliveryTime = deliveryTime;
    }

    @Override
    public HashMap getDeliveryInfo() {
        return deliveryInfo;
    }

    public String getDeliveryTime() {
        return deliveryTime;
    }

    public void setDeliveryTime(String deliveryTime) {
        this.deliveryTime = deliveryTime;
    }

    /**
     * add new media to order
     *
     * @param om the instance of order media
     */
    @Override
    public void addOrderMedia(OrderMedia om) {
        if (om.getMedia().isRushSupported()) {
            this.lstOrderMedia.add(om);
        }
    }

    /**
     * remove a media from a order
     *
     * @param om media to be removed
     */
    @Override
    public void removeOrderMedia(OrderMedia om) {
        if (om.getMedia().isRushSupported()) {
            this.lstOrderMedia.remove(om);
        }
    }
}
