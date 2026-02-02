
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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author denis
 */
public class TelaMatriculas extends JFrame{
    private JComboBox<Alunos> comboAlunos;
    private JComboBox<Planos> comboPlanos;
    private JComboBox<String> comboStatus;
    private JButton btnMatricular, btnVoltar;
    private JTextArea txtResumo;
    
    public TelaMatriculas() {
        setTitle("Nova Matrícula");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelPrincipal.setBackground(Color.white);
 
        JLabel lblTitulo = new JLabel("NOVA MATRÍCULA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(155, 89, 182));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelForm = new JPanel(new GridBagLayout());
        painelForm.setBackground(Color.white);
        painelForm.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182), 2),
            "Dados da Matrícula",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(155, 89, 182)
        ));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        JLabel lblAluno = new JLabel("Selecione o Aluno:");
        lblAluno.setFont(new Font("Arial", Font.BOLD, 14));
        painelForm.add(lblAluno, gbc);
        
        comboAlunos = new JComboBox<>();
        comboAlunos.setFont(new Font("Arial", Font.PLAIN, 12));
        carregarAlunos();
        gbc.gridx = 1; gbc.gridy = 0;
        painelForm.add(comboAlunos, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        JLabel lblPlano = new JLabel("Selecione o Plano:");
        lblPlano.setFont(new Font("Arial", Font.BOLD, 14));
        painelForm.add(lblPlano, gbc);
        
        comboPlanos = new JComboBox<>();
        comboPlanos.setFont(new Font("Arial", Font.PLAIN, 12));
        comboPlanos.addActionListener(e -> atualizarResumo());
        carregarPlanos();
        gbc.gridx = 1; gbc.gridy = 1;
        painelForm.add(comboPlanos, gbc);

        gbc.gridx = 0; gbc.gridy = 2;
        JLabel lblStatus = new JLabel("Status do Pagamento:");
        lblStatus.setFont(new Font("Arial", Font.BOLD, 14));
        painelForm.add(lblStatus, gbc);
        
        String[] status = {"Ativa", "Pendente", "Cancelada"};
        comboStatus = new JComboBox<>(status);
        comboStatus.setFont(new Font("Arial", Font.PLAIN, 12));
        gbc.gridx = 1; gbc.gridy = 2;
        painelForm.add(comboStatus, gbc);

        JPanel painelResumo = new JPanel(new BorderLayout());
        painelResumo.setBackground(Color.white);
        painelResumo.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(155, 89, 182), 2),
            "Resumo da Matrícula",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(155, 89, 182)
        ));
        
        txtResumo = new JTextArea(8, 40);
        txtResumo.setEditable(false);
        txtResumo.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtResumo.setBackground(new Color(245, 245, 245));
        JScrollPane scrollResumo = new JScrollPane(txtResumo);
        painelResumo.add(scrollResumo, BorderLayout.CENTER);
 
        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        painelCentral.setBackground(Color.white);
        painelCentral.add(painelForm, BorderLayout.NORTH);
        painelCentral.add(painelResumo, BorderLayout.CENTER);
        
        painelPrincipal.add(painelCentral, BorderLayout.CENTER);

        JPanel painelBotoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        painelBotoes.setBackground(Color.white);
        
        btnMatricular = new JButton("Confirmar Matrícula");
        btnMatricular.setBackground(new Color(0, 153, 76));
        btnMatricular.setForeground(Color.black);
        btnMatricular.setFont(new Font("Arial", Font.BOLD, 14));
        btnMatricular.setFocusPainted(false);
        btnMatricular.setPreferredSize(new Dimension(200, 40));
        btnMatricular.addActionListener(e -> realizarMatricula());
        
        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setBackground(new Color(108, 117, 125));
        btnVoltar.setForeground(Color.black);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 14));
        btnVoltar.setFocusPainted(false);
        btnVoltar.setPreferredSize(new Dimension(200, 40));
        btnVoltar.addActionListener(e -> dispose());
        
        painelBotoes.add(btnMatricular);
        painelBotoes.add(btnVoltar);
        
        painelPrincipal.add(painelBotoes, BorderLayout.SOUTH);
        
        add(painelPrincipal);
        
        atualizarResumo();
    }
    
    private void carregarAlunos() {
        comboAlunos.removeAllItems();
        List<Alunos> alunos = DadosSistema.getInstancia().getAlunos();
        
        if (alunos.isEmpty()) {
            comboAlunos.addItem(null);
            JOptionPane.showMessageDialog(this,
                "Não há alunos cadastrados!\nCadastre alunos primeiro.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
        } else {
            for (Alunos aluno : alunos) {
                comboAlunos.addItem(aluno);
            }
        }
    }
    
    private void carregarPlanos() {
        comboPlanos.removeAllItems();
        List<Planos> planos = DadosSistema.getInstancia().getPlanos();
        
        if (planos.isEmpty()) {
            comboPlanos.addItem(null);
            JOptionPane.showMessageDialog(this,
                "Não há planos cadastrados!\nCadastre planos primeiro.",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
        } else {
            for (Planos plano : planos) {
                comboPlanos.addItem(plano);
            }
        }
    }
    
    private void atualizarResumo() {
        Alunos alunoSelecionado = (Alunos) comboAlunos.getSelectedItem();
        Planos planoSelecionado = (Planos) comboPlanos.getSelectedItem();
        
        if (alunoSelecionado == null || planoSelecionado == null) {
            txtResumo.setText("Selecione um aluno e um plano para ver o resumo.");
            return;
        }
        
        StringBuilder resumo = new StringBuilder();
        resumo.append("=".repeat(50)).append("\n");
        resumo.append("           RESUMO DA MATRÍCULA\n");
        resumo.append("=".repeat(50)).append("\n\n");
        
        resumo.append("ALUNO:\n");
        resumo.append("  Nome: ").append(alunoSelecionado.getNome()).append("\n");
        resumo.append("  CPF: ").append(alunoSelecionado.getCPF()).append("\n");
        if (alunoSelecionado.getTelefone() != null) {
            resumo.append("  Telefone: ").append(alunoSelecionado.getTelefone()).append("\n");
        }
        
        resumo.append("\nPLANO SELECIONADO:\n");
        resumo.append("  ").append(planoSelecionado.getNomePlano()).append("\n");
        if (planoSelecionado.getDescricao() != null) {
            resumo.append("  ").append(planoSelecionado.getDescricao()).append("\n");
        }
        resumo.append("  Duração: ").append(planoSelecionado.getDuracaoDias()).append(" dias\n");
        
        resumo.append("\nVALOR:\n");
        resumo.append("  R$ ").append(String.format("%.2f", planoSelecionado.getValor())).append("\n");
        
        resumo.append("\n").append("=".repeat(50)).append("\n");
        
        txtResumo.setText(resumo.toString());
    }
    
    private void realizarMatricula() {
        Alunos alunoSelecionado = (Alunos) comboAlunos.getSelectedItem();
        Planos planoSelecionado = (Planos) comboPlanos.getSelectedItem();
        String status = (String) comboStatus.getSelectedItem();
        
        if (alunoSelecionado == null || planoSelecionado == null) {
            JOptionPane.showMessageDialog(this,
                "Selecione um aluno e um plano!",
                "Aviso",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        Matriculas novaMatricula = new Matriculas(alunoSelecionado, planoSelecionado, status);
        DadosSistema.getInstancia().adicionarMatricula(novaMatricula);
        
        JOptionPane.showMessageDialog(this,
            "Matrícula realizada com sucesso!\n\n" +
            "Aluno: " + alunoSelecionado.getNome() + "\n" +
            "Plano: " + planoSelecionado.getNomePlano() + "\n" +
            "Valor: R$ " + String.format("%.2f", planoSelecionado.getValor()) + "\n" +
            "Data de início: " + novaMatricula.getDataInicio() + "\n" +
            "Data de término: " + novaMatricula.getDataFim(),
            "Matrícula Confirmada",
            JOptionPane.INFORMATION_MESSAGE);
        
        dispose();
    }
}
