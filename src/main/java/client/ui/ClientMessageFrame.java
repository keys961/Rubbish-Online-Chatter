/*
 * Created by JFormDesigner on Mon Dec 11 18:00:16 CST 2017
 */

package client.ui;

import java.awt.event.*;
import client.Client;
import client.ClientMessage;
import common.ClientInfo;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

/**
 * @author Jintao Ye
 */
public class ClientMessageFrame extends JFrame
{
    private Client client;

    private int currentId;

    private int targetUid;

    private String targetInfo;

    private ClientMessage clientMessage;

    public ClientMessageFrame(int targetUid, String targetInfo, Client client, int id)
    {
        this.targetUid = targetUid;
        this.targetInfo = targetInfo;
        this.currentId = id;
        initComponents();
        targetInfoLabel.setText("You're chatting with: " + this.targetInfo);
        try
        {
            clientMessage = new ClientMessage(targetUid, client.getSocket(), chatTextArea, id);
            Thread t = new Thread(clientMessage);
            t.start();
        }
        catch (IOException e)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Client internal error", "Client internal error",
                    JOptionPane.ERROR_MESSAGE);
            this.dispose();
        }
    }

    private void sendButtonMouseClicked(MouseEvent e)
    {
        String message = sendTextArea.getText();
        sendTextArea.setText("");
        chatTextArea.append("You:\r\n");
        chatTextArea.append(message + "\r\n");

        clientMessage.sendMessage(message);
    }

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jintao Ye
        targetInfoLabel = new JLabel();
        scrollPane1 = new JScrollPane();
        chatTextArea = new JTextArea();
        panel1 = new JPanel();
        scrollPane2 = new JScrollPane();
        sendTextArea = new JTextArea();
        sendButton = new JButton();

        //======== this ========
        setMinimumSize(new Dimension(40, 80));
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridLayout(3, 0));

        //---- targetInfoLabel ----
        targetInfoLabel.setText("You're chatting: ");
        contentPane.add(targetInfoLabel);

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(chatTextArea);
        }
        contentPane.add(scrollPane1);

        //======== panel1 ========
        {

            // JFormDesigner evaluation mark
            panel1.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            panel1.setLayout(new GridLayout(1, 2));

            //======== scrollPane2 ========
            {
                scrollPane2.setViewportView(sendTextArea);
            }
            panel1.add(scrollPane2);

            //---- sendButton ----
            sendButton.setText("Send");
            sendButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    sendButtonMouseClicked(e);
                }
            });
            panel1.add(sendButton);
        }
        contentPane.add(panel1);
        setSize(400, 300);
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Jintao Ye
    private JLabel targetInfoLabel;
    private JScrollPane scrollPane1;
    private JTextArea chatTextArea;
    private JPanel panel1;
    private JScrollPane scrollPane2;
    private JTextArea sendTextArea;
    private JButton sendButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
