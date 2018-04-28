## Lab 7 - Usando Hystrix Circuit Breakers

**Parte 1, Iniciar servicios existentes**

1.  Deten todos los servicios que se han estado ejecutando. Si usas un IDE cierra todo lo que no esta relacionado con el "lab-7" o "common".  

2.  Inicia el common-config-server y common-eureka-server.  

3.  Lab 7 tiene copias del  heroe server y dcvsmarvel server que han sido convertidos para usar Feign, ahora modificaremos los mismos para usar Hystrix.  Inicia 2 copias separadas del  lab-7-heroe-server, usando los profiles "marvel" y  "dc".  Hay diversas formas de hacer esto, segun tus preferencias:
  - Si deseas usar Maven, abre terminales separadas o command prompts en el directorio  target y ejecuta estos comandos:
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=marvel"
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=dc"
 - O si deseas ejecuta todos estos directamente desde el STS, clic derecho al proyecto, Run As... / Run Configurations... .  desde the Spring Boot tab especifica un Profile de "marvel", deschequea live bean support, y Run.  Repite este proceso (o copia el run configuration) para el profile "dc".

4.  Chequea que el servidor Eureka se este ejecutando en [http://localhost:8010](http://localhost:8010).   Ignora los warnings acerca de correr una sola instancia; esto es lo esperado. Asegurate que los servicios se estan ejecutando y listando en la sección "Application".	


5.  Opcional - si deseas, puedes hacer clic en el link a la derecha de los servidores. Reemplaza el  "/info" con "/" y refresca varias veces.  Observaras la lista de heroes generada.


6.  Ejecuta el proyecto lab-7-dcvsmarvel-server.  Refresca el Eureka para ver si aparece en la lista.  Verifica que trabaja abriendo [http://localhost:8020/dcvsmarvel](http://localhost:8020/dcvsmarvel).  Tu deberías ver una lista de heroes aparecer.  Vamos a refactorizar este código para usar Hystrix.



  **Parte 2 - Refactorización**


7.  Primero, observa el proyecto lab-7-dcvsmarvel-server. Este tiene el código de los laboratorios previos.  Hay un  HeroeService y HeroeServiceImpl que wrapea las llamadas a los clientes Feign. 

8.  Abre el POM.  Agrega otra dependencia para spring-cloud-starter-hystrix.

9.  Edita la clase Application y anotala con @EnableHystix.

10.  Refactoriza el HeroeServiceImpl para usar Hystrix.  Hemos decidido que no es estrictamente necesario tener un dc heroe en nuestra lista si es que el servicio DC esta fallando, así que modificaremos el servicio getDc para ejecutarse como un comando Hystrix.  Estableceremos un método fallback (alternativo) que retornará un Heroe vacío (new Heroe(“”)).

11.  Deten cualquiero servicio dcvsmarvel que se este ejecutando y lanza uno nuevo.  Verifica que trabaja abriendo[http://localhost:8020/dcvsmarvel](http://localhost:8020/dcvsmarvel).  La aplicación debería trabajar igual, solo que ahora la llamada al servicio  “Dc” va a traves de un Hystrix circuit breaker.

12.  Localiza y deten del servicio Dc.  Refresca [http://localhost:8020/dcvsmarvel](http://localhost:8020/dcvsmarvel).  La lista de heroes no debería tener ningún heroe de Dc.  Reinicia el servicio Dc. Una vez que el registro de  Eureka se haya completado y el  circuit breaker re-cerrado, La lista de heroes nuevamente mostrará heroes de DC.

  **BONUS - Agregar Hystrix Dashboard**

14.  Agrega el Hystrix Dashboard a tu servicio dcvsmarvel.  Comienza agregando la dependencia spring-cloud-starter-hystrix-dashboard, luego agrega @EnableHystrixDashboard a tu clase Application.

15.  Reinicia el servicio dcvsmarvel.  Abre [http://localhost:8020/hystrix](http://localhost:8020/hystrix).  Cuando salga la  solicitud de que observar, ingresa http://localhost:8020/hystrix.stream en el host para monitorear.  

16.  Refresca [http://localhost:8020/dcvsmarvel](http://localhost:8020/dcvsmarvel) varias veces para generar actividad.  Si detienes los servicios clientes podras ver el uso de los circuit breakers.

  **BONUS - Agrega comportamiento  Asyncrono**

17.  Haz en tu servicio dcvsmarvel que las llamadas a sus otros servicios sea de forma “reactiva”.
