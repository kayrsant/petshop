package br.csi.petshop.service;

import br.csi.petshop.model.agendamento.*;
import br.csi.petshop.model.cliente.Cliente;
import br.csi.petshop.model.cliente.ClienteRepository;
import br.csi.petshop.model.funcionario.Funcionario;
import br.csi.petshop.model.funcionario.FuncionarioRepository;
import br.csi.petshop.model.produto.Produto;
import br.csi.petshop.model.produto.ProdutoRepository;
import br.csi.petshop.model.servico.Servico;
import br.csi.petshop.model.servico.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    private final AgendamentoRepository agendamentoRepository;
    @Autowired
    private final ClienteRepository clienteRepository;

    @Autowired
    private final FuncionarioRepository funcionarioRepository;

    @Autowired
    private final ServicoRepository servicoRepository;

    @Autowired
    private final ProdutoRepository produtoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository, ClienteRepository clienteRepository, FuncionarioRepository funcionarioRepository, ServicoRepository servicoRepository, ProdutoRepository produtoRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
        this.servicoRepository = servicoRepository;
        this.produtoRepository = produtoRepository;
    }

    public ResponseEntity<?> cadastrar(Agendamento agendamento) {
        Agendamento agendamentoCriado = new Agendamento();
        Optional<Funcionario> funcionario = this.funcionarioRepository.findById(agendamento.getFuncionario().getId());

        if(funcionario.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Funcionário não encontrado");
        }

        Optional<Cliente> cliente = this.clienteRepository.findById(agendamento.getCliente().getId());

        if(cliente.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente não encontrado");
        }

        double valorTotal = 0;

        agendamentoCriado.setData(agendamento.getData());
        agendamentoCriado.setCliente(cliente.get());
        agendamentoCriado.setPet(agendamento.getPet());
        agendamentoCriado.setFuncionario(funcionario.get());
        List<ProdutoAgendamento> produtos = agendamento.getProdutosAgendamento();
        List<ServicoAgendamento> servicos = agendamento.getServicosAgendamento();
        agendamentoCriado.setProdutosAgendamento(produtos);
        agendamentoCriado.setServicosAgendamento(servicos);

        Long idProduto = produtos.get(0).getId();
        Optional<Produto> produtoOptional = produtoRepository.findById(idProduto);
        Produto produto = produtoOptional.get();
        valorTotal += produto.getPreco() * produtos.get(0).getQuantidade();

        Long idServico = servicos.get(0).getId();
        Optional<Servico> servicoOptional = servicoRepository.findById(idServico);
        Servico servico = servicoOptional.get();
        valorTotal += servico.getPreco();

        agendamentoCriado.setValor(valorTotal);
        agendamentoRepository.save(agendamentoCriado);
        return ResponseEntity.ok(new DadosAgendamento(agendamentoCriado.getId(), agendamentoCriado.getCliente().getId(), agendamentoCriado.getPet().getId(), agendamentoCriado.getFuncionario().getId(), agendamentoCriado.getData(), agendamentoCriado.getValor()));
    }

    public ResponseEntity<?> deletar(Long id){
        Agendamento agendamento = this.agendamentoRepository.findById(id).orElse(null);

        if(agendamento == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamento não encontrado.");
        }
        this.agendamentoRepository.delete(agendamento);
        return ResponseEntity.status(HttpStatus.OK).body("Agendamento " + agendamento.getId() + " deletado com sucesso.");
    }

    public DadosAgendamento findAgendamento(Long id) {
        Agendamento agendamento = agendamentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Agendamento não encontrado"));

        return convertToDadosAgendamento(agendamento);
    }

    public List<DadosAgendamento> findAllAgendamentos() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();

        return agendamentos.stream()
                .map(this::convertToDadosAgendamento)
                .collect(Collectors.toList());
    }

    private DadosAgendamento convertToDadosAgendamento(Agendamento agendamento) {
        return new DadosAgendamento(agendamento.getId(), agendamento.getCliente().getId(), agendamento.getPet().getId(), agendamento.getFuncionario().getId(), agendamento.getData(), agendamento.getValor());
    }

    public ResponseEntity<?> atualizar(Long id, Agendamento agendamentoAtualizado) {
        Optional<Agendamento> agendamentoExistente = agendamentoRepository.findById(id);

        if (agendamentoExistente.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Agendamento não encontrado.");
        }

        double valorAtualizado = 0;

        Agendamento agendamento = agendamentoExistente.get();
        agendamento.setData(agendamentoAtualizado.getData());
        agendamento.setPet(agendamentoAtualizado.getPet());

        List<ServicoAgendamento> listServicos = agendamentoAtualizado.getServicosAgendamento();
        List<ProdutoAgendamento> listProdutos = agendamentoAtualizado.getProdutosAgendamento();


        Long idProduto = listProdutos.get(0).getId();
        Optional<Produto> produtoOptional = produtoRepository.findById(idProduto);
        Produto produto = produtoOptional.get();
        valorAtualizado += produto.getPreco() * listProdutos.get(0).getQuantidade();

        Long idServico = listServicos.get(0).getId();
        Optional<Servico> servicoOptional = servicoRepository.findById(idServico);
        Servico servico = servicoOptional.get();
        valorAtualizado += servico.getPreco();

        agendamento.setValor(valorAtualizado);

        agendamentoRepository.save(agendamento);

        DadosAgendamento dadosAgendamentoAtualizado = convertToDadosAgendamento(agendamento);
        return ResponseEntity.ok(dadosAgendamentoAtualizado);
    }


}
