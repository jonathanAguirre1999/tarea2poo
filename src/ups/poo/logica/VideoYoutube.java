package ups.poo.logica;

import java.util.Objects;

public class VideoYoutube extends ContenidoAudiovisual {

	private String nombreCanal;
	private String linkVideo;
	
	//constructores
	public VideoYoutube() {

	}

	public VideoYoutube(String titulo, int duracionEnMinutos, String genero, String nombreCanal, String linkVideo) {
		super(titulo, duracionEnMinutos, genero);
		this.nombreCanal = nombreCanal;
		this.linkVideo = linkVideo;
	}

	//getters y setters
	public String getNombreCanal() {
		return nombreCanal;
	}

	public void setNombreCanal(String nombreCanal) {
		this.nombreCanal = nombreCanal;
	}

	public String getLinkVideo() {
		return linkVideo;
	}

	public void setLinkVideo(String linkVideo) {
		this.linkVideo = linkVideo;
	}

	//metodos heredados
	@Override
	public String mostrarDetallesCompletos() {
		StringBuilder detalles = new StringBuilder();
		detalles.append("INFORMACION DEL VIDEO\n");
		detalles.append("ID: " + this.getId() + "\n");
		detalles.append("Titulo: " + this.getTitulo() + "\n");
		detalles.append("Duración en minutos: " + this.getDuracionEnMinutos() + " minutos\n");
		detalles.append("Género: " + this.getGenero() + "\n");
		detalles.append("Canal: " + this.getNombreCanal() + "\n");
		detalles.append("Link del video: " + this.getLinkVideo() + "\n");
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
		} else if("canal".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setNombreCanal((String) nuevoValor);
				return true;
			}
		} else if("link".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setLinkVideo((String) nuevoValor);
				return true;
			}
		}
		return false;
	}
	
	//metodos hash y equals
	@Override
	public int hashCode() {
		if(this.getId() != 0) {
			return Objects.hash(this.getId());
		}
		return Objects.hash(this.getTitulo(), this.getLinkVideo());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) 
			return false;
		VideoYoutube other = (VideoYoutube) obj;
		
		if(this.getId() != 0 && other.getId() != 0) {
			return this.getId() == other.getId();
		}
		
		return Objects.equals(this.getTitulo(), other.getTitulo()) && Objects.equals(this.getLinkVideo(), other.getLinkVideo());
	}
	
	//tostring
	@Override
	public String toString() {
		return this.mostrarDetallesCompletos();
	}
	
}
