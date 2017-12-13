/*
 * Created by JFormDesigner on Tue Dec 12 18:37:52 CST 2017
 */

package client.ui;

import common.DBConnectionFactory;
import common.ServerDBConfig;

import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @author Jintao Ye
 */
public class ClientRegisterFrame extends JFrame {
    public ClientRegisterFrame() {
        initComponents();
    }

    private void okButtonMouseClicked(MouseEvent e)
    {
        String username = usernameTextField.getText();
        String password = new String(passwordField.getPassword());
        String passwordAgain = new String(passwordAgainField.getPassword());

        if(username.length() == 0 || password.length() == 0 || passwordAgain.length() == 0 || !password.equals(passwordAgain))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Username/Password input incorrect!",
                    "Username/Password Input Incorrect",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection serverConnection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT count(uid) FROM server_user WHERE "
            + ServerDBConfig.ServerUser.USERNAME + " = ?";
        String update = "INSERT INTO server_user VALUES (?,?,?)";
        try
        {
            serverConnection = DBConnectionFactory.getServerConnection();
            preparedStatement = serverConnection.prepareStatement(query);
            preparedStatement.setString(1, username);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                int count = resultSet.getInt(1);
                if(count > 0)
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Username duplicated!",
                            "Duplicated Username",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            preparedStatement = serverConnection.prepareStatement(update);
            preparedStatement.setInt(1, new Date().hashCode());
            preparedStatement.setString(2, username);
            preparedStatement.setString(3, password);
            if(preparedStatement.executeUpdate() > 0)
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "User Created!",
                        "User Created",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "User create fail!",
                        "User create fail",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Server Internal Error!",
                    "Server Internal Error",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }
        finally
        {
            try
            {
                serverConnection.close();
                preparedStatement.close();
                resultSet.close();
            }
            catch (SQLException | NullPointerException ex)
            {
                //
            }
        }
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jintao Ye
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        usernameTextField = new JTextField();
        label2 = new JLabel();
        passwordField = new JPasswordField();
        label3 = new JLabel();
        passwordAgainField = new JPasswordField();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setTitle("Client Register");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

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
                contentPanel.setLayout(new GridLayout(3, 2));

                //---- label1 ----
                label1.setText("Username:");
                contentPanel.add(label1);
                contentPanel.add(usernameTextField);

                //---- label2 ----
                label2.setText("Password:");
                contentPanel.add(label2);
                contentPanel.add(passwordField);

                //---- label3 ----
                label3.setText("Again:");
                contentPanel.add(label3);
                contentPanel.add(passwordAgainField);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- okButton ----
                okButton.setText("OK");
                okButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        okButtonMouseClicked(e);
                    }
                });
                buttonBar.add(okButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
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
    private JLabel label1;
    private JTextField usernameTextField;
    private JLabel label2;
    private JPasswordField passwordField;
    private JLabel label3;
    private JPasswordField passwordAgainField;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
