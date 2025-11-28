package ups.poo.igu;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.JTextComponent;

import ups.poo.logica.Actor;
import ups.poo.logica.Documental;
import ups.poo.logica.GestorContenidos;
import ups.poo.logica.Investigador;
import ups.poo.logica.Pelicula;
import ups.poo.logica.SerieDeTV;
import ups.poo.logica.Temporada;
import ups.poo.logica.VideoMusical;
import ups.poo.logica.VideoYoutube;


public class PanelMenuAdd extends JPanel {

	private static final long serialVersionUID = 1L;
	private GestorContenidos gestor;
	private JComboBox <String> cbTipoContenido;
	private JPanel panelCamposAdd;
	private Map<String, JTextComponent> camposFormulario = new HashMap<>();
	private JButton btnAgregar;
	private JRadioButton rbtnEmisionSi;
	private JRadioButton rbtnEmisionNo;
	private ButtonGroup grupoEmision;
	private List<SerieDeTV> seriesGuardadas;
	private JComboBox <String> cbSeriesGuardadas;
	private List<Documental> documentalesFiltrados;
	private JComboBox <String> cbDocumentalesGuardados;
	
	public PanelMenuAdd(GestorContenidos gestor) {
		this.gestor = gestor;
		setBorder(new LineBorder(Color.BLACK));
		setBackground(Estilos.COLOR_FONDO_OSCURO);
		this.setVisible(true);
		this.setSize(722, 546);
		setLayout(null);
		
		configSelectorTipo();
		
		JLabel lblTitulo = new JLabel("AGREGAR NUEVO");
		lblTitulo.setFont(Estilos.FUENTE_TITULO);
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setBounds(266, 10, 198, 30);
		add(lblTitulo);
		
		this.cbTipoContenido.setBounds(31, 38, 159, 25);
		add(this.cbTipoContenido);
		
		panelCamposAdd = new JPanel();
		panelCamposAdd.setBorder(new LineBorder(Color.BLACK, 1, true));
		panelCamposAdd.setBackground(Estilos.COLOR_FONDO_CLARO);
		panelCamposAdd.setBounds(10, 77, 702, 459);
		add(panelCamposAdd);
		
		btnAgregar = new JButton("AGREGAR");
		btnAgregar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String tipoSeleccionado = (String) cbTipoContenido.getSelectedItem();
				if(tipoSeleccionado.equals("Pelicula")) {
					agregarPelicula();
				} else if(tipoSeleccionado.equals("Serie de TV")) {
					agregarSerie();
				} else if(tipoSeleccionado.equals("Documental")) {
					agregarDocumental();
				} else if(tipoSeleccionado.equals("Actor")) {
					agregarActor();
				} else if(tipoSeleccionado.equals("Temporada")) {
					agregarTemporada();
				} else if(tipoSeleccionado.equals("Investigador")) {
					agregarInvestigador();
				} else if(tipoSeleccionado.equals("Video de Youtube")) {
					agregarVideoYT();
				} else if(tipoSeleccionado.equals("Video Musical")) {
					agregarVideoMusical();
				}		
			}
		});
		btnAgregar.setFont(Estilos.FUENTE_NORMAL);
		btnAgregar.setBounds(559, 38, 131, 30);
		add(btnAgregar);
		
		cargarFormularioCampos((String) this.cbTipoContenido.getSelectedItem());
	}
	
	private void configSelectorTipo() {
		String [] tipos = {"Pelicula", "Serie de TV", "Documental", "Actor", "Temporada", "Investigador", "Video de Youtube", "Video Musical"};
		cbTipoContenido = new JComboBox<>(tipos);
		cbTipoContenido.setFont(new Font("Segoe UI", Font.PLAIN, 12));
		
		cbTipoContenido.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String tipoElegido = (String) cbTipoContenido.getSelectedItem();
				cargarFormularioCampos(tipoElegido);
			}
		});
	}
	
	private void cargarFormularioCampos(String tipoElegido) {
		//limpia el lienzo principal
		panelCamposAdd.removeAll();
		panelCamposAdd.setLayout(new BorderLayout());
		
		//panel que contiene el titulo, el titulo cambiara dinamicamente de acuerdo a lo que se seleccione
		JPanel contenedorTitulo = new JPanel(new FlowLayout(FlowLayout.CENTER));
		JLabel lblTitulo = new JLabel("AGREGAR " + tipoElegido.toString().toUpperCase());
		lblTitulo.setFont(Estilos.FUENTE_SUBTITULO);
		contenedorTitulo.add(lblTitulo);
		contenedorTitulo.setBackground(Estilos.COLOR_FONDO_CLARO);
		panelCamposAdd.add(contenedorTitulo, BorderLayout.NORTH);
		
		//panel que contiene los campos a llenar para agregar el objeto
		//cada objeto mostrara opciones de campos a llenar distintas
		JPanel panelCentral = new JPanel();
		panelCentral.setLayout(new FlowLayout(FlowLayout.CENTER));
		panelCentral.setBackground(Estilos.COLOR_FONDO_CLARO);
		panelCentral.add(Box.createRigidArea(new Dimension(0, 30)));
		
		if(tipoElegido.equals("Pelicula")) {
			crearCamposPelicula(panelCentral);
		} else if(tipoElegido.equals("Serie de TV")) {
			crearCamposSerie(panelCentral);
		} else if(tipoElegido.equals("Documental")) {
			crearCamposDocumental(panelCentral);
		} else if(tipoElegido.equals("Actor")) {
			crearCamposActor(panelCentral);
		} else if(tipoElegido.equals("Temporada")) {
			crearCamposTemporada(panelCentral);
		} else if(tipoElegido.equals("Investigador")) {
			crearCamposInvestigador(panelCentral);
		} else if(tipoElegido.equals("Video de Youtube")) {
			crearCamposVideoYT(panelCentral);
		}  else if(tipoElegido.equals("Video Musical")) {
			crearCamposVideoMusical(panelCentral);
		}
		
		panelCamposAdd.add(panelCentral, BorderLayout.CENTER);
		
		panelCamposAdd.revalidate();
		panelCamposAdd.repaint();
	}
	
//----------------------------------------METODOS PARA CREAR LOS CAMPOS NECESARIOS PARA AGREGAR OBJETOS-----------------------
	
	
	private void crearCamposPelicula(JPanel panelCentral) {
		camposFormulario.clear();
		JPanel formulario = new JPanel();
		formulario.setLayout(new GridLayout(0, 2, 10, 30));
		formulario.setBackground(Estilos.COLOR_FONDO_CLARO);
		
		formulario.add(new JLabel("Titulo:"));
		JTextField txtTitulo = new JTextField(30);
		formulario.add(txtTitulo);
		camposFormulario.put("titulo", txtTitulo);
		
		formulario.add(new JLabel("Duración en minutos:"));
		JTextField txtDuracion = new JTextField(4);
		formulario.add(txtDuracion);
		camposFormulario.put("duracion", txtDuracion);
		
		formulario.add(new JLabel("Género:"));
		JTextField txtGenero = new JTextField(20);
		formulario.add(txtGenero);
		camposFormulario.put("genero", txtGenero);
		
		formulario.add(new JLabel("Estudio responsable:"));
		JTextField txtEstudio = new JTextField(20);
		formulario.add(txtEstudio);
		camposFormulario.put("estudio", txtEstudio);
		
		panelCentral.add(formulario);
	}
	
	private void crearCamposSerie(JPanel panelCentral) {
		camposFormulario.clear();
		JPanel formulario = new JPanel();
		formulario.setLayout(new GridLayout(0, 2, 10, 30));
		formulario.setBackground(Estilos.COLOR_FONDO_CLARO);
		
		formulario.add(new JLabel("Titulo:"));
		JTextField txtTitulo = new JTextField(30);
		formulario.add(txtTitulo);
		camposFormulario.put("titulo", txtTitulo);
		
		formulario.add(new JLabel("Duración en minutos:"));
		JTextField txtDuracion = new JTextField(4);
		formulario.add(txtDuracion);
		camposFormulario.put("duracion", txtDuracion);
		
		formulario.add(new JLabel("Género:"));
		JTextField txtGenero = new JTextField(20);
		formulario.add(txtGenero);
		camposFormulario.put("genero", txtGenero);
		
		formulario.add(new JLabel("Año de estreno:"));
		JTextField txtAnio = new JTextField(4);
		formulario.add(txtAnio);
		camposFormulario.put("anio", txtAnio);
		
		formulario.add(new JLabel("¿La serie sigue en emisión?"));
		JPanel opcionesEmision = new JPanel();
		opcionesEmision.setLayout(new FlowLayout(FlowLayout.LEFT));
		opcionesEmision.setBackground(Estilos.COLOR_FONDO_CLARO);
		
		rbtnEmisionSi = new JRadioButton("Si");
		rbtnEmisionNo = new JRadioButton("No");
		rbtnEmisionSi.setBackground(Estilos.COLOR_FONDO_CLARO);
		rbtnEmisionNo.setBackground(Estilos.COLOR_FONDO_CLARO);
		
		grupoEmision = new ButtonGroup();
		grupoEmision.add(rbtnEmisionSi);
		grupoEmision.add(rbtnEmisionNo);
		rbtnEmisionSi.setSelected(true);
		
		opcionesEmision.add(rbtnEmisionSi);
		opcionesEmision.add(rbtnEmisionNo);
		formulario.add(opcionesEmision);
		
		panelCentral.add(formulario);
	}
	
	private void crearCamposDocumental(JPanel panelCentral) {
		camposFormulario.clear();
		JPanel formulario = new JPanel();
		formulario.setLayout(new GridLayout(0, 2, 10, 30));
		formulario.setBackground(Estilos.COLOR_FONDO_CLARO);
		
		formulario.add(new JLabel("Titulo:"));
		JTextField txtTitulo = new JTextField(30);
		formulario.add(txtTitulo);
		camposFormulario.put("titulo", txtTitulo);
		
		formulario.add(new JLabel("Duración en minutos:"));
		JTextField txtDuracion = new JTextField(4);
		formulario.add(txtDuracion);
		camposFormulario.put("duracion", txtDuracion);
		
		formulario.add(new JLabel("Género:"));
		JTextField txtGenero = new JTextField(20);
		formulario.add(txtGenero);
		camposFormulario.put("genero", txtGenero);
		
		formulario.add(new JLabel("Temática:"));
		JTextField txtTema = new JTextField(20);
		formulario.add(txtTema);
		camposFormulario.put("tema", txtTema);
		
		panelCentral.add(formulario);
	}
	
	private void crearCamposActor(JPanel panelCentral) {
		camposFormulario.clear();
		JPanel formulario = new JPanel();
		formulario.setLayout(new GridLayout(0, 2, 10, 30));
		formulario.setBackground(Estilos.COLOR_FONDO_CLARO);
		
		formulario.add(new JLabel("Nombre:"));
		JTextField txtNombre = new JTextField(30);
		formulario.add(txtNombre);
		camposFormulario.put("nombre", txtNombre);
		
		formulario.add(new JLabel("Apellido:"));
		JTextField txtApellido = new JTextField(30);
		formulario.add(txtApellido);
		camposFormulario.put("apellido", txtApellido);
		
		formulario.add(new JLabel("Nacionalidad:"));
		JTextField txtNacionalidad = new JTextField(20);
		formulario.add(txtNacionalidad);
		camposFormulario.put("nacionalidad", txtNacionalidad);
		
		panelCentral.add(formulario);
	}
	
	private void crearCamposTemporada(JPanel panelCentral) {
		camposFormulario.clear();
		JPanel formulario = new JPanel();
		formulario.setLayout(new GridLayout(0, 2, 10, 30));
		formulario.setBackground(Estilos.COLOR_FONDO_CLARO);
		
		formulario.add(new JLabel("Numero de temporada:"));
		JTextField txtNumeroTemporada = new JTextField(10);
		formulario.add(txtNumeroTemporada);
		camposFormulario.put("numeroTemporada", txtNumeroTemporada);
		
		formulario.add(new JLabel("Titulo de la temporada:"));
		JTextField txtTitulo = new JTextField(30);
		formulario.add(txtTitulo);
		camposFormulario.put("titulo", txtTitulo);
		
		formulario.add(new JLabel("Cantidad de capítulos:"));
		JTextField txtCantCapitulos = new JTextField(10);
		formulario.add(txtCantCapitulos);
		camposFormulario.put("cantidadCapitulos", txtCantCapitulos);
		
		formulario.add(new JLabel("Serie a la que pertenece:"));
		this.cbSeriesGuardadas = cbListaSeriesDisponibles(gestor);
		formulario.add(this.cbSeriesGuardadas);
		
		formulario.add(new JLabel("Fecha de estreno (AAAA-MM-DD):"));
		JTextField txtFechaEstreno = new JTextField(20);
		formulario.add(txtFechaEstreno);
		camposFormulario.put("fechaEstreno", txtFechaEstreno);
		
		panelCentral.add(formulario);
	}
	
	private void crearCamposInvestigador(JPanel panelCentral) {
		camposFormulario.clear();
		JPanel formulario = new JPanel();
		formulario.setLayout(new GridLayout(0, 2, 10, 30));
		formulario.setBackground(Estilos.COLOR_FONDO_CLARO);
		
		formulario.add(new JLabel("Nombre:"));
		JTextField txtNombre = new JTextField(30);
		formulario.add(txtNombre);
		camposFormulario.put("nombre", txtNombre);
		
		formulario.add(new JLabel("Apellido:"));
		JTextField txtApellido = new JTextField(4);
		formulario.add(txtApellido);
		camposFormulario.put("apellido", txtApellido);
		
		formulario.add(new JLabel("Area de especialidad:"));
		JTextField txtEspecialidad = new JTextField(20);
		formulario.add(txtEspecialidad);
		camposFormulario.put("especialidad", txtEspecialidad);
		
		formulario.add(new JLabel("Nacionalidad:"));
		JTextField txtNacionalidad = new JTextField(20);
		formulario.add(txtNacionalidad);
		camposFormulario.put("nacionalidad", txtNacionalidad);
		
		panelCentral.add(formulario);
	}
	
	private void crearCamposVideoYT(JPanel panelCentral) {
		camposFormulario.clear();
		JPanel formulario = new JPanel();
		formulario.setLayout(new GridLayout(0, 2, 10, 30));
		formulario.setBackground(Estilos.COLOR_FONDO_CLARO);
		
		formulario.add(new JLabel("Titulo:"));
		JTextField txtTitulo = new JTextField(30);
		formulario.add(txtTitulo);
		camposFormulario.put("titulo", txtTitulo);
		
		formulario.add(new JLabel("Duración en minutos:"));
		JTextField txtDuracion = new JTextField(4);
		formulario.add(txtDuracion);
		camposFormulario.put("duracion", txtDuracion);
		
		formulario.add(new JLabel("Género:"));
		JTextField txtGenero = new JTextField(20);
		formulario.add(txtGenero);
		camposFormulario.put("genero", txtGenero);
		
		formulario.add(new JLabel("Nombre del canal:"));
		JTextField txtCanal = new JTextField(20);
		formulario.add(txtCanal);
		camposFormulario.put("canal", txtCanal);
		
		formulario.add(new JLabel("Link del video:"));
		JTextArea txtLink = new JTextArea(2, 25);
		txtLink.setLineWrap(true);
		txtLink.setWrapStyleWord(true);
		JScrollPane scPanLink = new JScrollPane(txtLink);
		formulario.add(scPanLink);
		camposFormulario.put("link", txtLink);
		
		panelCentral.add(formulario);
	}
	
	private void crearCamposVideoMusical(JPanel panelCentral) {
		camposFormulario.clear();
		JPanel formulario = new JPanel();
		formulario.setLayout(new GridLayout(0, 2, 10, 30));
		formulario.setBackground(Estilos.COLOR_FONDO_CLARO);
		
		formulario.add(new JLabel("Titulo:"));
		JTextField txtTitulo = new JTextField(30);
		formulario.add(txtTitulo);
		camposFormulario.put("titulo", txtTitulo);
		
		formulario.add(new JLabel("Duración en minutos:"));
		JTextField txtDuracion = new JTextField(4);
		formulario.add(txtDuracion);
		camposFormulario.put("duracion", txtDuracion);
		
		formulario.add(new JLabel("Género:"));
		JTextField txtGenero = new JTextField(20);
		formulario.add(txtGenero);
		camposFormulario.put("genero", txtGenero);
		
		formulario.add(new JLabel("Artista:"));
		JTextField txtArtista = new JTextField(20);
		formulario.add(txtArtista);
		camposFormulario.put("artista", txtArtista);
		
		formulario.add(new JLabel("Album:"));
		JTextField txtAlbum = new JTextField(20);
		formulario.add(txtAlbum);
		camposFormulario.put("album", txtAlbum);
		
		formulario.add(new JLabel("Año de lanzamiento:"));
		JTextField txtAnio = new JTextField(20);
		formulario.add(txtAnio);
		camposFormulario.put("anio", txtAnio);
		
		panelCentral.add(formulario);
	}
	
//------------------------------------------------ METODOS QUE CREAN EL OBJETO SEGUN LA INFORMACION QUE AGREGA EL USUARIO ---------------------------------------
	
	//metodo para agregar pelicula
	public void agregarPelicula() {
		try {
			
			String titulo = camposFormulario.get("titulo").getText();
			String duracionString = camposFormulario.get("duracion").getText();
			String genero = camposFormulario.get("genero").getText();
			String estudio = camposFormulario.get("estudio").getText();
			
			//valida que todos los campos esten llenos
			if(titulo.isBlank() || duracionString.isBlank() || genero.isBlank() || estudio.isBlank()) {
				JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados correctamente", "Error en la validación",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//aqui inician las conversiones de tipos de datos de acuerdo a lo que pide el constructor
			int duracionInt = 0;
			try {
				duracionInt = Integer.parseInt(duracionString.trim());
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Debe ingresar un número en el campo de duración", "Datos ingresados incorrectamente",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//comienza la creacion del objeto
			Pelicula nuevaPeli = new Pelicula(titulo, duracionInt, genero, estudio);
			
			gestor.agregarNuevoObj(nuevaPeli);
			
			JOptionPane.showMessageDialog(this, "Película " + titulo.toUpperCase() + " agregada con éxito a la colección.", 
					"Tarea exitosa",JOptionPane.INFORMATION_MESSAGE);
			
			camposFormulario.values().forEach(campo -> campo.setText(""));
			
		} catch (Exception e) {
			//si la creacion del objeto falla se desplegara un mensaje de error
			JOptionPane.showMessageDialog(this, "Error al agregar la película a la colección: " + e.getMessage(), "Falla de sistema.",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//metodo para agregar serie
	public void agregarSerie() {
		try {
			
			String titulo = camposFormulario.get("titulo").getText();
			String duracionString = camposFormulario.get("duracion").getText();
			String genero = camposFormulario.get("genero").getText();
			String anioEstrenoString = camposFormulario.get("anio").getText();
			boolean enEmision = rbtnEmisionSi.isSelected();
			
			
			//valida que todos los campos esten llenos
			if(titulo.isBlank() || duracionString.isBlank() || genero.isBlank() || anioEstrenoString.isBlank()) {
				JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados correctamente", "Error en la validación",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//aqui inician las conversiones de tipos de datos de acuerdo a lo que pide el constructor
			int duracionInt = 0;
			int anioEstrenoInt = 0;
			
			try {
				duracionInt = Integer.parseInt(duracionString.trim());
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Debe ingresar un número en el campo de duración", "Datos ingresados incorrectamente",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			try {
				anioEstrenoInt = Integer.parseInt(anioEstrenoString.trim());
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Debe ingresar un número en el campo de año de estreno", 
						"Datos ingresados incorrectamente",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//comienza la creacion del objeto
			SerieDeTV nuevaSerie = new SerieDeTV(titulo, duracionInt, genero, anioEstrenoInt, enEmision);
			gestor.agregarNuevoObj(nuevaSerie);
			
			JOptionPane.showMessageDialog(this, "Serie " + titulo.toUpperCase() + " agregada con éxito a la colección.", 
					"Tarea exitosa",JOptionPane.INFORMATION_MESSAGE);
			
			camposFormulario.values().forEach(campo -> campo.setText(""));
			
		} catch (Exception e) {
			//si la creacion del objeto falla se desplegara un mensaje de error
			JOptionPane.showMessageDialog(this, "Error al agregar la serie a la colección: " + e.getMessage(), "Falla de sistema.",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//metodo para agregar documental
public void agregarDocumental() {
	try {
		
		String titulo = camposFormulario.get("titulo").getText();
		String duracionString = camposFormulario.get("duracion").getText();
		String genero = camposFormulario.get("genero").getText();
		String tema = camposFormulario.get("tema").getText();
				
				
		//valida que todos los campos esten llenos
		if(titulo.isBlank() || duracionString.isBlank() || genero.isBlank() || tema.isBlank()) {
			JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados correctamente", "Error en la validación",JOptionPane.ERROR_MESSAGE);
			return;
		}
				
		//aqui inician las conversiones de tipos de datos de acuerdo a lo que pide el constructor
		int duracionInt = 0;
				
		try {
			duracionInt = Integer.parseInt(duracionString.trim());
		} catch(NumberFormatException e) {
			JOptionPane.showMessageDialog(this, "Debe ingresar un número en el campo de duración", "Datos ingresados incorrectamente",JOptionPane.ERROR_MESSAGE);
			return;
		}
				
		//comienza la creacion del objeto
		Documental nuevoDocu= new Documental(titulo, duracionInt, genero, tema);
		gestor.agregarNuevoObj(nuevoDocu);
				
		JOptionPane.showMessageDialog(this, "Documental " + titulo.toUpperCase() + " agregado con éxito a la colección.", 
				"Tarea exitosa",JOptionPane.INFORMATION_MESSAGE);
			
		camposFormulario.values().forEach(campo -> campo.setText(""));
				
	} catch (Exception e) {
		//si la creacion del objeto falla se desplegara un mensaje de error
		JOptionPane.showMessageDialog(this, "Error al agregar el documental a la colección: " + e.getMessage(), "Falla de sistema.",JOptionPane.ERROR_MESSAGE);
	}
}
		
	//metodo para agregar actor
	public void agregarActor() {
		try {
			
			String nombre = camposFormulario.get("nombre").getText();
			String apellido = camposFormulario.get("apellido").getText();
			String nacionalidad = camposFormulario.get("nacionalidad").getText();
			
			//valida que todos los campos esten llenos
			if(nombre.isBlank() || apellido.isBlank() || nacionalidad.isBlank()) {
				JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados correctamente", "Error en la validación",JOptionPane.ERROR_MESSAGE);
				return;
			}
					
			//aqui inician las conversiones de tipos de datos de acuerdo a lo que pide el constructor
			//para este objeto no se requiere conversion de datos
			
			//comienza la creacion del objeto
			Actor nuevoActor= new Actor(nombre, apellido, nacionalidad);
			
			boolean exito = gestor.agregarNuevoObj(nuevoActor);
			
			if(exito) JOptionPane.showMessageDialog(this, "Actor " + nombre.toUpperCase() + " " + apellido.toUpperCase() + " agregado con éxito a la colección.", 
					"Tarea exitosa",JOptionPane.INFORMATION_MESSAGE);
			if(!exito) JOptionPane.showMessageDialog(this, "El actor ya existe, verifique la información e intente nuevamente", 
					"Advertencia de duplicado",JOptionPane.WARNING_MESSAGE);
				
			camposFormulario.values().forEach(campo -> {
				
				if(campo instanceof JTextComponent) {
					campo.setText("");
				}
			});
						
		} catch (Exception e) {
			//si la creacion del objeto falla se desplegara un mensaje de error
			JOptionPane.showMessageDialog(this, "Error al agregar el actor a la colección: " + e.getMessage(), "Falla de sistema.",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//metodo que agrega una temporada
	private void agregarTemporada() {
		try {
			
			String numTemporadaString = camposFormulario.get("numeroTemporada").getText();
			String tituloTemporada = camposFormulario.get("titulo").getText();
			String cantidadCapitulosString= camposFormulario.get("cantidadCapitulos").getText();
			String fechaEstrenoString = camposFormulario.get("fechaEstreno").getText();
			
			String tituloSerie = (String) this.cbSeriesGuardadas.getSelectedItem();
			
			//valida que todos los campos esten llenos
			if(numTemporadaString.isBlank() || tituloTemporada.isBlank() || cantidadCapitulosString.isBlank() || fechaEstrenoString.isBlank()) {
				JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados correctamente", "Error en la validación",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//valida que exista una serie a la cual agregarle la temporada, si no existe una serie no se agrega la temporada
			if(tituloSerie == null || tituloSerie.startsWith("(No existen series disponibles actualmente)")) {
				JOptionPane.showMessageDialog(this, "Es indispensable seleccionar una serie para agregar una temporada", 
						"Error en la asociación de datos",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//aqui se realizan las diferentes conversiones de datos como lo solicita el constructor
			int numTemporadaInt = 0;
			int cantCapitulosInt = 0;
			LocalDate fechaEstrenoFormateada;
			
			try {
				
				numTemporadaInt = Integer.parseInt(numTemporadaString.trim());
				cantCapitulosInt = Integer.parseInt(cantidadCapitulosString.trim());
				fechaEstrenoFormateada = LocalDate.parse(fechaEstrenoString);
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Número de temporada y cantidad de capítulos deben tener datos únicamente numéricos.", 
						"Error en la validación",JOptionPane.ERROR_MESSAGE);
				return;
			} catch (DateTimeParseException e) {
				JOptionPane.showMessageDialog(this, "El campo fecha de estreno debe tener el siguiente formato: AAAA-MM-DD (debe incluir los guiones intermedios)", 
						"Error en la validación",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			SerieDeTV serieAsociada = null;
			for(SerieDeTV serie : this.seriesGuardadas) {
				if(serie.getTitulo().equals(tituloSerie)) {
					serieAsociada = serie;
					break;
				}
			}
			
			if(serieAsociada == null) {
				JOptionPane.showMessageDialog(this, "No se pudo encontrar la serie en el gestor.", "ERROR LOGICO CRITICO",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//aqui comienza la creacion del objeto
			Temporada nuevaTemporada = new Temporada(numTemporadaInt, serieAsociada, tituloTemporada, cantCapitulosInt, fechaEstrenoFormateada);
			gestor.agregarNuevoObj(nuevaTemporada);
			
			JOptionPane.showMessageDialog(this, "Temporada " + numTemporadaInt + ": " + tituloTemporada.toUpperCase() + " agregado con éxito a la colección.", 
					"Tarea exitosa",JOptionPane.INFORMATION_MESSAGE);
				
			camposFormulario.values().forEach(campo -> {
				
				if(campo instanceof JTextComponent) {
					campo.setText("");
				}
			});
			
		} catch (Exception e) {
			
			//si la creacion del objeto falla se desplegara un mensaje de error
			JOptionPane.showMessageDialog(this, "Error al agregar la temporada a la colección: " + e.getMessage(), "Falla de sistema.",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//metodo para agregar investigador
	public void agregarInvestigador() {
		try {
				
			String nombre = camposFormulario.get("nombre").getText();
			String apellido = camposFormulario.get("apellido").getText();
			String nacionalidad = camposFormulario.get("nacionalidad").getText();
			String areaEspecialidad = camposFormulario.get("especialidad").getText();

			//almacenara el documental seleccionado en el programa
			String tituloDocumental = (String) this.cbDocumentalesGuardados.getSelectedItem();
			
				
			//valida que todos los campos esten llenos
			if(nombre.isBlank() || apellido.isBlank() || nacionalidad.isBlank() || areaEspecialidad.isBlank()) {
				JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados correctamente", "Error en la validación",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			Documental documentalAsociado = null;
			
			if(documentalesFiltrados != null && documentalesFiltrados.isEmpty() 
					&& !tituloDocumental.startsWith("(No existen documentales disponibles actualmente)")) {
				for(Documental docu : this.documentalesFiltrados) {
					if(docu.getTitulo().equals(tituloDocumental)) {
						documentalAsociado = docu;
						break;
					}
				}
				
			}
					
			//aqui inician las conversiones de tipos de datos de acuerdo a lo que pide el constructor
			//para este objeto no se requiere conversion de datos
						
			//comienza la creacion del objeto
			Investigador nuevoInvestigador= new Investigador(nombre, apellido, nacionalidad, areaEspecialidad, documentalAsociado);
			gestor.agregarNuevoObj(nuevoInvestigador);
			
						
			JOptionPane.showMessageDialog(this, "Investigador " + nombre.toUpperCase() + " " + apellido.toUpperCase() + " agregado con éxito a la colección.", 
					"Tarea exitosa",JOptionPane.INFORMATION_MESSAGE);
					
			camposFormulario.values().forEach(campo -> {
					
				if(campo instanceof JTextComponent) {
					campo.setText("");
				}
			});
				
		} catch (Exception e) {
			
			//si la creacion del objeto falla se desplegara un mensaje de error
			JOptionPane.showMessageDialog(this, "Error al agregar al investigador a la colección: " + e.getMessage(), "Falla de sistema.",JOptionPane.ERROR_MESSAGE);
		}
	}
	
	//metodo para agregar video de youtube
	public void agregarVideoYT() {
		try {
				
			String titulo = camposFormulario.get("titulo").getText();
			String duracionString = camposFormulario.get("duracion").getText();
			String genero = camposFormulario.get("genero").getText();
			String canal = camposFormulario.get("canal").getText();
			String link = camposFormulario.get("link").getText();
				
				
			//valida que todos los campos esten llenos
			if(titulo.isBlank() || duracionString.isBlank() || genero.isBlank() || canal.isBlank() || link.isBlank()) {
				JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados correctamente", "Error en la validación",JOptionPane.ERROR_MESSAGE);
				return;
			}
				
			//aqui inician las conversiones de tipos de datos de acuerdo a lo que pide el constructor
			int duracionInt = 0;
			
			try {
				
				duracionInt = Integer.parseInt(duracionString.trim());
				
			} catch (NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Duracion tener datos únicamente numéricos.", 
						"Error en la validación",JOptionPane.ERROR_MESSAGE);
				return;
			}
			
			//comienza la creacion del objeto
			VideoYoutube nuevoVideoYT = new VideoYoutube(titulo, duracionInt, genero, canal, link);
			gestor.agregarNuevoObj(nuevoVideoYT);
				
			JOptionPane.showMessageDialog(this, "Video de Youtube " + titulo.toUpperCase() + " agregado con éxito a la colección.", 
					"Tarea exitosa",JOptionPane.INFORMATION_MESSAGE);
				
			camposFormulario.values().forEach(campo -> campo.setText(""));
				
		} catch (Exception e) {
			//si la creacion del objeto falla se desplegara un mensaje de error
			JOptionPane.showMessageDialog(this, "Error al agregar el video de Youtube a la colección: " + e.getMessage(), "Falla de sistema.",JOptionPane.ERROR_MESSAGE);
		}
	}
		
	//metodo para agregar video musical
	public void agregarVideoMusical() {
		try {
				
			String titulo = camposFormulario.get("titulo").getText();
			String duracionString = camposFormulario.get("duracion").getText();
			String genero = camposFormulario.get("genero").getText();
			String artista = camposFormulario.get("artista").getText();
			String anio = camposFormulario.get("anio").getText();
			String album = camposFormulario.get("album").getText();
				
				
			//valida que todos los campos esten llenos
			if(titulo.isBlank() || duracionString.isBlank() || genero.isBlank() || artista.isBlank() || anio.isBlank() || album.isBlank()) {
				JOptionPane.showMessageDialog(this, "Todos los campos deben ser llenados correctamente", "Error en la validación",JOptionPane.ERROR_MESSAGE);
				return;
			}
				
			//aqui inician las conversiones de tipos de datos de acuerdo a lo que pide el constructor
			int duracionInt = 0;
			int anioEstrenoInt = 0;
				
			try {
				duracionInt = Integer.parseInt(duracionString.trim());
				anioEstrenoInt = Integer.parseInt(anio.trim());
			} catch(NumberFormatException e) {
				JOptionPane.showMessageDialog(this, "Los campos de año y duración deben tener datos únicamente numéricos", 
						"Datos ingresados incorrectamente",JOptionPane.ERROR_MESSAGE);
				return;
			}
				
			
			//comienza la creacion del objeto
			VideoMusical nuevoVideoMusical = new VideoMusical(titulo, duracionInt, genero, artista, album, anioEstrenoInt);
			gestor.agregarNuevoObj(nuevoVideoMusical);
				
				JOptionPane.showMessageDialog(this, "Video musical " + titulo.toUpperCase() + " del artista " + artista.toUpperCase() + 
						" agregado con éxito a la colección.", 
						"Tarea exitosa",JOptionPane.INFORMATION_MESSAGE);
				
				camposFormulario.values().forEach(campo -> campo.setText(""));
				
			} catch (Exception e) {
				//si la creacion del objeto falla se desplegara un mensaje de error
				JOptionPane.showMessageDialog(this, "Error al agregar la serie a la colección: " + e.getMessage(), "Falla de sistema.",JOptionPane.ERROR_MESSAGE);
			}
		}
	
//------------------------------------------------ METODOS AUXILIARES---------------------------------------------------------------------
	
	
	//crea el ComboBox que despliega las opciones de Series que estan disponibles para crear la relacion entre temporadas y series
	private JComboBox <String> cbListaSeriesDisponibles(GestorContenidos gestor){
		this.seriesGuardadas = gestor.obtenerTodos(SerieDeTV.class);
		
		if(this.seriesGuardadas == null || this.seriesGuardadas.isEmpty()) {
			JOptionPane.showMessageDialog(this, "No existen series guardadas actualmente. Agregue una serie y vuelva a intentarlo.", 
					"ADVERTENCIA", JOptionPane.WARNING_MESSAGE);
			return new JComboBox<>(new String[] {"(No existen series disponibles actualmente)"});
		}
		
		String [] titulos = this.seriesGuardadas.stream().map(SerieDeTV :: getTitulo).toArray(String[] :: new);
		return new JComboBox<>(titulos);
	}
	
}
