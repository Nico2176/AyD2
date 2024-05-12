package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import modelo.Cliente;

import javax.swing.JScrollPane;

import javax.swing.JList;
import java.awt.Color;

public class VentanaMonitor extends JFrame implements Ivista{

    private ActionListener actionListener;
    private JPanel panel;
    private JPanel panel_5;
    private JLabel lblSiguiente;
    private JPanel panel_6;
    private JLabel lblBox;
    private JPanel panel_1;
    private JPanel panel_2;
    private JPanel panel_3;
    private JPanel panel_4;
    private JPanel panel_7;
    private JPanel panel_9;
    private JPanel panel_8;
    private JPanel panel_10;
    private JPanel panel_11;
    private JPanel panel_13;
    private JPanel panel_12;
    private JPanel panel_14;
    private JLabel label_lista_1;
    private JLabel label_lista_2;
    private JLabel label_lista_3;
    private JLabel label_lista_4;
    private JLabel label_lista_5;
    private JLabel label_lista_6;
    private JLabel label_lista_7;
    private JLabel label_lista_8;
    private JLabel[] labels = new JLabel[8];
    private final int N=8;

    private static final long serialVersionUID = 1L; // Serial version UID for Serializable class

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    VentanaMonitor window = new VentanaMonitor();
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
    public VentanaMonitor() {
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
    	this.setTitle("Monitor");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        
        panel = new JPanel();
        getContentPane().add(panel, BorderLayout.NORTH);
        panel.setLayout(new GridLayout(0, 2, 0, 0));
        
        panel_5 = new JPanel();
        panel_5.setBackground(SystemColor.activeCaption);
        panel.add(panel_5);
        
        lblSiguiente = new JLabel("Siguiente: ");
        lblSiguiente.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        panel_5.add(lblSiguiente);
        
        panel_6 = new JPanel();
        panel_6.setBackground(SystemColor.activeCaption);
        panel.add(panel_6);
        
        lblBox = new JLabel("Box:");
        lblBox.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
        panel_6.add(lblBox);
        
        panel_1 = new JPanel();
        panel_1.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_1, BorderLayout.SOUTH);
        
        panel_2 = new JPanel();
        panel_2.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_2, BorderLayout.WEST);
        
        panel_3 = new JPanel();
        panel_3.setBackground(SystemColor.activeCaption);
        getContentPane().add(panel_3, BorderLayout.EAST);
        
        panel_4 = new JPanel();
        panel_4.setBackground(SystemColor.inactiveCaption);
        getContentPane().add(panel_4, BorderLayout.CENTER);
        panel_4.setLayout(new GridLayout(0, 1, 0, 0));
        
        panel_7 = new JPanel();
        panel_7.setBackground(SystemColor.controlHighlight);
        panel_4.add(panel_7);
        
        label_lista_1 = new JLabel("");
        panel_7.add(label_lista_1);
        labels[0] = label_lista_1;
        
        panel_9 = new JPanel();
        panel_9.setBackground(SystemColor.scrollbar);
        panel_4.add(panel_9);
        
        label_lista_2 = new JLabel("");
        panel_9.add(label_lista_2);
        labels[1] = label_lista_2;
        
        panel_8 = new JPanel();
        panel_8.setBackground(SystemColor.controlHighlight);
        panel_4.add(panel_8);
        
        label_lista_3 = new JLabel("");
        panel_8.add(label_lista_3);
        labels[2] = label_lista_3;
        
        panel_10 = new JPanel();
        panel_10.setBackground(SystemColor.scrollbar);
        panel_4.add(panel_10);
        
        label_lista_4 = new JLabel("");
        panel_10.add(label_lista_4);
        labels[3] = label_lista_4;
        
        panel_11 = new JPanel();
        panel_11.setBackground(SystemColor.controlHighlight);
        panel_4.add(panel_11);
        
        label_lista_5 = new JLabel("");
        panel_11.add(label_lista_5);
        labels[4] = label_lista_5;
        
        panel_13 = new JPanel();
        panel_13.setBackground(SystemColor.scrollbar);
        panel_4.add(panel_13);
        
        label_lista_6 = new JLabel("");
        panel_13.add(label_lista_6);
        labels[5] = label_lista_6;
        
        panel_12 = new JPanel();
        panel_12.setBackground(SystemColor.controlHighlight);
        panel_4.add(panel_12);
        
        label_lista_7 = new JLabel("");
        panel_12.add(label_lista_7);
        labels[6] = label_lista_7;
        
        panel_14 = new JPanel();
        panel_14.setBackground(SystemColor.scrollbar);
        panel_4.add(panel_14);
        
        label_lista_8 = new JLabel("");
        panel_14.add(label_lista_8);
        labels[7] = label_lista_8;
    }
    
    public void printLblBox(int box) {
    	this.lblBox.setText("BOX: "+ box);
    }
    
    public void printlblSiguiente(String DNI) {
    	this.lblSiguiente.setText("SIGUIENTE: "+ DNI);
    }
    
    public void printeaLista(Object obj) {
    	//Toolkit.getDefaultToolkit().beep();
        Queue<Cliente> clientes = (Queue<Cliente>) obj;
        LinkedList<Cliente> lista = new LinkedList<Cliente> (clientes); //convierto la queue en una lista para poder acceder a los elementos 
        Iterator<Cliente> iterador = lista.iterator();               
        int i=0;
        while (iterador.hasNext()) {
            Cliente cliente = iterador.next();
            this.labels[i].setText(cliente.toString());                 //los printeo en la ventana
            i++;  
        }
        
        for (int x = i ; x < N; x++) {
           this.labels[i].setText("");
        }
    }
    

    @Override
    public void cerrar() {
        dispose();
    }

    @Override
    public void mostrar() {
        setVisible(true);
    }

    @Override
    public void setActionListener(ActionListener actionListener) {
        this.actionListener=actionListener;
    }

}