package com.allangroisman.Aplicacao.Historico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoHistorico;


@Component
public class HistoricoApostas_UC {

    @Autowired
    private ServicoHistorico servicoHistorico;
    public List<String> run() {
        return servicoHistorico.historicoApostas();
    }

}
