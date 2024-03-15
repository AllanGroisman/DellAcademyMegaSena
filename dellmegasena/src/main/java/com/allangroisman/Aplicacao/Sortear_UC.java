package com.allangroisman.Aplicacao;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class Sortear_UC {
    
    @AutoWired
    private ServicoSorteio servicoSorteio;

    public String run() {
        return servicoSorteio.criarSorteio();
    }
}
