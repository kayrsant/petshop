package br.csi.petshop.service;

import br.csi.petshop.model.agendamento.Agendamento;
import br.csi.petshop.model.agendamento.AgendamentoRepository;
import br.csi.petshop.model.agendamento.DadosAgendamento;
import br.csi.petshop.model.cliente.Cliente;
import br.csi.petshop.model.cliente.ClienteRepository;
import br.csi.petshop.model.funcionario.Funcionario;
import br.csi.petshop.model.funcionario.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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

    public AgendamentoService(AgendamentoRepository agendamentoRepository, ClienteRepository clienteRepository, FuncionarioRepository funcionarioRepository) {
        this.agendamentoRepository = agendamentoRepository;
        this.clienteRepository = clienteRepository;
        this.funcionarioRepository = funcionarioRepository;
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
        agendamentoCriado.setData(agendamento.getData());
        agendamentoCriado.setCliente(cliente.get());
        agendamentoCriado.setPet(agendamento.getPet());
        agendamentoCriado.setFuncionario(funcionario.get());
        agendamentoCriado.setProdutosAgendamento(null);
        agendamentoCriado.setServicosAgendamento(null);
        agendamentoCriado.setValor(agendamento.getValor());
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


}
