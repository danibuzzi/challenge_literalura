package com.aluracursos.challenge_literalura.principal;

import com.aluracursos.challenge_literalura.model.Datos;
import com.aluracursos.challenge_literalura.model.DatosLibros;
import com.aluracursos.challenge_literalura.service.ConsumoAPI;
import com.aluracursos.challenge_literalura.service.ConvierteDatos;

import java.util.Comparator;
import java.util.DoubleSummaryStatistics;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Principal {
    private static final String URL_BASE = "https://gutendex.com/books/";
    private ConsumoAPI consumoAPI = new ConsumoAPI();
    private ConvierteDatos conversor = new ConvierteDatos();
    private Scanner teclado = new Scanner(System.in);

    public void muestraElMenu(){

        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    1 - Buscar libros por título 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores por nombre 
                    5 - Listar autores vivos en un determinado año 
                    6 - Listar autores por idioma 
                    7 - Listar libros por tema 
                    8 - Top 10 libros más descargados   
                    9 - Mostrar estadísticas de descargas de libros       
                    0 - Salir
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
                    }else {
                        System.out.println("Libro no encontrado");
                    }

                    break;
                case 2:
                    //buscarEpisodioPorSerie();
                    break;
                case 3:
                    //mostrarSeriesBuscadas();
                    break;
                case 4:
                    //buscarSeriesPorTitulo();
                    break;
                case 5:
                    //buscarTop5Series();
                    break;
                case 6:
                    //buscarSeriesPorCategoria();
                    break;
                case 7:
                    //buscarSeriesPorTemporadaYEvaluacion();
                    break;
                case 8:
                    //buscarEpisodiosPorTitulo();
                    break;
                case 9:
                    //buscarTop5Episodios();
                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    System.out.close();
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }


        var json = consumoAPI.obtenerDatos(URL_BASE);
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

    }
}
