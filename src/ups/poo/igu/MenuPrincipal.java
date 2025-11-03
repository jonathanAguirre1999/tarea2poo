package ups.poo.igu;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import ups.poo.logica.*;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel bg;
	private JPanel panelContenidos;
	private PanelMenuAdd panelAgregar;
	private PanelMenuConsultas panelConsultas;
	private PanelMenuModificar panelModificar;
	private PanelMenuEliminar panelEliminar;
	private GestorContenidos gestor;
	
	public MenuPrincipal(GestorContenidos gestor) {
		this.gestor = gestor;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 884, 583);
		bg = new JPanel();
		bg.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(bg);
		bg.setLayout(null);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBackground(new Color(63, 81, 181));
		panelPrincipal.setBounds(0, 0, 870, 546);
		bg.add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JButton btnMenuAdd = new JButton("AGREGAR");
		btnMenuAdd.setForeground(new Color(255, 255, 255));
		btnMenuAdd.setBackground(new Color(48, 63, 159));
		btnMenuAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel(panelAgregar);
				
			}
		});
		btnMenuAdd.setBounds(10, 82, 128, 44);
		panelPrincipal.add(btnMenuAdd);
		
		JButton btnMenuConsul = new JButton("BUSCAR");
		btnMenuConsul.setForeground(new Color(255, 255, 255));
		btnMenuConsul.setBackground(new Color(48, 63, 159));
		btnMenuConsul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel(panelConsultas);
			}
		});
		btnMenuConsul.setBounds(10, 136, 128, 44);
		panelPrincipal.add(btnMenuConsul);
		
		JButton btnMenuElim = new JButton("ELIMINAR");
		btnMenuElim.setForeground(new Color(255, 255, 255));
		btnMenuElim.setBackground(new Color(48, 63, 159));
		btnMenuElim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel(panelEliminar);
			}
		});
		
		JButton btnMenuModificar = new JButton("MODIFICAR");
		btnMenuModificar.setForeground(new Color(255, 255, 255));
		btnMenuModificar.setBackground(new Color(48, 63, 159));
		btnMenuModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel(panelModificar);
			}
		});
		btnMenuModificar.setBounds(10, 190, 128, 44);
		panelPrincipal.add(btnMenuModificar);
		btnMenuElim.setBounds(10, 244, 128, 44);
		panelPrincipal.add(btnMenuElim);
		
		panelContenidos = new JPanel();
		panelContenidos.setBackground(new Color(255, 255, 255));
		panelContenidos.setBounds(148, 0, 722, 546);
		panelPrincipal.add(panelContenidos);
		panelContenidos.setLayout(null);

		panelAgregar = new PanelMenuAdd(gestor);
		panelAgregar.setBackground(new Color(121, 134, 203));
		panelConsultas = new PanelMenuConsultas(gestor);
		panelModificar = new PanelMenuModificar(gestor);
		panelEliminar = new PanelMenuEliminar(gestor);
		mostrarPanel(panelAgregar);
		
		JLabel lblMenu = new JLabel("MENU");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(new Font("Segoe UI Black", Font.PLAIN, 18));
		lblMenu.setBounds(10, 10, 134, 44);
		panelPrincipal.add(lblMenu);
		
	}

	private void mostrarPanel(JPanel panel) {
		
		panelContenidos.removeAll();
		panel.setBounds(0, 0, 722, 546);
		panelContenidos.add(panel);
		panelContenidos.revalidate();
		panelContenidos.repaint();
		
		
	}
	
	
	
}
