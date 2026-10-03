package br.edu.cs.poo.ac.seguro.mediators;

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
     * Valida o CPF: não pode ser nulo/branco e deve ser um CPF válido.
     * Retorna mensagem de erro, ou null se válido.
     */
    public String validarCpf(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
            return "CPF inválido";
        }
        if (!ValidadorCpfCnpj.ehCpfValido(cpf)) {
            return "CPF inválido";
        }
        return null;
    }

    /**
     * Valida a renda: deve ser maior que zero.
     * Retorna mensagem de erro, ou null se válida.
     */
    public String validarRenda(double renda) {
        if (renda <= 0) {
            return "Renda inválida";
        }
        return null;
    }

    /**
     * Valida todos os dados de um SeguradoPessoa.
     * Retorna a primeira mensagem de erro encontrada, ou null se tudo for válido.
     */
    public String validarSeguradoPessoa(SeguradoPessoa seg) {
        String msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (msg != null) return msg;

        msg = seguradoMediator.validarDataCriacao(seg.getDataNascimento());
        if (msg != null) return msg;

        msg = validarCpf(seg.getCpf());
        if (msg != null) return msg;

        msg = validarRenda(seg.getRenda());
        if (msg != null) return msg;

        return null;
    }

    /**
     * Inclui um SeguradoPessoa após validar os dados.
     * Retorna null em caso de sucesso, ou mensagem de erro.
     */
    public String incluirSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) {
            return msg;
        }
        boolean incluido = seguradoPessoaDAO.incluir(seg);
        if (!incluido) {
            return "Segurado já existente";
        }
        return null;
    }

    /**
     * Altera um SeguradoPessoa após validar os dados.
     * Retorna null em caso de sucesso, ou mensagem de erro.
     */
    public String alterarSeguradoPessoa(SeguradoPessoa seg) {
        String msg = validarSeguradoPessoa(seg);
        if (msg != null) {
            return msg;
        }
        boolean alterado = seguradoPessoaDAO.alterar(seg);
        if (!alterado) {
            return "Segurado não encontrado";
        }
        return null;
    }

    /**
     * Exclui um SeguradoPessoa pelo CPF.
     * Retorna null em caso de sucesso, ou mensagem de erro.
     */
    public String excluirSeguradoPessoa(String cpf) {
        if (StringUtils.ehNuloOuBranco(cpf)) {
            return "CPF inválido";
        }
        boolean excluido = seguradoPessoaDAO.excluir(cpf);
        if (!excluido) {
            return "Segurado não encontrado";
        }
        return null;
    }

    /**
     * Busca um SeguradoPessoa pelo CPF.
     * Retorna o objeto encontrado, ou null se não existir.
     */
    public SeguradoPessoa buscarSeguradoPessoa(String cpf) {
        return seguradoPessoaDAO.buscar(cpf);
    }
}
