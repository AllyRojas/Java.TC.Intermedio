import java.util.*;
import java.util.function.Predicate;

public class PlataformaEducativa {

    public static void mostrarMateriales(List<? extends MaterialCurso> lista) {
        System.out.println("\nMateriales del curso:");
        for (MaterialCurso material : lista) {
            material.mostrarDetalle();
        }
    }

    public static void contarDuracionVideos(List<? extends Video> lista) {
        int total = lista.stream()
                        .mapToInt(Video::getDuracion)
                        .sum();
        System.out.printf("%nDuración total de videos: %d minutos%n", total);
    }

    public static void marcarEjerciciosRevisados(List<? super Ejercicio> lista) {
        System.out.println();
        for (Object obj : lista) {
            if (obj instanceof Ejercicio e) {
                e.setRevisado(true);
                System.out.printf("Ejercicio '%s' marcado como revisado.%n", e.getTitulo());
            }
        }
    }

    public static void filtrarPorAutor(List<? extends MaterialCurso> lista, Predicate<MaterialCurso> filtro) {
        System.out.println("\nMateriales filtrados por autor:");
        for (MaterialCurso material : lista) {
            if (filtro.test(material)) {
                material.mostrarDetalle();
            }
        }
    }

    public static void main(String[] args) {
        List<MaterialCurso> materiales = Arrays.asList(
                new Video("Introducción a Java", "Mario", 15),
                new Video("POO en Java", "Carlos", 20),
                new Articulo("Historia de Java", "Ana", 1200),
                new Articulo("Tipos de datos", "Luis", 800),
                new Ejercicio("Variables y tipos", "Luis"),
                new Ejercicio("Condicionales", "Mario")
        );

        List<Video> videos = new ArrayList<>();
        List<Ejercicio> ejercicios = new ArrayList<>();

        for (MaterialCurso m : materiales) {
            if (m instanceof Video v) {
                videos.add(v);
            } else if (m instanceof Ejercicio e) {
                ejercicios.add(e);
            }
        }

        mostrarMateriales(materiales);
        contarDuracionVideos(videos);
        marcarEjerciciosRevisados(ejercicios);
        filtrarPorAutor(materiales, m -> m.getAutor().equals("Mario"));
    }
}
