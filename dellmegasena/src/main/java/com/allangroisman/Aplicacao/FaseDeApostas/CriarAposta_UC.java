package com.allangroisman.Aplicacao.FaseDeApostas;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class CriarAposta_UC {

    @Autowired
    private ServicoSorteio servicoSorteio; 

    public String run(String nome, String cpf, Set<Integer> listaNumeros) {
        return servicoSorteio.criarAposta(nome, cpf, listaNumeros);
    }

}
