package utils;

import entity.order.OrderMedia;

import java.util.List;

/**
 * description
 *
 * @author Nguyen Thai An
 * <p>
 * creted at: 22/12/2020
 * <p>
 * project name: AIMS-Student
 * <p>
 * teacher's name: Dr. Nguyen Thi Thu Trang
 * <p>
 * class name: TT.CNTT ICT 02 - K62
 */
public class RushBasicShippingFormula extends ShippingFeeCalculator {
    @Override
    public int calculateShippingFee(int amount, List orderMediaList) {
        int rushFees = 5 * amount / 100;
        for (Object obj : orderMediaList) {
            OrderMedia om = (OrderMedia) obj;
            if (om.getMedia().isRushSupported())
                rushFees += 10000;
        }
        return rushFees;
    }
}
