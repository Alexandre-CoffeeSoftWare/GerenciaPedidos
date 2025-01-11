package org.example.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "pedido")
@Data
public class PedidoEntity {

    @Id
    @Column(nullable = false)
    private Long id;

    @Column(name = "total", nullable = false)
    private Float total;
}