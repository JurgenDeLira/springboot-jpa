package com.jorge.jpa.springboot_jpa;

import com.jorge.jpa.springboot_jpa.entities.Person;
import com.jorge.jpa.springboot_jpa.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;

@SpringBootApplication
public class SpringbootJpaApplication implements CommandLineRunner {

	@Autowired
	private PersonRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(SpringbootJpaApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		create();

	}

	public void create(){
		Person person = new Person(null, "Lalo", "Thor", "Python");

		Person personNew = repository.save(person);
		System.out.println(personNew);
	}

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
