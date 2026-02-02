
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
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author denis
 */
public class TelaCadastroAlunos extends JFrame {
    private JTextField txtNome, txtCPF, txtTelefone, txtEmail;
    private JButton btnSalvar, btnLimpar, btnVoltar;
    private JTable tabelaAlunos;
    private DefaultTableModel modeloTabela;
    
    public TelaCadastroAlunos() {
        setTitle("Cadastro de Alunos");
        setSize(900, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        inicializarComponentes();
        carregarAlunos();
    }
    
    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelPrincipal.setBackground(Color.white);

        JLabel lblTitulo = new JLabel("CADASTRO DE ALUNOS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 10, 10));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);
        
        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(Color.white);
        painelForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0,10,10), 2),
            "Dados do Aluno",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(0, 10, 10)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        gbc.gridx = 0; gbc.gridy = 0;
        painelForm.add(new JLabel("Nome Completo:"), gbc);
        
        txtNome = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 2;
        painelForm.add(txtNome, gbc);
 
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        painelForm.add(new JLabel("CPF:"), gbc);
        
        txtCPF = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 1;
        painelForm.add(txtCPF, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        painelForm.add(new JLabel("Telefone:"), gbc);
        
        txtTelefone = new JTextField(15);
        gbc.gridx = 1; gbc.gridy = 2;
        painelForm.add(txtTelefone, gbc);
  
        gbc.gridx = 0; gbc.gridy = 3;
        painelForm.add(new JLabel("E-mail:"), gbc);
        
        txtEmail = new JTextField(25);
        gbc.gridx = 1; gbc.gridy = 3; gbc.gridwidth = 2;
        painelForm.add(txtEmail, gbc);

        JPanel painelBotoesForm = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoesForm.setBackground(Color.white);
        
        btnSalvar = new JButton("Salvar Aluno");
        btnSalvar.setBackground(new Color(250, 250, 250));
        btnSalvar.setForeground(Color.black);
        btnSalvar.setFont(new Font("Arial", Font.BOLD, 12));
        btnSalvar.setFocusPainted(false);
        btnSalvar.addActionListener(e -> salvarAluno());
        
        btnLimpar = new JButton("Limpar Campos");
        btnLimpar.setBackground(new Color(250, 250, 250));
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
            BorderFactory.createLineBorder(new Color(0, 10, 10), 2),
            "Alunos Cadastrados",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(0, 10, 10)
        ));
        
        String[] colunas = {"ID", "Nome", "CPF", "Telefone", "E-mail", "Data Cadastro"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tabelaAlunos = new JTable(modeloTabela);
        tabelaAlunos.setFont(new Font("Arial", Font.PLAIN, 12));
        tabelaAlunos.setRowHeight(25);
        tabelaAlunos.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tabelaAlunos.getTableHeader().setBackground(new Color(250, 250, 250));
        tabelaAlunos.getTableHeader().setForeground(Color.black);
        
        JScrollPane scrollPane = new JScrollPane(tabelaAlunos);
        painelTabela.add(scrollPane, BorderLayout.CENTER);

        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        painelCentral.setBackground(Color.white);
        painelCentral.add(painelForm, BorderLayout.NORTH);
        painelCentral.add(painelTabela, BorderLayout.CENTER);
        
        painelPrincipal.add(painelCentral, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelBotoes.setBackground(Color.white);
        
        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setBackground(new Color(250, 250, 250));
        btnVoltar.setForeground(Color.black);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnVoltar);
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private void salvarAluno() {
        String nome = txtNome.getText().trim();
        String cpf = txtCPF.getText().trim();
        String telefone = txtTelefone.getText().trim();
        String email = txtEmail.getText().trim();
        
        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Nome e CPF são obrigatórios!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (DadosSistema.getInstancia().buscarAlunoPorCPF(cpf) != null) {
            JOptionPane.showMessageDialog(this,
                "CPF já cadastrado no sistema!",
                "Erro",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        Alunos novoAluno = new Alunos(nome, cpf, telefone, email);
        DadosSistema.getInstancia().adicionarAluno(novoAluno);
        
        JOptionPane.showMessageDialog(this,
            "Aluno cadastrado com sucesso!",
            "Sucesso",
            JOptionPane.INFORMATION_MESSAGE);
        
        limparCampos();
        carregarAlunos();
    }
    
    private void limparCampos() {
        txtNome.setText("");
        txtCPF.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtNome.requestFocus();
    }
    
    private void carregarAlunos() {
        modeloTabela.setRowCount(0);
        List<Alunos> alunos = DadosSistema.getInstancia().getAlunos();
        
        for (Alunos aluno : alunos) {
            Object[] linha = {
                aluno.getId(),
                aluno.getNome(),
                aluno.getCPF(),
                aluno.getTelefone() != null ? aluno.getTelefone() : "-",
                aluno.getEmail() != null ? aluno.getEmail() : "-",
                aluno.getdataCadastro()
            };
            modeloTabela.addRow(linha);
        }
    }
}
