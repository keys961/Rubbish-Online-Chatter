/*
 * Created by JFormDesigner on Mon Dec 11 16:22:21 CST 2017
 */

package client.ui;

import java.awt.event.*;
import client.Client;
import client.ClientGetOnlineList;
import common.ClientInfo;

import java.awt.*;
import java.io.*;
import java.net.Socket;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jintao Ye
 */
public class ClientMainFrame extends JFrame
{
    private Client client;

    private int currentId;

    public ClientMainFrame(Client client)
    {
        this.client = client;
        initComponents();

        try
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(client.getSocket().getInputStream()));
            PrintWriter out = new PrintWriter(client.getSocket().getOutputStream());
            out.println("[GET INFO]"); //send request to get online list
            out.flush();
            String id = in.readLine();
            this.idLabel.setText("Your ID: " + id);
            currentId = Integer.parseInt(id);

            Thread t = new Thread(new ClientGetOnlineList(onlineList, client.getSocket())); //start to update the online list
            t.start();
        }
        catch (IOException e)
        {
            //do nothing
        }
    }


    private void refreshMenuItemActionPerformed(ActionEvent e)
    {
        try
        {
            Thread t = new Thread(new ClientGetOnlineList(onlineList, client.getSocket()));
            t.start();
        }
        catch (IOException ex)
        {
            //do nothing
        }
    }

    private void exitMenuItemActionPerformed(ActionEvent e)
    {
        Socket socket = client.getSocket();
        try
        {
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("[EXIT]");
            out.flush();
            out.close();
            socket.close();
            System.exit(0);
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
    }

    private void refreshMenuItemMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void exitMenuItemMouseClicked(MouseEvent e) {
        // TODO add your code here
    }

    private void onlineListMouseClicked(MouseEvent e)
    {
        if(onlineList.getSelectedIndex() != -1)
        {
            if(e.getClickCount() >= 2)
            {
                String targetInfo = (String)onlineList.getSelectedValue();

                String uidStr = targetInfo.split(",")[0].substring(4);
                int uid = Integer.parseInt(uidStr);
                ClientMessageFrame clientMessageFrame = new ClientMessageFrame(uid, targetInfo, client, currentId);
                clientMessageFrame.setVisible(true);
            }
        }
    }

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jintao Ye
        menuBar1 = new JMenuBar();
        menu1 = new JMenu();
        refreshMenuItem = new JMenuItem();
        exitMenuItem = new JMenuItem();
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        scrollPane1 = new JScrollPane();
        onlineList = new JList();
        panel1 = new JPanel();
        idLabel = new JLabel();
        textArea1 = new JTextArea();

        //======== this ========
        setTitle("Main Frame");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== menuBar1 ========
        {

            //======== menu1 ========
            {
                menu1.setText("Options");

                //---- refreshMenuItem ----
                refreshMenuItem.setText("Refresh Online Client List");
                refreshMenuItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        refreshMenuItemMouseClicked(e);
                    }
                });
                refreshMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        refreshMenuItemActionPerformed(e);
                    }
                });
                menu1.add(refreshMenuItem);

                //---- exitMenuItem ----
                exitMenuItem.setText("Exit");
                exitMenuItem.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        exitMenuItemMouseClicked(e);
                    }
                });
                exitMenuItem.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
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
            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridLayout(1, 2));

                //======== scrollPane1 ========
                {

                    //---- onlineList ----
                    onlineList.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                            onlineListMouseClicked(e);
                        }
                    });
                    scrollPane1.setViewportView(onlineList);
                }
                contentPanel.add(scrollPane1);

                //======== panel1 ========
                {
                    panel1.setLayout(new GridLayout(2, 0));

                    //---- idLabel ----
                    idLabel.setText("Your ID:");
                    panel1.add(idLabel);

                    //---- textArea1 ----
                    textArea1.setText("Double click the item on the left to start chatting!");
                    textArea1.setLineWrap(true);
                    textArea1.setFont(new Font("\u5fae\u8f6f\u96c5\u9ed1", Font.BOLD, 20));
                    textArea1.setAlignmentX(1.0F);
                    textArea1.setAlignmentY(1.0F);
                    textArea1.setEditable(false);
                    textArea1.setTabSize(4);
                    textArea1.setWrapStyleWord(true);
                    panel1.add(textArea1);
                }
                contentPanel.add(panel1);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);
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
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JScrollPane scrollPane1;
    private JList onlineList;
    private JPanel panel1;
    private JLabel idLabel;
    private JTextArea textArea1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
