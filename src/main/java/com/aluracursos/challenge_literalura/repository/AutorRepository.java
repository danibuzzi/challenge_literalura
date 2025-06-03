package com.aluracursos.challenge_literalura.repository;


import com.aluracursos.challenge_literalura.model.Autor;
import com.aluracursos.challenge_literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface AutorRepository extends JpaRepository<Autor, Long> {

        @Query("SELECT a FROM Autor a WHERE LOWER(a.nombre) LIKE LOWER(:nombre)")
        Optional<Autor> listarAutorPorNombre(String nombre);

        //Acordarme de poner fecha nacimiento como entero
        @Query("SELECT a FROM Autor a WHERE :anio>=a.fechaDeNacimiento AND :anio<a.fechaDeMuerte")
        List<Autor> listarAutoresVivosEnAnio(int anio);

    //Autores vivos en determinado año
    //List<Autor> findByFechaDeMuerteLessThanEqualAndFechaDeNacimientoGreaterThanEqual(int anio);
    /*@Query("SELECT a FROM Autor a ORDER BY a.nombre DESC")
    Optional<Autor> listarAutoresRegistrados();*/

    }

