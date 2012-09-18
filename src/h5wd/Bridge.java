/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package h5wd;

import com.paulm.jsignal.SignalException;
import h5wd.controller.MessageController;
import java.util.logging.Level;
import java.util.logging.Logger;
import netscape.javascript.JSObject;

/**
 *
 * @author dannykopping
 */
class Bridge
{

    public Bridge()
    {
        MessageController.getInstance().log("Bridge initialized");
    }
    
    public void callFromJS(JSObject data)
    {
        MessageController.getInstance().log(data.getMember("message").toString() + " [" + data.getMember("id") + "]");
    }
}