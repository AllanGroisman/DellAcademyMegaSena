package com.allangroisman.Aplicacao;

import com.allangroisman.Dominio.Servicos.ServicoApuracao;

@Component
public class Apurar_UC {
    
    @AutoWired
    private ServicoApuracao servicoApuracao;

    public String run() {
        return servicoApuracao.apurarSorteio();
    }
}
