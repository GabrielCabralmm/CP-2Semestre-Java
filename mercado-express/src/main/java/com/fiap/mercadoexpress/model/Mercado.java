package com.fiap.mercadoexpress.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade que representa um produto do "mercado express".
 * Mapeada para a tabela TDS_TB_mercado no banco ORACLE_FIAP.
 *
 * Colunas exigidas pelo enunciado: Id, Nome, Tipo, Setor, Tamanho, Preco.
 *
 * O Lombok (@Data) gera automaticamente getters, setters, toString,
 * equals e hashCode, evitando codigo boilerplate (uso obrigatorio
 * conforme enunciado).
 */
@Entity
@Table(name = "TDS_TB_MERCADO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mercado {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @NotBlank(message = "O nome do produto e obrigatorio")
    @Column(name = "NOME", nullable = false, length = 100)
    private String nome;

    @Column(name = "TIPO", length = 50)
    private String tipo;

    @Column(name = "SETOR", length = 50)
    private String setor;

    @Column(name = "TAMANHO", length = 20)
    private String tamanho;

    @Column(name = "PRECO")
    private Double preco;

}
