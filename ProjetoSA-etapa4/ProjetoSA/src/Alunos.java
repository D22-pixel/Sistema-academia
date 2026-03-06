/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author denis
 */
 public class Alunos {
    
    private static int contadorId = 1;
    private int id;
    private String nome;
    private String CPF;
    private String telefone;
    private String email;
    private String dataCadastro;
    
    public Alunos() {
    }
    
 public Alunos(String nome, String cpf) {
        this.id = 0;
        this.nome = nome;
        this.CPF = cpf;
        this.dataCadastro = java.time.LocalDate.now().toString();
    }
    
    public Alunos(String nome, String cpf, String telefone, String email) {
        this(nome, cpf);
        this.telefone = telefone;
        this.email = email;
    }

    public static int getContadorID() {
        return contadorId;
    }

    public String getNome() {
        return nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public String getEmail() {
        return email;
    }
    public String getCPF() {
        return CPF;
    }
     public String getdataCadastro() {
        return dataCadastro;
    }
      public int getId() {
        return id;
    }
    

    public static void setContadorID(int contadorID) {
        Alunos.contadorId = contadorID;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public void setCPF(String CPF) {
        this.CPF = CPF;
    }
     public void setdataCadastro(String dataCadatro) {
        this.dataCadastro = dataCadastro;
    }
     public void setId(int id) {
        this.id = id;
    }
     
    @Override
    public String toString(){
        return id + "_" + nome + "("+ CPF+")";
    }
}
