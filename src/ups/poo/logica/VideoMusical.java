package ups.poo.logica;

import java.util.Objects;

public class VideoMusical extends ContenidoAudiovisual {

	private String artista;
	private String album;
	private int anio;
	
	//constructores
	public VideoMusical() {

	}

	public VideoMusical(String titulo, int duracionEnMinutos, String genero, String artista, String album, int anio) {
		super(titulo, duracionEnMinutos, genero);
		this.artista = artista;
		this.album = album;
		this.anio = anio;
	}

	//getters y setters
	public String getArtista() {
		return artista;
	}

	public void setArtista(String artista) {
		this.artista = artista;
	}

	public String getAlbum() {
		return album;
	}

	public void setAlbum(String album) {
		this.album = album;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	//metodos heredados
	@Override
	public String mostrarDetallesCompletos() {
		StringBuilder detalles = new StringBuilder();
		detalles.append("INFORMACION DEL VIDEO MUSICAL\n");
		detalles.append("ID: " + this.getId() + "\n");
		detalles.append("Titulo: " + this.getTitulo() + "\n");
		detalles.append("Duración en minutos: " + this.getDuracionEnMinutos() + " minutos\n");
		detalles.append("Género: " + this.getGenero() + "\n");
		detalles.append("Artista: " + this.getArtista() + "\n");
		detalles.append("Album: " + this.getAlbum() + "\n");
		detalles.append("Año: " + this.getAnio() + "\n");
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
		} else if("artista".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setArtista((String) nuevoValor);
				return true;
			}
		} else if("album".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setAlbum((String) nuevoValor);
				return true;
			}
		} else if("año".equals(atributoMinus)) {
			if(nuevoValor instanceof String) {
				this.setAnio((Integer) nuevoValor);
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
		return Objects.hash(this.getTitulo(), this.getArtista());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) 
			return false;
		VideoMusical other = (VideoMusical) obj;
			
		if(this.getId() != 0 && other.getId() != 0) {
			return this.getId() == other.getId();
		}
			
		return Objects.equals(this.getTitulo(), other.getTitulo()) && Objects.equals(this.getArtista(), other.getArtista());
	}
		
	//tostring
	@Override
	public String toString() {
		return this.mostrarDetallesCompletos();
	}
		
	
}
