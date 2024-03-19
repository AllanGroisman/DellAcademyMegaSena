package com.allangroisman.Aplicacao.FaseDeApuracao;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class BuscarSorteados_UC {

    @Autowired
    private ServicoSorteio servicoSorteio;
    public Set<Integer> run(){
        return servicoSorteio.buscarSorteados();
    }
}
