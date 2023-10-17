package br.csi.petshop.model.agendamento;

import java.time.LocalDateTime;

public record DadosAgendamento(Long id, Long cliente, Long pet, Long funcionario, LocalDateTime data, double valor) {

    public DadosAgendamento(Agendamento agendamento) {
        this(agendamento.getId(), agendamento.getCliente().getId(), agendamento.getPet().getId(), agendamento.getFuncionario().getId(), agendamento.getData(), agendamento.getValor());
    }
}
