package net.ramonsilva.po;

import java.util.Scanner;

/**
 * Created by ramonsilva on 08/05/17.
 */
public class Programa {


    private int restricoes;
    private int varDecisao;

    public static void main(String[] args) {
        Programa p = new Programa();
        p.lerDadosDeEntrada();
    }

    private void lerDadosDeEntrada(){
        System.out.println("Informe a qunatidade de restrições : ");
        restricoes = lerValorInteiro();

        System.out.println("Informe a qunatidade variaveis de decisão : ");
        varDecisao = lerValorInteiro();

        System.out.println("Informe os custos da funcao objetivo : ");

        double[] custos = new double[restricoes];

        for (int i = 0; i < restricoes; i++) {
            System.out.println("Informe o custo c" + (i+1) + " :");
            custos[i] = lerValorReal();
        }


    }

    private boolean verificarValorNaoNegativo(double valor){
        return valor > 0;
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
