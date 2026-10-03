package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.ApoliceDAO;
import br.edu.cs.poo.ac.seguro.entidades.Apolice;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TesteApoliceDAO extends TesteDAO {
    private ApoliceDAO dao = new ApoliceDAO();

    protected Class getClasse() {
        return Apolice.class;
    }

    // Metodo auxiliar para criar uma Apolice de teste com numero definido
    private Apolice criarApolice(String numero) {
        Veiculo veiculo = new Veiculo("XYZ5678", 2021, null, null, null);
        Apolice apolice = new Apolice(veiculo, BigDecimal.valueOf(1000.0),
                BigDecimal.valueOf(500.0), BigDecimal.valueOf(50000.0));
        apolice.setNumero(numero);
        return apolice;
    }

    @Test
    public void teste01() {
        // buscar deve retornar a apolice quando ela existe
        String numero = "APO00001";
        Apolice apolice = criarApolice(numero);
        cadastro.incluir(apolice, numero);
        Apolice resultado = dao.buscar(numero);
        Assertions.assertNotNull(resultado);
    }

    @Test
    public void teste02() {
        // buscar deve retornar null quando o numero nao existe
        String numero = "APO00002";
        cadastro.incluir(criarApolice(numero), numero);
        Apolice resultado = dao.buscar("APO99999");
        Assertions.assertNull(resultado);
    }

    @Test
    public void teste03() {
        // excluir deve retornar true quando a apolice existe
        String numero = "APO00003";
        cadastro.incluir(criarApolice(numero), numero);
        boolean ret = dao.excluir(numero);
        Assertions.assertTrue(ret);
    }

    @Test
    public void teste04() {
        // excluir deve retornar false quando a apolice nao existe
        String numero = "APO00004";
        cadastro.incluir(criarApolice(numero), numero);
        boolean ret = dao.excluir("APO99999");
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste05() {
        // incluir deve retornar true e o objeto deve poder ser buscado depois
        String numero = "APO00005";
        boolean ret = dao.incluir(criarApolice(numero));
        Assertions.assertTrue(ret);
        Apolice resultado = dao.buscar(numero);
        Assertions.assertNotNull(resultado);
    }

    @Test
    public void teste06() {
        // incluir deve retornar false quando a apolice ja existe (duplicata)
        String numero = "APO00006";
        Apolice apolice = criarApolice(numero);
        cadastro.incluir(apolice, numero);
        boolean ret = dao.incluir(apolice);
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste07() {
        // alterar deve retornar false quando a apolice nao existe
        String numero = "APO00007";
        boolean ret = dao.alterar(criarApolice(numero));
        Assertions.assertFalse(ret);
        Apolice resultado = dao.buscar(numero);
        Assertions.assertNull(resultado);
    }

    @Test
    public void teste08() {
        // alterar deve retornar true quando a apolice existe
        String numero = "APO00008";
        Apolice apolice = criarApolice(numero);
        cadastro.incluir(apolice, numero);
        Apolice apoliceAlterada = new Apolice(apolice.getVeiculo(),
                BigDecimal.valueOf(2000.0), BigDecimal.valueOf(800.0), BigDecimal.valueOf(80000.0));
        apoliceAlterada.setNumero(numero);
        boolean ret = dao.alterar(apoliceAlterada);
        Assertions.assertTrue(ret);
    }
}
