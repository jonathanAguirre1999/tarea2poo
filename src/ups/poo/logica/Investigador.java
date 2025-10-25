package ups.poo.logica;

public class Investigador implements IConsultable, IModificable{
	
	private String nombre;
	private String apellido;
	private String areaEspecialidad;
	private String nacionalidad;
	private Documental docum; //relacion con clase Documental
	
	//constructores
	public Investigador() {
	}

	public Investigador(String nombre, String apellido, String areaEspecialidad, String nacionalidad, Documental docum) {
		this.nombre = nombre;
		this.apellido = apellido;
		this.areaEspecialidad = areaEspecialidad;
		this.nacionalidad = nacionalidad;
		this.docum = docum;
	}
	
	
	//getters y setters
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

	public String getAreaEspecialidad() {
		return areaEspecialidad;
	}

	public void setAreaEspecialidad(String areaEspecialidad) {
		this.areaEspecialidad = areaEspecialidad;
	}

	public String getNacionalidad() {
		return nacionalidad;
	}

	public void setNacionalidad(String nacionalidad) {
		this.nacionalidad = nacionalidad;
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
			
}
