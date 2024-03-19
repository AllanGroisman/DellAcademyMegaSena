package com.allangroisman;

import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

public class ServicoSorteioTest {
    private ServicoSorteio testeServSorteio;
    
    @BeforeEach
    public void initialize(){
        testeServSorteio = new ServicoSorteio();
        testeServSorteio.novoSorteio();


    }
    //TESTES CRIAR APOSTA UC
    @Test
    public void menorQueUm(){
        //tem um numero zero
        Set<Integer> listaApostas = new HashSet<>();
        for (int i = 0; i < 5; i++) {
            listaApostas.add(i);
        }
        String resposta = testeServSorteio.criarAposta("Fulano", "039",listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Os números devem estar entre 1 e 50.");
    }
    
}
