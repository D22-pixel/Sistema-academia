


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_academia";
    private static final String USUARIO = "root";
    private static final String SENHA = "senha";
    
    private static Connection conexao = null;
    
    private Conexao() {}
    
    public static Connection getConexao() {
        try {
            if (conexao == null || conexao.isClosed()) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conexao = DriverManager.getConnection(URL, USUARIO, SENHA);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return conexao;
    }
    
    public static void fecharConexao() {
        try {
            if (conexao != null && !conexao.isClosed()) {
                conexao.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

