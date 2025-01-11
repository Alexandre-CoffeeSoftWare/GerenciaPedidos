
# Projeto de Gerenciamento de Pedidos

Este projeto implementa um sistema de gerenciamento de pedidos, utilizando Spring Boot para o back-end e RabbitMQ para a fila de mensagens. A principal funcionalidade é a inserção e manipulação de pedidos, incluindo a comunicação com o RabbitMQ para o envio de mensagens.

## Estrutura do Projeto

O projeto segue a arquitetura MVC (Model-View-Controller) e está dividido nas seguintes camadas:

- **Controller**: Responsável por expor os endpoints da API para interação com o front-end.
- **Service**: Contém a lógica de negócios e integra com o RabbitMQ para envio de mensagens.
- **Model**: Contém as entidades e DTOs (Data Transfer Objects) que representam os dados.
- **RabbitMQ**: Sistema de mensageria utilizado para processar pedidos de forma assíncrona.

## Funcionalidades

- **Listagem de Pedidos**: Endpoint para listar todos os pedidos armazenados.
- **Busca de Pedido por ID**: Endpoint para buscar um pedido específico através do seu ID.
- **Inserção de Pedido**: Endpoint para inserir um novo pedido.
- **Alteração de Pedido**: Endpoint para alterar um pedido existente.
- **Exclusão de Pedido**: Endpoint para excluir um pedido do sistema.

## Tecnologias Utilizadas

- **Spring Boot**: Framework principal para o desenvolvimento do back-end.
- **RabbitMQ**: Sistema de mensageria utilizado para o envio de mensagens assíncronas.
- **Java 21**: Linguagem de programação utilizada.
- **Maven**: Gerenciador de dependências e construção do projeto.

## Como Rodar o Projeto

### 1. Clonar o repositório

```bash
git clone <url-do-repositorio>
cd <nome-do-repositorio>
```

### 2. Configuração do RabbitMQ

Este projeto utiliza o RabbitMQ para o envio de mensagens. O RabbitMQ deve estar configurado e em execução antes de rodar o serviço.

Para rodar o RabbitMQ utilizando Docker, utilize o seguinte comando:

```bash
docker run -d --name rabbitmq -p 5672:5672 -p 15672:15672 rabbitmq:management
```

Isso irá subir o RabbitMQ na porta 5672 para o envio de mensagens e 15672 para a interface de gerenciamento.

### 3. Executar o projeto

Com o RabbitMQ rodando, basta rodar o projeto Spring Boot usando o Maven:

```bash
mvn spring-boot:run
```

O servidor será iniciado e estará disponível para receber requisições na porta 8080.

## Observações Importantes

### Erro ao Enviar Mensagens para o RabbitMQ

Ao tentar enviar um pedido para a fila do RabbitMQ, foi identificado que o método `enviarMensagemParaFila` não estava funcionando corretamente ao tentar serializar o objeto `PedidoDTO`. O erro retornado foi:

```
java.lang.IllegalArgumentException: Could not serialize object of type: class java.util.HashMap
```

Esse erro ocorre porque o RabbitMQ, via Spring RabbitTemplate, não consegue serializar objetos complexos diretamente. Para resolver esse problema, foi adotada a abordagem de serializar o objeto `PedidoDTO` para uma string JSON usando a biblioteca `ObjectMapper` da Jackson. O código ajustado para enviar a mensagem ficou assim:

```java
public void enviarMensagemParaFila(String fila, PedidoDTO pedidoDTO, String motivo) {
    try {
        // Criação do mapa de dados a ser enviado
        Map<String, Object> mensagem = new HashMap<>();

        // Usando ObjectMapper para serializar o PedidoDTO em uma string JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String pedidoJson = objectMapper.writeValueAsString(pedidoDTO);

        // Adicionando as informações no mapa (você pode adicionar outras informações aqui)
        mensagem.put("pedido", pedidoJson);  // Serializando PedidoDTO como JSON
        mensagem.put("motivo", motivo);

        // Enviando a mensagem para a fila
        rabbitTemplate.convertAndSend(fila, mensagem);

        // Log para confirmar que a mensagem foi enviada
        System.out.println("Mensagem enviada para a fila " + fila + ": " + mensagem);
    } catch (Exception e) {
        System.err.println("Erro ao enviar mensagem para a fila: " + e.getMessage());
    }
}
```

### Detalhes sobre a Estrutura do PedidoDTO

A classe `PedidoDTO` é a principal estrutura de dados que representa um pedido. Ela contém uma lista de itens, que são representados pela classe `ItemDTO`. O código para o `PedidoDTO` é o seguinte:

```java
package org.example.dto;

import jakarta.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
public class PedidoDTO {

    private Long id;

    @NotNull(message = "O Pedido deve conter itens")
    private List<ItemDTO> itens;
}
```

A classe `ItemDTO` possui as informações sobre os produtos no pedido, como a quantidade e o valor:

```java
package org.example.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class ItemDTO {

    private Long id;

    @NotNull(message = "A quantidade não pode ser nulo")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private Integer idProduto;

    @NotNull(message = "A quantidade não pode ser nulo")
    @Min(value = 1, message = "A quantidade deve ser maior que zero")
    private Integer quantidade;

    @NotNull(message = "O valor não pode ser nulo")
    @Min(value = 1, message = "O valor deve ser maior que zero")
    private Float valor;
}
```

### Conclusão

Este projeto utiliza Spring Boot e RabbitMQ para gerenciar e processar pedidos de forma assíncrona. Com o RabbitMQ em funcionamento, o sistema é capaz de enviar e processar pedidos de maneira eficiente e escalável. A solução para o erro de serialização garantiu que os pedidos sejam enviados corretamente para a fila, com um formato JSON que pode ser manipulado posteriormente.

## Licença

Este projeto está licenciado sob a [MIT License](LICENSE).
