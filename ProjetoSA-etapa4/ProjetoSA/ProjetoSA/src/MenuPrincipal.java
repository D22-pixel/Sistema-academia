
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class MenuPrincipal extends JFrame {
     private Usuarios usuarioLogado;
    
    public MenuPrincipal(Usuarios usuario) {
        this.usuarioLogado = usuario;
        setTitle("Sistema de Academia - Menu Principal");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelPrincipal.setBackground(new Color(245, 245, 245));
  
        JPanel painelCabecalho = new JPanel(new BorderLayout());
        painelCabecalho.setBackground(new Color(0, 102, 204));
        painelCabecalho.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        JLabel lblTitulo = new JLabel("SISTEMA DE GERENCIAMENTO - ACADEMIA");
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTitulo.setForeground(Color.WHITE);
        
        JLabel lblUsuario = new JLabel("Usuário: " + usuarioLogado.getNomeUsuario() + " (" + usuarioLogado.getCargo() + ")");
        lblUsuario.setFont(new Font("Arial", Font.PLAIN, 12));
        lblUsuario.setForeground(Color.WHITE);
        
        painelCabecalho.add(lblTitulo, BorderLayout.NORTH);
        painelCabecalho.add(lblUsuario, BorderLayout.SOUTH);
        
        painelPrincipal.add(painelCabecalho, BorderLayout.NORTH);
      
        JPanel painelMenu = new JPanel(new GridLayout(3, 2, 15, 15));
        painelMenu.setBackground(new Color(245, 245, 245));
        painelMenu.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
  
        JButton btnCadastroAlunos = criarBotaoMenu("Cadastro de Alunos", 
            "Adicionar, editar e consultar alunos", new Color(0, 10, 10));
        btnCadastroAlunos.addActionListener(e -> abrirTelaCadastroAlunos());
        
        JButton btnCadastroPlanos = criarBotaoMenu("Gerenciar Planos", 
            "Cadastrar e editar planos de mensalidade", new Color(0, 10, 10));
        btnCadastroPlanos.addActionListener(e -> abrirTelaGerenciarPlanos());
        
        JButton btnMatriculas = criarBotaoMenu("Novas Matrículas", 
            "Realizar matrículas de alunos", new Color(0, 10, 10));
        btnMatriculas.addActionListener(e -> abrirTelaMatriculas());
        
        JButton btnConsultarMatriculas = criarBotaoMenu("Consultar Matrículas", 
            "Visualizar matrículas ativas", new Color(0, 10, 10));
        btnConsultarMatriculas.addActionListener(e -> abrirTelaConsultarMatriculas());
        
        JButton btnRelatorios = criarBotaoMenu("Relatórios", 
            "Gerar relatórios do sistema", new Color(0, 10, 10));
        btnRelatorios.addActionListener(e -> abrirTelaRelatorios());
        
        JButton btnSair = criarBotaoMenu("Sair do Sistema", 
            "Fazer logout", new Color(0, 10, 10));
        btnSair.addActionListener(e -> sairDoSistema());
        
        painelMenu.add(btnCadastroAlunos);
        painelMenu.add(btnCadastroPlanos);
        painelMenu.add(btnMatriculas);
        painelMenu.add(btnConsultarMatriculas);
        painelMenu.add(btnRelatorios);
        painelMenu.add(btnSair);
        
        painelPrincipal.add(painelMenu, BorderLayout.CENTER);

        JPanel painelRodape = new JPanel();
        painelRodape.setBackground(new Color(245, 245, 245));
        JLabel lblRodape = new JLabel("© 2025 - Sistema de Academia - Denise Saldanha dos Santos");
        lblRodape.setFont(new Font("Arial", Font.ITALIC, 10));
        lblRodape.setForeground(Color.GRAY);
        painelRodape.add(lblRodape);
        painelPrincipal.add(painelRodape, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private JButton criarBotaoMenu(String titulo, String descricao, Color cor) {
        JButton btn = new JButton("<html><center><b>" + titulo + "</b><br>" +
                                  "<font size='2'>" + descricao + "</font></center></html>");
        btn.setFont(new Font("Arial", Font.PLAIN, 14));
        btn.setBackground(cor);
        btn.setForeground(Color.black);
        btn.setFocusPainted(false);
        btn.setBorder(BorderFactory.createRaisedBevelBorder());
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn.setBackground(cor);
            }
        });
        
        return btn;
    }
    
    private void abrirTelaCadastroAlunos() {
        new TelaCadastroAlunos().setVisible(true);
    }
    
    private void abrirTelaGerenciarPlanos() {
        new TelaGerenciarPlanos().setVisible(true);
    }
    
    private void abrirTelaMatriculas() {
        new TelaMatriculas().setVisible(true);
    }
    
    private void abrirTelaConsultarMatriculas() {
        new TelaConsultarMatriculas().setVisible(true);
    }
    
    private void abrirTelaRelatorios() {
        new TelaRelatorios().setVisible(true);
    }
    
    private void sairDoSistema() {
        int opcao = JOptionPane.showConfirmDialog(this,
            "Deseja realmente sair do sistema?",
            "Confirmar saída",
            JOptionPane.YES_NO_OPTION);
        
        if (opcao == JOptionPane.YES_OPTION) {
            dispose();
            new TelaLogin().setVisible(true);
        }
    }
}
