##Lab 9 - Simple API Gateway con Zuul

  **Parte 1 - Inicio**

1.  Deten todos los servicios que esten ejecutandose de laboratorios previos.  Si usas un IDE cierra todos los proyectos que no tienen que ver con  "lab-9”.

2.  Inicia el  common-config-server y common-eureka-server.  

3.  Lab 9 tiene copias del servidor heroe.  Inicia 2 copias separadas del lab-9-heroe-server, usando los profiles "marvel" y "dc".  Hay diversas formas como:
  - Si usas Maven en distintos command prompt en el directorio target y ejecutando estos comandos:
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=marvel"
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=dc"
  - O si deseas ejecutarlo desde el STS, clic derecho en el proyecto, Run As... / Run Configurations... .  desde el Spring Boot tab especifica un Profile de "marvel", deschequea el live bean support, y Run.  Repite el proceso para "Dc".

4.  Chequea el Eureka en [http://localhost:8010](http://localhost:8010).   Ignora los warnings.

5.  Opcional - Hazle clic derecho a alguno de los servidores.  Reemplaza el "/info" con "/" y refresca este distintas veces.  Tu puedes ver la lista de heroes generada. 



  **Parte2 - Inicia y examina el sistema existente**

6.  Abre lab-9-gateway.  Esta es una simple aplicación web Spring Boot.  Nosotros la convertiremos a un simple API gateway con Zuul.

7.  Examina la pagina templates/dcvsmarvel.html.  Observa que esta contiene JavaScript para hacer llamadas AJAX y obtener los heroes de marvel y dc.  Hay 2 llamadas separadas a hacer, cada uno con un diferente servicio.  Homo el Js hace estas llamadas sin encontrar  cross site scripting restrictions?

8.  Ejecuta el lab-9-gateway.  Accede al [http://localhost:8080](http://localhost:8080).  Tu encontraras que varias llamadas AJAX calls no se pueden completar satisfactoriamente.  Vamos a reparar eso en lo que viene.



  **Parte 3 - Implementa un Proxy reverso Zuul**

9.  Deten la aplicación lab-9-gateway.

10.  Agrega la dependencia para el config client.  org.springframework.cloud / spring-cloud-config-client.  

11.  Agrega la dependencia para Eureka.  org.springframework.cloud / spring-cloud-starter-eureka.

12.  Agrega la dependencia para  Zuul.  org.springframework.cloud / spring-cloud-starter-zuul.

13.  Si usas eclipse, el M2E plugin puede requerir actualizar el proyecto en este punto.  Clic derecho al proyecto / Maven / Update Project

14.  Configura la application para obtener la configuración del config server al iniciar.  ¿Ya sabes como hacer esto?.  Abre el bootstrap.yml y agrega la ubicación del configuration server.  

15.  Abre la clase Application y agrega la anotación para el Zuul proxy.

16.  Guarda tu trabajo.  Ejecuta la aplicación.  Accede a [http://localhost:8080](http://localhost:8080).  La lista de errores debe verse sin errores.



  **Parte 4 - Agrega un prefijo para el servicio**  Nuestra pagina web espera que los recursos JavaScript y CSS esten localizados bajo "/js" y "/css" respectivamente.  Vamos a  hacer que la llamada a nuestros servicios esten bajo  "/services".
  
17.  Abre la pagina templates/dcvsmarvel.html.  Encuentra el comentario TODO en la linea 30.  Cambia la variable prefijo a  "/services".  Esa variable sera usada en las siguientes lineas.

18.  Refresca la pagina en el navegador.  Nosotros obtendremos algunos errores en este punto.  ¿Porque?

19.  Abre el application.yml.   Configura el prefijo zuul a "/services".  Guarda tu trabajo y reinicia.


  **Parte 5 - Agregar soporte a  ETag**  En este momento nuestro servidor envía heroes individuales por medio de los request AJAX aun si el navegador ya había enviado el valor antes.  Los ETags pueden ser usados para eliminar la necesidad de enviar el  payload a el cliente cuando nada ha cambiado.
  
20.  Dentro de tu navegador, abre el Developer Tools (Internet Explorer / Chrome), Firebug (Firefox), Web Inspector (Safari), refresca la pagina web, y examina la actividad network.  El browser recibe un 304 code en lugar de un  200 para el JavaScript y CSS puesto que estos ya no estan cambiando.  Vamos a agregar un soporte similar para las llamadas AJAX de heroes si es que estos no cambian.

21.  Abre tu clase  Application y agrega este Bean:

    ```
    @Bean
    public Filter shallowEtagHeaderFilter() {
        return new ShallowEtagHeaderFilter();
    }   
    ```    


22.  Guarda tu trabajo y reinicia.  Refresca el navegador varias veces.  Observa que recibimos aleatoriamente varios 304s para los requests AJAX en lugar de  200s. 

**Reflexión**

1.  ¿Como la aplicación cuales son los servicios de heroes?  Zuul automaticamente usa  Eureka.

2.  ¿Como la aplicación se contacta con Eureka?  Nosotros usamos Spring Cloud Config, y el server / repository que nosotros usamos conoce la ubicación.

3.  Esta web usa Thymeleaf, JQuery, y Bootstrap, aunque su uso es basico.  El application.properties tiene una configuración que hace que los cambios al Thymeleaf template sean cargados inmediatamente. Esto es util solo en desarrollo.
