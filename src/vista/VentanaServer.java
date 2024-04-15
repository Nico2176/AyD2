package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.BoxLayout;

public class VentanaServer extends JFrame implements Ivista {

    private ActionListener actionListener;
    private JPanel panel;
    private JPanel panel_1;
    private JLabel lblUsuariosConectados_1;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_4;
    private JPanel panel_5;
    private JPanel panel_6;
    private JPanel panel_7;
    private JPanel panel_8;
    private JPanel panel_9;
    private JLabel lblEstadoServer;
    private JButton btnNewButton;
    private JLabel lblOnOff;
    private int x=0;
    private JButton btnNewButton_1;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaServer window = new VentanaServer();
                    window.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public VentanaServer() {
    	this.setTitle(("Servidor"));
        System.out.println("INICIALIZANDO VENTANA SERVER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        
        panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel, BorderLayout.NORTH);
        panel.setLayout(new CardLayout(0, 0));

        panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_1, BorderLayout.SOUTH);

        lblUsuariosConectados_1 = new JLabel("Usuarios conectados al servidor: "+ x);
        lblUsuariosConectados_1.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel_1.add(lblUsuariosConectados_1);

        panel_2 = new JPanel();
        panel_2.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_2, BorderLayout.WEST);

        panel_3 = new JPanel();
        panel_3.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_3, BorderLayout.EAST);

        panel_4 = new JPanel();
        panel_4.setBackground(SystemColor.inactiveCaption);
        getContentPane().add(panel_4, BorderLayout.CENTER);
        panel_4.setLayout(new GridLayout(5, 1, 0, 0));
        
        panel_5 = new JPanel();
        panel_5.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_5);
        
        lblEstadoServer = new JLabel("Estado del servidor:");
        lblEstadoServer.setFont(new Font("Corbel Light", Font.BOLD, 24));
        panel_5.add(lblEstadoServer);
        
        panel_6 = new JPanel();
        panel_6.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_6);
        
        lblOnOff = new JLabel("OFF");
        lblOnOff.setForeground(Color.RED);
        lblOnOff.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 23));
        panel_6.add(lblOnOff);
        
        panel_7 = new JPanel();
        panel_7.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_7);
        
        panel_8 = new JPanel();
        panel_8.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_8);
        
        panel_9 = new JPanel();
        panel_9.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_9);
        
        btnNewButton = new JButton("Iniciar Server");
        btnNewButton.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        panel_9.add(btnNewButton);
        
        btnNewButton_1 = new JButton("Estadísticas");
        btnNewButton_1.setEnabled(false);
        btnNewButton_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        btnNewButton_1.setActionCommand("Estadisticas");
        panel_9.add(btnNewButton_1);
    }
    
    
    public void serverON() {
    	System.out.println("CAMIANDO A ON");
    	this.lblOnOff.setText("ON");
    	lblOnOff.setForeground(Color.GREEN);
        lblOnOff.setFont(new Font("Microsoft Sans Serif", Font.BOLD, 23));
        this.btnNewButton.setEnabled(false);
    }

    
    
    
    public JButton getBtnNewButton_1() {
		return btnNewButton_1;
	}

	public void alta() {
    	this.lblUsuariosConectados_1.setText("Usuarios conectados al servidor: "+ ++x);
    }
    
    public void baja() {
    	this.lblUsuariosConectados_1.setText("Usuarios conectados al servidor: "+ --x);
    }
    
    
    public JButton getBtnNewButton() {
		return btnNewButton;
	}

	public void setBtnNewButton(JButton btnNewButton) {
		this.btnNewButton = btnNewButton;
	}

	public JLabel getLblOnOff() {
		return lblOnOff;
	}

	public void setLblOnOff(JLabel lblOnOff) {
		this.lblOnOff = lblOnOff;
	}

	public JLabel getLblUsuariosConectados() {
		return lblUsuariosConectados_1;
	}

	public void setLblUsuariosConectados(JLabel lblNewLabel_1) {
		this.lblUsuariosConectados_1 = lblNewLabel_1;
	}

	public JLabel getLblEstadoServer() {
		return lblEstadoServer;
	}

	public void setLblEstadoServer(JLabel lblNewLabel) {
		this.lblEstadoServer = lblNewLabel;
	}

	@Override
    public void cerrar() {
        this.dispose();
    }

    @Override
    public void mostrar() {
        this.setVisible(true);
    }

    @Override
    public void setActionListener(ActionListener actionListener) {
    	this.btnNewButton.addActionListener(actionListener);
    	this.btnNewButton_1.addActionListener(actionListener);
        this.actionListener = actionListener;
    }

}