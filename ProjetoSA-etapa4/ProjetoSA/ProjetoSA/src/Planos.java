/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author denis
 */
 public class Planos {
 private static int contadorId = 1;
    
    private int id;
    private String nomePlano;
    private String descricao;
    private double valor;
    private int duracaoDias;
    
    public Planos(String nome, double valor, int dias) {
        this.id = contadorId++;
        this.nomePlano = nome;
        this.valor = valor;
        this.duracaoDias = dias;
    }
    
    public Planos(String nome, String descricao, double valor, int dias) {
        this(nome, valor, dias);
        this.descricao = descricao;
    }

    public static int getContadorId() {
        return contadorId;
    }

    public int getId() {
        return id;
    }

    public String getNomePlano() {
        return nomePlano;
    }

    public String getDescricao() {
        return descricao;
    }

    public double getValor() {
        return valor;
    }

    public int getDuracaoDias() {
        return duracaoDias;
    }

    public static void setContadorId(int contadorId) {
        Planos.contadorId = contadorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomePlano(String nomePlano) {
        this.nomePlano = nomePlano;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setDuracaoDias(int duracaoDias) {
        this.duracaoDias = duracaoDias;
    }
    
    @Override
    public String toString() {
        return nomePlano + " - R$ " + String.format("%.2f", valor) + " (" + duracaoDias + " dias)";
    }  
    
}
