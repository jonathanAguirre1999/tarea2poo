package ups.poo.logica;

import java.util.*;
import java.time.*;

public class GestorContenidos implements IControlable{
	//asignacion de id global a todos los objetos
	private static int id = 1;
	
	//colecciones (funcionan como una simil base de datos)
	private List<Pelicula> peliculas = new ArrayList<>();
    private List<SerieDeTV> series = new ArrayList<>();
    private List<Documental> documentales = new ArrayList<>();
    private List<Actor> actores = new ArrayList<>();
    private List<Investigador> investigadores = new ArrayList<>();
    private List<Temporada> temporadas = new ArrayList<>();
	
    //constructor, inicializa los datos base almacenados por defecto
    public GestorContenidos() {
    	inicializarDatosBase();
    }
    
    //getters y setters
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
    
    //generador de ID
    private int generarId() {
    	return id++;
    }
    
	//METODOS IMPLEMENTADOS DE LA INTERFAZ

	//usado para agregar nuevos objetos
	@Override
	public <T> boolean agregarNuevoObj(T objeto) {
		if(objeto instanceof Pelicula) {
			Pelicula nuevaPeli = ((Pelicula) objeto);
			if(!peliculas.contains(nuevaPeli)) {
				if(nuevaPeli.getId() == 0) {
					nuevaPeli.setId(generarId());
				}
				peliculas.add(nuevaPeli);
				return true;
			}
		} else if(objeto instanceof SerieDeTV) {
			SerieDeTV nuevaSerie = ((SerieDeTV) objeto);
			if(!series.contains(nuevaSerie)) {
				if(nuevaSerie.getId() == 0) {
					nuevaSerie.setId(generarId());
				}
				series.add(nuevaSerie);
				return true;
			}
		} else if(objeto instanceof Documental) {
			Documental nuevoDocu = ((Documental) objeto);
			if(!documentales.contains(nuevoDocu)) {
				if(nuevoDocu.getId() == 0) {	
					nuevoDocu.setId(generarId());
				}
				documentales.add(nuevoDocu);
				return true;
			}
		} else if(objeto instanceof Actor) {
			Actor nuevoActor = ((Actor) objeto);
			if(!actores.contains(nuevoActor)) {
				if(nuevoActor.getId() == 0) {
					nuevoActor.setId(generarId());
				}	
				actores.add(nuevoActor);
				return true;
			}
		} else if(objeto instanceof Temporada) {
			Temporada nuevaTempo = ((Temporada) objeto);
			if(!temporadas.contains(nuevaTempo)) {
				if(nuevaTempo.getId() == 0) {
					nuevaTempo.setId(generarId());
				}
				temporadas.add(nuevaTempo);
				return true;
			}
		} else if(objeto instanceof Investigador) {
			Investigador nuevoInves = ((Investigador) objeto);
			if(!investigadores.contains(nuevoInves)) {
				if(nuevoInves.getId() == 0) {
					nuevoInves.setId(generarId());
				}
				investigadores.add(nuevoInves);
				return true;
			}  
		}
		return false;	
	}
		
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
		} else {
			return new ArrayList <> ();
		}
	}

	//elimina un objeto basado en su ID unica
	@Override
	public boolean elimObj(int id) {
		//para peliculas
		Pelicula eliminarPeli = peliculas.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(eliminarPeli != null) {
			borrarRelacion(eliminarPeli);
			peliculas.remove(eliminarPeli);
			return true;
		}
		
		//para series
		SerieDeTV eliminarSerie = series.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(eliminarSerie != null) {
			borrarRelacion(eliminarSerie);
			series.remove(eliminarSerie);
			return true;
		}
		
		//para documentales
		Documental eliminarDocu = documentales.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(eliminarDocu != null) {
			borrarRelacion(eliminarDocu);
			documentales.remove(eliminarDocu);
			return true;
		}
		
		//para actores
		Actor eliminarActor = actores.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(eliminarActor != null) {
			borrarRelacion(eliminarActor);
			actores.remove(eliminarActor);
			return true;
		}
		
		//para temporadas
		Temporada eliminarTempo= temporadas.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(eliminarTempo != null) {
			borrarRelacion(eliminarTempo);
			temporadas.remove(eliminarTempo);
			return true;
		}
		
		//para investigadores
		Investigador eliminarInves = investigadores.stream().filter(p -> p.getId() == id).findFirst().orElse(null);
		if(eliminarInves != null) {
			borrarRelacion(eliminarInves);
			investigadores.remove(eliminarInves);
			return true;
		}
		return false;
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
		Investigador investigador1 = new Investigador ("Jon", "Sistiaga", "Política", "Español", documental1);
		
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
		this.agregarNuevoObj(documental1);
	
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
		
		return null;
	}
	
	
	
	
}
