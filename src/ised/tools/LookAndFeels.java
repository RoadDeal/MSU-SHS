
package ised.tools;

/**
 * Date Coded: Friday, April 10, 2009
 * @author TALIB, Abdul Hamid M.
 * LookAndFeel a class created to change the GUI LookAndFeels
 */
/*
import java.awt.Image;
import javax.swing.ImageIcon;*/
import javax.swing.UIManager;
import javax.swing.SwingUtilities;

public class LookAndFeels {
    /*
     * looks[ 0 ] returns MetalLookAndFeel
     * looks[ 1 ] returns MotifLookAndFeel
     * looks[ 2 ] returns WindowsLookAndFeel
     * looks[ 3 ] returns WindowsClassicLookAndFeel
     */
    private UIManager.LookAndFeelInfo looks[]; // look and feels
    private int lookNum = 1;
    
    //WindowsLookAndFeel is the initial LookAndFeels
    public LookAndFeels( java.awt.Component invoker ) {

        try{
            // get installed look-and-feel information on this platform
            looks = UIManager.getInstalledLookAndFeels();
            //set look and feels
            UIManager.setLookAndFeel( looks[ lookNum ].getClassName() );
             // update Looks and Feels components in this application
            SwingUtilities.updateComponentTreeUI( invoker );
        }// end of try block
        catch( Exception exception ) {
            javax.swing.JOptionPane.showMessageDialog( invoker, exception );
        }//end of catch block
    }//end of method contructor LookAndFeels

    public LookAndFeels( java.awt.Component invoker, int look ) {
        
        try{
            // get installed look-and-feel information on this platform
            looks = UIManager.getInstalledLookAndFeels();
            //set look and feels
            UIManager.setLookAndFeel( looks[ look ].getClassName() );
             // update Looks and Feels components in this application
            SwingUtilities.updateComponentTreeUI( invoker );
            lookNum = look;
        }// end of try block
        catch( Exception exception ) {
            javax.swing.JOptionPane.showMessageDialog( invoker, exception );
        }//end of catch block
    }//end of method contructor LookAndFeels

    //Sets the invokers current look and feel to newLookAndFeel
    public void changeTheLookAndFeels( java.awt.Component invoker, int look ) {
        try{
            // get installed look-and-feel information on this platform
            looks = UIManager.getInstalledLookAndFeels();
            //set look and feels
            UIManager.setLookAndFeel( looks[ look ].getClassName() );
             // update Looks and Feels components in this application
            SwingUtilities.updateComponentTreeUI( invoker );
            lookNum = look;
        }// end of try block
        catch( Exception exception ) {
            javax.swing.JOptionPane.showMessageDialog( invoker, exception );
        }//end of catch block
    }//end of method changeTheLookAndFeels

    public int getLookAndFeel(){
        return this.lookNum;
    }
    /*
    public static Image getIcon() {
        Image rhisIcon;
        String iconPath = "/Utilities/IconsAndImages/ised.png";

        rhisIcon = new ImageIcon( LookAndFeels.class.getResource( iconPath ) ).getImage();

        return rhisIcon;
    }//end method
    */
}//
