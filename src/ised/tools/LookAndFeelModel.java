/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.tools;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class LookAndFeelModel
{


    public static void setLookAndFeelModel()
    {

        String lookAndFeel = UIManager.getSystemLookAndFeelClassName();

        try
        {
            UIManager.setLookAndFeel(lookAndFeel);
        }
        catch (UnsupportedLookAndFeelException e)
        {
            ExceptionHandler exception = new ExceptionHandler(e);
        }
        catch (Exception e)
        {
           ExceptionHandler exception = new ExceptionHandler(e);
        }

    }

}
