import java.util.*;
import java.util.stream.*;

public class ConfirmacionPedidos {

    public static void main(String[] args) {
        List<Pedido> pedidos = List.of(
                new Pedido("Panfilo", "domicilio", "555-4321"),
                new Pedido("Isabel", "local", null),
                new Pedido("Ramón", "domicilio", null),
                new Pedido("Claudia", "domicilio", "555-8765")
        );

        // Guardamos los mensajes de confirmación para entregas a domicilio
        List<String> mensajes = pedidos.stream()
                .filter(p -> p.getTipoEntrega().equalsIgnoreCase("domicilio"))
                .map(Pedido::getTelefono) // devuelve Optional<String>
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(tel -> "Confirmación enviada al teléfono: " + tel)
                .toList();

        System.out.println("Confirmaciones de pedidos para entrega a domicilio:");
        mensajes.forEach(System.out::println);
    }
}
