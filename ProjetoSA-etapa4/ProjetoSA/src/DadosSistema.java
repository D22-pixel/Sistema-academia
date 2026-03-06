
import java.util.ArrayList;
import java.util.List;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author denis
 */
public class DadosSistema {
      private static DadosSistema instancia;
    
    private List<Alunos> alunos;
    private List<Planos> planos;
    private List<Matriculas> matriculas;
    private List<Usuarios> usuarios;
    private List<PlanoTreino> planosTreino;
    
    private DadosSistema() {
        alunos = new ArrayList<>();
        planos = new ArrayList<>();
        matriculas = new ArrayList<>();
        usuarios = new ArrayList<>();
        planosTreino = new ArrayList<>();
        inicializarDados();
    }
    
    public static DadosSistema getInstancia() {
        if (instancia == null) {
            instancia = new DadosSistema();
        }
        return instancia;
    }
    
    private void inicializarDados() {
        usuarios.add(new Usuarios("admin", "admin123", "Administrador"));
        usuarios.add(new Usuarios("recepcionista", "recep123", "Recepcionista"));
        usuarios.add(new Usuarios("gerente", "gerente456", "Gerente"));
        usuarios.add(new Usuarios("prof_ricardo", "treino2024", "Prof_Ricardo"));
        usuarios.add(new Usuarios("prof_julia", "yoga2024", "Prof_Julia"));

        planos.add(new Planos("Plano Mensal", "Acesso completo por 30 dias", 99.90, 30));
        planos.add(new Planos("Plano Trimestral", "Acesso completo por 90 dias com desconto", 259.90, 90));
        planos.add(new Planos("Plano Semestral", "Acesso completo por 180 dias", 479.90, 180));
        planos.add(new Planos("Plano Anual", "Acesso completo por 365 dias com melhor custo-benefício", 899.90, 365));
        
        alunos.add(new Alunos("Denise Saldanha", "123.456.789-00", "(51) 98765-4321", "denise@email.com"));
        alunos.add(new Alunos("João Silva", "987.654.321-00", "(51) 99999-8888", "joao@email.com"));

        if (!alunos.isEmpty() && !planos.isEmpty()) {
            matriculas.add(new Matriculas(alunos.get(0), planos.get(3), "Ativa"));
        }
    }
  
    public void adicionarAluno(Alunos aluno) {
        alunos.add(aluno);
    }
    
    public void removerAluno(Alunos aluno) {
        alunos.remove(aluno);
    }
    
    public List<Alunos> getAlunos() {
        return new ArrayList<>(alunos);
    }
    
    public Alunos buscarAlunoPorCPF(String cpf) {
        for (Alunos aluno : alunos) {
            if (aluno.getCPF().equals(cpf)) {
                return aluno;
            }
        }
        return null;
    }

    public void adicionarPlano(Planos plano) {
        planos.add(plano);
    }
    
    public void removerPlano(Planos plano) {
        planos.remove(plano);
    }
    
    public List<Planos> getPlanos() {
        return new ArrayList<>(planos);
    }
    
    public void adicionarMatricula(Matriculas matricula) {
        matriculas.add(matricula);
    }
    
    public void removerMatricula(Matriculas matricula) {
        matriculas.remove(matricula);
    }
    
    public List<Matriculas> getMatriculas() {
        return new ArrayList<>(matriculas);
    }
    
    public List<Matriculas> getMatriculasPorAluno(Alunos aluno) {
        List<Matriculas> resultado = new ArrayList<>();
        for (Matriculas m : matriculas) {
            if (m.getAluno().equals(aluno)) {
                resultado.add(m);
            }
        }
        return resultado;
    }

    public Usuarios autenticarUsuario(String nomeUsuario, String senha) {
        for (Usuarios usuario : usuarios) {
            if (usuario.getNomeUsuario().equals(nomeUsuario) && usuario.autenticar(senha)) {
                return usuario;
            }
        }
        return null;
    }
    
    public List<Usuarios> getUsuarios() {
        return new ArrayList<>(usuarios);
    }
    public void adicionarPlanoTreino(PlanoTreino planoTreino) {
        planosTreino.add(planoTreino);
    }
    
    public void removerPlanoTreino(PlanoTreino planoTreino) {
        planosTreino.remove(planoTreino);
    }
    
    public List<PlanoTreino> getPlanosTreino() {
        return new ArrayList<>(planosTreino);
    }
    
    public List<PlanoTreino> getPlanosTreinoPorAluno(Alunos aluno) {
        List<PlanoTreino> resultado = new ArrayList<>();
        for (PlanoTreino pt : planosTreino) {
            if (pt.getAluno().equals(aluno)) {
                resultado.add(pt);
            }
        }
        return resultado;
    }
    
}
