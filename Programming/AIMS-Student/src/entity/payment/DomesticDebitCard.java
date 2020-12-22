package entity.payment;

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
public class DomesticDebitCard extends PaymentCard {
    private String issuingBank;
    private String cardNumber;
    private String validDate;
    private String cardHolder;

    public DomesticDebitCard(String issuingBank, String cardNumber, String validDate, String cardHolder) {
        this.issuingBank = issuingBank;
        this.cardNumber = cardNumber;
        this.validDate = validDate;
        this.cardHolder = cardHolder;
    }
}
