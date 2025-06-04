package com.aluracursos.challenge_literalura.repository;

import com.aluracursos.challenge_literalura.model.Idioma;
import com.aluracursos.challenge_literalura.model.Libro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface LibroRepository extends JpaRepository<Libro,Long> {

    @Query("SELECT l FROM Libro l WHERE LOWER(l.titulo) LIKE LOWER(:nombre)")
    Optional<Libro> obtenerLibroPorNombre(String nombre);


    @Query("SELECT l FROM Libro l ORDER BY l.titulo DESC")
    Optional<Libro> listarLibrosRegistrados();

    List<Libro> findTop10ByOrderByNumeroDescargasDesc();

    @Query("SELECT l FROM Libro l WHERE l.idioma=:idioma")
    List<Libro> listarLibrosPorIdioma(Idioma idioma);

    @Query("SELECT l.titulo, l.numeroDescargas FROM Libro l")
    List<Libro> listaLibroParaEstadistica();


}
