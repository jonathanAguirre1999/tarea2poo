package ups.poo.igu;

import ups.poo.logica.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.JTextComponent;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JButton;

public class PanelMenuModificar extends JPanel {

	private static final long serialVersionUID = 1L;
	private GestorContenidos gestor;
	private final String [] objetosModificables = {"Peliculas", "Series de TV", "Documentales", "Actores", "Temporadas", "Investigadores",
            "Videos de Youtube", "Videos Musicales"};
	private JComboBox <String> cbObjetosModificables;
	private DefaultTableModel modeloTabla;
	private JScrollPane scPanelTabla;
	private JTable tablaObjetos;
	private JPanel panelSuperior;
	private JPanel panelInferior;
	private JButton btnModificarAtributos;
	
	private IModificable objetoEnEdicion;
	private Map<String, JComponent> camposFormulario = new HashMap<>();
	
	public PanelMenuModificar(GestorContenidos gestor) {
		this.gestor = gestor;
		setBorder(new LineBorder(Color.BLACK));
		setBackground(Estilos.COLOR_FONDO_OSCURO);
		
		this.setVisible(true);
		this.setSize(722, 546);
		setLayout(null);
		
		
		JLabel lblTitulo = new JLabel("MODIFICAR");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(Estilos.FUENTE_TITULO);
		lblTitulo.setBounds(267, 10, 198, 30);
		add(lblTitulo);
		
		inicializarTabla();
		
		panelSuperior =  new JPanel();
		panelSuperior.setBackground(Estilos.COLOR_FONDO_CLARO);
		panelSuperior.setBounds(10, 86, 702, 179);
		panelSuperior.setLayout(new BorderLayout());
		panelSuperior.add(scPanelTabla, BorderLayout.CENTER);
		add(panelSuperior);
		
		panelInferior = new JPanel();
		panelInferior.setBackground(Estilos.COLOR_FONDO_CLARO);
		panelInferior.setBounds(10, 273, 702, 226);
		panelInferior.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 15));
		add(panelInferior);
		
		
		JLabel lblDatoAModificar = new JLabel("Lista de objetos a modificar:");
		lblDatoAModificar.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatoAModificar.setFont(Estilos.FUENTE_SUBTITULO);
		lblDatoAModificar.setBounds(10, 46, 209, 30);
		add(lblDatoAModificar);
		
		cbObjetosModificables = new JComboBox<>(objetosModificables);
		cbObjetosModificables.setBounds(490, 50, 209, 28);
		add(cbObjetosModificables);
		
		btnModificarAtributos = new JButton("MODIFICAR ATRIBUTOS");
		btnModificarAtributos.setFont(Estilos.FUENTE_BOTON);
		btnModificarAtributos.setBounds(507, 509, 205, 27);
		btnModificarAtributos.setEnabled(false);
		add(btnModificarAtributos);
		
		configAcciones();
		
	}
	
	//inicializa la tabla de objetos
	private void inicializarTabla() {
		modeloTabla = new DefaultTableModel();
		tablaObjetos = new JTable(modeloTabla) {
			
			private static final long serialVersionUID = 1L;
		
			//modificar para evitar edicion directa de las celdas
				@Override
				public boolean isCellEditable(int row, int colum) {
					return false;
				}
		};
		tablaObjetos.setFillsViewportHeight(true);
		tablaObjetos.setBackground(Estilos.COLOR_FONDO_CLARO);
		scPanelTabla = new JScrollPane(tablaObjetos);
	}
	
	//configura las acciones que se realizan con los objetos de la tabla de objetos
	private void configAcciones() {
		cbObjetosModificables.addActionListener(e -> cargarTablaModificaciones());
		
		//carga la informacion con doble click en el objeto de la tabla
		tablaObjetos.getSelectionModel().addListSelectionListener(e -> {
			if(!e.getValueIsAdjusting()) {
				cargarDatosSeleccion();
			}	
			
			if (tablaObjetos.getSelectedRow() != -1) {
	            cargarDatosSeleccion();
	        } else {
	            limpiarFormulario();
	        }
		});
		
		//configura el comportamiento del boton
		btnModificarAtributos.addActionListener(e -> guardarCambios());
		
		cargarTablaModificaciones();
		
	}
	
	//
	private void cargarTablaModificaciones() {
		String tipoSeleccionado = (String) cbObjetosModificables.getSelectedItem();
		List <?> listaObjetos = obtenerListaPorTipo(tipoSeleccionado);
		String [] nombresColumnas = TablaConfig.obtenerNombresColumnas(tipoSeleccionado);
		
		modeloTabla.setColumnIdentifiers(nombresColumnas);
		modeloTabla.setRowCount(0);
		
		if (listaObjetos != null && !listaObjetos.isEmpty()) {
	        for (Object obj : listaObjetos) {
	            Object [] fila = TablaConfig.mapearObjetoAFila(obj, tipoSeleccionado); 
	            if (fila != null) {
	                modeloTabla.addRow(fila);
	            }
	        }
	    } else {
	        modeloTabla.addRow(new Object[]{"No hay datos disponibles para " + tipoSeleccionado});
	    }
		
		limpiarFormulario();
	}
	
	//limpia el formulario al cambiar la tabla
	private void limpiarFormulario() {
		panelInferior.removeAll();
		panelInferior.revalidate();
		panelInferior.repaint();
		
		objetoEnEdicion = null;
		camposFormulario.clear();
		btnModificarAtributos.setEnabled(false);
	}
	
	//obtiene la lista de un tipo especifico
	private List<?> obtenerListaPorTipo(String tipo) {
	    Class<?> claseAObtener = null;

	    switch (tipo) {
	        case "Peliculas":
	            claseAObtener = Pelicula.class;
	            break;
	        case "Series de TV":
	            claseAObtener = SerieDeTV.class;
	            break;
	        case "Documentales":
	            claseAObtener = Documental.class;
	            break;
	        case "Actores":
	            claseAObtener = Actor.class;
	            break;
	        case "Temporadas":
	            claseAObtener = Temporada.class;
	            break;
	        case "Investigadores":
	            claseAObtener = Investigador.class;
	            break;
	        case "Videos de Youtube":
	            claseAObtener = VideoYoutube.class;
	            break;
	        case "Videos Musicales":
	            claseAObtener = VideoMusical.class;
	            break;
	        default:
	            return Collections.emptyList();
	    }
	    return gestor.obtenerTodos(claseAObtener);
	}
	
	//carga los datos del objeto seleccionado
	private void cargarDatosSeleccion() {
		try {
			
			int filaSeleccionada = tablaObjetos.getSelectedRow();
			if(filaSeleccionada == -1) return;
			
			int idObjeto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
			
			Object objeto = gestor.buscarPorId(idObjeto);
			
			if(objeto == null) {
				JOptionPane.showMessageDialog(this, "Error. El objeto seleccionado no se encontró: ", "Error en los datos", JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			if(objeto instanceof IModificable) {
				this.objetoEnEdicion = (IModificable) objeto;
				String tipoSeleccionado = (String) cbObjetosModificables.getSelectedItem();
				
				generarFormularioEdicion(this.objetoEnEdicion, tipoSeleccionado);
				btnModificarAtributos.setEnabled(true);
			} else {
				JOptionPane.showMessageDialog(this, "El objeto seleccionado no se puede modificar", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al seleccionar el objeto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
		
	}
	
	private void guardarCambios() {
		if(objetoEnEdicion == null) {
			JOptionPane.showMessageDialog(this, " Debe seleccionar un objeto para modificarlo", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		boolean confirmarCambios = false;
		
		for(Map.Entry<String, JComponent> entrada : camposFormulario.entrySet()) {
			String nombreAtributo = entrada.getKey();
			JComponent componente = entrada.getValue();
			
			String nuevoValorString = "";
			if(componente instanceof JTextComponent) {
				nuevoValorString = ((JTextComponent) componente).getText();
			}
			
			Object nuevoValorConvertido = convertidorValorAtributo(nombreAtributo, nuevoValorString);
			if(nuevoValorConvertido != null) {
				if(objetoEnEdicion.modificarAtributo(nombreAtributo, nuevoValorConvertido)) {
					confirmarCambios = true;
				}
			}
		}
		
		if(confirmarCambios) {
			JOptionPane.showMessageDialog(this, "Valores modificados exitosamente", "Exito", JOptionPane.INFORMATION_MESSAGE);
			cargarTablaModificaciones();
		} else {
			JOptionPane.showMessageDialog(this, "Ninguna modificacion fue realizada", "Aviso", JOptionPane.WARNING_MESSAGE);
		}
	}
	
	//transforma (parsea) los datos ingresados a los formatos requeridos
	private Object convertidorValorAtributo(String atributo, String valorString) {
		
		if(valorString == null || valorString.isBlank()) {
			return (atributo.toLowerCase().contains("titulo") || atributo.toLowerCase().contains("nombre")) ? valorString.trim() : null;
		}
		
		String atrib = atributo.toLowerCase();
		
		//verifica y parsea valores en campos donde se esperan numeros
		if (atrib.contains("numero") || atrib.contains("cantidad") || atrib.contains("anio")) {
	        try {
	            return Integer.parseInt(valorString.trim());
	        } catch (NumberFormatException e) {
	            JOptionPane.showMessageDialog(this, "El campo '" + atributo + "' debe tener solo números enteros", "Error de dato", JOptionPane.ERROR_MESSAGE);
	            return null;
	        }
	    }
		
		//verifica y parsea los datos de tipo fecha
		if (atrib.contains("fecha")) {
	        try {
	            return LocalDate.parse(valorString.trim()); 
	        } catch (DateTimeParseException e) {
	            JOptionPane.showMessageDialog(this, "El campo '" + atributo + "' debe tener el formato AAAA-MM-DD.", "Error de dato", JOptionPane.ERROR_MESSAGE);
	            return null;
	        }
	    }
		
		//aquellos valores que no entran en la verificacion son tipo String y son retornados sin necesidad de cambios
		return valorString;
	}
	
	//genera el formulario de edicion de acuerdo a los atributos del objeto
	private void generarFormularioEdicion(IModificable objeto, String tipoSeleccionado) {
		panelInferior.removeAll();
		camposFormulario.clear();
		panelInferior.setLayout(new GridLayout(0, 2, 10, 10));
		
		switch (tipoSeleccionado) {
        case "Peliculas":
            crearCamposPelicula((Pelicula) objeto);
            break;
        case "Series de TV":
            crearCamposSerie((SerieDeTV) objeto);
            break;
        case "Documentales":
            crearCamposDocumental((Documental) objeto);
            break;
        case "Actores":
            crearCamposActor((Actor) objeto);
            break;
        case "Temporadas":
            crearCamposTemporada((Temporada) objeto);
            break;
        case "Investigadores":
            crearCamposInvestigador((Investigador) objeto);
            break;
        case "Videos de Youtube":
            crearCamposVideoYT((VideoYoutube) objeto);
            break;
        case "Videos Musicales":
            crearCamposVideoMusical((VideoMusical) objeto);
            break;
        default:
            panelInferior.add(new JLabel("Objeto no reconocido o no habilitado para su edición."));
            btnModificarAtributos.setEnabled(false);
            break;
		}
		
		panelInferior.revalidate();
		panelInferior.repaint();
		
	}
	
//------------------------------------METODOS DE CREACION DE FORMULARIO DINAMICO--------------------------------------------------
	
	private void crearCamposPelicula(Pelicula p) {

		panelInferior.add(new JLabel("ID Pelicula:"));
		JTextField txtIdPelicula = new JTextField(String.valueOf(p.getId()));
		txtIdPelicula.setEditable(false);
		panelInferior.add(txtIdPelicula);
		
		panelInferior.add(new JLabel("Título:"));
		JTextField txtTituloTemporada = new JTextField(p.getTitulo());
		panelInferior.add(txtTituloTemporada);
		camposFormulario.put("titulo", txtTituloTemporada);
		
		panelInferior.add(new JLabel("Duracion en minutos:"));
		JTextField txtDuracion = new JTextField(String.valueOf(p.getDuracionEnMinutos()));
		panelInferior.add(txtDuracion);
		camposFormulario.put("duracion", txtDuracion);
		
		panelInferior.add(new JLabel("Género:"));
		JTextField txtGenero = new JTextField(p.getGenero());
		panelInferior.add(txtGenero);
		camposFormulario.put("genero", txtGenero);
		
		panelInferior.add(new JLabel("Estudio a cargo:"));
		JTextField txtEstudio = new JTextField(p.getEstudio());
		panelInferior.add(txtEstudio);
		camposFormulario.put("estudio", txtEstudio);
	}
	
	private void crearCamposSerie(SerieDeTV s) {
		panelInferior.add(new JLabel("ID serie:"));
		JTextField txtIdSerie = new JTextField(String.valueOf(s.getId()));
		txtIdSerie.setEditable(false);
		panelInferior.add(txtIdSerie);
		
		panelInferior.add(new JLabel("Título de la serie:"));
		JTextField txtTitulo = new JTextField(s.getTitulo());
		panelInferior.add(txtTitulo);
		camposFormulario.put("titulo", txtTitulo);
		
		panelInferior.add(new JLabel("Duración en minutos (por capítulo promedio):"));
		JTextField txtDuracion = new JTextField(String.valueOf(s.getDuracionEnMinutos()));
		panelInferior.add(txtDuracion);
		camposFormulario.put("duracion", txtDuracion);
		
		panelInferior.add(new JLabel("Género:"));
		JTextField txtGenero = new JTextField(s.getGenero());
		panelInferior.add(txtGenero);
		camposFormulario.put("genero", txtGenero);
		
		panelInferior.add(new JLabel("Año de estreno:"));
		JTextField txtAnioEstreno = new JTextField(String.valueOf(s.getAnioEstreno()));
		panelInferior.add(txtAnioEstreno);
		camposFormulario.put("fechaEstreno", txtAnioEstreno);
	}
	
	private void crearCamposDocumental(Documental d) {

		panelInferior.add(new JLabel("ID Documental:"));
		JTextField txtId = new JTextField(String.valueOf(d.getId()));
		txtId.setEditable(false);
		panelInferior.add(txtId);
		
		panelInferior.add(new JLabel("Título del documental:"));
		JTextField txtTitulo = new JTextField(d.getTitulo());
		panelInferior.add(txtTitulo);
		camposFormulario.put("titulo", txtTitulo);
		
		panelInferior.add(new JLabel("Duración en minutos:"));
		JTextField txtDuracion = new JTextField(String.valueOf(d.getDuracionEnMinutos()));
		panelInferior.add(txtDuracion);
		camposFormulario.put("duracion", txtDuracion);
		
		panelInferior.add(new JLabel("Género:"));
		JTextField txtGenero = new JTextField(d.getGenero());
		panelInferior.add(txtGenero);
		camposFormulario.put("genero", txtGenero);
		
		panelInferior.add(new JLabel("Tema:"));
		JTextField txtTema = new JTextField(d.getTema());
		panelInferior.add(txtTema);
		camposFormulario.put("tema", txtTema);
		
	}
	
	private void crearCamposActor(Actor a) {

		panelInferior.add(new JLabel("ID Actor:"));
		JTextField txtId = new JTextField(String.valueOf(a.getId()));
		txtId.setEditable(false);
		panelInferior.add(txtId);
		
		panelInferior.add(new JLabel("Nombre:"));
		JTextField txtNombre = new JTextField(a.getNombre());
		panelInferior.add(txtNombre);
		camposFormulario.put("nombre", txtNombre);
		
		panelInferior.add(new JLabel("Apellido:"));
		JTextField txtApellido = new JTextField(a.getApellido());
		panelInferior.add(txtApellido);
		camposFormulario.put("apellido", txtApellido);
		
		panelInferior.add(new JLabel("Nacionalidad:"));
		JTextField txtNacionalidad= new JTextField(a.getNacionalidad());
		panelInferior.add(txtNacionalidad);
		camposFormulario.put("nacionalidad", txtNacionalidad);
		
	}
	
	private void crearCamposTemporada(Temporada t) {
		
		panelInferior.add(new JLabel("ID temporada:"));
		JTextField txtIdTemporada = new JTextField(String.valueOf(t.getId()));
		txtIdTemporada.setEditable(false);
		panelInferior.add(txtIdTemporada);
		
		panelInferior.add(new JLabel("Título de la temporada:"));
		JTextField txtTituloTemporada = new JTextField(t.getTitulo());
		panelInferior.add(txtTituloTemporada);
		camposFormulario.put("titulo", txtTituloTemporada);
		
		panelInferior.add(new JLabel("Número de temporada:"));
		JTextField txtNumeroTemporada = new JTextField(String.valueOf(t.getNumTemporada()));
		panelInferior.add(txtNumeroTemporada);
		camposFormulario.put("numeroTemporada", txtNumeroTemporada);
		
		panelInferior.add(new JLabel("Cantidad de capitulos:"));
		JTextField txtCantCapitulos = new JTextField(String.valueOf(t.getCantidadCapitulos()));
		panelInferior.add(txtCantCapitulos);
		camposFormulario.put("cantidadCapitulos", txtCantCapitulos);
		
		panelInferior.add(new JLabel("Fecha de estreno AAAA-MM-DD:"));
		JTextField txtFechaEstreno = new JTextField(t.getFechaEstreno() != null ? t.getFechaEstreno().toString() : "");
		panelInferior.add(txtFechaEstreno);
		camposFormulario.put("fechaEstreno", txtFechaEstreno);
		
	}
	
	private void crearCamposInvestigador(Investigador i) {

		panelInferior.add(new JLabel("ID Investigador:"));
		JTextField txtId = new JTextField(String.valueOf(i.getId()));
		txtId.setEditable(false);
		panelInferior.add(txtId);
		
		panelInferior.add(new JLabel("Nombre:"));
		JTextField txtNombre = new JTextField(i.getNombre());
		panelInferior.add(txtNombre);
		camposFormulario.put("nombre", txtNombre);
		
		panelInferior.add(new JLabel("Apellido:"));
		JTextField txtApellido = new JTextField(i.getApellido());
		panelInferior.add(txtApellido);
		camposFormulario.put("apellido", txtApellido);
		
		panelInferior.add(new JLabel("Nacionalidad:"));
		JTextField txtNacionalidad= new JTextField(i.getNacionalidad());
		panelInferior.add(txtNacionalidad);
		camposFormulario.put("nacionalidad", txtNacionalidad);
		
		panelInferior.add(new JLabel("Area de especialidad:"));
		JTextField txtEspecialidad= new JTextField(i.getAreaEspecialidad());
		panelInferior.add(txtEspecialidad);
		camposFormulario.put("especialidad", txtEspecialidad);
	}
	
	private void crearCamposVideoYT(VideoYoutube yt) {

		panelInferior.add(new JLabel("ID Video de Youtube:"));
		JTextField txtId = new JTextField(String.valueOf(yt.getId()));
		txtId.setEditable(false);
		panelInferior.add(txtId);
		
		panelInferior.add(new JLabel("Título del video:"));
		JTextField txtTitulo = new JTextField(yt.getTitulo());
		panelInferior.add(txtTitulo);
		camposFormulario.put("titulo", txtTitulo);
		
		panelInferior.add(new JLabel("Duración en minutos:"));
		JTextField txtDuracion = new JTextField(String.valueOf(yt.getDuracionEnMinutos()));
		panelInferior.add(txtDuracion);
		camposFormulario.put("duracion", txtDuracion);
		
		panelInferior.add(new JLabel("Género:"));
		JTextField txtGenero = new JTextField(yt.getGenero());
		panelInferior.add(txtGenero);
		camposFormulario.put("genero", txtGenero);
		
		panelInferior.add(new JLabel("Canal:"));
		JTextField txtCanal = new JTextField(yt.getNombreCanal());
		panelInferior.add(txtCanal);
		camposFormulario.put("canal", txtCanal);
		
		panelInferior.add(new JLabel("Link:"));
		JTextArea txtLink = new JTextArea(yt.getLinkVideo(), 2, 25);
		txtLink.setLineWrap(true);
		txtLink.setWrapStyleWord(true);
		JScrollPane scPanLink = new JScrollPane(txtLink);
		panelInferior.add(scPanLink);
		camposFormulario.put("link", txtLink);
		
	}
	
	private void crearCamposVideoMusical(VideoMusical vm) {
		
		panelInferior.add(new JLabel("ID Video Musical:"));
		JTextField txtId = new JTextField(String.valueOf(vm.getId()));
		txtId.setEditable(false);
		panelInferior.add(txtId);
		
		panelInferior.add(new JLabel("Título del video:"));
		JTextField txtTitulo = new JTextField(vm.getTitulo());
		panelInferior.add(txtTitulo);
		camposFormulario.put("titulo", txtTitulo);
		
		panelInferior.add(new JLabel("Duración en minutos:"));
		JTextField txtDuracion = new JTextField(String.valueOf(vm.getDuracionEnMinutos()));
		panelInferior.add(txtDuracion);
		camposFormulario.put("duracion", txtDuracion);
		
		panelInferior.add(new JLabel("Género:"));
		JTextField txtGenero = new JTextField(vm.getGenero());
		panelInferior.add(txtGenero);
		camposFormulario.put("genero", txtGenero);
		
		panelInferior.add(new JLabel("Artista:"));
		JTextField txtArtista = new JTextField(vm.getArtista());
		panelInferior.add(txtArtista);
		camposFormulario.put("artista", txtArtista);
		
		panelInferior.add(new JLabel("Album:"));
		JTextField txtAlbum = new JTextField(vm.getAlbum());
		panelInferior.add(txtAlbum);
		camposFormulario.put("album", txtAlbum);
		
		panelInferior.add(new JLabel("Año de lanzamiento:"));
		JTextField txtAnio = new JTextField(vm.getAnio());
		panelInferior.add(txtAnio);
		camposFormulario.put("anio", txtAnio);
		
	}
	
	
}
