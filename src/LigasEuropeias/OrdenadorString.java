package LigasEuropeias;

public class OrdenadorString {
	public static void insertionSort (String[][] array, int index) {

        String key;
        int i;
    
        for (int j = 1; j < array.length; j++) {

            key = array[j][index];
            for (i = j - 1; (i >= 0) && (array[i][index].compareToIgnoreCase(key) > 0); i--) {

                array[i + 1][index] = array[i][index];
            }
            array[i + 1][index] = key;
        }
    }

    public static void selectionSort (String[][] array, int index) {

        for (int i = 0; i < array.length - 1; i++) {

            int menor = i;
            String menorStr = array[i][index];
            for (int j = menor + 1; j < array.length; j++) {
                if (array[j][index].compareToIgnoreCase(menorStr) < 0) {
                    menorStr = array[j][index];
                    menor = j;
                }
            }
            if (menor != i) {
                trocar(array, i, menor);
            }
        }
    }

    public static void bubbleSort (String array[][], int index) {

        for (int i = 0; i < array.length-1; i++) {
            for (int j = 0; j < array.length - 1; j++) {
                if (array[j][index].compareToIgnoreCase(array[j + 1][index]) > 0) {
                    trocar(array, j, j+1);
                }
            }
        }
    }
    
    public static void mergeSort (String array[][], int esquerdo, int direito, int index) {

        if (esquerdo < direito) {
  
            int meio = (esquerdo + direito) / 2;
  
            mergeSort(array, esquerdo, meio, index);
            mergeSort(array, meio + 1, direito, index);
  
            merge(array, esquerdo, meio, direito, index);
        }
    }
    
    public static void merge (String array[][], int esquerdo, int meio, int direito, int index) {
  
        int esquerdoTam = meio - esquerdo + 1;
        int direitoTam = direito - meio;
    
        String esquerdoArr[][] = new String[esquerdoTam][];
        String direitoArr[][] = new String[direitoTam][];
    
        for (int i = 0; i < esquerdoTam; i++) {
            esquerdoArr[i] = array[esquerdo + i];
        }
        for (int j = 0; j < direitoTam; j++) {
            direitoArr[j] = array[meio + 1 + j];
        }
        int i = 0, j = 0, k = esquerdo;
    
        while (i < esquerdoTam && j < direitoTam) {
            if (esquerdoArr[i][index].compareToIgnoreCase(direitoArr[j][index]) <= 0) {
                array[k] = esquerdoArr[i];
                i++;
            } else {
                array[k] = direitoArr[j];
                j++;
            }
            k++;
        }  
        while (i < esquerdoTam) {
            array[k] = esquerdoArr[i];
            i++;
            k++;
        }
    
        while (j < direitoTam) {
            array[k] = direitoArr[j];
            j++;
            k++;
        }
    }

    public static void quickSort (String[][] array, int inicio, int fim, int index) {

        if (inicio < fim) {

            int posicaoPivo = particionar(array, inicio, fim, index);
            quickSort(array, inicio, posicaoPivo - 1, index);
            quickSort(array, posicaoPivo + 1, fim, index);
        }
    }
    private static int particionar (String[][] array, int inicio, int fim, int index) {
        
        String[] pivo = array[inicio];
        int i = inicio + 1, f = fim;
        while (i <= f) {
            if (array[i][index].compareToIgnoreCase(pivo[index]) <= 0) {
                i++;

            } else if (pivo[index].compareToIgnoreCase(array[f][index]) < 0) {
                f--;

            } else {
                trocar(array, i, f);
                i++;
                f--;
            }
        }
        array[inicio] = array[f];
        array[f] = pivo;
        return f;
    }

    public static void quickMedianaDe3(String[][] array, int inicio, int fim, int index) {  

        if (inicio < fim) {
            int q = particionarMed3(array, inicio, fim, index);
            quickMedianaDe3(array, inicio, q - 1, index);
            quickMedianaDe3(array, q + 1, fim, index);            
        }
    }    
    private static int particionarMed3(String[][] array, int inicio, int fim, int index) {

        int meio = (inicio + fim) / 2;
        String str1 = array[inicio][index];
        String str2 = array[meio][index];
        String str3 = array[fim][index];
        int medianaIndice;

        if (str1.compareToIgnoreCase(str2) < 0) {
            if (str2.compareToIgnoreCase(str3) < 0) {
                medianaIndice = meio;
            } else {                
                if (str1.compareToIgnoreCase(str3) < 0) {
                    medianaIndice = fim;
                } else {
                    medianaIndice = inicio;
                }
            }
        } else {
            if (str3.compareToIgnoreCase(str2) < 0) {
                medianaIndice = meio;
            } else {
                if (str3.compareToIgnoreCase(str1) < 0) {
                    medianaIndice = fim;
                } else {
                    medianaIndice = inicio;
                }
            }
        }
        trocar(array, medianaIndice, fim);
        String pivo = array[fim][index];
        int i = inicio - 1;

        for (int j = inicio; j <= fim - 1; j++) {
            if (array[j][index].compareToIgnoreCase(pivo) <= 0) {
                i = i + 1;
                trocar(array, i, j);
            }
        }
        trocar(array, i + 1, fim);
        return i + 1; 
    }
    
    public static void heapSort (String array[][], int index) {
  
        for (int i = array.length / 2 - 1; i >= 0; i--) {

            agrupar(array, array.length, i, index);
        }
        for (int i = array.length - 1; i >= 0; i--) {

            trocar(array, 0, i);  
            agrupar(array, i, 0, index);
        }
    } 
    public static void agrupar (String array[][], int n, int i, int index) {

        int maior = i;
        int esquerdo = 2 * i + 1;
        int direito = 2 * i + 2;
  
        if (esquerdo < n && array[esquerdo][index].compareToIgnoreCase(array[maior][index]) > 0) {
            maior = esquerdo;
        }
        if (direito < n && array[direito][index].compareToIgnoreCase(array[maior][index]) > 0) {
            maior = direito;
        }
        if (maior != i) {
            trocar(array, i, maior);
            agrupar(array, n, maior, index);
        }
    }

    private static void trocar(String[][] array, int i, int j) {
        String aux[] = array[i];
        array[i] = array[j];
        array[j] = aux;
    }
}
