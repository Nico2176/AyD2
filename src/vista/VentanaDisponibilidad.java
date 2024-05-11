package vista;

import java.awt.EventQueue;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class VentanaDisponibilidad extends JFrame {

    private JPanel contentPane;
    private JList<String> list1;
    private JList<String> list2;
    private DefaultListModel<String> model1;
    private DefaultListModel<String> model2;

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                VentanaDisponibilidad window = new VentanaDisponibilidad();
                window.setVisible(true);
                window.escribirLista1("Elemento 1");
                window.escribirLista1("Elemento 2");
                window.escribirLista2("Elemento A");
                window.escribirLista2("Elemento B");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public VentanaDisponibilidad() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        JPanel panel = new JPanel();
        contentPane.add(panel, BorderLayout.CENTER);
        panel.setLayout(new GridLayout(1, 2, 0, 0));

        JPanel panel_1 = new JPanel();
        panel.add(panel_1);
        panel_1.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        panel_1.add(scrollPane, BorderLayout.CENTER);

        model1 = new DefaultListModel<>();
        list1 = new JList<>(model1);
        scrollPane.setViewportView(list1);

        JPanel panel_2 = new JPanel();
        panel.add(panel_2);
        panel_2.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane_1 = new JScrollPane();
        panel_2.add(scrollPane_1, BorderLayout.CENTER);

        model2 = new DefaultListModel<>();
        list2 = new JList<>(model2);
        scrollPane_1.setViewportView(list2);
    }

    public void escribirLista1(String cadena) {
        model1.addElement(cadena);
    }

    public void escribirLista2(String cadena) {
        model2.addElement(cadena);
    }
}