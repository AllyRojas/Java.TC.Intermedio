/**
 * Clase abstracta que representa un material de curso.
 * Debe ser extendida por tipos específicos como artículos o ejercicios.
 */
public abstract class MaterialCurso {
    private final String titulo;
    private final String autor;

    public MaterialCurso(String titulo, String autor) {
        this.titulo = titulo;
        this.autor = autor;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public abstract void mostrarDetalle();
}
