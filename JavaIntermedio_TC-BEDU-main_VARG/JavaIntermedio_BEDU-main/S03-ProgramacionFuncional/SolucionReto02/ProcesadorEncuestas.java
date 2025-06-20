import java.util.*;

public class ProcesadorEncuestas {

    public static void main(String[] args) {
        // Lista de sucursales con sus encuestas
        List<Sucursal> sucursales = List.of(
                new Sucursal("Centro", List.of(
                        new Encuesta("Patricia", "El tiempo de espera fue excesivo", 2),
                        new Encuesta("Miguel", null, 5)
                )),
                new Sucursal("Norte", List.of(
                        new Encuesta("Lorena", "La atención fue adecuada, pero la limpieza necesita mejoras", 3),
                        new Encuesta("Raúl", null, 4)
                )),
                new Sucursal("Sur", List.of(
                        new Encuesta("Esteban", null, 2),
                        new Encuesta("Diana", "No pude encontrar el medicamento requerido", 1)
                ))
        );

        System.out.println("Seguimiento a pacientes con calificaciones bajas:");

        sucursales.stream()
                .flatMap(sucursal ->
                        sucursal.getEncuestas().stream()
                                .filter(e -> e.getCalificacion() <= 3) // Filtrar encuestas con calificación baja
                                .map(encuesta -> new Seguimiento(encuesta, sucursal.getNombre())) // Combinar encuesta y sucursal
                )
                .filter(seg -> seg.encuesta().getComentario().isPresent()) // Solo con comentario
                .map(seg -> {
                    String comentario = seg.encuesta().getComentario().get();
                    return "Sucursal " + seg.sucursal() +
                            ": Seguimiento necesario por comentario: \"" + comentario + "\"";
                })
                .forEach(System.out::println);
    }

    // Clase auxiliar para agrupar Encuesta y Sucursal
    record Seguimiento(Encuesta encuesta, String sucursal) {
    }
}
