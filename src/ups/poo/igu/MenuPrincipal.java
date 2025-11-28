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
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.event.*;
public class MenuPrincipal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel bg;
	private JPanel panelContenidos;
	private PanelMenuAdd panelAgregar;
	private PanelMenuConsultas panelConsultas;
	private PanelMenuModificar panelModificar;
	private PanelMenuEliminar panelEliminar;
	private PanelMenuRelacionar panelRelacionar;
	private GestorContenidos gestor;
	private GestorPersistencia persistencia;
	
	public MenuPrincipal(GestorContenidos gestor) {
		this.gestor = gestor;
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 884, 583);
		bg = new JPanel();
		bg.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(bg);
		bg.setLayout(null);
		
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setBackground(Estilos.COLOR_FONDO_MENU_LATERAL);
		panelPrincipal.setBounds(0, 0, 870, 546);
		bg.add(panelPrincipal);
		panelPrincipal.setLayout(null);
		
		JButton btnMenuAdd = new JButton("AGREGAR");
		btnMenuAdd.setForeground(Estilos.COLOR_TEXTO_BOTON);
		btnMenuAdd.setBackground(Estilos.COLOR_BOTON_ACCION);
		btnMenuAdd.setFont(Estilos.FUENTE_BOTON);
		btnMenuAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel(panelAgregar);
				
			}
		});
		btnMenuAdd.setBounds(10, 82, 128, 44);
		panelPrincipal.add(btnMenuAdd);
		
		JButton btnMenuConsul = new JButton("BUSCAR");
		btnMenuConsul.setForeground(Estilos.COLOR_TEXTO_BOTON);
		btnMenuConsul.setBackground(Estilos.COLOR_BOTON_ACCION);
		btnMenuConsul.setFont(Estilos.FUENTE_BOTON);
		btnMenuConsul.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel(panelConsultas);
			}
		});
		btnMenuConsul.setBounds(10, 136, 128, 44);
		panelPrincipal.add(btnMenuConsul);
		
		JButton btnMenuElim = new JButton("ELIMINAR");
		btnMenuElim.setForeground(Estilos.COLOR_TEXTO_BOTON);
		btnMenuElim.setBackground(Estilos.COLOR_BOTON_ACCION);
		btnMenuElim.setFont(Estilos.FUENTE_BOTON);
		btnMenuElim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel(panelEliminar);
			}
		});
		btnMenuElim.setBounds(10, 246, 128, 44);
		panelPrincipal.add(btnMenuElim);
		
		JButton btnMenuModificar = new JButton("MODIFICAR");
		btnMenuModificar.setForeground(Estilos.COLOR_TEXTO_BOTON);
		btnMenuModificar.setBackground(Estilos.COLOR_BOTON_ACCION);
		btnMenuModificar.setFont(Estilos.FUENTE_BOTON);
		btnMenuModificar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel(panelModificar);
			}
		});
		btnMenuModificar.setBounds(10, 190, 128, 44);
		panelPrincipal.add(btnMenuModificar);
		
		JButton btnMenuRelacionar = new JButton("RELACIONAR");
		btnMenuRelacionar.setForeground(Estilos.COLOR_TEXTO_BOTON);
		btnMenuRelacionar.setBackground(Estilos.COLOR_BOTON_ACCION);
		btnMenuRelacionar.setFont(Estilos.FUENTE_BOTON);
		btnMenuRelacionar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mostrarPanel(panelRelacionar);
			}
		});
		btnMenuRelacionar.setBounds(10, 300, 128, 44);
		panelPrincipal.add(btnMenuRelacionar);
		
		panelContenidos = new JPanel();
		panelContenidos.setBackground(new Color(255, 255, 255));
		panelContenidos.setBounds(148, 0, 722, 546);
		panelPrincipal.add(panelContenidos);
		panelContenidos.setLayout(null);

		panelAgregar = new PanelMenuAdd(gestor);
		panelAgregar.setBackground(Estilos.COLOR_FONDO_OSCURO);
		panelConsultas = new PanelMenuConsultas(gestor);
		panelModificar = new PanelMenuModificar(gestor);
		panelEliminar = new PanelMenuEliminar(gestor);
		panelRelacionar = new PanelMenuRelacionar(gestor);
		mostrarPanel(panelAgregar);
		
		JLabel lblMenu = new JLabel("MENU");
		lblMenu.setHorizontalAlignment(SwingConstants.CENTER);
		lblMenu.setFont(Estilos.FUENTE_TITULO);
		lblMenu.setBounds(10, 12, 134, 44);
		panelPrincipal.add(lblMenu);
		
		JButton btnGuardarDatos = new JButton("GUARDAR");
		btnGuardarDatos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int confirmacion = JOptionPane.showConfirmDialog(MenuPrincipal.this, "¿Desea guardar los datos?", 
						"Guardar", JOptionPane.YES_NO_CANCEL_OPTION);
				
				if (confirmacion == JOptionPane.CANCEL_OPTION) {
					return;
				}
				
				if (confirmacion == JOptionPane.YES_OPTION) {
					gestor.guardarEstado();
					JOptionPane.showMessageDialog(MenuPrincipal.this, "Datos guardados exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		btnGuardarDatos.setForeground(Estilos.COLOR_TEXTO_BOTON);
		btnGuardarDatos.setBackground(Estilos.COLOR_BOTON_ACCION);
		btnGuardarDatos.setFont(Estilos.FUENTE_BOTON);
		btnGuardarDatos.setBounds(10, 490, 128, 44);
		panelPrincipal.add(btnGuardarDatos);
		
		//FUNCION PARA GUARDAR Y SALIR AL CERRAR LA APLICACION
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				
				int confirmacion = JOptionPane.showConfirmDialog(MenuPrincipal.this, "¿Desea guardar los cambios antes de salir?", 
						"Guardar y cerrar", JOptionPane.YES_NO_CANCEL_OPTION);
				
				if (confirmacion == JOptionPane.CANCEL_OPTION) {
					return;
				}
				
				if (confirmacion == JOptionPane.YES_OPTION) {
					gestor.guardarEstado();
				}
				
				dispose();
				System.exit(0);
			}
		});
		
	}

	private void mostrarPanel(JPanel panel) {
		
		panelContenidos.removeAll();
		panel.setBounds(0, 0, 722, 546);
		panelContenidos.add(panel);
		panelContenidos.revalidate();
		panelContenidos.repaint();
		
	}
	
	
}
