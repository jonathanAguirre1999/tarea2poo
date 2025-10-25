package ups.poo.logica;
import java.util.*;


// Subclase Pelicula que extiende de ContenidoAudiovisual
public class Pelicula extends ContenidoAudiovisual {
    private String estudio;
    private List <Actor> actores = new ArrayList <> ();
    
    //constructores
    public Pelicula() {
    }
    
    public Pelicula(String titulo, int duracionEnMinutos, String genero, String estudio) {
        super(titulo, duracionEnMinutos, genero);
        this.estudio = estudio;
    }

    
    //getters y setters
    public String getEstudio() {
        return estudio;
    }

    public void setEstudio(String estudio) {
        this.estudio = estudio;
    }

    public List<Actor> getActores() {
		return actores;
	}

	public void setActores(List<Actor> actores) {
		this.actores = actores;
	}

	//metodos heredados de las interfaces
	@Override
	public String mostrarDetallesCompletos() {
		StringBuilder detalles = new StringBuilder();
		detalles.append("DETALLES DE LA PELICULA\n");
		detalles.append("ID: " + this.getId() + "\n");
		detalles.append("Título: " + this.getTitulo() + "\n");
		detalles.append("Duración: " + this.getDuracionEnMinutos() + " minutos\n");
		detalles.append("Género: " + this.getGenero() + "\n");
		detalles.append("Estudio: " + this.getEstudio() + "\n");
		detalles.append("Número de actores del elenco principal: " + this.actores.size() + "\n");
		return detalles.toString();
	}

	@Override
	public <T> boolean modificarAtributo(String atributo, T nuevoValor) {
		String atributoMinus = atributo.toLowerCase();
		if("titulo".equals(atributoMinus)) {	
			if(nuevoValor instanceof String) {
				this.setTitulo((String) nuevoValor);
				return true;
			}
		} else if("duracion".equals(atributoMinus)) {
			if(nuevoValor instanceof Integer) {
				this.setDuracionEnMinutos((Integer) nuevoValor);
				return true;
			}
		} else if("genero".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setGenero((String) nuevoValor);
				return true;
			}
		} else if("estudio".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setEstudio((String) nuevoValor);
				return true;
			}
		}
		return false;
	}
	
    
    //metodo para agregar actores a la pelicula
    public void agregarActor(Actor actor) {
    	if(actor != null && !this.actores.contains(actor)) {
    		this.actores.add(actor);
    		if(!actor.getPeliculas().contains(this)) {
    			actor.getPeliculas().add(this);
    		}
    	}
    }
}