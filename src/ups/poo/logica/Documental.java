package ups.poo.logica;

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
        this.tema = tema;
    }

    //getters y setters
    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
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

}