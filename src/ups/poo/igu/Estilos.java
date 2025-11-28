package ups.poo.igu;

import java.awt.Color;
import java.awt.Font;

public class Estilos {
	
//==============================================COLORES=====================================================
	
	public static final Color COLOR_FONDO_OSCURO = new Color(121, 134, 203); //azul morado base, fondo principal
	public static final Color COLOR_FONDO_MENU_LATERAL = new Color(63, 81, 181); //morado fondo de la barra lateral
    public static final Color COLOR_FONDO_CLARO = new Color(197, 202, 233); //gris lavanda, fondo de los formularios y paneles internos
    public static final Color COLOR_BOTON_ACCION = new Color(48, 63, 159); //color botones principales
    public static final Color COLOR_TEXTO_BOTON = Color.WHITE; //blanco, color del texto de los botones principales

//==============================================FUENTES=====================================================

    public static final Font FUENTE_TITULO = new Font("Segoe UI Black", Font.PLAIN, 18);; //titulos, negrita grandes
    public static final Font FUENTE_SUBTITULO = new Font("Segoe UI Semibold", Font.PLAIN, 16); //para subtitulos y etiquetas importantes
    public static final Font FUENTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 12); //fuente normal, texto de contenido general
    public static final Font FUENTE_BOTON = Estilos.FUENTE_BOTON; //fuente para botones
	
    //CONSTRUCTOR PRIVADO IMPIDE INSTANCIACION
    private Estilos() {
    	
    }
	
}
