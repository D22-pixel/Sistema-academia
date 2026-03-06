
import java.sql.*;
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
public class PlanoTreinoDAO {
    public void salvarOuAtualizar(PlanoTreino pt) {
        String sql;
        boolean isInsert = (pt.getId() == 0);

        if (isInsert) {
            sql = "INSERT INTO planotreino (aluno, objetivo, frequencia, descricao) VALUES (?, ?, ?, ?)";
        } else {
            sql = "UPDATE planotreino SET aluno=?, objetivo=?, frequencia=?, descricao=? WHERE id=?";
        }

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, pt.getAluno());
            stmt.setString(2, pt.getObjetivo());
            stmt.setString(3, pt.getFrequencia());
            stmt.setString(4, pt.getDescricao());

            if (!isInsert) {
                stmt.setInt(5, pt.getId());
            }

            stmt.executeUpdate();

            if (isInsert) {
                ResultSet keys = stmt.getGeneratedKeys();
                if (keys.next()) {
                    pt.setId(keys.getInt(1));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<PlanoTreino> listarTodos() {
        List<PlanoTreino> lista = new ArrayList<>();
        String sql = "SELECT * FROM planotreino";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                PlanoTreino pt = new PlanoTreino();
                pt.setId(rs.getInt("id"));
                pt.setAluno(rs.getString("aluno"));
                pt.setObjetivo(rs.getString("objetivo"));
                pt.setFrequencia(rs.getString("frequencia"));
                pt.setDescricao(rs.getString("descricao"));
                pt.setDataCriacao(rs.getTimestamp("dataCriacao").toString()); 
                lista.add(pt);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lista;
    }
    public void excluir(int id) {
    String sql = "DELETE FROM planotreino WHERE id = ?";
    
    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {
        
        stmt.setInt(1, id);
        stmt.executeUpdate();
        System.out.println("Plano excluído com sucesso!");

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
}
