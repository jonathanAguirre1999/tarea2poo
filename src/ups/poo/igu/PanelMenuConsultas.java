package ups.poo.igu;

import ups.poo.logica.*;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.util.*;

public class PanelMenuConsultas extends JPanel {

	private static final long serialVersionUID = 1L;
	private final String [] objetosConsultables = {"Peliculas", "Series de TV", "Documentales", "Actores", "Temporadas", "Investigadores",
			"Videos de Youtube", "Videos Musicales"};
	private GestorContenidos gestor;
	private JComboBox<String> cbObjetoConsulta;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JScrollPane scPanelTabla;
    private JPanel panelCentral;
	
	public PanelMenuConsultas(GestorContenidos gestor) {
		this.gestor = gestor;
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setBackground(new Color(121, 134, 203));
		
		this.setVisible(true);
		this.setSize(722, 546);
		setLayout(null);
		
		
		JLabel lblTitulo = new JLabel("CONSULTAR");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(new Font("Segoe UI Black", Font.PLAIN, 18));
		lblTitulo.setBounds(267, 10, 198, 30);
		add(lblTitulo);
		
		cbObjetoConsulta = new JComboBox<>(objetosConsultables);
		cbObjetoConsulta.setBounds(397, 50, 162, 21);
		add(cbObjetoConsulta);
		
		panelCentral = new JPanel();
		panelCentral.setBackground(new Color(197, 202, 233));
		panelCentral.setBounds(10, 92, 702, 444);
		panelCentral.setLayout(new BorderLayout());
		add(panelCentral);
		
		JLabel lblDatoAConsultar = new JLabel("Tipo de objeto a consultar:");
		lblDatoAConsultar.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatoAConsultar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
		lblDatoAConsultar.setBounds(115, 42, 246, 30);
		add(lblDatoAConsultar);
		
		inicializarTabla();
		
		panelCentral.add(scPanelTabla, BorderLayout.CENTER);
		
		configAccion();
	}
	
	//configura la accion de llenado de tabla mediante la seleccion del item en el ComboBox
	private void configAccion() {
		
		cbObjetoConsulta.addActionListener(e -> realizarConsulta());
		
		if(objetosConsultables.length > 0) {
			cargarDatosTabla(objetosConsultables[0]);
		}
	}
	
	//metodo que inicializa la tabla
	private void inicializarTabla() {
		modeloTabla = new DefaultTableModel();
		tablaResultados = new JTable(modeloTabla);
		tablaResultados.setBackground(new Color(197, 202, 233));
		tablaResultados.setFillsViewportHeight(true);
		
		scPanelTabla = new JScrollPane(tablaResultados);
		}
	
	//metodo para realizar consultas
	private void realizarConsulta() {
		String tipoSeleccionado = (String) cbObjetoConsulta.getSelectedItem();
		cargarDatosTabla(tipoSeleccionado);
	}
	
	//carga los datos en la tabla
	private void cargarDatosTabla(String tipoSeleccionado) {
		List <?> listaObjetos = obtenerListaPorTipo(tipoSeleccionado);
		
		String [] nombreColumnas = obtenerNombresColumnas(tipoSeleccionado);
		modeloTabla.setColumnIdentifiers(nombreColumnas);
		
		modeloTabla.setRowCount(0);
		
		if(listaObjetos.isEmpty() || listaObjetos == null) {
			if(nombreColumnas.length == 0 || (nombreColumnas.length == 1 && !nombreColumnas[0].equals("Mensaje"))) {
				modeloTabla.setColumnIdentifiers(new String[] {"Mensaje"});
			}
			modeloTabla.addRow(new Object[] {"No existen datos disponibles para " + tipoSeleccionado});
		} else {
			for(Object obj : listaObjetos) {
				Object[] fila = mapearObjetoAFila(obj, tipoSeleccionado);
				if(fila != null) {
					modeloTabla.addRow(fila);
				}
			}
		}
		
		tablaResultados.repaint();
	}
	
	private List<?> obtenerListaPorTipo(String tipo) {
		
		switch (tipo) {
			case "Peliculas":
				return this.gestor.obtenerTodos(Pelicula.class);
			case "Series de TV":
				return this.gestor.obtenerTodos(SerieDeTV.class);
			case "Documentales":
				return this.gestor.obtenerTodos(Documental.class);
			case "Actores":
				return this.gestor.obtenerTodos(Actor.class);
			case "Temporadas":
				return this.gestor.obtenerTodos(Temporada.class);
			case "Investigador":
				return this.gestor.obtenerTodos(Investigador.class);
			case "Videos de Youtube":
				return this.gestor.obtenerTodos(VideoYoutube.class);
			case "Videos Musicales":
				return this.gestor.obtenerTodos(VideoMusical.class);
			default:
				return Collections.emptyList();
				
		}
		
	}
	
	//configura los nombres de las columnas de la taba de acuerdo a los atributos de cada objeto
	private String[] obtenerNombresColumnas(String tipo) {
	    switch (tipo) {
	        case "Peliculas":
	            return new String[]{"ID", "Título", "Duración (min)", "Género", "Estudio", "Cant. Actores"};
	        case "Series de TV":
	            return new String[]{"ID", "Título", "Duración Cap.", "Género", "Año", "En emisión actualmente"};
	        case "Documentales":
	            return new String[]{"ID", "Título", "Duración", "Género", "Tema"};
	        case "Actores":
	            return new String[]{"ID", "Nombre", "Apellido", "Nacionalidad", "Cant. Peliculas"};
	        case "Temporadas":
	            return new String[]{"ID", "Serie", "Núm. Temporada", "Título", "Capítulos", "Fecha Estreno"};
	        case "Investigadores":
	            return new String[]{"ID", "Nombre", "Apellido", "Nacionalidad", "Especialidad", "Documental Asociado"};
	        case "Videos de Youtube":
	            return new String[]{"ID", "Título", "Duración", "Género", "Canal", "Link"};
	        case "Videos Musicales":
	            return new String[]{"ID", "Título", "Duración", "Género", "Artista", "Álbum", "Año"};
	        default:
	            return new String[]{"ID", "Datos"};
	    }
	}
	
	//convierte los atributos de un objeto a valores para ser agregados a las filas
	private Object[] mapearObjetoAFila(Object obj, String tipoEscogido) {
	    
	    switch (tipoEscogido) {
	        case "Peliculas":
	            Pelicula p = (Pelicula) obj;
	            int cantActores = (p.getActores() != null) ? p.getActores().size() : 0;
	            return new Object[]{
	                p.getId(), 
	                p.getTitulo(), 
	                p.getDuracionEnMinutos(), 
	                p.getGenero(), 
	                p.getEstudio(), 
	                cantActores,
	            };
	            
	        case "Series de TV":
	            SerieDeTV s = (SerieDeTV) obj;
	            String enEmision = s.isEnEmision() ? "Sí" : "No";
	            return new Object[]{
	                s.getId(), 
	                s.getTitulo(), 
	                s.getDuracionEnMinutos(), 
	                s.getGenero(), 
	                s.getAnioEstreno(), 
	                enEmision
	            };
	            
	        case "Documentales":
	            Documental d = (Documental) obj;
	            return new Object[]{
	                d.getId(), 
	                d.getTitulo(), 
	                d.getDuracionEnMinutos(),
	                d.getGenero(), 
	                d.getTema()
	            };
	            
	        case "Actores":
	            Actor a = (Actor) obj;
	            int cantPeliculas = (a.getPeliculas() != null) ? a.getPeliculas().size() : 0;
	            return new Object[]{
	                a.getId(), 
	                a.getNombre(), 
	                a.getApellido(), 
	                a.getNacionalidad(), 
	                cantPeliculas
	            };
	            
	        case "Temporadas":
	            Temporada t = (Temporada) obj;
	            String tituloSerie = (t.getSerie() != null) ? t.getSerie().getTitulo() : "N/A";
	            return new Object[]{
	                t.getId(), 
	                tituloSerie, 
	                t.getNumTemporada(), 
	                t.getTitulo(), 
	                t.getCantidadCapitulos(), 
	                t.getFechaEstreno()
	            };
	            
	        case "Investigadores":
	            Investigador inv = (Investigador) obj;
	            String tituloDoc = (inv.getDocum() != null) ? inv.getDocum().getTitulo() : "Ninguno";
	            return new Object[]{
	                inv.getId(), 
	                inv.getNombre(), 
	                inv.getApellido(), 
	                inv.getNacionalidad(), 
	                inv.getAreaEspecialidad(), 
	                tituloDoc
	            };
	            
	        case "Videos de Youtube":
	            VideoYoutube yt = (VideoYoutube) obj;
	            return new Object[]{
	                yt.getId(), 
	                yt.getTitulo(), 
	                yt.getDuracionEnMinutos(), 
	                yt.getGenero(), 
	                yt.getNombreCanal(), 
	                yt.getLinkVideo()
	            };
	            
	        case "Videos Musicales":
	            VideoMusical vm = (VideoMusical) obj;
	            return new Object[]{
	                vm.getId(), 
	                vm.getTitulo(), 
	                vm.getDuracionEnMinutos(), 
	                vm.getGenero(), 
	                vm.getArtista(), 
	                vm.getAlbum(), 
	                vm.getAnio()
	            };

	        default:
	            return null;
	    }
	}
}
