import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.ThreadLocalRandom;

public class MovilidadApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Bienvenido a la simulación de viaje.");

        System.out.print("Ingrese el punto de partida: ");
        String origen = scanner.nextLine().trim();
        if (origen.isEmpty()) {
            System.out.println("El punto de partida no puede estar vacío. Saliendo...");
            return;
        }

        System.out.print("Ingrese el destino: ");
        String destino = scanner.nextLine().trim();
        if (destino.isEmpty()) {
            System.out.println("El destino no puede estar vacío. Saliendo...");
            return;
        }

        System.out.println("\nIniciando simulación de viaje desde " + origen + " hasta " + destino + "...\n");

        CompletableFuture<String> rutaFuture = calcularRuta(origen, destino);
        CompletableFuture<Double> tarifaFuture = estimarTarifa();

        CompletableFuture<Void> viajeCompleto = rutaFuture.thenCombine(tarifaFuture,
                        (ruta, tarifa) -> String.format("Viaje planificado: %s | Tarifa estimada: $%.2f", ruta, tarifa))
                .thenAccept(System.out::println)
                .exceptionally(ex -> {
                    System.out.println("Error al procesar el viaje: " + ex.getMessage());
                    return null;
                });

        viajeCompleto.join();

        scanner.close();
    }

    public static CompletableFuture<String> calcularRuta(String origen, String destino) {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Calculando ruta...");
            dormir(3); // Simula demora de 3 segundos
            return origen + " -> " + destino;
        });
    }

    public static CompletableFuture<Double> estimarTarifa() {
        return CompletableFuture.supplyAsync(() -> {
            System.out.println("Calculando tarifa estimada...");
            dormir(2); // Simula demora de 2 segundos
            // Genera tarifa aleatoria entre $50 y $100
            return ThreadLocalRandom.current().nextDouble(50, 100);
        });
    }

    public static void dormir(int segundos) {
        try {
            TimeUnit.SECONDS.sleep(segundos);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
