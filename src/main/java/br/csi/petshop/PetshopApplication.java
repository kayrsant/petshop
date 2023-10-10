package br.csi.petshop;

import br.csi.petshop.model.usuario.Usuario;
import br.csi.petshop.model.usuario.UsuarioPermissao;
import br.csi.petshop.service.UsuarioService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PetshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(PetshopApplication.class, args);
	}
}
