package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Font;

public class VentanaPersonal extends JFrame implements Ivista {

    private JButton btnSiguiente;
    private JButton btnFinalizarTurno;
    private ActionListener actionListener;

    private JFrame frame;
    private JPanel panel;
    private JPanel panel_1;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_4;
    private JPanel panel_5;
    private JPanel panel_6;
    private JPanel panel_7;
    private JPanel panel_8;
    private JLabel lblNewLabel;
    private JLabel lblNewLabel_1;
    private JLabel lblNewLabel_2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaPersonal window = new VentanaPersonal();
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
    public VentanaPersonal() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        
        panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel, BorderLayout.NORTH);
        
        panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_1, BorderLayout.SOUTH);
        
        btnFinalizarTurno = new JButton("Finalizar turno");
        btnFinalizarTurno.setActionCommand("FinalizarTurno");
        btnFinalizarTurno.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        panel_1.add(btnFinalizarTurno);
        
        btnSiguiente = new JButton("Siguiente");
        btnSiguiente.setFont(new Font("Comic Sans MS", Font.PLAIN, 11));
        panel_1.add(btnSiguiente);
        
        panel_2 = new JPanel();
        panel_2.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_2, BorderLayout.WEST);
        
        panel_3 = new JPanel();
        panel_3.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_3, BorderLayout.EAST);
        
        panel_4 = new JPanel();
        panel_4.setBackground(SystemColor.inactiveCaption);
        getContentPane().add(panel_4, BorderLayout.CENTER);
        panel_4.setLayout(new BorderLayout(0, 0));
        
        panel_5 = new JPanel();
        panel_5.setBorder(new LineBorder(new Color(0, 0, 0), 0));
        panel_5.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_5, BorderLayout.NORTH);
        panel_5.setLayout(new GridLayout(0, 2, 0, 0));
        
        panel_7 = new JPanel();
        panel_7.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_7.setBackground(SystemColor.inactiveCaption);
        panel_5.add(panel_7);
        
        lblNewLabel = new JLabel("BOX:");
        lblNewLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        panel_7.add(lblNewLabel);
        
        panel_8 = new JPanel();
        panel_8.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_8.setBackground(SystemColor.inactiveCaption);
        panel_5.add(panel_8);
        
        lblNewLabel_2 = new JLabel("Actualmente atendiendo a:");
        lblNewLabel_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 14));
        panel_8.add(lblNewLabel_2);
        
        panel_6 = new JPanel();
        panel_6.setBorder(new LineBorder(new Color(0, 0, 0)));
        panel_6.setBackground(SystemColor.inactiveCaption);
        panel_4.add(panel_6, BorderLayout.CENTER);
        panel_6.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        lblNewLabel_1 = new JLabel("");
        panel_6.add(lblNewLabel_1);
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
        this.btnSiguiente.addActionListener(actionListener);
        this.btnFinalizarTurno.addActionListener(actionListener);
        this.actionListener=actionListener;
    }

}