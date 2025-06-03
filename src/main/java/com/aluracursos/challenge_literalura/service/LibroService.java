package com.aluracursos.challenge_literalura.service;

import com.aluracursos.challenge_literalura.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LibroService {

    private LibroRepository libroRepository;

    @Autowired
    public LibroService(LibroRepository repository) {
        this.libroRepository = repository;
    }

    public LibroService() {
    }


}