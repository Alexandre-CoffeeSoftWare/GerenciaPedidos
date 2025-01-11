package org.example.service;

import org.example.dto.PedidoDTO;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class RabbitMQService {

    private final RabbitTemplate rabbitTemplate;

    public RabbitMQService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void enviarMensagemParaFila(String fila, PedidoDTO pedidoDTO, String motivo) {
        try {
            Map<String, Object> mensagem = new HashMap<>();
            mensagem.put("Nro Pedido:", pedidoDTO.getId().toString());
            mensagem.put("motivo", motivo);
            rabbitTemplate.convertAndSend(fila, mensagem);
            System.out.println("Mensagem enviada para a fila " + fila + ": " + mensagem);
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem para a fila: " + e.getMessage());
        }
    }
}

