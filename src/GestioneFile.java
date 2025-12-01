import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class Gestore {

    public List<String> leggiAtleti(String nomeFile) {
        List<String> nomi = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(nomeFile))) {
            String riga;
            while ((riga = br.readLine()) != null) {
                riga = riga.trim();
                if (!riga.isEmpty()) {
                    nomi.add(riga);
                }
            }
        } catch (IOException e) {
            System.err.println("Errore lettura file atleti: " + e.getMessage());
        }
        return nomi;
    }



    public void scriviPodio(List<Atleta> podio, String nomeFile) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nomeFile))) {
            int posizione = 1;
            for (Atleta c : podio) {
                pw.printf("%d. %s | Tempo: %.1f s%n", posizione++, c.getNomeAtleta(), c.getTempoArrivo());
            }
            System.out.println("Il podio è stato registrato sul file: " + nomeFile);
        } catch (IOException e) {
            System.err.println("Errore scrittura podio: " + e.getMessage());
        }
    }
}





