package com.allangroisman.Aplicacao.FaseDeEntrada;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class CriarSorteio_UC {

    @Autowired
    private ServicoSorteio servicoSorteio; // Serviço que trata de criar e gerenciar apostas

    public String run() {
        return servicoSorteio.novoSorteio();
    }

}
