package com.allangroisman.Dominio.Servicos;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allangroisman.Dominio.Entidades.Aposta;
import com.allangroisman.Dominio.Entidades.Sorteio;
import com.allangroisman.Dominio.Funcoes.Sorteador;

@Service
public class ServicoSorteio {

    private static Sorteio sorteioAtual;

    @Autowired
    public ServicoSorteio() {
        
    }

    public String novoSorteio() {
        // Cria um novo sorteio
        sorteioAtual = new Sorteio();
        // Salva o sorteio no banco de dados
        //repSorteios.save(sorteioAtual);
        return "Sorteio Criado" + sorteioAtual.toString();
    }

    public String criarAposta(String nome, String cpf, Set<Integer> listaNumeros) {
        // se foi adicionado quantidade de numeros errada, ja retorna o erro
        if (listaNumeros.size() != 5) {
            return "Quantidade de números incorreta";
        }

        // Busca a lista de apostas ja realizadas
        ArrayList<Aposta> listaApostas = sorteioAtual.getListaApostas();

        // Testa se esse usuario esta fazendo uma aposta repetida
        for (Aposta aposta : listaApostas) {
            if (aposta.getCpfUsuario().equals(cpf)) {
                if (aposta.getNumerosApostados().containsAll(listaNumeros)) {
                    return "Aposta já realizada pelo usuário";
                }
            }
        }

        // Caso a aposta seja inédita, apenas cria uma nova e adiciona na lista de
        // apostas
        Aposta novaAposta = new Aposta(nome, cpf, listaNumeros);
        sorteioAtual.adicionarAposta(novaAposta);
        return "Aposta " + novaAposta.toString() + "realizada com sucesso.";
    }

    public ArrayList<String> listarAposta() {
        ArrayList<Aposta> listaApostas = sorteioAtual.getListaApostas();
        ArrayList<String> listaApostasString = listaApostas.stream()
                .map(Object::toString)
                .collect(Collectors.toCollection(ArrayList::new));
        return listaApostasString;
    }

    public Set<Integer> sortearResultado() {
        // Fecha o sorteio para novas apostas
        sorteioAtual.fechar();
        // Cria um set com os numeros sorteados
        Set<Integer> numerosSorteados = sorteioAtual.getNumerosSorteados();
        // adiciona 5 numeros atraves do sorteador
        for (int i = 0; i < 5; i++) {
            sorteioAtual.adicionarNumeroResultado(Sorteador.sortearNumero(numerosSorteados));
        }
        return numerosSorteados;
    }

    public ArrayList<String> apurarSorteio() {
        // procura vencedores
        int count = 0;
        while (!sorteioAtual.procurarVencedores() || count < 25) { // tenta procurar vencedores até 25 vezes
            sorteioAtual.adicionarNumeroResultado(Sorteador.sortearNumero(sorteioAtual.getNumerosSorteados()));
            count++;
        }

        // Transforma a saída para String genérica
        ArrayList<Aposta> listaVencedores = sorteioAtual.getListaVencedores();
        ArrayList<String> listaVencedoresString = listaVencedores.stream()
                .sorted(Comparator.comparing(aposta -> aposta.getNomeUsuario())) //coloca em ordem alfabetica
                .map(Object::toString) //transforma pra string pra so assim mandar como resposta.
                .collect(Collectors.toCollection(ArrayList::new));
        return listaVencedoresString;
    }

    public ArrayList<String> listarApostaUsuario(String cpf) {
        ArrayList<Aposta> listaApostas = sorteioAtual.getListaApostas();

        // Filtrar a lista de apostas e converter cada Aposta em uma String representativa
        ArrayList<String> apostasDoUsuario = listaApostas.stream()
                .filter(aposta -> aposta.getCpfUsuario().equals(cpf))
                .map(aposta -> aposta.toString()) // Substitua aposta.toString() pela representação de Aposta desejada
                .collect(Collectors.toCollection(ArrayList::new));

        return apostasDoUsuario;
    }

    public Map<Integer, Integer> exibirRelatorio() {
        // cria um mapa zerado com 1 a 50
        Map<Integer, Integer> relatorio = new HashMap<>();
        for (int i = 1; i < 51; i++) {
            relatorio.put(i, 0);

        }
        // busca a lista de apostas
        ArrayList<Aposta> listaApostas = sorteioAtual.getListaApostas();
        for (Aposta aposta : listaApostas) {
            // busca os numeros apostados em cada aposta
            Set<Integer> numerosApostados = aposta.getNumerosApostados();
            // pra cada numero apostado, atualiza a contagem dele no relatorio
            for (Integer numero : numerosApostados) {
                relatorio.put(numero, relatorio.get(numero) + 1);
            }
        }

        return relatorio;
    }
}
