package br.csi.petshop.model.agendamento;

import br.csi.petshop.model.cliente.Cliente;
import br.csi.petshop.model.funcionario.Funcionario;
import br.csi.petshop.model.pet.Pet;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "agendamentos")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Agendamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "id_pet", nullable = false)
    private Pet pet;

    @ManyToOne
    @JoinColumn(name = "id_funcionario", nullable = false)
    private Funcionario funcionario;

    @Column(name = "data", nullable = false)
    private LocalDateTime data;

    @Column(name = "valor", nullable = false)
    private double valor;

    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProdutoAgendamento> produtos = new ArrayList<>();

    @OneToMany(mappedBy = "agendamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ServicoAgendamento> servicos = new ArrayList<>();

    public DadosAgendamento criarDTO() {
        return DadosAgendamento.criarDTO(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Pet getPet() {
        return pet;
    }

    public void setPet(Pet pet) {
        this.pet = pet;
    }

    public Funcionario getFuncionario() {
        return funcionario;
    }

    public void setFuncionario(Funcionario funcionario) {
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

    public void setValor(BigDecimal valorTotal) {
        this.valor = valor;
    }

    public List<ProdutoAgendamento> getProdutos() {
        return produtos;
    }

    public void setProdutosAgendamento(List<ProdutoAgendamento> produtos) {
        this.produtos = produtos;
    }

    public List<ServicoAgendamento> getServicos() {
        return servicos;
    }

    public void setServicosAgendamento(List<ServicoAgendamento> servicos) {
        this.servicos = servicos;
    }


}
