package vista;

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;

public class VentanaRegistro extends JFrame implements Ivista {
    private JTextField textField;
    private JButton btnNewButton;
    private JButton btnNumero0;
    private JButton btnNumero1;
    private JButton btnNumero2;
    private JButton btnNumero3;
    private JButton btnNumero4;
    private JButton btnNumero5;
    private JButton btnNumero6;
    private JButton btnNumero7;
    private JButton btnNumero8;
    private JButton btnNumero9;
    private JButton btnBorrar;

    public VentanaRegistro() {
        // Configurar la ventana
        setTitle("Registro de usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 700);
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
        panel_3.setLayout(new GridLayout(0, 1, 5, 0));

        JPanel panel_21 = new JPanel();
        panel_21.setBackground(SystemColor.inactiveCaption);
        panel_3.add(panel_21);

        JPanel panel_22 = new JPanel();
        panel_22.setBackground(SystemColor.inactiveCaption);
        panel_3.add(panel_22);
        panel_22.setLayout(new BorderLayout(0, 0));

        btnNumero1 = new JButton("1");
        btnNumero1.setFont(new Font("Franklin Gothic Pro Heavy", Font.BOLD, 26));
        panel_22.add(btnNumero1);

        JPanel panel_23 = new JPanel();
        panel_23.setBackground(SystemColor.inactiveCaption);
        panel_3.add(panel_23);
        panel_23.setLayout(new BorderLayout(0, 0));

        btnNumero4 = new JButton("4");
        btnNumero4.setFont(new Font("Franklin Gothic Pro Heavy", Font.PLAIN, 26));
        panel_23.add(btnNumero4);

        JPanel panel_24 = new JPanel();
        panel_24.setBackground(SystemColor.inactiveCaption);
        panel_3.add(panel_24);
        panel_24.setLayout(new BorderLayout(0, 0));

        btnNumero7 = new JButton("7");
        btnNumero7.setFont(new Font("Franklin Gothic Pro Heavy", Font.PLAIN, 26));
        panel_24.add(btnNumero7);

        JPanel panel_25 = new JPanel();
        panel_25.setBackground(SystemColor.inactiveCaption);
        panel_3.add(panel_25);

        JPanel panel_4 = new JPanel();
        panel.add(panel_4);
        panel_4.setLayout(new GridLayout(5, 1, 0, 0));

        JPanel panel_6 = new JPanel();
        panel_6.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_6);
        panel_6.setLayout(new GridLayout(6, 1, 0, 0));

        JPanel panel_14 = new JPanel();
        panel_14.setBackground(SystemColor.inactiveCaption);
        panel_6.add(panel_14);

        JLabel lblNewLabel = new JLabel("Ingresa tu DNI ");
        panel_14.add(lblNewLabel);
        lblNewLabel.setFont(new Font("Franklin Gothic Pro", Font.PLAIN, 16));

        JPanel panel_13 = new JPanel();
        panel_13.setBackground(SystemColor.inactiveCaption);
        panel_6.add(panel_13);

        JLabel lblNewLabel_1 = new JLabel("");
        lblNewLabel_1.setFont(new Font("Franklin Gothic Pro", Font.PLAIN, 16));
        panel_13.add(lblNewLabel_1);

        JPanel panel_17 = new JPanel();
        panel_17.setBackground(SystemColor.inactiveCaption);
        panel_6.add(panel_17);
        panel_17.setLayout(new BorderLayout(0, 0));

        textField = new JTextField();
        panel_17.add(textField);
        textField.setColumns(10);

        JPanel panel_18 = new JPanel();
        panel_18.setBackground(SystemColor.inactiveCaption);
        panel_6.add(panel_18);
        panel_18.setLayout(new BorderLayout(0, 0));

        JPanel panel_19 = new JPanel();
        panel_19.setBackground(SystemColor.inactiveCaption);
        panel_6.add(panel_19);

        JPanel panel_20 = new JPanel();
        panel_20.setBackground(SystemColor.inactiveCaption);
        panel_6.add(panel_20);

        JPanel panel_7 = new JPanel();
        panel_7.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_7);
        panel_7.setLayout(new BorderLayout(0, 0));

        btnNumero2 = new JButton("2");
        btnNumero2.setFont(new Font("Franklin Gothic Pro Heavy", Font.BOLD, 26));
        panel_7.add(btnNumero2);

        JPanel panel_10 = new JPanel();
        panel_10.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_10);
        panel_10.setLayout(new BorderLayout(0, 0));

        btnNumero5 = new JButton("5");
        btnNumero5.setFont(new Font("Franklin Gothic Pro Heavy", Font.PLAIN, 26));
        panel_10.add(btnNumero5);

        JPanel panel_9 = new JPanel();
        panel_9.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_9);
        panel_9.setLayout(new GridLayout(1, 1, 0, 0));

        btnNumero8 = new JButton("8");
        btnNumero8.setFont(new Font("Franklin Gothic Pro Heavy", Font.PLAIN, 26));
        panel_9.add(btnNumero8);

        JPanel panel_31 = new JPanel();
        panel_4.add(panel_31);
        panel_31.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel panel_8 = new JPanel();
        panel_31.add(panel_8);
        panel_8.setLayout(new BorderLayout(0, 0));

        btnNumero0 = new JButton("0");
        btnNumero0.setFont(new Font("Franklin Gothic Pro Heavy", Font.PLAIN, 26));
        panel_8.add(btnNumero0);

        JPanel panel_15 = new JPanel();
        panel_31.add(panel_15);
        panel_15.setLayout(new GridLayout(2, 1, 0, 0));

        JPanel panel_16 = new JPanel();
        panel_16.setBackground(SystemColor.inactiveCaption);
        panel_15.add(panel_16);

        JPanel panel_32 = new JPanel();
        panel_32.setBackground(SystemColor.inactiveCaption);
        panel_15.add(panel_32);

        btnNewButton = new JButton("Registrarse");
        panel_32.add(btnNewButton);
        btnNewButton.setFont(new Font("Franklin Gothic Medium", Font.PLAIN, 16));

        JPanel panel_5 = new JPanel();
        panel_5.setBackground(SystemColor.inactiveCaption);
        panel.add(panel_5);
        panel_5.setLayout(new GridLayout(5, 1, 0, 0));

        JPanel panel_26 = new JPanel();
        panel_26.setBackground(SystemColor.inactiveCaption);
        panel_5.add(panel_26);
        panel_26.setLayout(new GridLayout(3, 0, 0, 0));

        JPanel panel_33 = new JPanel();
        panel_33.setBackground(SystemColor.inactiveCaption);
        panel_26.add(panel_33);

        JPanel panel_35 = new JPanel();
        panel_35.setBackground(SystemColor.inactiveCaption);
        panel_26.add(panel_35);

        JPanel panel_37 = new JPanel();
        panel_26.add(panel_37);
        panel_37.setLayout(new BorderLayout(0, 0));

        btnBorrar = new JButton("←");
        btnBorrar.setActionCommand("Borrar");
        btnBorrar.setFont(new Font("Franklin Gothic Pro", Font.BOLD, 26));
        panel_37.add(btnBorrar);

        JPanel panel_27 = new JPanel();
        panel_27.setBackground(SystemColor.inactiveCaption);
        panel_5.add(panel_27);
        panel_27.setLayout(new BorderLayout(0, 0));

        btnNumero3 = new JButton("3");
        btnNumero3.setFont(new Font("Franklin Gothic Pro Heavy", Font.BOLD, 26));
        panel_27.add(btnNumero3);

        JPanel panel_28 = new JPanel();
        panel_28.setBackground(SystemColor.inactiveCaption);
        panel_5.add(panel_28);
        panel_28.setLayout(new BorderLayout(0, 0));

        btnNumero6 = new JButton("6");
        btnNumero6.setFont(new Font("Franklin Gothic Pro", Font.BOLD, 26));
        panel_28.add(btnNumero6);

        JPanel panel_29 = new JPanel();
        panel_29.setBackground(SystemColor.inactiveCaption);
        panel_5.add(panel_29);
        panel_29.setLayout(new BorderLayout(0, 0));

        btnNumero9 = new JButton("9");
        btnNumero9.setFont(new Font("Franklin Gothic Pro Heavy", Font.PLAIN, 26));
        panel_29.add(btnNumero9);

        JPanel panel_30 = new JPanel();
        panel_30.setBackground(SystemColor.inactiveCaption);
        panel_5.add(panel_30);

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
    
    

    public JTextField getTextField() {
		return textField;
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
        this.btnNumero0.addActionListener(actionListener);
        this.btnNumero1.addActionListener(actionListener);
        this.btnNumero2.addActionListener(actionListener);
        this.btnNumero3.addActionListener(actionListener);
        this.btnNumero4.addActionListener(actionListener);
        this.btnNumero5.addActionListener(actionListener);
        this.btnNumero6.addActionListener(actionListener);
        this.btnNumero7.addActionListener(actionListener);
        this.btnNumero8.addActionListener(actionListener);
        this.btnNumero9.addActionListener(actionListener);
        this.btnBorrar.addActionListener(actionListener);
    }
}