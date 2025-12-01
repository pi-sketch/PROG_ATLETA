import java.util.Random;


/**
 * Rappresenta un corridore: esegue un ciclo ogni secondo incrementando la distanza percorsa.
 * Quando supera la lunghezza del tracciato notifica il giudice.
 * Non esiste più il sistema di "stamina".
 */
class Atleta implements Runnable {
    private final String nomeAtleta;
    private final Giudice giudice;
    private final Random random = new Random();



    private double distanza;           // corrisponde a "progresso"
    private long idCorrente = -1;      // corrisponde a "numero"
    private int secondiT;
    private double tempoArrivo = -1.0;



    public Atleta(String nome, Giudice giudice) {
        this.nomeAtleta = nome;
        this.giudice = giudice;
        this.distanza = 0.0;
        this.secondiT = 0;

        // registra il corridore presso il giudice (sincronizzato nel giudice)
        giudice.registraAtleta(this);


        // Messaggio sintetico sullo stato del corridore (senza sistema stamina)
        System.out.printf("%s è pronto.%n", nomeAtleta);
    }



    public String getNomeAtleta() {
        return nomeAtleta;
    }


    public long getIdCorrente() {
        return idCorrente;
    }
    public double getTempoArrivo() {
        return tempoArrivo;
    }

    private void corsa() {
        // aumento casuale della stamina
        double incremento = 1.0 + random.nextDouble() * 7.0; // tra 1.0 e 5.0 unità
        distanza += incremento;
        secondiT += 1;
    }



    @Override
    public void run() {
        idCorrente = Thread.currentThread().getId();


        // ogni secondo avanza
        while (distanza < giudice.getLunghezzaTracciato()) {
            corsa();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }


        // registra il tempo di arrivo e segnala il giudice
        tempoArrivo = secondiT;
        giudice.Arrivo(this);
    }
}





