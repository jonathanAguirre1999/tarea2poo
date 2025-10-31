package ups.poo.igu;
import ups.poo.logica.*;

import java.time.LocalDate;
import java.util.*;
public class MainAudioVisual {
	public static void main(String[] args) {
		//inicializacion de datos base (datos cargados por defecto) 
		System.out.println("----------------INICIALIANDO SISTEMA--------------------");
		System.out.println("\nDATOS BASE CARGADOS CON EXITO\n");
		GestorContenidos gestor = new GestorContenidos();
		menuPrincipal(gestor);
		System.out.println("Gracias por usar esta aplicación. Hasta la próxima!");
    }
	
	//metodo de inicializacion de menu de usuario
	public static void menuPrincipal(GestorContenidos gestor) {
		//permite ingresar una opcion en el menu
		Scanner scan = new Scanner(System.in);
		StringBuilder opcionesMenu = new StringBuilder();
		opcionesMenu.append("-----------MENÚ PRINCIPAL----------\n\n");
		opcionesMenu.append("1- Agregar nuevo \n");
		opcionesMenu.append("2- Buscar contenidos por título\n");
		opcionesMenu.append("3- Mostrar todo el contenido de una clase especifica\n");
		opcionesMenu.append("4- Elimminar contenido por ID\n");
		opcionesMenu.append("5- Contar películas de un actor\n");
		opcionesMenu.append("0- Salir del sistema\n\n");
		opcionesMenu.append("Escoja la opción que desea realizar: ");
		String opciones = opcionesMenu.toString();
		
		try {
			int opcion;
			do {
				//despliegue de menu de usuario
				System.out.print(opciones);
				opcion = scan.nextInt();
				scan.nextLine();
				switch (opcion) {
				
				//agregar nuevo
				case 1:
					StringBuilder opcionAgregarNuevo = new StringBuilder();
					opcionAgregarNuevo.append("\n\n-----------AGREGAR NUEVO----------\n\n");
					opcionAgregarNuevo.append("1- Agregar nuevo actor \n");
					opcionAgregarNuevo.append("2- Agregar nueva pelicula\n");
					opcionAgregarNuevo.append("3- Agregar nueva serie\n");
					opcionAgregarNuevo.append("4- Agregar nueva temporada\n");
					opcionAgregarNuevo.append("5- Agregar nuevo documental\n");
					opcionAgregarNuevo.append("6- Agregar nuevo investigador\n\n");
					opcionAgregarNuevo.append("Escoja la opción que desea realizar: ");
					String menuOpcion1 = opcionAgregarNuevo.toString();
					
					System.out.print(menuOpcion1);
					int opcionSubmenu = scan.nextInt();
					scan.nextLine();
					
					Object objetoNuevo = null;
					
					try {
						switch(opcionSubmenu) {
						
						case 1: //actor
							//datos para la creacion
							System.out.print("Nombre: ");
							String nombreActor = scan.nextLine().trim();
							System.out.print("Apellido: ");
							String apellidoActor = scan.nextLine().trim();
							System.out.print("Nacionalidad: ");
							String nacionalidadActor = scan.nextLine().trim();
							
							objetoNuevo = new Actor(nombreActor, apellidoActor, nacionalidadActor);
							gestor.agregarNuevoObj(objetoNuevo);
							Actor nuevoActor = (Actor) objetoNuevo;
							System.out.print("Nuevo actor agregado con éxito: " + nuevoActor.mostrarDetallesCompletos());
							break;
						
						case 2: //pelicula
							//datos para la creacion
							System.out.print("Titulo: ");
							String tituloPeli = scan.nextLine().trim();
							System.out.print("Duración (minutos): ");
							int duracionMinutosPeli = scan.nextInt();
							scan.nextLine();
							System.out.print("Género: ");
							String generoPeli = scan.nextLine().trim();
							System.out.print("Estudio: ");
							String estudioPeli = scan.nextLine().trim();
							
							objetoNuevo = new Pelicula(tituloPeli, duracionMinutosPeli, generoPeli, estudioPeli);
							gestor.agregarNuevoObj(objetoNuevo);
							Pelicula nuevaPeli = (Pelicula) objetoNuevo;
							System.out.print("Nueva película agregada con éxito: " + nuevaPeli.mostrarDetallesCompletos());
							break;
							
						case 3: //serie
							//datos para la creacion
							System.out.print("Titulo: ");
							String tituloSerie = scan.nextLine().trim();
							System.out.print("Duración (minutos): ");
							int duracionMinutosSerie = scan.nextInt();
							scan.nextLine();
							System.out.print("Género: ");
							String generoSerie = scan.nextLine().trim();
							System.out.print("Año de estreno: ");
							int anioEstreno = scan.nextInt();
							scan.nextLine();
							
							boolean enEmision;
							String entradaEmision;
							char opcionEmision;
							do {
								System.out.print("¿Sigue la serie en emisión? - s (si)/n (no) ");
								entradaEmision = scan.nextLine().trim().toLowerCase();
								if(entradaEmision.length() > 0) {
									opcionEmision = entradaEmision.charAt(0);
								} else {
									opcionEmision = ' ';
								}
								
								if(opcionEmision != 's' && opcionEmision != 'n') {
									System.out.print("Opción no válida, escoja (s) para sí y (n) para no: ");
								} 
							} while (opcionEmision != 's' && opcionEmision != 'n');
							enEmision = (opcionEmision == 's');
							
							objetoNuevo = new SerieDeTV(tituloSerie, duracionMinutosSerie, generoSerie, anioEstreno, enEmision);
							break;
							
						case 4: //temporada
							//datos para la creacion
							System.out.print("Número de temporada: ");
							int numTemporada = scan.nextInt();
							scan.nextLine();
							System.out.print("Serie a la que pertenece (debe ingresar el ID de la serie): ");
							int idSerie = scan.nextInt();
							scan.nextLine();
							SerieDeTV serie = (SerieDeTV) gestor.buscarPorId(idSerie);
							if(serie == null) {
								System.out.println("Error: Serie no encontrada\n -> ID de serie: " + idSerie + " inexistente");
								System.out.println("Primero debe crear la serie para agregar la temporada, inténte de nuevo más tarde.");
								break;
							}
							
							
							System.out.print("Titulo de la temporada: ");
							String tituloTemporada = scan.nextLine().trim();
							System.out.print("Cantidad de capitulos: ");
							int cantCapitulos = scan.nextInt();
							scan.nextLine();
							System.out.print("Año de estreno (AAAA): ");
							int anio = scan.nextInt();
							scan.nextLine();
							System.out.print("Mes de estreno (MM): ");
							int mes = scan.nextInt();
							scan.nextLine();
							System.out.print("Dia de estreno (DD): ");
							int dia = scan.nextInt();
							scan.nextLine();
							LocalDate fechaEstreno = LocalDate.of(anio, mes, dia);
							
							Temporada nuevaTempo = new Temporada(numTemporada, serie, tituloTemporada, cantCapitulos, fechaEstreno);
							serie.agregarTemporada(nuevaTempo);
							gestor.agregarNuevoObj(nuevaTempo);
							System.out.print("Nueva temporada agregada con éxito: " + nuevaTempo.mostrarDetallesCompletos());
							break;
							
						case 5: //documental
							//datos para la creacion
							System.out.print("Titulo: ");
							String tituloDocu= scan.nextLine().trim();
							System.out.print("Duración (minutos): ");
							int duracionMinutosDocu= scan.nextInt();
							scan.nextLine();
							System.out.print("Género: ");
							String generoDocu = scan.nextLine().trim();
							System.out.print("Tema: ");
							String temaDocu = scan.nextLine().trim();
							
							objetoNuevo = new Documental(tituloDocu, duracionMinutosDocu, generoDocu, temaDocu);
							gestor.agregarNuevoObj(objetoNuevo);
							Documental nuevoDocu = (Documental) objetoNuevo;
							System.out.print("Nuevo documental agregadao con éxito: " + nuevoDocu.mostrarDetallesCompletos());
							break;
									
						case 6: //investigador
							//datos para la creacion
							System.out.print("Nombre: ");
							String nombreInves = scan.nextLine().trim();
							System.out.print("Apellido: ");
							String apellidoInves = scan.nextLine();
							System.out.print("Area de especialidad: ");
							String areaEspecialidad = scan.nextLine().trim();
							System.out.print("Nacionalidad ");
							String nacionalidadInves = scan.nextLine().trim();
							System.out.print("Documental que realiza (debe ingresar el ID del documental): ");
							int idDocu = scan.nextInt();
							scan.nextLine();
							Documental docu = (Documental) gestor.buscarPorId(idDocu);
							if(docu == null) {
								System.out.println("Error: Documental no encontrado\n -> ID de documental: " + idDocu + " inexistente");
								System.out.println("Primero debe crear el documental para agregar el investigador, inténte de nuevo más tarde.");
								break;
							}
							
							objetoNuevo = new Investigador(nombreInves, apellidoInves, nacionalidadInves, areaEspecialidad, docu);
							gestor.agregarNuevoObj(objetoNuevo);
							Investigador nuevoInves= (Investigador) objetoNuevo;
							docu.setInvesti(nuevoInves);
							System.out.print("Nuevo investigador agregado con éxito: " + nuevoInves.mostrarDetallesCompletos());
							break;
						default:
							System.out.print("Opción erronea: intentelo de nuevo.");
							break;
						}
						break;
					} catch (Exception e) {
						System.out.println("Error en el ingreso de datos, vuelva a intentarlo.");
						scan.nextLine();
						break;
					} 
				
				//buscar por titulo
				case 2:
					System.out.println("\n----------BUSQUEDA POR TITULO--------\n");
					System.out.print("Ingrese el título a buscar: ");
					String tituloParaBuscar = scan.nextLine().trim();
					if(gestor.buscarPorTitulo(tituloParaBuscar) != null) {	
						Object resulBusqueda = gestor.buscarPorTitulo(tituloParaBuscar);
						System.out.println("Informacion encontrada:\n" + resulBusqueda.toString());
						break;
					} else {
						System.out.println("Titulo no encontrado, vuelva a intentarlo.");
						break;
					}
				//enlistar contenido por tipo	
				case 3:
					System.out.println("\n--------ENLISTAR CONTENIDO POR TIPO--------\n");
					StringBuilder opcionListar = new StringBuilder();
					opcionListar.append("Contenidos disponibles: \n\n");
					opcionListar.append("A-> Actores \n");
					opcionListar.append("P-> Peliculas \n");
					opcionListar.append("S-> Series \n");
					opcionListar.append("T-> Temporadas\n");
					opcionListar.append("D-> Documentales \n");
					opcionListar.append("I-> Investigadores \n\n");
					opcionListar.append("Escoja la opción de la cual desplegar todo el contenido disponible: ");
					String menuOpcion3 = opcionListar.toString();
					
					System.out.print(menuOpcion3);
					String opcionParaListar = scan.nextLine().trim().toUpperCase();
					
					//este bloque de codigo realiza un mapeo de los diferentes tipos de objetos existentes
					//se obtendra la respectiva clase para obtener un listado de sus objetos
					Class <?> claseParaListar = switch (opcionParaListar.charAt(0)) {
					case 'P' -> Pelicula.class;
					case 'A' -> Actor.class;
					case 'S' -> SerieDeTV.class;
					case 'T' -> Temporada.class;
					case 'D' -> Documental.class;
					case 'I' -> Investigador.class;
					default -> null;
					};
					
					if(claseParaListar != null) {
						List <?> listaCompleta = gestor.obtenerTodos(claseParaListar);
						System.out.println("Cantidad de elementos de tipo " + claseParaListar.getSimpleName() + " existentes: " + listaCompleta.size() + "\n");
						if(listaCompleta.isEmpty()) {
							System.out.println("Lista vacía");
						} else {
							listaCompleta.forEach(item -> System.out.println(item.toString() + "\n")); //detalles de cada objeto
						}
					} else {
						System.out.println("Tipo de objeto inválido, intente de nuevo");
					}
					break;
				
				//eliminar un objeto por ID
				case 4:
					System.out.println("\n--------ELIMINACION POR ID--------\n");
					System.out.print("Ingrese el ID del objeto a eliminar: ");
					try {
						int idParaEliminar = scan.nextInt();
						scan.nextLine();
						Object objetoParaEliminar = gestor.buscarPorId(idParaEliminar);
						if(objetoParaEliminar != null) {
							System.out.println("ATENCION!!!! Se eliminará el siguiente objeto y todas sus referencias del sistema: \n\n");
							System.out.println(objetoParaEliminar.toString() + "\n\n");
							System.out.print("¿Está seguro de eliminar este objeto? Esta acción es irreversible (s/n): ");
							String confirmacion = scan.nextLine().trim().toLowerCase();
							if(confirmacion.length() > 0 && confirmacion.charAt(0) == 's') {
								boolean eliminado = gestor.elimObj(idParaEliminar);
								if(eliminado) {
									System.out.println("Objeto eliminado satisfactoriamente junto con todas sus referencias. \n");
								} else {
									System.out.println("Error interno. No se pudo eliminar el objeto, vuelva a intentarlo más tarde. \n");
								}
							} else if(confirmacion.length() > 0 && confirmacion.charAt(0) == 'n'){
								System.out.println("Eliminación cancelada por el usuario. \n");
							} else {
								System.out.println("Opción no válida, la eliminación se ha suspendido, vuelva a empezar. \n");
							}	
						} else {
							System.out.println("Objeto no encontrado, ID " + idParaEliminar + " no válido, verifique los datos y vuelva a intentarlo. \n");
						}
					} catch (InputMismatchException e) {
						System.out.println("Entrada incorrecta: debe ingresar un número para el ID. \n");
						scan.nextLine();
					}
					break;
					
				//conteo de peliculas por actor
				case 5:
					System.out.println("\n-------CONTAR PELICULAS POR ACTOR--------\n");
					System.out.print("Ingrese el ID del actor: ");
					
					try {
						int idActorBuscado = scan.nextInt();
						scan.nextLine();
						
						//se verifica que el id corresponda a un actor y no a otro tipo de objeto
						Object objetoActor = gestor.buscarPorId(idActorBuscado);
						if(objetoActor == null || !(objetoActor instanceof Actor)) {
							System.out.println("No se ha encontrado un actor con el id " + idActorBuscado);
							break;
						} 
						
						Actor actor = (Actor) objetoActor;
						int conteoPeliculas = gestor.conteoPeliculasActor(actor);
						System.out.println("\nEl actor " + actor.getNombre() + " " + actor.getApellido() + " tiene " + conteoPeliculas + " película(s)\n");
					} catch (InputMismatchException e) {
						System.out.println("Entrada inválida, debe ingresar un número de ID");
					} catch (Exception e) {
						System.out.println("Ha ocurrido un error inesperado: \n" + e.getMessage());
					}
					break;
				
				//salir
				case 0:
					System.out.println("Usted ha salido del sistema.");
					break;
				
				//opciones fuera del menu
				default:
					System.out.println("\nOpción incorrecta, escoja una opción váida.");
					break;
				}
				
			} while (opcion != 0);
		} catch (InputMismatchException e) {
			System.out.println("Ingreso incorrecto de datos, ingrese una opción numérica.");
		} catch (Exception e) {
			System.out.println("Error desconocido: " + e.getMessage());
		}
	}	
}