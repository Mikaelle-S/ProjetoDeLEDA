package LigasEuropeias;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProcessadorArquivo {
	/**
     * Corrige vírgulas dentro de aspas em uma dada linha, substituindo-as por asteriscos.
     * @param linhaEntrada A linha de entrada do arquivo.
     * @return Um array de valores separados por vírgulas, com vírgulas dentro das aspas corrigidas.
     */
	public static String[] corrigirVirgulas(String line) {

        if (line == null) {
            return new String[0];  // retorna um array vazio
        }

        char[] lineChar = line.toCharArray(); 
        int[] aspasIndex = new int[10]; 
        int aux = 0;


        for (int i = 0; i < lineChar.length; i++) {
    
            if (lineChar[i] == '\"') {
                aspasIndex[aux] = i;
                aux++;
            }
        }
        /* depois disso esse laço percorre o array aspasIndex */

        for (int i = 0; i < aux; i++) {

            if (i % 2 == 0) {

                // o laço vai percorrer o índice da primeira aspas do par até a segunda
                for (int j = aspasIndex[i]; j < aspasIndex[i+1]; j++) {

                    // se ele acha uma vírgula nesse intervalo, troca por um asterisco
                    if (lineChar[j] == ',') {
                        lineChar[j] = '*';
                    }
                }
            }
        }
        line = new String(lineChar);

        // com o .split separamos todas as posições da linha do arquivo em um array auxiliar
        String[] lineArray = line.split(",");

        // após ter todas posições corretas no array, trocamos de volta os asteríscos por vírgula
        for (int i = 0; i < lineArray.length; i++) {
            lineArray[i] = lineArray[i].replace('*', ',');
        }

        return lineArray;

    }

	public static long contarLinhas (String pathFile) {

        long count = 0;

        try {
            Path file = Paths.get(pathFile);
      
            count = Files.lines(file).count();
      
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
        }

        return count;
        
    }

    public static int contarLinhasPL (String path) {
      
        int count = 0;
  
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
          String line = br.readLine();
  
          while (line != null) {
  
            if (line.contains("Premier League")) {
              count++;
            }
            line = br.readLine();
          }
        } catch (Exception e) {
          System.out.println("Erro: " + e.getMessage() + "\nOcorreu um erro com o arquivo");
        }
        return count;
    }

    public static int contarLinhasMaisQue20K (String[] lines) {

        String[][] matrix = new String[lines.length][];
        int count = 0;

        for (int i = 0; i < lines.length; i++) {
            matrix[i] = corrigirVirgulas(lines[i]);
            matrix[i][6] = matrix[i][6].replaceAll("\"", "");
            matrix[i][6] = matrix[i][6].replaceAll(",", "");
            if ((matrix[i][6] != "") && (Integer.parseInt(matrix[i][6]) > 20000)) {
                count++;
            }
        }
        return count;
    }

    public static int formatarInteiros (String line, int index) {
        
        String[] lineArr = corrigirVirgulas(line);
        String number = lineArr[index];
        int fNumber;
        number = number.replaceAll("\"", "");
        number = number.replaceAll("/", "");
        number = number.replaceAll(",", "");
        if (number != "") {
            fNumber = Integer.parseInt(number);
        } else {
            return 0;
        }
        return fNumber;
    }
    
	public static String formarDataCompleta(String[] date) {

        String[] dateArr = new String[3];
        StringBuilder strb1 = new StringBuilder();
        StringBuilder strb2 = new StringBuilder();

        strb1.append(date[3]+" "); // adiciona o mês e o dia ao StringBuilder
        strb1.append(date[4]+" "); // adiciona o ano ao StringBuilder

        // junta tudo numa só String
        String dateStr = strb1.toString();
        
        // elimia caracteres indesejados da String
        dateStr = dateStr.replace("\"", "");
        dateStr = dateStr.replace(",", "");

        // divide dia, mês e ano em cada posição de um array
        dateArr = dateStr.split(" ");
        strb2.append(dateArr[2]+"/"); // adiciona o dia mais uma barra ao StringBuilder

        // switch para formatar o mês que estava em forma escrita para forma numérica
        switch (dateArr[1]) {
            // quando encontrado o mês, o valor numérico é adicionado ao StringBuilder
            case "January":
                strb2.append("01"); break;
            case "February":
                strb2.append("02"); break;
            case "March":
                strb2.append("03"); break;
            case "April":
                strb2.append("04"); break;
            case "May":
                strb2.append("05"); break;
            case "June":
                strb2.append("06"); break;
            case "July":
                strb2.append("07"); break;
            case "August": 
                strb2.append("08"); break;
            case "September":
                strb2.append("09"); break;
            case "October":
                strb2.append("10"); break;
            case "November":
                strb2.append("11"); break;
            case "December":
                strb2.append("12"); break;
            default:
                // caso falte o mês nos dados, apenas um valor em branco será colocado no StringBuilder
                strb2.append(" ");
        }
        strb2.append("/"+dateArr[3]); // adiciona o ano ao StringBuilder
        String fDateStr = strb2.toString(); // salva o conteúdo do StringBuilder numa string

        return fDateStr; // retorna a data formatada para ser gravada no array
    }

    public static int formarDataInt (String date) {

        String[] dateArr = date.split("/");
        StringBuilder sb = new StringBuilder();

        for (int i = dateArr.length-1; i >= 0; i--) {
            sb.append(dateArr[i]);
        }
        int dateInt = Integer.parseInt(sb.toString());

        return dateInt;
    }
}
