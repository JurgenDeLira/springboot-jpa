package com.jorge.jpa.springboot_jpa;

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

		personalizedQueries();

	}

	@Transactional(readOnly = true)
	public void personalizedQueries(){

		Scanner scanner = new Scanner(System.in);

		System.out.println("=============== consulta solo el nombre por el id ===============");
		System.out.println("Ingrese el id para el nombre: ");
		Long id = scanner.nextLong();
		scanner.close();

		String name = repository.getNameById(id);
		System.out.println(name);

		Long idDb = repository.getIdById(id);
		System.out.println(idDb);

		String fullName = repository.getFullNameById(id);
		System.out.println(fullName);
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
