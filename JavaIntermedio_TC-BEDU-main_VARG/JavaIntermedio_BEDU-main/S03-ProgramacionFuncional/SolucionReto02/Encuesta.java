import java.util.Optional;

public class Encuesta {
    private final String paciente;
    private final String comentario; // Puede ser null
    private final int calificacion; // Rango: 1 a 5

    public Encuesta(String paciente, String comentario, int calificacion) {
        this.paciente = paciente;
        this.comentario = comentario;
        this.calificacion = calificacion;
    }

    public int getCalificacion() {
        return calificacion;
    }

    /**
     * Devuelve el comentario del paciente envuelto en un Optional para manejo seguro de posibles valores nulos.
     */
    public Optional<String> getComentario() {
        return Optional.ofNullable(comentario);
    }
}
