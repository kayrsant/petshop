package br.csi.petshop.model.agendamento;

import br.csi.petshop.model.servico.Servico;
import jakarta.persistence.*;

import java.util.Optional;

@Entity
@Table(name = "servicos_agendamento")
public class ServicoAgendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="id_agendamento")
    private Agendamento agendamento;

    @ManyToOne
    @JoinColumn(name = "id_servico")
    private Servico servico;

    @Column(name = "preco", nullable = false)
    private double valor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Servico getServico() {
        return servico;
    }

    public Agendamento getAgendamento() {
        return agendamento;
    }

    public void setAgendamento(Agendamento agendamento) {
        this.agendamento = agendamento;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}
