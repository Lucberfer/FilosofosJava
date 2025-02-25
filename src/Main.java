import java.util.Scanner;
import java.util.concurrent.Semaphore;

public class Main {
    private static Filosofo[] filosofos;
    private static boolean simulacionEnMarcha = false;
    private static Mesa mesa;
    private static Semaphore semaforo;
    private static final int numFilosofos = 5;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;
        mesa = new Mesa(numFilosofos);
        semaforo = new Semaphore(numFilosofos - 1);

        while (running) {
            System.out.println("\nMENÚ FILÓSOFOS COMENSALES");
            System.out.println("1. Iniciar simulación");
            System.out.println("2. Detener simulación");
            System.out.println("3. Salir");
            System.out.print("Selecciona una opción: ");
            int opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    iniciarSimulacion();
                    break;

                case 2:
                    detenerSimulacion();
                    break;

                case 3:
                    System.out.println("\nSaliendo del programa...");
                    detenerSimulacion();
                    running = false;
                    break;

                default:
                    System.out.println("Opción no válida. Intenta de nuevo.");
            }
        }
        scanner.close();
    }

    private static void iniciarSimulacion() {
        if (simulacionEnMarcha) {
            System.out.println("\nLa simulación ya está en marcha.");
            return;
        }

        System.out.println("\nIniciando simulación...");
        filosofos = new Filosofo[numFilosofos];
        for (int i = 0; i < numFilosofos; i++) {
            filosofos[i] = new Filosofo(i, mesa, semaforo);
            filosofos[i].start();
        }
        simulacionEnMarcha = true;
    }

    private static void detenerSimulacion() {
        if (!simulacionEnMarcha) {
            System.out.println("\nLa simulación no está en marcha.");
            return;
        }

        System.out.println("\nDeteniendo simulación...");
        for (Filosofo f : filosofos) {
            if (f != null) {
                f.detener();
            }
        }

        for (Filosofo f : filosofos) {
            if (f != null) {
                try {
                    f.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Todos los filósofos han dejado de comer.");
        simulacionEnMarcha = false;
    }
}
