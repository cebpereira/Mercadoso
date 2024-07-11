package model;

public class Cliente {
	private int id;
    private String nome;
    private String rg;
    private String dataNascimento;
    private double limiteCredito;

    // Construtor
    public Cliente(String nome, String rg, String dataNascimento, double limiteCredito) {
        this.nome = nome;
        this.rg = rg;
        this.dataNascimento = dataNascimento;
        this.limiteCredito = limiteCredito;
    }

    // Getters e Setters
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

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public double getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
}
