package com.allangroisman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.allangroisman.Dominio.Entidades.Aposta;
import com.allangroisman.Dominio.Funcoes.Sorteador;
import com.allangroisman.Dominio.Servicos.ServicoSorteio;

public class ServicoSorteioTest {
    private ServicoSorteio testeServSorteio;
    Set<Integer> listaApostas;
    
    @BeforeEach
    public void initialize(){
        testeServSorteio = new ServicoSorteio();
        testeServSorteio.novoSorteio();
        //adiciona de 2 a 5 numeros na lista
        listaApostas = new HashSet<>();
        for (int i = 2; i < 6; i++) {
            listaApostas.add(i);
        }
    }

    //TESTES CRIAR APOSTA UC
    //LIMITES INFERIOR E SUPERIOR DOS NUMEROS DA APOSTA
    @Test
    public void menorQueUm(){
        //tem um numero zero
        listaApostas.add(0);
        String resposta = testeServSorteio.criarAposta("Fulano", "039",listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Os números devem estar entre 1 e 50.");
    }
    @Test
    public void exatamenteUm(){
        //tem um numero 1
        listaApostas.add(1);
        String resposta = testeServSorteio.criarAposta("Fulano", "039",listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Aposta realizada com sucesso.");
    }
    @Test
    public void exatamenteCinquenta(){
        //tem um numero 50
        listaApostas.add(50);
        String resposta = testeServSorteio.criarAposta("Fulano", "039",listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Aposta realizada com sucesso.");
    }
    @Test
    public void maiorQueCinquenta(){
        //tem um numero 51
        listaApostas.add(51);
        String resposta = testeServSorteio.criarAposta("Fulano", "039",listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Os números devem estar entre 1 e 50.");
    }

    //QUANTIDADE INCORRETA DE NUMEROS NA APOSTA
    @Test
    public void menorQuantidadeNumeros(){        
        String resposta = testeServSorteio.criarAposta("Fulano", "039",listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Quantidade de números incorreta.");
    }
    @Test
    public void maiorQuantidadeNumeros(){  
        listaApostas.add(49);
        listaApostas.add(50);      
        String resposta = testeServSorteio.criarAposta("Fulano", "039",listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Quantidade de números incorreta.");
    }
    //APOSTA REPETIDA PELO USUARIO
    @Test
    public void apostaRepetida(){  
        listaApostas.add(50);      
        String resposta = testeServSorteio.criarAposta("Fulano", "039",listaApostas);
        resposta = testeServSorteio.criarAposta("Fulano", "039",listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Aposta já realizada pelo usuário");
    }

    //FASE DE APOSTAS TERMINADA
    @Test
    public void sorteioFechado(){  
        testeServSorteio.sortearResultado();
        listaApostas.add(50);      
        String resposta = testeServSorteio.criarAposta("Fulano", "039",listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Fase de apostas já finalizada.");
    }

    //TESTES FASE SORTEIO
    //Sorteia apenas uma vez
    @Test
    public void primeiroSorteio(){  
        testeServSorteio.sortearResultado();      
        int resposta = testeServSorteio.sortearResultado().size();
        Assertions.assertThat(5 == resposta);
    }
    //Sorteia duas vezes e deve-se manter igual
    @Test
    public void segundoSorteio(){  
        testeServSorteio.sortearResultado();      
        Set<Integer> resposta = testeServSorteio.sortearResultado();
        Set<Integer> resposta2 = testeServSorteio.sortearResultado();
        Assertions.assertThat(resposta).isEqualTo(resposta2);
    }
    
    //APURADOR
    //Tentar apurar antes do primeiro sorteio e de fechar as apostas
    @Test
    public void apurarPrecocemente(){  
        String resposta = testeServSorteio.apurarSorteio().get(0); 
        Assertions.assertThat(resposta).isEqualTo("AINDA EM FASE DE APOSTAS");
    }
    //
    @Test
    public void semVencedores(){ 
        testeServSorteio.sortearResultado(); 
        String resposta = testeServSorteio.apurarSorteio().get(0); 
        Assertions.assertThat(resposta).isEqualTo("NÃO HÁ VENCEDORES!");
    }
    



}
