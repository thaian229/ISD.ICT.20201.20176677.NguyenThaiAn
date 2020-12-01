package views.screen.shipping;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import common.exception.InvalidDeliveryInfoException;
import common.exception.MediaNotAvailableException;
import common.exception.PlaceOrderException;
import controller.PlaceOrderController;
import controller.PlaceRushOrderController;
import controller.ViewCartController;
import entity.cart.CartMedia;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.RushOrder;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.invoice.InvoiceScreenHandler;
import views.screen.popup.PopupScreen;
import views.screen.shipping.ShippingScreenHandler;

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
    void submitDeliveryInfo(MouseEvent event) throws IOException, InterruptedException, SQLException {

        // add info to messages
        HashMap messages = new HashMap<>();
        messages.put("name", nameRush.getText());
        messages.put("phone", phoneRush.getText());
        messages.put("address", addressRush.getText());
        messages.put("instructions", instructionsRush.getText());
        messages.put("province", provinceRush.getValue());
        try {
            // process and validate delivery info
            getBController().processDeliveryInfo(messages);
        } catch (InvalidDeliveryInfoException e) {
            throw new InvalidDeliveryInfoException(e.getMessage());
        }

        // calculate shipping fees
        int shippingFees = getBController().calculateShippingFee(rushOrder);
        rushOrder.setShippingFees(shippingFees);
        rushOrder.setDeliveryInfo(messages);

        // create invoice screen
        Invoice invoice = getBController().createInvoice(rushOrder);
        BaseScreenHandler InvoiceScreenHandler = new InvoiceScreenHandler(this.stage, Configs.INVOICE_SCREEN_PATH, invoice);
        InvoiceScreenHandler.setPreviousScreen(this);
        InvoiceScreenHandler.setHomeScreenHandler(homeScreenHandler);
        InvoiceScreenHandler.setScreenTitle("Invoice Screen");
        InvoiceScreenHandler.setBController(getBController());
        InvoiceScreenHandler.show();
    }

    public PlaceOrderController getBController(){
        return (PlaceOrderController) super.getBController();
    }

    public void notifyError(){
        // TODO: implement later on if we need
    }
}
