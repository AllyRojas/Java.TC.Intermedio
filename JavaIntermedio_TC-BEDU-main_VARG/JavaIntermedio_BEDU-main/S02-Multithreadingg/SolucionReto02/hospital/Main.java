package hospital;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        System.out.println("Iniciando simulación de acceso al recurso hospitalario...");

        RecursoMedico salaCirugia = new RecursoMedico("Sala de cirugía");

        // Creamos un grupo de 4 hilos para simular el acceso concurrente
        ExecutorService executor = Executors.newFixedThreadPool(4);

        executor.submit(new Profesional("Dra. Martínez", salaCirugia));
        executor.submit(new Profesional("Dr. Pérez", salaCirugia));
        executor.submit(new Profesional("Enfermero Hernández", salaCirugia));
        executor.submit(new Profesional("Dra. Torres", salaCirugia));

        executor.shutdown(); // No se aceptan nuevas tareas
    }
}
