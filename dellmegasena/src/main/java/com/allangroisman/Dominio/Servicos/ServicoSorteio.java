package com.allangroisman.Dominio.Servicos;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allangroisman.Dominio.Entidades.Aposta;
import com.allangroisman.Dominio.Entidades.Sorteio;
import com.allangroisman.Dominio.Funcoes.NumRelatorio;
import com.allangroisman.Dominio.Funcoes.Sorteador;
import com.allangroisman.Dominio.Interface.IRepAposta;
import com.allangroisman.Dominio.Interface.IRepSorteio;

@Service
public class ServicoSorteio {
    private IRepAposta repApostas;
    private IRepSorteio repSorteio;

    private static Sorteio sorteioAtual;

    @Autowired
    public ServicoSorteio(IRepAposta repApostas, IRepSorteio repSorteio) {
        this.repApostas = repApostas;
        this.repSorteio = repSorteio;
    }

    public String novoSorteio() {
        // Cria um novo sorteio
        sorteioAtual = new Sorteio();
        repSorteio.save(sorteioAtual);
        return "ID do Sorteio Criado: ";
    }

    // FUNÇÕES

    // FUNÇÃO QUE CRIA NOVAS
    // APOSTAS////////////////////////////////////////////////////////////////////////////////
    public String criarAposta(String nome, String cpf, Set<Integer> listaNumeros) {
        // Se a fase de apostas ja terminou
        if (!sorteioAtual.getAberto()) {

            return "Fase de apostas já finalizada.";
        }

        // se foi adicionado quantidade de numeros errada, ja retorna o erro
        if (listaNumeros.size() != 5) {
            return "Quantidade de números incorreta.";
        }
        // se algum dos numeros nao estiver entre 1 e50
        for (Integer integer : listaNumeros) {
            if (integer < 1 || integer > 50) {
                return "Os números devem estar entre 1 e 50.";
            }
        }

        // Busca a lista de apostas ja realizadas
        List<Aposta> listaApostas = sorteioAtual.getListaApostas();

        // Testa se esse usuario esta fazendo uma aposta repetida
        for (Aposta aposta : listaApostas) {
            if (aposta.getCpfUsuario().equals(cpf)) {
                if (aposta.getNumerosApostados().containsAll(listaNumeros)) {
                    return "Aposta já realizada pelo usuário";
                }
            }
        }

        // Caso a aposta seja inedita, cria uma
        // nova////////////////////////////////////////////////////////
        Long id = repApostas.count() + 1000;
        Aposta novaAposta = new Aposta(id, nome, cpf, listaNumeros);
        // informa qual seu sorteio
        novaAposta.setSorteio(sorteioAtual);
        // adiciona ela na listas do sorteio
        sorteioAtual.addAposta(novaAposta);
        // atualiza o sorteio no db
        repSorteio.save(sorteioAtual);
        return "Aposta Criada com Sucesso! " + novaAposta.toString();
    }

    // FUNÇÃO QUE ESCOLHE OS NUMEROS DA APOSTA
    // SURPRESINHA////////////////////////////////////////
    public String apostaSurpresinha(String nome, String cpf) {
        // Cria a lista de numeros
        Set<Integer> listaNumeros = new HashSet<>();
        // Adiciona 5 numeros aleatorios
        for (int i = 0; i < 5; i++) {
            listaNumeros.add(Sorteador.sortearNumero(listaNumeros));
        }
        // chama a funcao original de apostar com esta lista
        return criarAposta(nome, cpf, listaNumeros);
    }

    // FUNÇÃO QUE LISTA AS APOSTAS DO SORTEIO
    // ATUAL///////////////////////////////////////////////
    public List<String> listarApostas() {
        // pega todas as apostas do sorteio atual e converte para um array de string
        List<Aposta> listaApostas = sorteioAtual.getListaApostas();
        List<String> listaApostasString = listaApostas.stream()
                .map(Object::toString)
                .collect(Collectors.toCollection(ArrayList::new));
        if (listaApostas.isEmpty()) {
            listaApostasString.add("NÁO HÁ APOSTAS");
        }
        return listaApostasString;
    }

    // FUNÇÃO QUE ENCERRA A FASE DE APOSTAS E SORTEIA OS PRIMEIROS 5//
    // NUMEROS///////////////////////////

    public String encerrarApostas() {

        // se ja esta com o sorteio atual fechado, apenas informa os numeros sorteados
        if (!sorteioAtual.getAberto()) {
            return "Sorteio fechado  || Números sorteados: " + sorteioAtual.getNumerosSorteados();
        }

        // fecha o sorteio para novas apostas
        sorteioAtual.fechar();

        // Cria um set de numeros sorteados
        Set<Integer> numerosSorteados = new HashSet<>();

        // adiciona 5 numeros atraves do sorteador
        for (int i = 0; i < 5; i++) {
            numerosSorteados.add(Sorteador.sortearNumero(numerosSorteados));
        }
        sorteioAtual.setNumerosSorteados(numerosSorteados);
        // salva o contexto do sorteio
        repSorteio.save(sorteioAtual);
        // retorna os numeros sorteados
        return "Sorteio fechado  || Números sorteados: " + numerosSorteados.stream().sorted().toList();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // FASE DE APURAÇÃO
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // FUNÇÃO QUE APURA O SORTEIO REALIZANDO ATE 25 RODADAS

    public String apurarSorteio() {

        // se ainda ta na fase de apostas
        if (sorteioAtual.getAberto()) {

            return "Ainda em fase de apostas.";
        }

        // tenta procurar vencedores com os numeros atuais, se não há adiciona mais um
        // numero e tenta novamente ate 25x
        // int count = 0;
        while (!sorteioAtual.procurarVencedores() && sorteioAtual.getNumerosSorteados().size() < 30) {
            sorteioAtual.adicionarNumeroResultado(Sorteador.sortearNumero(sorteioAtual.getNumerosSorteados()));
            // count++;
        }

        // salva o contexto do sorteio
        repSorteio.save(sorteioAtual);

        // Se tiver vencedor
        if (sorteioAtual.getQtdVencedores() > 0) {
            return "Vencedor Encontrado!";
        }
        return "Não há vencedores.";
    }

    ////////////////////////////////////////////////////////////////////
    // FUNCOES QUE FORMAM O MENU DE APURACAO
    public List<Integer> buscarSorteados() {
        return sorteioAtual.getNumerosSorteados().stream().sorted().toList();
    }

    public int buscarQtdRodadas() {
        return sorteioAtual.getNumerosSorteados().size() - 5;
    }

    public int buscarQtdVencedores() {
        return sorteioAtual.getQtdVencedores();
    }

    //////////////////////////////////////////////////////////////////////
    // FUNCAO QUE GERA O RELATORIO

    public List<String> exibirRelatorio() {
        // Cria um mapa de 1 a 50
        Map<Integer, Integer> mapaDeRepeticoes = new HashMap<>();
        for (int i = 1; i < 51; i++) {
            mapaDeRepeticoes.put(i, 0);
        }

        // Pega a lista de apostas
        List<Aposta> apostas = sorteioAtual.getListaApostas();
        // pra cada aposta pega o conjunto de numeros
        for (Aposta aposta : apostas) {
            Set<Integer> numeros = aposta.getNumerosApostados();
            // pra cada numero, atualiza a contagem no mapa
            for (Integer numero : numeros) {
                mapaDeRepeticoes.put(numero, mapaDeRepeticoes.get(numero) + 1);
            }
        }

        ArrayList<NumRelatorio> listaPares = new ArrayList<>();
        for (int i = 1; i < 51; i++) {
            NumRelatorio novoPar = new NumRelatorio(i, mapaDeRepeticoes.get(i));
            listaPares.add(novoPar);
        }

        List<NumRelatorio> listaOrdenada = listaPares.stream()
                .sorted((nr1, nr2) -> Integer.compare(nr2.getRepeticoes(), nr1.getRepeticoes())) // Ordenação
                                                                                                 // decrescente pelo
                                                                                                 // número de repetições
                .collect(Collectors.toList());

        ArrayList<String> listaString = new ArrayList<>();
        listaString.add("Número  | Repetições");
        for (NumRelatorio numRelatorio : listaOrdenada) {
            if (numRelatorio.getRepeticoes() != 0) {
                listaString.add(numRelatorio.getNumero() + " | " + numRelatorio.getRepeticoes());
            }
        }

        return listaString;

    }

    //////////////////////////////////////////////////////////////////////
    // FUNCAO QUE BUSCA A LISTA DE APOSTAS VENCEDORAS DO SORTEIO
    public ArrayList<String> listarVencedores() {

        List<Aposta> apostas = sorteioAtual.getListaApostas();
        List<Aposta> apostasVencedoras = apostas.stream()
                .filter(Aposta::getVencedora)
                .sorted(Comparator.comparing(Aposta::getNomeUsuario))
                .collect(Collectors.toList());

        ArrayList<String> apostasVencedorasString = new ArrayList<>();
        for (Aposta aposta : apostasVencedoras) {
            apostasVencedorasString.add(aposta.toString());
        }
        if (apostasVencedorasString.isEmpty()) {
            apostasVencedorasString.add("Não há vencedores");
        }
        return apostasVencedorasString;
    }

    ///////////////////////////////////////////////////////////////////////
    // FUNÇÃO QUE MANIPULA O RESULTADO PARA QUE SEJA 1,2,3,4,5
    public void manipular() {
        sorteioAtual.manipularResultado();
        repSorteio.save(sorteioAtual);
    }

    // Função que finaliza e salva o Sorteio.
    public void encerrarSorteio() {
        repSorteio.save(sorteioAtual);
    }

    public Boolean receberPremio(String cpf) {
        if (sorteioAtual.getQtdVencedores() == 0) {
            return false;
        }

        List<Aposta> apostas = sorteioAtual.getListaApostas();
        for (Aposta aposta : apostas) {
            if (aposta.getVencedora() && aposta.getCpfUsuario().equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    public String buscarNome(String cpf) {
        List<Aposta> apostas = sorteioAtual.getListaApostas();
        for (Aposta aposta : apostas) {
            if (aposta.getCpfUsuario().equals(cpf)) {
                return aposta.getNomeUsuario();
            }
        }
        return "Nome não encontrado";
    }
}
