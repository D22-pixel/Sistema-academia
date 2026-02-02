
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet; 
import java.sql.SQLException;
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
public class MatriculasDAO {

    public boolean salvar(Matriculas m) {

        String sql = "INSERT INTO matriculas (id_aluno, id_plano, data_inicio, status_pagamento) VALUES (?, ?, ?, ?)";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
   
            stmt.setInt(1, m.getAluno().getId());
            stmt.setInt(2, m.getPlano().getId());
            stmt.setString(3, m.getDataInicio()); 
            stmt.setString(4, m.getStatusPagamento());
            
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public String listarParaRelatorio() {
    StringBuilder sb = new StringBuilder();
    
    String sql = "SELECT m.id_matricula, a.nome, p.nome_plano, p.valor, m.data_inicio, m.status_pagamento " +
                 "FROM matriculas m " +
                 "JOIN alunos a ON m.id_aluno = a.id_aluno " +
                 "JOIN planos p ON m.id_plano = p.id_plano";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            sb.append("ID: ").append(rs.getInt("id_matricula")).append("\n");
            sb.append("ALUNO: ").append(rs.getString("nome")).append("\n");
            sb.append("PLANO: ").append(rs.getString("nome_plano")).append("\n");
            sb.append("VALOR: R$ ").append(String.format("%.2f", rs.getDouble("valor"))).append("\n");
            sb.append("STATUS: ").append(rs.getString("status_pagamento")).append("\n");
            sb.append("-".repeat(50)).append("\n");
        }
    } catch (SQLException e) {
        e.printStackTrace();
        return "Erro ao carregar dados do banco.";
    }
    return sb.toString();
 }
    public List<Matriculas> listarTudoParaRelatorio() {
    List<Matriculas> lista = new ArrayList<>();
    String sql = "SELECT m.id_matricula, a.nome, a.cpf, p.nome_plano, p.valor, m.data_inicio, m.status_pagamento " +
                 "FROM matriculas m " +
                 "JOIN alunos a ON m.id_aluno = a.id_aluno " +
                 "JOIN planos p ON m.id_plano = p.id_plano";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Alunos a = new Alunos();
            a.setNome(rs.getString("nome"));
            a.setCPF(rs.getString("cpf"));

            Planos p = new Planos();
            p.setNomePlano(rs.getString("nome_plano"));
            p.setValor(rs.getDouble("valor"));

            Matriculas m = new Matriculas(a, p, rs.getString("status_pagamento"));
            m.setId(rs.getInt("id_matricula"));
            m.setDataInicio(rs.getString("data_inicio"));
            
            lista.add(m);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
    }
    public List<Matriculas> listarParaTabela() {
    List<Matriculas> lista = new ArrayList<>();
    String sql = "SELECT m.id_matricula, a.nome, a.cpf, p.nome_plano, p.valor, m.data_inicio, m.status_pagamento " +
                 "FROM matriculas m " +
                 "JOIN alunos a ON m.id_aluno = a.id_aluno " +
                 "JOIN planos p ON m.id_plano = p.id_plano";

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            Alunos aluno = new Alunos();
            aluno.setNome(rs.getString("nome"));
            aluno.setCPF(rs.getString("cpf"));

            Planos plano = new Planos();
            plano.setNomePlano(rs.getString("nome_plano"));
            plano.setValor(rs.getDouble("valor"));

            Matriculas m = new Matriculas(aluno, plano, rs.getString("status_pagamento"));
            m.setId(rs.getInt("id_matricula"));
            m.setDataInicio(rs.getString("data_inicio"));
            
            lista.add(m);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return lista;
   }
}    
