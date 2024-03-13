package br.csi.petshop.service;

import br.csi.petshop.model.servico.DadosServico;
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
public class ServicoService {
    @Autowired
    private final ServicoRepository servicoRepository;

    public ServicoService(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    public ResponseEntity<?> cadastrar(DadosServico servico) {
        Servico servicoNovo = new Servico();
        servicoNovo.setNome(servico.nome());
        servicoNovo.setDescricao(servico.descricao());
        servicoNovo.setPreco(servico.preco());
        servicoRepository.save(servicoNovo);
        return ResponseEntity.status(HttpStatus.CREATED).body(servicoNovo);
    }

    public ResponseEntity<?> deletar(Long id){
        Servico servico = this.servicoRepository.findById(id).orElse(null);

        if(servico == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado.");
        }
        this.servicoRepository.delete(servico);
        return ResponseEntity.status(HttpStatus.OK).body("Serviço " + servico.getId() + " deletado com sucesso.");
    }

    public DadosServico findServico(Long id) {
        Servico servico = servicoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Serviço não encontrado"));

        return convertToDadosServico(servico);
    }

    public List<DadosServico> findAllServicos() {
        List<Servico> servicos = servicoRepository.findAll();

        return servicos.stream()
                .map(this::convertToDadosServico)
                .collect(Collectors.toList());
    }

    public ResponseEntity<?> atualizar(Long id, DadosServico servicoAtualizado){
        try{
            Optional<Servico> servicoExistenteOpt = this.servicoRepository.findById(id);

            if(servicoExistenteOpt.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Serviço não encontrado.");
            }

            Servico servicoExistente = servicoExistenteOpt.get();

            servicoExistente.setNome(servicoAtualizado.nome());
            servicoExistente.setDescricao(servicoAtualizado.descricao());
            servicoExistente.setPreco(servicoAtualizado.preco());

            this.servicoRepository.save(servicoExistente);

            return ResponseEntity.ok(new DadosServico(servicoExistente.getId(), servicoExistente.getNome(), servicoExistente.getDescricao(), servicoExistente.getPreco()));
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao atualizar serviço: " + e.getMessage());
        }
    }

    private DadosServico convertToDadosServico(Servico servico) {
        return new DadosServico(servico.getId(), servico.getNome(), servico.getDescricao(), servico.getPreco());
    }

}
