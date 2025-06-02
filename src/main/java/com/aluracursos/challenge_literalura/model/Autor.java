package com.aluracursos.challenge_literalura.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name="autores")

public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long Id;
    private String fechaDeNacimiento;
    private String fechaDeMuerte;

    //private List<Libro> listaLibros;

    @ManyToOne
    private List<Libro> listaLibros;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public Autor(long id) {
        Id = id;
    }

    public  Autor(){

    }

    @Override
    public String toString() {
        return "Autor{" +
                "Id=" + Id +
                ", fechaDeNacimiento='" + fechaDeNacimiento + '\'' +
                ", fechaDeMuerte='" + fechaDeMuerte + '\'' +
                ", listaLibros=" + listaLibros +
                '}';
    }
}
