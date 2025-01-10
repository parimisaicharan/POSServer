/*
 * ServerConsole.java
 *
 * Created on December 4, 2008, 12:11 PM
 */
package ISRetail.serverconsole;

import ISRetail.poscontrolreport.POSInstallationReport;
import ISRetail.Helpers.ConvertDate;
import ISRetail.Helpers.NetConnection;
import ISRetail.Helpers.UnclosedProgressBar;
import ISRetail.Logging.DatabaseLogger;
import ISRetail.SalesOrderBilling.SalesOrderBillingDO;
import ISRetail.ServerRmi.StartRMIServer;
import ISRetail.Webservices.*;
import ISRetail.article.ArticleMaster;
import ISRetail.clientconnection.clientConnectionAccepterThread;
import ISRetail.configurations.Configuration;
import ISRetail.msdeconnection.MsdeConnection;
import ISRetail.plantdetails.SiteMasterDO;
import ISRetail.plantdetails.SystemDate;
import ISRetail.utility.db.GetSiteDescription;
import ISRetail.utility.db.PopulateData;
import ISRetail.utility.validations.Validations;
import java.io.IOException;
import java.net.ServerSocket;
import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import java.awt.*;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import posstaging.HighFrequencyResponseWebService;
import posstaging.PosStagingDO;

/**
 *
 * @author Dileep
 */
public class ServerConsole extends javax.swing.JFrame {

    public static String siteID = "";
    public static String businessDate = "";
    public static String currentversion = "103";
    private ServerSocket server;
    private static int port;
    public static StagingThread stagingthread;
    public static Thread communThread;
    private boolean isCloserequired = true;
    public static String contactlensdualpricingenabled = "";

    /**
     * /**
     * Creates new form ServerConsole
     */
    // soumyo
    static boolean idealResolution;
    static boolean idealWidth, idealHeight;
    boolean xslide = false;
    boolean yslide = false;
    static int sw, sh;
    StagingTableStatus stagingTableStatus;

    static {

        idealResolution = isSetResolution(1152, 864);
        //idealResolution = isSetResolution(1920, 1200);
        idealWidth = isidealWidth(1152);
        idealHeight = isidealHeight(864);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        sw = screenSize.width;
        sh = screenSize.height;

    }

    public static boolean isSetResolution(int width, int height) {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        return (width == dm.getWidth() && height == dm.getHeight());

    }

    public static boolean isidealWidth(int width) {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        return (width == dm.getWidth());

    }

    public static boolean isidealHeight(int height) {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        return (height == dm.getHeight());

    }

    public static boolean isGreaterOrEqualTo(int width, int height) {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode dm = gd.getDisplayMode();
        return (width >= dm.getWidth() && height >= dm.getHeight());

    }

    public static boolean isAvailableResolution(int width, int height) {

        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        DisplayMode dm[] = gd.getDisplayModes();

        for (int i = 0; i < dm.length; i++) {
            if (width == dm[i].getWidth() && height == dm[i].getHeight()) {
                return true;
            }
        }

        return false;

    }

    public void setResolution(int width, int height) {

        try {

            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            GraphicsDevice gd = ge.getDefaultScreenDevice();
            /* GraphicsDevice[] gs = ge.getScreenDevices();
            for(int j=0;j< gs.length;j++){
            if(gs[j].isDisplayChangeSupported ()) 
            {
            JOptionPane.showMessageDialog(this, j+" Supports");   
            }
            }
             */
            DisplayMode dm[] = gd.getDisplayModes();

            if (gd.isDisplayChangeSupported()) {
                for (int i = 0; i < dm.length; i++) {
                    if (width == dm[i].getWidth() && height == dm[i].getHeight()) {
                        // gd.setFullScreenWindow(this);
                        gd.setDisplayMode(dm[i]);
                        return;
                    }
                }
            }
            // else
            // JOptionPane.showMessageDialog(this, "Operating system does not support changing the display mode");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*public Rectangle getMaximizedBounds()
    {
    return(maxBounds);
    }

    public synchronized void setMaximizedBounds(Rectangle maxBounds)
    {
    this.maxBounds = maxBounds;
    super.setMaximizedBounds(maxBounds);
    }
     */

 /* public synchronized void setExtendedState(int state)
    {
    if (maxBounds == null &&
    (state & Frame.MAXIMIZED_BOTH) == Frame.MAXIMIZED_BOTH)
    {
    Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration());
    Rectangle screenSize = getGraphicsConfiguration().getBounds();
    Rectangle maxBounds = new Rectangle(screenInsets.left + screenSize.x,
    screenInsets.top + screenSize.y,
    screenSize.x + screenSize.width - screenInsets.right - screenInsets.left,
    screenSize.y + screenSize.height - screenInsets.bottom - screenInsets.top);
    super.setMaximizedBounds(maxBounds);
    }

    super.setExtendedState(state);
    }
     */
 /*  public synchronized void setExtendedState(int state) {
    if (maxBounds == null &&
    (state & java.awt.Frame.MAXIMIZED_BOTH) == java.awt.Frame.MAXIMIZED_BOTH) {
    Insets screenInsets = getToolkit().getScreenInsets(getGraphicsConfiguration());
    Rectangle screenSize = getGraphicsConfiguration().getBounds();
    // don't take the screenSize.x and screenSize.y into account
    // otherwise you get incorrect behaviour on a dual monitor
    Rectangle maxBounds = new Rectangle(screenInsets.left,  screenInsets.top,
    screenSize.width - screenInsets.right - screenInsets.left,
    screenSize.height - screenInsets.bottom - screenInsets.top);
    super.setMaximizedBounds(maxBounds);
    }
    super.setExtendedState(state);
    }
     */
    void setScrollPane() {

        // maxBounds = null;
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 1152) / 2, (screenSize.height - 855) / 2, 1152, 855);
        setLocation(0, 0);
        setUndecorated(false);

        //setResizable(false);
        if (screenSize.width <= 1152) {

            setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        } else {

            setResizable(false);

        }


        /*  java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 1154) / 2, (screenSize.height - 855) / 2, 1154, 855);
        setResizable(false);
        
        // GraphicsEnvironment env =  GraphicsEnvironment.getLocalGraphicsEnvironment();
        // setMaximizedBounds(env.getMaximumWindowBounds());
        setLocation(0,0);
        setUndecorated(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);


        /*
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        //setBounds((screenSize.width - 1154) / 2, (screenSize.height - 855) / 2, 1154, 855);
        setSize(1154, 855);
        setResizable(false);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setLocation(0,0);
        if(screenSize.width>1152)        
        setLocation((screenSize.width - 1154) / 2, (screenSize.height - 855) / 2);
        setUndecorated(false);

        //GraphicsEnvironment env =  GraphicsEnvironment.getLocalGraphicsEnvironment();
        //setMaximizedBounds(env.getMaximumWindowBounds());



         */
        if (!idealResolution && isAvailableResolution(1152, 864)) {
            JOptionPane.showMessageDialog(this, "Please Set Screen to Available Resolution [ 1152,864 ]  for Scroll Free Viewing ");
        }

        if (idealResolution) {
            jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
            jScrollPane1.setEnabled(false);

        }
        if (!idealWidth) {

            jScrollPane1.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
            jScrollPane1.setEnabled(true);
        }
        if (!idealHeight) {

            jScrollPane1.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            jScrollPane1.setEnabled(true);
        }

    }
    //soumyo

    public static void stopServerSocket() {
        if (communThread != null) {
            communThread.stop();
        }
    }

    public ServerConsole() {

        SiteMasterDO siteMasterDO;
        Connection con;
        MsdeConnection msdeconn;

        try {
            msdeconn = new MsdeConnection();
            con = msdeconn.createConnection();
            siteMasterDO = new SiteMasterDO();
            siteID = siteMasterDO.getSiteId(con);
            PopulateData contactlensdata = new PopulateData();//added by charan for contact lens dual pricing
            contactlensdualpricingenabled = contactlensdata.getConfigValue(con, "contactLens_DualPricing_Enabled");
            if (contactlensdualpricingenabled == null) {
                contactlensdualpricingenabled = "N";
            }
            initComponents();
            this.setMaximumSize(new Dimension(1162, 864));
            this.setExtendedState(Frame.MAXIMIZED_BOTH);
            setScrollPane();
//            siteMasterDO = new SiteMasterDO();
//            siteID = siteMasterDO.getSiteId(con);
            storeCode.setText(siteID);
            stagingthread = new StagingThread(this);
            //  setBusinessDate(con,null);
            con.close();

            // added by ravi thota on 11.01.2013 to initialize database logger
            DatabaseLogger.setDatabaseLogger();

            //MainTabbedPane.addTab("Server Monitor", new StagingThread());
            MainTabbedPane.addTab("Server Monitor", stagingthread);
            MainTabbedPane.addTab("Master Data : Download ", new ArticleMaster(this));
            MainTabbedPane.addTab("Configurations : Download ", new Configuration(this));
            stagingTableStatus = new StagingTableStatus(this);
            MainTabbedPane.addTab("Transaction Staging Status", stagingTableStatus);

            //MainTabbedPane.addTab("Version Update", new VersionUpdate());
            //MainTabbedPane.addTab("Locks", new PosLocks());
            MainTabbedPane.addTab("POS Control Report", new POSInstallationReport(this));
            storeBanner1.start();
            //SplScr3.endStartUpScreen();
            try {
                BackgroundPropertiesFromFile bf = new BackgroundPropertiesFromFile();
                port = bf.getPosServerPort();
                server = new ServerSocket(port);
                handleConnection();
                bf.getProxySettings();
            } catch (Exception e) {
                e.printStackTrace();
                // SplScr3.endStartUpScreen();
            }

            //added by ravi thota on 31.05.2013 to restrict closing pos server before pos client
            try {
                StartRMIServer srmi = new StartRMIServer();
                srmi.start();
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No Database Connection Available !", "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            siteMasterDO = null;
            // SplScr3.endStartUpScreen();
        }

        // SplScr3.endStartUpScreen();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        storeBanner1 = new ISRetail.components.StoreBanner();
        interPanel = new javax.swing.JPanel();
        storecodeLabel = new javax.swing.JLabel();
        busDateLabel = new javax.swing.JLabel();
        storeCode = new javax.swing.JLabel();
        busDate = new javax.swing.JLabel();
        bdatestatus = new javax.swing.JLabel();
        bdatestatus11 = new javax.swing.JLabel();
        bdatestatus1 = new javax.swing.JLabel();
        MainTabbedPane = new javax.swing.JTabbedPane();

        GetSiteDescription runwaydesc = new GetSiteDescription();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        if (Validations.isFieldNotEmpty(runwaydesc.GetSiteDescriptionforRunway()) && runwaydesc.GetSiteDescriptionforRunway().contains("RUNWAY")) {
            setTitle("Runway    Server Console");
        } else {
            if (Validations.isFieldNotEmpty(siteID) && !siteID.startsWith("IF")) {
                setTitle("TITAN Eye+    Server Console");
            } else if (Validations.isFieldNotEmpty(siteID) && siteID.startsWith("IF")) {
                setTitle("TITAN Fastrack Eyewear    Server Console");
            }
        }
        // setTitle("TITAN Eye+    Server Console");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setExtendedState(6);
        addWindowListener(new java.awt.event.WindowAdapter() {

            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }

            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel5.setBackground(new java.awt.Color(235, 235, 245));
        jPanel5.setAutoscrolls(true);
        jPanel5.setMaximumSize(new java.awt.Dimension(1152, 864));
        jPanel5.setMinimumSize(new java.awt.Dimension(23, 23));
        jPanel5.setName("jPanel5"); // NOI18N

        jScrollPane1.setBackground(new java.awt.Color(143, 194, 245));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1152, 864));
        jScrollPane1.setName("jScroll"); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1152, 864));
        jScrollPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseMoved(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(235, 235, 245));
        jPanel1.setAutoscrolls(true);
        jPanel1.setMaximumSize(new java.awt.Dimension(1152, 864));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1152, 864));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });

        // jPanel2.setBackground(new java.awt.Color(255, 102, 51));
        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(255, 153, 51));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36));
        //jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/title.jpg"))); // NOI18N

        System.err.println("Site marque:" + runwaydesc.GetSiteDescriptionforRunway());
        if (Validations.isFieldNotEmpty(runwaydesc.GetSiteDescriptionforRunway()) && runwaydesc.GetSiteDescriptionforRunway().contains("RUNWAY")) {
            jLabel1.setHorizontalAlignment(SwingConstants.CENTER);
            jLabel1.setVerticalAlignment(SwingConstants.CENTER);
            jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/Runway-title.jpg"))); // NOI18N 
        } else {
            if (Validations.isFieldNotEmpty(siteID) && !siteID.startsWith("IF")) {
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/title.jpg"))); // NOI18N
            } else if (Validations.isFieldNotEmpty(siteID) && siteID.startsWith("IF")) {
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/title_fastrack.jpg"))); // NOI18N
            }
        }
        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup().addContainerGap(294, Short.MAX_VALUE).add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 630, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED).add(storeBanner1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap()));
        jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jPanel2Layout.createSequentialGroup().add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(storeBanner1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 74, Short.MAX_VALUE)).addContainerGap()));

        storecodeLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        storecodeLabel.setForeground(new java.awt.Color(88, 109, 148));
        storecodeLabel.setText("Store Code :");

        busDateLabel.setFont(new java.awt.Font("Verdana", 1, 12));
        busDateLabel.setForeground(new java.awt.Color(88, 109, 148));
        busDateLabel.setText("Posting Date :");

        storeCode.setFont(new java.awt.Font("Verdana", 1, 12));
        storeCode.setForeground(new java.awt.Color(88, 109, 148));
        storeCode.setText("STORE");

        busDate.setFont(new java.awt.Font("Verdana", 1, 12));
        busDate.setForeground(new java.awt.Color(88, 109, 148));
        busDate.setText("DD/MM/YYYY");

        bdatestatus.setFont(new java.awt.Font("Verdana", 1, 12));
        bdatestatus.setForeground(new java.awt.Color(255, 0, 51));
        bdatestatus.setMaximumSize(new java.awt.Dimension(42, 10));
        bdatestatus.setMinimumSize(new java.awt.Dimension(42, 10));

        bdatestatus11.setFont(new java.awt.Font("Verdana", 1, 12));
        bdatestatus11.setMaximumSize(new java.awt.Dimension(42, 10));
        bdatestatus11.setMinimumSize(new java.awt.Dimension(42, 10));

        bdatestatus1.setFont(new java.awt.Font("Verdana", 1, 12));
        bdatestatus1.setForeground(new java.awt.Color(255, 0, 51));
        bdatestatus1.setMaximumSize(new java.awt.Dimension(42, 10));
        bdatestatus1.setMinimumSize(new java.awt.Dimension(42, 10));

        org.jdesktop.layout.GroupLayout interPanelLayout = new org.jdesktop.layout.GroupLayout(interPanel);
        interPanel.setLayout(interPanelLayout);
        interPanelLayout.setHorizontalGroup(
                interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(interPanelLayout.createSequentialGroup().addContainerGap().add(interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(interPanelLayout.createSequentialGroup().add(bdatestatus1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 767, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap()).add(interPanelLayout.createSequentialGroup().add(interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(bdatestatus11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 576, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(bdatestatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 488, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 218, Short.MAX_VALUE).add(storecodeLabel).add(2, 2, 2).add(storeCode).add(18, 18, 18).add(busDateLabel).addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED).add(busDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 116, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))));
        interPanelLayout.setVerticalGroup(
                interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(interPanelLayout.createSequentialGroup().addContainerGap().add(interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE).add(busDateLabel).add(storecodeLabel).add(storeCode).add(busDate)).add(org.jdesktop.layout.GroupLayout.TRAILING, interPanelLayout.createSequentialGroup().add(bdatestatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE).add(bdatestatus11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))).addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED).add(bdatestatus1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).addContainerGap()));

        MainTabbedPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        MainTabbedPane.setName("MainTabbedPane"); // NOI18N
        MainTabbedPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {

            public void mouseMoved(java.awt.event.MouseEvent evt) {
                MainTabbedPaneMouseMoved(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(interPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(MainTabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1140, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE));
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jPanel1Layout.createSequentialGroup().add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(5, 5, 5).add(interPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE).add(6, 6, 6).add(MainTabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 630, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)));

        jScrollPane1.setViewportView(jPanel1);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        org.jdesktop.layout.GroupLayout jPanel5Layout = new org.jdesktop.layout.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1146, Short.MAX_VALUE));
        jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING).add(jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 800, Short.MAX_VALUE));
        getContentPane().add(jPanel5);
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width - 1154) / 2, (screenSize.height - 855) / 2, 1154, 855);

        // pack();
    }

    /*
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        storeBanner1 = new ISRetail.components.StoreBanner();
        interPanel = new javax.swing.JPanel();
        storecodeLabel = new javax.swing.JLabel();
        busDateLabel = new javax.swing.JLabel();
        storeCode = new javax.swing.JLabel();
        busDate = new javax.swing.JLabel();
        bdatestatus = new javax.swing.JLabel();
        bdatestatus11 = new javax.swing.JLabel();
        bdatestatus1 = new javax.swing.JLabel();
        MainTabbedPane = new javax.swing.JTabbedPane();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("TITAN Eye+    Server Console");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setExtendedState(6);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosed(java.awt.event.WindowEvent evt) {
                formWindowClosed(evt);
            }
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });
        getContentPane().setLayout(new java.awt.GridLayout(1, 0));

        jPanel5.setBackground(new java.awt.Color(235, 235, 245));
        jPanel5.setAutoscrolls(true);
        jPanel5.setMaximumSize(new java.awt.Dimension(1152, 864));
        jPanel5.setMinimumSize(new java.awt.Dimension(23, 23));
        jPanel5.setName("jPanel5"); // NOI18N
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jScrollPane1.setBackground(new java.awt.Color(143, 194, 245));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setMaximumSize(new java.awt.Dimension(1152, 864));
        jScrollPane1.setName("jScroll"); // NOI18N
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1152, 864));
        jScrollPane1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseMoved(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(235, 235, 245));
        jPanel1.setAutoscrolls(true);
        jPanel1.setMaximumSize(new java.awt.Dimension(1152, 864));
        jPanel1.setName("jPanel1"); // NOI18N
        jPanel1.setOpaque(false);
        jPanel1.setPreferredSize(new java.awt.Dimension(1152, 864));
        jPanel1.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                jPanel1MouseMoved(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 102, 51));

        jLabel1.setBackground(new java.awt.Color(255, 153, 51));
        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/ISRetail/images/title.jpg"))); // NOI18N

        storeBanner1.setBackground(new java.awt.Color(0, 0, 0));
        storeBanner1.setForeground(new java.awt.Color(0, 0, 51));

        org.jdesktop.layout.GroupLayout jPanel2Layout = new org.jdesktop.layout.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(247, Short.MAX_VALUE)
                .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 815, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(storeBanner1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2Layout.createSequentialGroup()
                .add(jPanel2Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(storeBanner1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        storecodeLabel.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        storecodeLabel.setForeground(new java.awt.Color(88, 109, 148));
        storecodeLabel.setText("Store Code :");

        busDateLabel.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        busDateLabel.setForeground(new java.awt.Color(88, 109, 148));
        busDateLabel.setText("Posting Date :");

        storeCode.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        storeCode.setForeground(new java.awt.Color(88, 109, 148));
        storeCode.setText("STORE");

        busDate.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        busDate.setForeground(new java.awt.Color(88, 109, 148));
        busDate.setText("DD/MM/YYYY");

        bdatestatus.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        bdatestatus.setForeground(new java.awt.Color(255, 0, 51));
        bdatestatus.setMaximumSize(new java.awt.Dimension(42, 10));
        bdatestatus.setMinimumSize(new java.awt.Dimension(42, 10));

        bdatestatus11.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        bdatestatus11.setMaximumSize(new java.awt.Dimension(42, 10));
        bdatestatus11.setMinimumSize(new java.awt.Dimension(42, 10));

        bdatestatus1.setFont(new java.awt.Font("Verdana", 1, 12)); // NOI18N
        bdatestatus1.setForeground(new java.awt.Color(255, 0, 51));
        bdatestatus1.setMaximumSize(new java.awt.Dimension(42, 10));
        bdatestatus1.setMinimumSize(new java.awt.Dimension(42, 10));

        org.jdesktop.layout.GroupLayout interPanelLayout = new org.jdesktop.layout.GroupLayout(interPanel);
        interPanel.setLayout(interPanelLayout);
        interPanelLayout.setHorizontalGroup(
            interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(interPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(interPanelLayout.createSequentialGroup()
                        .add(bdatestatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 488, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 306, Short.MAX_VALUE)
                        .add(storecodeLabel)
                        .add(2, 2, 2)
                        .add(storeCode)
                        .add(18, 18, 18)
                        .add(busDateLabel)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(busDate, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 116, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(interPanelLayout.createSequentialGroup()
                        .add(interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(bdatestatus1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 767, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(bdatestatus11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 576, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        interPanelLayout.setVerticalGroup(
            interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(interPanelLayout.createSequentialGroup()
                .addContainerGap()
                .add(interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(interPanelLayout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(busDateLabel)
                        .add(storeCode)
                        .add(busDate)
                        .add(storecodeLabel))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, interPanelLayout.createSequentialGroup()
                        .add(bdatestatus, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 18, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(bdatestatus11, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(bdatestatus1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 16, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        MainTabbedPane.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(88, 109, 148)));
        MainTabbedPane.setName("MainTabbedPane"); // NOI18N
        MainTabbedPane.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseMoved(java.awt.event.MouseEvent evt) {
                MainTabbedPaneMouseMoved(evt);
            }
        });

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(interPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
            .add(MainTabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 1147, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1Layout.createSequentialGroup()
                .add(jPanel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(5, 5, 5)
                .add(interPanel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(MainTabbedPane, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 665, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(19, 19, 19))
        );

        jScrollPane1.setViewportView(jPanel1);

        jPanel5.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, -1));

        getContentPane().add(jPanel5);

        pack();
    }// </editor-fold>//GEN-END:initComponents
     */
    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        // TODO add your handling code here:
        //added by ravi thota on 31.05.2013 to restrict closing pos server before pos client
        StartRMIServer rc = new StartRMIServer();
        String s = rc.getRunningClients();
        Connection con = null;
        if (s.trim().length() == 0) {
            int closeoption = 0;
            PosStagingDO stagingdo;
            try {
                Thread t = new Thread(new Runnable() {
                    public void run() {
                        HighFrequencyResponseWebService highFrequencyResponseWebService;
                        try {
                            highFrequencyResponseWebService = new HighFrequencyResponseWebService();
                            stagingthread.getHighFreResDisplayArea().append("\n\n************************************\n");
                            stagingthread.getHighFreResDisplayArea().append("\n\nSending request while closing server\n");
                            highFrequencyResponseWebService.callWebService(stagingthread.getHighFreResDisplayArea());
                            stagingthread.getHighFreResDisplayArea().append("\n\n************************************\n");
                            // checking is there any waitig transactions available or not
                            stagingTableStatus.performSearchForFilters();
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        } finally {
                            highFrequencyResponseWebService = null;
                        }
                    }
                });
                UnclosedProgressBar pb = new UnclosedProgressBar();
                pb.start(t, this, "Checking Transactions...");
                t.join();
                MsdeConnection msdeConnection = new MsdeConnection();
                con = msdeConnection.createConnection();
                // calling high frequeny response
                stagingdo = new PosStagingDO();
                setCursor(null);
                if (stagingdo.getPendingTransOnTheDay(con)) {
                    closeoption = JOptionPane.showConfirmDialog(this, "Some Of The Transactions Are Not Completed Or Waiting \nStill Do You want to Exit?", "Confirmation", JOptionPane.YES_NO_OPTION);
                }
                //dialog.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {

                stagingdo = null;
            }
            //end : checking not cleared trasactions while pos server closing
            if (closeoption == JOptionPane.YES_OPTION) {
                int option = JOptionPane.showConfirmDialog(this, "Do You want to Exit ?", "Confirmation", JOptionPane.YES_NO_OPTION);
                if (option == JOptionPane.YES_OPTION) {
                    try {
                        SalesOrderBillingDO salesOrderBillingDO = new SalesOrderBillingDO();
                        salesOrderBillingDO.deleteLockEntry("", con);
                        con.commit();
                        con.close();
                        System.exit(0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    this.setVisible(true);
                    isCloserequired = false;
                }
            } else {
                this.setVisible(true);
                isCloserequired = false;
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please close POS Client at the host " + s);
            this.setVisible(true);
            isCloserequired = false;
        }
    }//GEN-LAST:event_formWindowClosing

    private void RecreatePDF_Directorie() {
        try {
            File files = new File("./POSClient");
            if (!files.exists()) {
                if (files.mkdirs()) {
                    System.out.println("Multiple directories are created!");
                } else {
                    System.out.println("Failed to create multiple directories!");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void formWindowClosed(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosed
        // TODO add your handling code here:
        if (!isCloserequired) {
            this.setVisible(true);
        }
    }//GEN-LAST:event_formWindowClosed

    private void MainTabbedPaneMouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_MainTabbedPaneMouseMoved
        // TODO add your handling code here:
    }//GEN-LAST:event_MainTabbedPaneMouseMoved

    public void myMainTabbedPaneMouseMoved(java.awt.event.MouseEvent evt) {

        //  System.out.println("MainTabbedPaneMouseMoved...");
        Ho(evt);

    }
    private void jPanel1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel1MouseMoved
        // TODO add your handling code here:
        //  System.out.println("jPanel1MouseMoved..... ");
        Ho(evt);
}//GEN-LAST:event_jPanel1MouseMoved

    private void jScrollPane1MouseMoved(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseMoved
        // TODO add your handling code here:
        //  System.out.println("jScrollPane1MouseMoved..... ");
        /*  System.out.println("mouse moved ... "+evt.getX());
        //  if(evt.getX()>=this.getWidth()-10)
        {
        Rectangle r= new Rectangle( evt.getX(), evt.getY(), 10,10);
        // ((JPanel)evt.getSource()).scrollRectToVisible(r);
        jScrollPane1.scrollRectToVisible(r);

        }*/
        Ho(evt);
}//GEN-LAST:event_jScrollPane1MouseMoved

    void Ho(java.awt.event.MouseEvent evt) {
        /*  //   System.out.println("ev: "+evt.getComponent().getName()+" "+evt.getX()+" "+evt.getY()+" "+xslide+" "+yslide+" "+idealResolution);;
        String ss = ((JComponent)evt.getSource()).getName();
        //   System.out.println("Ho..."+ss+" "+evt.getY());
        int W = 100;
        int H = 100;
        int x = evt.getX() ;
        int y = evt.getY() ;
        if(ss!=null && ss.equals("jScroll"))
        {

        if(x>sw-250){
        Rectangle rect = new Rectangle(sw-10, evt.getY(), 100, 100);
        jPanel1.scrollRectToVisible(rect);
        xslide=true;
        return;
        }
        if(y>=sh-418){

        //   Rectangle rect = new Rectangle(sh-10, evt.getX(), 100, 100);
        Rectangle rect = new Rectangle(evt.getX(),sh-10 , 100, 100);
        jPanel1.scrollRectToVisible(rect);
        yslide=true;
        return;
        }
        }
        if(!idealResolution)
        {
        try{


        // System.out.println("ho... "+y);

        //if((x>=sw-250 +( xslide?150:0)))
        if((x>=sw-250 ))
        {
        // System.out.println(x+" "+y+" "+W+" "+H);
        Rectangle rect = new Rectangle(x, y, W, H);
        ((JComponent)evt.getSource()).scrollRectToVisible(rect);

        xslide=true;
        // System.out.println("slide...");

        }
        else if(xslide)
        {

        Rectangle rect = new Rectangle(0, 0, W, H);
        ((JComponent)evt.getSource()).scrollRectToVisible(rect);
        //  System.out.println("Beginning ....");

        xslide = false;
        }
        //  System.out.println("Ho..."+(sh-318)+" "+y);
        //if(y>=sh-418+(yslide?150:0))
        if(y>=sh-418)
        {
        //  System.out.println("yy : "+y);
        //  Rectangle rect = new Rectangle(x+W/2, y+W/2, W, H);
        Rectangle rect = new Rectangle( evt.getX(),sh-10, 100, 100);
        ((JComponent)evt.getSource()).scrollRectToVisible(rect);

        yslide=true;

        }
        else if(yslide)
        {
        //   System.out.println("yy2 : "+y);
        Rectangle rect = new Rectangle(0, 0, W, H);
        // ((JComponent)evt.getSource()).scrollRectToVisible(rect);
        jPanel1.scrollRectToVisible(rect);
        yslide = false;
        }


        //jPanel5.repaint();
        } catch (Exception e) {
        e.printStackTrace();
        }
        
        }
         */
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
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

                    bf.getXIConnectionDetails();
                    MsdeConnection.setConnectstring(connectstring1);
                    MsdeConnection.setUsername(msdeConnectionDetailsPojo.getUsername());
                    MsdeConnection.setPassword(msdeConnectionDetailsPojo.getPassword());

                } catch (Exception e) {

                }

                new ServerConsole().setVisible(true);

            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JTabbedPane MainTabbedPane;
    private static javax.swing.JLabel bdatestatus;
    private static javax.swing.JLabel bdatestatus1;
    private static javax.swing.JLabel bdatestatus11;
    private static javax.swing.JLabel busDate;
    private javax.swing.JLabel busDateLabel;
    private javax.swing.JPanel interPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private ISRetail.components.StoreBanner storeBanner1;
    private javax.swing.JLabel storeCode;
    private javax.swing.JLabel storecodeLabel;
    // End of variables declaration//GEN-END:variables

    public String getBusinessDate() {
        return businessDate;
    }

    public static JTextArea getBackupdisp() {
        return stagingthread.getBackupdisplayArea();
    }

    public void handleConnection() {
        try {
            communThread = new Thread(new clientConnectionAccepterThread(server, port));
            communThread.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

//    public static void setBusinessDate() {
//        Connection con;
//        MsdeConnection msdeconn;
//        SiteMasterDO siteMasterDO;
//        try {
//            msdeconn = new MsdeConnection();
//            con = msdeconn.createConnection();
//            siteMasterDO = new SiteMasterDO();
//            SystemDate systemDate = siteMasterDO.getBusinessDate(con);
//            con.close();
//            if (systemDate.getPostingIndicator() != null) {
//                if (systemDate.getPostingIndicator().equalsIgnoreCase("X")) {
//                    businessDate = ConvertDate.getNumericToDate(systemDate.getPostingDate());
//                } else {
//                    businessDate = ConvertDate.getNumericToDate(systemDate.getSystemDate());
//                }
//            } else {
//                businessDate = ConvertDate.getNumericToDate(systemDate.getSystemDate());
//            }
//            busDate.setText(businessDate);
//        } catch (Exception e) {
//            e.printStackTrace();
//
//        } finally {
//            con = null;
//            msdeconn = null;
//            siteMasterDO = null;
//        }
//
//    }
    public static void setBdateColor(Color color) {
        bdatestatus.setForeground(color);
        bdatestatus11.setForeground(color);
    }

    public static void setBdateStatus(String budatestatus) {
        bdatestatus.setText(budatestatus);
    }

    public static void setBdateStatus1(String budatestatus) {
        bdatestatus11.setText(budatestatus);
    }

    public static void setCorrectionMessage(String correctmessage) {
        bdatestatus1.setText(correctmessage);
    }

    public static void setBusinessDate(Connection con, String manualdategiven) {

        SiteMasterDO siteMasterDO;
        CheckBusinessDateDO checkBusinessDateDO;
        try {

            siteMasterDO = new SiteMasterDO();
            checkBusinessDateDO = new CheckBusinessDateDO();
            SystemDate systemDate = siteMasterDO.getBusinessDate(con);

            if (manualdategiven == null) {
                businessDate = ConvertDate.getNumericToDate(systemDate.getPostingDate());

                if (businessDate != null) {
                    if (busDate != null) {
                        busDate.setText(businessDate);
                    }
                }
            } else {
                System.out.println("Businessdate:" + ConvertDate.getNumericToDate(systemDate.getPostingDate()));
                businessDate = ConvertDate.getNumericToDate(systemDate.getPostingDate());

                if (businessDate != null) {
                    if (busDate != null) {
                        System.out.println("Businessdate:" + ConvertDate.getNumericToDate(systemDate.getPostingDate()));
                        busDate.setText(businessDate);
                    }
                }
            }
            if (businessDate != null) {
                StagingTableStatus.setfromtodate(businessDate);
            }
            //   CheckBusinessDateThread.checkiffiscalyearchanged(businessDate, checkBusinessDateDO, con, siteMasterDO);

        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            //   con = null;
            //  msdeconn = null;
            siteMasterDO = null;
        }

    }

    public static boolean performConnectionChecking(JTextArea statusArea) {
        boolean valid = true;
        try {
            statusArea.append("\nChecking for Network Connection ....\n");
            int internetconnection = NetConnection.checkInternetConnection();
            if (internetconnection == 3 || internetconnection == 2) {
                valid = false;
                statusArea.append("\nDownload Failed : Check Your Network for Internet Connection !");
            } else {
                statusArea.append("\nChecking PI Server is Reachable....\n");
                internetconnection = NetConnection.checkXIConnection();
                if (internetconnection == 2) {
                    valid = false;
                    statusArea.append("\nFailed : Incorrect Server Address : Contact Administrator to Fix the Problem!");
                } else if (internetconnection == 3 || internetconnection == 4) {
                    valid = false;
                    statusArea.append("\nFailed : Unknown Error : Contact Administrator to Fix the Problem!");
                }
            }
        } catch (Exception e) {
            valid = false;
            statusArea.append("\nFailed : Exception : " + e);
        } finally {
        }
        return valid;
    }

    private boolean isPosClientClosed() {
//        MonitoredHost host = null;
//        Set vms = null;
//        HostIdentifier hostiden;
//        try {
//            //hostiden = new HostIdentifier((String) null);
//            hostiden = new HostIdentifier("ge-dt-0430");
//            host = MonitoredHost.getMonitoredHost(hostiden);
//            vms = host.activeVms();
//        } catch (java.net.URISyntaxException sx) {
//            throw new InternalError(sx.getMessage());
//        } catch (MonitorException mx) {
//            throw new InternalError(mx.getMessage());
//        } catch (Exception ex){
//            ex.printStackTrace();
//        }
//        MonitoredVm mvm = null;
//        for (Object vmid : vms) {
//            if (vmid instanceof Integer) {
//                String name = vmid.toString(); // default to pid if name not available
//                try {
//                    mvm = host.getMonitoredVm(new VmIdentifier(name));
//                    // use the command line as the display name
//                    name = MonitoredVmUtil.commandLine(mvm);
//                    name = name.substring(name.lastIndexOf("\\") + 1, name.length());
//                    mvm.detach();
//                    System.out.println("Starting process name : " + name);
//                    if ((name.equalsIgnoreCase("ISRetail.Screens.Login"))) {
//                        return false;
//                    }
//                } catch (Exception x) {
//                    //ignore
//                    System.out.println("Ignore the exception");// added on 28 Jun 2011 for printing the exception
//                    x.printStackTrace(); // added on 28 Jun 2011 for printing the exception
//                }
//            }
//        }

        return true;
    }
}
