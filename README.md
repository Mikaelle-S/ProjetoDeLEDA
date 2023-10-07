# Projeto de Laboratório de Estruturas de Dados

###  _Mikaelle dos Santos Oliveira_
###### _2023.2_

---

##### Orientações sobre o projeto:

- Dentro do arquivo zipado há uma pasta chamada ***ProjetoDeLEDA***, dentro dessa pasta há o package do projeto, intitulado 'LigasEuropeias';
- O arquivo ***"matches.csv"*** deverá ser colocado dentro da pasta ***ProjetoDeLEDA***;
- Dentro do package, o arquivo java onde se encontra a classe principal é intitulada de 'Leda', é ela que deverá ser executada, os demais arquivos só têm funções que são utilizados na classe principal, tais como as funções dos algoritmos de ordenação;
- Há um ***menu*** para a criação dos arquivos que serão ordenados. 
  - Nesse menu, o usuário irá escolher a coluna (attendance, data, venue) que irá ordenar, após isso, terá que selecionar o algoritmo de ordenação que irá ser utilizado;
  - Quando selecionar o algoritmo de ordenação, os arquivos do médio, pior e melhor caso correspondentes à aquela coluna e algoritmo serão criados altomaticamente.
  O médio caso será a entrada normal do arquivo, o melhor caso será o arquivo já ordenado e o pior caso será o arquivo ordenado, porém em ordem decrescente.
  - Quando for selecionado a opção de gerar os arquivos com o algoritmo de ordenação **QuickSort**, serão criados os arquivos ordenados com o próprio **QuickSort** e o **QuickSortMedianaDeTres**;
---

##### Tabela de tempo (em milissegundos) da ordenação pelo local (venue):

|       Algoritmos       | Pior caso | Caso Médio | Melhor Caso |
| :--------------------: | :-------: | :--------: | :---------: |
|   **Insertion Sort**   |   24545   |   16508    |     10      |
|   **Selection Sort**   |   47756   |   30572    |    33988    |
|    **Bubble Sort**     |  50008    |   120838   |   136066    |
|     **Quick Sort**     |   3074    |    202     |     490     |
| **Quick Sort (Med 3)** |    142    |    156     |     105     |
|     **Merge Sort**     |    15     |     32     |     14      |
|     **Heap Sort**      |    42     |    81      |     39      |

Obs: O counting sort não funciona para ordenação de strings, pois ele usa o conteúdo a ser ordenado como índices de um array auxiliar para contar quantos números iguais à aquele índice há no array a ser ordenado e interar sobre aquela posição, e a partir disso a ordenação ser feita.


##### Tabela de tempo (em milissegundos) da ordenação pelo público pagante (attendance):

|       Algoritmos       | Pior caso | Caso Médio | Melhor Caso |
| :--------------------: | :-------: | :--------: | :---------: |
|   **Insertion Sort**   |    849    |    570     |      0      |
|   **Selection Sort**   |    602    |    366     |     426     |
|    **Bubble Sort**     |   1395    |    1625    |      1      |
|     **Quick Sort**     |    8      |     65     |      41     |
| **Quick Sort (Med 3)** |    101    |     47     |      74     |
|     **Merge Sort**     |    24     |     33     |      18     |
|     **Heap Sort**      |    0      |     6      |      3      |
|    **Couting Sort**    |    11     |     7      |      9      |


##### Tabela de tempo (em milissegundos) da ordenação pela data:

|       Algoritmos       | Pior caso | Caso Médio | Melhor Caso |
| :--------------------: | :-------: | :--------: | :---------: |
|   **Insertion Sort**   |   758     |    247     |      0      |
|   **Selection Sort**   |    387    |    399     |     477     |
|    **Bubble Sort**     |   1626    |    1292    |      1      |
|     **Quick Sort**     |    2      |     3      |      0      |
| **Quick Sort (Med 3)** |    4      |     0      |      16     |
|     **Merge Sort**     |    22     |     22     |     14      |
|     **Heap Sort**      |    0      |     17     |      0      |
|    **Couting Sort**    |    63     |     35     |     81      |





---

