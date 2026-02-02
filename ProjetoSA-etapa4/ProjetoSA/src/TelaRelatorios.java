
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;


public class TelaRelatorios extends JFrame {
     private JTextArea txtRelatorio;
    private JButton btnAlunosCadastrados, btnPlanos, btnMatriculas, btnResumoGeral, btnVoltar;
    
    public TelaRelatorios() {
        setTitle("Relatórios do Sistema");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        inicializarComponentes();
    }
    
    private void inicializarComponentes() {
        JPanel painelPrincipal = new JPanel(new BorderLayout(10, 10));
        painelPrincipal.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        painelPrincipal.setBackground(Color.white);

        JLabel lblTitulo = new JLabel("RELATÓRIOS DO SISTEMA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        lblTitulo.setForeground(new Color(0, 10, 10));
        lblTitulo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        painelPrincipal.add(lblTitulo, BorderLayout.NORTH);

        JPanel painelBotoes = new JPanel(new GridLayout(2, 2, 10, 10));
        painelBotoes.setBackground(Color.white);
        painelBotoes.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        btnAlunosCadastrados = criarBotaoRelatorio("Alunos Cadastrados", new Color(0, 10, 10));
        btnAlunosCadastrados.addActionListener(e -> gerarRelatorioAlunos());
        
        btnPlanos = criarBotaoRelatorio("Planos Disponíveis", new Color(0, 10, 10));
        btnPlanos.addActionListener(e -> gerarRelatorioPlanos());
        
        btnMatriculas = criarBotaoRelatorio("Matrículas Ativas", new Color(10, 10, 10));
        btnMatriculas.addActionListener(e -> gerarRelatorioMatriculas());
        
        btnResumoGeral = criarBotaoRelatorio("Resumo Geral", new Color(0, 10, 10));
        btnResumoGeral.addActionListener(e -> gerarResumoGeral());
        
        painelBotoes.add(btnAlunosCadastrados);
        painelBotoes.add(btnPlanos);
        painelBotoes.add(btnMatriculas);
        painelBotoes.add(btnResumoGeral);
        
        JPanel painelRelatorio = new JPanel(new BorderLayout());
        painelRelatorio.setBackground(Color.white);
        painelRelatorio.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(new Color(0, 10, 10), 2),
            "Visualização do Relatório",
            javax.swing.border.TitledBorder.LEFT,
            javax.swing.border.TitledBorder.TOP,
            new Font("Arial", Font.BOLD, 14),
            new Color(0, 10, 10)
        ));
        
        txtRelatorio = new JTextArea();
        txtRelatorio.setFont(new Font("Monospaced", Font.PLAIN, 12));
        txtRelatorio.setEditable(false);
        txtRelatorio.setBackground(new Color(250,250,250));
        txtRelatorio.setText("Selecione um tipo de relatório acima para visualizar.");
        
        JScrollPane scrollPane = new JScrollPane(txtRelatorio);
        painelRelatorio.add(scrollPane, BorderLayout.CENTER);

        JPanel painelCentral = new JPanel(new BorderLayout(10, 10));
        painelCentral.setBackground(Color.white);
        painelCentral.add(painelBotoes, BorderLayout.NORTH);
        painelCentral.add(painelRelatorio, BorderLayout.CENTER);
        
        painelPrincipal.add(painelCentral, BorderLayout.CENTER);
 
        JPanel painelRodape = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        painelRodape.setBackground(Color.white);
        
        btnVoltar = new JButton("Voltar ao Menu");
        btnVoltar.setBackground(new Color(108, 117, 125));
        btnVoltar.setForeground(Color.black);
        btnVoltar.setFont(new Font("Arial", Font.BOLD, 12));
        btnVoltar.setFocusPainted(false);
        btnVoltar.addActionListener(e -> dispose());
        
        painelRodape.add(btnVoltar);
        painelPrincipal.add(painelRodape, BorderLayout.SOUTH);
        
        add(painelPrincipal);
    }
    
    private JButton criarBotaoRelatorio(String texto, Color cor) {
        JButton btn = new JButton(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 14));
        btn.setBackground(cor);
        btn.setForeground(Color.black);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return btn;
    }
    
    private void gerarRelatorioAlunos() {
        List<Alunos> alunos = DadosSistema.getInstancia().getAlunos();
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=".repeat(70)).append("\n");
        relatorio.append("              RELATÓRIO DE ALUNOS CADASTRADOS\n");
        relatorio.append("=".repeat(70)).append("\n\n");
        relatorio.append("Total de alunos: ").append(alunos.size()).append("\n\n");
        
        if (alunos.isEmpty()) {
            relatorio.append("Nenhum aluno cadastrado no sistema.\n");
        } else {
            for (Alunos aluno : alunos) {
                relatorio.append("-".repeat(70)).append("\n");
                relatorio.append("ID: ").append(aluno.getId()).append("\n");
                relatorio.append("Nome: ").append(aluno.getNome()).append("\n");
                relatorio.append("CPF: ").append(aluno.getCPF()).append("\n");
                relatorio.append("Telefone: ").append(aluno.getTelefone() != null ? aluno.getTelefone() : "Não informado").append("\n");
                relatorio.append("E-mail: ").append(aluno.getEmail() != null ? aluno.getEmail() : "Não informado").append("\n");
                relatorio.append("Data de Cadastro: ").append(aluno.getdataCadastro()).append("\n");
            }
        }
        
        relatorio.append("=".repeat(70)).append("\n");
        txtRelatorio.setText(relatorio.toString());
        txtRelatorio.setCaretPosition(0);
    }
    
    private void gerarRelatorioPlanos() {
        List<Planos> planos = DadosSistema.getInstancia().getPlanos();
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=".repeat(70)).append("\n");
        relatorio.append("              RELATÓRIO DE PLANOS DISPONÍVEIS\n");
        relatorio.append("=".repeat(70)).append("\n\n");
        relatorio.append("Total de planos: ").append(planos.size()).append("\n\n");
        
        if (planos.isEmpty()) {
            relatorio.append("Nenhum plano cadastrado no sistema.\n");
        } else {
            for (Planos plano : planos) {
                relatorio.append("-".repeat(70)).append("\n");
                relatorio.append("ID: ").append(plano.getId()).append("\n");
                relatorio.append("Nome: ").append(plano.getNomePlano()).append("\n");
                relatorio.append("Descrição: ").append(plano.getDescricao() != null ? plano.getDescricao() : "Sem descrição").append("\n");
                relatorio.append("Valor: R$ ").append(String.format("%.2f", plano.getValor())).append("\n");
                relatorio.append("Duração: ").append(plano.getDuracaoDias()).append(" dias\n");
            }
        }
        
        relatorio.append("=".repeat(70)).append("\n");
        txtRelatorio.setText(relatorio.toString());
        txtRelatorio.setCaretPosition(0);
    }
    
    private void gerarRelatorioMatriculas() {
        List<Matriculas> matriculas = DadosSistema.getInstancia().getMatriculas();
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=".repeat(70)).append("\n");
        relatorio.append("              RELATÓRIO DE MATRÍCULAS\n");
        relatorio.append("=".repeat(70)).append("\n\n");
        relatorio.append("Total de matrículas: ").append(matriculas.size()).append("\n\n");
        
        if (matriculas.isEmpty()) {
            relatorio.append("Nenhuma matrícula cadastrada no sistema.\n");
        } else {
            double valorTotal = 0;
            int ativas = 0, pendentes = 0, canceladas = 0;
            
            for (Matriculas matricula : matriculas) {
                relatorio.append("-".repeat(70)).append("\n");
                relatorio.append("Matrícula ID: ").append(matricula.getId()).append("\n");
                relatorio.append("Aluno: ").append(matricula.getAluno().getNome()).append("\n");
                relatorio.append("CPF: ").append(matricula.getAluno().getCPF()).append("\n");
                relatorio.append("Plano: ").append(matricula.getPlano().getNomePlano()).append("\n");
                relatorio.append("Valor: R$ ").append(String.format("%.2f", matricula.getPlano().getValor())).append("\n");
                relatorio.append("Data Início: ").append(matricula.getDataInicio()).append("\n");
                relatorio.append("Data Fim: ").append(matricula.getDataFim()).append("\n");
                relatorio.append("Status: ").append(matricula.getStatusPagamento()).append("\n");
                
                valorTotal += matricula.getPlano().getValor();
                
                switch (matricula.getStatusPagamento()) {
                    case "Ativa": ativas++; break;
                    case "Pendente": pendentes++; break;
                    case "Cancelada": canceladas++; break;
                }
            }
            
            relatorio.append("\n").append("=".repeat(70)).append("\n");
            relatorio.append("RESUMO:\n");
            relatorio.append("  Ativas: ").append(ativas).append("\n");
            relatorio.append("  Pendentes: ").append(pendentes).append("\n");
            relatorio.append("  Canceladas: ").append(canceladas).append("\n");
            relatorio.append("  Valor Total: R$ ").append(String.format("%.2f", valorTotal)).append("\n");
        }
        
        relatorio.append("=".repeat(70)).append("\n");
        txtRelatorio.setText(relatorio.toString());
        txtRelatorio.setCaretPosition(0);
    }
    
    private void gerarResumoGeral() {
        List<Alunos> alunos = DadosSistema.getInstancia().getAlunos();
        List<Planos> planos = DadosSistema.getInstancia().getPlanos();
        List<Matriculas> matriculas = DadosSistema.getInstancia().getMatriculas();
        
        StringBuilder relatorio = new StringBuilder();
        relatorio.append("=".repeat(70)).append("\n");
        relatorio.append("              RESUMO GERAL DO SISTEMA\n");
        relatorio.append("=".repeat(70)).append("\n\n");
        
        relatorio.append("ESTATÍSTICAS GERAIS:\n");
        relatorio.append("-".repeat(70)).append("\n");
        relatorio.append("Total de Alunos Cadastrados: ").append(alunos.size()).append("\n");
        relatorio.append("Total de Planos Disponíveis: ").append(planos.size()).append("\n");
        relatorio.append("Total de Matrículas: ").append(matriculas.size()).append("\n\n");
        
        if (!matriculas.isEmpty()) {
            int ativas = 0, pendentes = 0, canceladas = 0;
            double valorTotal = 0, valorAtivas = 0;
            
            for (Matriculas m : matriculas) {
                valorTotal += m.getPlano().getValor();
                
                switch (m.getStatusPagamento()) {
                    case "Ativa":
                        ativas++;
                        valorAtivas += m.getPlano().getValor();
                        break;
                    case "Pendente":
                        pendentes++;
                        break;
                    case "Cancelada":
                        canceladas++;
                        break;
                }
            }
            
            relatorio.append("MATRÍCULAS POR STATUS:\n");
            relatorio.append("-".repeat(70)).append("\n");
            relatorio.append("  Ativas: ").append(ativas).append("\n");
            relatorio.append("  Pendentes: ").append(pendentes).append("\n");
            relatorio.append("  Canceladas: ").append(canceladas).append("\n\n");
            
            relatorio.append("VALORES:\n");
            relatorio.append("-".repeat(70)).append("\n");
            relatorio.append("  Receita de Matrículas Ativas: R$ ").append(String.format("%.2f", valorAtivas)).append("\n");
            relatorio.append("  Valor Total de Matrículas: R$ ").append(String.format("%.2f", valorTotal)).append("\n\n");
        }
        
        if (!planos.isEmpty()) {
            relatorio.append("PLANOS MAIS CAROS:\n");
            relatorio.append("-".repeat(70)).append("\n");
            planos.stream()
                .sorted((p1, p2) -> Double.compare(p2.getValor(), p1.getValor()))
                .limit(3)
                .forEach(p -> relatorio.append("  ").append(p.getNomePlano())
                    .append(" - R$ ").append(String.format("%.2f", p.getValor())).append("\n"));
        }
        
        relatorio.append("\n").append("=".repeat(70)).append("\n");
        txtRelatorio.setText(relatorio.toString());
        txtRelatorio.setCaretPosition(0);
    }
}
