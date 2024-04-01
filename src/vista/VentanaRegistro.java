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
import java.awt.Font;


public class VentanaRegistro extends JFrame {
	private JTextField textField;
    public VentanaRegistro() {
        // Configurar la ventana
        setTitle("Registro de usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);
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
        
        JLabel lblNewLabel = new JLabel("Ingresa tu DNI para registrarte");
        lblNewLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 12));
        panel_6.add(lblNewLabel);
        
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
        
        JButton btnNewButton = new JButton("Registrarse");
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
}
