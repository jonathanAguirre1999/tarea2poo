package ups.poo.logica;

import java.util.Objects;

// Subclase Documental que extiende de ContenidoAudiovisual
public class Documental extends ContenidoAudiovisual {
    private String tema;
    private Investigador investi;

    //constructores
    public Documental() {
    	super();
    }
    
    public Documental(String titulo, int duracionEnMinutos, String genero, String tema) {
        super(titulo, duracionEnMinutos, genero);
        this.tema = tema.trim();
    }

    //getters y setters
    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema.trim();
    }
    
    public Investigador getInvesti() {
		return investi;
	}

	public void setInvesti(Investigador investi) {
		if(this.investi != null) {
			this.investi.setDocum(null);
		}
		
		this.investi = investi;
		
		if(investi != null) {
			investi.setDocum(this);
		}
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
			} else if("tema".equals(atributoMinus)) {
				if(nuevoValor instanceof String) {
					this.setTema((String) nuevoValor);
					return true;
				}
			}
			return false;
	}

	@Override
	public String mostrarDetallesCompletos() {
		StringBuilder detalles = new StringBuilder();
		detalles.append("INFORMACION DEL DOCUMENTAL\n");
		detalles.append("ID: " + this.getId() + "\n");
		detalles.append("Titulo: " + this.getTitulo() + "\n");
		detalles.append("Duración en minutos: " + this.getDuracionEnMinutos() + " minutos\n");
		detalles.append("Género: " + this.getGenero() + "\n");
		detalles.append("Tema: " + this.getTema() + "\n");
		detalles.append("Investigador a cargo: " + this.getInvesti() + "\n");
		return detalles.toString();
	}

	//metodos equals y hashcode para control de entradas duplicadas
	@Override
	public int hashCode() {
		if(this.getId() != 0) {
			return Objects.hash(this.getId());
		}
		return Objects.hash(this.getTitulo(), this.getTema());
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass()) 
			return false;
		Documental other = (Documental) obj;
		
		if(this.getId() != 0 && other.getId() != 0) {
			return this.getId() == other.getId();
		}
		
		return Objects.equals(this.getTitulo(), other.getTitulo()) && Objects.equals(this.getTema(), other.getTema());
	}
	
	//tostring
		@Override
		public String toString() {
			return this.mostrarDetallesCompletos();
		}

}