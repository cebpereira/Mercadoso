CREATE TABLE Produto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    tipo VARCHAR(255) NOT NULL,
    preco_compra DOUBLE NOT NULL,
    preco_venda DOUBLE NOT NULL,
    fabricacao DATE NOT NULL,
    validade DATE NOT NULL,
    quantidade_estoque INT NOT NULL
);
