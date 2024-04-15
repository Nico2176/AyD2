package vista;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.SystemColor;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class VentanaEstadisticas extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblClientesAtendidos;
    private JLabel lblSegsAtendiendo;
    private JLabel lblSegundsoDesocupado;
    private JLabel lblSegundosTotales;
    private JLabel lblPromedioAtencion;
    private JLabel lblPromedioAtendidosxHora;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaEstadisticas frame = new VentanaEstadisticas();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public VentanaEstadisticas() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(6, 0, 0, 0));

        JPanel panel_7 = new JPanel();
        panel_7.setBackground(SystemColor.inactiveCaption);
        panel.add(panel_7);

        JLabel lblNewLabel = new JLabel("Clientes atendidos:");
        lblNewLabel.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_7.add(lblNewLabel);

        lblClientesAtendidos = new JLabel("0");
        lblClientesAtendidos.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_7.add(lblClientesAtendidos);

        JPanel panel_2 = new JPanel();
        panel_2.setBackground(SystemColor.inactiveCaption);
        panel.add(panel_2);

        JLabel lblSegundosAtendiendo = new JLabel("Segundos atendiendo:");
        lblSegundosAtendiendo.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_2.add(lblSegundosAtendiendo);

        lblSegsAtendiendo = new JLabel("0");
        lblSegsAtendiendo.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_2.add(lblSegsAtendiendo);

        JPanel panel_4 = new JPanel();
        panel_4.setBackground(SystemColor.inactiveCaption);
        panel.add(panel_4);

        JLabel lblNewLabel_1 = new JLabel("Segundos desocupado:");
        lblNewLabel_1.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_4.add(lblNewLabel_1);

        lblSegundsoDesocupado = new JLabel("0");
        lblSegundsoDesocupado.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_4.add(lblSegundsoDesocupado);

        JPanel panel_3 = new JPanel();
        panel_3.setBackground(SystemColor.inactiveCaption);
        panel.add(panel_3);

        JLabel lblNewLabel_2 = new JLabel("Segundos totales:");
        lblNewLabel_2.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_3.add(lblNewLabel_2);

        lblSegundosTotales = new JLabel("0");
        lblSegundosTotales.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_3.add(lblSegundosTotales);

        JPanel panel_5 = new JPanel();
        panel_5.setBackground(SystemColor.inactiveCaption);
        panel.add(panel_5);

        JLabel lblNewLabel_3 = new JLabel("Promedio tiempo de atención:");
        lblNewLabel_3.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_5.add(lblNewLabel_3);

        lblPromedioAtencion = new JLabel("0");
        lblPromedioAtencion.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_5.add(lblPromedioAtencion);

        JPanel panel_6 = new JPanel();
        panel_6.setBackground(SystemColor.inactiveCaption);
        panel.add(panel_6);

        JLabel lblNewLabel_4 = new JLabel("Promedio clientes atendidos por hora:");
        lblNewLabel_4.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_6.add(lblNewLabel_4);

        lblPromedioAtendidosxHora = new JLabel("0");
        lblPromedioAtendidosxHora.setFont(new Font("Microsoft YaHei", Font.PLAIN, 13));
        panel_6.add(lblPromedioAtendidosxHora);

        JPanel panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.activeCaption);
        contentPane.add(panel_1, BorderLayout.NORTH);

        JPanel panel_8 = new JPanel();
        panel_8.setBackground(SystemColor.activeCaption);
        contentPane.add(panel_8, BorderLayout.SOUTH);

        JPanel panel_9 = new JPanel();
        panel_9.setBackground(SystemColor.activeCaption);
        contentPane.add(panel_9, BorderLayout.WEST);

        JPanel panel_10 = new JPanel();
        panel_10.setBackground(SystemColor.activeCaption);
        contentPane.add(panel_10, BorderLayout.EAST);
    }


public void actualizarEstadisticas(int clientesAtendidos, int segundosAtendiendo, int segundosDesocupado, int segundosTotales, float promedioAtencion, float promedioAtendidosPorHora) {
	lblClientesAtendidos.setText(Integer.toString(clientesAtendidos));
	lblSegsAtendiendo.setText(Integer.toString(segundosAtendiendo));
	lblSegundsoDesocupado.setText(Integer.toString(segundosDesocupado));
	lblSegundosTotales.setText(Integer.toString(segundosTotales));
	lblPromedioAtencion.setText(Float.toString(promedioAtencion));
	lblPromedioAtendidosxHora.setText(Float.toString(promedioAtendidosPorHora));
}
}