CREATE TABLE Cliente (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    rg VARCHAR(255) NOT NULL,
    data_nascimento DATE NOT NULL,
    limite_credito DOUBLE
);
