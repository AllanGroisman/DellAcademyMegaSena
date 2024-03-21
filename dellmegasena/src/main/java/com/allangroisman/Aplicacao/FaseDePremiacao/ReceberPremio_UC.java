package com.allangroisman.Aplicacao.FaseDePremiacao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class ReceberPremio_UC {
     @Autowired
    private ServicoSorteio servicoSorteio;

    public Boolean run(String cpf) {
        return servicoSorteio.receberPremio(cpf);
    }
}
