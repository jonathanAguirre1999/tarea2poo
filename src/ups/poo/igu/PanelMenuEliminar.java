package ups.poo.igu;

import ups.poo.logica.*;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

public class PanelMenuEliminar extends JPanel {

	private static final long serialVersionUID = 1L;
	private GestorContenidos gestor;
    private final String [] objetosEliminables = {"Peliculas", "Series de TV", "Documentales", "Actores", "Temporadas", "Investigadores",
            "Videos de Youtube", "Videos Musicales"};
    private JComboBox <String> cbObjetosEliminables;
    private DefaultTableModel modeloTabla;
    private JTable tablaObjetos;
    private JScrollPane scPanelTabla;
    private JButton btnEliminar;

	public PanelMenuEliminar(GestorContenidos gestor) {
		this.gestor = gestor;
		setBorder(new LineBorder(new Color(0, 0, 0)));
        setBackground(new Color(121, 134, 203));
        
        this.setVisible(true);
        this.setSize(722, 546);
        setLayout(null);
        
        JLabel lblTitulo = new JLabel("ELIMINAR OBJETOS");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI Black", Font.PLAIN, 18));
        lblTitulo.setBounds(267, 10, 198, 30);
        add(lblTitulo);
        
        JLabel lblDatoAEliminar = new JLabel("Seleccione el tipo de objeto a eliminar:");
        lblDatoAEliminar.setHorizontalAlignment(SwingConstants.LEFT);
        lblDatoAEliminar.setFont(new Font("Segoe UI Semibold", Font.PLAIN, 16));
        lblDatoAEliminar.setBounds(10, 46, 300, 30);
        add(lblDatoAEliminar);
        
        cbObjetosEliminables = new JComboBox<>(objetosEliminables);
        cbObjetosEliminables.setBounds(460, 50, 250, 28);
        add(cbObjetosEliminables);
        
        inicializarTabla();
        
        JPanel panelTablaContenedor = new JPanel();
        panelTablaContenedor.setBackground(new Color(197, 202, 233));
        panelTablaContenedor.setBounds(10, 86, 702, 389); 
        panelTablaContenedor.setLayout(new BorderLayout());
        panelTablaContenedor.add(scPanelTabla, BorderLayout.CENTER);
        add(panelTablaContenedor);
		
        btnEliminar = new JButton("ELIMINAR INFORMACION");
        btnEliminar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEliminar.setBounds(507, 485, 205, 27);
        btnEliminar.setEnabled(false);
        add(btnEliminar);
        
        configAcciones();
        
	}
	
	//inicializa la tabla
	private void inicializarTabla() {
        modeloTabla = new DefaultTableModel();
        tablaObjetos = new JTable(modeloTabla) {
            private static final long serialVersionUID = 1L;
            @Override
            public boolean isCellEditable(int row, int colum) {
                return false;
            }
        };
        tablaObjetos.setBackground(new Color(197, 202, 233));
        tablaObjetos.setFillsViewportHeight(true);
        scPanelTabla = new JScrollPane(tablaObjetos);
    }
	
	//configura las acciones a realizar
	private void configAcciones() {
        cbObjetosEliminables.addActionListener(e -> cargarTablaEliminar());
        
        // el boton de eliminar se habilita o deshabilita en funcion de si esta seleccionado un objeto
        tablaObjetos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                 btnEliminar.setEnabled(tablaObjetos.getSelectedRow() != -1);
            }
        });

        // confiigura el boton eliminar
        btnEliminar.addActionListener(e -> eliminarObjetoSeleccionado());
        
        cargarTablaEliminar();
    }
	
	//carga la tabla de datos a eliminar
	private void cargarTablaEliminar() {
		String tipoSeleccionado = (String) cbObjetosEliminables.getSelectedItem();
	    
	    
	    List<?> listaObjetos = obtenerListaPorTipo(tipoSeleccionado);
	    String[] nombresColumnas = obtenerNombresColumnas(tipoSeleccionado); 
	    
	    modeloTabla.setColumnIdentifiers(nombresColumnas);
	    modeloTabla.setRowCount(0);
	
	    if (listaObjetos != null && !listaObjetos.isEmpty()) {
	        for (Object obj : listaObjetos) {
	            Object[] fila = mapearObjetoAFila(obj, tipoSeleccionado); 
	            if (fila != null) {
	                modeloTabla.addRow(fila);
	            }
	        }
	    } else {
	        modeloTabla.addRow(new Object[]{"No hay datos disponibles para " + tipoSeleccionado + "."});
	    }
	    
	    tablaObjetos.repaint();
	    btnEliminar.setEnabled(false);
    }
	
	//logica de eliminacion de objetos
	private void eliminarObjetoSeleccionado() {
        int filaSeleccionada = tablaObjetos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un objeto a ser eliminado", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            int idObjeto = (int) modeloTabla.getValueAt(filaSeleccionada, 0);
            String tipoSeleccionado = (String) cbObjetosEliminables.getSelectedItem();
            
            int confirmacion = JOptionPane.showConfirmDialog(this, 
                "El objeto de ID " + idObjeto + " (" + tipoSeleccionado + ") será eliminado permanentemente de su sistema, esta acción no se puede deshacer. "
                		+ "¿Está seguro de querer eliminarlo?" , 
                "Confirmar Eliminación", 
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
            
            if (confirmacion == JOptionPane.YES_OPTION) {
                boolean confirmacionEliminacion = gestor.eliminarPorId(idObjeto, tipoSeleccionado); 
                
                if (confirmacionEliminacion) {
                    JOptionPane.showMessageDialog(this, "Se ha eliminado el objeto y todas sus referencias", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                    cargarTablaEliminar(); 
                } else {
                    JOptionPane.showMessageDialog(this, "No se pudo eliminar el objeto, asegúrese de que la información es correcta", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar el objeto: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
	
	
//----------------------------------------METODOS AUXILIARES-------------------------------------------------------
	
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
	
	//obtiene los nombres de las columnas de las tablas
	private String[] obtenerNombresColumnas(String tipo) {
	    switch (tipo) {
	        case "Peliculas":
	            return new String[] {"ID", "Título", "Duración(min)", "Género", "Estudio"};
	        case "Documentales":
	            return new String[] {"ID", "Título", "Duración(min)", "Género", "Tema"};
	        case "Series de TV":
	            return new String[] {"ID", "Título", "Duración(min/capitulo)", "Género", "Año de estreno"};
	        case "Actores":
	            return new String[] {"ID", "Nombre", "Apellido", "Nacionalidad"};
	        case "Temporadas":
	            return new String[] {"ID", "Título", "Número de temporada", "Cantidad de Capítulos", "Fecha Estreno"};
	        case "Investigadores":
	            return new String[] {"ID", "Nombre", "Apellido", "Nacionalidad", "Area de especialidad"};
	        case "Videos de Youtube":
	            return new String[] {"ID", "Titulo", "Duracion(min)", "Género", "Canal", "Link"};
	        case "Videos Musicales":
	            return new String[] {"ID", "Título", "Duracion(min)", "Género", "Artista", "Album", "Año de lanzamiento"};
	        default:
	            return new String[] {"ID", "Objeto"};
	    }
	}
	
	//convierte los datos de los objetos en datos para la tabla
	private Object[] mapearObjetoAFila(Object obj, String tipoEscogido) {
	    if (obj == null) return null;
	
	    switch (tipoEscogido) {
	        case "Peliculas":
	            Pelicula p = (Pelicula) obj;
	            return new Object[] {
	                p.getId(), 
	                p.getTitulo(), 
	                p.getDuracionEnMinutos(),
	                p.getGenero(), 
	                p.getEstudio()
	            };
	            
	        case "Documentales":
	            Documental d = (Documental) obj;
	            return new Object[] {
	                d.getId(), 
	                d.getTitulo(), 
	                d.getDuracionEnMinutos(),
	                d.getGenero(), 
	                d.getTema()
	            };
	            
	        case "Series de TV":
	            SerieDeTV s = (SerieDeTV) obj;
	            return new Object[] {
	                s.getId(), 
	                s.getTitulo(), 
	                s.getDuracionEnMinutos(),
	                s.getGenero(), 
	                s.getAnioEstreno() 
	            };
	            
	        case "Actores":
	            Actor a = (Actor) obj;
	            return new Object[] {
	                a.getId(), 
	                a.getNombre(), 
	                a.getApellido(),
	                a.getNacionalidad()
	            };
	            
	        case "Temporadas":
	            Temporada t = (Temporada) obj;
	            return new Object[] {
	                t.getId(), 
	                t.getTitulo(),
	                t.getNumTemporada(), 
	                t.getCantidadCapitulos(), 
	                t.getFechaEstreno()
	            };
	            
	        case "Investigadores":
	            Investigador i = (Investigador) obj;
	            return new Object[] {
	                i.getId(), 
	                i.getNombre(), 
	                i.getApellido(),
	                i.getNacionalidad(), 
	                i.getAreaEspecialidad()
	            };
	            
	        case "Videos de Youtube":
	            VideoYoutube yt = (VideoYoutube) obj;
	            return new Object[] {
	                yt.getId(), 
	                yt.getTitulo(), 
	                yt.getDuracionEnMinutos(),
	                yt.getGenero(), 
	                yt.getNombreCanal(), 
	                yt.getLinkVideo()
	            };
	            
	        case "Videos Musicales":
	            VideoMusical vm = (VideoMusical) obj;
	            return new Object[] {
	                vm.getId(), 
	                vm.getTitulo(), 
	                vm.getDuracionEnMinutos(), 
	                vm.getGenero(), 
	                vm.getArtista(), 
	                vm.getAlbum(),
	                vm.getAnio()
	            };
	            
	        default:
	            return new Object[]{};
	    }
	}
	
}
