package utils;

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
public abstract class ShippingFeeCalculator {
    public abstract int calculateShippingFee(int amount, List orderMediaList);
}
