package com.aluracursos.challenge_literalura.principal;

import com.aluracursos.challenge_literalura.model.*;
import com.aluracursos.challenge_literalura.repository.AutorRepository;
import com.aluracursos.challenge_literalura.repository.LibroRepository;
import com.aluracursos.challenge_literalura.service.AutorService;
import com.aluracursos.challenge_literalura.service.ConsumoAPI;
import com.aluracursos.challenge_literalura.service.ConvierteDatos;
import com.aluracursos.challenge_literalura.service.LibroService;

import java.sql.SQLOutput;
import java.util.*;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    private List<Libro> libros;
    private List<Autor> autores;

    private LibroRepository repositorioLibro;
    private AutorRepository repositorioAutor;


    public Principal(LibroRepository repositoryLibro,AutorRepository repositoryAutor) {
        this.repositorioLibro=repositoryLibro;
        this.repositorioAutor=repositoryAutor;

    }

    private LibroService libroService;
    private AutorService autorService;




    public void muestraElMenu(){

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por título 
                    2 - Listar libros registrados 
                    3 - Listar autores registrados
                    4 - Listar autores por nombre 
                    5 - Listar autores vivos en un determinado año 
                    6 - Listar libros por idioma 
                    7 - Listar libros por tema 
                    8 - Top 10 libros más descargados   
                    9 - Mostrar estadísticas de descargas de libros       
                    0 - Salir
                    Elige una opción: 
                    """;
            System.out.println(menu);
            opcion = teclado.nextInt();
            teclado.nextLine();

           switch (opcion) {
                case 1:
                    //buscarSerieWeb();
                    System.out.println("Ingrese el nombre del libro que desea buscar");
                    var tituloLibro = teclado.nextLine();
                    String json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + tituloLibro.replace(" ","+"));
                    var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
                    Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                            .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                            .findFirst();
                    if(libroBuscado.isPresent()){
                        System.out.println("Libro Encontrado ");
                        System.out.println(libroBuscado.get());
                        System.out.println("Guardando libro y autor");

                        Libro libro=new Libro(libroBuscado);

                        Autor autor=new Autor(libroBuscado.get().autor().get(0).nombre(),
                                libroBuscado.get().autor().get(0).fechaDeNacimiento(),
                                libroBuscado.get().autor().get(0).fechaDeMuerte());
                        libro.setAutor(autor);
                        Optional<Libro> libroRegistrado= repositorioLibro.obtenerLibroPorNombre(libroBuscado.get().titulo());
                        System.out.println("Consulta libro");
                        Optional<Autor> autorRegistrado=repositorioAutor.listarAutorPorNombre(libroBuscado.get().autor().get(0).nombre());
                        System.out.println("Consulta autor");
                        if (autorRegistrado.isPresent()){
                            System.out.println("El autor ya está registrado por tanto no se guardará nuevamente ");
                        }else{
                            repositorioAutor.save(autor);
                        }
                        if (libroRegistrado.isPresent()){
                            System.out.println("El libro ya está registrado por tanto no se guardará nuevamente ");
                        }else {
                            repositorioLibro.save(libro);
                        }

                    }else {
                        System.out.println("Libro no encontrado");
                    }

                    break;
                case 2:
                    listarLibrosRegistrados();
                    break;
                case 3:
                    listarAutoresRegistrados();
                    break;
                case 4:
                    listarAutoresPorNombre();
                    break;
                case 5:
                    listarAutoresVivoEnAnio();
                    break;
                case 6:
                    //buscarSeriesPorCategoria();
                    break;
                case 7:
                    //buscarSeriesPorTemporadaYEvaluacion();
                    break;
                case 8:
                    listarTop10LibrosDescargas();
                    break;
                case 9:
                    estadisticasDescargasLibros();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    System.out.close();
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }


       /* var json = consumoAPI.obtenerDatos(URL_BASE);
        System.out.println(json);
        var datos = conversor.obtenerDatos(json,Datos.class);
        System.out.println(datos);

        //Top 10 libros más descargados
        System.out.println("Top 10 libros más descargados");
        datos.resultados().stream()
                .sorted(Comparator.comparing(DatosLibros::numeroDeDescargas).reversed())
                .limit(10)
                .map(l -> l.titulo().toUpperCase())
                .forEach(System.out::println);

        //Busqueda de libros por nombre
        System.out.println("Ingrese el nombre del libro que desea buscar");
        var tituloLibro = teclado.nextLine();
        json = consumoAPI.obtenerDatos(URL_BASE+"?search=" + tituloLibro.replace(" ","+"));
        var datosBusqueda = conversor.obtenerDatos(json, Datos.class);
        Optional<DatosLibros> libroBuscado = datosBusqueda.resultados().stream()
                .filter(l -> l.titulo().toUpperCase().contains(tituloLibro.toUpperCase()))
                .findFirst();
        if(libroBuscado.isPresent()){
            System.out.println("Libro Encontrado ");
            System.out.println(libroBuscado.get());
        }else {
            System.out.println("Libro no encontrado");
        }

        //Trabajando con estadisticas
        DoubleSummaryStatistics est = datos.resultados().stream()
                .filter(d -> d.numeroDeDescargas() >0 )
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println("Cantidad media de descargas: " + est.getAverage());
        System.out.println("Cantidad máxima de descargas: "+ est.getMax());
        System.out.println("Cantidad mínima de descargas: " + est.getMin());
        System.out.println(" Cantidad de registros evaluados para calcular las estadisticas: " + est.getCount());
*/
    }
    private void listarLibrosRegistrados(){
        repositorioLibro.findAll().stream()

                .forEach(System.out::println);

    }

    private void listarAutoresRegistrados(){
        repositorioAutor.findAll().stream()
                        .forEach(System.out::println);
        //System.out.println("-------Autor------- ");
        //System.out.println("Nombre "+autores.get().getNombre());

       /* repositorioAutor.listarAutoresRegistrados().stream()
                .forEach(System.out::println);*/
    }

    private void listarTop10LibrosDescargas() {

        List<Libro> topLibros =repositorioLibro.findTop10ByOrderByNumeroDescargasDesc();
        topLibros.forEach(l-> System.out.println("Libro: "+l.getTitulo()+
                " -  Cantidad de descargas:  "+l.getNumeroDescargas()));
    }

    private void listarAutoresVivoEnAnio(){
        System.out.println("Ingrese el año del cual desea saber que autores estaban vivos ");
        int anio =teclado.nextInt();
        repositorioAutor.listarAutoresVivosEnAnio(anio).stream()
                .forEach(System.out::println);

    }

    private void listarAutoresPorNombre(){
        System.out.println("Ingrese el nombre del autor que desea buscar ");
        String nombre=teclado.nextLine();
        repositorioAutor.listarAutorPorNombre(nombre).stream()
                .forEach(System.out::println);
    }

    private  void estadisticasDescargasLibros(){
        var json = consumoAPI.obtenerDatos(URL_BASE);
        var datos = conversor.obtenerDatos(json,Datos.class);
        DoubleSummaryStatistics est= datos.resultados().stream()

                .filter(d -> d.numeroDeDescargas() >0 )
                .collect(Collectors.summarizingDouble(DatosLibros::numeroDeDescargas));
        System.out.println("---Estadisticas de descargar de libros en datos de la API----");
        System.out.println("Cantidad media de descargas: " + est.getAverage());
        System.out.println("Cantidad máxima de descargas: "+ est.getMax());
        System.out.println("Cantidad mínima de descargas: " + est.getMin());
        System.out.println(" Cantidad de registros evaluados para calcular las estadisticas: " + est.getCount());

    }

}


