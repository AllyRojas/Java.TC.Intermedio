package misionespacial;

import java.util.concurrent.Callable;

public class SistemaSoporteVital implements Callable<String> {
    @Override
    public String call() throws Exception {
        Thread.sleep(1200);
        return "Soporte vital: niveles de presión y oxígeno estables y seguros.";
    }
}
