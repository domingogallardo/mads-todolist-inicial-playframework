import org.junit.*;
import static org.junit.Assert.*;
import javax.inject.*;

import play.test.*;
import static play.test.Helpers.*;

import play.Application;
import play.Mode;
import play.inject.guice.GuiceApplicationBuilder;
import play.inject.Injector;
import play.inject.guice.GuiceInjectorBuilder;

import play.Environment;

import services.UsuarioService;
import services.TareaService;

public class IntegrationTestEjemplo {

   // La clase Injector de Guice nos permite obtener
   // instancias de los objetos inyectados
   static private Injector injector;

   @BeforeClass
   static public void setUpInjector() {
      // Creamos la aplicación a partir del fichero de configuración.
      // El fichero de configuración se puede cambiar en el comando
      // para lanzar sbt y los tests:
      // sbt '; set javaOptions += "-Dconfig.file=conf/integration.conf"; testOnly Integration*'
      GuiceApplicationBuilder guiceApplicationBuilder =
          new GuiceApplicationBuilder().in(Environment.simple());
      // Obtenemos el injector de la aplicación, del que podremos obtener
      // instancias de los objetos inyectados (usuarioService, ...)
      injector = guiceApplicationBuilder.injector();
   }

   @Test
   public void injectorDevuelveObjetosNoNulos(){
      UsuarioService usuarioService = injector.instanceOf(UsuarioService.class);
      TareaService tareaService = injector.instanceOf(TareaService.class);
      assertNotNull(usuarioService);
      assertNotNull(tareaService);
   }
}
