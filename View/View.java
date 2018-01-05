package View;
import Model.Model;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class View extends JFrame {
/* Principal classe que além de gerar a interface chama os metodos principais
    de controle */
    Model model;

    public View() throws IOException {
        this.model = new Model();
        model.findPort();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 636, 365);
        model.contentPane = new JPanel();
        model.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(model.contentPane);
        model.contentPane.setLayout(null);
        model.timer = new Thread(new Control.Control());
        model.timer.start();
        model.timer.interrupt();

        /*Botao de conectar*/
        model.btnNewButton = new JButton("Conectar");
        model.inputConfig();//Configurar entrada das portas
        model.btnNewButton.setBounds(38, 63, 190, 23);
        model.contentPane.add(model.btnNewButton);

        /*Botão de desconectar*/
        model.btnNewButton_1 = new JButton("Desconectar");
        model.btnNewButton_1.setEnabled(false);
        model.btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                model.timer.interrupt();
                model.porta_serial.close();
                model.btnNewButton_1.setEnabled(false);
                model.btnNewButton_SQL.setEnabled(false);
                model.btnNewButton.setEnabled(true);
                model.comboPorta.setEnabled(true);
                model.textFieldCertificado.setEnabled(true);
                model.btnNewButton_cSQL.setEnabled(false);
                model.sendData = false;
            }
        });
        model.btnNewButton_1.setBounds(38, 93, 190, 23);
        model.contentPane.add(model.btnNewButton_1);

        /*Botao de começar registro*/
        model.btnNewButton_SQL = new JButton("Iniciar Registro");
        model.btnNewButton_SQL.setEnabled(false);
        model.btnNewButton_SQL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                model.btnNewButton_SQL.setEnabled(false);
                model.btnNewButton_cSQL.setEnabled(true);
                model.textFieldCertificado.setEnabled(false);
                model.textFieldHora.setEnabled(false);
                model.textFieldMinutes.setEnabled(false);
                model.textFieldSeconds.setEnabled(false);
                model.identificacao = model.textFieldCertificado.getText();//recebe o valor no campo de texto
                model.sendData = true;
                
                model.runTime = true;
               
                
                
            }
        });
        model.btnNewButton_SQL.setBounds(305, 63, 128, 23);
        model.contentPane.add(model.btnNewButton_SQL);

        /*Botao de parar registro*/
        model.btnNewButton_cSQL = new JButton("Parar Registro");
        model.btnNewButton_cSQL.setEnabled(false);
        model.btnNewButton_cSQL.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                model.btnNewButton_cSQL.setEnabled(false);
                model.btnNewButton_SQL.setEnabled(true);
                model.textFieldCertificado.setEnabled(true);
                model.textFieldHora.setEnabled(true);
                model.textFieldMinutes.setEnabled(true);
                model.textFieldSeconds.setEnabled(true);
                
                model.sendData = false;
                model.runTime = false;
            }
        });
        model.btnNewButton_cSQL.setBounds(305, 93, 128, 23);
        model.contentPane.add(model.btnNewButton_cSQL);

        /*ComboBox das portas*/
        model.comboPorta.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent ie) {
                if (ie.getStateChange() == ItemEvent.SELECTED) {
                    model.com = ie.getItem().toString();
                    System.out.println(model.com);
                }
            }
        });
        model.comboPorta.setBounds(38, 32, 190, 23);
        model.contentPane.add(model.comboPorta);
        
        /*Textfield do numero do certificado*/
        model.textFieldCertificado = new JTextField();
        model.textFieldCertificado.setBounds(305, 32, 128, 23);
        model.contentPane.add(model.textFieldCertificado);
        model.textFieldCertificado.setColumns(10);

        /*Label de combobox */
        JLabel lblcomboBox = new JLabel("Selecione a porta do Termômetro");
        lblcomboBox.setBounds(37, 9, 190, 23);
        model.contentPane.add(lblcomboBox);

        /*Label "Identicicacao"*/
        JLabel lblCertificado = new JLabel("Identificação");
        lblCertificado.setBounds(306, 9, 128, 23);
        model.contentPane.add(lblCertificado);

        /*Label "Termometro 1" */
        model.lblNewLabelTemp1 = new JLabel("Termômetro 1");
        model.lblNewLabelTemp1.setBounds(37, 234, 128, 24);
        model.lblNewLabelTemp1.setFont(new java.awt.Font("Arial", 0, 20));
        model.lblNewLabelTemp1.setForeground(Color.blue);
        model.contentPane.add(model.lblNewLabelTemp1);

        /*Label "Termometro 2" */
        model.lblNewLabelTemp2 = new JLabel("Termômetro 2");
        model.lblNewLabelTemp2.setBounds(37, 264, 128, 24);
        model.lblNewLabelTemp2.setFont(new java.awt.Font("Arial", 0, 20));
        model.lblNewLabelTemp2.setForeground(Color.blue);
        model.contentPane.add(model.lblNewLabelTemp2);
        
    }

    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(Color.blue);
        g.fillRect(38, 250 - model.temperatura, 20, model.temperatura);
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
