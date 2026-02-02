
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
public class AlunosDAO {
    public List<Alunos> listarTodos() { 
        List<Alunos> lista = new ArrayList<>();
        String sql = "SELECT * FROM alunos";
    
        try (java.sql.Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Alunos a = new Alunos();
                a.setId(rs.getInt("id_aluno"));
                a.setNome(rs.getString("nome"));
                a.setCPF(rs.getString("cpf"));
                a.setTelefone(rs.getString("telefone"));
                a.setEmail(rs.getString("email"));
                a.setdataCadastro(rs.getString("data_cadastro")); 
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void salvarOuAtualizar(Alunos aluno) {
        String sql;
        if (aluno.getId() == 0) {
        sql = "INSERT INTO alunos (nome, cpf, telefone, email, data_cadastro) VALUES (?, ?, ?, ?, CURDATE())";
    } else {
        sql = "UPDATE alunos SET nome=?, telefone=?, email=? WHERE id_aluno=?";
    }

    try (java.sql.Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setString(1, aluno.getNome());
        if (aluno.getId() == 0) {
            stmt.setString(2, aluno.getCPF());
            stmt.setString(3, aluno.getTelefone());
            stmt.setString(4, aluno.getEmail());
        } else {
            stmt.setString(2, aluno.getTelefone());
            stmt.setString(3, aluno.getEmail());
            stmt.setInt(4, aluno.getId());
        }
        
        stmt.executeUpdate();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    }
}
  