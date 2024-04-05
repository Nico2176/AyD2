package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.JLabel;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import javax.swing.border.LineBorder;

import modelo.Cliente;

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
    private JLabel lblBox;
    private JLabel lblNewLabel_2;
    private JPanel panel_lista_1;
    private JPanel panel_lista_2;
    private JPanel panel_lista_3;
    private JPanel panel_lista_4;
    private JPanel panel_lista_5;
    private JPanel panel_lista_6;
    private JLabel lbl_lista_1;
    private JLabel lbl_lista_2;
    private JLabel lbl_lista_3;
    private JLabel lbl_lista_4;
    private JLabel lbl_lista_5;
    private JLabel lbl_lista_6;
    private JPanel[] paneles = new JPanel[6];
	private JLabel[] labels = new JLabel[6];

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
        
        lblBox = new JLabel("BOX:");
        lblBox.setFont(new Font("Microsoft YaHei", Font.PLAIN, 16));
        panel_7.add(lblBox);
        
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
        panel_6.setLayout(new GridLayout(6, 0, 0, 0));
        
        panel_lista_1 = new JPanel();
        panel_lista_1.setBackground(SystemColor.controlHighlight);
        panel_6.add(panel_lista_1);
        panel_lista_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        lbl_lista_1 = new JLabel("");
        panel_lista_1.add(lbl_lista_1);
        
        panel_lista_2 = new JPanel();
        panel_lista_2.setBackground(SystemColor.scrollbar);
        panel_6.add(panel_lista_2);
        
        lbl_lista_2 = new JLabel("");
        panel_lista_2.add(lbl_lista_2);
        
        panel_lista_3 = new JPanel();
        panel_lista_3.setBackground(SystemColor.controlHighlight);
        panel_6.add(panel_lista_3);
        
        lbl_lista_3 = new JLabel("");
        panel_lista_3.add(lbl_lista_3);
        
        panel_lista_4 = new JPanel();
        panel_lista_4.setBackground(SystemColor.scrollbar);
        panel_6.add(panel_lista_4);
        
        lbl_lista_4 = new JLabel("");
        panel_lista_4.add(lbl_lista_4);
        
        panel_lista_5 = new JPanel();
        panel_lista_5.setBackground(SystemColor.controlHighlight);
        panel_6.add(panel_lista_5);
        
        lbl_lista_5 = new JLabel("");
        panel_lista_5.add(lbl_lista_5);
        
        panel_lista_6 = new JPanel();
        panel_lista_6.setBackground(SystemColor.scrollbar);
        panel_6.add(panel_lista_6);
        
        lbl_lista_6 = new JLabel("");
        panel_lista_6.add(lbl_lista_6);
        
        
        labels[0] = lbl_lista_1;
    	labels[1] = lbl_lista_2;
    	labels[2] = lbl_lista_3;
    	labels[3] = lbl_lista_4;
    	labels[4] = lbl_lista_5;
    	labels[5] = lbl_lista_6;
        
        
    }
    
    public void setBox(int box) {
    	this.lblBox.setText("BOX: "+ box);
    }
    
    public void actualizaSiguiente(String DNI) {
    	this.lblNewLabel_2.setText("Actualmente atendiendo a: " + DNI );
    }

    
    public void printeaLista(Object obj) {
    	Queue<Cliente> clientes = (Queue<Cliente>) obj;
    	LinkedList<Cliente> lista = new LinkedList<Cliente> (clientes); //convierto la queue en una lista para poder acceder a los elementos 
    	Iterator<Cliente> iterador = lista.iterator();               
    	int i=0;
        while (iterador.hasNext()) {
        	Cliente cliente = iterador.next();
        	this.labels[i].setText(cliente.toString());                 //los printeo en la ventana
        	i++;  
        }
        
        for (int x = i+1 ; x < 6; x++) {
           this.labels[i].setText("");
        }
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