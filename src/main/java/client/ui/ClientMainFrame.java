/*
 * Created by JFormDesigner on Mon Dec 11 16:22:21 CST 2017
 */

package client.ui;

import java.awt.event.*;
import client.Client;
import client.ClientImpl;
import common.ClientInfo;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jintao Ye
 */
public class ClientMainFrame extends JFrame
{
    private Client client;

    private ClientInfo clientInfo;

    private Map<ClientInfo, JTextArea> clientInfoJTextAreaMap = new ConcurrentHashMap<>();

    private ClientImpl clientImpl;

    private Thread t;

    public ClientMainFrame(Client client)
    {
        this.client = client;
        this.clientInfo = client.getClientInfo();
        initComponents();
        this.setSize(new Dimension(550, 600));
        this.setResizable(false);
        try
        {
            this.clientImpl = new ClientImpl(client, clientInfoJTextAreaMap, this.onlineList);
        }
        catch (Exception e)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Initializing receiving message error!",
                    "Initializing receiving message error",
                    JOptionPane.ERROR_MESSAGE);
            this.dispose();
            return;
        }
            /**
             * GET INFO FORMAT:
             * [GET INFO]
             * \r\n
             */
//            out.println("[GET INFO]"); //send request to get client info
//            out.println();
//            out.flush();
//            String id = in.readLine();
            /**
             * Register the client to the server, format:
             * [REG]\r\n
             * \r\n
             * (uid)&&(username)\r\n
             * \r\n
             */
        /**
         * Get online list format:
         * [GET ONLINE LIST]\r\n
         * \r\n
         */
            t = new Thread(clientImpl);
            t.start();

            String reg = "[REG]\r\n\r\n" + clientInfo.getId() + "&&" + clientInfo.getUsername() + "\r\n\r\n";//reg
            this.clientImpl.sendMessage(null, reg);
            this.clientImpl.sendMessage(null, "[GET ONLINE LIST]\r\n\r\n");

//            Thread t = new Thread(new ClientGetOnlineList(onlineList, client.getSocket())); //start to update the online list
//            t.start();
        this.usernameLabel.setText("Username: " + clientInfo.getUsername());
        this.idLabel.setText("ID: " + Integer.toString(clientInfo.getId()));
        this.setTitle("Online Chatter - You are " + clientInfo.getUsername());
    }


    private void refreshMenuItemActionPerformed(ActionEvent e)
    {
        this.clientImpl.sendMessage(null, "[GET ONLINE LIST]\r\n\r\n");
    }

    private void exitMenuItemActionPerformed(ActionEvent e)
    {
        try
        {
            clientImpl.sendMessage(null, "[EXIT]\r\n\r\n");
            /**
             * Exit, format:
             * [EXIT]\r\n
             * \r\n
             */
            t.join();
            System.exit(0);
        }
        catch (InterruptedException e1)
        {
            e1.printStackTrace();
        }
    }

    private void changePasswordMenuItemActionPerformed(ActionEvent e)
    {
        ClientChangePasswordFrame clientChangePasswordFrame = new ClientChangePasswordFrame(clientInfo.getId());
        clientChangePasswordFrame.setVisible(true);
    }

    private void onlineListMouseClicked(MouseEvent e)
    {
        if(onlineList.getSelectedIndex() != -1)
        {
            if(e.getClickCount() >= 2)
            {
                String targetInfoStr = (String)onlineList.getSelectedValue();

                String[] targetInfoStrs = targetInfoStr.split(",\\s+");
                String targetUsername = targetInfoStrs[1].split("\\s+")[1];
                int targetId = Integer.parseInt(targetInfoStrs[0].split("\\s+")[1]);
                ClientMessageFrame clientMessageFrame = new ClientMessageFrame(this.client,
                        new ClientInfo(targetId, targetUsername), this.clientImpl);
                clientMessageFrame.setVisible(true);
            }
        }
    }

    private void refreshButtonClicked(MouseEvent e)
    {
        this.clientImpl.sendMessage(null, "[GET ONLINE LIST]\r\n\r\n");
    }

    private void thisWindowClosing(WindowEvent e)
    {
        try
        {
            clientImpl.sendMessage(null, "[EXIT]\r\n\r\n");
            t.join();
            Thread.sleep(1);
            System.exit(0);
        }
        catch (InterruptedException ex)
        {
            ex.printStackTrace();
        }
    }

    private void thisWindowClosed(WindowEvent e)
    {
        thisWindowClosing(e);
    }

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jintao Ye
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        refreshMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        changePasswordMenuItem = new JMenuItem("Change Password");
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        onlineList = new JList();
        panel1 = new JPanel();
        panel2 = new JPanel();
        usernameLabel = new JLabel();
        idLabel = new JLabel();
        refreshButton = new JButton("Refresh Online List");
        textPane1 = new JTextPane();
        textArea1 = new JTextArea();

        //======== this ========
        setTitle("Main Frame");
        setMinimumSize(new Dimension(180, 320));
        setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.PLAIN, 12));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        addWindowListener(new WindowAdapter()
        {
            @Override
            public void windowClosed(WindowEvent e)
            {
                thisWindowClosed(e);
            }
            @Override
            public void windowClosing(WindowEvent e)
            {
                thisWindowClosing(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("Options");

                //---- refreshMenuItem ----
                refreshMenuItem.setText("Refresh Online Client List");
                refreshMenuItem.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        refreshMenuItemActionPerformed(e);
                    }
                });
                menu1.add(refreshMenuItem);

                changePasswordMenuItem.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        changePasswordMenuItemActionPerformed(e);
                    }
                });
                menu1.add(changePasswordMenuItem);

                //---- exitMenuItem ----
                exitMenuItem.setText("Exit");
                exitMenuItem.addActionListener(new ActionListener()
                {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        exitMenuItemActionPerformed(e);
                    }
                });
                menu1.add(exitMenuItem);


            }
            menuBar1.add(menu1);
        }
        setJMenuBar(menuBar1);

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

            // JFormDesigner evaluation mark
//            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
//                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridBagLayout());
                ((GridBagLayout)contentPanel.getLayout()).columnWidths = new int[] {290, 0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).rowHeights = new int[] {0, 0, 0};
                ((GridBagLayout)contentPanel.getLayout()).columnWeights = new double[] {1.0, 0.0, 1.0, 1.0E-4};
                ((GridBagLayout)contentPanel.getLayout()).rowWeights = new double[] {1.0, 0.0, 1.0E-4};

                //======== scrollPane1 ========
                {

                    //---- onlineList ----
                    onlineList.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 18));
                    onlineList.setBorder(null);
                    onlineList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
                    onlineList.setVisibleRowCount(10);
                    onlineList.addMouseListener(new MouseAdapter()
                    {
                        @Override
                        public void mouseClicked(MouseEvent e)
                        {
                            onlineListMouseClicked(e);
                        }
                    });
                    scrollPane1.setViewportView(onlineList);
                }
                contentPanel.add(scrollPane1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(20, 0, 10, 2), 0, 0));

                //======== panel1 ========
                {
                    panel1.setLayout(new GridBagLayout());
                    ((GridBagLayout)panel1.getLayout()).columnWidths = new int[] {0, 0};
                    ((GridBagLayout)panel1.getLayout()).rowHeights = new int[] {0, 0, 0, 0};
                    ((GridBagLayout)panel1.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
                    ((GridBagLayout)panel1.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0, 1.0E-4};

                    //======== panel2 ========
                    {
                        panel2.setLayout(new GridLayout(1, 2));

                        //---- usernameLabel ----
                        usernameLabel.setText("Username: ");
                        panel2.add(usernameLabel);

                        //---- idLabel ----
                        idLabel.setText("Your ID:");
                        panel2.add(idLabel);
                    }
                    panel1.add(panel2, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                        new Insets(20, 0, 0, 0), 0, 0));

                    //---- textPane1 ----
                    textPane1.setText("Double click the item in the left list to start chatting!");
                    textPane1.setFont(textPane1.getFont().deriveFont(textPane1.getFont().getStyle() | Font.BOLD, 20f));
                    textPane1.setOpaque(false);
                    panel1.add(textPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
                        GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                        new Insets(0, 0, 0, 0), 0, 0));
                    refreshButton.addMouseListener(new MouseAdapter()
                    {
                        @Override
                        public void mouseClicked(MouseEvent e)
                        {
                            refreshButtonClicked(e);
                        }
                    });
                    panel1.add(refreshButton, new GridBagConstraints(0, 2, 1, 1, 0.0, 0.0,
                            GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL,
                            new Insets(0, 0, 0, 0), 0, 0));
                }
                contentPanel.add(panel1, new GridBagConstraints(2, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 10, 0), 0, 0));
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //---- textArea1 ----
            textArea1.setText("Online List");
            textArea1.setLineWrap(true);
            textArea1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 20));
            textArea1.setAlignmentX(1.0F);
            textArea1.setAlignmentY(1.0F);
            textArea1.setTabSize(4);
            textArea1.setWrapStyleWord(true);
            textArea1.setEditable(false);
            textArea1.setOpaque(false);
            dialogPane.add(textArea1, BorderLayout.NORTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Jintao Ye
    private JMenuBar menuBar1;
    private JMenu menu1;
    private JMenuItem refreshMenuItem;
    private JMenuItem exitMenuItem;
    private JMenuItem changePasswordMenuItem;
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    private JList onlineList;
    private JPanel panel1;
    private JPanel panel2;
    private JLabel usernameLabel;
    private JLabel idLabel;
    private JButton refreshButton;
    private JTextPane textPane1;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
