package br.csi.petshop.model.usuario;

public record DadosUsuario(Long id, String login,
                           UsuarioPermissao permissao) {
    public DadosUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getLogin(),
                usuario.getPermissao());
    }
}
