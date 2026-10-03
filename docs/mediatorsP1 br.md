1- Colocar no projeto todas as classes do pacote br.edu.cs.poo.ac.seguro.testes,  atualizando as classes já existentes.  

2- A leitura dos testes automatizados vai servir como especificação das classes  dadas a seguir. Seguem algumas diretrizes:  

a. As classes SeguradoPessoaMediator e SeguradoEmpresaMediator devem  ter como atributo, inicializado na sua própria definição, seguradoMediator,  do tipo SeguradoMediator. A partir deste, os atributos comuns da  superclasse Segurado podem ser validados invocando-se os métodos  pertinentes. 

b. As classes SeguradoPessoaMediator e SeguradoEmpresaMediator devem  ter como atributo, respectivamente, SeguradoPessoaDAO e  

SeguradoEmpresaDAO.  

c. As classes SeguradoMediator, SeguradoPessoaMediator e  

SeguradoEmpresaMediator devem ser Singletons. Os nomes de  

“instância” e do método que obtém instância estão especificados pela  implementação dos testes automatizados destas classes.  

d. A classe SeguradoPessoaMediator deve ter um método public String  validarSeguradoPessoa(SeguradoPessoa seg) a ser usado pelos métodos  incluirSeguradoPessoa e alterarSeguradoPessoa. 

e. A classe SeguradoEmpresaMediator deve ter um método public String  validarSeguradoEmpresa(SeguradoPessoa seg) a ser usado pelos métodos  incluirSeguradoEmpresa e alterarSeguradoEmpresa. 

3- Criar e implementar a classe StringUtils, no mesmo pacote dos mediators, que  tem a estrutura dada no arquivo mediatorsTemplates.txt. Esta classe deve ter seus  métodos utilizados pelos demais mediators.  

4- Criar e implementar a classe ValidadorCpfCnpj, no mesmo pacote dos mediators,  que tem a estrutura dada no arquivo mediatorsTemplates.txt. Esta classe deve ter  seus métodos utilizados pelos mediators de segurado pessoa e segurado  empresa.  

5- Os “esqueletos” dos mediators a serem implementados estão no arquivo  mediatorsTemplates.txt.