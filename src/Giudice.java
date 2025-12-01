import java.util.ArrayList;
import java.util.List;

class Giudice {
    private final List<Atleta> concorrenti = new ArrayList<>();
    private final List<Atleta> podio = new ArrayList<>();
    private final Gestore gestifile = new Gestore();
    private final Object lockPodio = new Object();
    private final double lunghezzaTracciato = 100.0;

    public double getLunghezzaTracciato() {
        return lunghezzaTracciato;
    }

    public synchronized void registraAtleta(Atleta c) {
        concorrenti.add(c);
    }

    public void Arrivo(Atleta c) {
        synchronized (lockPodio) {
            if (!podio.contains(c)) {
                podio.add(c);
                System.out.printf("--------------------------------\n");
                System.out.printf("%s ha tagliato il traguardo\n",
                         c.getNomeAtleta());
            }
            if (podio.size() == concorrenti.size()) {
                lockPodio.notifyAll();
            }
        }
    }


    private void attendi() {
        synchronized (lockPodio) {
            while (podio.size() < concorrenti.size()) {
                try {
                    lockPodio.wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }


    public void startRun() {
        List<String> nomi = gestifile.leggiAtleti("atleti.txt");


        if (nomi.isEmpty()) {
            System.out.println("Nessun atleta registrato. Interrompo la gara.");
            return;
        }
        for (String nome : nomi) {
            new Atleta(nome, this);
        }


        System.out.println("Pronti...VIA!");

        List<Thread> threads = new ArrayList<>();
        synchronized (this) {
            for (Atleta c : concorrenti) {
                Thread t = new Thread(c);
                threads.add(t);
                t.start();
            }
        }

        attendi();

        sClassifica();
        gestifile.scriviPodio(podio, "podio.txt");
    }

    private void sClassifica() {
        System.out.println();
        int i = 1;
        for (Atleta c : podio) {
            System.out.printf("%d. %s | Tempo: %.1f s%n", i++, c.getNomeAtleta(), c.getTempoArrivo());
        }
    }
}

