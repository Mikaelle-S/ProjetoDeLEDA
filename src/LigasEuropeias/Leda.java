package LigasEuropeias;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;


public class Leda {
    //Adicionando ArrayList para armazenar partidas ordenadas pelo campo 'venue'
    static ArrayList<String[]> sortedMatchesByVenue = new ArrayList<>();
    // Adicionando ArrayList para armazenar partidas ordenadas pelo campo 'attendance'
    static ArrayList<int[]> sortedMatchesByAttendance = new ArrayList<>();

	public static void main(String[] args) throws IOException {

        // arquivo a ser trabalhado
        String matchesPath = "matches.csv";

        File currentDirectory = new File(".");
        System.out.println("Diretório atual: " + currentDirectory.getAbsolutePath());
    
        // variável que recebe a quantidade de linhas do arquivo 
        int nLines = (int) ProcessadorArquivo.contarLinhas(matchesPath);

        
        int aux = 0; // variável auxiliar feita para controlar as linhas da matriz

        String[][] matches = new String[nLines][];
        try (BufferedReader br = new BufferedReader(new FileReader(matchesPath))) {
            
            // variável que recebe toda a linha do arquivo
            String line = br.readLine();

            // enquanto houverem linhas a serem lidas, os tratamentos continuarão sendo feitos
            while (line != null) {

                // é gravado na linha da matriz todas as posições referentes as colunas do arquivo
                matches[aux] = ProcessadorArquivo.corrigirVirgulas(line);
                aux++; // variável que controla a linha da matriz a ser gravada é incrementada
                line = br.readLine(); // variável que controla se ainda linhas a serem lidas é atualizada
            }
            System.out.println("O arquivo matches_T1 será gravado\n");

        } catch (IOException e) {
            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
        }

        try (PrintWriter writerMatchesT1 = new PrintWriter(new File("matches_T1.csv"))) {

            // objeto da classe StringBuilder para ser concatenado tudo referente ao novo arquivo
            StringBuilder strb1 = new StringBuilder();

            // laço que percorre todas as posições da matriz
            for (int i = 0; i < matches.length; i++) {
                for (int j = 0; j < 34; j++) {
                    if ((j <= 8) || (j >= 12 && j <= 13) || (j == 33) || (j == 31)) {

                        // o strb1 tem seu conteúdo concatenado mais uma vírgula para o CSV
                        strb1.append(matches[i][j] + ",");
                    }
                }
                // quebra de linha no arquivo
                if (i < matches.length - 1) {
                    strb1.append("\n");
                }
            }
            // delay no programa para que possa ser melhor acompanhado a execução e a criação dos arquivos
            Temporizador.pausar3Segundos();

            // conteúdo do nosso sb é gravado no arquivo
            writerMatchesT1.write(strb1.toString());
            writerMatchesT1.close();

            System.out.println("\nO arquivo matches_T1 foi gravado no caminho: ProjetoDeLEDA/matches_T1.csv\n");
            Temporizador.pausar1Segundo(); // delay de transição para o próximo arquivo

        } catch (FileNotFoundException e) {
            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
            return;
        }
        
        String[][] matches_T1 = new String[nLines-1][]; // matriz para colocar os dados de matches_T1
        String[] formattedFullDates = new String[nLines-1]; // array para guardar as datas formatadas
        String lineIndices = null; // variável para adicionar o campo 'full_date' aos índices dos dados

        // arquivo a ser trabalhado
        String matches_T1Path = "matches_T1.csv";
        int aux2 = 0;

        try (BufferedReader br = new BufferedReader(new FileReader(matches_T1Path))) {

            // lê a primeira linha do arquivo, a que corresponde aos índices das colunas
            String line = br.readLine();

            // salva esses índices numa nova variável e adiciona o novo campo 'full_date'
            lineIndices = line.concat("full_date");

            /* grava a segunda linha do arquivo onde estão as datas para que elas possam ser formatadas,
            o laço então começa a ler o arquivo a partir da linha onde estão os dados*/ 
            line = br.readLine();

            while (line != null) {

                // chama a o método corrigirVirgulas para reorganizar as linhas
                matches_T1[aux2] = ProcessadorArquivo.corrigirVirgulas(line);

                // chama o método responsável por formatar a data
                formattedFullDates[aux2] = ProcessadorArquivo.formarDataCompleta(matches_T1[aux2]);

                // grava na linha da matriz os dados antigos mais a coluna da data formatada
                formattedFullDates[aux2] = line.concat(formattedFullDates[aux2]);

                aux2++; // variável que controla a linha da matriz a ser gravada é incrementada
                line = br.readLine(); // variável que controla se ainda linhas a serem lidas é atualizada
            }
            System.out.println("O arquivo matches_T2 será gravado\n");
            
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
            return;
        }

        try (PrintWriter writerMatchesT2 = new PrintWriter(new File("matches_T2.csv"))) {

            // objeto da classe StringBuilder para ser concatenado tudo referente ao novo arquivo
            StringBuilder strb2 = new StringBuilder();

            strb2.append(lineIndices+"\n"); // guarda no arquivo a linha dos índices

            // laço que percorre as posições do array com as linhas do novo arquivo
            for (int i = 0; i < formattedFullDates.length; i++) {

                // o strb2 tem seu conteúdo concatenado com a linha do arquivo mais uma quebra de linha
                strb2.append(formattedFullDates[i]);
                if (i < formattedFullDates.length-1) {
                    strb2.append("\n");
                }
            }
            // delay no programa para que possa ser melhor acompanhado a execução e a criação dos arquivos
            Temporizador.pausar3Segundos();

            // o conteúdo é gravado no arquivo
            //System.out.println(strb2.toString());
            writerMatchesT2.write(strb2.toString());
            writerMatchesT2.close();

            System.out.println("\nO arquivo matches_T2 foi gravado no caminho: ProjetoDeLEDA/matches_T2.csv\n");
            Temporizador.pausar1Segundo(); // delay de transição para o próximo arquivo

        } catch (FileNotFoundException e) {
            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
            return;
        }

        // arquivo a ser lido
        String matches_T2Path = "matches_T2.csv";

        // dimensiona a matriz com a quantidade de partidas da Premier League
        int linesPL = ProcessadorArquivo.contarLinhasPL(matches_T2Path);
        String[] matches_F1 = new String[linesPL];
        int aux3 = 0; // variável que controla a linha da matriz

        try (BufferedReader br = new BufferedReader(new FileReader(matches_T2Path))) {

            String line = br.readLine();

            while (line != null) {

                // se a linha conter "Premier League ela é salva no Array"
                if (line.contains("Premier League")) {

                    matches_F1[aux3] = line;
                    aux3++;
                }
                line = br.readLine(); // lê a próxima linha
            }

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
            return;
        }

        try (PrintWriter writerMatchesF1 = new PrintWriter(new File("matches_F1.csv"))) {

            System.out.println("O arquivo matches_F1 será gravado\n");
            
            // StringBuilder que terá todas as partidas da premier league antes de ser gravada no arquivo
            StringBuilder strb3 = new StringBuilder();
            strb3.append(lineIndices+"\n"); // adiciona a linha dos indices

            for (int i = 0; i < matches_F1.length; i++) {

                strb3.append(matches_F1[i]+"\n");
            }
            // pausa no código para transição
            Temporizador.pausar3Segundos();

            writerMatchesF1.write(strb3.toString());
            writerMatchesF1.close();

            System.out.println("\nO arquivo matches_F1 foi gravado no caminho: ProjetoDeLEDA/matches_F1.csv\n");
            Temporizador.pausar1Segundo(); // delay de transição para o próximo arquivo

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
            return;
        }
        
        // Contar o número de partidas com mais de 20k de público
        int linesPLM20k = ProcessadorArquivo.contarLinhasMaisQue20K(matches_F1);
        String[] matchesF2 = new String[linesPLM20k];

        // HashMap para armazenar as partidas com mais de 20k de público
        HashMap<String, String> matchesOver20k = new HashMap<>();

        try (PrintWriter writerMatchesF2 = new PrintWriter(new File("matches_F2.csv"))) {

            System.out.println("O arquivo matches_F2 será gravado\n");

            // laço que percorrerá o número de partidas com mais de 20k de público
            for (int i = 0; i < matches_F1.length; i++) {

                // se o retorno da função formatarPublico for maior que 20000
                if (ProcessadorArquivo.formatarInteiros(matches_F1[i], 6) > 20000) {
                    // adiciona a partida ao HashMap
                    matchesOver20k.put(matches_F1[i], lineIndices + "\n" + matches_F1[i]);
                    // adiciona a partida ao array matchesF2
                    matchesF2[matchesOver20k.size() - 1] = matches_F1[i];
                }
            }

            Temporizador.pausar3Segundos();

            // Escrever as partidas no arquivo
            writerMatchesF2.write(String.join("\n", matchesOver20k.values()));
            writerMatchesF2.close();

            System.out.println("\nO arquivo matches_F2 foi gravado no caminho: ProjetoDeLEDA/matches_F2.csv\n");
            Temporizador.pausar1Segundo(); // delay de transição para o próximo arquivo

        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
            return;
        }
        
        Scanner sc = new Scanner(System.in);
        int ordenar = -1;
        // laço do menu para decidir por qual coluna o arquivo será ordenado
        while (ordenar != 0) {

            System.out.println("Escolha por qual coluna deseja ordenar\n");
            System.out.println("Digite 1 para ordenar pelo campo 'venue'\nDigite 2 para ordenar pelo campo 'attendance'");
            System.out.println("Digite 3 para ordenar pelo campo 'full_date'\n\nDigite 0 para sair");
            ordenar = sc.nextInt();

            int ordenaçãoVenue = -1;
            int ordenaçãoAttend = -1;
            int ordenaçãoDate = -1;

            switch (ordenar) {

                case 1:
                // laço para decidir qual algoritmo de ordenação será usado
                while (ordenaçãoVenue != 0) {

                    // essa matriz receberá o conteúdo de matches_T2 e servirá como o médio caso
                    // o melhor caso será ela própria após ser ordenada
                    String[][] venueMDC = new String[formattedFullDates.length][];
                    for (int i = 0; i < venueMDC.length; i++) {
                        venueMDC[i] = ProcessadorArquivo.corrigirVirgulas(formattedFullDates[i]);
                    }
                    /* o pior caso será essa, ela será o inverso de 'venueMDC', essa transformação
                    ocorrerá dentro dos blocos 'try' */
                    String[][] venuePRC = new String[formattedFullDates.length][];

                    System.out.println("Escolha qual algoritmo de ordenação deseja usar\n");
                    System.out.println("Digite 1 para Insertion Sort\nDigite 2 para Selection Sort\nDigite 3 para Bubble Sort");
                    System.out.println("Digite 4 para Quick Sort\nDigite 5 para Merge Sort\nDigite 6 para Heap Sort");
                    System.out.println("\nDigite 0 para sair");
                    ordenaçãoVenue = sc.nextInt();

                    switch (ordenaçãoVenue) {
                        case 1:
                        try (PrintWriter venuesInsertionSortMDC = new PrintWriter(new File("matches_t2_venues_insertionSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Insertion Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            if (venueMDC != null && venueMDC.length > 0) {
                                System.out.println("\nO médio caso para o insertion sort está sendo ordenado");
                                long tempo = System.currentTimeMillis();
                                OrdenadorString.insertionSort(venueMDC, 7);
                                System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");
                            }


                            venuesInsertionSortMDC.write(sb.toString());
                            venuesInsertionSortMDC.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_insertionSort_medioCaso.csv' foi criado");
                            
                             // Adicionando as partidas ordenadas pelo campo 'venue' ao ArrayList
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venuesInsertionSortMLC = new PrintWriter(new File("matches_t2_venues_insertionSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            if (venuePRC != null && venuePRC.length > 0) {            
                                System.out.println("\nO melhor caso para o insertion sort está sendo ordenado");
                                long tempo = System.currentTimeMillis();
                                OrdenadorString.insertionSort(venueMDC, 7);
                                System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");
                            }

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venueMDC.length; i++) {
                                for (int j = 0; j < venueMDC[i].length; j++) {
                                    sb.append(venueMDC[i][j]+",");
                                }
                                if (i != venueMDC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = venuePRC.length-1;
                            for (int i = 0; i < venueMDC.length; i++) {
                                venuePRC[i] = venueMDC[j]; j--;
                            }

                            venuesInsertionSortMLC.write(sb.toString());
                            venuesInsertionSortMLC.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_insertionSort_melhorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venuesInsertionSortPRC = new PrintWriter(new File("matches_t2_venues_insertionSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                        
                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venuePRC.length; i++) {
                                for (int m = 0; m < venuePRC[i].length; m++) {
                                    sb.append(venuePRC[i][m]+",");
                                }
                                if (i != venuePRC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            if (venuePRC != null && venuePRC.length > 0) {
                                System.out.println("\nO pior caso para o insertion sort está sendo ordenado");
                                long tempo = System.currentTimeMillis();
                                OrdenadorString.insertionSort(venuePRC, 7);
                                System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");
                            }

                            venuesInsertionSortPRC.write(sb.toString());
                            venuesInsertionSortPRC.close();
                            System.out.println("O arquivo 'matches_t2_venues_insertionSort_piorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        break;

                        case 2:
                        try (PrintWriter venuesSelectionSortMDC = new PrintWriter(new File("matches_t2_venues_selectionSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Selection Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o selection sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.selectionSort(venueMDC, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venuesSelectionSortMDC.write(sb.toString());
                            venuesSelectionSortMDC.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_selectionSort_medioCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venuesSelectionSortMLC = new PrintWriter(new File("matches_t2_venues_selectionSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o selection sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.selectionSort(venueMDC, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");
                            
                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venueMDC.length; i++) {
                                for (int j = 0; j < venueMDC[i].length; j++) {
                                    sb.append(venueMDC[i][j]+",");
                                }
                                if (i != venueMDC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = venuePRC.length-1;
                            for (int i = 0; i < venueMDC.length; i++) {
                                venuePRC[i] = venueMDC[j]; j--;
                            }

                            venuesSelectionSortMLC.write(sb.toString());
                            venuesSelectionSortMLC.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_selectionSort_melhorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venuesSelectionSortPRC = new PrintWriter(new File("matches_t2_venues_selectionSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o selection sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venuePRC.length; i++) {
                                for (int m = 0; m < venuePRC[i].length; m++) {
                                    sb.append(venuePRC[i][m]+",");
                                }
                                if (i != venuePRC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.selectionSort(venuePRC, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venuesSelectionSortPRC.write(sb.toString());
                            venuesSelectionSortPRC.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_selectionSort_piorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        break;

                        case 3:
                        try (PrintWriter venuesBubbleSortMDC = new PrintWriter(new File("matches_t2_venues_bubbleSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Bubble Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Bubble sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.bubbleSort(venueMDC, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venuesBubbleSortMDC.write(sb.toString());
                            venuesBubbleSortMDC.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_bubbleSort_medioCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venuesBubbleSortMLC = new PrintWriter(new File("matches_t2_venues_bubbleSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Bubble sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.bubbleSort(venueMDC, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");
                            
                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venueMDC.length; i++) {
                                for (int j = 0; j < venueMDC[i].length; j++) {
                                    sb.append(venueMDC[i][j]+",");
                                }
                                if (i != venueMDC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = venuePRC.length-1;
                            for (int i = 0; i < venueMDC.length; i++) {
                                venuePRC[i] = venueMDC[j]; j--;
                            }

                            venuesBubbleSortMLC.write(sb.toString());
                            venuesBubbleSortMLC.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_bubbleSort_melhorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venuesBubbleSortPRC = new PrintWriter(new File("matches_t2_venues_bubbleSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o bubble sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venuePRC.length; i++) {
                                for (int m = 0; m < venuePRC[i].length; m++) {
                                    sb.append(venuePRC[i][m]+",");
                                }
                                if (i != venuePRC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.bubbleSort(venuePRC, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venuesBubbleSortPRC.write(sb.toString());
                            venuesBubbleSortPRC.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_bubbleSort_piorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        break;

                        case 4:
                        try (PrintWriter venuesInsertionSortMDC = new PrintWriter(new File("matches_t2_venues_quickSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Quick Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Quick Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.quickSort(venueMDC, 0, venueMDC.length-1, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venuesInsertionSortMDC.write(sb.toString());
                            venuesInsertionSortMDC.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_quickSort_medioCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venuesInsertionSortMLC = new PrintWriter(new File("matches_t2_venues_quickSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Quick Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.quickSort(venueMDC, 0, venueMDC.length-1, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");
                            
                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venueMDC.length; i++) {
                                for (int j = 0; j < venueMDC[i].length; j++) {
                                    sb.append(venueMDC[i][j]+",");
                                }
                                if (i != venueMDC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = venuePRC.length-1;
                            for (int i = 0; i < venueMDC.length; i++) {
                                venuePRC[i] = venueMDC[j]; j--;
                            }

                            venuesInsertionSortMLC.write(sb.toString());
                            venuesInsertionSortMLC.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_quickSort_melhorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venuesInsertionSortPRC = new PrintWriter(new File("matches_t2_venues_quickSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Quick Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venuePRC.length; i++) {
                                for (int m = 0; m < venuePRC[i].length; m++) {
                                    sb.append(venuePRC[i][m]+",");
                                }
                                if (i != venuePRC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.quickSort(venuePRC, 0, venuePRC.length-1, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venuesInsertionSortPRC.write(sb.toString());
                            venuesInsertionSortPRC.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_quickSort_piorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_venues_quickSortMedianaDe3_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Quick Sort Mediana de 3 serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Quick Sort Mediana de 3 está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.quickMedianaDe3(venueMDC, 0, venueMDC.length-1, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_quickSortMedianaDe3_medioCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_venues_quickSortMedianaDe3_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Quick Sort Mediana de 3 está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.quickMedianaDe3(venueMDC, 0, venueMDC.length-1, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");
                            
                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venueMDC.length; i++) {
                                for (int j = 0; j < venueMDC[i].length; j++) {
                                    sb.append(venueMDC[i][j]+",");
                                }
                                if (i != venueMDC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = venuePRC.length-1;
                            for (int i = 0; i < venueMDC.length; i++) {
                                venuePRC[i] = venueMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_quickSortMedianaDe3_melhorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_venues_quickSortMedianaDe3_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Quick Sort Mediana de 3 está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venuePRC.length; i++) {
                                for (int m = 0; m < venuePRC[i].length; m++) {
                                    sb.append(venuePRC[i][m]+",");
                                }
                                if (i != venuePRC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.quickMedianaDe3(venuePRC, 0, venueMDC.length-1, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("O arquivo 'matches_t2_venues_quickSortMedianaDe3_piorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        break;

                        case 5:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_venues_mergeSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Merge Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Merge Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.mergeSort(venueMDC, 0, venueMDC.length-1, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_mergeSort_medioCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_venues_mergeSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Merge Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.mergeSort(venueMDC, 0, venueMDC.length-1, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");
                            
                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venueMDC.length; i++) {
                                for (int j = 0; j < venueMDC[i].length; j++) {
                                    sb.append(venueMDC[i][j]+",");
                                }
                                if (i != venueMDC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = venuePRC.length-1;
                            for (int i = 0; i < venueMDC.length; i++) {
                                venuePRC[i] = venueMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_mergeSort_melhorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_venues_mergeSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Merge Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venuePRC.length; i++) {
                                for (int m = 0; m < venuePRC[i].length; m++) {
                                    sb.append(venuePRC[i][m]+",");
                                }
                                if (i != venuePRC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.mergeSort(venuePRC, 0, venueMDC.length-1, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_mergeSort_piorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        break;

                        case 6:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_venues_heapSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Heap Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Heap Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.heapSort(venueMDC, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_heapSort_medioCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_venues_heapSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Heap Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.heapSort(venueMDC, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");
                            
                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venueMDC.length; i++) {
                                for (int j = 0; j < venueMDC[i].length; j++) {
                                    sb.append(venueMDC[i][j]+",");
                                }
                                if (i != venueMDC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = venuePRC.length-1;
                            for (int i = 0; i < venueMDC.length; i++) {
                                venuePRC[i] = venueMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_heapSort_melhorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_venues_heapSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Heap Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < venuePRC.length; i++) {
                                for (int m = 0; m < venuePRC[i].length; m++) {
                                    sb.append(venuePRC[i][m]+",");
                                }
                                if (i != venuePRC.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorString.heapSort(venueMDC, 7);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_venues_heapSort_piorCaso.csv' foi criado");
                            sortedMatchesByVenue.addAll(Arrays.asList(venueMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        break;    
                    }
                }
                break;

                case 2:
                // laço para decidir qual algoritmo de ordenação será usado
                while (ordenaçãoAttend != 0) {

                    /* essa matriz receberá o conteúdo de 'attendance' e o índice da linha,
                    o conteúdo será usado nas ordenações e o índice nas gravações dos arquivos,
                    servirá para ser acessado o índice correto a ser adicionado na próxima linha */

                    // ela será o médio caso e depois o melhor caso após ser ordenada
                    int[][] attendanceMDC = new int[formattedFullDates.length][2];
                    for (int i = 0; i < formattedFullDates.length; i++) {
                        attendanceMDC[i][0] = ProcessadorArquivo.formatarInteiros(formattedFullDates[i], 6);
                        attendanceMDC[i][1] = i;
                    }
                    /* essa será o inverso de 'attendanceMDC' e servirá como o pior caso, essa
                    transformação ocorrerá dentro dos blocos 'try' */
                    int[][] attendancePRC = new int[formattedFullDates.length][2];

                    System.out.println("Escolha qual algoritmo de ordenação deseja usar\n");
                    System.out.println("Digite 1 para Insertion Sort\nDigite 2 para Selection Sort\nDigite 3 para Bubble Sort");
                    System.out.println("Digite 4 para Quick Sort\nDigite 5 para Merge Sort\nDigite 6 para Heap Sort");
                    System.out.println("Digite 7 para Counting Sort\n\nDigite 0 para sair");
                    ordenaçãoAttend = sc.nextInt();

                    switch (ordenaçãoAttend) {

                        case 1:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_insertionSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Insertion Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Insertion Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.insertionSort(attendanceMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_insertionSort_medioCaso.csv' foi criado");
                            // Adicionando as partidas ordenadas pelo campo 'attendance' ao ArrayList
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                                
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_insertionSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Insertion Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.insertionSort(attendanceMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendanceMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = attendancePRC.length-1;
                            for (int i = 0; i < attendanceMDC.length; i++) {
                                attendancePRC[i] = attendanceMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_insertionSort_melhorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_insertionSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Insertion Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendancePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.insertionSort(attendancePRC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_insertionSort_piorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 2:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_selectionSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Selection Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Selection Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.selectionSort(attendanceMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_selectionSort_medioCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_selectionSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Selection Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.selectionSort(attendanceMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendanceMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = attendancePRC.length-1;
                            for (int i = 0; i < attendanceMDC.length; i++) {
                                attendancePRC[i] = attendanceMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_selectionSort_melhorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_selectionSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Selection Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendancePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.selectionSort(attendancePRC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_selectionSort_piorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 3:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_bubbleSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Bubble Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Bubble Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.bubbleSort(attendanceMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_bubbleSort_medioCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_bubbleSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Bubble Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.bubbleSort(attendanceMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendanceMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = attendancePRC.length-1;
                            for (int i = 0; i < attendanceMDC.length; i++) {
                                attendancePRC[i] = attendanceMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_bubbleSort_melhorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_bubbleSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Bubble Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendancePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.bubbleSort(attendancePRC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_bubbleSort_piorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 4:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_quickSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Quick Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Quick Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickSort(attendanceMDC, 0, attendanceMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_quickSort_medioCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_quickSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Quick Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickSort(attendanceMDC, 0, attendanceMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendanceMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = attendancePRC.length-1;
                            for (int i = 0; i < attendanceMDC.length; i++) {
                                attendancePRC[i] = attendanceMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_quickSort_melhorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_quickSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Quick Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendancePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickSort(attendancePRC, 0, attendancePRC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_quickSort_piorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_quickSortMedianaDe3_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Quick Sort Mediana de 3 serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Quick Sort Mediana de 3 está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickMedianaDe3(attendanceMDC, 0, attendanceMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_quickSortMedianaDe3_medioCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_quickSortMedianaDe3_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Quick Sort Mediana De 3 está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickMedianaDe3(attendanceMDC, 0, attendanceMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendanceMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = attendancePRC.length-1;
                            for (int i = 0; i < attendanceMDC.length; i++) {
                                attendancePRC[i] = attendanceMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_quickSortMedianaDe3_melhorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_quickSortMedianaDe3_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Quick Sort Mediana De 3 está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendancePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickMedianaDe3(attendancePRC, 0, attendancePRC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_quickSortMedianaDe3_piorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 5:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_mergeSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Merge Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Merge Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.mergeSort(attendanceMDC, 0, attendanceMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_mergeSort_medioCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_mergeSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Merge Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.mergeSort(attendanceMDC, 0, attendanceMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendanceMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = attendancePRC.length-1;
                            for (int i = 0; i < attendanceMDC.length; i++) {
                                attendancePRC[i] = attendanceMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_mergeSort_melhorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_mergeSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Merge Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendancePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.mergeSort(attendancePRC, 0, attendancePRC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_mergeSort_piorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 6:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_heapSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Heap Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Heap Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.heapSort(attendanceMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_heapSort_medioCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_heapSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Heap Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.heapSort(attendanceMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendanceMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = attendancePRC.length-1;
                            for (int i = 0; i < attendanceMDC.length; i++) {
                                attendancePRC[i] = attendanceMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_heapSort_melhorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_heapSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Heap Sort está sendo ordenado");
                            
                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendancePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.heapSort(attendancePRC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_heapSort_piorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 7:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_countingSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Counting Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            System.out.println("\nO médio caso para o Counting Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.countingSort(attendanceMDC, attendanceMDC.length);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_countingSort_medioCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));        
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_countingSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Counting Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.countingSort(attendanceMDC, attendanceMDC.length);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendanceMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            int j = attendancePRC.length-1;
                            for (int i = 0; i < attendanceMDC.length; i++) {
                                attendancePRC[i] = attendanceMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_countingSort_melhorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_attendance_countingSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Counting Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[attendancePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.countingSort(attendancePRC, attendancePRC.length);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_countingSort_piorCaso.csv' foi criado");
                            sortedMatchesByAttendance.addAll(Arrays.asList(attendanceMDC));
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;
                        
                    }
                }
                break; 

                case 3:
                //Usando TreeMap para armazenar para armazenar dados associados às datas
                TreeMap<Integer, String> sortedMatchesByDate = new TreeMap<>();
                // laço para decidir qual algoritmo de ordenação será usado
                while (ordenaçãoDate != 0) {

                    // essa array receberá as datas no formato DD/MM/AAAA
                    String[] fullDates = new String[formattedFullDates.length];
                    for (int i = 0; i < fullDates.length; i++) {
                        fullDates[i] = ProcessadorArquivo.formarDataCompleta(matches_T1[i]);
                    }

                    /* essa matriz receberá o conteúdo de 'full_date' e o índice da linha,
                    o conteúdo será usado nas ordenações e o índice nas gravações dos arquivos,
                    servirá para ser acessado o índice correto a ser adicionado na próxima linha */

                    // ela será o médio caso e depois o melhor caso após ser ordenada
                    int[][] dateMDC = new int[formattedFullDates.length][2];
                    for (int i = 0; i < formattedFullDates.length; i++) {
                        dateMDC[i][0] = ProcessadorArquivo.formarDataInt(fullDates[i]);
                        dateMDC[i][1] = i;
                    }
                    /* essa será o inverso de 'dateMDC' e servirá como o pior caso, essa
                    transformação ocorrerá dentro dos blocos 'try' */
                    int[][] datePRC = new int[formattedFullDates.length][2];

                    System.out.println("Escolha qual algoritmo de ordenação deseja usar\n");
                    System.out.println("Digite 1 para Insertion Sort\nDigite 2 para Selection Sort\nDigite 3 para Bubble Sort");
                    System.out.println("Digite 4 para Quick Sort\nDigite 5 para Merge Sort\nDigite 6 para Heap Sort");
                    System.out.println("Digite 7 para Counting Sort\n\nDigite 0 para sair");
                    ordenaçãoDate = sc.nextInt();

                    switch (ordenaçãoDate) {

                        case 1:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_insertionSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Insertion Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]); // Adiciona ao TreeMap
                            }
                            System.out.println("\nO médio caso para o Insertion Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.insertionSort(dateMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_full_date_medioCaso.csv' foi criado");
                                
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_insertionSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Insertion Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.insertionSort(dateMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[dateMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            int j = datePRC.length-1;
                            for (int i = 0; i < dateMDC.length; i++) {
                                datePRC[i] = dateMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_attendance_full_date_melhorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_insertionSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Insertion Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[datePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.insertionSort(datePRC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_insertionSort_piorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 2:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_selectionSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Selection Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);        
                            }
                            System.out.println("\nO médio caso para o Selection Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.selectionSort(dateMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_selectionSort_medioCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_selectionSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Selection Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.selectionSort(dateMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[dateMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            int j = datePRC.length-1;
                            for (int i = 0; i < dateMDC.length; i++) {
                                datePRC[i] = dateMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_selectionSort_melhorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_selectionSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Selection Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[datePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.selectionSort(datePRC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_selectionSort_piorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 3:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_bubbleSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Bubble Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            System.out.println("\nO médio caso para o Bubble Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.bubbleSort(dateMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_bubbleSort_medioCaso.csv' foi criado");
                                    
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_bubbleSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Bubble Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.bubbleSort(dateMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[dateMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            int j = datePRC.length-1;
                            for (int i = 0; i < dateMDC.length; i++) {
                                datePRC[i] = dateMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_bubbleSort_melhorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_bubbleSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Bubble Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[datePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.bubbleSort(datePRC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_bubbleSort_piorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 4:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_quickSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Quick Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            System.out.println("\nO médio caso para o Quick Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickSort(dateMDC, 0, dateMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_quickSort_medioCaso.csv' foi criado");
                                    
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_quickSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Quick Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickSort(dateMDC, 0, dateMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[dateMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            int j = datePRC.length-1;
                            for (int i = 0; i < dateMDC.length; i++) {
                                datePRC[i] = dateMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_quickSort_melhorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_quickSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Quick Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[datePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickSort(datePRC, 0, datePRC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_quickSort_piorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_quickSortMedianaDe3_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Quick Sort Mediana de 3 serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            System.out.println("\nO médio caso para o Quick Sort Mediana de 3 está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickMedianaDe3(dateMDC, 0, dateMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_quickSortMedianaDe3_medioCaso.csv' foi criado");
                                    
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_quickSortMedianaDe3_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Quick Sort Mediana De 3 está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickMedianaDe3(dateMDC, 0, dateMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[dateMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            int j = datePRC.length-1;
                            for (int i = 0; i < dateMDC.length; i++) {
                                datePRC[i] = dateMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_quickSortMedianaDe3_melhorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_quickSortMedianaDe3_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Quick Sort Mediana De 3 está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[datePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.quickMedianaDe3(datePRC, 0, datePRC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_quickSortMedianaDe3_piorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 5:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_mergeSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Merge Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            System.out.println("\nO médio caso para o Merge Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.mergeSort(dateMDC, 0, dateMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_mergeSort_medioCaso.csv' foi criado");
                                    
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_mergeSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Merge Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.mergeSort(dateMDC, 0, dateMDC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[dateMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            int j = datePRC.length-1;
                            for (int i = 0; i < dateMDC.length; i++) {
                                datePRC[i] = dateMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_mergeSort_melhorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_mergeSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Merge Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[datePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.mergeSort(datePRC, 0, datePRC.length-1);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_mergeSort_piorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 6:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_heapSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Heap Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            System.out.println("\nO médio caso para o Heap Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.heapSort(dateMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_heapSort_medioCaso.csv' foi criado");
                                    
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_heapSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Heap Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.heapSort(dateMDC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[dateMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            int j = datePRC.length-1;
                            for (int i = 0; i < dateMDC.length; i++) {
                                datePRC[i] = dateMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_heapSort_melhorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_heapSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Heap Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[datePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.heapSort(datePRC);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_heapSort_piorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;

                        case 7:
                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_countingSort_medioCaso.csv"))) {
                            System.out.println("\nOs arquivos de ordenação do Counting Sort serão criados");
                            StringBuilder sb = new StringBuilder();

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[i]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            System.out.println("\nO médio caso para o Counting Sort está sendo ordenado");
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.countingSort(dateMDC, dateMDC.length);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_countingSort_medioCaso.csv' foi criado");
                                    
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_countingSort_melhorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();

                            System.out.println("\nO melhor caso para o Counting Sort está sendo ordenado");

                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.countingSort(dateMDC, dateMDC.length);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[dateMDC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            int j = datePRC.length-1;
                            for (int i = 0; i < dateMDC.length; i++) {
                                datePRC[i] = dateMDC[j]; j--;
                            }

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_countingSort_melhorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        try (PrintWriter venues = new PrintWriter(new File("matches_t2_full_date_countingSort_piorCaso.csv"))) {
                            StringBuilder sb = new StringBuilder();
                            System.out.println("\nO pior caso para o Counting Sort está sendo ordenado");

                            sb.append(lineIndices+"\n");
                            for (int i = 0; i < formattedFullDates.length; i++) {
                                sb.append(formattedFullDates[datePRC[i][1]]);
                                if (i != formattedFullDates.length-1) {
                                    sb.append("\n");
                                }
                                sortedMatchesByDate.put(ProcessadorArquivo.formarDataInt(fullDates[i]), formattedFullDates[i]);
                            }
                            long tempo = System.currentTimeMillis();
                            OrdenadorInt.countingSort(datePRC, datePRC.length);
                            System.out.println("\nA ordenação ocorreu em " + (System.currentTimeMillis() - tempo) + " milissegundos");

                            venues.write(sb.toString());
                            venues.close();
                            System.out.println("\nO arquivo 'matches_t2_full_date_countingSort_piorCaso.csv' foi criado");
                            
                        } catch (Exception e) {
                            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
                            return;
                        }
                        System.out.println("_________________________________________________________________________________");

                        break;
                        
                    }
                }
                break;
            }
        }
        sc.close();    
    }
}