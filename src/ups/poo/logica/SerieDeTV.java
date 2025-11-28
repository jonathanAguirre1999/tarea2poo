package ups.poo.logica;
import java.util.*;

// Subclase SerieDeTV que extiende de ContenidoAudiovisual
public class SerieDeTV extends ContenidoAudiovisual {
    
	private static final long serialVersionUID = 1L;
	private int anioEstreno;
    private boolean enEmision;
    private List <Temporada> tempo = new ArrayList <> ();
    
    //constructores
	public SerieDeTV() {
	}
    
    public SerieDeTV(String titulo, int duracionEnMinutos, String genero, int anioEstreno, boolean enEmision) {
		super(titulo, duracionEnMinutos, genero);
		this.anioEstreno = anioEstreno;
		this.enEmision = enEmision;
	}

	//getters y setters
	public int getAnioEstreno() {
		return anioEstreno;
	}

	public void setAnioEstreno(int anioEstreno) {
		this.anioEstreno = anioEstreno;
	}

	public boolean isEnEmision() {
		return enEmision;
	}

	public void setEnEmision(boolean enEmision) {
		this.enEmision = enEmision;
	}

	public List <Temporada> getTempo() {
		return tempo;
	}

	public void setTempo(List <Temporada> tempo) {
		this.tempo = tempo;
	}

	//metodos heredados de las interfaces
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
		} else if("año".equals(atributoMinus) || "anio".equals(atributoMinus)) {
			if(nuevoValor instanceof Integer) {
				this.setAnioEstreno((Integer) nuevoValor);
				return true;
			}
		} else if("emision".equals(atributoMinus) || "enemision".equals(atributoMinus)) {
			if(nuevoValor instanceof Boolean) {
				this.setEnEmision((Boolean) nuevoValor);
				return true;
				}
			}
		return false;
		}
		


	@Override
	public String mostrarDetallesCompletos() {
		String emi = (this.enEmision) ? "Sí" : "No";
		StringBuilder detalles = new StringBuilder();
		detalles.append("INFORMACION DE LA SERIE\n");
		detalles.append("ID: " + this.getId() + "\n");
		detalles.append("Titulo: " + this.getTitulo() + "\n");
		detalles.append("Duración en minutos (por capitulo promedio): " + this.getDuracionEnMinutos() + " minutos\n");
		detalles.append("Género: " + this.getGenero() + "\n");
		detalles.append("Año: " + this.getAnioEstreno() + "\n");
		detalles.append("En emisión: " + emi + "\n");
		detalles.append("Temporadas: " + this.tempo.size() + "\n");
		return detalles.toString();
	}
	
	
	//metodo que permite agregar temporadas a la serie
	public void agregarTemporada(Temporada temporada) {
		if(temporada != null) {
			if(!this.tempo.contains(temporada)) {
				this.tempo.add(temporada);
				temporada.setSerie(this);
			}
		}
	}
	
	//metodo que elimina la referencia de temporadas de una serie
  	//elimina una temporada de la lista de temporadas asociadoss a esta serie
  	public void eliminarTemporada(Temporada temporada) {
  		if(this.tempo != null) {
  			this.tempo.remove(temporada);
  		}
  	}
	
	//metodos equals y hashcode para control de entradas duplicadas
	@Override
	public int hashCode() {
		if(this.getId() != 0) {
			return Objects.hash(this.getId());
		}
		return Objects.hash(this.getAnioEstreno(), this.getTitulo());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) 
			return false;
		SerieDeTV other = (SerieDeTV) obj;
				
		if(this.getId() != 0 && other.getId() != 0) {
					return this.getId() == other.getId();
		}
		
		return Objects.equals(this.getAnioEstreno(), other.getAnioEstreno()) && 
				Objects.equals(this.getTitulo(), other.getTitulo());
	}
	
	//tostring
		@Override
		public String toString() {
			return this.mostrarDetallesCompletos();
		}
			
}