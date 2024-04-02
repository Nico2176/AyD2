package vista;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;

public class VentanaServer extends JFrame implements Ivista {

    private ActionListener actionListener;
    private JPanel panel;
    private JPanel panel_1;
    private JLabel lblNewLabel_1;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_4;
    private JLabel lblNewLabel;

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
        System.out.println("INICIALIZANDO VENTANA SERVER");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        
        panel = new JPanel();
        panel.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel, BorderLayout.NORTH);

        panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_1, BorderLayout.SOUTH);

        lblNewLabel_1 = new JLabel("Usuarios conectados al servidor:");
        lblNewLabel_1.setFont(new Font("Courier New", Font.PLAIN, 18));
        panel_1.add(lblNewLabel_1);

        panel_2 = new JPanel();
        panel_2.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_2, BorderLayout.WEST);

        panel_3 = new JPanel();
        panel_3.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_3, BorderLayout.EAST);

        panel_4 = new JPanel();
        panel_4.setBackground(SystemColor.inactiveCaption);
        getContentPane().add(panel_4, BorderLayout.CENTER);

        lblNewLabel = new JLabel("Estado del servidor:");
        lblNewLabel.setFont(new Font("Corbel Light", Font.BOLD, 24));
        panel_4.add(lblNewLabel);
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
        this.actionListener = actionListener;
    }

}