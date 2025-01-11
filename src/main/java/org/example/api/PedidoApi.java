package org.example.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.example.dto.PedidoDTO;
import org.example.model.PedidoEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
public interface PedidoApi {

    @Operation(summary = "Cadastrar novo pedido", tags = {"pedidos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoDTO.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Esse status deverá ser retornado quando algum requisito não for atendido, e não devemos ter quaisquer informação sobre qual foi o requisito não atendido")})
    @PostMapping(value = "/pedidos",
            produces = {"application/json"},
            consumes = {"application/json"})
    PedidoEntity inserir(@RequestBody @Valid PedidoDTO pedidoDTO);

    @Operation(summary = "Alterar pedido", tags = {"pedido"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Esse status deverá ser retornado quando algum requisito não for atendido, e não devemos ter quaisquer informação sobre qual foi o requisito não atendido")})
    @PutMapping(value = "/pedidos/{id}",
            produces = {"application/json"},
            consumes = {"application/json"})
    PedidoEntity alterar(@PathVariable Long id, @Valid @RequestBody PedidoDTO pedidoDTO);

    @Operation(summary = "Excluir pedido", tags = {"pedido"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Esse status deverá ser retornado quando algum requisito não for atendido, e não devemos ter quaisquer informação sobre qual foi o requisito não atendido")})
    @DeleteMapping(value = "/pedidos/{id}")
    Void excluir(@PathVariable Long id);

    @Operation(summary = "Listar pedidos", tags = {"pedido"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Esse status deverá ser retornado quando algum requisito não for atendido, e não devemos ter quaisquer informação sobre qual foi o requisito não atendido")})
    @GetMapping(value = "/pedidos")
    List<PedidoEntity> listar();

    @Operation(summary = "Buscar uma pedido", tags = {"pedido"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation", content = @Content(mediaType = "application/json", schema = @Schema(implementation = PedidoEntity.class))),
            @ApiResponse(responseCode = "400", description = "Bad Request - Esse status deverá ser retornado quando algum requisito não for atendido, e não devemos ter quaisquer informação sobre qual foi o requisito não atendido")})
    @GetMapping(value = "/pedidos/{id}")
    PedidoEntity buscar(@PathVariable("id") Long id);
}
