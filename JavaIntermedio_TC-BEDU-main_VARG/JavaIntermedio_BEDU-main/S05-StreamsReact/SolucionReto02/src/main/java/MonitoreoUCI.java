import reactor.core.publisher.Flux;
import java.time.Duration;
import java.util.Random;

public class MonitoreoUCI {

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        // Simular flujo de signos vitales por paciente
        Flux<EventoVital> paciente1 = generarSignosVitales(1, random);
        Flux<EventoVital> paciente2 = generarSignosVitales(2, random);
        Flux<EventoVital> paciente3 = generarSignosVitales(3, random);

        // Fusionar los flujos de los pacientes y procesar eventos críticos
        Flux.merge(paciente1, paciente2, paciente3)
                .filter(EventoVital::esCritico)                         // Filtrar eventos críticos
                .sort((e1, e2) -> Integer.compare(e1.prioridad(), e2.prioridad())) // Priorizar FC sobre otros
                .delayElements(Duration.ofSeconds(1))                   // Simular procesamiento lento (backpressure)
                .subscribe(evento -> System.out.println(evento));

        // Mantener la aplicación activa para observar la emisión
        Thread.sleep(15000);
    }

    // Genera flujo de signos vitales para un paciente
    private static Flux<EventoVital> generarSignosVitales(int pacienteId, Random random) {
        return Flux.interval(Duration.ofMillis(300))
                .map(tick -> {
                    int fc = 40 + random.nextInt(101);   // FC: 40-140 bpm
                    int pas = 80 + random.nextInt(81);   // PAS: 80-160 mmHg
                    int pad = 50 + random.nextInt(51);   // PAD: 50-100 mmHg
                    int spo2 = 85 + random.nextInt(16);  // SpO2: 85-100%

                    return new EventoVital(pacienteId, fc, pas, pad, spo2);
                })
                .take(10); // Limitar a 10 eventos por paciente
    }

    // Clase para representar un evento de signos vitales
    static class EventoVital {
        private final int pacienteId;
        private final int fc;   // Frecuencia cardiaca (bpm)
        private final int pas;  // Presión arterial sistólica (mmHg)
        private final int pad;  // Presión arterial diastólica (mmHg)
        private final int spo2; // Saturación de oxígeno (%)

        public EventoVital(int pacienteId, int fc, int pas, int pad, int spo2) {
            this.pacienteId = pacienteId;
            this.fc = fc;
            this.pas = pas;
            this.pad = pad;
            this.spo2 = spo2;
        }

        // Define si el evento es crítico según rangos médicos
        public boolean esCritico() {
            return fc < 50 || fc > 120 || spo2 < 90 || pas < 90 || pas > 140 || pad < 60 || pad > 90;
        }

        // Prioridad para ordenamiento (1=FC, 2=SpO2, 3=PA)
        public int prioridad() {
            if (fc < 50 || fc > 120) return 1;
            if (spo2 < 90) return 2;
            return 3;
        }

        @Override
        public String toString() {
            StringBuilder mensaje = new StringBuilder();
            if (fc < 50 || fc > 120) {
                mensaje.append("Paciente ").append(pacienteId).append(" - Frecuencia cardíaca crítica: ").append(fc).append(" bpm\n");
            }
            if (spo2 < 90) {
                mensaje.append("Paciente ").append(pacienteId).append(" - Saturación de oxígeno baja: ").append(spo2).append("%\n");
            }
            if (pas < 90 || pas > 140 || pad < 60 || pad > 90) {
                mensaje.append("Paciente ").append(pacienteId).append(" - Presión arterial crítica: ").append(pas).append("/").append(pad).append(" mmHg\n");
            }
            return mensaje.toString().trim();
        }
    }
}
