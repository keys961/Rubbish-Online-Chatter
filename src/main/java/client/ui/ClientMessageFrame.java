/*
 * Created by JFormDesigner on Mon Dec 11 18:00:16 CST 2017
 */

package client.ui;

import java.awt.event.*;
import client.Client;
import client.ClientImpl;
import common.ClientDBConfig;
import common.ClientInfo;
import common.DBConnectionFactory;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import javax.swing.*;

/**
 * @author Jintao Ye
 */
public class ClientMessageFrame extends JFrame
{
    private Client client;

    private ClientInfo targetInfo;

    private ClientImpl clientImpl;

    public ClientMessageFrame(Client client, ClientInfo targetInfo, ClientImpl clientImpl)
    {
        initComponents();

        this.client = client;
        this.targetInfo = targetInfo;
        this.clientImpl = clientImpl;
        this.clientImpl.addTextArea(targetInfo, this.chatTextArea);
        targetInfoLabel.setText("You're chatting with: " + this.targetInfo.getUsername());
        this.setTitle("You're chatting with: " + this.targetInfo.getUsername());
        loadHistoryMessage();
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    }

    private void loadHistoryMessage()
    {
        String query = String.format("SELECT %s, %s, %s, %s FROM %s " +
                "WHERE ((%s = ? AND %s = ?) OR (%s = ? AND %s = ?)) AND %s = ?",
                ClientDBConfig.ClientMessage.FROM_ID,
                ClientDBConfig.ClientMessage.TO_ID,
                ClientDBConfig.ClientMessage.MESSAGE,
                ClientDBConfig.ClientMessage.SENT_TIME,
                ClientDBConfig.ClientMessage.TABLE_NAME,
                ClientDBConfig.ClientMessage.FROM_ID,
                ClientDBConfig.ClientMessage.TO_ID,
                ClientDBConfig.ClientMessage.FROM_ID,
                ClientDBConfig.ClientMessage.TO_ID,
                ClientDBConfig.ClientMessage.USER_ID);
        Connection conn;
        PreparedStatement statement;
        ResultSet resultSet;

        try
        {
            conn = DBConnectionFactory.getClientConnection();
            statement = conn.prepareStatement(query);
            statement.setInt(1, client.getClientInfo().getId());
            statement.setInt(2, targetInfo.getId());
            statement.setInt(4, client.getClientInfo().getId());
            statement.setInt(3, targetInfo.getId());
            statement.setInt(5, client.getClientInfo().getId());
            resultSet = statement.executeQuery();
            while(resultSet.next())
            {
                int fromId = resultSet.getInt(1);
                int toId = resultSet.getInt(2);
                String message = resultSet.getString(3);
                String date = resultSet.getString(4);
                if(fromId == targetInfo.getId())
                {
                    chatTextArea.append("From " + targetInfo.getUsername() + " at " + date + " :\r\n");
                    chatTextArea.append(message);
                    chatTextArea.append("\r\n");
                }
                else if(fromId == client.getClientInfo().getId())
                {
                    chatTextArea.append("You at " + date + " :\r\n");
                    chatTextArea.append(message);
                    chatTextArea.append("\r\n");
                }
            }

            conn.close();
        }
        catch (SQLException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
    }


    private void sendButtonMouseClicked(MouseEvent e)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new java.util.Date();
        String message = sendTextArea.getText();
        String[] segment = message.split("[\\r\\n]+");
        StringBuilder msg = new StringBuilder();
        for(String s : segment)
        {
            if(s == null || s.length() == 0)
                continue;
            msg.append(s);
            msg.append("\r\n");
        }
        sendTextArea.setText("");
        chatTextArea.append("You at " + sdf.format(date) + ": \r\n");
        chatTextArea.append(msg.toString() + "\r\n");

        clientImpl.sendMessage(targetInfo, msg.toString());
    }

    private void thisWindowClosed(WindowEvent e)
    {
        clientImpl.removeTextArea(targetInfo);
    }

    private void thisWindowClosing(WindowEvent e)
    {
        clientImpl.removeTextArea(targetInfo);
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
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                thisWindowClosed(e);
            }
            @Override
            public void windowClosing(WindowEvent e) {
                thisWindowClosing(e);
            }
        });
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
