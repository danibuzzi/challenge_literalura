package com.aluracursos.challenge_literalura.model;
import jakarta.persistence.*;

import java.util.List;
import java.util.Optional;

@Entity
@Table(name="libros")

public class Libro{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long Id;

    @Column(unique = true)
    private String titulo;

    @Enumerated(EnumType.STRING)
    private Idioma idioma;
    private  Double numeroDescargas;



    /*@Enumerated(EnumType.STRING)
    private Categoria genero;*/

    //@Transient //no tomo aun la relacion con lista de episodios
    //RElaciono esta entidad con la entidad episodios mapeando por el campo serie en
    //la entidad episodios, si hay un cambio en esta entidad (Serie) debe ser reflejado tambien en Episodios
    //pido los datos de manera ansiosa (trae todos los datos al mismo tiempo)

    @ManyToOne
    @JoinColumn(name = "autor_id")

    private Autor autor;

    //Ojo JAp siempre exige que cada clase tenga un constructor predeterminado

    public Libro() {

    }

    public Libro(DatosLibros datos) {
        this.titulo = datos.titulo();
        Optional<DatosAutor> autor = datos.autor().stream().findFirst();
        Optional<String> idioma = datos.idiomas().stream().findFirst();
        idioma.ifPresent(s -> this.idioma = Idioma.stringToEnum(s));
        this.numeroDescargas=datos.numeroDeDescargas();
    }

    public Libro(Optional<DatosLibros> datos) {
        this.titulo = datos.get().titulo();
        Optional<DatosAutor> autor = datos.get().autor().stream().findFirst();
        Optional<String> idioma = datos.get().idiomas().stream().findFirst();
        idioma.ifPresent(s -> this.idioma = Idioma.stringToEnum(s));
        this.numeroDescargas=datos.get().numeroDeDescargas();

    }


    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Autor getAutor() {
        return autor;
    }

    public Idioma getIdioma() {
        return idioma;
    }

    public void setIdioma(Idioma idioma) {
        this.idioma = idioma;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public Double getNumeroDescargas() {
        return numeroDescargas;
    }

    public void setNumeroDescargas(Double numeroDescargas) {
        this.numeroDescargas = numeroDescargas;
    }

    public Idioma getIdiomas() {
        return idioma;
    }

    public void setIdiomas(List<String> idiomas) {
        this.idioma = idioma;
    }


    @Override
    public String toString() {
        return  " *****Libro*****" + '\'' +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", numeroDescargas=" + numeroDescargas +
                ", autor=" + autor.getNombre() ;
    }
}
