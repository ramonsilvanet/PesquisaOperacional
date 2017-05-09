package net.ramonsilva.po;

import net.ramonsilva.po.solvers.Simplex;

import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by ramonsilva on 08/05/17.
 */
public class Programa {

    private final static int HEADER = 0;
    private final static int FUNCAO_OBJETIVO = 1;
    private final static int VALORES_INEQUACOES = 2;

    private int restricoes;
    private int varDecisao;
    private double[] funcaoObjetivo;
    private double[][] matrixRestricoes;
    private double[] membrosSegundo;

    public static void main(String[] args) {
        Programa p = new Programa();
        //p.lerDadosDeEntrada("arquivos/teste.txt");
        p.lerDadosDeEntrada("arquivos/entrada.txt");

        Simplex simplex = new Simplex(p.varDecisao, p.restricoes);
        double[][] sistema = new double[p.restricoes+1][p.varDecisao+1];

        for (int i = 0; i < p.restricoes; i++) {
            for (int j = 0; j < p.varDecisao; j++) {
                sistema[i][j] = p.matrixRestricoes[i][j];
            }

            sistema[i][p.varDecisao] = p.membrosSegundo[i];
        }

        for (int i = 0; i < p.varDecisao; i++) {
            sistema[p.restricoes][i] = 0 - p.funcaoObjetivo[i];
        }

        simplex.preencherTabela(sistema);

        System.out.println("--- Iniciando ---");
        simplex.mostrarTabela();
        System.out.println("-----------------");

        long inicio = System.currentTimeMillis();
        int solucao = Simplex.SOLUCAO_NAO_OTIMA;

        int i = 1;


        while (solucao == Simplex.SOLUCAO_NAO_OTIMA){
            solucao = simplex.computar();

            if(solucao == Simplex.SOLUCAO_OTIMA ) {
                System.out.println("Solução ótima encontrada.");
                System.out.println("");
                break;
            } else if(solucao ==  Simplex.SEM_SOLUCAO){
                System.out.println("Sistema sem solução viável.");
                System.out.println("");
                break;
            } else {
                System.out.println("Iteração " + i);
            }

            i++;
            simplex.mostrarTabela();
        }

        simplex.mostrarSolucao();

        long fim = System.currentTimeMillis();

        System.out.println("Tempo de execução : " + (fim - inicio) + " ms");

        System.out.println("FIM");
    }

    private void lerDadosDeEntrada(String nomeArquivo){
        try{
            FileReader fr = new FileReader(nomeArquivo);
            BufferedReader br = new BufferedReader(fr);

            String sCurrentLine = br.readLine();
            int linha = 0;

            while (sCurrentLine != null){
                if(linha  == HEADER){
                    String[] dados = sCurrentLine.split("\\s");
                    System.out.println(dados[0]);

                    restricoes = Integer.parseInt(dados[0]);
                    varDecisao = Integer.parseInt(dados[1]);

                    membrosSegundo = new double[restricoes];
                    matrixRestricoes = new double[restricoes][varDecisao];

                } else if(linha == FUNCAO_OBJETIVO) {
                    String[] dados = sCurrentLine.split("\\s");

                    funcaoObjetivo = new double[dados.length];

                    for(int i = 0; i < dados.length; i++){
                        funcaoObjetivo[i] = Double.parseDouble(dados[i]);
                    }
                } else if(linha == VALORES_INEQUACOES) {
                    String[] dados = sCurrentLine.split("\\s");
                    membrosSegundo = new double[dados.length];

                    for(int i = 0; i < dados.length; i++){
                        membrosSegundo[i] = Double.parseDouble(dados[i]);
                    }
                } else {
                    String[] dados = sCurrentLine.split("\\s");
                    for (int i = 0; i < dados.length; i++) {
                        matrixRestricoes[linha-3][i] = Double.parseDouble(dados[i]);
                    }

                }

                linha++;
                sCurrentLine = br.readLine();
            }

        } catch (Exception ex){
            System.out.println("Ocorreu um erro ao ler o arquivo. " + ex.getLocalizedMessage());
        }
    }

    private boolean verificarValorNaoNegativo(double valor){
        return valor > 0;
    }
}
