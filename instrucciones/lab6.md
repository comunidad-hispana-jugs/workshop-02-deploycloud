## Lab 6 - Usando clientes Feign 

**Parte 1, Iniciar servicios existentes.**

1.  Deten todos los servicios que se han estado ejecutando. Si usas un IDE cierra todo lo que no esta relacionado con el "lab-6" o "common".

2.  Inicia el common-config-server y el common-eureka-server. Estas son versiones de lo que tu has creado en los primeros labs.

3.  Inicia 2  copias separadas de el lab-6-heroe-server, usando los profiles "dc" y "marvel".  Hay diversas formas de hacer esto, segun tus preferencias:
  - Si deseas usar Maven, abre terminales separadas o command prompts en el directorio  target y ejecuta estos comandos:
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=marvel"
    - mvn spring-boot:run -Drun.jvmArguments="-Dspring.profiles.active=dc"
  - O si deseas ejecuta todos estos directamente desde el STS, clic derecho al proyecto, Run As... / Run Configurations... .  desde the Spring Boot tab especifica un Profile de "marvel", deschequea live bean support, y Run.  Repite este proceso (o copia el run configuration) para el profile "dc".

4.  Chequea que el servidor Eureka se este ejecutando en [http://localhost:8010](http://localhost:8010).   Ignora los warnings acerca de correr una sola instancia; esto es lo esperado. Asegurate que los servicios se estan ejecutando y listando en la sección "Application".	

5.  Opcional - si deseas, puedes hacer clic en el link a la derecha de los servidores. Reemplaza el  "/info" con "/" y refresca varias veces.  Observaras la lista de heroes generada.

6.  Ejecuta el proyecto lab-6-dcvsmarvel-server.  Refresca el Eureka para ver si aparece en la lista.  Verifica que trabaja abriendo [http://localhost:8020/dcvsmarvel](http://localhost:8020/dcvsmarvel).  Tu deberías ver una lista de heroes aparecer.  Vamos a refactorizar este código para usar Feign.

  **Parte 2 - Refactorización**

7.  Primero, observa el proyecto lab-6-dcvsmarvel-server.  Este tiene los cambios del último laboratorio.  La controladora ha sido simplificada para hacer solo trabajo web, la tarea de ensamblar la lista de heroes esta ahora en la capa de servicio.  El DcVsMarvelService usa @Autowire para referencias a componentes  DAO individuales, que han sido creados para obtener los heroes desde recursos remotos.  Los recursos remotos usan la misma tecnologia cliente Ribbon y RestTemplate.

8.  Abre el POM.  Agrega otra dependencia para spring-cloud-starter-feign.

9.  Edita la clase de configuración Application y anotala con @EnableFeignClients.

10.  Refactoriza el servicio "Marvel" para usar Feign.  Crea una nueva interface en el paquete dao  denominado MarvelClient.  Anota este con  @FeignClient. ¿Que deberias usara para el service ID? El código existente debería decirtelo.

11.  Luego, provee la firma del metodo que sera provisto por Feign.  Para tener una idea sobre esto, observa el  lab-6-heroe-server HeroeController.  Observa la  anotación usada y tipo de retorno.  Tu puedes  copiar/pegar esta firma tal cual, excepto 1) elimina la implementación del método, y 2) no hay necesidad de usar @ResponseBody, y 3) agrega method=RequestMethod.GET para indicar que es un request GET.

12.  Edita el DcVsMarvelService.  Reemplaza el private HeroeDao marvelService y su setter con el MarvelClient que hicimos. Dependiendo en como creaste tu MarvelClient, tu puedes necesitar refactorizar el método buildSentence.

13.  Observa que puedes haber introducido un error en  DcVsMarvelServiceImplTest test class.  Haz los ajustes necesarios para acomodar el MarvelClient.

14.  Deten cualquier servicio dcvsmarvel ejecutado anteriormente y lanza uno nuevo.  Verifica que trabaja abriendo [http://localhost:8020/dcvsmarvel](http://localhost:8020/dcvsmarvel).  La aplicación debería trabajar como antes, solo que ahora usa clientes declarativos Feign para hacer la llamada a marvel.

  **BONUS - Refactorización adicional**

15. Refactoriza al cliente DC. Sigue los pasos anteriores.


**Reflexión:**

1. Si bien no tenemos código DAO que requiera unit testing, aun  necesitamos ejecutar INTEGRATION testing.

2. Nuestra aplicación aún falla si no encontramos algún tipo de servicio heroe.  Nosotros daremos solución a este problema con circuit breakers - Hystrix.

3. Para mejorar la performance, ¿Podemos ejecutar las llamadas en paralelo?. Esto lo discutiremos cuando veamos Hystrix.
