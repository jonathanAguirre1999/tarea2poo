package ups.poo.igu;

import ups.poo.logica.*;

public class TablaConfig {
	
	//configura los nombres de las columnas de la taba de acuerdo a los atributos de cada objeto
	public static String[] obtenerNombresColumnas(String tipo) {
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
	public static Object[] mapearObjetoAFila(Object obj, String tipoEscogido) {
	    
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
