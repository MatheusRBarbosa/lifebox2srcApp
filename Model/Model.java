/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import gnu.io.CommPortIdentifier;
import gnu.io.NoSuchPortException;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.UnsupportedCommOperationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author mathe
 */
public class Model extends JFrame {
    
    /*Classe que guarda as principais variaveis, que podem ser modificadas em
    qualquer outra classe*/

    public JPanel contentPane;
    public JTextField textFieldCOM, textFieldCertificado, textFieldHora, 
            textFieldMinutes, textFieldSeconds;
    public Enumeration portas_livres = null;
    public CommPortIdentifier portAux, port = null;
    public SerialPort porta_serial = null;
    public static OutputStream out = null;
    public static InputStream in = null;
    public int temperatura = 10;
    public Thread timer;
    public static JLabel lblNewLabelTemp1, lblNewLabelTemp2;
    public JButton btnNewButton, btnNewButton_1, btnNewButton_SQL, btnNewButton_cSQL;
    
    public static String identificacao;
    public static boolean sendData = false;

    public byte[] buffer = new byte[20];

    private int bufferAux;
    private static String portAns = "";

    public static JComboBox comboPorta = new JComboBox();
    public static String com = null;
    
    public static Thread countDown = new Thread();
    public static int horas, minutos, segundos;
    public static boolean runTime = false;
    
    /*Metodo para Atualizar os valores na View*/
    public void setLabelTemp(String temp1, String temp2) {
        lblNewLabelTemp1.setText("T1: " + temp1 + " ºC");
        lblNewLabelTemp2.setText("T2: " + temp2 + " ºC");
        repaint();
    }

    /*Metodo para achar todas as portas COM que estão sendo usadas na maquina
    e adiciona no combobox*/
    public void findPort() {
        portas_livres = CommPortIdentifier.getPortIdentifiers();
        while (portas_livres.hasMoreElements()) {
            portAux = (CommPortIdentifier) portas_livres.nextElement();
            if(com == null){
                com = portAux.getName();
            }
            this.comboPorta.addItem(portAux.getName());
        }
    }
    
    /*Metodo para configurar e definir o canal de entrada e saida (in e out)*/
    public void inputConfig() {
        this.btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int aux = 0;
                try {
                    try {
                        port = (CommPortIdentifier) CommPortIdentifier.getPortIdentifier(com);
                    } catch (NoSuchPortException ex) {
                        Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    porta_serial = (SerialPort) port.open("porta serial", 2000);
                    int baudRate = 9600; // 9600bps
                    //Configuração do arduino
                    porta_serial.setSerialPortParams(
                            baudRate,
                            SerialPort.DATABITS_8,
                            SerialPort.STOPBITS_1,
                            SerialPort.PARITY_NONE);
                    porta_serial.setDTR(true);
                    try {
                        out = porta_serial.getOutputStream();//saida do java
                        in = porta_serial.getInputStream(); // entrada do java
                    } catch (IOException ex) {
                        Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    btnNewButton_1.setEnabled(true);
                    btnNewButton_SQL.setEnabled(true);
                    btnNewButton.setEnabled(false);
                    comboPorta.setEnabled(false);

                    timer.resume(); //esta variavel iniciará o metodo ImplementoRunnable
                } catch (PortInUseException | UnsupportedCommOperationException e1) {
                }
            }

        });
    }
}
