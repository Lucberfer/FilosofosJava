public class Mesa {
    private final boolean[] tenedores;

    public Mesa(int numFilosofos) {
        tenedores = new boolean[numFilosofos];
    }

    public synchronized void cogerTenedorIzquierdo(int id) throws InterruptedException {
        while (tenedores[id]) {
            wait();
        }
        tenedores[id] = true;
        System.out.println("Filosofo " + id + " ha tomado el tenedor izquierdo.");
    }

    public synchronized void cogerTenedorDerecho(int id) throws InterruptedException {
        while (tenedores[(id + 1) % tenedores.length]) {
            wait();
        }
        tenedores[(id + 1) % tenedores.length] = true;
        System.out.println("Filosofo " + id + " ha tomado el tenedor derecho.");
    }

    public synchronized void soltarTenedores(int id) {
        tenedores[id] = false;
        tenedores[(id + 1) % tenedores.length] = false;
        System.out.println("Filosofo " + id + " ha soltado los tenedores.");
        notifyAll();
    }
}
