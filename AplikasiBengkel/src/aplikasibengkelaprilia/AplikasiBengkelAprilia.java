/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aplikasibengkelaprilia;
import java.awt.GraphicsConfiguration;
import javax.swing.JFrame;
import javax.swing.JDialog;
import javax.swing.UIManager;
import javax.swing.*;
/**
 *
 * @author Aprilia
 */
public class AplikasiBengkelAprilia {
    /**
     * @param args the command line arguments
     */
    public static void showFrame(String plaf) {
        try {
            UIManager.setLookAndFeel(plaf);
        } catch(Exception e) {
            e.printStackTrace();
        }
//        JFrame f = new JFrame(plaf);
        new MainMenu().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        new MainMenu().setSize(400,100);
        new MainMenu().setLocationByPlatform(true);
        new MainMenu().setDefaultLookAndFeelDecorated(true);
        new MainMenu().setVisible(true);
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
//        System.out.println("Hello World");
//          new MainMenu().setVisible(true);
        showFrame(UIManager.getSystemLookAndFeelClassName());
//        showFrame(UIManager.getCrossPlatformLookAndFeelClassName());
//        showFrame("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel");
        Koneksi conn = new Koneksi();
        conn.openConnect();
    }
}
