/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author denis
 */
public class Produto {
    private int id;
    private String nome;
    private double preco;
    private int estoque;
    private String unidade;
    private String tamanho;

    public Produto() {}

    public Produto(String nome, double preco, int estoque) {
        this.nome = nome;
        this.preco = preco;
        this.estoque = estoque;
    }
    public int getId()
    { return id; }
    public void setId(int id)
    { this.id = id; }
    public String getNome()
    { return nome; }
    public void setNome(String nome)
    { this.nome = nome; }
    public double getPreco()
    { return preco; }
    public void setPreco(double preco)
    { this.preco = preco; }
    public int getEstoque() 
    { return estoque; }
    public void setEstoque(int estoque)
    { this.estoque = estoque; }
    public String getUnidade() {
        return unidade;
    }

    public void setUnidade(String unidade) {
        this.unidade = unidade;
    }

    public String getTamanho() {
        return tamanho;
    }

    public void setTamanho(String tamanho) {
        this.tamanho = tamanho;
    }
} 
     
