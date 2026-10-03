package br.edu.cs.poo.ac.seguro.mediators;

import br.edu.cs.poo.ac.seguro.daos.SeguradoEmpresaDAO;
import br.edu.cs.poo.ac.seguro.entidades.SeguradoEmpresa;

public class SeguradoEmpresaMediator {

    // Singleton
    private static SeguradoEmpresaMediator instancia = new SeguradoEmpresaMediator();

    private SeguradoEmpresaMediator() {}

    public static SeguradoEmpresaMediator getInstancia() {
        return instancia;
    }

    // Atributos inicializados na própria definição (conforme especificação)
    SeguradoMediator seguradoMediator = SeguradoMediator.getInstancia();
    private SeguradoEmpresaDAO seguradoEmpresaDAO = new SeguradoEmpresaDAO();

    /**
     * Valida o CNPJ: não pode ser nulo/branco e deve ser um CNPJ válido.
     * Retorna mensagem de erro, ou null se válido.
     */
    public String validarCnpj(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj)) {
            return "CNPJ inválido";
        }
        if (!ValidadorCpfCnpj.ehCnpjValido(cnpj)) {
            return "CNPJ inválido";
        }
        return null;
    }

    /**
     * Valida o faturamento: deve ser maior que zero.
     * Retorna mensagem de erro, ou null se válido.
     */
    public String validarFaturamento(double faturamento) {
        if (faturamento <= 0) {
            return "Faturamento inválido";
        }
        return null;
    }

    /**
     * Valida todos os dados de um SeguradoEmpresa.
     * Retorna a primeira mensagem de erro encontrada, ou null se tudo for válido.
     */
    public String validarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg;

        msg = seguradoMediator.validarNome(seg.getNome());
        if (msg != null) return msg;

        msg = seguradoMediator.validarEndereco(seg.getEndereco());
        if (msg != null) return msg;

        msg = seguradoMediator.validarDataCriacao(seg.getDataAbertura());
        if (msg != null) return msg;

        msg = validarCnpj(seg.getCnpj());
        if (msg != null) return msg;

        msg = validarFaturamento(seg.getFaturamento());
        if (msg != null) return msg;

        return null;
    }

    /**
     * Inclui um SeguradoEmpresa após validar os dados.
     * Retorna null em caso de sucesso, ou mensagem de erro.
     */
    public String incluirSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) {
            return msg;
        }
        boolean incluido = seguradoEmpresaDAO.incluir(seg);
        if (!incluido) {
            return "Segurado já existente";
        }
        return null;
    }

    /**
     * Altera um SeguradoEmpresa após validar os dados.
     * Retorna null em caso de sucesso, ou mensagem de erro.
     */
    public String alterarSeguradoEmpresa(SeguradoEmpresa seg) {
        String msg = validarSeguradoEmpresa(seg);
        if (msg != null) {
            return msg;
        }
        boolean alterado = seguradoEmpresaDAO.alterar(seg);
        if (!alterado) {
            return "Segurado não encontrado";
        }
        return null;
    }

    /**
     * Exclui um SeguradoEmpresa pelo CNPJ.
     * Retorna null em caso de sucesso, ou mensagem de erro.
     */
    public String excluirSeguradoEmpresa(String cnpj) {
        if (StringUtils.ehNuloOuBranco(cnpj)) {
            return "CNPJ inválido";
        }
        boolean excluido = seguradoEmpresaDAO.excluir(cnpj);
        if (!excluido) {
            return "Segurado não encontrado";
        }
        return null;
    }

    /**
     * Busca um SeguradoEmpresa pelo CNPJ.
     * Retorna o objeto encontrado, ou null se não existir.
     */
    public SeguradoEmpresa buscarSeguradoEmpresa(String cnpj) {
        return seguradoEmpresaDAO.buscar(cnpj);
    }
}
