package br.edu.cs.poo.ac.seguro.testes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import br.edu.cs.poo.ac.seguro.daos.SinistroDAO;
import br.edu.cs.poo.ac.seguro.entidades.Sinistro;
import br.edu.cs.poo.ac.seguro.entidades.TipoSinistro;
import br.edu.cs.poo.ac.seguro.entidades.Veiculo;

public class TesteSinistroDAO extends TesteDAO {
    private SinistroDAO dao = new SinistroDAO();

    protected Class getClasse() {
        return Sinistro.class;
    }

    // Método auxiliar para criar um Sinistro de teste com numero definido
    private Sinistro criarSinistro(String numero) {
        Veiculo veiculo = new Veiculo("ABC1234", 2020, null, null, null);
        return new Sinistro(numero, veiculo, LocalDateTime.now(), LocalDateTime.now(),
                "usuario", BigDecimal.valueOf(5000.0), TipoSinistro.COLISAO);
    }

    @Test
    public void teste01() {
        // buscar deve retornar o sinistro quando ele existe
        String numero = "SIN00001";
        Sinistro sin = criarSinistro(numero);
        cadastro.incluir(sin, numero);
        Sinistro resultado = dao.buscar(numero);
        Assertions.assertNotNull(resultado);
    }

    @Test
    public void teste02() {
        // buscar deve retornar null quando o numero nao existe
        String numero = "SIN00002";
        cadastro.incluir(criarSinistro(numero), numero);
        Sinistro resultado = dao.buscar("SIN99999");
        Assertions.assertNull(resultado);
    }

    @Test
    public void teste03() {
        // excluir deve retornar true quando o sinistro existe
        String numero = "SIN00003";
        cadastro.incluir(criarSinistro(numero), numero);
        boolean ret = dao.excluir(numero);
        Assertions.assertTrue(ret);
    }

    @Test
    public void teste04() {
        // excluir deve retornar false quando o sinistro nao existe
        String numero = "SIN00004";
        cadastro.incluir(criarSinistro(numero), numero);
        boolean ret = dao.excluir("SIN99999");
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste05() {
        // incluir deve retornar true e o objeto deve poder ser buscado depois
        String numero = "SIN00005";
        boolean ret = dao.incluir(criarSinistro(numero));
        Assertions.assertTrue(ret);
        Sinistro resultado = dao.buscar(numero);
        Assertions.assertNotNull(resultado);
    }

    @Test
    public void teste06() {
        // incluir deve retornar false quando o sinistro ja existe (duplicata)
        String numero = "SIN00006";
        Sinistro sin = criarSinistro(numero);
        cadastro.incluir(sin, numero);
        boolean ret = dao.incluir(sin);
        Assertions.assertFalse(ret);
    }

    @Test
    public void teste07() {
        // alterar deve retornar false quando o sinistro nao existe
        String numero = "SIN00007";
        boolean ret = dao.alterar(criarSinistro(numero));
        Assertions.assertFalse(ret);
        Sinistro resultado = dao.buscar(numero);
        Assertions.assertNull(resultado);
    }

    @Test
    public void teste08() {
        // alterar deve retornar true quando o sinistro existe
        String numero = "SIN00008";
        Sinistro sin = criarSinistro(numero);
        cadastro.incluir(sin, numero);
        Sinistro sinAlterado = new Sinistro(numero, sin.getVeiculo(), LocalDateTime.now(),
                LocalDateTime.now(), "usuario_alterado", BigDecimal.valueOf(9999.0), TipoSinistro.FURTO);
        boolean ret = dao.alterar(sinAlterado);
        Assertions.assertTrue(ret);
    }
}
