package net.ramonsilva.po;

import net.ramonsilva.po.solvers.Tableau;

public class Main {

    public static void main(String[] args) {
        /**
         *
         * Maximizar
         *      3x + 5y
         * S.A.
         *      x + y <= 4
         *      x + 3y <= 6
         *      x, y >=0
         * */


        double[][] sistemaLinear =  {
                { 1,   1,    1,  0,   4},
                { 1,   3,    0,  1,   6},
                {-3,  -5,    0,  0,   0}
        };

        Tableau simplex = new Tableau(4, 2);
        simplex.preencherTabela(sistemaLinear);

        System.out.println("--- Iniciando ---");
        simplex.mostrarTabela();

        int solucao = Tableau.SOLUCAO_NAO_OTIMA;

        while (solucao == Tableau.SOLUCAO_NAO_OTIMA){
            solucao = simplex.computar();

            if(solucao == Tableau.SEM_SOLUCAO){
                System.out.println("Sistema sem solução viável");
            }

            if(solucao == Tableau.SOLUCAO_OTIMA){
                System.out.println("Solução ótima");
                simplex.mostrarTabela();
            }
        }


    }
}
