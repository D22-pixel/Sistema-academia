/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.sql.Timestamp;
/**
 *
 * @author denis
 */
public class Venda {
  
    private int id;
    private int produtoId;
    private String nomeProduto;
    private int quantidade;
    private double valorTotal;
    private Timestamp dataVenda;


    public int getId()
    { return id; }
    public void setId(int id)
    { this.id = id; }
    public int getProdutoId()
    { return produtoId; }
    public void setProdutoId(int produtoId)
    { this.produtoId = produtoId; }
    public String getNomeProduto()
    { return nomeProduto; }
    public void setNomeProduto(String nomeProduto)
    { this.nomeProduto = nomeProduto; }
    public int getQuantidade() 
    { return quantidade; }
    public void setQuantidade(int quantidade)
    { this.quantidade = quantidade; }
    public double getValorTotal()
    { return valorTotal; }
    public void setValorTotal(double valorTotal) 
    { this.valorTotal = valorTotal; }
    public Timestamp getDataVenda() { return dataVenda; }
    public void setDataVenda(Timestamp dataVenda)
    { this.dataVenda = dataVenda; }
 }

    
