package com.allangroisman.Aplicacao;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class SortearResultado_UC {

    @Autowired
    private ServicoSorteio servicoSorteio;

    public Set<Integer> run() {
        return servicoSorteio.sortearResultado();
    }

}
