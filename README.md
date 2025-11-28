GESTOR DE CONTENIDO AUDIOVISUAL (CRUD POO)

 Descripción del Proyecto

Sistema de escritorio desarrollado en Java para la gestión completa (CRUD: Crear, Consultar, Modificar, Eliminar) de diferentes tipos de contenido audiovisual y sus entidades asociadas.

El proyecto está diseñado bajo el paradigma de la Programación Orientada a Objetos (POO), haciendo uso extensivo de la herencia, el polimorfismo mediante interfaces y la gestión de relaciones bidireccionales entre entidades.

Tecnologías y Requisitos

Lenguaje: Java

Interfaz Gráfica: Swing

Patrones de Diseño: Principios SOLID, Arquitectura en Capas (Lógica de Negocio, Presentación).

Entidades Soportadas

El sistema gestiona:

Contenido Audiovisual: Películas, Series de TV, Documentales, Videos de Youtube, Videos Musicales.

Entidades de Soporte: Actores, Investigadores, Temporadas.

Arquitectura y Diseño (UML)

La estructura del sistema garantiza modularidad y extensibilidad:

Clase Base: ContenidoAudiovisual (Clase Abstracta).

Interfaces: IConsultable e IModificable aseguran que todas las entidades implementen sus propias lógicas de visualización y edición.

Controlador Central: La clase GestorContenidos centraliza todas las operaciones CRUD y maneja la integridad de las relaciones (ej. borrarRelacion() para la eliminación en cascada o la ruptura de referencias).

Relaciones Clave:

Actor <-> Película: Asociación N:M bidireccional.

SerieDeTV <-> Temporada: Composición (Temporadas dependen de la Serie).

Documental <-> Investigador: Asociación 1:1.

Funcionalidades Principales

El menú principal ofrece acceso a los cuatro paneles CRUD:

Panel

Funcionalidad

Añadir

Captura de datos para crear nuevos objetos, incluyendo la asociación de entidades (ej., Actores a Películas).

Consultar

Listado tabular de todos los objetos de un tipo seleccionado.

Modificar

Permite seleccionar un objeto de la tabla y editar sus atributos específicos.

Eliminar

Proporciona una tabla para seleccionar y eliminar un objeto por su ID, asegurando la ruptura de relaciones para evitar inconsistencias.

Ejecución del Proyecto

El proyecto está diseñado para ejecutarse como un archivo JAR autocontenido.

Asegúrese de tener el Java Runtime Environment (JRE) instalado.

Ejecute el archivo JAR (ej. GESTOR DE CONTENIDO AUDIOVISUAL.jar) haciendo doble clic sobre él.

La aplicación se iniciará desde la clase principal (MainAudioVisual).

Desarrollado como proyecto para el curso de Programación Orientada a Objetos.
