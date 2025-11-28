package ups.poo.igu;

import ups.poo.logica.*;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.List;

public class PanelMenuRelacionar extends JPanel {
	private static final long serialVersionUID = 1L;
	private GestorContenidos gestor;
	private JComboBox<String> cbTipoRelacion;
	private JComboBox<String> cbEntidadPrincipal;
	private JComboBox<String> cbEntidadSecundaria;
	private List<?> listaPrincipal;
	private List<?> listaSecundaria;
	
	public PanelMenuRelacionar(GestorContenidos gestor) {
		this.gestor = gestor;
		setBorder(new LineBorder(Color.BLACK));
        setBackground(Estilos.COLOR_FONDO_OSCURO);
        
        this.setVisible(true);
        this.setSize(722, 546);
        setLayout(null);
        
        JLabel lblTitulo = new JLabel("CREAR RELACIONES");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(Estilos.FUENTE_TITULO);
        lblTitulo.setBounds(267, 10, 198, 30);
        add(lblTitulo);
		
        JLabel lblTipo = new JLabel("Tipo de relación:");
        lblTipo.setFont(Estilos.FUENTE_SUBTITULO);
        lblTipo.setBounds(50, 80, 150, 20);
        add(lblTipo);

        String[] relaciones = {"Actor -> Película", "Investigador -> Documental"};
        cbTipoRelacion = new JComboBox<>(relaciones);
        cbTipoRelacion.setBounds(450, 80, 200, 25);
        add(cbTipoRelacion);
		
        JLabel lblPrincipal = new JLabel("Selección de contenidos:");
        lblPrincipal.setFont(Estilos.FUENTE_SUBTITULO);
        lblPrincipal.setBounds(50, 150, 250, 20);
        add(lblPrincipal);

        cbEntidadPrincipal = new JComboBox<>();
        cbEntidadPrincipal.setBounds(50, 180, 250, 25);
        add(cbEntidadPrincipal);

        JLabel lblSecundaria = new JLabel("Vincular con:");
        lblSecundaria.setFont(Estilos.FUENTE_SUBTITULO);;
        lblSecundaria.setBounds(400, 150, 150, 20);
        add(lblSecundaria);

        cbEntidadSecundaria = new JComboBox<>();
        cbEntidadSecundaria.setBounds(400, 180, 250, 25);
        add(cbEntidadSecundaria);
		
        JButton btnVincular = new JButton("CREAR VÍNCULO");
        btnVincular.setBackground(Estilos.COLOR_BOTON_ACCION);
        btnVincular.setForeground(Color.WHITE);
        btnVincular.setFont(Estilos.FUENTE_BOTON);
        btnVincular.setBounds(260, 300, 200, 40);
        add(btnVincular);
        
        JLabel lblRelacionando = new JLabel("---->");
        lblRelacionando.setHorizontalAlignment(SwingConstants.CENTER);
        lblRelacionando.setFont(Estilos.FUENTE_TITULO);
        lblRelacionando.setForeground(Color.WHITE);
        lblRelacionando.setBounds(330, 185, 40, 20);
        add(lblRelacionando);
        
        logicaBotonVincular(btnVincular);
        cargarListas("Actor -> Película");
		
	}
	
	//CONFIGURA LA LOGICA DE AGREGACION AL CLICKEAR EL BOTON
	private void logicaBotonVincular(JButton btnVincular) {
		cbTipoRelacion.addActionListener(e -> {
			String tipo = (String) cbTipoRelacion.getSelectedItem();
			cargarListas(tipo);
		});
		
		btnVincular.addActionListener(e -> crearRelacion());
	}
	
	//CARGA LAS LISTAS DE OBJETOS DISPONIBLES PARA RELACIONAR
	private void cargarListas (String cbTipoRelacion) {
		cbEntidadPrincipal.removeAllItems();
		cbEntidadSecundaria.removeAllItems();
		
		if (cbTipoRelacion.equals("Actor -> Película")) {
			listaPrincipal = gestor.obtenerTodos(Actor.class);
			listaSecundaria = gestor.obtenerTodos(Pelicula.class);
			
			for (Object o : listaPrincipal) {
				cbEntidadPrincipal.addItem(((Actor)o).getNombre() + " " + ((Actor)o).getApellido());
			} 
			
			for (Object o : listaSecundaria) {
				cbEntidadSecundaria.addItem(((Pelicula)o).getTitulo());
			}
		} else if (cbTipoRelacion.equals("Investigador -> Documental")) {
			listaPrincipal = gestor.obtenerTodos(Investigador.class);
			listaSecundaria = gestor.obtenerTodos(Documental.class);
			
			for (Object o : listaPrincipal) {
				cbEntidadPrincipal.addItem(((Investigador)o).getNombre() + " " + ((Investigador)o).getApellido());
			}
			
			for (Object o : listaSecundaria) {
				cbEntidadPrincipal.addItem(((Documental)o).getTitulo());
			}
		} 
		
	}
	
	//CREA LA RELACION ENTRE ENTIDADES
	private void crearRelacion() {
		int indiceP = cbEntidadPrincipal.getSelectedIndex();
		int indiceS = cbEntidadSecundaria.getSelectedIndex();
		
		if (indiceP == -1 || indiceS == -1) {
			JOptionPane.showMessageDialog(this, "Seleccione ambos elementos", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		String tipoRelacion = (String) cbTipoRelacion.getSelectedItem();
		
		if (tipoRelacion.equals("Actor -> Película")) {
			Actor actor = (Actor) listaPrincipal.get(indiceP);
			Pelicula peli = (Pelicula) listaSecundaria.get(indiceS);
			
			peli.agregarActor(actor);
			
			JOptionPane.showMessageDialog(this, "Relación de entidades establecida exitosamente",
					"Éxito", JOptionPane.INFORMATION_MESSAGE);
			
		} else if(tipoRelacion.equals("Investigador -> Documental")){
			Investigador inves = (Investigador) listaPrincipal.get(indiceP);
			Documental docu = (Documental) listaSecundaria.get(indiceS);
			
			inves.setDocum(docu);
			docu.setInvesti(inves);
			
			JOptionPane.showMessageDialog(this, "Relación de entidades establecida exitosamente",
					"Éxito", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
}
