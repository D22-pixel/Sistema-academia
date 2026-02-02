/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author denis
 */
public class PlanoTreino {
     private static int contadorId = 1;
    private int id;
    private String aluno;
    private String objetivo; 
    private String frequencia; 
    private String descricao;
    private String dataCriacao;
    private String instrutor;
    
    public PlanoTreino(String aluno, String objetivo, String frequencia, String instrutor) {
        this.id = contadorId++;
        this.aluno = aluno;
        this.objetivo = objetivo;
        this.frequencia = frequencia;
        this.instrutor = instrutor;
        this.dataCriacao = java.time.LocalDate.now().toString();
        this.descricao = descricao;
    }
    public PlanoTreino() {
     }
    
    public PlanoTreino(String aluno, String objetivo, String frequencia, String instrutor, String descricao) {
        this(aluno, objetivo, frequencia, instrutor);
        this.descricao = descricao;
    }

    public static int getContadorId() {
        return contadorId;
    }

    public static void setContadorId(int contadorId) {
        PlanoTreino.contadorId = contadorId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAluno() {
        return aluno;
    }

    public void setAluno(String aluno) {
        this.aluno = aluno;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public void setObjetivo(String objetivo) {
        this.objetivo = objetivo;
    }

    public String getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(String frequencia) {
        this.frequencia = frequencia;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(String dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getInstrutor() {
        return instrutor;
    }

    public void setInstrutor(String instrutor) {
        this.instrutor = instrutor;
    }

    @Override
    public String toString() {
        return "Plano #" + id + " - " + aluno + " - " + objetivo;
    }
}
