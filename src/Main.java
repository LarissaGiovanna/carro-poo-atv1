import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import br.edu.cs.poo.ac.seguro.mediators.SeguradoEmpresaMediator;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoMediator;
import br.edu.cs.poo.ac.seguro.mediators.SeguradoPessoaMediator;
import br.edu.cs.poo.ac.seguro.telas.TelaPrincipal;

/**
 * Ponto de entrada principal da aplicação.
 * Inicializa os serviços de backend (Mediators e DAOs) e abre a interface gráfica (Swing).
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("  INICIALIZANDO SISTEMA DE GESTÃO DE SEGUROS    ");
        System.out.println("=================================================");

        // 1. Inicialização dos Mediators do Backend
        System.out.println("-> Conectando aos Mediators do backend...");
        SeguradoMediator seguradoMed = SeguradoMediator.getInstancia();
        SeguradoPessoaMediator pessoaMed = SeguradoPessoaMediator.getInstancia();
        SeguradoEmpresaMediator empresaMed = SeguradoEmpresaMediator.getInstancia();

        if (seguradoMed != null && pessoaMed != null && empresaMed != null) {
            System.out.println("   [OK] SeguradoMediator pronto.");
            System.out.println("   [OK] SeguradoPessoaMediator pronto (DAO conectado).");
            System.out.println("   [OK] SeguradoEmpresaMediator pronto (DAO conectado).");
        }

        // 2. Configuração do Look and Feel para a interface gráfica nativa
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            System.err.println("Aviso: Não foi possível aplicar o Look and Feel do sistema: " + e.getMessage());
        }

        // 3. Inicialização e exibição da interface gráfica na Event Dispatch Thread
        System.out.println("-> Carregando telas gráficas Swing...");
        SwingUtilities.invokeLater(() -> {
            TelaPrincipal telaPrincipal = new TelaPrincipal();
            telaPrincipal.setVisible(true);
            System.out.println("   [OK] Tela Principal carregada com sucesso.");
            System.out.println("=================================================");
            System.out.println("  Sistema pronto para uso.");
            System.out.println("=================================================");
        });
    }
}