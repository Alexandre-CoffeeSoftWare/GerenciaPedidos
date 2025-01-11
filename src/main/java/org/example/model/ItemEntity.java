package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "itens_pedido")
@Data
public class ItemEntity {

    @Id
    @Column(nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name="idPedido", nullable = false)
    private PedidoEntity pedidoEntity;

    @Column(name = "idProduto", nullable = false)
    private Integer idProduto;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade;

    @Column(name = "valor", nullable = false)
    private Float valor;
}
