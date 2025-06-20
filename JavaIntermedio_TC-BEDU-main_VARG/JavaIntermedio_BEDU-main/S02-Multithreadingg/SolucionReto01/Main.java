import misionespacial.SistemaComunicaciones;
import misionespacial.SistemaControlTermico;
import misionespacial.SistemaNavegacion;
import misionespacial.SistemaSoporteVital;

import java.util.concurrent.*;

public class Main {
    public static void main(String[] args) throws Exception {
        System.out.println("Iniciando simulación de misión espacial: Proyecto Panfilo");

        ExecutorService executor = Executors.newFixedThreadPool(4);

        Future<String> nav = executor.submit(new SistemaNavegacion());
        Future<String> vital = executor.submit(new SistemaSoporteVital());
        Future<String> com = executor.submit(new SistemaComunicaciones());
        Future<String> termico = executor.submit(new SistemaControlTermico());

        System.out.println(nav.get());
        System.out.println(vital.get());
        System.out.println(com.get());
        System.out.println(termico.get());

        executor.shutdown();
        System.out.println("Todos los sistemas están funcionando dentro de los parámetros esperados.");
    }
}
