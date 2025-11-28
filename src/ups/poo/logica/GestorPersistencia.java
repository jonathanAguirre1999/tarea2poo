package ups.poo.logica;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

public class GestorPersistencia {
	private static final String ARCHIVO_DATOS = "sistema_audiovisual.bin";
	
	
//---------------------------------METODOS DE PERSISTENCIA DE DATOS---------------------------------------------------
	
	//guarda todos los datos del sistema en un archivo binario con serializacion
	public void guardarEstado(GestorContenidos gestor) {
		
		System.out.println("Guardando datos del sistema en " + ARCHIVO_DATOS + "....");
		
		try (FileOutputStream archivoSer = new FileOutputStream(ARCHIVO_DATOS);
				ObjectOutputStream objetoSer = new ObjectOutputStream(archivoSer)){
			
			//este metodo escribe en el archivo todas las listas presentes y el contador de IDs
			objetoSer.writeObject(gestor.getPeliculas());
			objetoSer.writeObject(gestor.getSeries());
			objetoSer.writeObject(gestor.getDocumentales());
			objetoSer.writeObject(gestor.getActores());
			objetoSer.writeObject(gestor.getInvestigadores());
			objetoSer.writeObject(gestor.getTemporadas());
			objetoSer.writeObject(gestor.getVideosYT());
			objetoSer.writeObject(gestor.getVideosMusicales());
			objetoSer.writeObject(gestor.getContadorID());
			
			System.out.println("Estado guardado con éxito");
			
		} catch (Exception e) {
			
			System.err.println("Error al guardar: " + e.getMessage());
			e.printStackTrace();
		}
		
		
	}
	
	//carga todos los datos del sistema desde el archvo .bin
	@SuppressWarnings("unchecked")
	public boolean cargarEstado(GestorContenidos gestor) {
		
		File archivo = new File(ARCHIVO_DATOS);
		
		if (!archivo.exists()) {
			System.out.println("El archivo de datos no existe, se inicializa la aplicacion con listas vacias");
			return false;
		}
		
		System.out.println("Cargando datos desde " + ARCHIVO_DATOS);
		
		try (FileInputStream archivoInput = new FileInputStream(archivo);
				ObjectInputStream objetosInput = new ObjectInputStream(archivoInput)){
			
			//se leen todas las listas en el orden que fueron guardadas y carga el contador de IDs
			gestor.setPeliculas((List<Pelicula>) objetosInput.readObject());
			gestor.setSeries((List<SerieDeTV>) objetosInput.readObject());
			gestor.setDocumentales((List<Documental>) objetosInput.readObject());
			gestor.setActores((List<Actor>) objetosInput.readObject());
			gestor.setInvestigadores((List<Investigador>) objetosInput.readObject());
			gestor.setTemporadas((List<Temporada>) objetosInput.readObject());
			gestor.setVideosyt((List<VideoYoutube>) objetosInput.readObject());
			gestor.setVideosMusicales((List<VideoMusical>) objetosInput.readObject());
			int idGuardado = (int) objetosInput.readObject();
			gestor.setContadorID(idGuardado);
			
			System.out.println("Datos cargados correctamente");
			return true;
		} catch (IOException | ClassNotFoundException e) {
			
			System.err.println("Error cargando datos: " + e.getMessage());
			e.printStackTrace();
			return false;

		}
	}
	
	//guarda (exporta) todos los datos a un archivo CSV
	public boolean exportarDatosCSV(GestorContenidos gestor, String rutaArchivo, String tipoSeleccionado) {
		
		try (FileWriter fw = new FileWriter(rutaArchivo);
				PrintWriter pw = new PrintWriter(new BufferedWriter(fw))) {
						
			switch (tipoSeleccionado) {
				
				case "Peliculas":
					exportarPeliculas(gestor.getPeliculas(), pw);
					break;
				case "Series de TV":
					exportarSeries(gestor.getSeries(), pw);
					break;
				case "Documentales":
					exportarDocumentales(gestor.getDocumentales(), pw);
					break;
				case "Actores":
					exportarActores(gestor.getActores(), pw);
					break;
				case "Temporadas":
					exportarTemporadas(gestor.getTemporadas(), pw);
					break;
				case "Investigadores":
					exportarInvestigadores(gestor.getInvestigadores(), pw);
					break;
				case "Videos de Youtube":
					exportarVideosYT(gestor.getVideosYT(), pw);
					break;
				case "Videos Musicales":
					exportarVideosMusicales(gestor.getVideosMusicales(), pw);
					break;
				default:
					throw new IllegalArgumentException("Tipo de objeto no exportable: " + tipoSeleccionado);
			}
			
			System.out.println("Exportación completada con éxito");
			return true;
		} catch (IOException e) {
			System.err.println("Error de exportación: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
		
	}
	
	
//=========================METODOS AUXILIARES Y ESPECIFICOS PARA LA EXPORTACION============================================
	
	//EXPORTA PELICULAS
	private void exportarPeliculas(List<Pelicula> peliculas ,PrintWriter pw) {
		pw.println("ID,Título,Duración(min),Género,Estudio,IDs Actores asociados".toUpperCase());
		for (Pelicula p : peliculas) {
			String idsActores = (p.getActores() != null) ? p.getActores().stream().map(actor -> String.valueOf(actor.getId())).collect(Collectors.joining("|")) : "";
			
			pw.println(String.join(",", String.valueOf(p.getId()), 
					corregirFormato(p.getTitulo()), 
					String.valueOf(p.getDuracionEnMinutos()), 
					corregirFormato(p.getGenero()), 
					corregirFormato(p.getEstudio()), 
					idsActores
					));
		}
	}
	
	//EXPORTA SERIES
	private void exportarSeries(List<SerieDeTV> series ,PrintWriter pw) {
		pw.println("ID,Título,Duración Cap.,Género,Año,En emisión actualmente,IDs Temporadas asociadas".toUpperCase());
		for (SerieDeTV s : series) {
			String idsTemporadas = (s.getTempo() != null) ? 
					s.getTempo().stream().map(temporada -> String.valueOf(temporada.getId())).collect(Collectors.joining("|")) : "";
			
			pw.println(String.join(",", String.valueOf(s.getId()),
					corregirFormato(s.getTitulo()), 
					String.valueOf(s.getDuracionEnMinutos()), 
					corregirFormato(s.getGenero()), 
					String.valueOf(s.getAnioEstreno()), 
					String.valueOf(s.isEnEmision()),
					idsTemporadas
					));	
		}
	}
	
	//EXPORTA DOCUMENTALES
	private void exportarDocumentales(List<Documental> documentales, PrintWriter pw) {
		pw.println("ID,Título,Duración,Género,Tema,ID Investigador asociado".toUpperCase());
		for (Documental d : documentales) {
			String idInvestigador = (d.getInvesti() != null) ? String.valueOf(d.getInvesti().getId()) : "";
			
			pw.println(String.join(",", String.valueOf(d.getId()), 
					corregirFormato(d.getTitulo()), 
					String.valueOf(d.getDuracionEnMinutos()), 
					corregirFormato(d.getGenero()), 
					corregirFormato(d.getTema()),
					idInvestigador
					));
		}
	}
	
	//EXPORTA ACTORES
	private void exportarActores(List<Actor> actores, PrintWriter pw) {
		pw.println("ID,Nombre,Apellido,Nacionalidad,IDs Peliculas asociadas".toUpperCase());
		for (Actor a : actores) {
			String idsPeliculas = (a.getPeliculas() != null) ? 
					a.getPeliculas().stream().map(pelicula -> String.valueOf(pelicula.getId())).collect(Collectors.joining("|")) : "";
			
			pw.println(String.join(",", String.valueOf(a.getId()), 
					corregirFormato(a.getNombre()), 
					corregirFormato(a.getApellido()), 
					corregirFormato(a.getNacionalidad()), 
					idsPeliculas
					));
		}
		
	}
	
	//EXPORTA INVESTIGADORES
	private void exportarInvestigadores(List<Investigador> investigadores, PrintWriter pw) {
		pw.println("ID,Nombre,Apellido,Nacionalidad,Especialidad,ID Documental Asociado".toUpperCase());
		for (Investigador i : investigadores) {
			String idDocumental = (i.getDocum() != null) ? String.valueOf(i.getDocum().getId()) : "";
			
			pw.println(String.join(",", String.valueOf(i.getId()), 
					corregirFormato(i.getNombre()), 
					corregirFormato(i.getApellido()), 
					corregirFormato(i.getNacionalidad()), 
					corregirFormato(i.getAreaEspecialidad()), 
					idDocumental
					));
		}
		
	}
	
	//EXPORTA TEMPORADAS
	private void exportarTemporadas(List<Temporada> temporadas, PrintWriter pw) {
		pw.println("ID,ID Serie Asociada,Núm. Temporada,Título,Capítulos,Fecha Estreno".toUpperCase());
		for (Temporada t : temporadas) {
			String idSerie = (t.getSerie() != null) ? String.valueOf(t.getSerie().getId()) : "";
			
			pw.println(String.join(",", String.valueOf(t.getId()), 
					idSerie, 
					String.valueOf(t.getNumTemporada()), 
					corregirFormato(t.getTitulo()), 
					String.valueOf(t.getCantidadCapitulos()),
					String.valueOf(t.getFechaEstreno())
					));
		}
	}
	
	//EXPORTA VIDEOS DE YOUTUBE
	private void exportarVideosYT(List<VideoYoutube> videosYT, PrintWriter pw) {
		pw.println("ID,Título,Duración,Género,Canal,Link".toUpperCase());
		for (VideoYoutube yt : videosYT) {
			pw.println(String.join(",", String.valueOf(yt.getId()), 
					corregirFormato(yt.getTitulo()), 
					String.valueOf(yt.getDuracionEnMinutos()), 
					corregirFormato(yt.getGenero()), 
					corregirFormato(yt.getNombreCanal()), 
					corregirFormato(yt.getLinkVideo())
					));
		}
		
	}
	
	//EXPORTA VIDEOS MUSICALES
	private void exportarVideosMusicales(List<VideoMusical> videosMusicales, PrintWriter pw) {
		pw.println("ID,Título,Duración,Género,Artista,Álbum,Año".toUpperCase());
		for (VideoMusical vm : videosMusicales) {
			pw.println(String.join(",", String.valueOf(vm.getId()), 
					corregirFormato(vm.getTitulo()), 
					String.valueOf(vm.getDuracionEnMinutos()), 
					corregirFormato(vm.getGenero()), 
					corregirFormato(vm.getArtista()), 
					corregirFormato(vm.getAlbum()), 
					String.valueOf(vm.getAnio())
					));
		}
	}
	
	
	//LIMPIA EL FORMATO DEL DATO INGRESADO EVITANDO ERRORES DE FORMATO EN EL ARCHIVO FINAL
	private String corregirFormato(String dato) {
		if(dato == null) {
			return "";
		}
		
		if(dato.contains(",") || dato.contains("\"")) {
			String datoCorregido = dato.replace("\"", "\"\"");
			return "\"" + datoCorregido + "\"";
		}
		
		return dato;
	}
	
	
	
	
	
}
