

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class TelaLogin extends JFrame {
   private JTextField txtUsuario;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnSair;
    
    public TelaLogin() {
        setTitle("Sistema de Academia - Login");
        setSize(450, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {

        JPanel painelPrincipal = new JPanel();
        painelPrincipal.setLayout(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        painelPrincipal.setBackground(new Color(240, 240, 240));
  
        JLabel lblTitulo = new JLabel("SISTEMA DE ACADEMIA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(50, 80, 204));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(new Color(240, 240, 240));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
 
        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        painelForm.add(lblUsuario, gbc);
        
        txtUsuario = new JTextField(15);
        txtUsuario.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        painelForm.add(txtUsuario, gbc);
  
        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        painelForm.add(lblSenha, gbc);
        
        txtSenha = new JPasswordField(15);
        txtSenha.setFont(new Font("Arial", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        painelForm.add(txtSenha, gbc);
        
        painelPrincipal.add(painelForm, BorderLayout.CENTER);
  
        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(new Color(240, 240, 240));
        
        btnEntrar = new JButton("Entrar");
        btnEntrar.setFont(new Font("Arial", Font.BOLD, 14));
        btnEntrar.setBackground(new Color(0, 10,10));
        btnEntrar.setForeground(Color.black);
        btnEntrar.setFocusPainted(false);
        btnEntrar.setPreferredSize(new Dimension(120, 35));
        btnEntrar.addActionListener(e -> realizarLogin());
        
        btnSair = new JButton("Sair");
        btnSair.setFont(new Font("Arial", Font.BOLD, 14));
        btnSair.setBackground(new Color(0, 10,10));
        btnSair.setForeground(Color.black);
        btnSair.setFocusPainted(false);
        btnSair.setPreferredSize(new Dimension(120, 35));
        btnSair.addActionListener(e -> System.exit(0));
        
        painelBotoes.add(btnEntrar);
        painelBotoes.add(btnSair);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
  
        add(painelPrincipal);
  
        txtSenha.addActionListener(e -> realizarLogin());

        JLabel lblInfo = new JLabel("<html><center> </center></html>", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Arial", Font.PLAIN, 11));
        lblInfo.setForeground(Color.GRAY);
        JPanel painelInfo = new JPanel();
        painelInfo.setBackground(new Color(240, 240, 240));
        painelInfo.add(lblInfo);
        painelPrincipal.add(painelInfo, BorderLayout.NORTH);
        painelPrincipal.add(lblTitulo, BorderLayout.CENTER);

        painelPrincipal.removeAll();
        painelPrincipal.setLayout(new BoxLayout(painelPrincipal, BoxLayout.Y_AXIS));
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(lblTitulo);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(lblInfo);
        painelPrincipal.add(Box.createVerticalStrut(20));
        painelPrincipal.add(painelForm);
        painelPrincipal.add(Box.createVerticalStrut(10));
        painelPrincipal.add(painelBotoes);
    }
    
    private void realizarLogin() {
        String usuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());
        
        if (usuario.isEmpty() || senha.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Por favor, preencha todos os campos!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Usuarios usuarioAutenticado = DadosSistema.getInstancia().autenticarUsuario(usuario, senha);
        
        if (usuarioAutenticado != null) {
            JOptionPane.showMessageDialog(this,
                "Bem-vindo, " + usuarioAutenticado.getNomeUsuario() + "!",
                "Login realizado com sucesso",
                JOptionPane.INFORMATION_MESSAGE);

            SwingUtilities.invokeLater(() -> {
                new MenuPrincipal(usuarioAutenticado).setVisible(true);
            });
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                "Usuário ou senha incorretos!",
                "Erro de autenticação",
                JOptionPane.ERROR_MESSAGE);
            txtSenha.setText("");
            txtUsuario.requestFocus();
        }
    } 
    
}
