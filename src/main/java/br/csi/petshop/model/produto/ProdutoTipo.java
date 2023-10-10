package br.csi.petshop.model.produto;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


public enum ProdutoTipo {

    REMEDIO,
    BRINQUEDO,
    RACAO,

    ACESSORIO,
}