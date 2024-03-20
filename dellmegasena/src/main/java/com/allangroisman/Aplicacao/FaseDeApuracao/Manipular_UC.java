package com.allangroisman.Aplicacao.FaseDeApuracao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class Manipular_UC {
    @Autowired
    private ServicoSorteio servicoSorteio; //Serviço que trata de criar e gerenciar apostas
    
    public void run() {
        servicoSorteio.manipular();
    }
}
