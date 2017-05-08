package net.ramonsilva.po;

import net.ramonsilva.po.solvers.Simplex;

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

        Simplex simplex = new Simplex(4, 2);
        simplex.preencherTabela(sistemaLinear);

        System.out.println("--- Iniciando ---");
        simplex.mostrarTabela();

        int solucao = Simplex.SOLUCAO_NAO_OTIMA;

        while (solucao == Simplex.SOLUCAO_NAO_OTIMA){
            solucao = simplex.computar();


            if(solucao == Simplex.SOLUCAO_OTIMA ) {
                System.out.println("Solução ótima [SOLUÇÃO LIMITIDA]");
                System.out.println("");
                break;
            } else if(solucao ==  Simplex.SEM_SOLUCAO){
                System.out.println("Sistema sem solução viável. [SOLUÇÃO ILIMITADA]");
                System.out.println("");
                break;
            } else {
                System.out.println("Solução não ótima");
                System.out.println("");
            }

            simplex.mostrarTabela();
        }


        simplex.mostrarTabela();

    }


}
