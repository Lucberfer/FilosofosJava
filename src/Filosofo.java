import java.util.Random;
import java.util.concurrent.Semaphore;

public class Filosofo extends Thread {
    private int id;
    private Mesa mesa;
    private Semaphore semaforo;
    private Random random = new Random();
    private volatile boolean running = true;

    public Filosofo(int id, Mesa mesa, Semaphore semaforo) {
        this.id = id;
        this.mesa = mesa;
        this.semaforo = semaforo;
    }

    private void pensar() throws InterruptedException {
        System.out.println("Filosofo " + id + " está pensando...");
        Thread.sleep(random.nextInt(2000) + 1000);
    }

    private void comer() throws InterruptedException {
        System.out.println("Filosofo " + id + " está comiendo...");
        Thread.sleep(random.nextInt(2000) + 1000);
    }

    public void detener() {
        running = false;
        this.interrupt();
    }

    public void run() {
        while (running) {
            try {
                pensar();
                semaforo.acquire();
                mesa.cogerTenedorIzquierdo(id);
                mesa.cogerTenedorDerecho(id);
                comer();
                mesa.soltarTenedores(id);
                semaforo.release();
            } catch (InterruptedException e) {
                break;
            }
        }
        System.out.println("Filosofo " + id + " ha dejado de comer.");
    }
}
