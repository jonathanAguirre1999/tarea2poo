package ups.poo.igu;

import ups.poo.logica.*;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.util.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PanelMenuConsultas extends JPanel {

	private static final long serialVersionUID = 1L;
	private final String [] objetosConsultables = {"Peliculas", "Series de TV", "Documentales", "Actores", "Temporadas", "Investigadores",
			"Videos de Youtube", "Videos Musicales"};
	private GestorContenidos gestor;
	private GestorPersistencia persistencia;
	private JComboBox<String> cbObjetoConsulta;
    private JTable tablaResultados;
    private DefaultTableModel modeloTabla;
    private JScrollPane scPanelTabla;
    private JPanel panelCentral;
    private JButton btnExportarCSV;
    private JButton btnReporteIndividual;
	
	public PanelMenuConsultas(GestorContenidos gestor) {
		this.gestor = gestor;
		setBorder(new LineBorder(Color.BLACK));
		setBackground(Estilos.COLOR_FONDO_OSCURO);
		
		this.setVisible(true);
		this.setSize(722, 546);
		setLayout(null);
		
		
		JLabel lblTitulo = new JLabel("CONSULTAR");
		lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitulo.setFont(Estilos.FUENTE_TITULO);
		lblTitulo.setBounds(267, 10, 198, 30);
		add(lblTitulo);
		
		cbObjetoConsulta = new JComboBox<>(objetosConsultables);
		cbObjetoConsulta.setBounds(136, 52, 162, 21);
		add(cbObjetoConsulta);
		
		panelCentral = new JPanel();
		panelCentral.setBackground(Estilos.COLOR_FONDO_CLARO);
		panelCentral.setBounds(10, 92, 702, 444);
		panelCentral.setLayout(new BorderLayout());
		add(panelCentral);
		
		JLabel lblDatoAConsultar = new JLabel("Seleccione:");
		lblDatoAConsultar.setHorizontalAlignment(SwingConstants.CENTER);
		lblDatoAConsultar.setFont(Estilos.FUENTE_SUBTITULO);
		lblDatoAConsultar.setBounds(10, 43, 123, 30);
		add(lblDatoAConsultar);
		
		btnExportarCSV = new JButton("REPORTE CSV");
		btnExportarCSV.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				botonExportarCSV(btnExportarCSV);
			}
		});
		btnExportarCSV.setBounds(571, 49, 128, 27);
		btnExportarCSV.setFont(Estilos.FUENTE_NORMAL);
		add(btnExportarCSV);
		
		btnReporteIndividual = new JButton("REPORTE INDIVIDUAL");
		btnReporteIndividual.setFont(Estilos.FUENTE_BOTON);
		btnReporteIndividual.setBounds(388, 49, 171, 27);
		btnReporteIndividual.setEnabled(false);
		add(btnReporteIndividual);
		
		inicializarTabla();
		
		panelCentral.add(scPanelTabla, BorderLayout.CENTER);
		tablaResultados.getSelectionModel().addListSelectionListener(e -> {
			if (!e.getValueIsAdjusting()) {
				btnReporteIndividual.setEnabled(tablaResultados.getSelectedRow() != -1);
			}
		});
		
		btnReporteIndividual.addActionListener(e -> crearReporteIndividual());
		
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
		tablaResultados = new JTable(modeloTabla) {
			private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int colum) {
                return false;
            }
		};
		tablaResultados.setRowSelectionAllowed(false);
		tablaResultados.setBackground(Estilos.COLOR_FONDO_CLARO);
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
		
		String [] nombreColumnas = TablaConfig.obtenerNombresColumnas(tipoSeleccionado);
		modeloTabla.setColumnIdentifiers(nombreColumnas);
		
		modeloTabla.setRowCount(0);
		
		if(listaObjetos.isEmpty() || listaObjetos == null) {
			if(nombreColumnas.length == 0 || (nombreColumnas.length == 1 && !nombreColumnas[0].equals("Mensaje"))) {
				modeloTabla.setColumnIdentifiers(new String[] {"Mensaje"});
			}
			modeloTabla.addRow(new Object[] {"No existen datos disponibles para " + tipoSeleccionado});
		} else {
			for(Object obj : listaObjetos) {
				Object[] fila = TablaConfig.mapearObjetoAFila(obj, tipoSeleccionado);
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
	
	//EXPORTA UN REPORTE COMPLETO A CSV
	private void botonExportarCSV(JButton btnExportarCSV) {
		
		btnExportarCSV.addActionListener(e -> {
			
			String tipoSeleccionado = (String) cbObjetoConsulta.getSelectedItem();
			
			
			JFileChooser rutaEscogida = new JFileChooser();
			rutaEscogida.setDialogTitle("Exportar como...");
			rutaEscogida.setSelectedFile(new File(tipoSeleccionado.replace(" ", "_") + ".csv"));
			FileNameExtensionFilter filtro = new FileNameExtensionFilter("Archivos CSV (*.csv)", "csv");
			rutaEscogida.setFileFilter(filtro);
			
			int eleccionUsuario = rutaEscogida.showSaveDialog(this);
			
			if (eleccionUsuario == JFileChooser.APPROVE_OPTION) {
				File archivoParaGuardar = rutaEscogida.getSelectedFile();
				String rutaArchivo = archivoParaGuardar.getAbsolutePath();
				
				//garantiza que el archivo tendra la extension .csv al ser exportado
				if (!rutaArchivo.toLowerCase().endsWith(".csv")) {
					rutaArchivo += ".csv";
				}
				
				boolean exitoExportar = persistencia.exportarDatosCSV(gestor, rutaArchivo, tipoSeleccionado);
				
				if (exitoExportar) {
					JOptionPane.showMessageDialog(this, "Datos exportados exitosamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(this, "Error al exportar datos, tarea no completada.", "Error al exportar", JOptionPane.ERROR_MESSAGE);
				}	
			}
		});
					
	}
	
	//CREA UN REPORTE INDIVIDUAL DE ACUERDO AL OBJETO SELECCIONADO EN LA TABLA
	private void crearReporteIndividual() {
		int filaSeleccionada = tablaResultados.getSelectedRow();
		if(filaSeleccionada == -1) return;
		
		try {
			int idObjeto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
			
			Object objeto = gestor.buscarPorId(idObjeto);
			
			if (objeto instanceof IConsultable) {
				String detalles = ((IConsultable) objeto).mostrarDetallesCompletos();
				
				JFileChooser ruta = new JFileChooser();
				ruta.setDialogTitle("Guardar archivo");
				
				String nombreSugerido = "Reporte_ID_" + idObjeto + ".txt";
				if(objeto instanceof ContenidoAudiovisual) nombreSugerido = "Reporte_" + ((ContenidoAudiovisual) objeto).getTitulo().replace(" ", "_") + ".txt";
				if(objeto instanceof Actor) nombreSugerido = "Reporte_" + ((Actor) objeto).getNombre() + "_" + ((Actor) objeto).getApellido() + ".txt";
				if(objeto instanceof Investigador) nombreSugerido = "Reporte_" + ((Investigador) objeto).getNombre() + "_" + 
						((Investigador) objeto).getApellido() + ".txt";
				if(objeto instanceof Temporada) nombreSugerido = "Reporte_" + ((Temporada) objeto).getTitulo().replace(" ", "_") +
						((Temporada) objeto).getSerie().getTitulo().replace(" ", "_") + ".txt";
				
				ruta.setSelectedFile(new File(nombreSugerido));
				
				int seleccion = ruta.showSaveDialog(this);
				
				if (seleccion == JFileChooser.APPROVE_OPTION) {
					String rutaString = ruta.getSelectedFile().getAbsolutePath();
					if (!rutaString.toLowerCase().endsWith(".txt")) {
						rutaString += ".txt";
					}
					
					boolean exito = gestor.crearReporteIndividual(rutaString, detalles);
					
					if(exito) {
						JOptionPane.showMessageDialog(this, "Archivo de reporte guardado exitosamente", "Éxito", JOptionPane.INFORMATION_MESSAGE);
					} else {
						JOptionPane.showMessageDialog(this, "Error al guardar el archivo", "Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			} else {
				JOptionPane.showMessageDialog(this, "Este objeto no permite crear un reporte", "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(this, "Error al exportar: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	
}
