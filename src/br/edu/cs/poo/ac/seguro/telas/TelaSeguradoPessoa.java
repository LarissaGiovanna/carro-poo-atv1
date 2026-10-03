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
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.text.MaskFormatter;

import br.edu.cs.poo.ac.seguro.entidades.Endereco;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;

/**
 * Tela gr\u00E1fica em Java Swing para o CRUD completo de Segurado Pessoa F\u00EDsica.
 * Contempla as 4 opera\u00E7\u00F5es t\u00EDpicas (Buscar, Incluir, Alterar, Excluir) em uma \u00FAnica tela.
 * Possui como atributo o mediator {@link SeguradoPessoaMediator}.
 */
public class TelaSeguradoPessoa extends JFrame {

    private static final long serialVersionUID = 1L;

    // Requisito 4: Atributo mediator da tela
    private SeguradoPessoaMediator seguradoPessoaMediator = SeguradoPessoaMediator.getInstancia();

    // Formatadores de data e máscaras
    private static final DateTimeFormatter DATA_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final String[] UFS = {
        "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA",
        "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN",
        "RS", "RO", "RR", "SC", "SP", "SE", "TO"
    };

    // Componentes visuais aderentes à natureza de cada dado
    private JFormattedTextField txtCpf;
    private JTextField txtNome;
    private JFormattedTextField txtDataNascimento;
    private JSpinner spnRenda;
    private JSpinner spnBonus;

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

    public TelaSeguradoPessoa() {
        super("Cadastro de Segurado Pessoa F\u00EDsica - Sistema de Seguros");
        configurarJanela();
        inicializarComponentes();
    }

    private void configurarJanela() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(780, 680);
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

        JLabel lblTitulo = new JLabel("Cadastro de Segurado Pessoa F\u00EDsica");
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

        // --- SEÇÃO 1: Dados Pessoais ---
        JPanel pnlDadosPessoais = new JPanel(new GridBagLayout());
        pnlDadosPessoais.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Dados do Segurado",
                0, 0, new Font("Segoe UI", Font.BOLD, 13), new Color(15, 23, 42)));

        GridBagConstraints gbcDP = new GridBagConstraints();
        gbcDP.insets = new Insets(5, 5, 5, 5);
        gbcDP.fill = GridBagConstraints.HORIZONTAL;

        // CPF (com máscara de CPF)
        gbcDP.gridx = 0; gbcDP.gridy = 0; gbcDP.weightx = 0.0;
        pnlDadosPessoais.add(new JLabel("CPF (11 d\u00EDgitos):"), gbcDP);

        try {
            MaskFormatter maskCpf = new MaskFormatter("###.###.###-##");
            maskCpf.setPlaceholderCharacter('_');
            txtCpf = new JFormattedTextField(maskCpf);
        } catch (ParseException e) {
            txtCpf = new JFormattedTextField();
        }
        txtCpf.setColumns(14);
        gbcDP.gridx = 1; gbcDP.gridy = 0; gbcDP.weightx = 0.6;
        pnlDadosPessoais.add(txtCpf, gbcDP);

        btnBuscar = new JButton("Buscar por CPF");
        btnBuscar.setForeground(new Color(15, 23, 42));
        btnBuscar.setToolTipText("Busca o segurado cadastrado com o CPF informado");
        btnBuscar.addActionListener(e -> acaoBuscar());
        gbcDP.gridx = 2; gbcDP.gridy = 0; gbcDP.weightx = 0.4;
        pnlDadosPessoais.add(btnBuscar, gbcDP);

        // Nome
        gbcDP.gridx = 0; gbcDP.gridy = 1; gbcDP.weightx = 0.0;
        pnlDadosPessoais.add(new JLabel("Nome Completo:"), gbcDP);

        txtNome = new JTextField(25);
        gbcDP.gridx = 1; gbcDP.gridy = 1; gbcDP.gridwidth = 2; gbcDP.weightx = 1.0;
        pnlDadosPessoais.add(txtNome, gbcDP);
        gbcDP.gridwidth = 1;

        // Data Nascimento (com máscara de data)
        gbcDP.gridx = 0; gbcDP.gridy = 2; gbcDP.weightx = 0.0;
        pnlDadosPessoais.add(new JLabel("Data Nascimento (dd/mm/aaaa):"), gbcDP);

        try {
            MaskFormatter maskData = new MaskFormatter("##/##/####");
            maskData.setPlaceholderCharacter('_');
            txtDataNascimento = new JFormattedTextField(maskData);
        } catch (ParseException e) {
            txtDataNascimento = new JFormattedTextField();
        }
        txtDataNascimento.setColumns(10);
        gbcDP.gridx = 1; gbcDP.gridy = 2; gbcDP.gridwidth = 2; gbcDP.weightx = 1.0;
        pnlDadosPessoais.add(txtDataNascimento, gbcDP);
        gbcDP.gridwidth = 1;

        // Renda (JSpinner numérico com suporte a decimais)
        gbcDP.gridx = 0; gbcDP.gridy = 3; gbcDP.weightx = 0.0;
        pnlDadosPessoais.add(new JLabel("Renda Mensal (R$):"), gbcDP);

        spnRenda = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 999999999.0, 100.0));
        gbcDP.gridx = 1; gbcDP.gridy = 3; gbcDP.weightx = 0.5;
        pnlDadosPessoais.add(spnRenda, gbcDP);

        // Bônus (JSpinner numérico)
        gbcDP.gridx = 0; gbcDP.gridy = 4; gbcDP.weightx = 0.0;
        pnlDadosPessoais.add(new JLabel("B\u00F4nus Inicial:"), gbcDP);

        spnBonus = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 999999999.0, 10.0));
        gbcDP.gridx = 1; gbcDP.gridy = 4; gbcDP.weightx = 0.5;
        pnlDadosPessoais.add(spnBonus, gbcDP);

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 1.0;
        pnlCentral.add(pnlDadosPessoais, gbc);

        // --- SEÇÃO 2: Endereço ---
        JPanel pnlEndereco = new JPanel(new GridBagLayout());
        pnlEndereco.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Endere\u00E7o",
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

    private String extrairCpfDigitos() {
        String texto = txtCpf.getText();
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

    private LocalDate extrairDataNascimento() {
        String texto = txtDataNascimento.getText();
        if (texto == null || texto.trim().isEmpty() || texto.contains("_")) {
            return null;
        }
        try {
            return LocalDate.parse(texto.trim(), DATA_FORMATTER);
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    private SeguradoPessoa montarSeguradoPessoaDosCampos() {
        String cpf = extrairCpfDigitos();
        String nome = txtNome.getText() != null ? txtNome.getText().trim() : "";
        LocalDate dataNasc = extrairDataNascimento();

        double renda = ((Number) spnRenda.getValue()).doubleValue();
        double bonusDouble = ((Number) spnBonus.getValue()).doubleValue();
        BigDecimal bonus = BigDecimal.valueOf(bonusDouble);

        String logradouro = txtLogradouro.getText() != null ? txtLogradouro.getText().trim() : "";
        String numero = txtNumero.getText() != null ? txtNumero.getText().trim() : "";
        String complemento = txtComplemento.getText() != null ? txtComplemento.getText().trim() : "";
        String cep = extrairCepDigitos();
        String cidade = txtCidade.getText() != null ? txtCidade.getText().trim() : "";
        String estado = (String) cbEstado.getSelectedItem();
        String pais = txtPais.getText() != null ? txtPais.getText().trim() : "";

        Endereco end = new Endereco(logradouro, cep, numero, complemento, pais, estado, cidade);
        return new SeguradoPessoa(nome, end, dataNasc, bonus, cpf, renda);
    }

    private void preencherCampos(SeguradoPessoa seg) {
        if (seg == null) return;
        txtNome.setText(seg.getNome());
        if (seg.getCpf() != null) {
            txtCpf.setText(seg.getCpf());
        }
        if (seg.getDataNascimento() != null) {
            txtDataNascimento.setText(seg.getDataNascimento().format(DATA_FORMATTER));
        } else {
            txtDataNascimento.setText("");
        }
        spnRenda.setValue(seg.getRenda());
        if (seg.getBonus() != null) {
            spnBonus.setValue(seg.getBonus().doubleValue());
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
        txtCpf.setText("");
        txtNome.setText("");
        txtDataNascimento.setText("");
        spnRenda.setValue(0.0);
        spnBonus.setValue(0.0);

        txtLogradouro.setText("");
        txtNumero.setText("");
        txtComplemento.setText("");
        txtCep.setText("");
        txtCidade.setText("");
        cbEstado.setSelectedItem("PE");
        txtPais.setText("Brasil");
    }

    private void acaoBuscar() {
        String cpf = extrairCpfDigitos();
        if (cpf == null || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o CPF para realizar a busca.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        SeguradoPessoa seg = seguradoPessoaMediator.buscarSeguradoPessoa(cpf);
        if (seg != null) {
            preencherCampos(seg);
            JOptionPane.showMessageDialog(this, "Segurado Pessoa F\u00EDsica encontrado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Segurado Pessoa F\u00EDsica n\u00E3o encontrado para o CPF: " + cpf,
                    "N\u00E3o Encontrado", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void acaoIncluir() {
        SeguradoPessoa seg = montarSeguradoPessoaDosCampos();
        String erro = seguradoPessoaMediator.incluirSeguradoPessoa(seg);

        if (erro != null) {
            JOptionPane.showMessageDialog(this, erro, "Erro ao Incluir", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Segurado Pessoa F\u00EDsica inclu\u00EDdo com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void acaoAlterar() {
        SeguradoPessoa seg = montarSeguradoPessoaDosCampos();
        String erro = seguradoPessoaMediator.alterarSeguradoPessoa(seg);

        if (erro != null) {
            JOptionPane.showMessageDialog(this, erro, "Erro ao Alterar", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Segurado Pessoa F\u00EDsica alterado com sucesso!",
                    "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void acaoExcluir() {
        String cpf = extrairCpfDigitos();
        if (cpf == null || cpf.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o CPF do segurado a ser exclu\u00EDdo.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmacao = JOptionPane.showConfirmDialog(this,
                "Tem certeza que deseja excluir o Segurado com CPF: " + cpf + "?",
                "Confirmar Exclus\u00E3o", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

        if (confirmacao == JOptionPane.YES_OPTION) {
            String erro = seguradoPessoaMediator.excluirSeguradoPessoa(cpf);
            if (erro != null) {
                JOptionPane.showMessageDialog(this, erro, "Erro ao Excluir", JOptionPane.ERROR_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Segurado Pessoa F\u00EDsica exclu\u00EDdo com sucesso!",
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
            TelaSeguradoPessoa tela = new TelaSeguradoPessoa();
            tela.setVisible(true);
        });
    }
}
