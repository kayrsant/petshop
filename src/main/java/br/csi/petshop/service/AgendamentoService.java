package br.csi.petshop.service;

import br.csi.petshop.model.agendamento.Agendamento;
import br.csi.petshop.model.agendamento.AgendamentoRepository;
import br.csi.petshop.model.agendamento.DadosAgendamento;
import br.csi.petshop.model.servico.DadosServico;
import br.csi.petshop.model.servico.Servico;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AgendamentoService {

    @Autowired
    private final AgendamentoRepository agendamentoRepository;

    public AgendamentoService(AgendamentoRepository agendamentoRepository) {
        this.agendamentoRepository = agendamentoRepository;
    }

    public void cadastrar(Agendamento agendamento) {
        agendamentoRepository.save(agendamento);
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

        return DadosAgendamento.criarDTO(agendamento);
    }

    public List<DadosAgendamento> findAllAgendamentos() {
        List<Agendamento> agendamentos = agendamentoRepository.findAll();

        return agendamentos.stream()
                .map(DadosAgendamento::criarDTO)
                .collect(Collectors.toList());
    }


}
