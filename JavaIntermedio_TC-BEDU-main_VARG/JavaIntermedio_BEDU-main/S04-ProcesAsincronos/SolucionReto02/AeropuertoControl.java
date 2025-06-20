import java.util.concurrent.*;
import java.util.Random;

public class AeropuertoControl {

    private static final Random random = new Random();

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Iniciando simulación de control de aterrizajes múltiples...\n");

        int cantidadAterrizajes = 5; // Número de aterrizajes a simular

        ExecutorService executor = Executors.newFixedThreadPool(cantidadAterrizajes);

        for (int i = 1; i <= cantidadAterrizajes; i++) {
            final int vueloId = i;
            executor.submit(() -> {
                System.out.println("=== Iniciando verificación para vuelo #" + vueloId + " ===");

                CompletableFuture<Boolean> pistaFuture = verificarPista();
                CompletableFuture<Boolean> climaFuture = verificarClima();
                CompletableFuture<Boolean> traficoFuture = verificarTraficoAereo();
                CompletableFuture<Boolean> personalFuture = verificarPersonalTierra();

                CompletableFuture<Void> controlAterrizaje = CompletableFuture.allOf(pistaFuture, climaFuture, traficoFuture, personalFuture)
                    .thenAccept(v -> {
                        try {
                            boolean pista = pistaFuture.get();
                            boolean clima = climaFuture.get();
                            boolean trafico = traficoFuture.get();
                            boolean personal = personalFuture.get();

                            if (pista && clima && trafico && personal) {
                                System.out.println("Vuelo #" + vueloId + ": Aterrizaje autorizado.");
                            } else {
                                System.out.println("Vuelo #" + vueloId + ": Aterrizaje rechazado.");
                            }
                        } catch (InterruptedException | ExecutionException e) {
                            System.out.println("Vuelo #" + vueloId + ": Error durante la evaluación: " + e.getMessage());
                            Thread.currentThread().interrupt();
                        }
                    })
                    .exceptionally(ex -> {
                        System.out.println("Vuelo #" + vueloId + ": Error en el proceso de control: " + ex.getMessage());
                        return null;
                    });

                controlAterrizaje.join();

                System.out.println("=== Finalizado proceso para vuelo #" + vueloId + " ===\n");
            });
        }

        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        System.out.println("Simulación de control de aterrizajes terminada.");
    }

    public static CompletableFuture<Boolean> verificarPista() {
        return CompletableFuture.supplyAsync(() -> {
            dormir(2);
            boolean disponible = random.nextInt(100) < 80;
            return disponible;
        });
    }

    public static CompletableFuture<Boolean> verificarClima() {
        return CompletableFuture.supplyAsync(() -> {
            dormir(3);
            boolean climaBueno = random.nextInt(100) < 85;
            return climaBueno;
        });
    }

    public static CompletableFuture<Boolean> verificarTraficoAereo() {
        return CompletableFuture.supplyAsync(() -> {
            dormir(1);
            boolean despejado = random.nextInt(100) < 90;
            return despejado;
        });
    }

    public static CompletableFuture<Boolean> verificarPersonalTierra() {
        return CompletableFuture.supplyAsync(() -> {
            dormir(2);
            boolean disponible = random.nextInt(100) < 95;
            return disponible;
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
