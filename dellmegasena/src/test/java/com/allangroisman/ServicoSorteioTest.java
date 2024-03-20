package com.allangroisman;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.allangroisman.Dominio.Entidades.Aposta;
import com.allangroisman.Dominio.Entidades.Sorteio;
import com.allangroisman.Dominio.Funcoes.Sorteador;
import com.allangroisman.Dominio.Interface.IRepAposta;
import com.allangroisman.Dominio.Interface.IRepSorteio;
import com.allangroisman.Dominio.Servicos.ServicoSorteio;

public class ServicoSorteioTest {
    private ServicoSorteio testeServSorteio;
    Set<Integer> listaApostas;
    IRepAposta repApostas = new IRepAposta() {

        @Override
        public void save(Aposta aposta) {
            
        }

        @Override
        public List<Aposta> all() {
            ArrayList<Aposta> a = new ArrayList<>();
            return a;
        }

        @Override
        public Long count() {
            Long i = (long) 10;
            return i;
        }

    };
    IRepSorteio repSorteio = new IRepSorteio() {

        @Override
        public void save(Sorteio sorteio) {
           
        }

        @Override
        public List<Sorteio> all() {
            List<Sorteio> a = new ArrayList<>();
            return a;
        }

    };

    @BeforeEach
    public void initialize() {
        testeServSorteio = new ServicoSorteio(repApostas, repSorteio);
        testeServSorteio.novoSorteio();
        // adiciona de 2 a 5 numeros na lista
        listaApostas = new HashSet<>();
        for (int i = 2; i < 6; i++) {
            listaApostas.add(i);
        }
    };

    // TESTES CRIAR APOSTA UC
    // LIMITES INFERIOR E SUPERIOR DOS NUMEROS DA APOSTA
    @Test
    public void menorQueUm() {
        // tem um numero zero
        listaApostas.add(0);
        String resposta = testeServSorteio.criarAposta("Fulano", "039", listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Os números devem estar entre 1 e 50.");
    }

    @Test
    public void exatamenteUm() {
        // tem um numero 1
        listaApostas.add(1);
        String resposta = testeServSorteio.criarAposta("Fulano", "039", listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Aposta Criada com Sucesso. Aposta: 1010, Usuário: Fulano, CPF: 039, Números: [1, 2, 3, 4, 5].");
    }

    @Test
    public void exatamenteCinquenta() {
        // tem um numero 50
        listaApostas.add(50);
        String resposta = testeServSorteio.criarAposta("Fulano", "039", listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Aposta Criada com Sucesso. Aposta: 1010, Usuário: Fulano, CPF: 039, Números: [2, 50, 3, 4, 5].");
    }

    @Test
    public void maiorQueCinquenta() {
        // tem um numero 51
        listaApostas.add(51);
        String resposta = testeServSorteio.criarAposta("Fulano", "039", listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Os números devem estar entre 1 e 50.");
    }

    // QUANTIDADE INCORRETA DE NUMEROS NA APOSTA
    @Test
    public void menorQuantidadeNumeros() {
        String resposta = testeServSorteio.criarAposta("Fulano", "039", listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Quantidade de números incorreta.");
    }

    @Test
    public void maiorQuantidadeNumeros() {
        listaApostas.add(49);
        listaApostas.add(50);
        String resposta = testeServSorteio.criarAposta("Fulano", "039", listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Quantidade de números incorreta.");
    }

    // APOSTA REPETIDA PELO USUARIO
    @Test
    public void apostaRepetida() {
        listaApostas.add(50);
        String resposta = testeServSorteio.criarAposta("Fulano", "039", listaApostas);
        resposta = testeServSorteio.criarAposta("Fulano", "039", listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Aposta já realizada pelo usuário");
    }

    // FASE DE APOSTAS TERMINADA
    @Test
    public void sorteioFechado() {
        testeServSorteio.encerrarApostas();
        listaApostas.add(50);
        String resposta = testeServSorteio.criarAposta("Fulano", "039", listaApostas);
        Assertions.assertThat(resposta).isEqualTo("Fase de apostas já finalizada.");
    }

    // // APOSTA SURPRESINHA
    // @Test
    // public void apostaSurpresinha() {
    // String resposta = testeServSorteio.apostaSurpresinha("Fulano", "039");
    // Assertions.assertThat(resposta).isEqualTo("Aposta realizada com sucesso.");
    // }

    /////////////////////////////////////////////////////////////////////////////////////////////////
    // TESTES FASE SORTEIO
    // Sorteia apenas uma vez
    @Test
    public void primeiroSorteio() {
        testeServSorteio.encerrarApostas();
        int resposta = testeServSorteio.buscarSorteados().size();
        Assertions.assertThat(5 == resposta);
    }

    // APURADOR
    // Tentar apurar antes do primeiro sorteio e de fechar as apostas
    @Test
    public void apurarPrecocemente() {
        String resposta = testeServSorteio.apurarSorteio();
        Assertions.assertThat(resposta).isEqualTo("Ainda em fase de apostas.");
    }

    // Garante que nao ha vencedores
    @Test
    public void semVencedores() {
        testeServSorteio.encerrarApostas();
        String resposta = testeServSorteio.apurarSorteio();
        Assertions.assertThat(resposta).isEqualTo("Não há vencedores.");
    }

    // FORÇAR TER UM VENCEDOR ATRAVES DE MANIPULACAO
    @Test
    public void comVencedor() {
        Set<Integer> listaApostas2 = new HashSet<>();
        for (int i = 1; i < 6; i++) {
            listaApostas2.add(i);
        }
        testeServSorteio.criarAposta("A", "1", listaApostas2);
        testeServSorteio.encerrarApostas();
        testeServSorteio.manipular();
        testeServSorteio.apurarSorteio();

        Assertions.assertThat(testeServSorteio.buscarQtdVencedores() == 1);
    }
}
