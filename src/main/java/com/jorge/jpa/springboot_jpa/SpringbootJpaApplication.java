package com.jorge.jpa.springboot_jpa;

import com.jorge.jpa.springboot_jpa.dto.PersonDto;
import com.jorge.jpa.springboot_jpa.entities.Person;
import com.jorge.jpa.springboot_jpa.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		personalizedQueriesDistinct();

	}

	@Transactional(readOnly = true)
	public void personalizedQueriesDistinct(){
		System.out.println("=============== consultas con nombres de personas ===============");
		List<String> names = repository.findAllNames();
		names.forEach(System.out::println);

		System.out.println("=============== consultas con nombres únicos de personas ===============");
		names = repository.findAllNamesDistinct();
		names.forEach(System.out::println);

		System.out.println("=============== consulta de lenguaje de programación únicas ===============");
		List<String> languages = repository.findAllProgrammingLanguageDistinct();
		languages.forEach(System.out::println);

		System.out.println("=============== consulta con total de lenguajes de programación únicas ===============");
		Long totalLanguage = repository.findAllProgrammingLanguageDistinctCount();
		System.out.println("Total de lenguajes de programación: " + totalLanguage);


	}

	@Transactional(readOnly = true)
	public void personalizedQueries2() {

		System.out.println("=============== consulta solo por objeto persona y lenguaje de programación ===============");
		List<Object[]> personRegs = repository.findAllMixPerson();

		personRegs.forEach(reg -> {
			System.out.println("programmingLanguage=" + reg[1] + ". person= " + reg[0]);
		});

		System.out.println("consulta que puebla y devuelve objeto entity de una instancia personalizada");
		List<Person> persons = repository.findAllObjectPersonPersonalized();
		persons.forEach(System.out::println);

		System.out.println("consulta que puebla y devuelve objeto dto de una clase personalizada");
		List<PersonDto> personDto = repository.findAllPersonDto();
		personDto.forEach(System.out::println);
	}

	@Transactional(readOnly = true)
	public void personalizedQueries(){

		Scanner scanner = new Scanner(System.in);

		System.out.println("=============== consulta solo el nombre por el id ===============");
		System.out.println("Ingrese el id: ");
		Long id = scanner.nextLong();
		scanner.close();

		System.out.println("===== mostrando solo el nombre =====");
		String name = repository.getNameById(id);
		System.out.println(name);

		System.out.println("===== mostrando solo el id =====");
		Long idDb = repository.getIdById(id);
		System.out.println(idDb);

		System.out.println("===== mostrando nombre completo con concat =====");
		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);

		System.out.println("===== Consulta por campos personalizados por el id =====");
		Optional<Object> optionalReg = repository.obtenerPersonDataById(id);
		if (optionalReg.isPresent()){
			Object[] personReg= (Object[]) optionalReg.orElseThrow();
			System.out.println("id=" + personReg[0] + ", nombre=" + ", apellido=" +  personReg[2] + ", lenguaje=" +personReg[3]);
		}

		System.out.println("==== consulta por campos personalizados lista ====");
		List<Object[]> regs = repository.obtenerPersonDataList();
		regs.forEach(reg -> System.out.println("id=" + reg[0] + ", nombre=" + ", apellido=" +  reg[2] + ", lenguaje=" + reg[3]));
	}

	@Transactional
	public void delete2(){
		repository.findAll().forEach(System.out::println);

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id a eliminar: ");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		optionalPerson.ifPresentOrElse(
				repository::delete,
				() -> System.out.println("Lo sentimos no exista le persona con ese id!"));

		repository.findAll().forEach(System.out::println);

		scanner.close();
	}

	@Transactional
	public void update(){

		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el id de la persona: ");
		Long id = scanner.nextLong();

		Optional<Person> optionalPerson = repository.findById(id);

		//optionalPerson.ifPresent(person -> {
		if (optionalPerson.isPresent()) {
			Person personDb = optionalPerson.orElseThrow();

			System.out.println(personDb);
			System.out.println("Ingrese el lenguaje de programación: ");
			String programmingLanguage = scanner.next();
			personDb.setProgrammingLanguage(programmingLanguage);
			Person personUpdated = repository.save(personDb);
			System.out.println(personUpdated);
		} else {
			System.out.println("El usuario no esta presente! no existe!");
		}

		//});

		scanner.close();
	}

	@Transactional
	public void create(){
		Scanner scanner = new Scanner(System.in);
		System.out.println("Ingrese el nombre: ");
		String name = scanner.next();
		System.out.println("Ingrese el apellido: ");
		String lastname = scanner.next();
		System.out.println("Ingrese el lenguaje de programación: ");
		String programmingLanguage = scanner.next();
		scanner.close();

		Person person = new Person(null, name, lastname, programmingLanguage);

		Person personNew = repository.save(person);
		System.out.println(personNew);

		repository.findById(personNew.getId()).ifPresent(p -> System.out.println(p)); // o (System.out::println)

	}

	@Transactional(readOnly = true)
	public void findOne() {
//		Person person = null;
//		Optional<Person> optionalPerson = repository.findById(1L);
//		//if(!optionalPerson.isEmpty()) {
//		if (optionalPerson.isPresent()) {
//			person = optionalPerson.get();
//		}
//		System.out.println(person);
//		repository.findById(1L).ifPresent(person -> System.out.println(person));
		repository.findByNameContaining("se").ifPresent(System.out::println);

	}

	@Transactional(readOnly = true)
	public void list() {

		//List<Person> persons = (List<Person>) repository.findAll();
		//List<Person> persons = (List<Person>) repository.buscarByProgrammingLanguage("Java", "Andres");
		List<Person> persons = (List<Person>) repository.findByProgrammingLanguageAndName("Java", "Andres");

		persons.stream().forEach(person -> {
			System.out.println(person);
		});

		List<Object[]> personsValues = repository.obtenerPersonDataByProgrammingLanguage("Java");
		personsValues.stream().forEach(person -> {
			System.out.println(person[0] + " es experto en " + person[1]);
		});
	}
}
