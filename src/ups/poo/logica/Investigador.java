package ups.poo.logica;

import java.util.Objects;

public class Investigador implements IConsultable, IModificable{
	
	private int id = 0;
	private String nombre;
	private String apellido;
	private String areaEspecialidad;
	private String nacionalidad;
	private Documental docum; //relacion con clase Documental
	
	//constructores
	public Investigador() {
	}

	public Investigador(String nombre, String apellido, String areaEspecialidad, String nacionalidad, Documental docum) {
		this.nombre = nombre.trim();
		this.apellido = apellido.trim();
		this.areaEspecialidad = areaEspecialidad.trim();
		this.nacionalidad = nacionalidad.trim();
		this.docum = docum;
	}
	
	
	//getters y setters
	public int getId() {
		return id;
	}

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

	public String getAreaEspecialidad() {
		return areaEspecialidad;
	}

	public void setAreaEspecialidad(String areaEspecialidad) {
		this.areaEspecialidad = areaEspecialidad.trim();
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad.trim();
	}

	public Documental getDocum() {
		return docum;
	}

	public void setDocum(Documental docum) {
		this.docum = docum;
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
		} else if("areaespecialidad".equals(atributoMinus) || "area especialidad".equals(atributoMinus) 
				|| "areadeespecialidad".equals(atributoMinus) || "area de especialidad".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setAreaEspecialidad((String) nuevoValor);
				return true;
			}
		} else if("nacionalidad".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setNacionalidad((String) nuevoValor);
				return true;
			}
		} else if("documental".equals(atributoMinus) || "docum".equals(atributoMinus)) {
			if(nuevoValor instanceof Documental) {
				this.setDocum((Documental) nuevoValor);
				return true;
			}
		}
		return false;
	}

	@Override
	public String mostrarDetallesCompletos() {
		StringBuilder detalles = new StringBuilder();
		detalles.append("INFORMACION DEL INVESTIGADOR\n");
		detalles.append("Nombre " + this.getNombre() + "\n");
		detalles.append("Apellido: " + this.getApellido() + "\n");
		detalles.append("Area de especialidad: " + this.getAreaEspecialidad() + "\n");
		detalles.append("Nacionalidad: " + this.getNacionalidad() + "\n");
		detalles.append("Documental: " + this.docum.getTitulo() + "\n");
		return detalles.toString();
	}
	
	//metodos equals y hashcode para control de entradas duplicadas
	@Override
	public int hashCode() {
		if(this.getId() != 0) {
			return Objects.hash(this.getId());
		}
		return Objects.hash(this.getNombre(), this.getApellido());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) 
			return false;
		Investigador other = (Investigador) obj;
		
		if(this.getId() != 0 && other.getId() != 0) {
			return this.getId() == other.getId();
		}
		
		return Objects.equals(this.getNombre(), other.getNombre()) && Objects.equals(this.getApellido(), other.getApellido());
	}
	
	//tostring
		@Override
		public String toString() {
			return this.mostrarDetallesCompletos();
		}
}
