package com.allangroisman.Aplicacao;

import com.allangroisman.Dominio.Servicos.ServicoAposta;

@Component
public class Apostar_UC {

    @AutoWired
    private ServicoAposta servicoAposta;

    public String run(){
        return servicoAposta.novaAposta();
    }
}
