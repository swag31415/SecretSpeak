package SecretSpeak2;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

public class FxController {

    @FXML
    private TextArea text_area;

    private static String text;
    private static String enc;

    @FXML
    void process_message(ActionEvent event) throws IOException {
        text = text_area.getText();
        if (text.length() == 0) return;
        App.wutils.push_to_doc(text);
        enc = AES.encrypt(text, "3931");
        App.loadScene("enc_screen.fxml");
    }

    @FXML
    private TextArea msg_box;

    @FXML
    void copy_enc(ActionEvent event) {
        ClipboardContent content = new ClipboardContent();
        content.putString(enc);
        Clipboard.getSystemClipboard().setContent(content);
    }

    @FXML
    void decrypt(ActionEvent event) {
        String msg = msg_box.getText();
        if (msg.length() == 0) {
            msg_box.setText("Enter SOMETHING!");
            return;
        }
        msg = AES.decrypt(msg, "3931");
        float sim = App.wutils.get_simmilarity(msg, text);
        if (sim > 0.5) {
            msg_box.setText("They wrote: " + msg);
        } else {
            msg_box.setText("Not simmilar enough. Better luck next time!");
        }
    }

    @FXML
    void restart(ActionEvent event) throws IOException {
        App.loadScene("enter_screen.fxml");
    }
}
