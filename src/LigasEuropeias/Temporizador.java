package LigasEuropeias;

import java.util.concurrent.TimeUnit;

public class Temporizador {
	/**
     * Pausa a execução do código por um dado número de segundos.
     * @param segundos Número de segundos para pausar a execução.
     */
    public static void pausar(int segundos) {
        try {
            TimeUnit.SECONDS.sleep(segundos);
        } catch (InterruptedException e) {
            System.out.println("Erro: " + e);
        }
    }
    
    public static void pausar3Segundos() {
        try {
            System.out.print("Criando arquivo");
            for (int i = 0; i < 3; i++) {
                pausar(1);
                System.out.print(". ");
            }
            System.out.println();
        } catch (Exception e) {
            System.out.println("Erro: " + e);
        }
    }
    public static void pausar1Segundo() {
        pausar(1);
    }

}
