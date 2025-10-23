import java.util.Random;
public class Atleta implements Runnable{
    int numero;
    String nome;
    double tempo=0;
    double metri =0;
    final double LUNGHEZZAGARA = 40000;
    final Random rand = new Random();

    public Atleta(int pNumero, String pNome){
        numero = pNumero;
        nome = pNome;
    }

    @Override
    public void run(){
        while(metri< LUNGHEZZAGARA){
            metri = metri + rand.nextInt(0,11);
            System.out.println("");
        }
    }
}

