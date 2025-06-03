package com.aluracursos.challenge_literalura;

import com.aluracursos.challenge_literalura.principal.Principal;
import com.aluracursos.challenge_literalura.repository.AutorRepository;
import com.aluracursos.challenge_literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DesafioApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DesafioApplication.class, args);
	}

	@Autowired
	private LibroRepository libroRepository;
	@Autowired
	private AutorRepository autorRepository;
	@Override
	public void run(String... args) throws Exception {

		Principal principal = new Principal(libroRepository,autorRepository);
		principal.muestraElMenu();

	}
}
