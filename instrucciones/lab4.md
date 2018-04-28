## Lab 4 - Crea un Spring Cloud Eureka Server y Cliente

**Parte 1, crear servidor**

1. Crear una nueva aplicación Spring Boot.
  - Nombrar el proyecto como  "lab-4-eureka-server”, y usar el mismo valor para el Artifact.  
  - Usar empaquetado JAR packaging y Java 8.  
  - Boot version 1.5.x, pero, también pueden usar la última versión si desean.
  - No seleccionar ninguna dependencia.

2. Editar el archivo POM.  Agregar una sección “Dependency Management” (despues de <properties>, antes de <dependencies>) para identificar el spring cloud parent POM.  "Dalston.RELEASE", pero, tu puedes usar la última versión estable.  Ejemplo:

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

3. Agregar una dependencia con group "org.springframework.cloud" y artifact "spring-cloud-starter-eureka-server".  No necesitas especificar una version -- esto ya esta definido en el proyecto padre.

4. Grabar un application.yml (o properties) en tu classpath (src/main/resources).  Agrega el siguiente key / valor (usar formato YAML):
  - server.port: 8010

5. (opcional) Grabar un bootstrap.yml (o properties) en tu classpath.  Agrega el siguiente key / valor (usar formato  YAML):
  - spring.application.name=lab-4-eureka-server

6. Agrega @EnableEurekaServer en la clase Application.  Guarda tus cambios.  Inicia el servidor.  Temporalmente ignora los warnings sobre ejecutar una simple instancia (i.e. connection refused, unable to refresh cache, backup registry not implemented, etc.).  Abre un navegador y ve a [http://localhost:8010](http://localhost:8010) para ver al servidor ejecutandose.

    **Parte 2, crear clientes**  
    
    En la siguiente sección vamos a crear varios clientes que trabajaran juntos para componer DC vs Marvel.  DC vs Marvel contendrá héroes de ambas franquicias.  2 servicios aleatoriamente generaran los heroes, y un tercer servicio ensamblara ellos para crear el universo DC vs Marvel.

7. Crear una nueva aplicación web Spring Boot.  
  - Nombrar el proyecto "lab-4-marvel”, y usar este valor para el Artifact.  
  - Usar el empaquetado JAR y la versión java 8.  
  - Usa la versión Boot 1.5.x o la última versión disponible.  
  - Agrega las dependencias web y actuator.

8. Modifica el archivo POM.  
  - Agrega la misma sección dependency management que tu insertaste en el POM del servidor.
  - Agrega una dependencia con group "org.springframework.cloud" y artifact "spring-cloud-starter-eureka".

9. Modifica la clase Application.  Agrega @EnableDiscoveryClient.

10. Guarda un application.yml (o properties) en tu classpath (src/main/resources).  Agrega el key / valor (usar el formato YAML correctamente):
  - eureka.client.serviceUrl.defaultZone=http://localhost:8010/eureka/
  - heroes=Batman,Mujer Maravilla,Flash,Linterna Verde
  - server.port=${PORT:${SERVER_PORT:0}}
(esto producira un puerto aleatorio, para ser asignado, si ninguno es asignado)

11. Graba un bootstrap.yml (o properties) en tu classpath.  Agrega el siguiente key / valor (usa el formato YAML correctamente):
  - spring.application.name=lab-4-dc

12. Agrega una clase Controladora
  - Coloca este en el paquete 'demo' o subpaquete de tu elección.
  - Nombra la clase como tu gustes.  Anota esta con @RestController.
  - Agrega un campo String denominado "heroes".  Anota este con @Value("${heroes}”).
  - Agrega el siguiente método para servir el recurso (optimiza el código si gustas):
  ```
    @RequestMapping("/")
    public @ResponseBody String getHeroe() {
      String[] heroeArray = heroes.split(",");
      int i = (int)Math.round(Math.random() * (heroeArray.length - 1));
      return heroe[i];
    }
  ```

13. Repite el paso  7 al 12 (copia el  proyecto si es mas sencillo), y usa los siguientes valores:
  - Name of application: “lab-4-marvel
  - spring.application.name: “lab-4-marvel"
  - words: “Iron Man,Capitan America,Thor,Spiderman”


14. Crea una nueva aplicación web Spring Boot.  
  - Nombra la aplicación “lab-4-dcvsmarvel”, y usa este valor para el Artifact.  
  - Usa el empaquetado JAR y la versión Spring Boot 1.5.x y Java 8.  
  - Agrega las dependencias actuator y web.  
  - Modifica el POM como hiciste en el paso 8. 

15. Agrega @EnableDiscoveryClient a la clase Application. 

16. Guarda un application.yml (o properties) en tu classpath (src/main/resources).  Agrega el key / valor (usa el formato YAML correctamente):
  - eureka.client.serviceUrl.defaultZone=http://localhost:8010/eureka/
  - server.port: 8020

17. Agrega una clase Controladora para ensamblar y retornar DC vs Marvel:
  - Nombra la clase como gustes.  Anota esta con @RestController.
  - Usar @Autowired para obtener un DiscoveryClient (importa desde Spring Cloud).
  - Agrega los siguientes métodos para crear DC vs Marvel obtenido desde los servicios cliente. (sientete libre de optimizar / refactorizar este código como gustes:
  ```
    @RequestMapping("/dcvsmarvel")
    public @ResponseBody String getDcVsMarvel() {
      return 
        getHeroe("LAB-4-DC") + " "
        + getHeroe("LAB-4-MARVEL") + "."
        ;
    }
    
    public String getHeroe(String service) {
      List<ServiceInstance> list = client.getInstances(service);
      if (list != null && list.size() > 0 ) {
        URI uri = list.get(0).getUri();
	if (uri !=null ) {
	  return (new RestTemplate()).getForObject(uri,String.class);
	}
      }
      return null;
    }
  ```

18. Ejecute los servicios cliente y servicio dc-vs-marvel.  (Ejecuta esto dentro de tu IDE, o construye los jars de cada uno con (mvn clean package) y ejecutalos desde la linea de comandos (java -jar name-of-jar.jar), lo que sea mas facil para ti).  (Si ejecutas desde STS, deschequea “Enable Live Bean support” en run configurations).  Puesto que cada servicio usa un puerto separado, todos pueden ser ejecutados en la misma computadora.  Abre [http://localhost:8020/dcvsmarvel](http://localhost:8020/dcvsmarvel) para ver la lista de heroes compuesta.  Refresca el URL y observa como cambia la lista.
 	
  **BONUS - Refactorizar para usar el servidor Spring Cloud Config.**  

  Nosotros podemos usar Eureka junto con el config server para eliminar la necesidad de que cada cliente sea configurado con la ubicacieon del servidor Eureka.

19. Agrega un nuevo archivo en tu repositorio GitHub  (el mismo repositorio usado en el anterior lab) llamado “application.yml” (o properties). Agrega el siguiente key / valor (usa el formato YAML correctamente):
  - eureka.client.serviceUrl.defaultZone=http://localhost:8010/eureka/ 

20. Abre el common-config-server project.  Este es esencialmente el mismo config server que tu produciste en el lab 3.  Modifica el application.yml para apuntar a tu propio repositorio github.  Guarda todo y ejecuta este servidor.  (Usaremos este servidor para todos los demas laboratorios.)  

21. Edita cada application.yml o application.properties de cada cliente.  Elimina el eureka client serviceUrl defaultZone key/valor.  Nosotros obtendremos este del config server.

22. En cada proyecto cliente, agrega el siguiente key/valor en el bootstrap.yml (o bootstrap.properties), usando el formato YAML correctamente: 
  - spring.cloud.config.uri: http://localhost:8001.
  
23. Agrega una dependencia adicional para spring-cloud-config-client. 

24. Asegurate que el servidor eureka se este ejecutando.  Comienza (o reinicia) cada cliente. Abre [http://localhost:8020/dcvsmarvel](http://localhost:8020/dcvsmarvel) para ver la lista completa de heroes.

25. Si tu gustas experimenta moviendo los "heroes" al repositorio GitHub para que sean servidos por el config server.  Usaremos distintos profiles en el mismo archivo (yml) o su equivalente en properties.  Un simple application.yml se vería así:

  ```
  ---
  spring:
    profiles: dc
  words: Superman,Mujer Maravilla,Batman,Flash
  
  ---
  spring:
    profiles: marvel
  words: Iron Man,Thor,Spiderman,Capitan America,Hulk

  ```

  **BONUS - Refactoriza para usar múltiples servidores Eureka**  
    
 Para hacer que la aplicación sea mas tolerante a fallas, nosotros podemos ejecutar múltiples servidores Eureka.  En la practica ejecutamos varios eureka en  diferentes racks / centros de datos, pero para simular lo haremos localmente:

26.  Deten todas las aplicaciones que se esten ejecutando.

27.  Edita en tu computadora Windows el archivo /etc/hosts  (c:\WINDOWS\system32\drivers\etc\hosts).  Agrega las siguientes lineas y guarda tu trabajo:

  ```
  # START section for Microservices with Spring Cloud
  127.0.0.1       eureka-primary
  127.0.0.1       eureka-secondary
  127.0.0.1       eureka-tertiary
  # END section for Microservices with Spring Cloud
  ```

28.  Dentro del proyecto lab-4-server, agrega un application.yml con multiple profiles:
primary, secondary, tertiary.  El server.port debería ser 8011, 8012, y 8013 respectivamente.  El eureka.client.serviceUrl.defaultZone para cada profile debería apuntar a los URLs  "eureka-*" de los otros dos; por ejemplo, el valor primary debería ser: http://eureka-secondary:8012/eureka/,http://eureka-tertiary:8013/eureka/

29.  Ejecuta la aplicación 3 veces, usando -Dspring.profiles.active=primary (y secondary, y tertiary) para activar los profiles relevantes.  El resultado debería ser 3 servidores Eureka los cuales se comunicaran el uno con el otro.

30.  En tu proyecto GitHub, modifica el application.properties eureka.client.serviceUrl.defaultZone para incluir los URIs de los tres servidores Eureka (separado por comas, no espacios).

31.  Inicia todos los clientes.  Abre [http://localhost:8020/dcvsmarvel](http://localhost:8020/dcvsmarvel) para ver la lista completa de heroes.

32.  Para testear la tolerancia a fallos del Eureka, deten las instancias 1 y 2.  Reinicia el cliente dc o marvel para asegurarnos que no tienen dificultades en encontrar el Eureka.  Observa que puede tomar algunos segundos hasta que los clientes y servidores puedan ubicarse y verse que servicios estan up/down.  Asegurate que la lista de heroes aún se muestra.


**Reflexión:**  Hay algunas mejoras que se pueden dar a esta solución.

1. Estos servicios contienen código duplicado.  Esto fue hecho solo para ilustrar la idea.  Tu puedes implementar este sistema usando un simple servidor ‘heroe’ el cual selecciona los diferentes heroes segun su @Profile.  (Esto esta hecho en la solution)

2. ¿Qué sucede si alguno de los servidores “heroe” se cae?  Efectivamente toda nuestra aplicación se cae.  Nosotros mejoraremos esto cuando veamos circuit breakers con Hystrix.

3. Para mejorar la performance, ¿podemos hacer las llamadas en paralelo?  También veremos esto cuando discutamos Ribbon y Hystrix.

4. Veremos una alternativa a RestTemplate cuando discutamos Feign.

