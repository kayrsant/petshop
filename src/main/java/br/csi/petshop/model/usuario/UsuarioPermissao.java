package br.csi.petshop.model.usuario;

public enum UsuarioPermissao {
    ADMIN("admin"),
    FUNCIONARIO("funcionario"),
    CLIENTE("cliente");

    private String role;

    UsuarioPermissao(String role){
        this.role = role;
    }

    public String getRole(){
        return role;
    }
}
