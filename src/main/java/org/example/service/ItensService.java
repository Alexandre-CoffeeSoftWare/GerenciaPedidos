package org.example.service;

import com.github.benmanes.caffeine.cache.Cache;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.dto.ItemDTO;
import org.example.model.ItemEntity;
import org.example.model.PedidoEntity;
import org.example.repository.ItensRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ItensService {

    private final ItensRepository itensRepository;
    private final Cache<Long, ItemEntity> itemCache;

    public float calcularTotalPedido(List<ItemDTO> itemDTOs) {
        float total = 0;
        for (ItemDTO itemDTO : itemDTOs) {
            total += itemDTO.getQuantidade() * itemDTO.getValor();
        }
        return total;
    }

    public void inserir(List<ItemDTO> itemDTOs, PedidoEntity pedidoEntity) {
        for (ItemDTO itemDTO : itemDTOs) {
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setId(itemDTO.getId());
            itemEntity.setIdProduto(itemDTO.getIdProduto());
            itemEntity.setQuantidade(itemDTO.getQuantidade());
            itemEntity.setValor(itemDTO.getValor());
            itemEntity.setPedidoEntity(pedidoEntity);
            ItemEntity salvedItem = itensRepository.save(itemEntity);
            itemCache.put(salvedItem.getId(), salvedItem);
        }
    }

    public void alterar(List<ItemDTO> itemDTOs, PedidoEntity pedidoEntity) {
        for (ItemDTO itemDTO : itemDTOs) {
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setId(itemDTO.getId());
            itemEntity.setIdProduto(itemDTO.getIdProduto());
            itemEntity.setQuantidade(itemDTO.getQuantidade());
            itemEntity.setValor(itemDTO.getValor());
            itemEntity.setPedidoEntity(pedidoEntity);
            ItemEntity salvedItem = itensRepository.save(itemEntity);
            itemCache.put(salvedItem.getId(), salvedItem);
        }
    }

}
