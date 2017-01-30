
package ised.tools;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.JPanel;

/**
 *
 * @author Abdul Hamid
 */
public class Printer implements Printable, ActionListener {

    JPanel printedPanel;
    private Paper paper;


    public Printer(JPanel panel) {
        printedPanel = panel;
        paper = new Paper();
    }

    public void setDocumentType(String type){
        if(type.equalsIgnoreCase("Grade Card")){
            paper.setSize(610, 720);
            paper.setImageableArea(14, 14, 583,700);
        }else if(type.equalsIgnoreCase("COR")){
            paper.setSize(610, 720);
            paper.setImageableArea(14, 14, 583,700);
        }else if(type.equalsIgnoreCase("CPRF")){
            paper.setSize(610, 740);
            paper.setImageableArea(14, 14, 583,720);
        }else {
            paper.setSize(613, 794);
            paper.setImageableArea(14, 14, 583,763);
        }
    }

    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex > 0) { /* We have only one page, and 'page' is zero-based */
            return NO_SUCH_PAGE;
        }

        Graphics2D g2d = (Graphics2D)graphics;
        g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());

        /* Now print the window and its visible contents */
        printedPanel.printAll(graphics);

        /* tell the caller that this page is part of the printed document */
        return PAGE_EXISTS;
    }

    public void actionPerformed(ActionEvent e) {
        PrinterJob job = PrinterJob.getPrinterJob();
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();

        /*
        System.out.println("WIDTH : " + pf.getWidth());
        System.out.println("HEIGHT : " + pf.getHeight());
        System.out.println("ImeageableX : " + pf.getImageableX());
        System.out.println("ImgaeableY : " + pf.getImageableY());
        System.out.println("ImageableWidth : " + pf.getImageableWidth());
        System.out.println("ImageableHeiht : " + pf.getImageableHeight());
        */
        
        boolean ok = job.printDialog(aset);
        PageFormat pf = job.getPageFormat(aset);
        pf.setPaper(paper);

        job.setPrintable(this,pf);

        if (ok) {
            try {
                job.print();
            } catch (PrinterException ex) {
                System.out.println(ex);
            }
        }
    } // end method
} // emd class
