package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pedido")
@Data
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_produto", nullable = false)
    private String nomeProduto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "valor", nullable = false)
    private Float valor;
}