package br.csi.petshop.model.usuario;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository
        extends JpaRepository<Usuario, Long> {
    public Usuario findByLogin(String login);
}

