package com.allangroisman.Dominio.Funcoes;

import java.util.Random;
import java.util.Set;

public class Sorteador {
    // Método para sortear um número entre 1 e 50
    public static int sortearNumero(Set<Integer> jaSorteados) { // é static logo pode ser chamado
        Random random = new Random();
        // Gera um número entre 0 (inclusive) e 50 (exclusive) e adiciona 1 para ficar
        // entre 1 e 50

        int numeroSorteado = random.nextInt(50) + 1; //sorteia o numero entre 1 e 50

        while (jaSorteados.contains(numeroSorteado)) { //se o numero ja foi sorteado, sorteia dnv ate sair um diferente
            numeroSorteado = random.nextInt(50) + 1;
        }

        return numeroSorteado;

    }
}
