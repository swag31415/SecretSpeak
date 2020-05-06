package SecretSpeak2;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class FxController {

    @FXML
    private TextArea text_area;

    @FXML
    private Button submit_button;

    @FXML
    void process_message(ActionEvent event) {
        System.out.println("It pressed submit");
    }

}
