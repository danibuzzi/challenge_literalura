package com.aluracursos.challenge_literalura.model;

import com.aluracursos.challenge_literalura.model.DatosAutor;
import com.aluracursos.challenge_literalura.model.Libro;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="autores")

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long Id;
    private String nombre;
    private String fechaDeNacimiento;
    private String fechaDeMuerte;

    //private List<Libro> listaLibros;


    @OneToMany(mappedBy = "autor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Libro> libros;

    public long getId() {
        return Id;
    }

    public List<Libro> getLibros() {
        return libros;
    }

    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFechaDeNacimiento() {
        return fechaDeNacimiento;
    }

    public void setFechaDeNacimiento(String fechaDeNacimiento) {
        this.fechaDeNacimiento = fechaDeNacimiento;
    }

    public String getFechaDeMuerte() {
        return fechaDeMuerte;
    }

    public void setFechaDeMuerte(String fechaDeMuerte) {
        this.fechaDeMuerte = fechaDeMuerte;
    }

    public  Autor(){



    }

    public Autor(DatosAutor datosAutor) {
        this.nombre= datosAutor.nombre();
        this.fechaDeNacimiento= datosAutor.fechaDeNacimiento();
        this.fechaDeMuerte= datosAutor.fechaDeMuerte();
    }

    @Override
    public String toString() {
        return "Autor{" +
                ", nombre='" + nombre.toLowerCase() + '\'' +
                ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                ", fechaDeMuerte='" + fechaDeMuerte + '\'' +
                '}';
    }
}
