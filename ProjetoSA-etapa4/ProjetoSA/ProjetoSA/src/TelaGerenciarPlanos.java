
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;


public class TelaGerenciarPlanos extends JFrame {
    private JTextField txtNomePlano, txtValor, txtDuracao;
    private JTextArea txtDescricao;
    private JButton btnSalvar, btnLimpar, btnVoltar;
    private JTable tabelaPlanos;
    private DefaultTableModel modeloTabela;
    
    public TelaGerenciarPlanos() {
        setTitle("Gerenciar Planos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        inicializarComponentes();
        carregarPlanos();
    }
    
    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelPrincipal.setBackground(Color.white);

        JLabel lblTitulo = new JLabel("GERENCIAMENTO DE PLANOS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0,10 ,10));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(Color.white);
        painelForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 10, 10), 2),
            "Dados do Plano",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(0, 10, 10)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Nome do Plano:"), gbc);
        
        txtNomePlano = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        painelForm.add(txtNomePlano, gbc);
  
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        painelForm.add(new JLabel("Valor (R$):"), gbc);
        
        txtValor = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        painelForm.add(txtValor, gbc);
 
        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(new JLabel("Duração (dias):"), gbc);
        
        txtDuracao = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        painelForm.add(txtDuracao, gbc);
 
        gbc.gridx = 0; gbc.gridy = 3;
        painelForm.add(new JLabel("Descrição:"), gbc);
        
        txtDescricao = new JTextArea(3, 25);
        txtDescricao.setLineWrap(true);
        txtDescricao.setWrapStyleWord(true);
        JScrollPane scrollDescricao = new JScrollPane(txtDescricao);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2;
        painelForm.add(scrollDescricao, gbc);

        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoesForm.setBackground(Color.white);
        
        btnSalvar = new JButton("Salvar Plano");
        btnSalvar.setBackground(new Color(0,10,10));
        btnSalvar.setForeground(Color.black);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 12));
        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(e -> salvarPlano());
        
        btnLimpar = new JButton("Limpar Campos");
        btnLimpar.setBackground(new Color(0,10 , 10));
        btnLimpar.setForeground(Color.black);
        btnLimpar.setFont(new Font("Arial", Font.BOLD, 12));
        btnLimpar.setFocusPainted(false);
        btnLimpar.addActionListener(e -> limparCampos());
        
        painelBotoesForm.add(btnSalvar);
        painelBotoesForm.add(btnLimpar);
        
        gbc.gridx = 0; gbc.gridy = 4; gbc.gridwidth = 3;
        painelForm.add(painelBotoesForm, gbc);
 
        JPanel painelTabela = new JPanel(new BorderLayout());
        painelTabela.setBackground(Color.white);
        painelTabela.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0,10 ,10), 2),
            "Planos Cadastrados",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(0,10 ,10)
        ));
        
        String[] colunas = {"ID", "Nome do Plano", "Valor (R$)", "Duração (dias)", "Descrição"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaPlanos = new JTable(modeloTabela);
        tabelaPlanos.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaPlanos.setRowHeight(25);
        tabelaPlanos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaPlanos.getTableHeader().setBackground(new Color(0,10 ,10));
        tabelaPlanos.getTableHeader().setForeground(Color.black);
        
        JScrollPane scrollPane = new JScrollPane(tabelaPlanos);
        painelTabela.add(scrollPane, BorderLayout.CENTER);

        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        painelCentral.setBackground(Color.white);
        painelCentral.add(painelForm, BorderLayout.NORTH);
        painelCentral.add(painelTabela, BorderLayout.CENTER);
        
        painelPrincipal.add(painelCentral, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(Color.white);
        
        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setBackground(new Color(108, 117, 125));
        btnVoltar.setForeground(Color.black);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnVoltar);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void salvarPlano() {
        String nome = txtNomePlano.getText().trim();
        String valorStr = txtValor.getText().trim();
        String duracaoStr = txtDuracao.getText().trim();
        String descricao = txtDescricao.getText().trim();
        
        if (nome.isEmpty() || valorStr.isEmpty() || duracaoStr.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Preencha todos os campos obrigatórios!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        try {
            double valor = Double.parseDouble(valorStr);
            int duracao = Integer.parseInt(duracaoStr);
            
            if (valor <= 0 || duracao <= 0) {
                JOptionPane.showMessageDialog(this,
                    "Valor e duração devem ser maiores que zero!",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            Planos novoPlano = new Planos(nome, descricao, valor, duracao);
            DadosSistema.getInstancia().adicionarPlano(novoPlano);
            
            JOptionPane.showMessageDialog(this,
                "Plano cadastrado com sucesso!",
                "Sucesso",
                JOptionPane.INFORMATION_MESSAGE);
            
            limparCampos();
            carregarPlanos();
            
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Valor e duração devem ser números válidos!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void limparCampos() {
        txtNomePlano.setText("");
        txtValor.setText("");
        txtDuracao.setText("");
        txtDescricao.setText("");
        txtNomePlano.requestFocus();
    }
    
    private void carregarPlanos() {
        modeloTabela.setRowCount(0);
        List<Planos> planos = DadosSistema.getInstancia().getPlanos();
        
        for (Planos plano : planos) {
            Object[] linha = {
                plano.getId(),
                plano.getNomePlano(),
                String.format("%.2f", plano.getValor()),
                plano.getDuracaoDias(),
                plano.getDescricao() != null ? plano.getDescricao() : "-"
            };
            modeloTabela.addRow(linha);
        }
    }
}
