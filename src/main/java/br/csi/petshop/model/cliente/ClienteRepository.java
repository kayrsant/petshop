package br.csi.petshop.model.cliente;

import org.springframework.data.jpa.repository.JpaRepository;


public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByEmail(String emailUsuario);
}
