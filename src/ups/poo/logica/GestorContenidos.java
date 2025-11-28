package ups.poo.logica;

import java.util.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.*;

public class GestorContenidos implements IControlable{
	
	//asignacion de id global a todos los objetos
	private static int id = 1;
	
	//constante para el archivo de datos iniciales
	
	//colecciones (funcionan como una simil base de datos)
	private List<Pelicula> peliculas = new ArrayList<>();
    private List<SerieDeTV> series = new ArrayList<>();
    private List<Documental> documentales = new ArrayList<>();
    private List<Actor> actores = new ArrayList<>();
    private List<Investigador> investigadores = new ArrayList<>();
    private List<Temporada> temporadas = new ArrayList<>();
    private List<VideoYoutube> videosYT = new ArrayList<>();
    private List<VideoMusical> videosMusicales = new ArrayList<>();
    private GestorPersistencia persistencia;
	
    //constructor, inicializa los datos base almacenados por defecto
    public GestorContenidos() {
    	this.persistencia = new GestorPersistencia();
    	
    	if(!persistencia.cargarEstado(this)) {
    		inicializarDatosBase();
    	}	
    	    	
    }
    
    //getters y setters
    public int getContadorID() {
    	return id;
    }
    
    public void setContadorID(int idGuardado) {
    	id = idGuardado;
    }
    
    public List<Pelicula> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(List<Pelicula> peliculas) {
		this.peliculas = peliculas;
	}

	public List<SerieDeTV> getSeries() {
		return series;
	}

	public void setSeries(List<SerieDeTV> series) {
		this.series = series;
	}

	public List<Documental> getDocumentales() {
		return documentales;
	}

	public void setDocumentales(List<Documental> documentales) {
		this.documentales = documentales;
	}

	public List<Actor> getActores() {
		return actores;
	}

	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}

	public List<Investigador> getInvestigadores() {
		return investigadores;
	}

	public void setInvestigadores(List<Investigador> investigadores) {
		this.investigadores = investigadores;
	}

	public List<Temporada> getTemporadas() {
		return temporadas;
	}

	public void setTemporadas(List<Temporada> temporadas) {
		this.temporadas = temporadas;
	}
	
	public List<VideoYoutube> getVideosYT() {
		return videosYT;
	}

	public void setVideosyt(List<VideoYoutube> videosYT) {
		this.videosYT = videosYT;
	}
    
	public List<VideoMusical> getVideosMusicales() {
		return videosMusicales;
	}

	public void setVideosMusicales(List<VideoMusical> videosMusicales) {
		this.videosMusicales = videosMusicales;
	}
    //generador de ID
    private int generarId() {
    	return id++;
    }
    
	//METODOS IMPLEMENTADOS DE LA INTERFAZ

//============================================AGREGA NUEVOS OBJETOS A LA COLECCION================================================
    
	//usado para agregar nuevos objetos
	@Override
	public <T> boolean agregarNuevoObj(T objeto) {
		
		if(objeto instanceof Pelicula) return procesarAgregacion((Pelicula) objeto, peliculas);
		if(objeto instanceof SerieDeTV) return procesarAgregacion((SerieDeTV) objeto, series);
		if(objeto instanceof Documental) return procesarAgregacion((Documental) objeto, documentales);
		if(objeto instanceof Actor) return procesarAgregacion((Actor) objeto, actores);
		if(objeto instanceof Investigador) return procesarAgregacion((Investigador) objeto, investigadores);
		if(objeto instanceof Temporada) return procesarAgregacion((Temporada) objeto, temporadas);
		if(objeto instanceof VideoYoutube) return procesarAgregacion((VideoYoutube) objeto, videosYT);
		if(objeto instanceof VideoMusical) return procesarAgregacion((VideoMusical) objeto, videosMusicales);
		
		return false;	
	}
	
	//se encarga de procesar la agregacion del nuevo objeto agregado por el usuario
	private <T extends IConsultable> boolean procesarAgregacion(T objeto, List<T> lista){
		
		if (!lista.contains(objeto)) {
			
			if(objeto.getId() == 0) {
				objeto.setId(generarId());
			}
			
			lista.add(objeto);
			return true;
		}
		return false;
	}

//=============================================================BUSQUEDAS===========================================================
	
	//busqueda por titulo de contenido audiovisual (cualquier tipo)
	@Override
	public ContenidoAudiovisual buscarPorTitulo(String titulo) {
		if(titulo == null || titulo.trim().isBlank()) {
			return null;
		}
		String tituloFormateado = titulo.trim().toLowerCase();
		
		//buscador de peliculas por titulo
		for (Pelicula peli : peliculas) {
			if(peli.getTitulo().toLowerCase().equals(tituloFormateado)) {
				return peli;
			}
		}
		
		//buscador de series por titulo
		for (SerieDeTV serie : series) {
			if(serie.getTitulo().toLowerCase().equals(tituloFormateado)) {
				return serie;
			}
		}
		
		//busqueda de documentales por titulo
		for (Documental docu : documentales) {
			if(docu.getTitulo().toLowerCase().equals(tituloFormateado)) {
				return docu;
			}
		}
		
		//busqueda de videos de youtube por titulo
		for (VideoYoutube videoyt : videosYT) {
			if(videoyt.getTitulo().toLowerCase().equals(tituloFormateado)) {
				return videoyt;
			}
		}
				
		//busqueda de videos musicales por titulo
		for (VideoMusical videoMusical: videosMusicales) {
			if(videoMusical.getTitulo().toLowerCase().equals(tituloFormateado)) {
				return videoMusical;
			}
		}
		return null;
	}

	//devuelve la cantidad de peliculas de un actor
	@Override
	public int conteoPeliculasActor(Actor actor) {
		if(actor != null && actores.contains(actor)) {
			return actor.getPeliculas().size();
		} else {
			return 0;
		}
	}

	//devuelve la lista completa de objetos de una misma clase
	@Override
	@SuppressWarnings("unchecked")
	public <T> List<T> obtenerTodos(Class<T> claseNom) {
		if(claseNom.equals(Pelicula.class)) {
			return (List<T>) peliculas;
		} else if(claseNom.equals(SerieDeTV.class)) {
			return (List<T>) series;
		} else if(claseNom.equals(Documental.class)) {
			return (List<T>) documentales;
		} else if(claseNom.equals(Actor.class)) {
			return (List<T>) actores;
		} else if(claseNom.equals(Temporada.class)) {
			return (List<T>) temporadas;
		} else if(claseNom.equals(Investigador.class)) {
			return (List<T>) investigadores;
		} else if(claseNom.equals(VideoYoutube.class)) {
			return (List<T>) videosYT;
		}else if(claseNom.equals(VideoMusical.class)) {
			return (List<T>) videosMusicales;
		} else {
			return new ArrayList <> ();
		}
	}
	
	//buscar por ID
	public Object buscarPorId(int id) {
		
		//peliculas
		Object resultado;
		resultado = peliculas.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(resultado != null) return resultado;
		
		//actores
		resultado = actores.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(resultado != null) return resultado;
	
		//series
		resultado = series.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(resultado != null) return resultado;
		
		//temporadas
		resultado = temporadas.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(resultado != null) return resultado;
				
		//documentales
		resultado = documentales.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(resultado != null) return resultado;
			
		//investigadores
		resultado = investigadores.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(resultado != null) return resultado;
		
		//videos yt
		resultado = videosYT.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(resultado != null) return resultado;
		
		//videos musicales
		resultado = videosMusicales.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(resultado != null) return resultado;
		
		return null;
	}

//=====================================================ELIMINACION DE OBJETOS===========================================================
	//elimina un objeto basado en su ID unica
	@Override
	public boolean elimObj(int id) {
		
		if(eliminarDeLista(peliculas, id)) return true;
		if(eliminarDeLista(series, id)) return true;
		if(eliminarDeLista(documentales, id)) return true;
		if(eliminarDeLista(actores, id)) return true;
		if(eliminarDeLista(investigadores, id)) return true;
		if(eliminarDeLista(temporadas, id)) return true;
		if(eliminarDeLista(videosYT, id)) return true;
		if(eliminarDeLista(videosMusicales, id)) return true;
		
		return false;
	}
	
	//elimina el objeto de acuerdo a su tipo
	private <T extends IConsultable> boolean eliminarDeLista(List <T> lista, int id) {
		
		T objetoParaEliminar = lista.stream().filter(o -> o.getId() == id).findFirst().orElse(null);
		
		if (objetoParaEliminar != null) {
			borrarRelacion(objetoParaEliminar);
			lista.remove(objetoParaEliminar);
			return true;
		}
		
		return false;
	}
	
	//metodo auxiliar que borra las relaciones existentes de los objetos a ser eliminados
		private <T> void borrarRelacion(T objetoEliminado) {
			//determina si el objeto en cuestion existe o no
			if(objetoEliminado == null) {
				return;
			}
			
			//se borraran las relaciones que el objeto a eliminar tenga con otras clases
			if(objetoEliminado instanceof Pelicula peli) { //casteo implicito mediante pattern matching
				for(Actor actor : peli.getActores()) {
					actor.eliminarPelicula(peli);
				}
			} else if(objetoEliminado instanceof Actor actor) {
				for(Pelicula peli : actor.getPeliculas()) {
					peli.eliminarActor(actor);
				}
			} else if(objetoEliminado instanceof SerieDeTV serie) { //al eliminar una serie se eliminan sus temporadas del sistema tambien
				List <Temporada> temporadasParaEliminar = new ArrayList<> (serie.getTempo()); //copia de lista de temporadas asociadas a la serie
				this.temporadas.removeAll(temporadasParaEliminar) ; //elimina las temporadas asociadas a la serie
				for(Temporada tempo : temporadasParaEliminar) {
					tempo.setSerie(null); //ruptura de referencias
				}
			} else if(objetoEliminado instanceof Temporada tempo) {
				SerieDeTV serie = tempo.getSerie();
				if(serie != null) {
					serie.eliminarTemporada(tempo);
					tempo.setSerie(null);
				}
			} else if(objetoEliminado instanceof Documental docu) {
				Investigador inves = docu.getInvesti();
				if(inves != null) {
					inves.setDocum(null);
				}
			} else if(objetoEliminado instanceof Investigador inves) {
				Documental docu = inves.getDocum();
				if(docu != null) {
					docu.setInvesti(null);
				}
			}
		}
	
	private void inicializarDatosBase() {
		//1- creacion de objetos base para pruebas
		Actor actor1 = new Actor ("  Drake", "Bell  ", "Estadounidense");
		Actor actor2 = new Actor (" Andrew", "Garfield","Estadounidense");
		Actor actor3 = new Actor ("Terry", "Crews", "Estadounidense");
		Actor actor4 = new Actor ("Sara", "Paxton", "Estadounidense");
		Pelicula peli1 = new Pelicula ("Rags", 88, "Musical", "Nickelodeon");
		Pelicula peli2 = new Pelicula ("Superheroe! La pelicula", 85, "Comedia", "Dimension Films");
		Pelicula peli3 = new Pelicula ("¿Y dónde están las rubias?", 109, "Comedia", "Sony Pictures Entertainment");
		Pelicula peli4 = new Pelicula ("The Amazing Spiderman", 136, "Acción", "Columbia Pictures");
		SerieDeTV serie1 = new SerieDeTV ("Breaking Bad", 60, "Drama", 2008, false);
		Temporada temporada1 = new Temporada (1, serie1, "Primera Temporada", 7, LocalDate.of(2008, 01, 20));
		Temporada temporada2 = new Temporada (2, serie1, "Segunda Temporada", 13, LocalDate.of(2009, 03, 8));
		Documental documental1 = new Documental ("Corea del Norte: Amarás al lider por sobre todas las cosas.", 51, "Política", "Comunismo");
		Documental documental2 = new Documental ("El cazador de cocodrilos", 52, "Vida silvestre", "Reptiles");
		Investigador investigador1 = new Investigador ("Jon", "Sistiaga", "Política", "Español", documental1);
		Investigador investigador2 = new Investigador ("Steve", "Irwin", "Naturaleza", "Estadounidense", null);		
		VideoYoutube videoYT1 = new VideoYoutube ("¿Por qué todos odian a Resident Evil 6?", 14, "Gaming", "El Reporte de W", 
				"https://www.youtube.com/watch?v=bvM1YyIbl68&t=597s");
		VideoMusical videoMusical1 = new VideoMusical ("Worldwide", 3, "Pop", "Big Time Rush", "Big Time Rush", 2011);
		
		//establecimiento de relaciones 
		actor1.agregarPelicula(peli1);
		actor1.agregarPelicula(peli2);
		actor2.agregarPelicula(peli4);
		actor3.agregarPelicula(peli3);
		actor4.agregarPelicula(peli2);
		
		peli1.agregarActor(actor1);
		peli2.agregarActor(actor1);
		peli2.agregarActor(actor4);
		peli3.agregarActor(actor3);
		peli4.agregarActor(actor2);
		
		serie1.agregarTemporada(temporada1);
		serie1.agregarTemporada(temporada2);
		
		documental1.setInvesti(investigador1);
		
		//asignacion de IDs y clasificacion en la coleccion principal
		//ACTORES
		this.agregarNuevoObj(actor1);
		this.agregarNuevoObj(actor2);
		this.agregarNuevoObj(actor3);
		this.agregarNuevoObj(actor4);
		
		//PELICULAS
		this.agregarNuevoObj(peli1);
		this.agregarNuevoObj(peli2);
		this.agregarNuevoObj(peli3);
		this.agregarNuevoObj(peli4);
		
		//SERIES - TEMPORADAS
		this.agregarNuevoObj(serie1);
		this.agregarNuevoObj(temporada1);
		this.agregarNuevoObj(temporada2);
		
		//INVESTIGADOR - DOCUMENTAL
		this.agregarNuevoObj(investigador1);
		this.agregarNuevoObj(investigador2);
		this.agregarNuevoObj(documental1);
		this.agregarNuevoObj(documental2);
		
		//VIDEOS DE YT Y MUSICALES
		this.agregarNuevoObj(videoYT1);
		this.agregarNuevoObj(videoMusical1);
	
	}
	
	//EXPORTA UN REPORTE INDIVIDUAL PARA CADA OBJETO SELECCIONADO EN CONSULTAS
	public boolean crearReporteIndividual(String rutaArchivo, String contenidoTexto) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(rutaArchivo))){
			bw.write(contenidoTexto);
			return true;
		} catch (Exception e) {
			System.err.println("Error de escritura: " + e.getMessage());
			e.printStackTrace();
			return false;
		}
	}
	
	
	//METODO QUE ACCESA A LA PERSISTENCIA PARA GUARADR LOS DATOS
	public void guardarEstado() {
	    if (this.persistencia != null) {
	        this.persistencia.guardarEstado(this);
	    } else {
	        System.err.println("Error crítico: El servicio de persistencia no ha sido inicializado.");
	    }
	}
	
}
