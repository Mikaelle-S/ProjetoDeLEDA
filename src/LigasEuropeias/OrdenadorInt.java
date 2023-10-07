package LigasEuropeias;

public class OrdenadorInt extends ProcessadorArquivo{
	
	public static void insertionSort(int [][] array) {
		
		int [] chave;
		int i;
		for(int j = 1; j < array.length; j++) {
			chave = array[j];
			for(i=j-1;(i>=0) && (array[i][0] > chave[0]); i--) {
				array[i + 1] = array[i];
			}
			array[i + 1] = chave;
		}
	}
	
	public static void selectionSort(int [][] array) {
		
		for (int i = 0; i < array.length - 1; i++) {
            int menor = i;

            for (int j = menor + 1; j < array.length; j++) {
                if (array[j][0] < array[menor][0]) {
                    menor = j;
                }
            }
            
            if (menor != i) {
                trocar(array, i, menor);;
            }
        }
	}
	
	public static void bubbleSort(int array[][]) {
		
		boolean temp = true;
        while (temp) {
            temp = false;
            for (int i = 0; i < array.length - 1; i++) {
                if (array[i][0] > array[i + 1][0]) {
                    trocar(array, i, i+1);
                    temp = true;
                }
            }
        }	
	}
	
	public static void mergeSort(int[][] array, int esquerdo, int direito) {
		
		if (esquerdo < direito) {
			  
            int meio = (esquerdo + direito) / 2;
  
            mergeSort(array, esquerdo, meio);
            mergeSort(array, meio + 1, direito);
  
            merge(array, esquerdo, meio, direito);
        }
	}
	public static void merge (int array[][], int esquerdo, int meio, int direito) {
		  
        int esquerdoTam = meio - esquerdo + 1;
        int direitoTam = direito - meio;
    
        int esquerdoArr[][] = new int[esquerdoTam][2];
        int direitoArr[][] = new int[direitoTam][2];
    
        for (int i = 0; i < esquerdoTam; i++) {
            esquerdoArr[i] = array[esquerdo + i];
        }
        for (int j = 0; j < direitoTam; j++) {
            direitoArr[j] = array[meio + 1 + j];
        }
        int i = 0, j = 0, k = esquerdo;
    
        while (i < esquerdoTam && j < direitoTam) {
            if (esquerdoArr[i][0] <= direitoArr[j][0]) {
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

	
	public static void quickSort(int[][] array, int inicio, int fim) {
		
		if (inicio < fim) {

            int pivo = particionar(array, inicio, fim);
            quickSort(array, inicio, pivo - 1);
            quickSort(array, pivo + 1, fim);
        }
	}
	private static int particionar (int[][] array, int inicio, int fim) {
        int meio = (inicio + fim) / 2;  // Escolhe o elemento do meio como pivô
        trocar(array, meio, inicio); // Troca o pivô com o primeiro elemento
        
        int[] pivo = array[inicio];
        int i = inicio + 1, f = fim;
        while (i <= f) {
            if (array[i][0] <= pivo[0]) {
                i++;

            } else if (pivo[0] < array[f][0]) {
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
	
	public static void quickMedianaDe3(int[][] array, int inicio, int fim) {
		if (inicio < fim) {

            int q = particionarMed3(array, inicio, fim);
            quickMedianaDe3(array, inicio, q - 1);
            quickMedianaDe3(array, q + 1, fim);            
        }
	}
	private static int particionarMed3(int[][] array, int inicio, int fim) {

        int meio = (inicio + fim) / 2;
        int a = array[inicio][0];
        int b = array[meio][0];
        int c = array[fim][0];
        int medianaIndice;

        if (a < b) {
            if (b < c) {
                medianaIndice = meio;
            } else {                
                if (a < c) {
                    medianaIndice = fim;
                } else {
                    medianaIndice = inicio;
                }
            }
        } else {
            if (c < b) {
                medianaIndice = meio;
            } else {
                if (c < a) {
                    medianaIndice = fim;
                } else {
                    medianaIndice = inicio;
                }
            }
        }
        trocar(array, medianaIndice, fim);
        
        int[] pivo = array[fim];
        int i = inicio - 1;

        for (int j = inicio; j <= fim - 1; j++) {
            if (array[j][0] <= pivo[0]) {
                i = i + 1;
                trocar(array, i, j);
            }
        }
        trocar(array, i + 1, fim);
        return i + 1; 
    }
	
	public static void countingSort(int[][] array, int tam) {
		 int[][] arrayAux = new int[tam + 1][2];
		    
	        int max = array[0][0];
	        for (int i = 1; i < tam; i++) {
	            if (array[i][0] > max) {
	            max = array[i][0];
	            }
	        }
	        int[] count = new int[max + 1];
	        
	        for (int i = 0; i < tam; i++) {
	            count[array[i][0]]++;
	        }
	        for (int i = 1; i <= max; i++) {
	            count[i] += count[i - 1];
	        }    
	        for (int i = tam - 1; i >= 0; i--) {
	            arrayAux[count[array[i][0]] - 1] = array[i];
	            count[array[i][0]]--;
	        }    
	        for (int i = 0; i < tam; i++) {
	            array[i] = arrayAux[i];
	        }
	}
	
	public static void heapSort(int array[][]) {
		
		for (int i = (array.length / 2) - 1; i >= 0; i--) {

            agrupar(array, array.length, i);
        }
        for (int i = array.length - 1; i >= 0; i--) {
            
            trocar(array, 0, i);
  
            agrupar(array, i, 0);
        }
	}
	
	public static void agrupar (int array[][], int n, int i) {

        int maior = i;
        int esquerdo = 2 * i + 1;
        int direito = 2 * i + 2;
  
        if (esquerdo < n && array[esquerdo][0] > array[maior][0]) {
            maior = esquerdo;
        }
        if (direito < n && array[direito][0] > array[maior][0]) {
            maior = direito;
        }
        if (maior != i) {
            trocar(array, i, maior);
            agrupar(array, n, maior);
        }
    }
    
    private static void trocar(int[][] array, int i, int j) {
        int[] aux = array[i];
        array[i] = array[j];
        array[j] = aux;
    } 
}
