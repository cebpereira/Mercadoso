package model;

public class Produto {
    private int id;
    private String nome;
    private String tipo;
    private double precoCompra;
    private double precoVenda;
    private String fabricacao;
    private String validade;
    private int quantidadeEstoque;

    public Produto(int id, String nome, String tipo, double precoCompra, double precoVenda, String fabricacao, String validade, int quantidadeEstoque) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.precoCompra = precoCompra;
        this.precoVenda = precoVenda;
        this.fabricacao = fabricacao;
        this.validade = validade;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    @Override
    public String toString() {
        return nome + " (" + tipo + ") - Preço: " + precoVenda + " - Estoque: " + quantidadeEstoque;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public double getPrecoCompra() {
        return precoCompra;
    }

    public void setPrecoCompra(double precoCompra) {
        this.precoCompra = precoCompra;
    }

    public double getPrecoVenda() {
        return precoVenda;
    }

    public void setPrecoVenda(double precoVenda) {
        this.precoVenda = precoVenda;
    }

    public String getFabricacao() {
        return fabricacao;
    }

    public void setFabricacao(String fabricacao) {
        this.fabricacao = fabricacao;
    }

    public String getValidade() {
        return validade;
    }

    public void setValidade(String validade) {
        this.validade = validade;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}
