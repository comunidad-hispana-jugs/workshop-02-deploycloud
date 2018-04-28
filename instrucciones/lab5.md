## Lab 5 - Usando clientes Ribbon 

**Parte 1, Ejecuta el Config Server, Eureka, y los clientes marvel y dc **

1.  Vamos a hacer un inicio desde cero: deten todos los servicios que se esten ejecutando de ejercicios previos.  Si estas usando un IDE cierra todos los proyectos que no esten relacionados con el  "lab-5" o "common".

2.  Abre el common-config-server y el common-eureka-server. Estas son versiones que tu has creado y usado en los ultimos laboratorios. Si no los habías usado antes, modifica el application.yml del common-config-server para que apunte a tu repositorio de github.

3.  Inicia 2  copias separadas de el lab-5-heroe-server, usando los profiles "dc" y "marvel".  Hay diversas formas de hacer esto, segun tus preferencias:
  - Si deseas usar Maven, abre terminales separadas o command prompts en el directorio  target y ejecuta estos comandos:
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=marvel"
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=dc"
  - O si deseas ejecuta todos estos directamente desde el STS, clic derecho al proyecto, Run As... / Run Configurations... .  desde the Spring Boot tab especifica un Profile de "marvel", deschequea live bean support, y Run.  Repite este proceso (o copia el run configuration) para el profile "dc".
		
4.  Chequea que el servidor Eureka se este ejecutando en [http://localhost:8010](http://localhost:8010).   Ignora los warnings acerca de correr una sola instancia; esto es lo esperado. Asegurate que los servicios se estan ejecutando y listando en la sección "Application".

5.  Opcional - si deseas, puedes hacer clic en el link a la derecha de los servidores. Reemplaza el  "/info" con "/" y refresca varias veces.  Observaras la lista de heroes generada.

  **Parte 2, Modifica el servicio dcvsmarvel para usar Ribbon**	

6.  Ejecuta el servicio lab-5-dcvsmarvel-server .  Refresca el  Eureka para ver si aparece en la lista.  Prueba que trabaja abriendo  [http://localhost:8020/dcvsmarvel](http://localhost:8020/dcvsmarvel). Tu deberías ver una lista diferente de heroes aparecer.  Vamos a refactorizar dicho código para usar Ribbon.

7.  Deten el lab-5-dcvsmarvel-server. Agrega la dependencia org.springframework.cloud / spring-cloud-starter-ribbon.

8.  Ir a Application.java.  Crea un nuevo método @Bean que instancia y retorna un nuevo RestTemplate.  El método @Bean debería también ser anotado con @LoadBalanced - esto asociará el RestTemplate con Ribbon.  El código debería verse de la siguiente manera:

  ```
    //  This "LoadBalanced" RestTemplate 
    //  is automatically hooked into Ribbon:
    @Bean @LoadBalanced
    RestTemplate restTemplate() {
        return new RestTemplate();
    }  
  ```

9.  Abre DcVsMarvelController.java.  Reemplaza el @Autowired DiscoveryClient con un @Autowired RestTemplate.  Esto temporalmente no compilara.

10.  Refactoriza el código en el método getHeroe. Usa el método getForObject de restTemplate para llamar a los servicios necesarios.  El primer argumento debería ser la concatenación de "http://" y el ID del servicio.  El segundo argumento debería simplementer ser un String.class.  La llamada quedaría algo así:

  ```
    return template.getForObject("http://" + service, String.class);
  ```

11.  Ejecuta el proyecto.  Verifica que este trabaja abriendo [http://localhost:8020/sentence](http://localhost:8020/sentence).  La aplicación debería trabajar como lo hizo antes del cambio, aunque ahora esta usando un cliente Ribbon como balanceador de carga.

  **BONUS - Multiples clientes**  En este punto hemos refactorizado el código para usar Ribbon, pero, no hemos visto el poder de Ribbon como balanceador de carga del lado del cliente.  Para ilustrar esto ejecutaremos dos copias del servicio “marvel” con diferentes heroes hardcodeados. Veremos que la lista de heroes se adaptara al uso de valores de ambos servidores.

12. Localiza y deten la copia de el servicio "heroe" que esta sirviendo heroes de "marvel".  Si te has perdido en como localizar dicho servicio, puedes ver la consola de salida y examinar si alguno se reporta asimismo en Eureka como "MARVEL".

13. Abre el lab-5-heroe-server.  Edita el bootstrap.yml y agrega la siguiente configuración de Eureka (el comentario explica el proposito de esta entrada):
  ```
    # Permitir a Eureka que reconozca dos apps de el mismo tipo en el mismo host como instancias separadas:
    eureka:
      instance:
        instanceId: ${spring.cloud.client.hostname}:${spring.application.name}:${spring.application.instance_id:${random.value}}
  ```
14. Ir al POM.  Elimina la dependencia de DevTools.  DevTools es magnifico para detectar cambios y reiniciar la app, pero, va a interferir con los siguientes pasos.

15. Comienza una  copia de lab-5-heroe-server usando el profile “marvel”, como hiciste anteriormente.

16. Mientras este nuevo servicio este  ejecutandose, edite la clase HeroeController.java.  Comente la variable “String heroes y reemplace esta con la versión hard-coded:
  ```
    String heroes = “Deadpool,Ant-man,Daredevil,Jessica Jones;
  ```
17. Inica otra copia de el  lab-5-heroe-server usando el profile “marvel”.  Como cada uno se ejecuta en su propio puerto, no habra conflicto.  Tu ahora tienes dos servidores marvel presentando diferente lista de heroes.  Ambos se registraran con Eureka, y el balanceador de carga Ribbon en el servidor dcvsmarvel pronto aprendara que ambos existen.

18. Retorna a la pagina de Eureka ejecutando [http://localhost:8010](http://localhost:8010).  Refresca este varias veces.  Una vez que el registro ya esta completo, tu veras 2 servicios  “MARVEL” ejecutandose, cada uno con su propio instance ID.

19. Refresca la pagina dcvsmarvel en [http://localhost:8020/dcvsmarvel](http://localhost:8020/dcvsmarvel).  Una vez que esta reconocido el nuevo servicio “MARVEL”, el loadbalancer distribuirá la carga entre los dos servicios, en la mitad del tiempo aparecerá la lista de heroes hardcodeada.

20. Deten uno de los servicios MARVEL y refresca tu pagina dcvsmarvel varias veces.  Observaras que la mitad del tiempo una de las listas ya no esta mas disponible.  En efecto desde que el loadbalancer esta basado en un algoritmo de round-robin, la falla ocurrirá cada segundo que el servicio marvel sea usado.  Si continuas refrescando un poco mas de tiempo, veras que las fallas eventualmente ya no suceden puesto que el cliente ribbon ya se ha actualizado con la lista de servicios del Eureka.

**Reflexión:**

1. Observamos un delay en el registro con Eureka.  Si observamos el log de cada aplicación, este se registra asimismo con el Eureka inmediatamente.  La causa es por la necesidad de sincronizar todos los clientes eureka y servidores; todos ellos necesitan tener la misma metadata.  Un heartbeat de 30 segundos significa que probablemente con 3 servidores necesitaríamos 3 heartbeats para sincronizar todo.  Tu puedes acortar este intervalo, pero, 30 segundos es probablemente OK para muchos caso en producción.

2. El delay del registro tambien se ve afectado cuando detienes un servicio MARVEL, y veras que balanceador de carga Ribbon no nos evita de usar el servicio no disponible. Esto es porque, por defecto, Ribbon toma una lista de servicios saludables de Eureka, y la actualización de dicha lista puede tomar un tiempo como hemos visto. Podríamos usar una diferente estrategia y emplear una regla que evite servicios que no funcionan. Exploraremos mas adelante esto con Hystrix.

3. Nuestra aplicación aún falla, si no puede encontrar al menos uno de los servicios que brinda heroes.  Nosotros mejoraremos esto cuando veamos circuit breakers con Hystrix.

4. Para mejorar la performance, ¿podríamos ejecutar las llamadas en paralelo?  Mejoraremos esto cuando discutamos de Ribbon e Hystrix.

5. Veremos una alternativa a RestTemplate cuando discutamos Feign.
