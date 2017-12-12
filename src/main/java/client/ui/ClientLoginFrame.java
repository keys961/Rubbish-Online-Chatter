/*
 * Created by JFormDesigner on Mon Dec 11 16:15:02 CST 2017
 */

package client.ui;

import client.Client;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
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
    }

    private void loginButtonMouseClicked(MouseEvent e)
    {
        String ip = ipTextField.getText();

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
            client = new Client(ip, port);
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

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jintao Ye
        dialogPane = new JPanel();
        contentPanel = new JPanel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        ipTextField = new JTextField();
        label4 = new JLabel();
        portTextField = new JTextField();
        buttonBar = new JPanel();
        loginButton = new JButton();

        //======== this ========
        setTitle("Client Log in");
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
                contentPanel.add(label1);
                contentPanel.add(label2);

                //---- label3 ----
                label3.setText("Server IP:");
                contentPanel.add(label3);
                contentPanel.add(ipTextField);

                //---- label4 ----
                label4.setText("Server Port:");
                contentPanel.add(label4);
                contentPanel.add(portTextField);
            }
            dialogPane.add(contentPanel, BorderLayout.CENTER);

            //======== buttonBar ========
            {
                buttonBar.setBorder(new EmptyBorder(12, 0, 0, 0));
                buttonBar.setLayout(new GridBagLayout());
                ((GridBagLayout)buttonBar.getLayout()).columnWidths = new int[] {0, 80};
                ((GridBagLayout)buttonBar.getLayout()).columnWeights = new double[] {1.0, 0.0};

                //---- loginButton ----
                loginButton.setText("OK");
                loginButton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        loginButtonMouseClicked(e);
                    }
                });
                buttonBar.add(loginButton, new GridBagConstraints(1, 0, 1, 1, 0.0, 0.0,
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
    private JLabel label2;
    private JLabel label3;
    private JTextField ipTextField;
    private JLabel label4;
    private JTextField portTextField;
    private JPanel buttonBar;
    private JButton loginButton;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
