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

    public PedidoEntity createPedido(PedidoDTO pedidoDTO) {
        PedidoEntity pedidoEntity = pedidoMapper.dtoToEntity(pedidoDTO);
        PedidoEntity savedPedido = pedidoRepository.save(pedidoEntity);
        pedidoCache.put(savedPedido.getId(), savedPedido);
        return savedPedido;
    }

    public PedidoEntity updatePedido(Long id, PedidoDTO pedidoDTO) {
        PedidoEntity pedidoEntity = pedidoMapper.dtoToEntity(pedidoDTO);
        pedidoEntity.setId(id);
        PedidoEntity updatedPedido = pedidoRepository.save(pedidoEntity);
        pedidoCache.put(id, updatedPedido);
        return updatedPedido;
    }

    public Void deletePedido(Long id) {
        pedidoRepository.deleteById(id);
        pedidoCache.invalidate(id);
        return null;
    }
}