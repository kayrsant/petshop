package br.csi.petshop.model.agendamento;

import java.time.LocalDateTime;

public class DadosAgendamento {
    private Long id;
    private String cliente;
    private String pet;
    private String funcionario;
    private LocalDateTime data;
    private double valor;

    public DadosAgendamento(Long id, String cliente, String pet, String funcionario, LocalDateTime data, double valor) {
        this.id = id;
        this.cliente = cliente;
        this.pet = pet;
        this.funcionario = funcionario;
        this.data = data;
        this.valor = valor;
    }

    public DadosAgendamento(Agendamento agendamento) {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getPet() {
        return pet;
    }

    public void setPet(String pet) {
        this.pet = pet;
    }

    public String getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(String funcionario) {
        this.funcionario = funcionario;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public static DadosAgendamento criarDTO(Agendamento agendamento) {
        DadosAgendamento dto = new DadosAgendamento(agendamento);
        dto.setId(agendamento.getId());
        dto.setCliente(agendamento.getCliente().getNome());
        dto.setPet(agendamento.getPet().getNome());
        dto.setFuncionario(agendamento.getFuncionario().getNome());
        dto.setData(agendamento.getData());
        dto.setValor(agendamento.getValor());
        return dto;
    }


}
