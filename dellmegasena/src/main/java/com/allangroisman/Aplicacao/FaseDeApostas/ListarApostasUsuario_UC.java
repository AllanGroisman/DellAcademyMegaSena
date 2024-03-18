package com.allangroisman.Aplicacao.FaseDeApostas;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.allangroisman.Dominio.Servicos.ServicoSorteio;

@Component
public class ListarApostasUsuario_UC {

    @Autowired
    private ServicoSorteio servicoSorteio; // Serviço que trata de criar e gerenciar apostas

    public ArrayList<String> run(String cpf) {
        return servicoSorteio.listarApostaUsuario(cpf);
    }

}