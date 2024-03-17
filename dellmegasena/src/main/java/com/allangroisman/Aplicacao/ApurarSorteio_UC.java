package com.allangroisman.Aplicacao;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;
@Component
public class ApurarSorteio_UC {

     @Autowired
    private ServicoSorteio servicoSorteio; //Serviço que trata de criar e gerenciar apostas
    
    public ArrayList<String> run() {
        return servicoSorteio.apurarSorteio();
    }
}
