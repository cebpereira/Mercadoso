CREATE TABLE Venda (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cliente_id INT,
    cliente_nome VARCHAR(255) NOT NULL,
    forma_pagamento VARCHAR(50) NOT NULL,
    valor_total DOUBLE NOT NULL,
    itens JSON NOT NULL,
    FOREIGN KEY (cliente_id) REFERENCES Cliente(id)
);
