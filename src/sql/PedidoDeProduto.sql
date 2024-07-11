CREATE TABLE PedidoDeProduto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    data_pedido DATE NOT NULL,
    itens JSON NOT NULL
);
