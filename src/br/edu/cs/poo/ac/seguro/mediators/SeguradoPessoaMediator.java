package br.edu.cs.poo.ac.seguro.mediators;

import java.time.LocalDate;
import br.edu.cs.poo.ac.seguro.daos.SeguradoPessoaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoPessoa;

public class SeguradoPessoaMediator {

    // Singleton
    private static SeguradoPessoaMediator instancia = new SeguradoPessoaMediator();

    private SeguradoPessoaMediator() {}

    public static SeguradoPessoaMediator getInstancia() {
        return instancia;
    }

    // Atributos inicializados na própria definição (conforme especificação)
    SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();
    private SeguradoPessoaDAO seguradoPessoaDAO = new SeguradoPessoaDAO();

    /**
     * Valida o CPF:
     * - Não pode ser nulo nem em branco -> "CPF deve ser informado"
     * - Tamanho deve ser 11 caracteres -> "CPF deve ter 11 caracteres"
     * - Dígitos verificadores válidos -> "CPF com dígito inválido"
     */
    public String validarCpf(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
            return "CPF deve ser informado";
        }
        if (cpf.length() != 11) {
            return "CPF deve ter 11 caracteres";
        }
        if (!ValidadorCpfCnpj.ehCpfValido(cpf)) {
            return "CPF com d\u00EDgito inv\u00E1lido";
        }
        return null;
    }

    /**
     * Valida a renda: deve ser maior ou igual a zero.
     */
    public String validarRenda(double renda) {
        if (renda < 0) {
            return "Renda deve ser maior ou igual \u00E0 zero";
        }
        return null;
    }

    /**
     * Valida todos os dados de um SeguradoPessoa.
     */
    public String validarSeguradoPessoa(SeguradoPessoa seg) {
        String msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (msg != null) return msg;

        if (seg.getDataNascimento() == null) {
            return "Data do nascimento deve ser informada";
        }
        if (seg.getDataNascimento().isAfter(LocalDate.now())) {
            return "Data do nascimento deve ser menor ou igual \u00E0 data atual";
        }

        msg = validarCpf(seg.getCpf());
        if (msg != null) return msg;

        msg = validarRenda(seg.getRenda());
        if (msg != null) return msg;

        return null;
    }

    /**
     * Inclui um SeguradoPessoa após validar os dados.
     */
    public String incluirSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) {
            return msg;
        }
        boolean incluido = seguradoPessoaDAO.incluir(seg);
        if (!incluido) {
            return "CPF do segurado pessoa j\u00E1 existente";
        }
        return null;
    }

    /**
     * Altera um SeguradoPessoa após validar os dados.
     */
    public String alterarSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) {
            return msg;
        }
        boolean alterado = seguradoPessoaDAO.alterar(seg);
        if (!alterado) {
            return "CPF do segurado pessoa n\u00E3o existente";
        }
        return null;
    }

    /**
     * Exclui um SeguradoPessoa pelo CPF.
     */
    public String excluirSeguradoPessoa(String cpf) {
        boolean excluido = seguradoPessoaDAO.excluir(cpf);
        if (!excluido) {
            return "CPF do segurado pessoa n\u00E3o existente";
        }
        return null;
    }

    /**
     * Busca um SeguradoPessoa pelo CPF.
     */
    public SeguradoPessoa buscarSeguradoPessoa(String cpf) {
        return seguradoPessoaDAO.buscar(cpf);
    }
}
