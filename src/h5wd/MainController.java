/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package h5wd;

import com.paulm.jsignal.SignalException;
import com.sun.webpane.webkit.JSObject;
import h5wd.controller.MessageController;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.concurrent.Worker.State;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TitledPane;
import javafx.scene.text.FontSmoothingType;
import javafx.scene.web.WebEvent;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author dannykopping
 */
public class MainController implements Initializable
{

    @FXML
    private WebView webkit;
    @FXML
    private TextArea statusBar;
    @FXML
    private Button invokeJS;
    @FXML
    private TitledPane browserPanel;
    
    private JSObject bridge;

    @Override
    public void initialize(URL url, ResourceBundle rb)
    {
        try
        {
            MessageController.getInstance().logger.add(this, "handleLog");
            MessageController.getInstance().log("Application initialized");

            loadDefaultPage();
        }
        catch (SignalException ex)
        {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void handleLog(String message)
    {
        statusBar.setText(message);
    }

    private void loadDefaultPage()
    {
        webkit.getEngine().setJavaScriptEnabled(true);
        webkit.setFontSmoothingType(FontSmoothingType.LCD);
        webkit.getEngine().load("http://html5demos/contenteditable");

        webkit.getEngine().getLoadWorker().stateProperty().addListener(
                new ChangeListener<State>()
                {
                    public void changed(ObservableValue ov, State oldState, State newState)
                    {
                        if (newState == State.SUCCEEDED)
                        {
                            bridge = (JSObject) webkit.getEngine().executeScript("window");
                            bridge.setMember("java", new Bridge());

                            browserPanel.setText(webkit.getEngine().getTitle());
                            MessageController.getInstance().log("Initialized bridge [" + webkit.getEngine().getTitle() + "]");
                            enableButton();
                        }
                    }
                });
    }
    
    public void enableButton()
    {
        invokeJS.setDisable(false);
    }
    
    public void callJavascript(ActionEvent event)
    {
        String o = webkit.getEngine().executeScript("callFromJava()").toString();
        String[] classes = o.split(",");
        System.out.println(o);
    }
}
