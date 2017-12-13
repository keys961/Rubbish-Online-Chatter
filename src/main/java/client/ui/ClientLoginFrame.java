/*
 * Created by JFormDesigner on Mon Dec 11 16:15:02 CST 2017
 */

package client.ui;

import client.Client;
import common.ClientInfo;
import common.DBConnectionFactory;
import common.ServerDBConfig;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jintao Ye
 */
public class ClientLoginFrame extends JFrame
{

    public ClientLoginFrame()
    {
        initComponents();
        this.setSize(360, 240);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private int checkUserValid()
    {
        String username = usernameTextField.getText();
        String password = new String(passwordField.getPassword());

        Connection serverConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try
        {
            serverConnection = DBConnectionFactory.getServerConnection();
        }
        catch (Exception e)
        {
            return 0;
        }

        try
        {
            String query = String.format("SELECT %s, %s FROM %s WHERE %s = ?",
                    ServerDBConfig.ServerUser.UID,
                    ServerDBConfig.ServerUser.PASSWORD,
                    "server_user",
                    ServerDBConfig.ServerUser.USERNAME);
                    /*"SELECT " + ServerDBConfig.ServerUser.PASSWORD + +
                    " " + Se" FROM server_user WHERE "
                    + ServerDBConfig.ServerUser.USERNAME + " = ?";*/

            preparedStatement = serverConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                if(password.equals(resultSet.getString(2)))
                    return resultSet.getInt(1);
                return 0;
            }
            else
                return 0;
        }
        catch (SQLException e)
        {
            return 0;
        }
        finally
        {
            try
            {
                serverConnection.close();
                preparedStatement.close();
                resultSet.close();
            }
            catch (SQLException | NullPointerException e)
            {
                //
            }

        }
    }

    private void loginButtonMouseClicked(MouseEvent e)
    {
        String ip = ipTextField.getText();
        int uid = -1;
        if((uid = checkUserValid()) == 0)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Username/Password incorrect!",
                    "Username/Password incorrect",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        int port;
        try
        {
            port = Integer.parseInt(portTextField.getText());
        }
        catch (NumberFormatException ex)
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Input Format Error", "Input Format Error",
                     JOptionPane.ERROR_MESSAGE);
            return;
        }

        Client client;
        try
        {
            client = new Client(ip, port, new ClientInfo(uid, usernameTextField.getText()));
        }
        catch (IOException ex)
        {
            JOptionPane.showMessageDialog(null, "Connection Error", "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        ClientMainFrame mainFrame = new ClientMainFrame(client);
        mainFrame.setVisible(true);
        this.setVisible(false);
    }

    private void registerButtonMouseClicked(MouseEvent e)
    {
        ClientRegisterFrame clientRegisterFrame = new ClientRegisterFrame();
        clientRegisterFrame.setVisible(true);
    }

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jintao Ye
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label3 = new JLabel();
        ipTextField = new JTextField();
        label4 = new JLabel();
        portTextField = new JTextField();
        label1 = new JLabel();
        usernameTextField = new JTextField();
        label2 = new JLabel();
        passwordField = new JPasswordField();
        buttonBar = new JPanel();
        loginButton = new JButton();
        registerButton = new JButton();

        //======== this ========
        setTitle("Client Log in");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

            // JFormDesigner evaluation mark
           /* dialogPane.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});
            */
            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridLayout(4, 2));

                //---- label3 ----
                label3.setText("Server IP:");
                contentPanel.add(label3);
                contentPanel.add(ipTextField);

                //---- label4 ----
                label4.setText("Server Port:");
                contentPanel.add(label4);
                contentPanel.add(portTextField);

                //---- label1 ----
                label1.setText("Username:");
                contentPanel.add(label1);
                contentPanel.add(usernameTextField);

                //---- label2 ----
                label2.setText("Password:");
                contentPanel.add(label2);
                contentPanel.add(passwordField);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- loginButton ----
                loginButton.setText("Login");
                loginButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        loginButtonMouseClicked(e);
                    }
                });
                buttonBar.add(loginButton, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 5), 0, 0));

                //---- registerButton ----
                registerButton.setText("Register");
                registerButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        registerButtonMouseClicked(e);
                    }
                });
                buttonBar.add(registerButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
                    GridBagConstraints.CENTER, GridBagConstraints.BOTH,
                    new Insets(0, 0, 0, 0), 0, 0));
            }
            dialogPane.add(buttonBar, BorderLayout.SOUTH);
        }
        contentPane.add(dialogPane, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Jintao Ye
    private JPanel dialogPane;
    private JPanel contentPanel;
    private JLabel label3;
    private JTextField ipTextField;
    private JLabel label4;
    private JTextField portTextField;
    private JLabel label1;
    private JTextField usernameTextField;
    private JLabel label2;
    private JPasswordField passwordField;
    private JPanel buttonBar;
    private JButton loginButton;
    private JButton registerButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
