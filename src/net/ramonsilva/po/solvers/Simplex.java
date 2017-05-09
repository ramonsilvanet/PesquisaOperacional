package net.ramonsilva.po.solvers;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramonsilva on 01/05/17.
 */
public class Simplex {

    public static final int SOLUCAO_NAO_OTIMA = 0;
    public static final int SOLUCAO_OTIMA = 1;
    public static final int SEM_SOLUCAO = -1;

    private int linhas;
    private int colunas;

    private boolean solucaoInviavel = false;

    private double[][] tabela;

    List<Double> variaveisNaoBasicas;
    List<Double> variaveisBasicas;

    public Simplex(int numVariaveisDeDecisao, int numRestricoesTecnicas){
        linhas = numRestricoesTecnicas + 1;
        colunas = numVariaveisDeDecisao + 1;

        tabela = new double[linhas][];

        for(int i = 0;i < linhas; i++){
            tabela[i] = new double[colunas];
        }

        variaveisBasicas = new ArrayList<>();
        variaveisNaoBasicas = new ArrayList<>();
    }


    public void mostrarTabela(){
        for(int i = 0; i < linhas; i++){
            for(int j = 0; j < colunas; j++){
                System.out.print( String.format("%.2f", tabela[i][j]) + "\t" );
            }
            System.out.println();
        }

        System.out.println();
    }

    public void preencherTabela(double [][] dados){
        for(int i =0; i < tabela.length; i++){
            System.arraycopy(dados[i], 0, this.tabela[i],0, dados[i].length);
        }
    }


    /** Calcula a solução do Simplex
     *  esse método será chamado repetidas vezes dentro
     *  de um loop, até encontrar uma solução ótima
     *  ou detectar que não há solução viável
    */
    public int computar(){

        //Passo 1 :Checar a solução atual
        if(checarOtimilidade()){
           return SOLUCAO_OTIMA;
        }


        //Passo 2 : encotrar a coluna que será o pivot
        // variável não básica que entrará na base
        int colunaPivot = determinarVariavelQueEntraNaBase();

        /*System.out.println("");
        System.out.println("Coluna Pivot : " + colunaPivot);
        System.out.println("");*/

        // Passo 3
        // encontrar a varivél que sai da base
        double[] razoes = calcularRazoes(colunaPivot);
        if(solucaoInviavel){
            return SEM_SOLUCAO;
        }

        // Passo 4
        // formar a nova tabela para a prtóxima iteração
        int linhaPivot = menorDentreAs(razoes);
        atualizarTabela(linhaPivot, colunaPivot);

        return SOLUCAO_NAO_OTIMA;
    }


    private void atualizarTabela(int linhaPivot, int colunaPivot) {
        double valorPivot = tabela[linhaPivot][colunaPivot];
        double[] valoresDaLinhaPivot = new double[colunas];
        double[] valoresDaColunaPivot = new double[colunas];
        double[] novaLinha = new double[colunas];

        // Divide todas os valores da linha pivot por valores da coluna pivot
        //Obter entrada na linha pivô
        System.arraycopy(tabela[linhaPivot], 0 , valoresDaLinhaPivot, 0, colunas);

        // Obter os valores da coluna pivot
        for(int i = 0; i < linhas; i++){
            valoresDaColunaPivot[i] = tabela[i][colunaPivot];
        }

        // divide todos os valores da linha pivot pelo valor do pivot
        for(int i =0; i < colunas; i++){
            novaLinha[i] = valoresDaLinhaPivot[i] / valorPivot;
        }

        // Subtraindo de cada uma das outras linhas
        for(int i = 0; i < linhas; i++){
            if(i != linhaPivot){
                for(int j = 0; j < colunas; j++){
                    double c = valoresDaColunaPivot[i];
                    //System.out.println("[" +i + " " + j + "] " + c + " -> " + novaLinha[j]);
                    tabela[i][j] = tabela[i][j] - (c * novaLinha[j]);
                }
            }
        }

        //Substituindo a linha
        System.arraycopy(novaLinha, 0, tabela[linhaPivot], 0, novaLinha.length);
    }

    private int menorDentreAs(double[] razoes) {
        double menor = razoes[0];
        int indice = 0;

        for(int i = 1; i < razoes.length; i++){
            if(razoes[i] > 0){
                if(Double.compare(razoes[i], menor) < 0 || menor == 0){
                    menor = razoes[i];
                    indice = i;
                }
            }
        }

        return indice;
    }

    private double[] calcularRazoes(int pivot) {
        double[] valoresPositivos = new double[linhas];
        double razoes[] = new double[linhas];
        int contadorNegativos = 0;

        for(int i = 0; i < linhas; i++){
            if (tabela[i][pivot] > 0){
                valoresPositivos[i] = tabela[i][pivot];
            } else {
                valoresPositivos[i] = 0;
                contadorNegativos++;
            }
            //System.out.println(valoresPositivos[i]);
        }

        if(contadorNegativos == linhas){
            this.solucaoInviavel = true;
        } else {
            for(int i = 0; i < linhas; i++){
                double valor = valoresPositivos[i];
                if(valor > 0 && tabela[i][colunas - 1] != 0) {
                    razoes[i] = tabela[i][colunas - 1] / valor;
                }
            }
        }

        return razoes;
    }

    /**
     * Encontra qual variável não básica deve entrar para a base
     */
    private int determinarVariavelQueEntraNaBase() {
        double[] valores = new double[colunas];
        int indice = 0;
        int pos, contador = 0;

        for(pos = 0; pos < colunas-1; pos++){
            if(tabela[linhas-1][pos] < 0){
                contador++;
            }
        }

        if(contador > 1){
            for(int i = 0; i < colunas-1; i++) {
                valores[i] = Math.abs(tabela[linhas - 1][i]);
            }
            indice = maiorDentreOs(valores);
        } else {
            indice = contador - 1;
        }

        return indice;
    }

    private int maiorDentreOs(double[] valores) {
        double maior = valores[0];
        int indice = 0;

        for(int i = 1; i < valores.length; i++){
            if(Double.compare(valores[i], maior) > 0){
                maior = valores[i];
                indice = i;
            }
        }

        return indice;
    }

    /**
     * Verifica se a soluyção encontra nesta iteração é
     * a solução ótima
     */
    private boolean checarOtimilidade() {
        boolean solucaoOtima = false;

        int contador = 0;
        for(int i = 0; i < colunas - 1; i++){
            double valor = tabela[linhas-1][i];

            if(valor >= 0){
                contador++;
            }

            if(contador == colunas-1){
                solucaoOtima = true;
            }
        }

        return solucaoOtima;
    }
}
