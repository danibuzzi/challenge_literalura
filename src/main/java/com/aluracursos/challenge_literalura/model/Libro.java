package com.aluracursos.challenge_literalura.model;
import jakarta.persistence.*;

@Entity
@Table(name="libros")

public class Libro{


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private long Id;

    @Column(unique = true)
    private String titulo;
    private String idioma;
    private  Integer numeroDescargas;



    /*@Enumerated(EnumType.STRING)
    private Categoria genero;*/

    //@Transient //no tomo aun la relacion con lista de episodios
    //RElaciono esta entidad con la entidad episodios mapeando por el campo serie en
    //la entidad episodios, si hay un cambio en esta entidad (Serie) debe ser reflejado tambien en Episodios
    //pido los datos de manera ansiosa (trae todos los datos al mismo tiempo)

    @OneToOne(mappedBy = "libro",cascade = CascadeType.ALL,fetch=FetchType.EAGER)
    //private List<Episodio> episodios;
    private Autor autor;
    //Ojo JAp siempre exige que cada clase tenga un constructor predeterminado

    public Libro() {

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


    @Override
    public String toString() {
        return "Libro{" +
                "Id=" + Id +
                ", titulo='" + titulo + '\'' +
                ", idioma='" + idioma + '\'' +
                ", numeroDescargas=" + numeroDescargas +
                ", autor=" + autor +
                '}';
    }
}
