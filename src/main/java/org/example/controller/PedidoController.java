package org.example.controller;

import org.example.api.PedidoApi;
import org.example.dto.PedidoDTO;
import org.example.model.PedidoEntity;
import org.example.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PedidoController implements PedidoApi {

    @Autowired
    private PedidoService pedidoService;

    @Override
    public List<PedidoEntity> listar() {
        return pedidoService.getAllPedidos();
    }

    @Override
    public PedidoEntity buscar(Long id) {
        return pedidoService.getPedidoById(id);
    }

    @Override
    public PedidoEntity inserir(PedidoDTO pedidoDTO) {
        return pedidoService.inserir(pedidoDTO);
    }

    @Override
    public PedidoEntity alterar(Long id, PedidoDTO pedidoDTO) {
        return pedidoService.alterar(id, pedidoDTO);
    }

    @Override
    public Void excluir(Long id) {
        return pedidoService.deletePedido(id);
    }
}