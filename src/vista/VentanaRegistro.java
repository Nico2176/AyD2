package vista;

import java.awt.EventQueue;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.Font;


public class VentanaRegistro extends JFrame implements Ivista {
	private JTextField textField;
	private ActionListener actionListener;
	private JButton btnNewButton;
	
    public JTextField getTextField() {
		return textField;
	}

	public void setTextField(JTextField textField) {
		this.textField = textField;
	}

	public JButton getBtnNewButton() {
		return btnNewButton;
	}

	public void setBtnNewButton(JButton btnNewButton) {
		this.btnNewButton = btnNewButton;
	}

	public VentanaRegistro() {
        // Configurar la ventana
        setTitle("Registro de usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 350);
        setLocationRelativeTo(null);
        getContentPane().setLayout(new BorderLayout(0, 0));
        
        JPanel panel_2 = new JPanel();
        panel_2.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_2, BorderLayout.NORTH);
        
        JPanel panel = new JPanel();
        getContentPane().add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(0, 3, 0, 0));
        
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(SystemColor.inactiveCaption);
        panel.add(panel_3);
        
        JPanel panel_4 = new JPanel();
        panel.add(panel_4);
        panel_4.setLayout(new GridLayout(4, 1, 0, 0));
        
        JPanel panel_6 = new JPanel();
        panel_6.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_6);
        panel_6.setLayout(new GridLayout(2, 1, 0, 0));
        
        JPanel panel_14 = new JPanel();
        panel_14.setBackground(SystemColor.inactiveCaption);
        panel_6.add(panel_14);
        
        JLabel lblNewLabel = new JLabel("Ingresa tu DNI ");
        panel_14.add(lblNewLabel);
        lblNewLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        
        JPanel panel_13 = new JPanel();
        panel_13.setBackground(SystemColor.inactiveCaption);
        panel_6.add(panel_13);
        
        JLabel lblNewLabel_1 = new JLabel("(sin puntos)");
        lblNewLabel_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        panel_13.add(lblNewLabel_1);
        
        JPanel panel_7 = new JPanel();
        panel_7.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_7);
        
        JPanel panel_10 = new JPanel();
        panel_10.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_10);
        
        JPanel panel_8 = new JPanel();
        panel_10.add(panel_8);
        panel_8.setLayout(new BorderLayout(0, 0));
        
        textField = new JTextField();
        panel_8.add(textField);
        textField.setColumns(10);
        
        JPanel panel_9 = new JPanel();
        panel_9.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_9);
        
        btnNewButton = new JButton("Registrarse");
        btnNewButton.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 12));
        panel_9.add(btnNewButton);
        
        JPanel panel_5 = new JPanel();
        panel_5.setBackground(SystemColor.inactiveCaption);
        panel.add(panel_5);
        
        JPanel panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_1, BorderLayout.SOUTH);
        
        JPanel panel_11 = new JPanel();
        panel_11.setBackground(SystemColor.activeCaption);
        panel_11.setForeground(SystemColor.inactiveCaption);
        getContentPane().add(panel_11, BorderLayout.WEST);
        
        JPanel panel_12 = new JPanel();
        panel_12.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_12, BorderLayout.EAST);
    }

    public static void main(String[] args) {
        // Crear y mostrar la ventana
        SwingUtilities.invokeLater(() -> {
            VentanaRegistro ventana = new VentanaRegistro();
            ventana.setVisible(true);
        });
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
		this.actionListener=actionListener;
		
	}
}
