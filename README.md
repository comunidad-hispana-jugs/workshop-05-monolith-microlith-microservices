# workshop-05-monolith-microlith-microservices
En la era que estamos de contenedores, cloud y muchas herramientas que escoger en el momento de crear nuevas aplicaciones o evolucionar las que tenemos; presentamos tips y hacemos enfasis en patrones y practicas elementales que aplican en nuestro dia a dia de desarrollo de aplicaciones empresariales.  A travez de este taller de 2 horas vamos a crear una aplicacion desde pasando de Monolitica a Microlitica y finalmente Microservicios.

## Requisitos:

- Open JDK 8 o superior
- Maven
- El IDE de su preferencia
- Un servidor de aplicaciones o distribucion de Microprofile en este caso para el taller vamos a utilizar OpenLiberty (https://openliberty.io), para facilidad bajar la distribucion para su sistema operativo del siguiente link: 
- Docker 

Agradeceriamos tener listo los pre requisitos previo al inicio de la siguiente seccion. 

## Que usaremos:

Aprenderas a ejecutar y actualizar una aplicacion simple basada en servicios REST y desplegada en un servidor Open Liberty. Usaremos Maven a lo largo de toda la guía para crear, implementar e interactuar con la instancia de servidor en ejecución.

### Open Liberty

Open Liberty es un servidor de aplicaciones diseñado para la nube. Es pequeño, ligero y diseñado pensando en el desarrollo de aplicaciones nativas de la nube. Soporta todas las APIs de MicroProfile y Jakarta EE (Java EE). También se despliega en todas las principales plataformas de nube, incluyendo Docker, Kubernetes y Cloud Foundry.

### Maven

Maven es una herramienta de creación de automatización que proporciona una forma eficiente de desarrollar aplicaciones Java. Usando Maven, vamos construir nuestros servicios. A continuación, realizará la configuración del servidor y los cambios de código y verá cómo los recoge un servidor en ejecución. También explorará cómo empaquetar su aplicación con el tiempo de ejecución del servidor para que se pueda implementar en cualquier lugar de una sola vez. Finalmente, empaquetaremos la aplicación junto con la configuración del servidor en una imagen Docker y la ejecutará como un contenedor.

#Notas:

El repositorio esta compuesto por un proyecto que puede ser desplegado como monolito, microlito o microservicios; ademas en los primeros laboratorios vamos a crear aplicaciones simples sin necesidad del codigo pre elaborado. El inicio del taller comienza con el archivo readme.md que contine un resumen del trabajo a realizar. 

## Monolitos

La mayoria de desarrolladores que llevan algunos años codificando en java estan muy familiarizados con un patron comun de desarrollo de aplicaciones empresariales multi capa que se denomina aplicacion Monolitica separada en capas de la siguiente manera:

----------     -----------     ----------------
| WEB    | <-> | BUSINESS| <-> | BASE DE DATOS |
----------     -----------     -----------------

La capa web es desarrollada usando Java Server Faces u en los ultimos años se usa Angular, Angular JS, React, VueJS u otro framework.

La capa de negocio comunmente la hemos desarrollado usando Enterprise Java Beans y la Capa de pesistencia Java Persistence Api y  dia a dia utilizamos estas especificaciones / apis acompanado  del servidor de aplicaciones de su preferencia (JBOSS, WEBSPHERE, WEBLOGIC, Others) que lo implementa. 

A continuacion vamos a crear un monolito simple en el laboratorio 1 y seguiremos con una aplicacion con codigo pre listo para explicar conceptos de la nueva ola de desarrollo de aplicaciones Empresariales con Java:

## Laboratorio 1

### Levantar Openliberty y correr una aplicacion Simple

Vamos a crear una aplicacion JAX-RS desde 0 y aprederas las consideraciones principales para configurar una aplicacion sobre Open Liberty.

### Creando una aplicación JAX-RS 

Crearemos una clase de aplicación JAX-RS en el archivo src/main/java/io/openliberty/guides/rest/SystemApplication.java:

``
package io.openliberty.guides.rest;

import javax.ws.rs.core.Application;
import javax.ws.rs.ApplicationPath;

@ApplicationPath("System")
public class SystemApplication extends Application {

}
``

La clase SystemApplication extiende la clase Application, que a su vez asocia todas las clases de recursos JAX-RS del archivo WAR con esta aplicación JAX-RS, haciéndolas disponibles bajo la ruta común especificada en la clase SystemApplication. La anotación @ApplicationPath tiene un valor que indica la ruta dentro de la WAR de la que la aplicación JAX-RS acepta peticiones.

### Creando un recurso JAX-RS 

En JAX-RS, una sola clase debe representar un solo recurso o un grupo de recursos del mismo tipo. En esta aplicación, un recurso puede ser una propiedad del sistema o un conjunto de propiedades del sistema. Es fácil tener una sola clase que maneje múltiples recursos diferentes, pero mantener una separación limpia entre los tipos de recursos ayuda a mantener la capacidad de mantenimiento a largo plazo.

Cree la clase de recurso JAX-RS en el archivo src/main/java/io/openliberty/guides/rest/PropertiesResource.java:

``
package io.openliberty.guides.rest;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("properties")
public class PropertiesResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getProperties() {

        JsonObjectBuilder builder = Json.createObjectBuilder();

        System.getProperties()
              .entrySet()
              .stream()
              .forEach(entry -> builder.add((String)entry.getKey(),
                                            (String)entry.getValue()));

       return builder.build();
    }
}
``

Esta clase de recurso tiene bastante código, así que vamos a dividirlo en trozos manejables.

La anotación @Path en la clase indica que este recurso responde a la ruta de propiedades en la aplicación JAX-RS. La anotación @ApplicationPath en la clase de aplicación junto con la anotación @Path en esta clase indica que el recurso está disponible en la ruta System/properties.

``
@Path("properties")
public class PropertiesResource {
``

JAX-RS asigna los métodos HTTP de la URL a los métodos de la clase. El método de llamada se determina por las anotaciones especificadas en los métodos. En la aplicación que está construyendo, una petición HTTP GET a la ruta Sistema/propiedades da como resultado que las propiedades del sistema sean devueltas:

``
@GET
@Produces(MediaType.APPLICATION_JSON)
public JsonObject getProperties() {

    JsonObjectBuilder builder = Json.createObjectBuilder();

    System.getProperties()
          .entrySet()
          .stream()
          .forEach(entry -> builder.add((String)entry.getKey(),
                                        (String)entry.getValue()));

   return builder.build();
}
``
