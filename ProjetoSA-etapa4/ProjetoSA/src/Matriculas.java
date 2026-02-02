/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author denis
 */
public class Matriculas {
    private static int contadorId = 1;
    private int id;
    private Alunos aluno;
    private Planos plano;
    private String dataInicio;
    private String dataFim;
    private String statusPagamento;
    
    
 public Matriculas (Alunos aluno, Planos plano, String status){
        this.id = contadorId++;
        this.aluno = aluno;
        this.plano = plano;
        this.statusPagamento = status;
        this.dataInicio = java.time.LocalDate.now().toString();
        calcularDataFim();
 } 
    private void calcularDataFim() {
        java.time.LocalDate inicio = java.time.LocalDate.parse(dataInicio);
        java.time.LocalDate fim = inicio.plusDays(plano.getDuracaoDias());
        this.dataFim = fim.toString();
    }

    public static int getContadorId() {
        return contadorId;
    }

    public int getId() {
        return id;
    }

    public Alunos getAluno() {
        return aluno;
    }

    public Planos getPlano() {
        return plano;
    }

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public String getStatusPagamento() {
        return statusPagamento;
    }

    public static void setContadorId(int contadorId) {
        Matriculas.contadorId = contadorId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setAluno(Alunos aluno) {
        this.aluno = aluno;
    }

    public void setPlano(Planos plano) {
        this.plano = plano;
    }

    public void setDataInicio(String dataInicio) {
        this.dataInicio = dataInicio;
    }

    public void setDataFim(String dataFim) {
        this.dataFim = dataFim;
    }

    public void setStatusPagamento(String statusPagamento) {
        this.statusPagamento = statusPagamento;
    }
     
    @Override
    public String toString() {
        return "Matrícula #" + id + " - " + aluno.getNome() + " - " + plano.getNomePlano();
    }
}
