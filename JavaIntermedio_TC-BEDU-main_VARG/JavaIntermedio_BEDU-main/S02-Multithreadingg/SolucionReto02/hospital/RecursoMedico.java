package hospital;

import java.util.concurrent.locks.ReentrantLock;

public class RecursoMedico {

    private final String nombre; // Ejemplo: "Sala de cirugía"
    private final ReentrantLock lock = new ReentrantLock(); // Control de acceso exclusivo

    public RecursoMedico(String nombre) {
        this.nombre = nombre;
    }

    // Simula el uso exclusivo del recurso por parte de un profesional
    public void usar(String profesional) {
        lock.lock(); // Solicita acceso exclusivo
        try {
            System.out.println(profesional + " ha ingresado a " + nombre);
            Thread.sleep(1000); // Si
