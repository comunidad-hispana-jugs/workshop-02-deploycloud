## Lab 1 - Spring Boot

- En este ejercicio crearemos una simple Spring Boot application.  

**Parte 1 - Simple Aplicación Web**

1.  Usa Spring Tool Suite, o [https://start.spring.io](https://start.spring.io), y crea un nuevo Spring Boot Project.
  - Usa Maven.  Todos los laboratorios estan basados en Maven.
  - Usaremos la ultima versión estable de Boot 1.5.X y Java 8.  Las instruciones de cada laboratorio han sido verificadas con Java 1.8, Boot 1.5.12.RELEASE.
  - Usa los valores que tu gustes para group, artifact, package, description, etc.
  - Selecciona las siguientes dependencias: Web, Thymeleaf, JDBC, JPA, HSQLDB, Actuator.

2.  Crea una nueva Controladora en el paquete base:
  - Coloca a la controladora el nombre que gustes.  
  - Anota la controladora con @Controller.

3.  Crea un nuevo método en la controladora:
  - Coloca el nombre que quieras al método.  Este debe retornar un String.  Ningún parámetro es necesario.
  - Anota el método con @RequestMapping("/")
  - Haz que el método retorne el String "hello".

4.  Si es que no esta presente, crear un nuevo directorio bajo src/main/resources llamado "templates"

5.  Crea un nuevo archivo en el directorio templates denominado "hello.html".  Coloca las palabras "Hello desde Thymeleaf" (o cualquier titulo que desees) dentro del archivo. 

6.  Guarda todo tu trabajo.  Ejecuta tu aplicación.
  - Si estas trabajando en Spring Tool Suite, simplemente haz clic derecho en la aplicación / Run As / Spring Boot App.
  - Si estas trabajando en otro IDE, ejecuta el método main que tiene la clase principal de la aplicación.  
  - Si deseas ejecutarlo desde la terminal o linea de comandos, ve al directorio raiz del proyecto y ejecuta mvn spring-boot:run. 
  
7.  Abre el navegador y ve a [http://localhost:8080/](http://localhost:8080/).  Deberíamos ver nuestra pagina web.


  **Parte 2 - Returnar una respuesta RESTful**
  
9.  Crear una nueva clase Java denominada "Jug" en el paquete base. Dale a este un campo id de tipo Long, y campos String para name, location, y mascot (o las propiedades que tu desees).  Genera "getters and setters" para todos los campos. Guarda tu trabajo.

10.  Crea una nueva Controladora denominada "JugController".  Anota esta con @RestController.

11.  Crea un nuevo método en el JugController.
  - Nombra el método con "getJugs".  Asegurate que retorne una lista de objetos Jugs.
  - Anota el método con @RequestMapping("/jugs")
  - Hacer que el metodo retorne una lista de objetos Jug.  Crea uno o mas objetos Jug y agrega este a la lista.  Coloca a los Jugs los valores que quieras y retorna la lista.  Ejemplo:
  ```
	@RequestMapping("/jugs")
	public List<Jug> getJugs() {
		List<Jug> list = new ArrayList<>();

		Jug jug = new Jug();
		jug.setId(0l);
		jug.setLocation("Colombia");
		jug.setName("Barranquilla JUG");
		list.add(jug);
		
		jug = new Jug();
		jug.setId(1l);
		jug.setLocation("Malaga");
		jug.setName("Malaga JUG");
		list.add(jug);
		
		return list;
	}

  ```

12.  Guarda todo tu trabajo.  Deten la aplicación si ya esta ejecutandose, e inicia este nuevamente.  Abre [http://localhost:8080/jugs](http://localhost:8080/jugs).  Tu deberías obtener una respuesta JSON con los datos de tus jugs.

  **Parte 3 - Crea repositorios con Spring Data JPA**
  
13.  Vuelve a la clase Jug.  Agrega anotaciones JPA:  La clase debe ser anotada con @Entity, el id debe ser anotado con @Id y @GeneratedValue.

14.  Crea una nueva Interface denominada "JugRepository".  Haz que herede de CrudRepository<Jug,Long>.
  - Asegurate de que sea una Interface, no una clase!
  
15.  Abre la clase principal de la aplicación (aquella que esta anotada con @SpringBootApplication).  Usa @Autowired para inyectar una dependencia como variable miembro de tipo JugRepository.  Coloca el nombre de variable que tu gustes (sugerencia: "jugRepository").

16.  Agrega algo de lógica para poblar la base de datos:  Agrega un método public void init().  Anota este con @PostConstruct.  Corta y pega el código para la creación de jugs en este método,  llama al método save() de tu repositorio.  También, elimina los IDs de los jugs.  Código de ejemplo:
  ```
    public void init() {
		List<Jug> list = new ArrayList<>();

		Jug jug = new Jug();
		jug.setLocation("Colombia");
		jug.setName("Barranquilla JUG");
		list.add(jug);
		
		jug = new Jug();
		jug.setLocation("Peru");
		jug.setName("Peru JUG");
		list.add(jug);

		jugRepository.save(list);
	}    
  ```

17.  Regresa al JugController.  Usa @Autowired para inyectar la dependencia JugRepository.  Nombra la variable como gustes (sugerencia: "jugRepository").

18.  Modifica la logica en el metodo de tu controladora para simplemente retornar el resultado del metodo findAll() del repositorio.
  ```
	@RequestMapping("/jugs")
	public Iterable<Jug> getJugs() {
		return jugRepository.findAll();
	}
  ```
19.  Guarda todo tu trabajo. Deten la aplicación si ya se estaba ejecutando, e inicia este nuevamente.  Abre [http://localhost:8080/jugs](http://localhost:8080/jugs).  Tu deberías ver una respuesta JSON con la data de tus jugs.


  **Parte 4 (Opcional)- Crea un simple endpoint Jug**
  
20.  Regresar a el JugController y agrega un método que retorne un simple Jug dado un ID.
  - Nombra el método como gustes.  Sugerencia: getJug.
  - El tipo de retorno debería ser Jug.
  - Usa una anotación @RequestMapping para asociar este método con el patrón "/jugs/{id}".
  - Define un parametro denominado  "id" de tipo Long anotado con @PathVariable.
  - Logica: retorna el resultado del método findOne() del jugRepository.

19.  Guarda todo tu trabajo.  Deten la aplicación si ya se esta ejecutando, e inicia este nuevamente.  Usa [http://localhost:8080/jugs](http://localhost:8080/jugs) para anotar los IDs generados para cada Jug.  Luego usa URLs como  [http://localhost:8080/jugs/1](http://localhost:8080/jugs/1) o [http://localhost:8080/jugs/2](http://localhost:8080/jugs/2) para obtener los resultados de jugs solicitados.

  
  **Parte 5 - Agregar Members**

20.  Agregar una nueva clase denominada Member.  Agrega campos para id, name, y rol.  El id debería ser Long, y otros campos pueden ser Strings.  Genera getters / setters para cada campo.  Agrega una anotación @Entity en la clase, y anotaciones @Id y @GeneratedValue en el id.   Deberiás agregar un constructor personalizado para poder crear objetos Member.  (Si hacemo esto, aseguremosno de tener tambien un constructor sin argumentos).  Guarda tu trabajo.

21.  Abre la clase Jug.  Agrega un Set de objetos Member denominado members.  Genera getters y setters.  Anota el set con 	@OneToMany(cascade=CascadeType.ALL) y @JoinColumn(name="jugId"). Puedes  crear un constructor para crear objetos Jug brindando name, location, y Set de Members.  (Si haces eso, asegurate de tener tambien un constructor sin argumentos).  Guarda tu trabajo.

22.  Regresar a la clase principal de la aplicación y modificar la logica que puebla la lista de jugs y agrega algunos miembors a cada jug.  Aquí un ejemplo de implementación:

  ```
    @PostConstruct
	public void init() {
		List<Jug> list = new ArrayList<>();

		Set<Member> set = new HashSet<>();
		set.add(new Member("Jose Diaz", "JUG Leader"));
		set.add(new Member("Eddu Melendez", "JUG Co-Leader"));
		set.add(new Member("Ytalo BorjaDizzy", "JUG Member"));
		
		list.add(new Jug("Peru", "PERU JUG", set));
		list.add(new Jug("Colombia","Barranquilla JUG",null));

		jugRepository.save(list);
	}   
  ```

23.  Guarda tu trabajo.  Reinicia la aplicación.  Abre [http://localhost:8080/jugs](http://localhost:8080/jugs) para ver sus miembros.


  **Parte 6 - Agregar Spring Data REST**
24.  Abre el POM del proyecto.  Agregar la dependencia  group org.springframework.boot y artifact spring-boot-starter-data-rest. Guarda tu trabajo.

25.  Abre JugRepository.  Agrega una anotación @RestResource(path="jugs", rel="jug") a la interface.

26.  Crea una nueva Interface denominada "MemberRepository".  Haz que herede de CrudRepository<Member,Long>.  (Asegurate de crear esto como una Interface, no una Class)!  Agrega una anotación @RestResource(path="members", rel="member") a la interface.

27.  Abre JugController.  Comenta la anotación @RestController en la clase.  (Usaremos Spring Data Rest, así que hay que evitar que la controladora interfiera).

28.  Guarda toto tu trabajo.  Reinicia la aplicación.  Abre [http://localhost:8080/jugs](http://localhost:8080/jugs) para ver los members.  Observa que (dependiendo del navegador que estes usando) tu puedes navegar con los links que hay para members y jugs.

  Si haz llegado a este punto, Felicitaciones, haz finalizado el ejercicio!!.

  **Parte 7 (Opcional) - Explora los Actuator Endpoints**

29.  Una de las dependencia que especificamos anteriormente fue Actuator.  Este automaticamente agregar algunos endpoints utiles para nuestra aplicacion web.  Abre lo siguiente con un navegador:
  - [/info](http://localhost:8080/info)
  - [/health](http://localhost:8080/health)

30.  Observa que algunos endpoints nos son habilitados por defecto.  Prueba los siguientes - ellos no trabajan, pero, sería un buen ejercicio que averigues, ¿porque? - exponer estos puede significar un riesgo de seguridad:
  - [/beans](http://localhost:8080/beans)
  - [/configprops](http://localhost:8080/configprops)
  - [/autoconfig](http://localhost:8080/autoconfig)

31.  Habilita estos actuator endpoints modificando tu POM: Agrega una dependencia para group org.springframework.boot y artifact spring-boot-starter-security.  Guarda tu trabajo y reinicia.   Observa la salidad de la consola y localiza la frase  "default security password".  Copia este password generador aleatoriamente, luego busca los endpoints listados anteriormente y, usa "user" para username y copia el valor anterior para el  password.  (Observa que este password es regenerado con cada reinicio, establece security.user.name y security.user.password para establecer valores estáticos).
 
32.  Explora [/mappings](http://localhost:8080/mappings).  ¿Te muestra algunos endpoints utiles?

  **Parte 8 (Opcional) - DevTools**
  
33.  Frecuentemente cuando desarrollamos necesitamos ejecutar nuestra aplicación, realizar algunos cambios, y luego reiniciar la aplicación.  La dependencia Spring Boot "DevTools" puede automaticamente reiniciar cuando los cambios son detecatados (¿te suena esto a JRebel?).  Agrega la siguiente dependencia: 

  ```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <optional>true</optional>
    </dependency>  
  ```
  
34.  Mientras tu aplicación se esta ejecutando, realiza un pequeño cambio, cambia algo del código (como comentar o generar espacios).  Observa como DevTools reinicia la aplicación. 


