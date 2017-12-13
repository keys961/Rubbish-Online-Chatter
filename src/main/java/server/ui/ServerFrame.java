/*
 * Created by JFormDesigner on Mon Dec 11 13:38:35 CST 2017
 */

package server.ui;

import java.awt.event.*;
import server.Server;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.text.NumberFormat;
import javax.swing.*;

/**
 * @author unknown
 */
public class ServerFrame extends JFrame
{
    private Server server;

    private Thread serverThread;

    public ServerFrame()
    {
        initComponents();
    }

    private void startButtonMouseClicked(MouseEvent e)
    {
        int port = Integer.parseInt(portTextField.getText());
        try
        {
            server = new Server(port, resultTextArea);
        }
        catch (IOException ex)
        {
            resultTextArea.setText("Start server error!\n");
            return;
        }

        serverThread = new Thread(server);
        serverThread.start();
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        resultTextArea.setText("Start server success! IP: " + server.getServerSocket().getLocalSocketAddress()
                + " , port: " + server.getServerSocket().getLocalPort() + "\n");
    }

    private void stopButtonMouseClicked(MouseEvent e)
    {
        serverThread.interrupt();
        ServerSocket socket = server.getServerSocket();
        try
        {
            socket.close();
        }
        catch (IOException e1)
        {
            resultTextArea.append("Close server error!\n");
            return;
        }

        resultTextArea.append("Close server success!\n");
        stopButton.setEnabled(false);
        startButton.setEnabled(true);
    }

    private void initComponents()
    {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - Jintao Ye
        panel1 = new JPanel();
        label1 = new JLabel();
        portTextField = new JFormattedTextField();
        startButton = new JButton();
        stopButton = new JButton();
        scrollPane1 = new JScrollPane();
        resultTextArea = new JTextArea();

        //======== this ========
        setTitle("Server");
        Container contentPane = getContentPane();
        contentPane.setLayout(new GridBagLayout());
        ((GridBagLayout)contentPane.getLayout()).columnWidths = new int[] {0, 0};
        ((GridBagLayout)contentPane.getLayout()).rowHeights = new int[] {0, 312, 0};
        ((GridBagLayout)contentPane.getLayout()).columnWeights = new double[] {1.0, 1.0E-4};
        ((GridBagLayout)contentPane.getLayout()).rowWeights = new double[] {1.0, 1.0, 1.0E-4};

        //======== panel1 ========
        {

//            // JFormDesigner evaluation mark
//            panel1.setBorder(new javax.swing.border.CompoundBorder(
//                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
//                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
//                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
//                    java.awt.Color.red), panel1.getBorder())); panel1.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});

            panel1.setLayout(new GridLayout());

            //---- label1 ----
            label1.setText("Listening Port:");
            panel1.add(label1);
            panel1.add(portTextField);

            //---- startButton ----
            startButton.setText("Start");
            startButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    startButtonMouseClicked(e);
                }
            });
            panel1.add(startButton);

            //---- stopButton ----
            stopButton.setText("Stop");
            stopButton.setEnabled(false);
            stopButton.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    stopButtonMouseClicked(e);
                }
            });
            panel1.add(stopButton);
        }
        contentPane.add(panel1, new GridBagConstraints(0, 0, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));

        //======== scrollPane1 ========
        {
            scrollPane1.setViewportView(resultTextArea);
        }
        contentPane.add(scrollPane1, new GridBagConstraints(0, 1, 1, 1, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 0, 0), 0, 0));
        pack();
        setLocationRelativeTo(getOwner());
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Jintao Ye
    private JPanel panel1;
    private JLabel label1;
    private JFormattedTextField portTextField;
    private JButton startButton;
    private JButton stopButton;
    private JScrollPane scrollPane1;
    private JTextArea resultTextArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
