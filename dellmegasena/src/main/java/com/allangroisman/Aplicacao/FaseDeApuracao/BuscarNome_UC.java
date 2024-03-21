package com.allangroisman.Aplicacao.FaseDeApuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class BuscarNome_UC {
    @Autowired
    private ServicoSorteio servicoSorteio;

    public String run(String cpf) {
        return servicoSorteio.buscarNome(cpf);
    }
}
