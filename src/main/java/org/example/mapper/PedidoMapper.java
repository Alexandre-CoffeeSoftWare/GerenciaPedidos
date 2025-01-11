package org.example.mapper;

import org.example.dto.ItemDTO;
import org.example.dto.PedidoDTO;
import org.example.model.ItemEntity;
import org.example.model.PedidoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PedidoMapper {

    @Mappings({
            @Mapping(target = "total", ignore = true)
    })
    PedidoEntity dtoToEntity(PedidoDTO pedidoDTO);
}
