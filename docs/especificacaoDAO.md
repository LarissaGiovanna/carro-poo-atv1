0\. Incluir no projeto a dependência PersistenciaObjetos.jar, as classes DAOGenerico e   
SeguradoPessoaDAO, ambas no pacote br.edu.cs.poo.ac.seguro.daos. Incluir no projeto   
as classes TesteSeguradoEmpresaDAO, TesteVeiculoDAO e TesteDAO no pacote   
pacote br.edu.cs.poo.ac.seguro.testes.

1\. Incluir o atributo privado String numero na classe Sinistro (se ele ainda não existir), com seus respectivos   
métodos get/set. Este atributo identifica unicamente um sinistro. Não alterar o   
construtor de sinistro para incluir este atributo\! 

2\. Incluir o atributo privado String numero na classe Apolice (se ele ainda não existir), com seus respectivos   
métodos get/set. Este atributo identifica unicamente uma apólice. Não alterar o   
construtor de apólice para incluir este atributo\! 

3\. Implementar as classes dadas abaixo seguindo o modelo da classe SeguradoPessoaDAO. Tais  
classes devem estar todas no pacote br.edu.cs.poo.ac.seguro.daos. Uma ou outra coisa do  
código da classe SeguradoPessoaDAO pode não ser completamente entendido, mas será devidamente  
explicado no próximo assunto de POO \- polimorfismo. Uma observação: os códigos das classes   
DAO são muito parecidos, e serão generalizados na 2a unidade, quando o polimorfismo for   
devidamente compreendido. Por enquanto, vamos conviver com estas "N-replicações" de código.

4\. Implementar testes automatizados para as classes SinistroDAO e ApoliceDAO, criando   
para isso as classes TesteSinistroDAO e TesteApoliceDAO, e seguindo o modelo dos testes   
automatizados TesteSeguradoEmpresaDAO e TesteVeiculoDAO. Tais classes devem estar no   
pacote br.edu.cs.poo.ac.seguro.testes. Estas classes devem ter métodos teste01, teste02,   
etc. para testar os métodos dos DAOs de sinistro e de apólice. Os alunos devem ler   
o código das classes de testes mencionadas como referências para entender o que está sendo   
feito nos métodos de testes. 

public class SeguradoEmpresaDAO {

	public SeguradoEmpresa buscar(String cnpj) {  
		return null;  
	}  
	public boolean incluir(SeguradoEmpresa segurado) {  
		return false;  
	}  
	public boolean alterar(SeguradoEmpresa segurado) {  
		return false;  
	}  
	public boolean excluir(String cnpj) {  
		return false;  
	}  
}  
public class VeiculoDAO {  
	public Veiculo buscar(String placa) {  
		return null;  
	}  
	public boolean incluir(Veiculo veiculo) {  
		return false;  
	}  
	public boolean alterar(Veiculo veiculo) {  
		return false;  
	}  
	public boolean excluir(String placa) {  
		return false;  
	}  
}  
public class SinistroDAO {  
	public Sinistro buscar(String numero) {  
		return null;  
	}  
	public boolean incluir(Sinistro segurado) {  
		return false;  
	}  
	public boolean alterar(Sinistro segurado) {  
		return false;  
	}  
	public boolean excluir(String numero) {  
		return false;  
	}  
}  
public class ApoliceDAO {  
	public Apolice buscar(String numero) {  
		return null;  
	}  
	public boolean incluir(Apolice segurado) {  
		return false;  
	}  
	public boolean alterar(Apolice segurado) {  
		return false;  
	}  
	public boolean excluir(String numero) {  
		return false;  
	}  
}  
