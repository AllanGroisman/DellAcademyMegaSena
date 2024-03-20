package com.allangroisman.Aplicacao.FaseDeApostas;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class ApostaSurpresinha_UC {
    @Autowired
    private ServicoSorteio servicoSorteio; // Serviço que trata de criar e gerenciar apostas

    public String run(String nome, String cpf) {
       
       return servicoSorteio.apostaSurpresinha(nome, cpf);
    }
}
