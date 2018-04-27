## Lab 3 - Crear un Servidor Spring Cloud Config y Cliente

**Parte 1 - Servidor Configuración:**

1. Crear una nueva aplicación Spring Boot.  Nombra el proyecto como "lab-3-server”, y usa este valor para el Artifact.  Usar el empaquetado Jar y la versión de Java = 8.  Usa la versión de Spring Boot = 1.5.x.  No hay necesidad de seleccionar ninguna dependencia.

1. Edita el archivo POM.  Agrega una sección “Dependency Management”  (despues de <properties>, antes de <dependencies>) para identificar el spring cloud parent POM.  "Dalston.RELEASE" que es la versión que trabaja bien con la versión de Spring Boot 1.5.x, pero, tu puedes usar la ultima versión estable disponible.  Ejemplo:

```
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Dalston.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```


1. Agrega una dependencia para group "org.springframework.cloud" y artifact "spring-cloud-config-server".  Tu no necesitas especificar una versión --esto ya esta definido en el  spring-cloud-dependencies BOM.

1. Edita la clase principal de la aplicación (denominada Application).  Agrega el @EnableConfigServer a esta clase.

1. Crea un nuevo repositorio en GitHub para guardar la configuración.  Nombra a ese repositorio "ConfigData" o el nombre que desees.  Observa el full URI del repository, se va a necesitar en el siguiente paso.

1. Agrega un nuevo archivo a tu repositorio GitHub denominado "lab-3-client.yml” (o lab-3-client.properties).  Y agregar un key "jugs-presentes" and a value of "Barranquilla JUG", "Peru JUG", "Malaga JUG", "Madrid JUG",  o cualquier otro valor que desees.

1. Regresa a tu proyecto y crea un application.yml (o application.properties) en la raiz de tu classpath (src/main/resources).  Agrega el  "spring.cloud.config.server.git.uri" y el valor "https://github.com/"TU-GITHUB-ID"/ConfigData", substituye el  Github ID y el nombre del repositorio segun sea tu caso.  También establece el  “server.port” a 8001.

8. Ejecuta la aplicación.  Abre el URL [http://localhost:8001/lab-3-client/default/](http://localhost:8001/lab-3-client/default/).  Nosotros deberíamos ver el JSON resultante que sera usado por Spring.  Si el servidor no trabaja, revisa los pasos anteriores y continúa. 

  **Parte 2 - Cliente del Servidor de Configuración:**

9. Crea un nuevo proyecto Spring Boot application.  Usa una versión de Boot = 1.5.x.  Nombra el proyecto como "lab-3-client", y usa el valor para el Artifact.  Agrega la dependencia web. 

10.  Abre el archivo POM y agrega una sección “Dependency Management” (despues de <properties>, antes de <dependencies>) para identificar el spring cloud parent pom. (Cambia los valores que sean necesarios):
```
    <dependencyManagement>
        <dependencies>
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Dalston.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
        </dependencies>
    </dependencyManagement>
```
11.  Agrega una dependencia para group  "org.springframework.cloud" y artifact "spring-cloud-starter-config”.  Tu no necesitas especificar una version -- esto ya esta definido en el parent pom en la sección dependency management.

12. Agrega un bootstrap.yml (o bootstrap.properties) en tu classpath (src/main/resources).  Agrega los siguientes key/values usando el formato apropiado:
spring.application.name=lab-3-client
spring.cloud.config.uri=http://localhost:8001  
server.port=8002

    _(OJO este archivo debe ser "boostrap" -- y no "application" -- para que sea leido en primera instancia en el proceso de startup de la aplicación.  El server.port si puede ser especificado en cualquiera de los dos archivos, pero, el URI a el servidor de configuración afecta el proceso de startup.)_

13. Agrega una controladora REST controller para obtener y mostrar los jugs participantes:

    ```
    @RestController
    public class JugsPresentesController {
 
      @Value("${jugs-presentes}") String jugsPresentes;
  
      @RequestMapping("/jugs-presentes")
      public String showLJugsPresentes() {
        return "Los Jugs presentes son: " + jugsPresentes;
      }
    }
    ```

14.  Inicia tu cliente.  Abre [http://localhost:8002/jugs-presentes](http://localhost:8002/jugs-presentes).  Tu deberías ver la lista de jugs presentes en el navegador.

  **BONUS - Profiles:**

15. Crea un nuevo archivo en tu repositorio GitHub demoninado "lab-3-client-europa.yml” (or .properties).  Puebla este con "jugs-presentes" y coloca valores diferentes del archivo original.

16. Deten la aplicación cliente.  Modifica el archibo bootstrap para que contenga un key  spring.profiles.active: europa.  Guarda, y reinicia tu cliente.  Accede a el URL.  ¿Qué JUGs son mostrados?  (Tu deberías ejecutar la aplicación con  -Dspring.profiles.active=europa en lugar de cambiar el archivo bootstrap)

### Reflección:  
1. Observa que el cliente necesita algunas dependencias para Spring Cloud, y el URI del Spring Cloud server, pero nada de código.
2. ¿Qué sucede si el Config Server no esta disponible cuando la aplicación cliente inicia?  Para mitigar esta posibilidad, es comun ejecutar varias instancias de config server en diferentes racks / zonas detras de un balanceador de carga.
3. ¿Qué sucede si cambiamos una propiedad despues de que el cliente ha iniciado?. El servidor toma los cambios rapidamente, pero, el cliente no lo hace.  Veremos después como Spring Cloud Bus y refresh scope pueden ser usados para propagar dinamicamente los cambios.
