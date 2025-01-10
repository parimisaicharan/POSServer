/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ISRetail.Helpers;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.Timer;

/**
 *
 * @author eyeplus
 */
public class UnclosedProgressBar extends JDialog {

    private Thread t;
    private Timer timer;
    private final static int interval = 250;
    private int incvalue = 10;
    private JLabel message;

    public UnclosedProgressBar() {
        JLabel jl = new JLabel();
        jl.setText("Count : 0");
        setTitle("Progress Dialog");
        setModal(true);
        // increase the size if the i con
//        ImageIcon imageIcon = new ImageIcon("/ISRetail/images/small/progressBarLong.gif");
//        Image image = imageIcon.getImage();
//        Image newimg = image.getScaledInstance(1000, 1000, java.awt.Image.SCALE_SMOOTH);
//        imageIcon = new ImageIcon(newimg);
        ImageIcon icon = new ImageIcon(getClass().getResource("/ISRetail/images/small/progressBarLong.gif"));
        message = new JLabel(icon);
        message.setText("Progress...");
        add(BorderLayout.NORTH, message);
        //add();
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setSize(500, 150);
        setBackground(new Color(153, 204, 255));

        //setLocationRelativeTo(parentFrame);
        setUndecorated(true);
        // timer setting
        timer = new Timer(interval, new ActionListener() {

            public void actionPerformed(ActionEvent evt) {
                setProgress();
            }
        });
    }

    public void setProgress() {
        incvalue = incvalue + 1;
        if (t != null) {
            if (t.getState() == Thread.State.TERMINATED) {
                Toolkit.getDefaultToolkit().beep();
                timer.stop();
                incvalue = 0;
                t.stop();
                dispose();
            }
        }
    }

    public synchronized void start(Thread th, JFrame p, String title) {
        this.t = th;
        timer.start();
        message.setText(title);
        th.setDaemon(true);
        th.start();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // set position to center
        setLocation(500, 150);
        pack();
        this.show();
    }
}
