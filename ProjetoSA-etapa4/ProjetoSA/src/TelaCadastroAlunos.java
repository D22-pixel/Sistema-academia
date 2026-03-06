
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
import javax.swing.JSplitPane;
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
public class TelaCadastroAlunos extends TelaBase {

    private JTextField txtNome, txtCPF, txtTelefone, txtEmail;
    private JButton btnSalvar, btnLimpar, btnEditar;
    private JTable tabelaAlunos;
    private DefaultTableModel modeloTabela;
    private Alunos alunoEmEdicao = null;
    private AlunosDAO alunoDAO = new AlunosDAO();

    public TelaCadastroAlunos() {
        super("Cadastro de Alunos");
        setSize(1060, 780);
        setLocationRelativeTo(null);
        inicializarUI();
        atualizarTabela();
    }

    private void inicializarUI() {
        JPanel root = new JPanel(new BorderLayout(14, 14));
        root.setBackground(COR_BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JPanel topo = new JPanel(new BorderLayout(0, 4));
        topo.setOpaque(false);
        topo.add(criarTituloPagina("👤  Cadastro de Alunos"), BorderLayout.NORTH);
        topo.add(criarSubtitulo("Adicionar, editar e consultar alunos"), BorderLayout.SOUTH);
        root.add(topo, BorderLayout.NORTH);

        JPanel cardForm = criarCard("Dados do Aluno");
        JPanel form = new JPanel(new GridBagLayout());
        form.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(7, 8, 7, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNome = criarCampoTexto(28);
        txtCPF = criarCampoTexto(18);
        txtTelefone = criarCampoTexto(18);
        txtEmail = criarCampoTexto(28);

        addRow(form, gbc, 0, "Nome Completo:", txtNome, 3);
        addRow(form, gbc, 1, "CPF:", txtCPF, 1);
        addRow(form, gbc, 2, "Telefone:", txtTelefone, 1);
        addRow(form, gbc, 3, "E-mail:", txtEmail, 3);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 4));
        btnPanel.setOpaque(false);
        btnSalvar = criarBotaoPrimario("✔  Salvar Aluno");
        btnSalvar.addActionListener(e -> salvarAluno());
        btnLimpar = criarBotaoSecundario("✕  Limpar");
        btnLimpar.addActionListener(e -> limparCampos());
        btnEditar = criarBotaoSecundario("✏  Editar Selecionado");
        btnEditar.addActionListener(e -> editarAluno());
        btnPanel.add(btnSalvar);
        btnPanel.add(btnLimpar);
        btnPanel.add(btnEditar);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.gridwidth = 4;
        form.add(btnPanel, gbc);
        cardForm.add(form, BorderLayout.CENTER);

        JPanel cardTabela = criarCard("Alunos Cadastrados");
        cardTabela.setPreferredSize(new Dimension(0, 320));
        String[] cols = {"ID", "Nome", "CPF", "Telefone", "E-mail", "Data Cadastro"};
        modeloTabela = new DefaultTableModel(cols, 0) {
            public boolean isCellEditable(int r, int c) {
                return false;
            }
        };
        tabelaAlunos = new JTable(modeloTabela);
        estilizarTabela(tabelaAlunos);
        cardTabela.add(criarScrollEscuro(tabelaAlunos), BorderLayout.CENTER);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT, cardForm, cardTabela);
        split.setDividerLocation(310);
        split.setDividerSize(6);
        split.setResizeWeight(0.35);
        split.setOpaque(false);
        split.setBorder(null);
        split.setBackground(COR_BG_MAIN);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        rodape.setOpaque(false);
        JButton btnVoltar = criarBotaoSecundario("← Voltar ao Menu");
        btnVoltar.addActionListener(e -> dispose());
        rodape.add(btnVoltar);

        root.add(split, BorderLayout.CENTER);
        root.add(rodape, BorderLayout.SOUTH);

        painelConteudo.removeAll();
        painelConteudo.add(root, BorderLayout.CENTER);
        painelConteudo.revalidate();
    }

    private void addRow(JPanel form, GridBagConstraints gbc, int row,
            String label, JTextField field, int span) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        form.add(criarLabel(label), gbc);
        gbc.gridx = 1;
        gbc.gridwidth = span;
        form.add(field, gbc);
    }

    private void editarAluno() {
        int linha = tabelaAlunos.getSelectedRow();
        if (linha == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um aluno!");
            return;
        }
        int id = Integer.parseInt(modeloTabela.getValueAt(linha, 0).toString());
        for (Alunos a : alunoDAO.listarTodos()) {
            if (a.getId() == id) {
                alunoEmEdicao = a;
                txtNome.setText(a.getNome());
                txtCPF.setText(a.getCPF());
                txtCPF.setEnabled(false);
                txtTelefone.setText(a.getTelefone());
                txtEmail.setText(a.getEmail());
                btnSalvar.setText("✔  Atualizar Aluno");
                break;
            }
        }
    }

    private void salvarAluno() {
        String nome = txtNome.getText().trim(), cpf = txtCPF.getText().trim();
        if (nome.isEmpty() || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nome e CPF são obrigatórios!");
            return;
        }
        if (alunoEmEdicao != null) {
            alunoEmEdicao.setNome(nome);
            alunoEmEdicao.setTelefone(txtTelefone.getText());
            alunoEmEdicao.setEmail(txtEmail.getText());
            alunoDAO.salvarOuAtualizar(alunoEmEdicao);
            JOptionPane.showMessageDialog(this, "Aluno atualizado!");
            alunoEmEdicao = null;
        } else {
            alunoDAO.salvarOuAtualizar(new Alunos(nome, cpf, txtTelefone.getText(), txtEmail.getText()));
            JOptionPane.showMessageDialog(this, "Aluno cadastrado!");
        }
        limparCampos();
        atualizarTabela();
    }

    public void atualizarTabela() {
        modeloTabela.setRowCount(0);
        for (Alunos a : alunoDAO.listarTodos()) {
            modeloTabela.addRow(new Object[]{a.getId(), a.getNome(), a.getCPF(),
                a.getTelefone(), a.getEmail(), a.getdataCadastro()});
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtCPF.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        txtCPF.setEnabled(true);
        btnSalvar.setText("✔  Salvar Aluno");
        alunoEmEdicao = null;
    }
}
