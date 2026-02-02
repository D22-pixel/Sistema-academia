
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicComboBoxUI;
import javax.swing.plaf.basic.BasicComboPopup;
import javax.swing.plaf.basic.BasicScrollBarUI;

public class TelaBase extends JFrame {

    private static final Dimension SCREEN = Toolkit.getDefaultToolkit().getScreenSize();

    protected static final float ESCALA = Math.min(SCREEN.width / 1920f, SCREEN.height / 1080f);

    protected static final Color COR_BG_MAIN = new Color(0x03112A);
    protected static final Color COR_BG_PANEL = new Color(0x05224D);
    protected static final Color COR_BG_CARD = new Color(0x07306B);
    protected static final Color COR_ACCENT = new Color(0x1565C0);
    protected static final Color COR_ACCENT2 = new Color(0x1976D2);
    protected static final Color COR_TEXTO = new Color(0xF5F5F5);
    protected static final Color COR_TEXTO_SEC = new Color(0xB0BEC5);
    protected static final Color COR_BORDER = new Color(0x0D3B7A);
    protected static final Color COR_HOVER = new Color(0x0A2F60);
    protected static final Color COR_SUCCESS = new Color(0x1B8A4E);
    protected static final Color COR_DANGER = new Color(0xB71C1C);
    protected static final Color COR_WARNING = new Color(0xF57F17);

    protected static final Color NAV_BG = new Color(0x0D0D0D);
    protected static final Color SUBNAV_BG = new Color(0x061828);
    protected static final Color SUBNAV_ACT = new Color(0x0C3060);

    protected JPanel painelConteudo;
    protected JPanel menuSuperior;

    private JPanel logoPnl;
    private JPanel navBtnsPnl;
    private JLabel lblLogoIcoRef;
    private JLabel lblLogoTxtRef;

    private static final int BASE_NAV_H = 62;
    private static final int BASE_LOGO_W = 130;
    private static final int BASE_NAV_BTN_W = 110;

    public TelaBase(String titulo) {
        setTitle(titulo);

        int largura = Math.max(800, (int) (SCREEN.width * 0.80));
        int altura = Math.max(550, (int) (SCREEN.height * 0.80));
        setSize(largura, altura);
        setMinimumSize(new Dimension(800, 550));

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(COR_BG_MAIN);

        menuSuperior = new JPanel(new BorderLayout());
        menuSuperior.setBackground(NAV_BG);
        menuSuperior.setPreferredSize(new Dimension(0, navH()));
        menuSuperior.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, COR_ACCENT));

        logoPnl = new JPanel(new BorderLayout());
        logoPnl.setPreferredSize(new Dimension(logoW(), navH()));
        logoPnl.setBackground(new Color(0x060606));
        logoPnl.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, new Color(0x222222)));

        JPanel logoInner = new JPanel(new GridBagLayout());
        logoInner.setOpaque(false);

        lblLogoIcoRef = new JLabel("⬡--⬡");
        lblLogoIcoRef.setFont(new Font("Arial", Font.BOLD, escalarDinamico(20)));
        lblLogoIcoRef.setForeground(Color.WHITE);

        lblLogoTxtRef = new JLabel("ACADEMIA");
        lblLogoTxtRef.setFont(new Font("Arial", Font.BOLD, escalarDinamico(12)));
        lblLogoTxtRef.setForeground(new Color(0x9ECFFF));

        GridBagConstraints gc = new GridBagConstraints();
        gc.gridx = 0;
        gc.gridy = 0;
        logoInner.add(lblLogoIcoRef, gc);
        gc.gridy = 1;
        logoInner.add(lblLogoTxtRef, gc);
        logoPnl.add(logoInner, BorderLayout.CENTER);

        navBtnsPnl = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        navBtnsPnl.setOpaque(false);
        navBtnsPnl.add(criarBotaoNavIcone("⌂", "HOME"));

        JLabel lblUser = new JLabel("Sub-tela  ");
        lblUser.setFont(new Font("Arial", Font.PLAIN, escalarDinamico(15)));
        lblUser.setForeground(COR_TEXTO_SEC);

        menuSuperior.add(logoPnl, BorderLayout.WEST);
        menuSuperior.add(navBtnsPnl, BorderLayout.CENTER);
        menuSuperior.add(lblUser, BorderLayout.EAST);
        add(menuSuperior, BorderLayout.NORTH);

        painelConteudo = new JPanel(new BorderLayout());
        painelConteudo.setBackground(COR_BG_MAIN);
        add(painelConteudo, BorderLayout.CENTER);

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                SwingUtilities.invokeLater(() -> atualizarLayout());
            }
        });
    }

    protected float getEscalaAtual() {
        int w = getWidth();
        int h = getHeight();
        if (w <= 0 || h <= 0) {
            return ESCALA;
        }
        return Math.min(w / 1920f, h / 1080f);
    }

    protected int escalarDinamico(int px) {
        float fator = Math.max(getEscalaAtual(), 0.75f);
        return Math.max(1, Math.round(px * fator));
    }

    protected int navH() {
        return escalarDinamico(BASE_NAV_H);
    }

    protected int logoW() {
        return escalarDinamico(BASE_LOGO_W);
    }

    protected void atualizarLayout() {
        int novoNavH = navH();
        int novoLogoW = logoW();

        menuSuperior.setPreferredSize(new Dimension(0, novoNavH));

        logoPnl.setPreferredSize(new Dimension(novoLogoW, novoNavH));

        if (lblLogoIcoRef != null) {
            lblLogoIcoRef.setFont(new Font("Arial", Font.BOLD, escalarDinamico(20)));
        }
        if (lblLogoTxtRef != null) {
            lblLogoTxtRef.setFont(new Font("Arial", Font.BOLD, escalarDinamico(12)));
        }

        for (Component c : navBtnsPnl.getComponents()) {
            if (c instanceof JButton) {
                c.setPreferredSize(new Dimension(escalarDinamico(BASE_NAV_BTN_W), novoNavH));
            }
        }

        menuSuperior.revalidate();
        menuSuperior.repaint();
        revalidate();
        repaint();
    }

    public static int escalar(int px) {
        return Math.max(1, Math.round(px * Math.max(ESCALA, 0.75f)));
    }

    private static JButton fabricarBotao(String texto, Color bgNormal, Color bgHover, Color fgCor) {
        final Color[] bg = {bgNormal};
        JButton btn = new JButton(texto) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(bg[0]);
                g2.fillRect(0, 0, getWidth(), getHeight());
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setFont(new Font("Arial", Font.BOLD, escalar(15)));
        btn.setForeground(fgCor);
        btn.setFocusPainted(false);
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setBorder(BorderFactory.createEmptyBorder(
                escalar(9), escalar(22), escalar(9), escalar(22)));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                bg[0] = bgHover;
                btn.repaint();
            }

            public void mouseExited(MouseEvent e) {
                bg[0] = bgNormal;
                btn.repaint();
            }
        });
        return btn;
    }

    public static JButton criarBotaoPrimario(String texto) {
        return fabricarBotao(texto, COR_ACCENT2, COR_ACCENT, Color.WHITE);
    }

    public static JButton criarBotaoSecundario(String texto) {
        return fabricarBotao(texto, COR_ACCENT2, COR_ACCENT, Color.WHITE);
    }

    public static JButton criarBotaoDanger(String texto) {
        return fabricarBotao(texto, new Color(0x8B0000), COR_DANGER, Color.WHITE);
    }

    protected static JButton criarBotaoNavIcone(String icone, String texto) {
        final Color[] bg = {NAV_BG};
        JButton btn = new JButton() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(bg[0]);
                g2.fillRect(0, 0, getWidth(), getHeight());
                if (Boolean.TRUE.equals(getClientProperty("ativo"))) {
                    g2.setColor(COR_ACCENT2);
                    g2.fillRect(0, getHeight() - 3, getWidth(), 3);
                }
                g2.dispose();
                super.paintComponent(g);
            }
        };
        btn.setLayout(new BorderLayout(0, 2));
        btn.setPreferredSize(new Dimension(escalar(BASE_NAV_BTN_W), escalar(BASE_NAV_H)));
        btn.setContentAreaFilled(false);
        btn.setOpaque(false);
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createEmptyBorder(escalar(9), 0, escalar(8), 0));

        JLabel lblIco = new JLabel(icone, SwingConstants.CENTER);
        lblIco.setFont(new Font("Arial", Font.BOLD, escalar(18)));
        lblIco.setForeground(Color.WHITE);

        JLabel lblTxt = new JLabel(texto, SwingConstants.CENTER);
        lblTxt.setFont(new Font("Arial", Font.BOLD, escalar(10)));
        lblTxt.setForeground(new Color(0xCCCCCC));
        lblTxt.setBorder(BorderFactory.createEmptyBorder(0, 0, 3, 0));

        btn.add(lblIco, BorderLayout.CENTER);
        btn.add(lblTxt, BorderLayout.SOUTH);

        btn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                if (!Boolean.TRUE.equals(btn.getClientProperty("ativo"))) {
                    bg[0] = new Color(0x1A1A1A);
                    lblTxt.setForeground(Color.WHITE);
                    btn.repaint();
                }
            }

            public void mouseExited(MouseEvent e) {
                if (!Boolean.TRUE.equals(btn.getClientProperty("ativo"))) {
                    bg[0] = NAV_BG;
                    lblTxt.setForeground(new Color(0xCCCCCC));
                    btn.repaint();
                }
            }
        });
        return btn;
    }

    protected JButton criarBotaoNav(String texto) {
        return criarBotaoNavIcone("", texto);
    }

    public static JTextField criarCampoTexto(int cols) {
        JTextField tf = new JTextField(cols);
        tf.setBackground(COR_BG_CARD);
        tf.setForeground(COR_TEXTO);
        tf.setCaretColor(COR_TEXTO);
        tf.setFont(new Font("Arial", Font.PLAIN, escalar(13)));
        tf.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDER, 1),
                BorderFactory.createEmptyBorder(
                        escalar(6), escalar(10), escalar(6), escalar(10))));
        return tf;
    }

    public static JLabel criarTituloPagina(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(resolverFonteIcone(escalar(22)).deriveFont(Font.BOLD));
        lbl.setForeground(COR_TEXTO);
        lbl.setBorder(BorderFactory.createEmptyBorder(0, 0, 4, 0));
        return lbl;
    }

    public static JLabel criarSubtitulo(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, escalar(12)));
        lbl.setForeground(COR_TEXTO_SEC);
        lbl.setBorder(BorderFactory.createEmptyBorder(2, 0, escalar(8), 0));
        return lbl;
    }

    public static JLabel criarLabel(String texto) {
        JLabel lbl = new JLabel(texto);
        lbl.setFont(new Font("Arial", Font.PLAIN, escalar(12)));
        lbl.setForeground(COR_TEXTO_SEC);
        return lbl;
    }

    public static JPanel criarCard(String titulo) {
        JPanel card = new JPanel(new BorderLayout(0, escalar(8)));
        card.setBackground(COR_BG_PANEL);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDER, 1),
                BorderFactory.createEmptyBorder(
                        escalar(16), escalar(16), escalar(16), escalar(16))));
        if (titulo != null && !titulo.isEmpty()) {
            JLabel lbl = new JLabel(titulo);
            lbl.setFont(new Font("Arial", Font.BOLD, escalar(13)));
            lbl.setForeground(COR_TEXTO);
            lbl.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COR_BORDER));
            card.add(lbl, BorderLayout.NORTH);
        }
        return card;
    }

    public static <T> void estilizarCombo(JComboBox<T> cb) {
        cb.setFont(new Font("Arial", Font.PLAIN, escalar(13)));
        cb.setBackground(COR_BG_CARD);
        cb.setForeground(Color.WHITE);
        cb.setOpaque(true);

        cb.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrowBtn = new JButton("▾") {
                    @Override
                    protected void paintComponent(Graphics g) {
                        Graphics2D g2 = (Graphics2D) g.create();
                        g2.setColor(COR_ACCENT);
                        g2.fillRect(0, 0, getWidth(), getHeight());
                        g2.dispose();
                        super.paintComponent(g);
                    }
                };
                arrowBtn.setFont(new Font("Arial", Font.BOLD, escalar(11)));
                arrowBtn.setForeground(Color.WHITE);
                arrowBtn.setContentAreaFilled(false);
                arrowBtn.setFocusPainted(false);
                arrowBtn.setBorderPainted(false);
                arrowBtn.setBorder(BorderFactory.createEmptyBorder(0, 4, 0, 4));
                arrowBtn.setPreferredSize(new Dimension(escalar(28), 0));
                return arrowBtn;
            }

            @Override
            public void paintCurrentValue(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(COR_BG_CARD);
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
                @SuppressWarnings("unchecked")
                ListCellRenderer<Object> renderer = (ListCellRenderer<Object>) cb.getRenderer();
                Component c = renderer.getListCellRendererComponent(
                        new JList<>(), cb.getSelectedItem(), -1, false, false);
                c.setBackground(COR_BG_CARD);
                c.setForeground(Color.WHITE);
                c.setFont(new Font("Arial", Font.PLAIN, escalar(13)));
                c.setBounds(bounds);
                SwingUtilities.paintComponent(g, c, cb, bounds);
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                g.setColor(COR_BG_CARD);
                g.fillRect(bounds.x, bounds.y, bounds.width, bounds.height);
            }
        });

        cb.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(COR_BORDER, 1),
                BorderFactory.createEmptyBorder(
                        escalar(4), escalar(8), escalar(4), escalar(4))));

        cb.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel lbl = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);
                lbl.setOpaque(true);
                lbl.setBackground(isSelected ? COR_ACCENT : COR_BG_CARD);
                lbl.setForeground(Color.WHITE);
                lbl.setFont(new Font("Arial", Font.PLAIN, escalar(13)));
                lbl.setBorder(BorderFactory.createEmptyBorder(
                        escalar(6), escalar(12), escalar(6), escalar(12)));
                return lbl;
            }
        });
    }
    
    public static Font resolverFonteIcone(int tamanho) {
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

    public static void estilizarTabela(JTable tabela) {
         tabela.setBackground(COR_BG_PANEL);
    tabela.setForeground(COR_TEXTO);
    tabela.setFont(new Font("Arial", Font.PLAIN, escalar(12)));
    tabela.setRowHeight(escalar(30));
    tabela.setGridColor(COR_BORDER);
    tabela.setSelectionBackground(COR_ACCENT);
    tabela.setSelectionForeground(Color.WHITE);
    tabela.setShowVerticalLines(false);
    tabela.setIntercellSpacing(new Dimension(0, 1));
    tabela.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

    JTableHeader header = tabela.getTableHeader();
    header.setBackground(new Color(0x041A3A));
    header.setForeground(COR_TEXTO_SEC);
    header.setFont(new Font("Arial", Font.BOLD, escalar(12)));
    header.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, COR_BORDER));
    header.setReorderingAllowed(false);

    final Font fonteIcone = resolverFonteIcone(escalar(12));

    tabela.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(JTable t, Object val,
                boolean sel, boolean foc, int row, int col) {
            super.getTableCellRendererComponent(t, val, sel, foc, row, col);
            setOpaque(true);
            String texto = val != null ? val.toString() : "";
            boolean temSimbolo = texto.chars().anyMatch(c -> c > 127);
            setFont(temSimbolo ? fonteIcone : t.getFont());
            if (sel) {
                setBackground(COR_ACCENT);
                setForeground(Color.WHITE);
            } else {
                setBackground(row % 2 == 0 ? COR_BG_PANEL : new Color(0x062040));
                setForeground(COR_TEXTO);
            }
            setBorder(BorderFactory.createEmptyBorder(0, escalar(8), 0, escalar(8)));
            return this;
          }
       });
     }

    public static JScrollPane criarScrollEscuro(JTable tabela) {
        JScrollPane sp = new JScrollPane(tabela);
        sp.setBackground(COR_BG_PANEL);
        sp.getViewport().setBackground(COR_BG_PANEL);
        sp.setBorder(BorderFactory.createLineBorder(COR_BORDER, 1));
        sp.getVerticalScrollBar().setUI(new BasicScrollBarUI() {
            @Override
            protected void configureScrollBarColors() {
                thumbColor = COR_ACCENT;
                trackColor = COR_BG_CARD;
            }
        });
        return sp;
    }
}
