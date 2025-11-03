package ups.poo.logica;
import java.time.LocalDate; //libreria que permite usar fechas
import java.util.Objects;

public class Temporada implements IConsultable, IModificable{
	
	private int id = 0;
	private int numTemporada;
	private SerieDeTV serie; //relacion con la clase SerieDeTV
	private String tituloTemporada;
	private int cantidadCapitulos;
	private LocalDate fechaEstreno;
	
	//constructores
	public Temporada() {
	}
	
	
	public Temporada(int numTemporada, SerieDeTV serie, String tituloTemporada, int cantidadCapitulos,
			LocalDate fechaEstreno) {
		this.numTemporada = numTemporada;
		this.serie = serie;
		this.tituloTemporada = tituloTemporada.trim();
		this.cantidadCapitulos = cantidadCapitulos;
		this.fechaEstreno = fechaEstreno;
	}


	//getters y setters

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}
	
	public int getNumTemporada() {
		return numTemporada;
	}


	public void setNumTemporada(int numTemporada) {
		this.numTemporada = numTemporada;
	}


	public SerieDeTV getSerie() {
		return serie;
	}


	public void setSerie(SerieDeTV serie) {
		this.serie = serie;
	}

	
	public String getTitulo() {
		return tituloTemporada;
	}


	public void setTituloTemporada(String tituloTemporada) {
		this.tituloTemporada = tituloTemporada.trim();
	}


	public int getCantidadCapitulos() {
		return cantidadCapitulos;
	}


	public void setCantidadCapitulos(int cantidadCapitulos) {
		this.cantidadCapitulos = cantidadCapitulos;
	}


	public LocalDate getFechaEstreno() {
		return fechaEstreno;
	}


	public void setFechaEstreno(LocalDate fechaEstreno) {
		this.fechaEstreno = fechaEstreno;
	}


	@Override
	public <T> boolean modificarAtributo(String atributo, T nuevoValor) {
		String atributoMinus = atributo.toLowerCase();
		if("numerotemporada".equals(atributoMinus) || "numero temporada".equals(atributoMinus) || 
				"numerodetemporada".equals(atributoMinus) || "numero de temporada".equals(atributoMinus))  {	
			if(nuevoValor instanceof Integer) {
				this.setNumTemporada((Integer) nuevoValor);
				return true;
			}
		} else if("titulo".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setTituloTemporada((String) nuevoValor);
				return true;
			}
		} else if("cantidadcapitulos".equals(atributoMinus) || "cantidad capitulos".equals(atributoMinus) 
				|| "cantidaddecapitulos".equals(atributoMinus) || "cantidad de capitulos".equals(atributoMinus)) {
			if(nuevoValor instanceof Integer) {
				this.setCantidadCapitulos((Integer) nuevoValor);
				return true;
			}
		} else if("fechaestreno".equals(atributoMinus) || "fecha estreno".equals(atributoMinus) 
				|| "fechadeestreno".equals(atributoMinus) || "fecha de estreno".equals(atributoMinus)) {
			if(nuevoValor instanceof LocalDate) {
				this.setFechaEstreno((LocalDate) nuevoValor);
				return true;
			}
		}
		return false;
	}


	@Override
	public String mostrarDetallesCompletos() {
		StringBuilder detalles = new StringBuilder();
		detalles.append("DETALLES DE LA TEMPORADA\n");
		detalles.append("Número de temporada: " + this.getNumTemporada() + "\n");
		detalles.append("Título: " + this.getTitulo() + "\n");
		detalles.append("Cantidad de capítulos: " + this.getCantidadCapitulos() + "\n");
		detalles.append("Fecha de estreno: " + this.getFechaEstreno() + "\n");
		detalles.append("Serie: " + this.getSerie() + "\n");
		return detalles.toString();
	}
	
	//metodos equals y hashcode para control de entradas duplicadas
		@Override
		public int hashCode() {
			if(this.getId() != 0) {
				return Objects.hash(this.getId());
			}
			return Objects.hash(this.getNumTemporada(), this.getSerie().getId());
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass()) 
				return false;
			Temporada other = (Temporada) obj;
			
			if(this.getId() != 0 && other.getId() != 0) {
				return this.getId() == other.getId();
			}
			
			return Objects.equals(this.getNumTemporada(), other.getNumTemporada()) && 
					Objects.equals(this.getSerie().getId(), other.getSerie().getId());
		}

		
	//tostring
		@Override
		public String toString() {
			return this.mostrarDetallesCompletos();
		}

}
