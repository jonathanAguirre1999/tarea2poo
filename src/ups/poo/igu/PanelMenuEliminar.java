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
		setBorder(new LineBorder(Color.BLACK));
        setBackground(Estilos.COLOR_FONDO_OSCURO);
        
        this.setVisible(true);
        this.setSize(722, 546);
        setLayout(null);
        
        JLabel lblTitulo = new JLabel("ELIMINAR OBJETOS");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(Estilos.FUENTE_TITULO);
        lblTitulo.setBounds(267, 10, 198, 30);
        add(lblTitulo);
        
        JLabel lblDatoAEliminar = new JLabel("Seleccione el tipo de objeto a eliminar:");
        lblDatoAEliminar.setHorizontalAlignment(SwingConstants.LEFT);
        lblDatoAEliminar.setFont(Estilos.FUENTE_SUBTITULO);
        lblDatoAEliminar.setBounds(10, 46, 300, 30);
        add(lblDatoAEliminar);
        
        cbObjetosEliminables = new JComboBox<>(objetosEliminables);
        cbObjetosEliminables.setBounds(460, 50, 250, 28);
        add(cbObjetosEliminables);
        
        inicializarTabla();
        
        JPanel panelTablaContenedor = new JPanel();
        panelTablaContenedor.setBackground(Estilos.COLOR_FONDO_CLARO);
        panelTablaContenedor.setBounds(10, 86, 702, 389); 
        panelTablaContenedor.setLayout(new BorderLayout());
        panelTablaContenedor.add(scPanelTabla, BorderLayout.CENTER);
        add(panelTablaContenedor);
		
        btnEliminar = new JButton("ELIMINAR INFORMACION");
        btnEliminar.setFont(Estilos.FUENTE_BOTON);
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
        tablaObjetos.setBackground(Estilos.COLOR_FONDO_CLARO);
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
	    String[] nombresColumnas = TablaConfig.obtenerNombresColumnas(tipoSeleccionado); 
	    
	    modeloTabla.setColumnIdentifiers(nombresColumnas);
	    modeloTabla.setRowCount(0);
	
	    if (listaObjetos != null && !listaObjetos.isEmpty()) {
	        for (Object obj : listaObjetos) {
	            Object[] fila = TablaConfig.mapearObjetoAFila(obj, tipoSeleccionado); 
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
                boolean confirmacionEliminacion = gestor.elimObj(idObjeto); 
                
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
	
}
