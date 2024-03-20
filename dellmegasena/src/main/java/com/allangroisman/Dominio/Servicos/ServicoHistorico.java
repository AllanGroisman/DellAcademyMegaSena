package com.allangroisman.Dominio.Servicos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.allangroisman.Dominio.Entidades.Aposta;
import com.allangroisman.Dominio.Entidades.Sorteio;
import com.allangroisman.Dominio.Interface.IRepAposta;
import com.allangroisman.Dominio.Interface.IRepSorteio;

@Service
public class ServicoHistorico {
    private IRepAposta repApostas;
    private IRepSorteio repSorteio;

    @Autowired
    public ServicoHistorico(IRepAposta repApostas, IRepSorteio repSorteio) {
        this.repApostas = repApostas;
        this.repSorteio = repSorteio;
    }

    public List<String> historicoSorteios() {
        List<Sorteio> historico = repSorteio.all();
        ArrayList<String> listaSorteios = new ArrayList<>();
        for (Sorteio sorteio : historico) {
            listaSorteios.add(sorteio.toString());
        }
        return listaSorteios;
    }

    public List<String> historicoApostas() {
        List<Aposta> historico = repApostas.all();
        ArrayList<String> listaAposta = new ArrayList<>();
        for (Aposta aposta : historico) {
            listaAposta.add(aposta.toString());
        }
        return listaAposta;
    }
}
