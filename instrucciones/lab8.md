##Lab 8 - Usando Spring Cloud Bus

  **Parte 1 - El Broker**

1.  Baja Rabbit MQ desde [https://www.rabbitmq.com/download.html](https://www.rabbitmq.com/download.html).  Usa la apropiada distribución para tu plataforma.  

2.  Lanza Rabbit MQ y dejalo corriendo.

  **Parte 2 - El Servidor**

3.  Deten todos los servicios que no sean  "lab-8” o "common".

4.  Abre lab-8-config-server.  Este proyecto es identico al que hicimos en el Lab 3.  Abre el  POM, agrega otra  dependencia para  spring-cloud-starter-bus-amqp.  

5.  Abre el application.yml.  Cambia el spring.cloud.config.server.git.uri a tu propio repositorio personal en github.  

6.  Guarda tu trabajo y ejecuta el lab-8-config-server.

  **Parte3 - El Client**

7.  Abre el lab-8-client (identico al cliente del lab 3).  Abre el POM, agrega una dependencia de spring-cloud-starter-bus-amqp.  Tambien agrega  una dependencia para  Spring Boot Actuator (org.springframework.boot / spring-boot-starter-actuator) 

8.  Abre el LuckyHeroeController.  Agrega un @ConfigurationProperties usando prefijo “heroeConfig”.  Observa las  propiedades / getters y setters.

9.  Abre el bootstrap.yml.  Observa el nombre de la aplicación.  ¿Que archivo de tu repositorio Git guardara las propiedades de tu aplicacion?

  **Part 4 - El Repository**

10.  Dentro de tu repositorio Git guarda el lucky-heroe-client.yml (o .properties).  Agrega un nivel para  “heroeConfig:” y entradas dentro de este para  “luckyHeroe:” y “preamble:”.  Agrega un valor para  lucky heroe, algo como  “Ultra X” o “Mr. Molecula” o “Mr. X”.  El preamble valor será “El lucky heroe es”.  Guarda tu trabajo y commitea al repositorio.

  **Parte 5 - Ejecución**

11.  Inicia tu aplicación lab-8-client.  Este debería iniciar sin errors.

12.  Abre el navegador en [http://localhost:8002/lucky-heroe](http://localhost:8002/lucky-heroe).  Deberíamos ver la salida "El lucky heroe es: Ultra X”.  Sino pasa eso, revisa los pasos anteriores.

  **Parte 6 - Cambios en la configuración**

13.  Retorna a tu repositorio Git y edita tu lucky-heroe-client.yml.  Cambia el lucky heroe a otro valor.  Comitea tu trabajo.  Este cambio sera visible si refrescas [http://localhost:8002/lucky-heroe](http://localhost:8002/lucky-heroe) en este momento?  Porque?

14.  Haz un POST request a http://localhost:8001/bus/refresh. Hazlo con un comando  “curl” en Linux / Unix, o usa un REST client plugin como POSTMAN.

15.  Refresca [http://localhost:8002/lucky-heroe](http://localhost:8002/lucky-heroe).  Tus cambios seran visibles.  Si es así, felicitaciones, haz usado satisfactoriamente Spring Cloud Bus!

  **BONUS - @RefreshScope**

16.  Retorna a tu LuckyHeroeController y conviertelo a @RefreshScope.  Para propositos de la prueba puedes hacer el controller stateful definiendo otra variable String  llamada fullStatement, y poblar este en un método init() marcado con  @PostConstruct.  Cambia el showLuckyHeroe() para retornar un fullStatement.  Repite el proceso para hacer un cambio y ver si tu  @RefreshScope trabaja.
