package net.ramonsilva.po;

import net.ramonsilva.po.solvers.Simplex;

import java.util.Scanner;

/**
 * Created by ramonsilva on 08/05/17.
 */
public class Programa {


    private int restricoes;
    private int qtdVariaveis;
    private double[] custos;
    private double[] segundoMembro;
    private double[][] matriz;

    public static void main(String[] args) {
        Programa p = new Programa();
        p.lerDadosDeEntrada();

        double[][] sistemaLinear = p.montarSistema();

        Simplex simplex = new Simplex(p.qtdVariaveis, p.restricoes);
        simplex.preencherTabela(sistemaLinear);



    }

    private double[][] montarSistema() {
        double[][] sistema = new double[restricoes+1][qtdVariaveis+1];

        for (int i = 0; i < matriz.length-1; i++) {
            for (int j = 0; j < matriz[0].length-1; j++) {
                sistema[i][j] = matriz[i][j];
            }

            sistema[i][sistema.length] = segundoMembro[i];
        }

        for (int i = 0; i < qtdVariaveis; i++) {
            sistema[sistema.length][i] = custos[i];
        }
        
        return sistema;
    }

    private void lerDadosDeEntrada(){

        System.out.print("Informe a quantidade de restrições : ");
        restricoes = lerValorInteiro();

        System.out.print("Informe a qunatidade variaveis: ");
        qtdVariaveis = lerValorInteiro();

        System.out.println("Informe os custos da funcao objetivo : ");

        this.custos = new double[qtdVariaveis];

        for (int i = 0; i < qtdVariaveis; i++) {
            System.out.println("Informe o custo c" + (i+1) + " : ");
            custos[i] = lerValorReal();
        }

        System.out.println("Informe os valores bi do segundo membro : ");

        this.segundoMembro = new double[restricoes];

        for (int i = 0; i < restricoes; i++) {
            System.out.println("Informe valor b" + (i+1) + ": ");
            this.segundoMembro[i] = lerValorReal();
        }

        System.out.println("Informe os coeficientes da matriz de restrição : ");

        this.matriz = new double[restricoes][qtdVariaveis];

        for (int i = 0; i < restricoes; i++) {
            System.out.println("Linha "+ (i+1) + ": ");

            for (int j = 0; j < qtdVariaveis; j++) {
                System.out.println("Informe valor da coluna" + (j+1) + ": ");
                this.matriz[i][j] = lerValorReal();
            }
        }

    }

    private boolean verificarValorNaoNegativo(double valor){
        return valor >= 0;
    }

    private int lerValorInteiro(){

        try {
            Scanner keyboard = new Scanner(System.in);
            int valor = keyboard.nextInt();

            while (!verificarValorNaoNegativo(valor)){
                System.out.println("Valor inválido");
                valor = keyboard.nextInt();
            }

            return valor;
        }
        catch (java.util.InputMismatchException ex){
            System.out.println("Valor inválido");
            return lerValorInteiro();
        }
    }

    private double lerValorReal(){
        try {
            Scanner keyboard = new Scanner(System.in);
            double valor = keyboard.nextDouble();

            while (!verificarValorNaoNegativo(valor)){
                System.out.println("Valor inválido");
                valor = keyboard.nextDouble();
            }

            return valor;
        }
        catch (java.util.InputMismatchException ex){
            System.out.println("Valor inválido");
            return lerValorReal();
        }
    }
}
