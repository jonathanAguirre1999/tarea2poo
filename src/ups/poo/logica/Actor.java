package ups.poo.logica;
import java.util.*;

public class Actor implements IConsultable, IModificable{
	
	private String nombre;
	private String apellido;
	private String personajeInterpretado;
	private String tipoPersonaje;
	private List <Pelicula> peliculas = new ArrayList <> (); //relaciona la clase con Pelicula
		
	// constructores
	public Actor() {
	}
	
	public Actor(String nombre, String apellido, String personajeInterpretado, String tipoPersonaje) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.personajeInterpretado = personajeInterpretado;
		this.tipoPersonaje = tipoPersonaje;
	}
	

	//lista de getters y setters
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido;
	}
	public String getPersonajeInterpretado() {
		return personajeInterpretado;
	}
	public void setPersonajeInterpretado(String personajeInterpretado) {
		this.personajeInterpretado = personajeInterpretado;
	}
	public String getTipoPersonaje() {
		return tipoPersonaje;
	}
	public void setTipoPersonaje(String tipoPersonaje) {
		this.tipoPersonaje = tipoPersonaje;
	}
	public List <Pelicula> getPeliculas() {
		return peliculas;
	}

	public void setPeliculas(List <Pelicula> peliculas) {
		this.peliculas = peliculas;
	} 
	
	

	//metodos heredados de las interfaces
	@Override
	public <T> boolean modificarAtributo(String atributo, T nuevoValor) {
		String atributoMinus = atributo.toLowerCase();
		if("nombre".equals(atributoMinus)) {	
			if(nuevoValor instanceof String) {
				this.setNombre((String) nuevoValor);
				return true;
			}
		} else if("apellido".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setApellido((String) nuevoValor);
				return true;
			}
		} else if("personajeinterpretado".equals(atributoMinus) || "personaje interpretado".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setPersonajeInterpretado((String) nuevoValor);
				return true;
			}
		} else if("tipopersonaje".equals(atributoMinus) || "tipo personaje".equals(atributoMinus) || "tipo de personaje".equals(atributoMinus) 
				|| "tipodepersonaje".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setTipoPersonaje((String) nuevoValor);
				return true;
			}
		}
		return false;
	}

	@Override
	public String mostrarDetallesCompletos() {
		StringBuilder detalles = new StringBuilder();
		detalles.append("DATOS DEL ACTOR\n");
		detalles.append("Nombre: " + this.getNombre() + "\n");
		detalles.append("Apellido: " + this.getApellido() + "\n");
		detalles.append("Personaje Interpretado: " + this.getPersonajeInterpretado() + "\n");
		detalles.append("Tipo de personaje: " + this.getTipoPersonaje() + "\n");
		detalles.append("Número de peliculas en las que aparece: " + this.peliculas.size() + "\n");
		return detalles.toString();
	}
	
	

	//metodo que agrega las peliculas en las que el actor participa
	public void agregarPelicula(Pelicula pelicula) {
		if(pelicula != null && !this.peliculas.contains(pelicula)) {
			this.peliculas.add(pelicula);
			if(!pelicula.getActores().contains(this)) {
				pelicula.getActores().add(this);
			}
		}	
	}
	
	
	
}
