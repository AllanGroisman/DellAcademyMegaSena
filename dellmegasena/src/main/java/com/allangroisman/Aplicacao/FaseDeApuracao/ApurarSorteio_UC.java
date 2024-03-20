package com.allangroisman.Aplicacao.FaseDeApuracao;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class ApurarSorteio_UC {

     @Autowired
    private ServicoSorteio servicoSorteio; //Serviço que trata de criar e gerenciar apostas
    
    public String run() {
        //return new ArrayList<>();
        return servicoSorteio.apurarSorteio();
    }
    
}
