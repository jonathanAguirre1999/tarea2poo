package ups.poo.logica;
import java.io.Serializable;
import java.util.*;

public class Actor implements IConsultable, IModificable, Serializable{
	
	private static final long serialVersionUID = 1L;
	private int id = 0;
	private String nombre;
	private String apellido;
	private String nacionalidad;
	private List <Pelicula> peliculas = new ArrayList <> (); //relaciona la clase con Pelicula
		
	// constructores
	public Actor() {
	}
	
	public Actor(String nombre, String apellido, String nacionalidad) {
		this.nombre = nombre.trim();
		this.apellido = apellido.trim();
		this.nacionalidad = nacionalidad.trim();
	}
	

	//lista de getters y setters
	@Override
	public int getId() {
		return id;
	}
	
	@Override
	public void setId(int id) {
		this.id = id;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre.trim();
	}
	public String getApellido() {
		return apellido;
	}
	public void setApellido(String apellido) {
		this.apellido = apellido.trim();
	}

	public String getNacionalidad() {
		return nacionalidad;
	}
	public void setTipoPersonaje(String nacionalidad) {
		this.nacionalidad = nacionalidad.trim();
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
		detalles.append("ID: " + this.getId() + "\n");
		detalles.append("Nombre: " + this.getNombre() + "\n");
		detalles.append("Apellido: " + this.getApellido() + "\n");
		detalles.append("Nacionalidad: " + this.getNacionalidad() + "\n");
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

	//metodo que elimina la referencia de peliculas de un actor
	//elimina una pelicula de la lista de peliculas asociadas a este actor
	public void eliminarPelicula(Pelicula peli) {
		if(this.peliculas != null) {
			this.peliculas.remove(peli);
		}
	}
	
	
	//metodos equals y hashcode para control de entradas duplicadas
	@Override
	public int hashCode() {
		if(this.id != 0) {
			return Objects.hash(this.id);
		}
		return Objects.hash(this.apellido, this.nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) 
			return false;
		Actor other = (Actor) obj;
		
		if(this.id != 0 && other.id != 0) {
			return this.id == other.id;
		}
		
		return Objects.equals(this.apellido, other.apellido) && Objects.equals(this.nombre, other.nombre);
	}
	
	//tostring
		@Override
		public String toString() {
			return this.mostrarDetallesCompletos();
		}
	
}
