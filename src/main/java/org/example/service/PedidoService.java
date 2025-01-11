package org.example.service;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.PedidoDTO;
import org.example.mapper.PedidoMapper;
import org.example.model.PedidoEntity;
import org.example.repository.PedidoRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final PedidoMapper pedidoMapper;
    private final Cache<Long, PedidoEntity> pedidoCache;
    private final ItensService itensService;
    private final RabbitMQService rabbitMQService;

    @GetMapping
    public List<PedidoEntity> getAllPedidos() {
        List<PedidoEntity> pedidosCache = pedidoCache.asMap().values().stream().toList();
        if (!pedidosCache.isEmpty()) {
            return pedidosCache;
        }

        List<PedidoEntity> pedidos = pedidoRepository.findAll();
        pedidos.forEach(pedido -> pedidoCache.put(pedido.getId(), pedido));
        return pedidos;
    }

    public PedidoEntity getPedidoById(Long id) {

        PedidoEntity pedido = pedidoCache.getIfPresent(id);

        if (pedido != null) {
            return pedido;
        }

        Optional<PedidoEntity> pedidoOptional = pedidoRepository.findById(id);

        if (pedidoOptional.isPresent()) {
            pedidoOptional.ifPresent(p -> pedidoCache.put(id, p));
            return pedidoOptional.get();
        } else
            return null;
    }

    public PedidoEntity inserir(PedidoDTO pedidoDTO) {

        if (pedidoCache.asMap().containsKey(pedidoDTO.getId())) {
            rabbitMQService.enviarMensagemParaFila("pedido-duplicado", pedidoDTO, "Pedido duplicado detectado no cache.");
            return null;
        }

        PedidoEntity pedidoEntity = pedidoMapper.dtoToEntity(pedidoDTO);

        Float total = itensService.calcularTotalPedido(pedidoDTO.getItens());
        pedidoEntity.setTotal(total);

        try {

            PedidoEntity savedPedido = pedidoRepository.save(pedidoEntity);
            pedidoCache.put(savedPedido.getId(), savedPedido);

            itensService.inserir(pedidoDTO.getItens(), savedPedido);

            return savedPedido;
        } catch (Exception e) {

            rabbitMQService.enviarMensagemParaFila("pedido.erro", pedidoDTO, "Erro ao salvar o pedido: " + e.getMessage());
            return null;
        }
    }

    public PedidoEntity alterar(Long id, PedidoDTO pedidoDTO) {
        PedidoEntity pedidoEntity = pedidoMapper.dtoToEntity(pedidoDTO);
        pedidoEntity.setId(id);

        Float total = itensService.calcularTotalPedido(pedidoDTO.getItens());
        pedidoEntity.setTotal(total);

        PedidoEntity savedPedido = pedidoRepository.save(pedidoEntity);
        pedidoCache.put(id, savedPedido);

        itensService.alterar(pedidoDTO.getItens(), savedPedido);

        return savedPedido;
    }

    public Void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
        pedidoCache.invalidate(id);
        return null;
    }
}