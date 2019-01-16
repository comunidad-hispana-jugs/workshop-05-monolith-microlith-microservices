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

###Notas:

El repositorio esta compuesto por un proyecto que puede ser desplegado como monolito, microlito o microservicios; ademas en los primeros laboratorios vamos a crear aplicaciones simples sin necesidad del codigo pre elaborado. El inicio del taller comienza con el archivo readme.md que contine un resumen del trabajo a realizar. 

El repositorio esta compuesto por 4 carpetas:

- lab01: Incluye nuestro primer taller con una aplicacion simple que expone un API rest del listado de grupos de usuarios Java participantes del Hackday. 
- lab02: Incluye la estructura de nuestro proyecto que expone un API rest de administracion de los miembros y grupos de usuarios participantes del Hackday. 
- lab03: De igual maneera que la rama monotilo esta compuesta de dos folders start y finish.
- lab04: Contiene el proyecto inicial para basarlo en microservicios y la carpeta finish el taller completo.

Cada rama tiene un archivo readme.md por el que comienza el taller de cada patron de construccion de aplicaciones empresariales.

## Monolitos

La mayoria de desarrolladores que llevan algunos años codificando en java estan muy familiarizados con un patron comun de desarrollo de aplicaciones empresariales multi capa que se denomina aplicacion Monolitica separada en capas de la siguiente manera:

----------     -----------     ----------------
| WEB    | <-> | BUSINESS| <-> | BASE DE DATOS |
----------     -----------     -----------------

La capa web es desarrollada usando Java Server Faces u en los ultimos años se usa Angular, Angular JS, React, VueJS u otro framework.

La capa de negocio comunmente la hemos desarrollado usando Enterprise Java Beans y la Capa de pesistencia Java Persistence Api y  dia a dia utilizamos estas especificaciones / apis acompanado  del servidor de aplicaciones de su preferencia (JBOSS, WEBSPHERE, WEBLOGIC, Others) que lo implementa. 

A continuacion vamos a crear un monolito simple en el laboratorio 1 y seguiremos con una aplicacion con codigo pre listo para explicar conceptos de la nueva ola de desarrollo de aplicaciones Empresariales con Java:

## Laboratorio 1

Cada laboratorio inicia con el folder del numero de laboratorio; es decir en esta caso vamos a dirigirnos al directorio lab01.

Vamos a encontrar dos carpetas:

- start: Contiene un proyecto base con el que vamos a trabajar.
- finish: Contiene el proyecto luego de realizar el laboratorio.

### Levantar Openliberty y correr una aplicacion Simple

Vamos a crear una aplicacion JAX-RS desde 0 y aprederas las consideraciones principales para configurar una aplicacion sobre Open Liberty. 

### Creando una aplicación Rest con JAX-RS 

Al crear una nueva aplicación REST, el diseño de la API es importante. Las APIs de JAX-RS podrían ser creadas con JSON-RPC, o APIs XML-RPC, pero no sería un servicio RESTful. Un buen servicio RESTful está diseñado en torno a los recursos que se exponen, y cómo crear, consultar, actualizar y eliminar recursos.

Vamos a crear un servicio simple responderia a las peticiones de GET a la ruta /hackday/group. La solicitud GET debe devuelve una respuesta 200 OK que contiene un conjunto de grupos de usuario participantes de nuestro hackday.

Vamos a crear una clase de aplicación JAX-RS en el archivo src/main/java/org/ecjug/hackday/app/HackDayApplication.java:

``
package org.ecjug.hackday.app;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/hackday")
public class HackDayApplication extends Application {

}
``

La clase HackDayApplication extiende la clase Application, que a su vez asocia todas las clases de recursos JAX-RS del archivo WAR con esta aplicación JAX-RS, esta haciendo disponibles bajo la ruta común especificada en la clase HackDayApplication. La anotación @ApplicationPath tiene un valor que indica la ruta dentro de la WAR de la que la aplicación JAX-RS acepta peticiones, es decir (/hackday).

### Creando un recurso JAX-RS 

En JAX-RS, una sola clase debe representar un solo recurso o un grupo de recursos del mismo tipo. En nuestra aplicación, un recurso puede ser una propiedad del sistema o un conjunto de propiedades del sistema. Es fácil tener una sola clase que maneje múltiples recursos diferentes, pero mantener una separación limpia entre los tipos de recursos ayuda al mantenimiento a largo plazo de la aplicacion.

Vamos a crear la clase de recurso JAX-RS en la carpeta src/main/java/org/ecjug/hackday/app/resources/GroupResource.java:

``
package org.ecjug.hackday.app.resources;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/group")
public class GroupResource {

    @GET
    @Path("/list")
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject listGroups() {
        JsonObjectBuilder builder = Json.createObjectBuilder();
        builder.add("ECJUG","Ecuador Java User Group");
        builder.add("MEDJUG","Medellin Java User Group");
        builder.add("BJUG","Barcelona Java User Group");
        builder.add("MJUG","Madrid Java User Group");
        builder.add("MalagaJUG","Malaga Java User Group");

        return builder.build();
    }
}
``


La anotación @Path en la clase indica que este recurso responde a la ruta de propiedades en la aplicación JAX-RS. 

La anotación @GET en el método indica que este método debe llamarse para el método HTTP GET. La anotación @Produce indica el formato del contenido que se devolverá, el valor de la anotación @Produces se especificará en el encabezado de respuesta HTTP Content-Type. Para esta aplicación, se debe devolver una estructura JSON. El tipo de contenido deseado para una respuesta JSON es application/json con MediaType.APPLICATION_JSON en lugar del tipo de contenido String. Usar código literal como MediaType.APPLICATION_JSON es mejor porque en el caso de un error ortográfico, ocurre una falla de compilación.

JAX-RS soporta varias formas de reunir a JSON. La especificación JAX-RS exige el procesamiento JSON (JSON-P) y JAX-B. La mayoría de las implementaciones JAX-RS también soportan una conversión de Java POJO a JSON, que permite devolver el objeto Properties en su lugar. Aunque esta conversión permitiría una implementación más simple, limita la portabilidad del código ya que la conversión de POJO a JSON no es estándar. Esta laguna en la especificación está fijada en Java EE 8 con la inclusión de JSON-B.

El cuerpo del método realiza las siguientes acciones: Crea un objeto JsonObjectBuilder utilizando la clase Json. El JsonObjectBuilder se utiliza para rellenar un JsonObject con valores.

Llama al método entrySet en el objeto Properties para obtener un Set de todas las entradas.
Convierta el Set a un Stream (nuevo en Java SE 8) llamando al método stream. Las secuencias hacen que trabajar con todas las entradas de una lista sea muy sencillo. Devuelve el JsonObject llamando al método build en el JsonObjectBuilder.

### Configurando el Servidor

Para que el servicio funcione, el servidor de Liberty debe estar configurado correctamente.

Vamos a editar el archivo src/main/liberty/config/server.xml y debe quedar de la siguiente manera:
``
<server description="Laboratorio 01 Hackday Open Liberty Server">

  <featureManager>
      <feature>jaxrs-2.1</feature>
      <feature>jsonp-1.1</feature>
  </featureManager>

  <httpEndpoint httpPort="${default.http.port}" httpsPort="${default.https.port}"
      id="defaultHttpEndpoint" host="*" />

  <webApplication location="application.war" contextRoot="${app.context.root}"/>
</server>
``
La configuración realiza las siguientes acciones:

- Configura el servidor para soportar tanto JAX-RS como JSON-P. Esto se especifica en el elemento featureManager.

- Configura el servidor para que recoja los números de puerto HTTP de las variables, que luego se especifican en el archivo pom.xml de Maven. Esto se especifica en el elemento httpEndpoint. Las variables utilizan la sintaxis ${variableName}.

- Configura el servidor para que ejecute la aplicación Web producida en una raíz de contexto especificada en el archivo pom.xml de Maven. Esto se especifica en el elemento webApplication.

Las variables que se utilizan en el archivo server.xml son proporcionadas por la sección bootstrapProperties del pom.xml de Maven:

``
<bootstrapProperties>
    <default.http.port>${testServerHttpPort}</default.http.port>
    <default.https.port>${testServerHttpsPort}</default.https.port>
    <app.context.root>${warContext}</app.context.root>
</bootstrapProperties>
``

### Ejecutando nuestro servidor

Para crear la aplicación, ejecute la fase de instalación de Maven desde la línea de comandos en el directorio de inicio:

``
mvn install
``
Este comando construye la aplicación y crea un archivo.war en el directorio de destino. También configura e instala Open Liberty en el directorio target/liberty/wlp.

A continuación, ejecute el objetivo de Maven liberty:start-server:

``
mvn liberty:start-server
``

Este objetivo inicia una instancia de servidor Open Liberty. Su pom.xml de Maven ya está configurado para iniciar la aplicación en esta instancia de servidor.

### Test de nuestro servicio

Puede probar este servicio manualmente iniciando un servidor y apuntando un navegador web a la URL de http://localhost:9080/application/hackday/group/list obteniendo como resultado el listado de grupos de usuarios participantes en este hackday (si tu JUG no esta listado incluyelo):

``
{"ECJUG":"Ecuador Java User Group","MEDJUG":"Medellin Java User Group","BJUG":"Barcelona Java User Group","MADRIDJUG":"Madrid Java User Group","MalagaJUG":"Malaga Java User Group"}
``

Las pruebas automatizadas son necesarias en nuestro dia a dia para evitar  fallas si un cambio introduce un error. JUnit y el API del cliente JAX-RS proporcionan un entorno muy sencillo para probar la aplicación.

Vamos a crear una clase de prueba en el archivo src/test/java/org/ecjug/hackday/app/rest/GroupRestTest.java:

``
package org.ecjug.hackday.app.rest;

import static org.junit.Assert.assertEquals;

import javax.json.JsonObject;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

import org.apache.cxf.jaxrs.provider.jsrjsonp.JsrJsonpProvider;
import org.junit.Test;

public class GroupRestTest {

    @Test
    public void testGetProperties() {
        String url = "http://localhost:9080/application/";

        Client client = ClientBuilder.newClient();
        client.register(JsrJsonpProvider.class);

        WebTarget target = client.target(url + "hackday/group/list");
        Response response = target.request().get();

        assertEquals("Incorrect response code from " + url, Response.Status.OK.getStatusCode(), response.getStatus());

        JsonObject obj = response.readEntity(JsonObject.class);

        assertEquals("Ecuador Java User Group",
                    obj.getString("ECJUG"));
        response.close();
    }
}

``
Esta clase de prueba tiene más líneas de código que la implementación del recurso. Esta situación es común. El método de prueba se indica con la anotación @Test. El código de prueba necesita saber alguna información sobre la aplicación para poder hacer solicitudes. El puerto del servidor y la raíz del contexto de la aplicación son clave, y están dictados por la configuración del servidor. 

Hemos terminado nuestro primer ejercicio de codigo; ahora vamos a pasar al laboratorio 2.

## Laboratorio 2
