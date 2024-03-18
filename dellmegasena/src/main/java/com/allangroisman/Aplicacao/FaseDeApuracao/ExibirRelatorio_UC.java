package com.allangroisman.Aplicacao.FaseDeApuracao;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class ExibirRelatorio_UC {

    @Autowired
    private ServicoSorteio servicoSorteio;

    public Map<Integer,Integer> run() {
        return servicoSorteio.exibirRelatorio();
    }

}
