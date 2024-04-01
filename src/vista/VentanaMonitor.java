package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.SystemColor;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.Color;

public class VentanaMonitor {

	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaMonitor window = new VentanaMonitor();
					window.frame.setVisible(true);
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
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(SystemColor.activeCaption);
		panel.add(panel_5);
		
		JLabel lblNewLabel = new JLabel("Siguiente: ");
		lblNewLabel.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
		panel_5.add(lblNewLabel);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(SystemColor.activeCaption);
		panel.add(panel_6);
		
		JLabel lblNewLabel_1 = new JLabel("Box:");
		lblNewLabel_1.setFont(new Font("Microsoft YaHei", Font.BOLD, 15));
		panel_6.add(lblNewLabel_1);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(SystemColor.activeCaption);
		frame.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(SystemColor.activeCaption);
		frame.getContentPane().add(panel_2, BorderLayout.WEST);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(SystemColor.activeCaption);
		frame.getContentPane().add(panel_3, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(SystemColor.inactiveCaption);
		frame.getContentPane().add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new GridLayout(0, 1, 0, 0));
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(SystemColor.controlHighlight);
		panel_4.add(panel_7);
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(SystemColor.scrollbar);
		panel_4.add(panel_9);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBackground(SystemColor.controlHighlight);
		panel_4.add(panel_8);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBackground(SystemColor.scrollbar);
		panel_4.add(panel_10);
		
		JPanel panel_11 = new JPanel();
		panel_11.setBackground(SystemColor.controlHighlight);
		panel_4.add(panel_11);
		
		JPanel panel_13 = new JPanel();
		panel_13.setBackground(SystemColor.scrollbar);
		panel_4.add(panel_13);
		
		JPanel panel_12 = new JPanel();
		panel_12.setBackground(SystemColor.controlHighlight);
		panel_4.add(panel_12);
		
		JPanel panel_14 = new JPanel();
		panel_14.setBackground(SystemColor.scrollbar);
		panel_4.add(panel_14);
	}

}
