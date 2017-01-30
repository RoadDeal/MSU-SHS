/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.tools;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;
/**
 *
 * @author ABDUL
 */
public class Position {
    
    public static void setCenter( Dimension frameSize, JFrame frame ){
        Dimension screenSize;
        int horizontalPos;
        int verticalPos;

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        horizontalPos = (int) ((screenSize.getWidth() - frameSize.getWidth()) / 2);
        verticalPos = (int) ((screenSize.getHeight() - frameSize.getHeight()) / 2);

        frame.setLocation( horizontalPos, verticalPos );
    }
    public static void setCenter( Dimension frameSize, JDialog dialog ){
        Dimension screenSize;
        int horizontalPos;
        int verticalPos;

        screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        horizontalPos = ( screenSize.width - frameSize.width ) / 2;
        verticalPos = ( screenSize.height - frameSize.height ) / 2;

        dialog.setLocation( horizontalPos, verticalPos );
    }

}
