package ups.poo.logica;
import java.util.*;


public interface IControlable {
	
	//agrega un nuevo objeto 
	<T> boolean agregarNuevoObj (T objeto);
	
	//busqueda por titulo de contenido audiovisual
	ContenidoAudiovisual buscarPorTitulo (String titulo);
	
	//devuelve la cantidad de peliculas de un actor
	int conteoPeliculasActor (Actor actor);
	
	//obtiene la lista completa de objetos de una clase
	<T> List <T> obtenerTodos (Class<T> claseNom);
	
	//elimina un objeto por medio de su id/
	boolean elimObj (int id);
	
}
