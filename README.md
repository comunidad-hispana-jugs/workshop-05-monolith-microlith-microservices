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

## Laboratorio 1

### Levantar Openliberty y correr una aplicacion Simple

Vamos a crear una aplicacion JAX-RS desde 0 y aprederas las consideraciones principales para configurar una aplicacion sobre Open Liberty.  

