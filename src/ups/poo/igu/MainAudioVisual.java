package ups.poo.igu;
import ups.poo.logica.*;

public class MainAudioVisual {
	public static void main(String[] args) {
		
		Pelicula harryPotter = new Pelicula ("Harry Potter", 90, "SciFi", "Universal Studios");		
        System.out.println(harryPotter.mostrarDetallesCompletos());		
    }
}