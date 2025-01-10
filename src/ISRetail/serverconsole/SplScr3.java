package ISRetail.serverconsole;

import ISRetail.Webservices.BackgroundPropertiesFromFile;
import ISRetail.Webservices.MsdeConnectionDetailsPojo;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.plantdetails.SiteMasterPOJO;
import static ISRetail.serverconsole.ServerConsole.siteID;
import ISRetail.utility.db.GetSiteDescription;
import ISRetail.utility.validations.Validations;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import java.awt.Toolkit;
import java.awt.event.KeyListener;
import java.sql.Connection;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class SplScr3 extends JWindow implements Runnable, KeyListener {

    // ImageIcon splashImage = new ImageIcon(getClass().getResource("/ISRetail/images/title4.JPG"));
    //ImageIcon splashImage = new ImageIcon(getClass().getResource("/ISRetail/images/title.jpg"));
    ImageIcon splashImage = null;
    JLabel jlblImage = new JLabel();
    // Color pColor = new Color(155, 100, 100);
    JProgressBar jProgressBar1 = new JProgressBar();
    int width, height;
    static boolean showStartupScr = true;
    MyBar myBar;
    public static String sitemarquee ="";
    public static String siteId ="";

    public static void showStartUpScreen() {
        new SplScr3().setVisible(true);

    }

    public static void endStartUpScreen() {
        SplScr3.showStartupScr = false;

    }

    class MyBar extends JPanel {

        int value;
        int uwidth;
        int width1, height1;
        Color pColor = new Color(155, 100, 100);

        MyBar() {
            super();
            width1 = 80;
            height1 = 20;
            uwidth = width1 / 100;
            this.setSize(width1, height1);

        }

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            //g.setColor(new Color(238, 238, 238));
            g.setColor(new Color(0, 0, 0));
            g.fillRoundRect(0, 0, width1, height1, 2, 2);
            g.setColor(Color.BLACK);
            g.drawRoundRect(0, 0, width1 - 1, height1 - 1, 2, 2);
            //this.pColor=new Color(155+ value ,110-value,110-value);  
            int r, gr, b;
            r = (153 + value) > 255 ? 255 : (153 + value);
            gr = (184 - value) < 0 ? 0 : (184 - value);
            b = (224 - value) < 0 ? 0 : (224 - value);
            this.pColor = new Color(r, gr, b);

            g.setColor(this.pColor);
            for (int i = 0; i < value; i++) {
                g.fillRoundRect(width1 / 2 - (i * width1) / 200, 1, i * width1 / 100, height1 - 2, 1, 1);

            }

        }

        public void paint(Graphics g) {

            super.paint(g);
            g.setColor(new Color(238, 238, 238));
            g.fillRoundRect(0, 0, width1, height1, 2, 2);
            g.setColor(Color.BLACK);
            g.drawRoundRect(0, 0, width1 - 1, height1 - 1, 2, 2);
            //this.pColor=new Color(155+ value ,150-value/2,150-value/2);    
            //this.pColor=new Color(155+ value ,124-value,205-value); 
            int r, gr, b;
            r = (153 + value) > 255 ? 255 : (153 + value);
            gr = (184 - value) < 0 ? 0 : (184 - value);
            b = (224 - value) < 0 ? 0 : (224 - value);
            this.pColor = new Color(r, gr, b);
            g.setColor(this.pColor);
            for (int i = 0; i < value; i++) {
                g.fillRoundRect(width1 / 2 - (i * width1) / 200, 1, i * width1 / 100, height1 - 2, 1, 1);

            }

        }

        public void setValue(int value) {
            this.value = value;
            repaint();

        }
    }

    synchronized public void run() {
        int i = 0;
        //  boolean b = true;
        System.out.println("" + jProgressBar1.getForeground().getRed() + " " + jProgressBar1.getForeground().getGreen() + " " + jProgressBar1.getForeground().getBlue());
        int red = jProgressBar1.getBackground().getRed();
        int gre = jProgressBar1.getBackground().getGreen();
        int blu = jProgressBar1.getBackground().getBlue();

        int r1, g1, b1;
        Color origBkColor = jProgressBar1.getBackground();

        int k = 1;
        while (showStartupScr) {
            try {

                //  setStatus("", i);
                myBar.setValue(i);
                /*  if (b) {
                    pColor = new Color(153 + i, 124 - i, 205 - i);
                } else //  pColor=new Color(255-i,60+i,150+i);
                {
                    pColor = origBkColor;
                }
                 */
                // jProgressBar1.setForeground(pColor);

                repaint();
                // System.out.println(""+i);
                i += k;
                Thread.sleep(7);

                if (i > 100) {
                    Thread.sleep(50);
                    //  i = 1;
                    k = -k;
                    /*  b = !b;
                    if (!b) //   jProgressBar1.setBackground(new Color(235,84,104));
                    {
                        jProgressBar1.setBackground(jProgressBar1.getForeground());
                    } else {
                        jProgressBar1.setBackground(origBkColor);
                    }
                     */
                    // showStartupScr = false;
                } else if (i < 0) {

                    k = -k;

                }
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }

        }

        setVisible(false);
    }

    public void doStart() {

        Thread t = new Thread(this);
        t.start();

    }

    public SplScr3() {
        super();
        try {
            width = 500;  // image is 500 wide
            height = 230; // image is 330 tall. Add a bit for the progress bar

            jbInit();

            // Center the screen
            Toolkit tk = this.getToolkit();

            int x = (tk.getScreenSize().width - width) / 2;
            int y = (tk.getScreenSize().height - height) / 2;
            this.setLocation(x, y);
            this.setSize(width, height);
            setVisible(true);
            jlblImage.requestFocus();
            doStart();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void jbInit() throws Exception {
        //jlblImage.setText("jLabel1");
        String siteCode = null;
        MsdeConnection msdeConnection;
        Connection con = null;
        SiteMasterDO siteDo = null;
        myBar = new MyBar();
        myBar.setSize(80, 20);
        myBar.setPreferredSize(new Dimension(80, 20));
        myBar.setMinimumSize(new Dimension(80, 20));
        GetSiteDescription runwaydesc = new GetSiteDescription();
        try {
            BackgroundPropertiesFromFile bf = new BackgroundPropertiesFromFile();
            MsdeConnectionDetailsPojo msdeConnectionDetailsPojo;
            msdeConnectionDetailsPojo = bf.getMsdeConnectionDetails();
            String connectstring1 = "";
            if (msdeConnectionDetailsPojo.getDbname().equalsIgnoreCase("null")) {
                connectstring1 = "jdbc:sqlserver://" + msdeConnectionDetailsPojo.getHostname() + ":" + msdeConnectionDetailsPojo.getPortno();
            } else {
                connectstring1 = "jdbc:sqlserver://" + msdeConnectionDetailsPojo.getHostname() + ":" + msdeConnectionDetailsPojo.getPortno() + ";databaseName=" + msdeConnectionDetailsPojo.getDbname();
            }

            MsdeConnection.setConnectstring(connectstring1);
            MsdeConnection.setUsername(msdeConnectionDetailsPojo.getUsername());
            MsdeConnection.setPassword(msdeConnectionDetailsPojo.getPassword());
            msdeConnection = new MsdeConnection();
            msdeConnection.checkConnection();
            con = msdeConnection.createConnection();
            siteDo = new SiteMasterDO();
            siteCode = siteDo.getSiteId(con);
            SiteMasterPOJO runwaypojo = new SiteMasterPOJO();
            runwaypojo = siteDo.getSite(con);
            sitemarquee = runwaypojo.getMarquee();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                con.close();
            }
        }
        // JLabel lb = new JLabel("");
        //  lb.setSize(100,25);
        // myBar.add(lb);
        this.setBackground(new Color(92, 130, 192));
        // this.setAlwaysOnTop(true);
        // this.setFocusable(true);
//        if (Validations.isFieldNotEmpty(siteCode) && siteCode.startsWith("IF")) {
//            jlblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/title_fastrack.jpg"))); // NOI18N
//        } else if (Validations.isFieldNotEmpty(siteCode) && !siteCode.startsWith("IF")) {
//            jlblImage.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/title.jpg"))); // NOI18N
//        }

        if (Validations.isFieldNotEmpty(sitemarquee) && sitemarquee.contains("RUNWAY")) {//Added by charan for Runway
            System.err.println("sitemarquee in splcr:"+sitemarquee);
            splashImage = new ImageIcon(getClass().getResource("/ISRetail/images/Runway-title.jpg"));
        } else {
            if (Validations.isFieldNotEmpty(siteId) && !siteId.startsWith("IF")) {
                splashImage = new ImageIcon(getClass().getResource("/ISRetail/images/title.jpg"));
            } else if (Validations.isFieldNotEmpty(siteId) && siteId.startsWith("IF")) {
                splashImage = new ImageIcon(getClass().getResource("/ISRetail/images/title_fastrack.jpg"));
            }
            
        }
        jlblImage.setIcon(splashImage);
        jlblImage.setHorizontalAlignment(SwingConstants.CENTER);
        jlblImage.setVerticalAlignment(SwingConstants.CENTER);
        JLabel clockImage = new JLabel();
        clockImage.setIcon(new ImageIcon(getClass().getResource("/ISRetail/images/pleasewait2.gif")));
        jProgressBar1.setStringPainted(true);
        jProgressBar1.setSize(width - 10, jProgressBar1.getHeight());
        JPanel jp = new JPanel();
        //jp.setBackground(new Color(92,130,192));
        jp.setBackground(new Color(142, 180, 242));
        // jp.setLayout(new BorderLayout());
        JPanel jp2 = new JPanel();
        jp2.setBackground(new Color(142, 180, 242));
        jp2.setLayout(new FlowLayout());
        ((FlowLayout) jp2.getLayout()).setHgap(0);

        // jp.setSize(width, 20);
        jp2.setSize(width, 30);
        jp2.setPreferredSize(new Dimension(width, 30));
        jp2.setMinimumSize(new Dimension(width, 30));

        jp2.add(new JLabel(new ImageIcon(getClass().getResource("/ISRetail/images/progressBarLong.gif"))));
        jp2.add(new JLabel(new ImageIcon(getClass().getResource("/ISRetail/images/progressBarLong.gif"))));
        // jp2.add(new JLabel(new ImageIcon(getClass().getResource("/ISRetail/images/progressBarLong.gif"))), BorderLayout.NORTH);
        // jp2.add(jProgressBar1);
        jp2.add(myBar);
        jp2.add(new JLabel(new ImageIcon(getClass().getResource("/ISRetail/images/progressBarLong.gif"))));
        jp2.add(new JLabel(new ImageIcon(getClass().getResource("/ISRetail/images/progressBarLong.gif"))));
        // jp2.add(new JLabel(new ImageIcon(getClass().getResource("/ISRetail/images/progressBarLong.gif"))), BorderLayout.SOUTH);
        jp.add(clockImage, BorderLayout.CENTER);
        this.getContentPane().add(jp, BorderLayout.NORTH);
        this.getContentPane().add(jlblImage, BorderLayout.CENTER);
        //this.getContentPane().add(jProgressBar1, BorderLayout.SOUTH);
        this.getContentPane().add(jp2, BorderLayout.SOUTH);

        jlblImage.setEnabled(true);
        jlblImage.setFocusable(true);

        jlblImage.setVisible(true);
        jlblImage.addKeyListener(this);
    }

    public void setStatus(String msg, int value) {
        jProgressBar1.setString(msg);
        jProgressBar1.setValue(value);
    }

    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new SplScr3();
            }
        });
    }

    public void keyTyped(KeyEvent e) {
        System.out.println("e.getKeyCode()1" + e.getKeyCode());
        showStartupScr = false;
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            setVisible(false);
        }
    }

    public void keyPressed(KeyEvent e) {
        System.out.println("e.getKeyCode()2" + e.getKeyCode());
        showStartupScr = false;
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            setVisible(false);
        }
    }

    public void keyReleased(KeyEvent e) {

        System.out.println("e.getKeyCode()3" + e.getKeyCode());
        showStartupScr = false;
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            setVisible(false);
        }

    }
}
