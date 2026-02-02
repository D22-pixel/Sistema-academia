/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author denis
 */
 public class Usuarios {
    private static int contadorId = 1;
    private int id;
    private String nomeUsuario;
    private String senha;
    private String cargo;
    
    public Usuarios(String nome, String senha, String cargo) {
        this.id = contadorId++;
        this.nomeUsuario = nome;
        this.senha = senha;
        this.cargo = cargo;
    }
    
    public boolean autenticar(String senha) {
        return this.senha.equals(senha);
    }   

    public static int getContadorId() {
        return contadorId;
    }

    public int getId() {
        return id;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public String getSenha() {
        return senha;
    }

    public String getCargo() {
        return cargo;
    }

    public static void setContadorId(int contadorId) {
        Usuarios.contadorId = contadorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }
    @Override
    public String toString() {
        return nomeUsuario + " (" + cargo + ")";
    }
    
}
