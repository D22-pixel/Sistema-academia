
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuPrincipal extends JFrame {

    private Usuarios usuarioLogado;

    private JButton[] topBtns;
    private JButton ativoTopBtn = null;
    private JButton ativoSubBtn = null;
    private JPanel subNavBar;
    private JPanel contentArea;

    private static final String[][] CATEGORIAS = {
        {"⌂", "HOME"},
        {"☺", "CADASTROS"},
        {"✓", "MATRÍCULAS"},
        {"♦", "TREINOS"},
        {"▦", "RELATÓRIOS"},
        {"▣", "PRODUTOS"},
        {"$", "VENDAS"},
        {"→", "SAIR"}
    };

    private static final String[][][] SUBITENS = {
        {},
        {{"Alunos"}, {"Planos de Mensalidade"}},
        {{"Nova Matrícula"}, {"Consultar Matrículas"}},
        {{"Planos de Treino"}},
        {{"Gerar Relatórios"}},
        {{"Gerenciar Produtos"}, {"Consultar Estoque"}},
        {{"Registrar Venda"}},
        {}
    };

    private static final String[][] HOME_CARDS = {
        {"👤", "Cadastro de Alunos", "Adicionar e consultar alunos"},
        {"📋", "Gerenciar Planos", "Planos de mensalidade"},
        {"✅", "Novas Matrículas", "Realizar matrículas"},
        {"🔍", "Consultar Matrículas", "Matrículas ativas e pendentes"},
        {"💪", "Planos de Treino", "Gerenciar treinos dos alunos"},
        {"📦", "Produtos & Estoque", "Cadastrar e controlar estoque"}
    };

    private static final String[] HOME_ACAO = {
        "Alunos", "Planos de Mensalidade", "Nova Matrícula",
        "Consultar Matrículas", "Planos de Treino", "Gerenciar Produtos"
    };

    private static final Color NAV_BG = new Color(0x0D0D0D);
    private static final Color NAV_ACTIVE_BG = new Color(0x0A1A3A);
    private static final Color SUBNAV_BG = new Color(0x061828);

    public MenuPrincipal(Usuarios usuario) {
        this.usuarioLogado = usuario;
        setTitle("Academia — Sistema de Gestão");
        setSize(1060, 680);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(TelaBase.COR_BG_MAIN);
        setLayout(new BorderLayout());
        add(criarTopBar(), BorderLayout.NORTH);
        add(criarCentro(), BorderLayout.CENTER);
        add(criarRodape(), BorderLayout.SOUTH);
        mostrarDashboard();
    }

    private JPanel criarTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(NAV_BG);
        bar.setPreferredSize(new Dimension(0, 68));
        bar.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, TelaBase.COR_ACCENT));

        JPanel logoCell = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                g.setColor(new Color(0x060606));
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        logoCell.setOpaque(false);
        logoCell.setPreferredSize(new Dimension(130, 68));
        logoCell.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0x222222)));
        JLabel lblIco = new JLabel("⬡");
        lblIco.setFont(resolverFonteIcone(22));
        lblIco.setForeground(Color.WHITE);
        JLabel lblNome = new JLabel("ACADEMIA");
        lblNome.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblNome.setForeground(new Color(0x9ECFFF));
        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        logoCell.add(lblIco, gc);
        gc.gridy = 1;
        logoCell.add(lblNome, gc);

        JPanel navRow = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        navRow.setOpaque(false);
        topBtns = new JButton[CATEGORIAS.length];
        for (int i = 0; i < CATEGORIAS.length; i++) {
            final int idx = i;
            topBtns[i] = criarTopBtn(CATEGORIAS[i][0], CATEGORIAS[i][1]);
            topBtns[i].addActionListener(e -> onTopBtnClick(idx));
            navRow.add(topBtns[i]);
        }

        JLabel lblUser = new JLabel("  " + usuarioLogado.getNomeUsuario()
                + "  ·  " + usuarioLogado.getCargo() + "   ");
        lblUser.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblUser.setForeground(TelaBase.COR_TEXTO_SEC);

        bar.add(logoCell, BorderLayout.WEST);
        bar.add(navRow, BorderLayout.CENTER);
        bar.add(lblUser, BorderLayout.EAST);
        return bar;
    }

    private static Font resolverFonteIcone(int tamanho) {
        String[] candidatas = {
            "Segoe UI Symbol",   
            "Segoe UI Emoji",    
            "Apple Symbols",     
            "Symbola",         
            "DejaVu Sans",      
            "Dialog"         
        };
        java.awt.GraphicsEnvironment ge =
                java.awt.GraphicsEnvironment.getLocalGraphicsEnvironment();
        java.util.Set<String> disponiveis = new java.util.HashSet<>(
                java.util.Arrays.asList(ge.getAvailableFontFamilyNames()));
        for (String nome : candidatas) {
            if (disponiveis.contains(nome)) {
                return new Font(nome, Font.PLAIN, tamanho);
            }
        }
        return new Font("Dialog", Font.PLAIN, tamanho);
    }

    private JButton criarTopBtn(String icone, String texto) {
        final Color[] bg = {NAV_BG};
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(bg[0]);
                g2.fillRect(0, 0, getWidth(), getHeight());
                if (Boolean.TRUE.equals(getClientProperty("ativo"))) {
                    g2.setColor(TelaBase.COR_ACCENT2);
                    g2.fillRect(0, getHeight() - 3, getWidth(), 3);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setLayout(new BorderLayout(0, 2));
        btn.setPreferredSize(new Dimension(110, 68));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(9, 0, 8, 0));

        JLabel lblI = new JLabel(icone, SwingConstants.CENTER);
        lblI.setFont(resolverFonteIcone(18));
        lblI.setForeground(Color.WHITE);
        JLabel lblT = new JLabel(texto, SwingConstants.CENTER);
        lblT.setFont(new Font("Segoe UI", Font.BOLD, 10));
        lblT.setForeground(new Color(0xBBBBBB));
        lblT.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 0));

        btn.add(lblI, BorderLayout.CENTER);
        btn.add(lblT, BorderLayout.SOUTH);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!Boolean.TRUE.equals(btn.getClientProperty("ativo"))) {
                    bg[0] = new Color(0x1A1A1A);
                    lblT.setForeground(Color.WHITE);
                    btn.repaint();
                }
            }

            public void mouseExited(MouseEvent e) {
                if (!Boolean.TRUE.equals(btn.getClientProperty("ativo"))) {
                    bg[0] = NAV_BG;
                    lblT.setForeground(new Color(0xBBBBBB));
                    btn.repaint();
                }
            }
        });
        return btn;
    }

    private JPanel criarCentro() {
        JPanel centro = new JPanel(new BorderLayout());
        centro.setBackground(TelaBase.COR_BG_MAIN);

        subNavBar = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        subNavBar.setBackground(SUBNAV_BG);
        subNavBar.setPreferredSize(new Dimension(0, 42));
        subNavBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, TelaBase.COR_BORDER));
        subNavBar.setVisible(false);

        contentArea = new JPanel(new BorderLayout());
        contentArea.setBackground(TelaBase.COR_BG_MAIN);

        centro.add(subNavBar, BorderLayout.NORTH);
        centro.add(contentArea, BorderLayout.CENTER);
        return centro;
    }

    private JPanel criarRodape() {
        JPanel rod = new JPanel(new FlowLayout(FlowLayout.CENTER));
        rod.setBackground(TelaBase.COR_BG_PANEL);
        rod.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, TelaBase.COR_BORDER));
        JLabel lbl = new JLabel("© 2025 — Sistema de Academia — Denise Saldanha dos Santos");
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        lbl.setForeground(TelaBase.COR_TEXTO_SEC);
        rod.add(lbl);
        return rod;
    }

    private void onTopBtnClick(int idx) {
        if (ativoTopBtn != null) {
            ativoTopBtn.putClientProperty("ativo", false);
            ativoTopBtn.repaint();
        }
        if (idx == 7) {
            sair();
            return;
        }
        ativoTopBtn = topBtns[idx];
        ativoTopBtn.putClientProperty("ativo", true);
        ativoTopBtn.repaint();
        if (idx == 0) {
            subNavBar.setVisible(false);
            mostrarDashboard();
            return;
        }
        mostrarSubNav(idx);
    }

    private void mostrarSubNav(int catIdx) {
        subNavBar.removeAll();
        ativoSubBtn = null;
        String[][] subs = SUBITENS[catIdx];
        if (subs.length == 0) {
            subNavBar.setVisible(false);
            return;
        }

        for (String[] sub : subs) {
            final String rotulo = sub[0];
            JButton btn = criarSubNavBtn(rotulo);
            btn.addActionListener(e -> {
                if (ativoSubBtn != null) {
                    ativoSubBtn.putClientProperty("ativo", false);
                    ativoSubBtn.repaint();
                }
                ativoSubBtn = btn;
                btn.putClientProperty("ativo", true);
                btn.repaint();
                executarSubItem(rotulo);
            });
            subNavBar.add(btn);
        }
        subNavBar.setVisible(true);
        subNavBar.revalidate();
        subNavBar.repaint();
        mostrarPrompt(CATEGORIAS[catIdx][1]);
    }

    private JButton criarSubNavBtn(String texto) {
        final Color BG_NORMAL = new Color(0x0D2A50);
        final Color BG_HOVER = TelaBase.COR_ACCENT;
        final Color BG_ATIVO = TelaBase.COR_ACCENT2;
        final Color[] bg = {BG_NORMAL};

        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(bg[0]);
                g2.fillRect(0, 0, getWidth(), getHeight());
                if (Boolean.TRUE.equals(getClientProperty("ativo"))) {
                    g2.setColor(new Color(0x42A5F5));
                    g2.fillRect(0, getHeight() - 3, getWidth(), 3);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btn.setForeground(Color.WHITE);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(0, 24, 2, 24));
        btn.setPreferredSize(new Dimension(200, 42));

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!Boolean.TRUE.equals(btn.getClientProperty("ativo"))) {
                    bg[0] = BG_HOVER;
                    btn.repaint();
                }
            }

            public void mouseExited(MouseEvent e) {
                if (!Boolean.TRUE.equals(btn.getClientProperty("ativo"))) {
                    bg[0] = BG_NORMAL;
                    btn.repaint();
                }
            }
        });
        return btn;
    }

    private void executarSubItem(String rotulo) {
        switch (rotulo) {
            case "Alunos" ->
                new TelaCadastroAlunos().setVisible(true);
            case "Planos de Mensalidade" ->
                new TelaGerenciarPlanos().setVisible(true);
            case "Nova Matrícula" ->
                new TelaMatriculas().setVisible(true);
            case "Consultar Matrículas" ->
                new TelaConsultarMatriculas().setVisible(true);
            case "Planos de Treino" ->
                new TelaPlanosTreino().setVisible(true);
            case "Gerar Relatórios" ->
                new TelaRelatorios().setVisible(true);
            case "Gerenciar Produtos" ->
                new TelaProduto().setVisible(true);
            case "Consultar Estoque" ->
                new TelaConsultaEstoque().setVisible(true);
            case "Registrar Venda" ->
                new TelaVendas().setVisible(true);
        }
    }

    private void mostrarDashboard() {
        contentArea.removeAll();
        JPanel root = new JPanel(new BorderLayout(16, 16));
        root.setBackground(TelaBase.COR_BG_MAIN);
        root.setBorder(BorderFactory.createEmptyBorder(28, 28, 20, 28));

        JPanel welcome = new JPanel(new BorderLayout(0, 6));
        welcome.setOpaque(false);
        JLabel lblBem = new JLabel("Bem-vindo, " + usuarioLogado.getNomeUsuario() + "!");
        lblBem.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblBem.setForeground(Color.WHITE);
        JLabel lblSub = new JLabel("Selecione uma categoria no menu superior para começar.");
        lblSub.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSub.setForeground(TelaBase.COR_TEXTO_SEC);
        welcome.add(lblBem, BorderLayout.NORTH);
        welcome.add(lblSub, BorderLayout.SOUTH);

        JPanel grid = new JPanel(new GridLayout(2, 3, 14, 14));
        grid.setOpaque(false);
        for (int i = 0; i < HOME_CARDS.length; i++) {
            final String acao = HOME_ACAO[i];
            grid.add(criarCardDashboard(HOME_CARDS[i][0], HOME_CARDS[i][1], HOME_CARDS[i][2],
                    () -> executarSubItem(acao)));
        }

        root.add(welcome, BorderLayout.NORTH);
        root.add(grid, BorderLayout.CENTER);
        contentArea.add(root, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
    }

    private void mostrarPrompt(String categoria) {
        contentArea.removeAll();
        JPanel p = new JPanel(new BorderLayout());
        p.setBackground(TelaBase.COR_BG_MAIN);
        JLabel lbl = new JLabel("Selecione uma opção em  " + categoria, SwingConstants.CENTER);
        lbl.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lbl.setForeground(TelaBase.COR_TEXTO_SEC);
        p.add(lbl, BorderLayout.CENTER);
        contentArea.add(p, BorderLayout.CENTER);
        contentArea.revalidate();
        contentArea.repaint();
    }

    private JPanel criarCardDashboard(String icone, String titulo, String desc, Runnable acao) {
        final Color BG_NORMAL = TelaBase.COR_BG_PANEL;
        final Color BG_HOVER = TelaBase.COR_BG_CARD;
        final Color[] bg = {BG_NORMAL};

        JPanel card = new JPanel(new BorderLayout(0, 12)) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg[0]);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
            }
        };
        card.setOpaque(false);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(TelaBase.COR_BORDER, 1),
                BorderFactory.createEmptyBorder(28, 20, 22, 20)));
        card.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JLabel lblIco = new JLabel(icone, SwingConstants.CENTER);
        lblIco.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 36));
        lblIco.setForeground(TelaBase.COR_ACCENT2);

        JPanel txtPanel = new JPanel(new GridLayout(2, 1, 0, 5));
        txtPanel.setOpaque(false);

        JLabel lblTitulo = new JLabel(titulo, SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblDesc = new JLabel(desc, SwingConstants.CENTER);
        lblDesc.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblDesc.setForeground(new Color(0x90CAF9));

        txtPanel.add(lblTitulo);
        txtPanel.add(lblDesc);

        card.add(lblIco, BorderLayout.CENTER);
        card.add(txtPanel, BorderLayout.SOUTH);

        card.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                bg[0] = BG_HOVER;
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(TelaBase.COR_ACCENT2, 2),
                        BorderFactory.createEmptyBorder(27, 19, 21, 19)));
                card.repaint();
            }

            public void mouseExited(MouseEvent e) {
                bg[0] = BG_NORMAL;
                card.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(TelaBase.COR_BORDER, 1),
                        BorderFactory.createEmptyBorder(28, 20, 22, 20)));
                card.repaint();
            }

            public void mouseClicked(MouseEvent e) {
                acao.run();
            }
        });
        return card;
    }

    private void sair() {
        if (ativoTopBtn != null) {
            ativoTopBtn.putClientProperty("ativo", false);
            ativoTopBtn.repaint();
            ativoTopBtn = null;
        }
        int op = JOptionPane.showConfirmDialog(this, "Deseja realmente sair do sistema?",
                "Confirmar saída", JOptionPane.YES_NO_OPTION);
        if (op == JOptionPane.YES_OPTION) {
            dispose();
            new TelaLogin().setVisible(true);
        }
    }
}