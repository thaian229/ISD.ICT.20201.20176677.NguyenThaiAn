package views.screen.invoice;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

import common.exception.ProcessInvoiceException;
import controller.PaymentController;
import entity.invoice.Invoice;
import entity.order.Order;
import entity.order.OrderMedia;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Configs;
import utils.Utils;
import views.screen.BaseScreenHandler;
import views.screen.payment.PaymentScreenHandler;

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
public class RushInvoiceScreenHandler extends BaseScreenHandler {
    private static Logger LOGGER = Utils.getLogger(InvoiceScreenHandler.class.getName());

    @FXML
    private Label pageTitleRush;

    @FXML
    private Label nameRush;

    @FXML
    private Label phoneRush;

    @FXML
    private Label provinceRush;

    @FXML
    private Label addressRush;

    @FXML
    private Label deliveryTimeRush;

    @FXML
    private Label instructionsRush;

    @FXML
    private Label subtotalRush;

    @FXML
    private Label shippingFeesRush;

    @FXML
    private Label totalRush;

    @FXML
    private VBox vboxItemsRush;

    @FXML
    private VBox vboxItemsNormal;

    private Invoice invoice;

    public RushInvoiceScreenHandler(Stage stage, String screenPath, Invoice invoice) throws IOException {
        super(stage, screenPath);
        this.invoice = invoice;
        setInvoiceInfo();
    }

    private void setInvoiceInfo() {
        HashMap<String, String> deliveryInfo = invoice.getRushOrder().getDeliveryInfo();
        nameRush.setText(deliveryInfo.get("name"));
        provinceRush.setText(deliveryInfo.get("province"));
        instructionsRush.setText(deliveryInfo.get("instructions"));
        addressRush.setText(deliveryInfo.get("address"));
        subtotalRush.setText(Utils.getCurrencyFormat(invoice.getRushOrder().getAmount()));
        shippingFeesRush.setText(Utils.getCurrencyFormat(invoice.getRushOrder().getShippingFees()));
        int amount = invoice.getRushOrder().getAmount() + invoice.getRushOrder().getShippingFees();
        totalRush.setText(Utils.getCurrencyFormat(amount));
        invoice.setAmount(amount);
        invoice.getRushOrder().getlstOrderMedia().forEach(orderMedia -> {
            try {
                MediaInvoiceScreenHandler mis = new MediaInvoiceScreenHandler(Configs.INVOICE_MEDIA_SCREEN_PATH);
                mis.setOrderMedia((OrderMedia) orderMedia);
                if (((OrderMedia) orderMedia).getMedia().isRushSupported()) {
                    vboxItemsRush.getChildren().add(mis.getContent());
                } else {
                    vboxItemsNormal.getChildren().add(mis.getContent());
                }
            } catch (IOException | SQLException e) {
                System.err.println("errors: " + e.getMessage());
                throw new ProcessInvoiceException(e.getMessage());
            }

        });

    }

    @FXML
    void confirmInvoice(MouseEvent event) throws IOException {
        BaseScreenHandler paymentScreen = new PaymentScreenHandler(this.stage, Configs.PAYMENT_SCREEN_PATH, invoice);
        paymentScreen.setBController(new PaymentController());
        paymentScreen.setPreviousScreen(this);
        paymentScreen.setHomeScreenHandler(homeScreenHandler);
        paymentScreen.setScreenTitle("Payment Screen");
        paymentScreen.show();
        LOGGER.info("Confirmed invoice");
    }
}
