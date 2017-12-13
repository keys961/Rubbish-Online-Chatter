package client.ui;

import common.DBConnectionFactory;
import common.ServerDBConfig;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ClientChangePasswordFrame extends JFrame
{
    private int currentUid;

    public ClientChangePasswordFrame(int currentUid)
    {
        initComponents();
        this.setSize(280, 160);
        this.setResizable(false);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.currentUid = currentUid;
    }

    private void okButtonMouseClicked(MouseEvent e)
    {
        String originPassword = new String(originPasswordTextField.getPassword());
        String password = new String(newPasswordField.getPassword());
        String passwordAgain = new String(newPasswordAgainField.getPassword());

        if(originPassword.length() == 0 || password.length() == 0 || passwordAgain.length() == 0 || !password.equals(passwordAgain))
        {
            Toolkit.getDefaultToolkit().beep();
            JOptionPane.showMessageDialog(null, "Password input format incorrect!",
                    "Password Input Format Incorrect",
                    JOptionPane.ERROR_MESSAGE);
            return;
        }

        Connection serverConnection = null;
        ResultSet resultSet = null;
        PreparedStatement preparedStatement = null;
        String query = "SELECT password FROM server_user WHERE "
                + ServerDBConfig.ServerUser.UID + " = ?";
        String update = String.format("UPDATE %s SET %s = ? WHERE %s = ?",
                ServerDBConfig.ServerUser.TABLE_NAME,
                ServerDBConfig.ServerUser.PASSWORD,
                ServerDBConfig.ServerUser.UID);
        try
        {
            serverConnection = DBConnectionFactory.getServerConnection();
            preparedStatement = serverConnection.prepareStatement(query);
            preparedStatement.setInt(1, currentUid);
            resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                String storedPassword = resultSet.getString(1);
                if(!storedPassword.equals(originPassword))
                {
                    Toolkit.getDefaultToolkit().beep();
                    JOptionPane.showMessageDialog(null, "Original password incorrect!",
                            "Original Password Incorrect",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }
            preparedStatement = serverConnection.prepareStatement(update);
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, currentUid);
            if(preparedStatement.executeUpdate() >= 1)
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Password changed!",
                        "Password changed",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            else
            {
                Toolkit.getDefaultToolkit().beep();
                JOptionPane.showMessageDialog(null, "Changing password failed!",
                        "Changing password failed",
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

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jintao Ye
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        originPasswordTextField = new JPasswordField();
        label2 = new JLabel();
        newPasswordField = new JPasswordField();
        label3 = new JLabel();
        newPasswordAgainField = new JPasswordField();
        buttonBar = new JPanel();
        okButton = new JButton();

        //======== this ========
        setTitle("Change Password");
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== dialogPane ========
        {
            dialogPane.setBorder(new EmptyBorder(12, 12, 12, 12));

//            // JFormDesigner evaluation mark
//            dialogPane.setBorder(new javax.swing.border.CompoundBorder(
//                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//                    java.awt.Color.red), dialogPane.getBorder())); dialogPane.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            dialogPane.setLayout(new BorderLayout());

            //======== contentPanel ========
            {
                contentPanel.setLayout(new GridLayout(3, 2));

                //---- label1 ----
                label1.setText("Origin Password:");
                contentPanel.add(label1);
                contentPanel.add(originPasswordTextField);

                //---- label2 ----
                label2.setText("Password:");
                contentPanel.add(label2);
                contentPanel.add(newPasswordField);

                //---- label3 ----
                label3.setText("Again:");
                contentPanel.add(label3);
                contentPanel.add(newPasswordAgainField);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- okButton ----
                okButton.setText("Change");
                okButton.addMouseListener(new MouseAdapter()
                {
                    @Override
                    public void mouseClicked(MouseEvent e)
                    {
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
    private JPasswordField originPasswordTextField;
    private JLabel label2;
    private JPasswordField newPasswordField;
    private JLabel label3;
    private JPasswordField newPasswordAgainField;
    private JPanel buttonBar;
    private JButton okButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
