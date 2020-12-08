package views.screen.shipping;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.ResourceBundle;

import common.exception.InvalidDeliveryInfoException;
import controller.PlaceRushOrderController;
import entity.invoice.Invoice;
import entity.order.RushOrder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Configs;
import views.screen.BaseScreenHandler;
import views.screen.invoice.RushInvoiceScreenHandler;

/**
 * description
 *
 * @author Nguyen Thai An
 * <p>
 * creted at: 01/12/2020
 * <p>
 * project name: AIMS-Student
 * <p>
 * teacher's name: Dr. Nguyen Thi Thu Trang
 * <p>
 * class name: TT.CNTT ICT 02 - K62
 */
public class RushShippingScreenHandler extends BaseScreenHandler implements Initializable {
    @FXML
    private Label screenTitleRush;

    @FXML
    private TextField nameRush;

    @FXML
    private TextField phoneRush;

    @FXML
    private TextField addressRush;

    @FXML
    private TextField deliveryTimeRush;

    @FXML
    private TextField instructionsRush;

    @FXML
    private ComboBox<String> provinceRush;

    private RushOrder rushOrder;

    public RushShippingScreenHandler(Stage stage, String screenPath, RushOrder rushOrder) throws IOException {
        super(stage, screenPath);
        this.rushOrder = rushOrder;
    }

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {
        final BooleanProperty firstTime = new SimpleBooleanProperty(true); // Variable to store the focus on stage load
        nameRush.focusedProperty().addListener((observable,  oldValue,  newValue) -> {
            if(newValue && firstTime.get()){
                content.requestFocus(); // Delegate the focus to container
                firstTime.setValue(false); // Variable value changed for future references
            }
        });
        this.provinceRush.getItems().addAll(Configs.PROVINCES);
    }

    @FXML
    public void submitRushDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {

        // add info to messages
        HashMap<String, String> messages = new HashMap<>();
        messages.put("name", nameRush.getText().trim());
        messages.put("phone", phoneRush.getText().trim());
        messages.put("address", addressRush.getText().trim());
        messages.put("instructions", instructionsRush.getText().trim());
        messages.put("deliveryTime", deliveryTimeRush.getText().trim());
        messages.put("province", provinceRush.getValue());
        try {
            // process and validate delivery info
            getBController().processDeliveryInfo(messages);
        } catch (InvalidDeliveryInfoException e) {
            throw new InvalidDeliveryInfoException(e.getMessage());
        }

        // calculate shipping fees
        int shippingFees = getBController().calculateShippingFee(rushOrder.getAmount(), rushOrder.getlstOrderMedia());
        rushOrder.setShippingFees(shippingFees);
        rushOrder.setDeliveryInfo(messages);

        // create invoice screen
        Invoice invoice = getBController().createInvoice(rushOrder);
        BaseScreenHandler rushInvoiceScreenHandler = new RushInvoiceScreenHandler(this.stage, Configs.RUSH_INVOICE_SCREEN_PATH, invoice);
        rushInvoiceScreenHandler.setPreviousScreen(this);
        rushInvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
        rushInvoiceScreenHandler.setScreenTitle("Rush Invoice Screen");
        rushInvoiceScreenHandler.setBController(getBController());
        rushInvoiceScreenHandler.show();
    }

    public PlaceRushOrderController getBController(){
        return (PlaceRushOrderController) super.getBController();
    }

    public void notifyError(){
        // TODO: implement later on if we need
    }
}
