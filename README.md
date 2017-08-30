# Práctica asignatura MADS

Realizada por Domingo Gallardo.

La práctica consiste en un proyecto implementado en [Play Framework 2.5](https://www.playframework.com/documentation/2.5.x/Home) para gestionar las tareas pendientes de un conjunto de usuarios de una empresa.

Aquí hay una breve descripción de cómo lanzar la práctica. La documentación
completa de la práctica se encuentra en el repositorio
[apuntes-mads](https://github.com/domingogallardo/apuntes-mads), en el
documento [Práctica 1: Primera aplicación Play Framework Java](https://github.com/domingogallardo/apuntes-mads/blob/master/practicas/01-introduccion-play/introduccion-play.md).


## Cómo ejecutar el proyecto

### Usando `sbt`

Se trata de una aplicación Play y necesitas [sbt](http://www.scala-sbt.org/).

Descarga el proyecto y, dentro del directorio, ejecuta:

```
sbt run
```

Este comando descargará todas las dependencias (tarda bastante) y pondrá
en marcha el servidor web con la aplicación. Luego ve a <http:localhost:9000>
para ver la aplicación web funcionando.

### Usando una imagen Docker

También es posible probar el proyecto usando una imagen de Docker.
Descarga el proyecto y, dentro del directorio, ejecuta:

```
docker run -it --rm -v ${PWD}:/code -p 80:9000 domingogallardo/playframework
```

Esto lanza una imagen docker que contiene todas las dependencias necesarias
para ejecutar el proyecto. La imagen docker tiene como comando principal `sbt`,
y lo único que hay que hacer es escribir `run`:

```
[mads-todolist-2017] $ run
```

Podrás acceder a la aplicación web en el ordenador host en <http:localhost>.
