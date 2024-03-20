package com.allangroisman.Aplicacao.FaseDeApuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class EncerrarSorteio_UC {

    @Autowired
    private ServicoSorteio servicoSorteio;
    public void run() {
        servicoSorteio.encerrarSorteio();
    }

}
