package Produtos;


public class Produtos {
    private int id;
    private String nome;
    private String tipo;
    private float preco;       // preço acho melhor padronizarmos em reais, no banco de dados ele aceita o formato: 99.99
    private long quantidade; //Acho melhor padronizarmos a quantidade em gramas

    public Produtos() {}

    public Produtos(int id, String nome, String tipo, float preco, long quantidade) {
        this.id = id;
        this.nome = nome;
        this.tipo = tipo;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // Getters e setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public float getPreco() { return preco; }
    public void setPreco(float preco) { this.preco = preco; }

    public long getQuantidade() { return quantidade; }
    public void setQuantidade(long quantidade) { this.quantidade = quantidade; }
}
