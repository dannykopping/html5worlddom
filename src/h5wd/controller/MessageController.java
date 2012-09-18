/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package h5wd.controller;

import com.paulm.jsignal.Signal;
import com.paulm.jsignal.SignalException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author dannykopping
 */
public class MessageController
{    
    private static MessageController instance;
    
    public final Signal logger = new Signal(String.class);
    
    private void MessageController()
    {
        instance = new MessageController();
        
        log("Message controller initialized");
    }
    
    public static MessageController getInstance()
    {
        if(instance == null)
            instance = new MessageController();
        
        return instance;
    }

    public void log(String message)
    {
        try
        {
            logger.dispatch(message);
        }
        catch (SignalException ex)
        {
            Logger.getLogger(MessageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
