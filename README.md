# Projeto de Laboratório de Estruturas de Dados

## _Mikaelle dos Santos Oliveira_

### _2023.2_

---

#### Orientações sobre o projeto

- Dentro do arquivo zipado há uma pasta chamada _**ProjetoDeLEDA**_, dentro dessa pasta há o package do projeto, intitulado 'LigasEuropeias';
- O arquivo _**"matches.csv"**_ deverá ser colocado dentro da pasta _**ProjetoDeLEDA**_;
- Dentro do package, o arquivo java onde se encontra a classe principal é intitulada de 'Leda', é ela que deverá ser executada, os demais arquivos só têm funções que são utilizados na classe principal, tais como as funções dos algoritmos de ordenação;
- Há um _**menu**_ para a criação dos arquivos que serão ordenados.
  - Nesse menu, o usuário irá escolher a coluna (attendance, data, venue) que irá ordenar, após isso, terá que selecionar o algoritmo de ordenação que irá ser utilizado;
  - Quando selecionar o algoritmo de ordenação, os arquivos do médio, pior e melhor caso correspondentes à aquela coluna e algoritmo serão criados altomaticamente.
  O médio caso será a entrada normal do arquivo, o melhor caso será o arquivo já ordenado e o pior caso será o arquivo ordenado, porém em ordem decrescente.
  - Quando for selecionado a opção de gerar os arquivos com o algoritmo de ordenação **QuickSort**, serão criados os arquivos ordenados com o próprio **QuickSort** e o **QuickSortMedianaDeTres**;

---

##### Tabela de tempo (em milissegundos) da ordenação pelo local (venue)

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

##### Tabela de tempo (em milissegundos) da ordenação pelo público pagante (attendance)

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

##### Tabela de tempo (em milissegundos) da ordenação pela data

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

##

### Informações gerais do Projeto da 1º Unidade

**Análise comparativa de algoritmos de ordenação**

O primeiro projeto da disciplina de Estrutura de dados visa utilizar os dados de projetos voltados para Data science para estudarmos o desempenho dos algoritmos de ordenação. Os dados foram retirados do site Kaggle através da curadoria realizada pela equipe responsável pelas disciplinas de LEDA e EDA. Os projetos que temos são:

A. [Ligas Européias de Futebol]()

B. [Bolsa de Valores Bovespa 1994-2020]()

C. [Base de dados dos 10k passwords mais comuns utilizados]()

D. [Lost Angeles Metro Bike Share]()

ATENÇÃO: ESTÁ TERMINANTEMENTE PROIBIDO USAR ESTRUTURAS DE DADOS COMPLEXAS PARA O PROCESSAMENTO E ORDENAÇÃO. VOCÊ DEVE _**APENAS**_ USAR **ARRAYS**.

- Preparação do dataset > O dataset compreende um registro histórico de dados coletados em algum período do tempo.
  - Você deve a(s) planilha(s) localmente para poder processá-la(s) no seu código **Java**.
  - Você deve realizar duas transformações nas colunas de dados que serão baixados. Cada uma das transformações será detalhada de acordo com o projeto.
  - Após realizar cada uma das transformações, você deve gerar um arquivo resultante e renomeado conforme a transformação solicitada. O nome dos arquivos serão detalhados nas páginas dos respectivos projetos.
- Análise dos algoritmos de ordenação
  - Implemente e utilize todos os algoritmos de ordenação estudados (Selection Sort, Insertion Sort, Merge Sort, Quick Sort, QuickSort com Mediana de 3, counting, e HeapSort) para ordenar os registros de acordo com os seguintes parâmetros:
    - Os atributos pelos quais os dadtasets precisam ser ordenados estarão descritos na página de cada projeto
  - Cada arquivo de saída de ordenação deve ser gerado com base no método de ordenação e no elemento ordenado. Por exemplo, para o quick sort devem ser gerado 3 arquivos: qSort_ordena_reviews.csv e qSort_ordena_prices.csv, qSort_ordena_places.csv. Isso deve continuar para cada um dos métodos de ordenação e para cada um dos projetos.
  - Elabore uma tabela para comparar o tempo de execução dos algoritmos.

Opcional: Para a elaboração dos comparativos devem ser usados ferramentas de code profiling, como por exemplo o <https://visualvm.github.io/Links> to an external site.. Elabore gráficos mostrando o consumo de tempo e memória quando da execução do algoritmo.

Entrega deste milestone:

- Entrega de um relatório descrevendo os casos de testes, ambiente de execução completo e análise comparativa dos algoritmos. (Equivale a 40% da nota)
- Você deve subir o projeto no GitHub e mandar o link do projeto e o link de uma versão para eu baixar no meu computador e executar com todas as instruções (arquivo readme). (Equivale a 60% da nota)

##

### Ligas Européias de Futebol

Separamos uma fonte de dados dados dos jogos das 6 principais ligas europeias de futebol temporada 2021-2022, porém referente apenas ao ano de 2021. Para tanto usamos o projeto do [kaggle](https://www.kaggle.com/datasets/josephvm/european-club-football-dataset?select=matches.csv). Desta forma, temos dados de jogos das seguintes ligas:

| English Premier League| Spanish La Liga | German Bundesliga | Italian Serie A | Dutch Eredivisie | French Ligue 1 |
| :-------------------: | :-------------: | :---------------: | :-------------: | :--------------: | :------------: |
|   Game Data - 2001  |   Game Data - 2004    |    Game Data - 2002    |      Game Data - 2016      |      Game Data - 2018      |      Game Data - 2018      |
|   Aggregate Stats - 2002  |   Aggregate Stats - 2002    |    Aggregate Stats - 2002    |      Aggregate Stats - 2001      |      Aggregate Stats - 2001      |      Aggregate Stats - 2002      |
|   Tables - 2001  |   Tables - 2000    |    Tables - 2000    |      Tables - 2000      |      Tables - 2000      |      Tables - 2002      |

 O arquivo a ser considerado é o matches.csv

##### Transformações

1. Filtrar o arquivo original preservando apenas os seguintes campos:
  _'id', 'home', 'away', 'date', 'year', 'time (utc)', 'attendance', 'venue', 'league', 'home_score', 'away_score', 'home_goal_scorers', 'away_goal_scorers'_

- Gerar um arquivo chamado matches_T1.csv

2. Considerando, o arquivo gerado na T1 (matches_T1.csv), Criar um novo campo de data completa chamado full_date no formato DD/MM/AAAA de acordo com os dados das colunas date e year.

- Gerar um arquivo chamado matches_T2.csv

3. Considerando, o arquivo gerado na T2 (matches_T2.csv), filtrar apenas os jogos da liga inglesa "English Premier League" através do campo league.

- Gerar um arquivo chamado matches_F1.csv

4. Considerando, o arquivo gerado na F1 (matches_F1.csv), Filtrar os jogos que tiveram attendance superior a 20.000 pagantes.

- Gerar um arquivo chamado matches_F2.csv

##### Ordenações

**Para as ordenações, devemos considerar como entrada dos dados o arquivo resultando da transformação T2 entitulado matches_T2.csv**

Lembrando que para cada questão abaixo deve ser utilizado todos os algoritmos de ordenação recomendados na descrição do projeto, sendo assim você deve gerar um arquivo diferente para cada algoritmo de ordenação / caso.

Sobre os casos, para efeito de análise dos algoritmos deve-se trabalhar com 3 casos (melhor, médio e pior) para cada um dos algoritmos.

1. Ordenar o arquivo completo pelo nomes dos estádios (campo venue) em ordem alfabética
   1. Atenção existem alguns algoritmos onde essa ordenação pode não se aplicar.
   2. Deve-se gerar um arquivo para cada algoritmo de ordenação e o tipo de caso. Por exemplo, matches_t2_venues_insertionSort_medioCaso.csv, matches_t2_venues_insertionSort_piorCaso.csv, matches_t2_venues_insertionSort_melhorCaso.csv
2. Ordenar o arquivo matches_t2 pelo público pagante (campo attendance) do menor para o maior
   1. Deve-se gerar um arquivo para cada algoritmo de ordenação e o tipo de caso. Por exemplo, matches_t2_attendance_insertionSort_medioCaso.csv, matches_t2_attendance_insertionSort_piorCaso.csv, matches_t2_attendance_insertionSort_melhorCaso.csv
3. Ordenar o arquivo manches_t2 pela data completa (campo full_date) mais recente para o mais antigo.
   1. Deve-se gerar um arquivo para cada algoritmo de ordenação e o tipo de caso. Por exemplo, manches_t2_full_date_insertionSort_medioCaso.csv, manches_t2_full_date_insertionSort_piorCaso.csv, manches_t2_full_date_insertionSort_melhorCaso.csv

---

##

## Alteração do Projeto UT1 para Projeto UT2

O projeto da disciplina de Estrutura de dados visa utilizar os dados de projetos voltados para Data science para estudarmos o desempenho dos algoritmos de ordenação. Os dados foram retirados do site Kaggle através da curadoria realizada pela equipe responsável pelas disciplinas de LEDA e EDA. Os projetos que temos são:

A. [Ligas Européias de Futebol]()

B. [Bolsa de Valores Bovespa 1994-2020]()

C. [Base de dados dos 10k passwords mais comuns utilizados]()

D. [Lost Angeles Metro Bike Share]()

**ATENÇÃO: VOCÊS DEVEM APLICAR 3 ESTRUTURAS DE DADOS EM SEUS PROJETOS E JUSTIFICAR O MOTIVO DE ESTAREM APLICANDO.**

Entrega deste milestone:

- Entrega de um relatório descrevendo as alterações realizadas no projeto. (Equivale a 40% da nota)
- Você deve subir o projeto no GitHub e mandar o link do projeto e o link de uma versão para eu baixar no meu computador e executar com todas as instruções (arquivo readme). (Equivale a 60% da nota)
