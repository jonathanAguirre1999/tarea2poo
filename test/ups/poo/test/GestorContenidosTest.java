package ups.poo.test;

import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import ups.poo.logica.*;
import java.time.*;

public class GestorContenidosTest {
	
	private GestorContenidos gestor;
	
	//ASEGURA QUE CADA PRUEBA INICIE CON UN GESTOR COMPLETAMENTE LIMPIO
	@Before
	public void setUp() {
		gestor = new GestorContenidos();
	}
	
	@Test
	public void probarAgregarID() {
		
		//ESTE CASO PRUEBA LA AGREGACION DE UN OBJETO Y SI EL GESTOR ESTA CUMPLIENDO CON AGREGARLE UN ID UNICO
		Pelicula p = new Pelicula("Pelicula de ejemplo", 100, "Prueba", "Prueba");
		
		//CUANDO SE AGREGA UN OBJETO NUEVO SU ID DEBE SER 0 HASTA QUE EL GESTOR LE ASIGNA UN ID UNICO
		assertEquals(0, p.getId());
		
		boolean agregado = gestor.agregarNuevoObj(p);
		
		//EL OBJETO SE DEBE AGREGAR EXITOSAMENTE
		assertTrue(agregado);
		//TRAS AGREGARSE EL OBJETO EL GESTOR LE ASIGNA UN ID DIFERENTE (Y MAYOR) A 0
		assertTrue(p.getId() > 0);
	}
	
	@Test
	public void probarNoPermitirAgregarDuplicadosLogicos() {
		
		//ESTE CASO PRUEBA SI EL SISTEMA PERMITE AGREGAR DUPLICADOS LOGICOS CON LOS MISMOS ATRIBUTOS CLAVES
		Actor a1 = new Actor("Actor", "Prueba", "Probando 1");
		Actor a2 = new Actor("Actor", "Prueba", "Probando 2");
		
		//EL PRIMER ACTOR SI SE DEBE AGREGAR (LOS ACTORES SE VERIFICAN DUPLICADOS MEDIANTE NOMBRE Y APELLIDO)
		assertTrue(gestor.agregarNuevoObj(a1));
		//EL SEGUNDO ACTOR NO SE DEBE AGREGAR AL SER DUPLICADO, DEBE DEVOLVER FALSO
		assertFalse(gestor.agregarNuevoObj(a2));
	}
	
	@Test
	public void probarBusquedaPorTitulo() {
		
		//LA BUSQUEDA DEBE IGNORAR MAYUSCULAS Y ESPACIOS AL INICIO Y AL FINAL DEL INGRESO DE DATOS
		String tituloDePrueba = "Busqueda de prueba";
		Pelicula p = new Pelicula(tituloDePrueba, 200, "Prueba", "Prueba");
		gestor.agregarNuevoObj(p);
		
		//CASO: BUSQUEDA EXACTA 
		assertNotNull(gestor.buscarPorTitulo("Busqueda de prueba"));
		
		//CASO: IGNORAR MAYUSCULAS/MINUSCULAS
		assertNotNull(gestor.buscarPorTitulo("BUSquEDA dE PRUeBA"));
		
		//CASO: IGNORAR ESPACIOS AL INICIO Y AL FINAL
		assertNotNull(gestor.buscarPorTitulo("     Busqueda de prueba       "));
		
		//CASO: COMBINACION DE LOS CASOS ANTERIORES
		assertNotNull(gestor.buscarPorTitulo("    BUSquEDA dE PRUeBA     "));
		
		//CASO: BUSQUEDA DE UN TITULO QUE NO EXISTE
		assertNull(gestor.buscarPorTitulo("Titulo que no existe"));
	}
	
	@Test
	public void pruebaRelacionBidireccionalActorPelicula() {
		
		//ESTE CASO PRUEBA QUE AMBOS OBJETOS SE AGREGAN ENTRE SI, ES DECIR, VERIFICA LA RELACCION BIDIRECCIONAL
		Pelicula p = new Pelicula("Resident Evil 4", 100, "Prueba", "Prueba");
		Actor a = new Actor("Leon", "Kennedy", "Estadounidense");
		
		//REGISTRO EN EL GESTOR
		gestor.agregarNuevoObj(p);
		gestor.agregarNuevoObj(a);
		
		//SE ESTABLECE LA RELACION
		p.agregarActor(a);
		
		//SE VERIFICA QUE AMBOS (ACTOR Y PELICULA) SE CONTENGAN ENTRE SI (ACTOR DEBE CONTENER A PELICULA Y VICEVERSA)
		assertTrue(p.getActores().contains(a));
		assertTrue(a.getPeliculas().contains(p));
		
		//SE REALIZA UN CONTEO PARA VERIFICAR LA ASOCIACION
		assertEquals(1, gestor.conteoPeliculasActor(a));
		assertEquals(1, p.getActores().size());	
	}
	
	@Test
	public void pruebaEliminacionPorId() {
		
		//VERIFICAR QUE UN OBJETO SE ELIMINA COMPLETAMENTE DEL SISTEMA
		Documental d = new Documental("La tierra", 120, "Naturaleza", "BBC");
		gestor.agregarNuevoObj(d);
		int id = d.getId();
		
		//VALIDAR LA EXISTENCIA DEL OBJETO ANTES DE PROCEDER A BORRARLO
		assertNotNull(gestor.buscarPorId(id));
		
		//ELIMINAR Y CONFIRMAR
		assertTrue(gestor.elimObj(id));
		assertNull(gestor.buscarPorId(id));
	}
	
	@Test
	public void pruebaEliminacionEnCascadaSeriesTemporadas() {
		
		//ESTE CASO PRUEBA LA ELIMINACION EN CASCADA DE TEMPORADAS TRAS ELIMINAR SU SERIE
		SerieDeTV serie = new SerieDeTV("One Piece", 30, "Anime", 1999, true);
		Temporada temp1 = new Temporada(1, serie, "Arco East Blue", 60, LocalDate.now());
		Temporada temp2 = new Temporada(2, serie, "Arco Alabasta", 60, LocalDate.now());

		serie.agregarTemporada(temp1);
		serie.agregarTemporada(temp2);
		
		gestor.agregarNuevoObj(serie);
		gestor.agregarNuevoObj(temp1);
		gestor.agregarNuevoObj(temp2);
		
		int idSerie = serie.getId();
		int idTemp1 = temp1.getId();
		int idTemp2 = temp2.getId();
		
		//CONDICION: EXISTENCIA DE LOS OBJETOS ANTES DE SU ELIMINACION
		assertNotNull(serie);
		assertNotNull(temp1);
		assertNotNull(temp2);
		
		//CASO: ELIMINACION DE SERIE
		assertTrue(gestor.elimObj(idSerie));
		
		//RESULTADOS: TANTO LA SERIE COMO LAS TEMPORADAS DEBEN DEJAR DE EXISTIR
		assertNull(gestor.buscarPorId(idSerie));
		assertNull(gestor.buscarPorId(idTemp1));
		assertNull(gestor.buscarPorId(idTemp2));
	}
	
}
