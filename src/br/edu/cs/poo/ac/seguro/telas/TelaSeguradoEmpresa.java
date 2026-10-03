package br.edu.cs.poo.ac.seguro.telas;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.MaskFormatter;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;

/**
 * Tela gr\u00E1fica em Java Swing para o CRUD completo de Segurado Empresa (Pessoa Jur\u00EDdica).
 * Contempla as 4 opera\u00E7\u00F5es t\u00EDpicas (Buscar, Incluir, Alterar, Excluir) em uma \u00FAnica tela.
 * Possui como atributo o mediator {@link SeguradoEmpresaMediator}.
 */
public class TelaSeguradoEmpresa extends JFrame {

    private static final long serialVersionUID = 1L;

    // Requisito 4: Atributo mediator da tela
    private SeguradoEmpresaMediator seguradoEmpresaMediator = SeguradoEmpresaMediator.getInstancia();

    // Formatadores de data e listas fixas
    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String[] UFS = {
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
        "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
        "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    };

    // Componentes de entrada aderentes à natureza de cada dado
    private JFormattedTextField txtCnpj;
    private JTextField txtNome;
    private JFormattedTextField txtDataAbertura;
    private JSpinner spnFaturamento;
    private JSpinner spnBonus;

    // Requisito 5: Radio buttons para seleção booleana de Locadora de Veículos
    private JRadioButton rbLocadoraSim;
    private JRadioButton rbLocadoraNao;
    private ButtonGroup bgLocadora;

    // Componentes de Endereço
    private JTextField txtLogradouro;
    private JTextField txtNumero;
    private JTextField txtComplemento;
    private JFormattedTextField txtCep;
    private JTextField txtCidade;
    private JComboBox<String> cbEstado;
    private JTextField txtPais;

    // Botões de ação do CRUD
    private JButton btnBuscar;
    private JButton btnIncluir;
    private JButton btnAlterar;
    private JButton btnExcluir;
    private JButton btnLimpar;

    public TelaSeguradoEmpresa() {
        super("Cadastro de Segurado Empresa - Sistema de Seguros");
        configurarJanela();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(780, 720);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
    }

    private void inicializarComponentes() {
        // Cabeçalho com fundo claro e texto de alto contraste
        JPanel pnlHeader = new JPanel(new FlowLayout(FlowLayout.CENTER));
        pnlHeader.setOpaque(true);
        pnlHeader.setBackground(new Color(241, 245, 249));
        pnlHeader.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(203, 213, 225)),
                BorderFactory.createEmptyBorder(12, 10, 12, 10)
        ));

        JLabel lblTitulo = new JLabel("Cadastro de Segurado Empresa (Pessoa Jur\u00EDdica)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitulo.setForeground(new Color(15, 23, 42)); // Texto escuro legível
        pnlHeader.add(lblTitulo);
        add(pnlHeader, BorderLayout.NORTH);

        // Painel Central com Scroll
        JPanel pnlCentral = new JPanel(new GridBagLayout());
        pnlCentral.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 6, 6, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // --- SEÇÃO 1: Dados da Empresa ---
        JPanel pnlDadosEmpresa = new JPanel(new GridBagLayout());
        pnlDadosEmpresa.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Dados da Empresa",
                0, 0, new Font("Segoe UI", Font.BOLD, 13), new Color(15, 23, 42)));

        GridBagConstraints gbcDE = new GridBagConstraints();
        gbcDE.insets = new Insets(5, 5, 5, 5);
        gbcDE.fill = GridBagConstraints.HORIZONTAL;

        // CNPJ (com máscara de CNPJ)
        gbcDE.gridx = 0; gbcDE.gridy = 0; gbcDE.weightx = 0.0;
        pnlDadosEmpresa.add(new JLabel("CNPJ (14 d\u00EDgitos):"), gbcDE);

        try {
            MaskFormatter maskCnpj = new MaskFormatter("##.###.###/####-##");
            maskCnpj.setPlaceholderCharacter('_');
            txtCnpj = new JFormattedTextField(maskCnpj);
        } catch (ParseException e) {
            txtCnpj = new JFormattedTextField();
        }
        txtCnpj.setColumns(16);
        gbcDE.gridx = 1; gbcDE.gridy = 0; gbcDE.weightx = 0.6;
        pnlDadosEmpresa.add(txtCnpj, gbcDE);

        btnBuscar = new JButton("Buscar por CNPJ");
        btnBuscar.setForeground(new Color(15, 23, 42));
        btnBuscar.setToolTipText("Busca a empresa cadastrada com o CNPJ informado");
        btnBuscar.addActionListener(e -> acaoBuscar());
        gbcDE.gridx = 2; gbcDE.gridy = 0; gbcDE.weightx = 0.4;
        pnlDadosEmpresa.add(btnBuscar, gbcDE);

        // Razão Social / Nome Fantasia
        gbcDE.gridx = 0; gbcDE.gridy = 1; gbcDE.weightx = 0.0;
        pnlDadosEmpresa.add(new JLabel("Nome / Raz\u00E3o Social:"), gbcDE);

        txtNome = new JTextField(25);
        gbcDE.gridx = 1; gbcDE.gridy = 1; gbcDE.gridwidth = 2; gbcDE.weightx = 1.0;
        pnlDadosEmpresa.add(txtNome, gbcDE);
        gbcDE.gridwidth = 1;

        // Data Abertura (com máscara de data)
        gbcDE.gridx = 0; gbcDE.gridy = 2; gbcDE.weightx = 0.0;
        pnlDadosEmpresa.add(new JLabel("Data Abertura (dd/mm/aaaa):"), gbcDE);

        try {
            MaskFormatter maskData = new MaskFormatter("##/##/####");
            maskData.setPlaceholderCharacter('_');
            txtDataAbertura = new JFormattedTextField(maskData);
        } catch (ParseException e) {
            txtDataAbertura = new JFormattedTextField();
        }
        txtDataAbertura.setColumns(10);
        gbcDE.gridx = 1; gbcDE.gridy = 2; gbcDE.gridwidth = 2; gbcDE.weightx = 1.0;
        pnlDadosEmpresa.add(txtDataAbertura, gbcDE);
        gbcDE.gridwidth = 1;

        // Faturamento (JSpinner numérico)
        gbcDE.gridx = 0; gbcDE.gridy = 3; gbcDE.weightx = 0.0;
        pnlDadosEmpresa.add(new JLabel("Faturamento Mensal (R$):"), gbcDE);

        spnFaturamento = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 9999999999.0, 500.0));
        gbcDE.gridx = 1; gbcDE.gridy = 3; gbcDE.weightx = 0.5;
        pnlDadosEmpresa.add(spnFaturamento, gbcDE);

        // Bônus (JSpinner numérico)
        gbcDE.gridx = 0; gbcDE.gridy = 4; gbcDE.weightx = 0.0;
        pnlDadosEmpresa.add(new JLabel("B\u00F4nus Inicial:"), gbcDE);

        spnBonus = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 999999999.0, 10.0));
        gbcDE.gridx = 1; gbcDE.gridy = 4; gbcDE.weightx = 0.5;
        pnlDadosEmpresa.add(spnBonus, gbcDE);

        // É Locadora de Veículos (Radio buttons - Requisito 5)
        gbcDE.gridx = 0; gbcDE.gridy = 5; gbcDE.weightx = 0.0;
        pnlDadosEmpresa.add(new JLabel("\u00C9 Locadora de Ve\u00EDculos?"), gbcDE);

        JPanel pnlRadio = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        rbLocadoraSim = new JRadioButton("Sim");
        rbLocadoraNao = new JRadioButton("N\u00E3o", true);
        rbLocadoraSim.setForeground(new Color(15, 23, 42));
        rbLocadoraNao.setForeground(new Color(15, 23, 42));
        bgLocadora = new ButtonGroup();
        bgLocadora.add(rbLocadoraSim);
        bgLocadora.add(rbLocadoraNao);
        pnlRadio.add(rbLocadoraSim);
        pnlRadio.add(rbLocadoraNao);

        gbcDE.gridx = 1; gbcDE.gridy = 5; gbcDE.gridwidth = 2; gbcDE.weightx = 1.0;
        pnlDadosEmpresa.add(pnlRadio, gbcDE);
        gbcDE.gridwidth = 1;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        pnlCentral.add(pnlDadosEmpresa, gbc);

        // --- SEÇÃO 2: Endereço ---
        JPanel pnlEndereco = new JPanel(new GridBagLayout());
        pnlEndereco.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Endere\u00E7o da Empresa",
                0, 0, new Font("Segoe UI", Font.BOLD, 13), new Color(15, 23, 42)));

        GridBagConstraints gbcEnd = new GridBagConstraints();
        gbcEnd.insets = new Insets(5, 5, 5, 5);
        gbcEnd.fill = GridBagConstraints.HORIZONTAL;

        // Logradouro
        gbcEnd.gridx = 0; gbcEnd.gridy = 0; gbcEnd.weightx = 0.0;
        pnlEndereco.add(new JLabel("Logradouro:"), gbcEnd);

        txtLogradouro = new JTextField(25);
        gbcEnd.gridx = 1; gbcEnd.gridy = 0; gbcEnd.gridwidth = 3; gbcEnd.weightx = 1.0;
        pnlEndereco.add(txtLogradouro, gbcEnd);
        gbcEnd.gridwidth = 1;

        // Número e Complemento
        gbcEnd.gridx = 0; gbcEnd.gridy = 1; gbcEnd.weightx = 0.0;
        pnlEndereco.add(new JLabel("N\u00FAmero:"), gbcEnd);

        txtNumero = new JTextField(8);
        gbcEnd.gridx = 1; gbcEnd.gridy = 1; gbcEnd.weightx = 0.4;
        pnlEndereco.add(txtNumero, gbcEnd);

        gbcEnd.gridx = 2; gbcEnd.gridy = 1; gbcEnd.weightx = 0.0;
        pnlEndereco.add(new JLabel("Complemento:"), gbcEnd);

        txtComplemento = new JTextField(12);
        gbcEnd.gridx = 3; gbcEnd.gridy = 1; gbcEnd.weightx = 0.6;
        pnlEndereco.add(txtComplemento, gbcEnd);

        // CEP
        gbcEnd.gridx = 0; gbcEnd.gridy = 2; gbcEnd.weightx = 0.0;
        pnlEndereco.add(new JLabel("CEP (8 d\u00EDgitos):"), gbcEnd);

        try {
            MaskFormatter maskCep = new MaskFormatter("#####-###");
            maskCep.setPlaceholderCharacter('_');
            txtCep = new JFormattedTextField(maskCep);
        } catch (ParseException e) {
            txtCep = new JFormattedTextField();
        }
        txtCep.setColumns(10);
        gbcEnd.gridx = 1; gbcEnd.gridy = 2; gbcEnd.weightx = 0.4;
        pnlEndereco.add(txtCep, gbcEnd);

        // Cidade
        gbcEnd.gridx = 2; gbcEnd.gridy = 2; gbcEnd.weightx = 0.0;
        pnlEndereco.add(new JLabel("Cidade:"), gbcEnd);

        txtCidade = new JTextField(15);
        gbcEnd.gridx = 3; gbcEnd.gridy = 2; gbcEnd.weightx = 0.6;
        pnlEndereco.add(txtCidade, gbcEnd);

        // Estado (JComboBox - Drop down list aderente à seleção de UF)
        gbcEnd.gridx = 0; gbcEnd.gridy = 3; gbcEnd.weightx = 0.0;
        pnlEndereco.add(new JLabel("Estado (UF):"), gbcEnd);

        cbEstado = new JComboBox<>(UFS);
        cbEstado.setSelectedItem("PE");
        gbcEnd.gridx = 1; gbcEnd.gridy = 3; gbcEnd.weightx = 0.4;
        pnlEndereco.add(cbEstado, gbcEnd);

        // País
        gbcEnd.gridx = 2; gbcEnd.gridy = 3; gbcEnd.weightx = 0.0;
        pnlEndereco.add(new JLabel("Pa\u00EDs:"), gbcEnd);

        txtPais = new JTextField("Brasil", 12);
        gbcEnd.gridx = 3; gbcEnd.gridy = 3; gbcEnd.weightx = 0.6;
        pnlEndereco.add(txtPais, gbcEnd);

        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 1.0;
        pnlCentral.add(pnlEndereco, gbc);

        add(new JScrollPane(pnlCentral), BorderLayout.CENTER);

        // --- PAINEL DE BOTÕES DE AÇÃO DO CRUD ---
        JPanel pnlBotoes = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        pnlBotoes.setBorder(BorderFactory.createEmptyBorder(5, 10, 10, 10));

        btnLimpar = new JButton("Limpar");
        btnLimpar.setForeground(new Color(15, 23, 42));
        btnLimpar.addActionListener(e -> limparCampos());
        pnlBotoes.add(btnLimpar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setForeground(new Color(185, 28, 28)); // Vermelho escuro legível
        btnExcluir.addActionListener(e -> acaoExcluir());
        pnlBotoes.add(btnExcluir);

        btnAlterar = new JButton("Alterar");
        btnAlterar.setForeground(new Color(15, 23, 42));
        btnAlterar.addActionListener(e -> acaoAlterar());
        pnlBotoes.add(btnAlterar);

        btnIncluir = new JButton("Incluir");
        btnIncluir.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnIncluir.setForeground(new Color(15, 23, 42));
        btnIncluir.addActionListener(e -> acaoIncluir());
        pnlBotoes.add(btnIncluir);

        add(pnlBotoes, BorderLayout.SOUTH);
    }

    // --- MÉTODOS DE AÇÃO DO CRUD ---

    private String extrairCnpjDigitos() {
        String texto = txtCnpj.getText();
        if (texto == null) return null;
        String digitos = texto.replaceAll("[^0-9]", "");
        return digitos.isEmpty() ? texto.trim() : digitos;
    }

    private String extrairCepDigitos() {
        String texto = txtCep.getText();
        if (texto == null) return null;
        String digitos = texto.replaceAll("[^0-9]", "");
        return digitos.isEmpty() ? texto.trim() : digitos;
    }

    private LocalDate extrairDataAbertura() {
        String texto = txtDataAbertura.getText();
        if (texto == null || texto.trim().isEmpty() || texto.contains("_")) {
            return null;
        }
        try {
            return LocalDate.parse(texto.trim(), DATA_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private SeguradoEmpresa montarSeguradoEmpresaDosCampos() {
        String cnpj = extrairCnpjDigitos();
        String nome = txtNome.getText() != null ? txtNome.getText().trim() : "";
        LocalDate dataAbertura = extrairDataAbertura();

        double faturamento = ((Number) spnFaturamento.getValue()).doubleValue();
        double bonusDouble = ((Number) spnBonus.getValue()).doubleValue();
        BigDecimal bonus = BigDecimal.valueOf(bonusDouble);
        boolean ehLocadora = rbLocadoraSim.isSelected();

        String logradouro = txtLogradouro.getText() != null ? txtLogradouro.getText().trim() : "";
        String numero = txtNumero.getText() != null ? txtNumero.getText().trim() : "";
        String complemento = txtComplemento.getText() != null ? txtComplemento.getText().trim() : "";
        String cep = extrairCepDigitos();
        String cidade = txtCidade.getText() != null ? txtCidade.getText().trim() : "";
        String estado = (String) cbEstado.getSelectedItem();
        String pais = txtPais.getText() != null ? txtPais.getText().trim() : "";

        Endereco end = new Endereco(logradouro, cep, numero, complemento, pais, estado, cidade);
        return new SeguradoEmpresa(nome, end, dataAbertura, bonus, cnpj, faturamento, ehLocadora);
    }

    private void preencherCampos(SeguradoEmpresa seg) {
        if (seg == null) return;
        txtNome.setText(seg.getNome());
        if (seg.getCnpj() != null) {
            txtCnpj.setText(seg.getCnpj());
        }
        if (seg.getDataAbertura() != null) {
            txtDataAbertura.setText(seg.getDataAbertura().format(DATA_FORMATTER));
        } else {
            txtDataAbertura.setText("");
        }
        spnFaturamento.setValue(seg.getFaturamento());
        if (seg.getBonus() != null) {
            spnBonus.setValue(seg.getBonus().doubleValue());
        }

        if (seg.isEhLocadoraDeVeiculos()) {
            rbLocadoraSim.setSelected(true);
        } else {
            rbLocadoraNao.setSelected(true);
        }

        Endereco end = seg.getEndereco();
        if (end != null) {
            txtLogradouro.setText(end.getLogradouro() != null ? end.getLogradouro() : "");
            txtNumero.setText(end.getNumero() != null ? end.getNumero() : "");
            txtComplemento.setText(end.getComplemento() != null ? end.getComplemento() : "");
            if (end.getCep() != null) {
                txtCep.setText(end.getCep());
            }
            txtCidade.setText(end.getCidade() != null ? end.getCidade() : "");
            if (end.getEstado() != null) {
                cbEstado.setSelectedItem(end.getEstado().toUpperCase());
            }
            txtPais.setText(end.getPais() != null ? end.getPais() : "Brasil");
        }
    }

    private void limparCampos() {
        txtCnpj.setText("");
        txtNome.setText("");
        txtDataAbertura.setText("");
        spnFaturamento.setValue(0.0);
        spnBonus.setValue(0.0);
        rbLocadoraNao.setSelected(true);

        txtLogradouro.setText("");
        txtNumero.setText("");
        txtComplemento.setText("");
        txtCep.setText("");
        txtCidade.setText("");
        cbEstado.setSelectedItem("PE");
        txtPais.setText("Brasil");
    }

    private void acaoBuscar() {
        String cnpj = extrairCnpjDigitos();
        if (cnpj == null || cnpj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o CNPJ para realizar a busca.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SeguradoEmpresa seg = seguradoEmpresaMediator.buscarSeguradoEmpresa(cnpj);
        if (seg != null) {
            preencherCampos(seg);
            JOptionPane.showMessageDialog(this, "Segurado Empresa encontrado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Segurado Empresa n\u00E3o encontrado para o CNPJ: " + cnpj,
                    "N\u00E3o Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void acaoIncluir() {
        SeguradoEmpresa seg = montarSeguradoEmpresaDosCampos();
        String erro = seguradoEmpresaMediator.incluirSeguradoEmpresa(seg);

        if (erro != null) {
            JOptionPane.showMessageDialog(this, erro, "Erro ao Incluir", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Segurado Empresa inclu\u00EDdo com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void acaoAlterar() {
        SeguradoEmpresa seg = montarSeguradoEmpresaDosCampos();
        String erro = seguradoEmpresaMediator.alterarSeguradoEmpresa(seg);

        if (erro != null) {
            JOptionPane.showMessageDialog(this, erro, "Erro ao Alterar", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Segurado Empresa alterado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void acaoExcluir() {
        String cnpj = extrairCnpjDigitos();
        if (cnpj == null || cnpj.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o CNPJ da empresa a ser exclu\u00EDda.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir a Empresa com CNPJ: " + cnpj + "?",
                "Confirmar Exclus\u00E3o", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirmacao == JOptionPane.YES_OPTION) {
            String erro = seguradoEmpresaMediator.excluirSeguradoEmpresa(cnpj);
            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro ao Excluir", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Segurado Empresa exclu\u00EDdo com sucesso!",
                        "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                limparCampos();
            }
        }
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(() -> {
            TelaSeguradoEmpresa tela = new TelaSeguradoEmpresa();
            tela.setVisible(true);
        });
    }
}
