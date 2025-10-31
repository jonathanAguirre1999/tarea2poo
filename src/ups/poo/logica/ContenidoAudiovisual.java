package ups.poo.logica;

public abstract class ContenidoAudiovisual implements IConsultable, IModificable {
    private String titulo;
    private int duracionEnMinutos;
    private String genero;
    private int id;
    
    //constructores
    public ContenidoAudiovisual() {
    }
    
    public ContenidoAudiovisual(String titulo, int duracionEnMinutos, String genero) {
        this.id = 0;
        this.titulo = titulo.trim();
        this.duracionEnMinutos = duracionEnMinutos;
        this.genero = genero.trim();
    }

    // getters y setters
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo.trim();
    }

    public int getDuracionEnMinutos() {
        return duracionEnMinutos;
    }

    public void setDuracionEnMinutos(int duracionEnMinutos) {
        this.duracionEnMinutos = duracionEnMinutos;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero.trim();
    }

    public int getId() {
        return id;
    }
    
    public void setId(int id) {
    	this.id = id;
    }
}