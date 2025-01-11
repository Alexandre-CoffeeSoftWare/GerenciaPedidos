package org.example.mapper;

import org.example.dto.PedidoDTO;
import org.example.model.PedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "nomeProduto", source = "nomeProduto")
    @Mapping(target = "quantidade", source = "quantidade")
    @Mapping(target = "valor", source = "valor")
    PedidoEntity dtoToEntity(PedidoDTO pedidoDTO);
}
