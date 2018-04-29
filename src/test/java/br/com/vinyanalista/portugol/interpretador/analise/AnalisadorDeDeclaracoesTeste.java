package br.com.vinyanalista.portugol.interpretador.analise;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import br.com.vinyanalista.portugol.base.node.*;
import br.com.vinyanalista.portugol.base.parser.Parser;
import br.com.vinyanalista.portugol.interpretador.simbolo.*;
import br.com.vinyanalista.portugol.interpretador.tipo.*;

public class AnalisadorDeDeclaracoesTeste {
	AnalisadorDeDeclaracoes analisadorDeDeclaracoes;
	AnalisadorSemantico analisadorSemantico;

	private void simularErroSemanticoDetectadoAnteriormente() {
		analisadorSemantico.lancarErroSemantico(null, 1, 1, "Um erro semântico para teste");
	}

	private void verificarAtributos(TabelaDeAtributos tabelaDeAtributos, Atributo[] atributosEsperados) {
		List<Atributo> atributosEsperadosComoLista = Arrays.asList(atributosEsperados);

		for (Atributo atributo : Atributo.values()) {
			if (atributosEsperadosComoLista.contains(atributo)) {
				// Verifica se atributos esperados estão presentes
				assertNotNull(tabelaDeAtributos.obter(atributo));
			} else {
				// Verifica se atributos não esperados estão ausentes
				assertNull(tabelaDeAtributos.obter(atributo));
			}
		}
	}

	@Before
	public void setUp() {
		analisadorSemantico = new AnalisadorSemantico((Parser) null);
		// TODO Refatoração - Alto nível de acoplamento entre as classes
		// AnalisadorDeDeclaracoes e AnalisadorSemantico
		analisadorDeDeclaracoes = new AnalisadorDeDeclaracoes(analisadorSemantico);
	}

	// ================================================================================
	// Método outADeclaracao()
	// ================================================================================

	// --------------------------------------------------------------------------------
	// Teste 1 - com erro semântico detectado antes da execução do método
	// --------------------------------------------------------------------------------

	@Test
	public void outADeclaracaoTeste1() {
		// Entrada: declare x numerico
		String entradaVariavelXIdentificadorComoString = "x";
		int entradaLinha = 5, entradaVariavelXColuna = 9;
		TIdentificador entradaVariavelXIdentificador = new TIdentificador(entradaVariavelXIdentificadorComoString,
				entradaLinha, entradaVariavelXColuna);
		ASimplesVariavel entradaVariavelX = new ASimplesVariavel(entradaVariavelXIdentificador);

		List<PVariavel> entradaListaDeVariaveis = new ArrayList<PVariavel>();
		entradaListaDeVariaveis.add(entradaVariavelX);

		PTipo entradaTipo = new ANumericoTipo();

		ADeclaracao entradaDeclaracao = new ADeclaracao(entradaListaDeVariaveis, entradaTipo);

		TabelaDeAtributos entradaAtributosDaVariavelX = new TabelaDeAtributos();
		entradaAtributosDaVariavelX.inserir(Atributo.ID, entradaVariavelXIdentificadorComoString.toUpperCase());
		Simbolo entradaVariavelXSimbolo = Simbolo.obter(entradaVariavelXIdentificadorComoString.toUpperCase());
		entradaAtributosDaVariavelX.inserir(Atributo.SIMBOLO, entradaVariavelXSimbolo);
		entradaAtributosDaVariavelX.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDaVariavelX.inserir(Atributo.COLUNA, entradaVariavelXColuna);
		entradaAtributosDaVariavelX.inserir(Atributo.STRING, entradaVariavelXIdentificadorComoString.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaVariavelX, entradaAtributosDaVariavelX);

		Tipo entradaTipoNumerico = new Tipo(TipoPrimitivo.NUMERICO);
		TabelaDeAtributos entradaAtributosDoTipoNumerico = new TabelaDeAtributos();
		entradaAtributosDoTipoNumerico.inserir(Atributo.TIPO, entradaTipoNumerico);
		entradaAtributosDoTipoNumerico.inserir(Atributo.STRING, entradaTipoNumerico.toString());
		analisadorSemantico.gravarAtributos(entradaTipo, entradaAtributosDoTipoNumerico);

		simularErroSemanticoDetectadoAnteriormente();

		// Execução do método
		analisadorDeDeclaracoes.outADeclaracao(entradaDeclaracao);

		// Saída
		TabelaDeAtributos saidaAtributosDaDeclaracao = analisadorSemantico.obterAtributos(entradaDeclaracao);
		assertNull(saidaAtributosDaDeclaracao);
	}

	// --------------------------------------------------------------------------------
	// Teste 2 - com entrada válida com apenas 1 variável de um tipo primitivo
	// --------------------------------------------------------------------------------

	final Atributo[] atributosEsperadosDaDeclaracao = new Atributo[] { Atributo.TIPO, Atributo.STRING };

	@Test
	public void outADeclaracaoTeste2() {
		// Entrada: declare x numerico
		String entradaVariavelXIdentificadorComoString = "x";
		int entradaLinha = 5, entradaVariavelXColuna = 9;
		TIdentificador entradaVariavelXIdentificador = new TIdentificador(entradaVariavelXIdentificadorComoString,
				entradaLinha, entradaVariavelXColuna);
		ASimplesVariavel entradaVariavelX = new ASimplesVariavel(entradaVariavelXIdentificador);

		List<PVariavel> entradaListaDeVariaveis = new ArrayList<PVariavel>();
		entradaListaDeVariaveis.add(entradaVariavelX);

		PTipo entradaTipo = new ANumericoTipo();

		ADeclaracao entradaDeclaracao = new ADeclaracao(entradaListaDeVariaveis, entradaTipo);

		TabelaDeAtributos entradaAtributosDaVariavelX = new TabelaDeAtributos();
		entradaAtributosDaVariavelX.inserir(Atributo.ID, entradaVariavelXIdentificadorComoString.toUpperCase());
		Simbolo entradaVariavelXSimbolo = Simbolo.obter(entradaVariavelXIdentificadorComoString.toUpperCase());
		entradaAtributosDaVariavelX.inserir(Atributo.SIMBOLO, entradaVariavelXSimbolo);
		entradaAtributosDaVariavelX.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDaVariavelX.inserir(Atributo.COLUNA, entradaVariavelXColuna);
		entradaAtributosDaVariavelX.inserir(Atributo.STRING, entradaVariavelXIdentificadorComoString.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaVariavelX, entradaAtributosDaVariavelX);

		Tipo entradaTipoNumerico = new Tipo(TipoPrimitivo.NUMERICO);
		TabelaDeAtributos entradaAtributosDoTipoNumerico = new TabelaDeAtributos();
		entradaAtributosDoTipoNumerico.inserir(Atributo.TIPO, entradaTipoNumerico);
		entradaAtributosDoTipoNumerico.inserir(Atributo.STRING, entradaTipoNumerico.toString());
		analisadorSemantico.gravarAtributos(entradaTipo, entradaAtributosDoTipoNumerico);

		// Execução do método
		analisadorDeDeclaracoes.outADeclaracao(entradaDeclaracao);

		// Saída
		TabelaDeAtributos saidaAtributosDaDeclaracao = analisadorSemantico.obterAtributos(entradaDeclaracao);
		assertNotNull(saidaAtributosDaDeclaracao);

		verificarAtributos(saidaAtributosDaDeclaracao, atributosEsperadosDaDeclaracao);

		TabelaDeAtributos saidaAtributosDaVariavelX = analisadorSemantico.obterAtributos(entradaVariavelX);

		Tipo saidaTipoDaVariavelX = (Tipo) saidaAtributosDaVariavelX.obter(Atributo.TIPO);
		assertNotNull(saidaTipoDaVariavelX);
		assertEquals(entradaTipoNumerico, saidaTipoDaVariavelX);

		assertNotNull(analisadorSemantico.getTabelaDeSimbolos().obter(entradaVariavelXSimbolo));

		Tipo saidaTipoDaDeclaracao = (Tipo) saidaAtributosDaDeclaracao.obter(Atributo.TIPO);
		assertEquals(entradaTipoNumerico, saidaTipoDaDeclaracao);

		String saidaStringDaDeclaracao = (String) saidaAtributosDaDeclaracao.obter(Atributo.STRING);
		assertEquals("[...] NUMERICO", saidaStringDaDeclaracao);
	}

	// --------------------------------------------------------------------------------
	// Teste 3 - com entrada válida com 1 variável de um tipo registro contendo 1
	// campo (nesse caso, deve-se testar a declaração do campo, em vez da declaração
	// da variável do tipo registro)
	// --------------------------------------------------------------------------------

	@Test
	public void outADeclaracaoTeste3() {
		// Entrada: registro (num numerico)
		String entradaCampoNumIdentificador = "num";
		int entradaLinha = 16, entradaCampoNumColuna = 22;
		PVariavel entradaCampoNum = new ASimplesVariavel(
				new TIdentificador(entradaCampoNumIdentificador, entradaLinha, entradaCampoNumColuna));

		List<PVariavel> auxiliarListaDeCampos = new ArrayList<PVariavel>();
		auxiliarListaDeCampos.add(entradaCampoNum);

		PTipo entradaTipo = new ANumericoTipo();

		ADeclaracao entradaDeclaracaoNumerico = new ADeclaracao(auxiliarListaDeCampos, entradaTipo);

		List<PDeclaracao> entradaListaDeDeclaracoes = new ArrayList<PDeclaracao>();
		entradaListaDeDeclaracoes.add(entradaDeclaracaoNumerico);

		@SuppressWarnings("unused")
		ARegistroTipo entradaTipoRegistro = new ARegistroTipo(entradaListaDeDeclaracoes);

		TabelaDeAtributos entradaAtributosDoCampoNum = new TabelaDeAtributos();
		entradaAtributosDoCampoNum.inserir(Atributo.ID, entradaCampoNumIdentificador.toUpperCase());
		Simbolo entradaCampoNumSimbolo = Simbolo.obter(entradaCampoNumIdentificador.toUpperCase());
		entradaAtributosDoCampoNum.inserir(Atributo.SIMBOLO, entradaCampoNumSimbolo);
		entradaAtributosDoCampoNum.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoCampoNum.inserir(Atributo.COLUNA, entradaCampoNumColuna);
		entradaAtributosDoCampoNum.inserir(Atributo.STRING, entradaCampoNumIdentificador.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaCampoNum, entradaAtributosDoCampoNum);

		Tipo entradaTipoNumerico = new Tipo(TipoPrimitivo.NUMERICO);
		TabelaDeAtributos entradaAtributosDoTipoNumerico = new TabelaDeAtributos();
		entradaAtributosDoTipoNumerico.inserir(Atributo.TIPO, entradaTipoNumerico);
		entradaAtributosDoTipoNumerico.inserir(Atributo.STRING, entradaTipoNumerico.toString());
		analisadorSemantico.gravarAtributos(entradaTipo, entradaAtributosDoTipoNumerico);

		// Execução do método
		analisadorDeDeclaracoes.outADeclaracao(entradaDeclaracaoNumerico);

		// Saída
		TabelaDeAtributos saidaAtributosDaDeclaracao = analisadorSemantico.obterAtributos(entradaDeclaracaoNumerico);
		assertNotNull(saidaAtributosDaDeclaracao);

		verificarAtributos(saidaAtributosDaDeclaracao, atributosEsperadosDaDeclaracao);

		TabelaDeAtributos saidaAtributosDoCampoNum = analisadorSemantico.obterAtributos(entradaCampoNum);

		Tipo saidaTipoDoCampoNum = (Tipo) saidaAtributosDoCampoNum.obter(Atributo.TIPO);
		assertNotNull(saidaTipoDoCampoNum);
		assertEquals(entradaTipoNumerico, saidaTipoDoCampoNum);

		// TODO Defeito - como o campo não é guardado em uma tabela de símbolos, é
		// necessário verificar campos repetidos no método outARegistroTipo(), mas essa
		// verificação deveria ser feita no método outADeclaracao(), remover o if na
		// linha 74 da classe AnalisadorDeDeclaracoes
		assertNotNull(analisadorSemantico.getTabelaDeSimbolos().obter(entradaCampoNumSimbolo));

		Tipo saidaTipoDaDeclaracao = (Tipo) saidaAtributosDaDeclaracao.obter(Atributo.TIPO);
		assertEquals(entradaTipoNumerico, saidaTipoDaDeclaracao);

		String saidaStringDaDeclaracao = (String) saidaAtributosDaDeclaracao.obter(Atributo.STRING);
		assertEquals("[...] NUMERICO", saidaStringDaDeclaracao);
	}

	// --------------------------------------------------------------------------------
	// Teste 4 - com entrada inválida com 2 variáveis sendo que os identificadores
	// são repetidos
	// --------------------------------------------------------------------------------

	@Test
	public void outADeclaracaoTeste4() {
		// Entrada: declare x, x numerico
		String entradaVariavelXIdentificadorComoString = "x";
		int entradaLinha = 5, entradaVariavelXColuna = 9;
		TIdentificador entradaVariavelXIdentificador = new TIdentificador(entradaVariavelXIdentificadorComoString,
				entradaLinha, entradaVariavelXColuna);
		ASimplesVariavel entradaVariavelX = new ASimplesVariavel(entradaVariavelXIdentificador);

		String entradaVariavelRepetidaIdentificadorComoString = entradaVariavelXIdentificadorComoString;
		int entradaVariavelRepetidaColuna = 12;
		TIdentificador entradaVariavelRepetidaIdentificador = new TIdentificador(
				entradaVariavelRepetidaIdentificadorComoString, entradaLinha, entradaVariavelRepetidaColuna);
		ASimplesVariavel entradaVariavelRepetida = new ASimplesVariavel(entradaVariavelRepetidaIdentificador);

		List<PVariavel> entradaListaDeVariaveis = new ArrayList<PVariavel>();
		entradaListaDeVariaveis.add(entradaVariavelX);
		entradaListaDeVariaveis.add(entradaVariavelRepetida);

		PTipo entradaTipo = new ANumericoTipo();

		ADeclaracao entradaDeclaracao = new ADeclaracao(entradaListaDeVariaveis, entradaTipo);

		TabelaDeAtributos entradaAtributosDaVariavelX = new TabelaDeAtributos();
		entradaAtributosDaVariavelX.inserir(Atributo.ID, entradaVariavelXIdentificadorComoString.toUpperCase());
		Simbolo entradaVariavelXSimbolo = Simbolo.obter(entradaVariavelXIdentificadorComoString.toUpperCase());
		entradaAtributosDaVariavelX.inserir(Atributo.SIMBOLO, entradaVariavelXSimbolo);
		entradaAtributosDaVariavelX.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDaVariavelX.inserir(Atributo.COLUNA, entradaVariavelXColuna);
		entradaAtributosDaVariavelX.inserir(Atributo.STRING, entradaVariavelXIdentificadorComoString.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaVariavelX, entradaAtributosDaVariavelX);

		TabelaDeAtributos entradaAtributosDaVariavelRepetida = new TabelaDeAtributos();
		entradaAtributosDaVariavelRepetida.inserir(Atributo.ID,
				entradaVariavelRepetidaIdentificadorComoString.toUpperCase());
		Simbolo entradaVariavelRepetidaSimbolo = Simbolo
				.obter(entradaVariavelRepetidaIdentificadorComoString.toUpperCase());
		entradaAtributosDaVariavelRepetida.inserir(Atributo.SIMBOLO, entradaVariavelRepetidaSimbolo);
		entradaAtributosDaVariavelRepetida.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDaVariavelRepetida.inserir(Atributo.COLUNA, entradaVariavelRepetidaColuna);
		entradaAtributosDaVariavelRepetida.inserir(Atributo.STRING,
				entradaVariavelRepetidaIdentificadorComoString.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaVariavelRepetida, entradaAtributosDaVariavelRepetida);

		Tipo entradaTipoNumerico = new Tipo(TipoPrimitivo.NUMERICO);
		TabelaDeAtributos entradaAtributosDoTipoNumerico = new TabelaDeAtributos();
		entradaAtributosDoTipoNumerico.inserir(Atributo.TIPO, entradaTipoNumerico);
		entradaAtributosDoTipoNumerico.inserir(Atributo.STRING, entradaTipoNumerico.toString());
		analisadorSemantico.gravarAtributos(entradaTipo, entradaAtributosDoTipoNumerico);

		// Execução do método
		analisadorDeDeclaracoes.outADeclaracao(entradaDeclaracao);

		// Saída
		TabelaDeAtributos saidaAtributosDaDeclaracao = analisadorSemantico.obterAtributos(entradaDeclaracao);
		assertNull(saidaAtributosDaDeclaracao);
		// TODO Refatoração - AnalisadorSemantico.analisar() throws ErroSemantico, seria
		// o caso de refatorar o método AnalisadorSemantico.lancarErroSemantico()
		assertTrue(analisadorSemantico.haErroSemantico());
		// TODO Refatoração - Não é possível testar linha e coluna do erro semântico

		TabelaDeAtributos saidaAtributosDaVariavelX = analisadorSemantico.obterAtributos(entradaVariavelX);

		Tipo saidaTipoDaVariavelX = (Tipo) saidaAtributosDaVariavelX.obter(Atributo.TIPO);
		// TODO Defeito - Não deveria guardar tipo se houve erro semântico
		assertNull(saidaTipoDaVariavelX);

		// TODO Defeito - Não deveria guardar símbolo se houve erro semântico
		assertNull(analisadorSemantico.getTabelaDeSimbolos().obter(entradaVariavelXSimbolo));

		TabelaDeAtributos saidaAtributosDaVariavelRepetida = analisadorSemantico
				.obterAtributos(entradaVariavelRepetida);

		Tipo saidaTipoDaVariavelRepetida = (Tipo) saidaAtributosDaVariavelRepetida.obter(Atributo.TIPO);
		// TODO Defeito - Não deveria guardar tipo se houve erro semântico
		assertNull(saidaTipoDaVariavelRepetida);

		// TODO Defeito - Não deveria guardar símbolo se houve erro semântico
		assertNull(analisadorSemantico.getTabelaDeSimbolos().obter(entradaVariavelRepetidaSimbolo));
	}

	// --------------------------------------------------------------------------------
	// Teste 5 - com entrada válida com 1 vetor de capacidade 1
	// --------------------------------------------------------------------------------

	@Test
	public void outADeclaracaoTeste5() {
		// Entrada: declare letras[1] literal
		String entradaVetorLetrasIdentificadorComoString = "letras";
		int entradaLinha = 5, entradaVetorLetrasColuna = 2;
		TIdentificador entradaVetorLetrasIdentificador = new TIdentificador(entradaVetorLetrasIdentificadorComoString,
				entradaLinha, entradaVetorLetrasColuna);
		List<PExpressao> entradaListaDeExpressoes = new ArrayList<PExpressao>();
		entradaListaDeExpressoes.add(new AValorExpressao(new AInteiroValor(new TNumeroInteiro("1", entradaLinha, 9))));
		AVetorOuMatrizVariavel entradaVetorLetras = new AVetorOuMatrizVariavel(entradaVetorLetrasIdentificador,
				entradaListaDeExpressoes);

		List<PVariavel> entradaListaDeVariaveis = new ArrayList<PVariavel>();
		entradaListaDeVariaveis.add(entradaVetorLetras);

		PTipo entradaTipo = new ALiteralTipo();

		ADeclaracao entradaDeclaracao = new ADeclaracao(entradaListaDeVariaveis, entradaTipo);

		TabelaDeAtributos entradaAtributosDoVetorLetras = new TabelaDeAtributos();
		entradaAtributosDoVetorLetras.inserir(Atributo.ID, entradaVetorLetrasIdentificadorComoString.toUpperCase());
		Simbolo entradaVetorLetrasSimbolo = Simbolo.obter(entradaVetorLetrasIdentificadorComoString.toUpperCase());
		entradaAtributosDoVetorLetras.inserir(Atributo.SIMBOLO, entradaVetorLetrasSimbolo);
		entradaAtributosDoVetorLetras.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoVetorLetras.inserir(Atributo.COLUNA, entradaVetorLetrasColuna);
		entradaAtributosDoVetorLetras.inserir(Atributo.STRING, entradaVetorLetrasIdentificadorComoString.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaVetorLetras, entradaAtributosDoVetorLetras);

		Tipo entradaTipoLiteral = new Tipo(TipoPrimitivo.LITERAL);
		TabelaDeAtributos entradaAtributosDoTipoLiteral = new TabelaDeAtributos();
		entradaAtributosDoTipoLiteral.inserir(Atributo.TIPO, entradaTipoLiteral);
		entradaAtributosDoTipoLiteral.inserir(Atributo.STRING, entradaTipoLiteral.toString());
		analisadorSemantico.gravarAtributos(entradaTipo, entradaAtributosDoTipoLiteral);

		// Execução do método
		analisadorDeDeclaracoes.outADeclaracao(entradaDeclaracao);

		// Saída
		TabelaDeAtributos saidaAtributosDaDeclaracao = analisadorSemantico.obterAtributos(entradaDeclaracao);
		assertNotNull(saidaAtributosDaDeclaracao);

		verificarAtributos(saidaAtributosDaDeclaracao, atributosEsperadosDaDeclaracao);

		TabelaDeAtributos saidaAtributosDoVetorLetras = analisadorSemantico.obterAtributos(entradaVetorLetras);

		String saidaStringDoVetorLetras = (String) saidaAtributosDoVetorLetras.obter(Atributo.STRING);
		assertNotNull(saidaStringDoVetorLetras);
		assertEquals("LETRAS[1]", saidaStringDoVetorLetras);

		Tipo esperadoTipoDoVetorLetras = new TipoVetorOuMatriz(entradaTipoLiteral, new int[] { 1 });

		Tipo saidaTipoDoVetorLetras = (Tipo) saidaAtributosDoVetorLetras.obter(Atributo.TIPO);
		assertNotNull(saidaTipoDoVetorLetras);
		assertEquals(esperadoTipoDoVetorLetras, saidaTipoDoVetorLetras);

		assertNotNull(analisadorSemantico.getTabelaDeSimbolos().obter(entradaVetorLetrasSimbolo));

		Tipo saidaTipoDaDeclaracao = (Tipo) saidaAtributosDaDeclaracao.obter(Atributo.TIPO);
		assertEquals(entradaTipoLiteral, saidaTipoDaDeclaracao);

		String saidaStringDaDeclaracao = (String) saidaAtributosDaDeclaracao.obter(Atributo.STRING);
		assertEquals("[...] LITERAL", saidaStringDaDeclaracao);
	}

	// --------------------------------------------------------------------------------
	// Teste 6 - com entrada inválida com 1 vetor de capacidade 0
	// --------------------------------------------------------------------------------

	@Test
	public void outADeclaracaoTeste6() {
		// Entrada: declare letras[0] literal
		String entradaVetorLetrasIdentificadorComoString = "letras";
		int entradaLinha = 5, entradaVetorLetrasColuna = 2;
		TIdentificador entradaVetorLetrasIdentificador = new TIdentificador(entradaVetorLetrasIdentificadorComoString,
				entradaLinha, entradaVetorLetrasColuna);
		List<PExpressao> entradaListaDeExpressoes = new ArrayList<PExpressao>();
		entradaListaDeExpressoes.add(new AValorExpressao(new AInteiroValor(new TNumeroInteiro("0", entradaLinha, 9))));
		AVetorOuMatrizVariavel entradaVetorLetras = new AVetorOuMatrizVariavel(entradaVetorLetrasIdentificador,
				entradaListaDeExpressoes);

		List<PVariavel> entradaListaDeVariaveis = new ArrayList<PVariavel>();
		entradaListaDeVariaveis.add(entradaVetorLetras);

		PTipo entradaTipo = new ALiteralTipo();

		ADeclaracao entradaDeclaracao = new ADeclaracao(entradaListaDeVariaveis, entradaTipo);

		TabelaDeAtributos entradaAtributosDoVetorLetras = new TabelaDeAtributos();
		entradaAtributosDoVetorLetras.inserir(Atributo.ID, entradaVetorLetrasIdentificadorComoString.toUpperCase());
		Simbolo entradaVetorLetrasSimbolo = Simbolo.obter(entradaVetorLetrasIdentificadorComoString.toUpperCase());
		entradaAtributosDoVetorLetras.inserir(Atributo.SIMBOLO, entradaVetorLetrasSimbolo);
		entradaAtributosDoVetorLetras.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoVetorLetras.inserir(Atributo.COLUNA, entradaVetorLetrasColuna);
		entradaAtributosDoVetorLetras.inserir(Atributo.STRING, entradaVetorLetrasIdentificadorComoString.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaVetorLetras, entradaAtributosDoVetorLetras);

		Tipo entradaTipoLiteral = new Tipo(TipoPrimitivo.LITERAL);
		TabelaDeAtributos entradaAtributosDoTipoLiteral = new TabelaDeAtributos();
		entradaAtributosDoTipoLiteral.inserir(Atributo.TIPO, entradaTipoLiteral);
		entradaAtributosDoTipoLiteral.inserir(Atributo.STRING, entradaTipoLiteral.toString());
		analisadorSemantico.gravarAtributos(entradaTipo, entradaAtributosDoTipoLiteral);

		// Execução do método
		analisadorDeDeclaracoes.outADeclaracao(entradaDeclaracao);

		// Saída
		TabelaDeAtributos saidaAtributosDaDeclaracao = analisadorSemantico.obterAtributos(entradaDeclaracao);
		assertNull(saidaAtributosDaDeclaracao);
		// TODO Defeito - mensagem de erro ambígua na linha 58 da classe
		// AnalisadorDeDeclaracoes
		assertTrue(analisadorSemantico.haErroSemantico());
		// TODO Refatoração - Não é possível testar linha e coluna do erro semântico

		TabelaDeAtributos saidaAtributosDoVetorLetras = analisadorSemantico.obterAtributos(entradaVetorLetras);

		String saidaStringDoVetorLetras = (String) saidaAtributosDoVetorLetras.obter(Atributo.STRING);
		// TODO Defeito - Não deveria guardar string se houve erro semântico
		assertNull(saidaStringDoVetorLetras);

		Tipo saidaTipoDoVetorLetras = (Tipo) saidaAtributosDoVetorLetras.obter(Atributo.TIPO);
		assertNull(saidaTipoDoVetorLetras);

		assertNull(analisadorSemantico.getTabelaDeSimbolos().obter(entradaVetorLetrasSimbolo));
	}

	// --------------------------------------------------------------------------------
	// Teste 7 - com entrada inválida com 1 vetor de capacidade -1
	// --------------------------------------------------------------------------------

	@Test
	public void outADeclaracaoTeste7() {
		// Entrada: declare letras[-1] literal
		String entradaVetorLetrasIdentificadorComoString = "letras";
		int entradaLinha = 5, entradaVetorLetrasColuna = 2;
		TIdentificador entradaVetorLetrasIdentificador = new TIdentificador(entradaVetorLetrasIdentificadorComoString,
				entradaLinha, entradaVetorLetrasColuna);
		List<PExpressao> entradaListaDeExpressoes = new ArrayList<PExpressao>();
		entradaListaDeExpressoes.add(new AValorExpressao(new AInteiroValor(new TNumeroInteiro("-1", entradaLinha, 9))));
		AVetorOuMatrizVariavel entradaVetorLetras = new AVetorOuMatrizVariavel(entradaVetorLetrasIdentificador,
				entradaListaDeExpressoes);

		List<PVariavel> entradaListaDeVariaveis = new ArrayList<PVariavel>();
		entradaListaDeVariaveis.add(entradaVetorLetras);

		PTipo entradaTipo = new ALiteralTipo();

		ADeclaracao entradaDeclaracao = new ADeclaracao(entradaListaDeVariaveis, entradaTipo);

		TabelaDeAtributos entradaAtributosDoVetorLetras = new TabelaDeAtributos();
		entradaAtributosDoVetorLetras.inserir(Atributo.ID, entradaVetorLetrasIdentificadorComoString.toUpperCase());
		Simbolo entradaVetorLetrasSimbolo = Simbolo.obter(entradaVetorLetrasIdentificadorComoString.toUpperCase());
		entradaAtributosDoVetorLetras.inserir(Atributo.SIMBOLO, entradaVetorLetrasSimbolo);
		entradaAtributosDoVetorLetras.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoVetorLetras.inserir(Atributo.COLUNA, entradaVetorLetrasColuna);
		entradaAtributosDoVetorLetras.inserir(Atributo.STRING, entradaVetorLetrasIdentificadorComoString.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaVetorLetras, entradaAtributosDoVetorLetras);

		Tipo entradaTipoLiteral = new Tipo(TipoPrimitivo.LITERAL);
		TabelaDeAtributos entradaAtributosDoTipoLiteral = new TabelaDeAtributos();
		entradaAtributosDoTipoLiteral.inserir(Atributo.TIPO, entradaTipoLiteral);
		entradaAtributosDoTipoLiteral.inserir(Atributo.STRING, entradaTipoLiteral.toString());
		analisadorSemantico.gravarAtributos(entradaTipo, entradaAtributosDoTipoLiteral);

		// Execução do método
		analisadorDeDeclaracoes.outADeclaracao(entradaDeclaracao);

		// Saída
		TabelaDeAtributos saidaAtributosDaDeclaracao = analisadorSemantico.obterAtributos(entradaDeclaracao);
		// TODO Defeito - Não deveria gravar atributos, se há erro semântico
		assertNull(saidaAtributosDaDeclaracao);
		// TODO Defeito - Não está gerando erro semântico
		assertTrue(analisadorSemantico.haErroSemantico());
		// TODO Refatoração - Não é possível testar linha e coluna do erro semântico

		TabelaDeAtributos saidaAtributosDoVetorLetras = analisadorSemantico.obterAtributos(entradaVetorLetras);

		String saidaStringDoVetorLetras = (String) saidaAtributosDoVetorLetras.obter(Atributo.STRING);
		// TODO Defeito - Não deveria guardar string se houve erro semântico
		assertNull(saidaStringDoVetorLetras);

		Tipo saidaTipoDoVetorLetras = (Tipo) saidaAtributosDoVetorLetras.obter(Atributo.TIPO);
		// TODO Defeito - Não deveria guardar tipo se houve erro semântico
		assertNull(saidaTipoDoVetorLetras);

		// TODO Defeito - Não deveria guardar símbolo se houve erro semântico
		assertNull(analisadorSemantico.getTabelaDeSimbolos().obter(entradaVetorLetrasSimbolo));
	}

	// --------------------------------------------------------------------------------
	// Teste 8 - com entrada inválida com 1 vetor cuja capacidade é indicada por um
	// valor lógico
	// --------------------------------------------------------------------------------

	@Test
	public void outADeclaracaoTeste8() {
		// Entrada: declare letras[verdadeiro] literal
		String entradaVetorLetrasIdentificadorComoString = "letras";
		int entradaLinha = 5, entradaVetorLetrasColuna = 2;
		TIdentificador entradaVetorLetrasIdentificador = new TIdentificador(entradaVetorLetrasIdentificadorComoString,
				entradaLinha, entradaVetorLetrasColuna);
		List<PExpressao> entradaListaDeExpressoes = new ArrayList<PExpressao>();
		// TODO Refatoração - a árvore sintática abstrata poderia produzir nós
		// verdadeiro ou falso, em vez de um nó valor lógico, revisar gramática
		entradaListaDeExpressoes
				.add(new AValorExpressao(new ALogicoValor(new TValorLogico("verdadeiro", entradaLinha, 9))));
		AVetorOuMatrizVariavel entradaVetorLetras = new AVetorOuMatrizVariavel(entradaVetorLetrasIdentificador,
				entradaListaDeExpressoes);

		List<PVariavel> entradaListaDeVariaveis = new ArrayList<PVariavel>();
		entradaListaDeVariaveis.add(entradaVetorLetras);

		PTipo entradaTipo = new ALiteralTipo();

		ADeclaracao entradaDeclaracao = new ADeclaracao(entradaListaDeVariaveis, entradaTipo);

		TabelaDeAtributos entradaAtributosDoVetorLetras = new TabelaDeAtributos();
		entradaAtributosDoVetorLetras.inserir(Atributo.ID, entradaVetorLetrasIdentificadorComoString.toUpperCase());
		Simbolo entradaVetorLetrasSimbolo = Simbolo.obter(entradaVetorLetrasIdentificadorComoString.toUpperCase());
		entradaAtributosDoVetorLetras.inserir(Atributo.SIMBOLO, entradaVetorLetrasSimbolo);
		entradaAtributosDoVetorLetras.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoVetorLetras.inserir(Atributo.COLUNA, entradaVetorLetrasColuna);
		entradaAtributosDoVetorLetras.inserir(Atributo.STRING, entradaVetorLetrasIdentificadorComoString.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaVetorLetras, entradaAtributosDoVetorLetras);

		Tipo entradaTipoLiteral = new Tipo(TipoPrimitivo.LITERAL);
		TabelaDeAtributos entradaAtributosDoTipoLiteral = new TabelaDeAtributos();
		entradaAtributosDoTipoLiteral.inserir(Atributo.TIPO, entradaTipoLiteral);
		entradaAtributosDoTipoLiteral.inserir(Atributo.STRING, entradaTipoLiteral.toString());
		analisadorSemantico.gravarAtributos(entradaTipo, entradaAtributosDoTipoLiteral);

		// Execução do método
		analisadorDeDeclaracoes.outADeclaracao(entradaDeclaracao);

		// Saída
		TabelaDeAtributos saidaAtributosDaDeclaracao = analisadorSemantico.obterAtributos(entradaDeclaracao);
		assertNull(saidaAtributosDaDeclaracao);
		assertTrue(analisadorSemantico.haErroSemantico());
		// TODO Refatoração - Não é possível testar linha e coluna do erro semântico

		TabelaDeAtributos saidaAtributosDoVetorLetras = analisadorSemantico.obterAtributos(entradaVetorLetras);

		String saidaStringDoVetorLetras = (String) saidaAtributosDoVetorLetras.obter(Atributo.STRING);
		// TODO Defeito - Não deveria guardar string se houve erro semântico
		assertNull(saidaStringDoVetorLetras);

		Tipo saidaTipoDoVetorLetras = (Tipo) saidaAtributosDoVetorLetras.obter(Atributo.TIPO);
		assertNull(saidaTipoDoVetorLetras);

		assertNull(analisadorSemantico.getTabelaDeSimbolos().obter(entradaVetorLetrasSimbolo));
	}

	// --------------------------------------------------------------------------------
	// Teste 9 - com entrada inválida com 1 vetor cuja capacidade é indicada por uma
	// variável
	// --------------------------------------------------------------------------------

	@Test
	public void outADeclaracaoTeste9() {
		// Entrada: declare letras[a] literal
		String entradaVetorLetrasIdentificadorComoString = "letras";
		int entradaLinha = 5, entradaVetorLetrasColuna = 2;
		TIdentificador entradaVetorLetrasIdentificador = new TIdentificador(entradaVetorLetrasIdentificadorComoString,
				entradaLinha, entradaVetorLetrasColuna);
		List<PExpressao> entradaListaDeExpressoes = new ArrayList<PExpressao>();
		entradaListaDeExpressoes.add(new APosicaoDeMemoriaExpressao(
				new AVariavelPosicaoDeMemoria(new ASimplesVariavel(new TIdentificador("a", entradaLinha, 9)))));
		AVetorOuMatrizVariavel entradaVetorLetras = new AVetorOuMatrizVariavel(entradaVetorLetrasIdentificador,
				entradaListaDeExpressoes);

		List<PVariavel> entradaListaDeVariaveis = new ArrayList<PVariavel>();
		entradaListaDeVariaveis.add(entradaVetorLetras);

		PTipo entradaTipo = new ALiteralTipo();

		ADeclaracao entradaDeclaracao = new ADeclaracao(entradaListaDeVariaveis, entradaTipo);

		TabelaDeAtributos entradaAtributosDoVetorLetras = new TabelaDeAtributos();
		entradaAtributosDoVetorLetras.inserir(Atributo.ID, entradaVetorLetrasIdentificadorComoString.toUpperCase());
		Simbolo entradaVetorLetrasSimbolo = Simbolo.obter(entradaVetorLetrasIdentificadorComoString.toUpperCase());
		entradaAtributosDoVetorLetras.inserir(Atributo.SIMBOLO, entradaVetorLetrasSimbolo);
		entradaAtributosDoVetorLetras.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoVetorLetras.inserir(Atributo.COLUNA, entradaVetorLetrasColuna);
		entradaAtributosDoVetorLetras.inserir(Atributo.STRING, entradaVetorLetrasIdentificadorComoString.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaVetorLetras, entradaAtributosDoVetorLetras);

		Tipo entradaTipoLiteral = new Tipo(TipoPrimitivo.LITERAL);
		TabelaDeAtributos entradaAtributosDoTipoLiteral = new TabelaDeAtributos();
		entradaAtributosDoTipoLiteral.inserir(Atributo.TIPO, entradaTipoLiteral);
		entradaAtributosDoTipoLiteral.inserir(Atributo.STRING, entradaTipoLiteral.toString());
		analisadorSemantico.gravarAtributos(entradaTipo, entradaAtributosDoTipoLiteral);

		// Execução do método
		analisadorDeDeclaracoes.outADeclaracao(entradaDeclaracao);

		// Saída
		TabelaDeAtributos saidaAtributosDaDeclaracao = analisadorSemantico.obterAtributos(entradaDeclaracao);
		assertNull(saidaAtributosDaDeclaracao);
		assertTrue(analisadorSemantico.haErroSemantico());
		// TODO Refatoração - Não é possível testar linha e coluna do erro semântico

		TabelaDeAtributos saidaAtributosDoVetorLetras = analisadorSemantico.obterAtributos(entradaVetorLetras);

		String saidaStringDoVetorLetras = (String) saidaAtributosDoVetorLetras.obter(Atributo.STRING);
		// TODO Defeito - Não deveria guardar string se houve erro semântico
		assertNull(saidaStringDoVetorLetras);

		Tipo saidaTipoDoVetorLetras = (Tipo) saidaAtributosDoVetorLetras.obter(Atributo.TIPO);
		assertNull(saidaTipoDoVetorLetras);

		assertNull(analisadorSemantico.getTabelaDeSimbolos().obter(entradaVetorLetrasSimbolo));
	}

	// --------------------------------------------------------------------------------
	// Teste 10 - com entrada nula
	// --------------------------------------------------------------------------------

	@Test
	public void outADeclaracaoTeste10() {
		try {
			analisadorDeDeclaracoes.outADeclaracao(null);
		} catch (AssertionError e) {
			// Esperado
			return;
		} catch (Exception e) {
		} finally {
			fail("Entrada nula não tratada");
		}
	}

	// ================================================================================
	// Método outASimplesVariavel()
	// ================================================================================

	// --------------------------------------------------------------------------------
	// Teste 1 - com erro semântico detectado antes da execução do método
	// --------------------------------------------------------------------------------

	@Test
	public void outASimplesVariavelTeste1() {
		// Entrada: n1
		String entradaIdentificadorComoString = "n1";
		int entradaLinha = 4, entradaColuna = 8;
		TIdentificador entradaIdentificador = new TIdentificador(entradaIdentificadorComoString, entradaLinha,
				entradaColuna);
		ASimplesVariavel entradaVariavel = new ASimplesVariavel(entradaIdentificador);

		simularErroSemanticoDetectadoAnteriormente();

		// Execução do método
		analisadorDeDeclaracoes.outASimplesVariavel(entradaVariavel);

		// Saída
		TabelaDeAtributos saidaAtributosDaVariavel = analisadorSemantico.obterAtributos(entradaVariavel);
		assertNull(saidaAtributosDaVariavel);
	}

	// --------------------------------------------------------------------------------
	// Teste 2 - com entrada válida (no sentido de não gerar erro semântico)
	// --------------------------------------------------------------------------------

	@Test
	public void outASimplesVariavelTeste2() {
		// Entrada: n1
		String entradaIdentificadorComoString = "n1";
		int entradaLinha = 4, entradaColuna = 8;
		TIdentificador entradaIdentificador = new TIdentificador(entradaIdentificadorComoString, entradaLinha,
				entradaColuna);
		ASimplesVariavel entradaVariavel = new ASimplesVariavel(entradaIdentificador);

		// Execução do método
		analisadorDeDeclaracoes.outASimplesVariavel(entradaVariavel);

		// Saída
		TabelaDeAtributos saidaAtributosDaVariavel = analisadorSemantico.obterAtributos(entradaVariavel);
		assertNotNull(saidaAtributosDaVariavel);

		Atributo[] atributosEsperadosDaVariavel = new Atributo[] { Atributo.SIMBOLO, Atributo.ID, Atributo.LINHA,
				Atributo.COLUNA, Atributo.STRING };
		verificarAtributos(saidaAtributosDaVariavel, atributosEsperadosDaVariavel);

		String saidaIdentificador = (String) saidaAtributosDaVariavel.obter(Atributo.ID);
		assertEquals(entradaIdentificadorComoString.toUpperCase(), saidaIdentificador);

		Simbolo saidaSimbolo = (Simbolo) saidaAtributosDaVariavel.obter(Atributo.SIMBOLO);
		assertEquals(Simbolo.obter(entradaIdentificadorComoString.toUpperCase()), saidaSimbolo);

		Integer saidaLinha = (Integer) saidaAtributosDaVariavel.obter(Atributo.LINHA);
		assertEquals(entradaLinha, (int) saidaLinha);

		Integer saidaColuna = (Integer) saidaAtributosDaVariavel.obter(Atributo.COLUNA);
		assertEquals(entradaColuna, (int) saidaColuna);

		String saidaString = (String) saidaAtributosDaVariavel.obter(Atributo.STRING);
		assertEquals(entradaIdentificadorComoString.toUpperCase(), saidaString);
	}

	// --------------------------------------------------------------------------------
	// Teste 3 - com entrada nula
	// --------------------------------------------------------------------------------

	@Test
	public void outASimplesVariavelTeste3() {
		try {
			analisadorDeDeclaracoes.outASimplesVariavel(null);
		} catch (AssertionError e) {
			// Esperado
			return;
		} catch (Exception e) {
		} finally {
			fail("Entrada nula não tratada");
		}
	}

	// ================================================================================
	// Método outAVetorOuMatrizVariavel()
	// ================================================================================

	// --------------------------------------------------------------------------------
	// Teste 1 - com erro semântico detectado antes da execução do método
	// --------------------------------------------------------------------------------

	@Test
	public void outAVetorOuMatrizVariavelTeste1() {
		// Entrada: num[9]
		String entradaIdentificadorComoString = "num";
		int entradaLinha = 6, entradaColuna = 2;
		TIdentificador entradaIdentificador = new TIdentificador(entradaIdentificadorComoString, entradaLinha,
				entradaColuna);
		List<PExpressao> entradaListaDeExpressoes = new ArrayList<PExpressao>();
		entradaListaDeExpressoes.add(new AValorExpressao(new AInteiroValor(new TNumeroInteiro("9", entradaLinha, 6))));
		AVetorOuMatrizVariavel entradaVetorOuMatriz = new AVetorOuMatrizVariavel(entradaIdentificador,
				entradaListaDeExpressoes);

		simularErroSemanticoDetectadoAnteriormente();

		// Execução do método
		analisadorDeDeclaracoes.outAVetorOuMatrizVariavel(entradaVetorOuMatriz);

		// Saída
		TabelaDeAtributos saidaAtributosDoVetorOuMatriz = analisadorSemantico.obterAtributos(entradaVetorOuMatriz);
		assertNull(saidaAtributosDoVetorOuMatriz);
	}

	// --------------------------------------------------------------------------------
	// Teste 2 - com entrada válida
	// --------------------------------------------------------------------------------

	@Test
	public void outAVetorOuMatrizVariavelTeste2() {
		// Entrada: num[9]
		String entradaIdentificadorComoString = "num";
		int entradaLinha = 6, entradaColuna = 2;
		TIdentificador entradaIdentificador = new TIdentificador(entradaIdentificadorComoString, entradaLinha,
				entradaColuna);
		List<PExpressao> entradaListaDeExpressoes = new ArrayList<PExpressao>();
		entradaListaDeExpressoes.add(new AValorExpressao(new AInteiroValor(new TNumeroInteiro("9", entradaLinha, 6))));
		AVetorOuMatrizVariavel entradaVetorOuMatriz = new AVetorOuMatrizVariavel(entradaIdentificador,
				entradaListaDeExpressoes);

		// Execução do método
		analisadorDeDeclaracoes.outAVetorOuMatrizVariavel(entradaVetorOuMatriz);

		// Saída
		TabelaDeAtributos saidaAtributosDoVetorOuMatriz = analisadorSemantico.obterAtributos(entradaVetorOuMatriz);
		assertNotNull(saidaAtributosDoVetorOuMatriz);

		Atributo[] atributosEsperadosDoVetorOuMatriz = new Atributo[] { Atributo.SIMBOLO, Atributo.ID, Atributo.LINHA,
				Atributo.COLUNA };
		verificarAtributos(saidaAtributosDoVetorOuMatriz, atributosEsperadosDoVetorOuMatriz);

		String saidaIdentificador = (String) saidaAtributosDoVetorOuMatriz.obter(Atributo.ID);
		assertEquals(entradaIdentificadorComoString.toUpperCase(), saidaIdentificador);

		Simbolo saidaSimbolo = (Simbolo) saidaAtributosDoVetorOuMatriz.obter(Atributo.SIMBOLO);
		assertEquals(Simbolo.obter(entradaIdentificadorComoString.toUpperCase()), saidaSimbolo);

		Integer saidaLinha = (Integer) saidaAtributosDoVetorOuMatriz.obter(Atributo.LINHA);
		assertEquals(entradaLinha, (int) saidaLinha);

		Integer saidaColuna = (Integer) saidaAtributosDoVetorOuMatriz.obter(Atributo.COLUNA);
		assertEquals(entradaColuna, (int) saidaColuna);
	}

	// --------------------------------------------------------------------------------
	// Teste 3 - com entrada nula
	// --------------------------------------------------------------------------------

	@Test
	public void outAVetorOuMatrizVariavelTeste3() {
		try {
			analisadorDeDeclaracoes.outAVetorOuMatrizVariavel(null);
		} catch (AssertionError e) {
			// Esperado
			return;
		} catch (Exception e) {
		} finally {
			fail("Entrada nula não tratada");
		}
	}

	// ================================================================================
	// Método outANumericoTipo()
	// ================================================================================

	// --------------------------------------------------------------------------------
	// Teste 1 - com erro semântico detectado antes da execução do método
	// --------------------------------------------------------------------------------

	@Test
	public void outANumericoTipoTeste1() {
		// Entrada: numerico
		ANumericoTipo entradaTipoNumerico = new ANumericoTipo();

		simularErroSemanticoDetectadoAnteriormente();

		// Execução do método
		analisadorDeDeclaracoes.outANumericoTipo(entradaTipoNumerico);

		// Saída
		TabelaDeAtributos saidaAtributosDoTipo = analisadorSemantico.obterAtributos(entradaTipoNumerico);
		assertNull(saidaAtributosDoTipo);
	}

	// --------------------------------------------------------------------------------
	// Teste 2 - com entrada válida
	// --------------------------------------------------------------------------------

	@Test
	public void outANumericoTipoTeste2() {
		// Entrada: numerico
		ANumericoTipo entradaTipoNumerico = new ANumericoTipo();

		// Execução do método
		analisadorDeDeclaracoes.outANumericoTipo(entradaTipoNumerico);

		// Saída
		TabelaDeAtributos saidaAtributosDoTipo = analisadorSemantico.obterAtributos(entradaTipoNumerico);
		assertNotNull(saidaAtributosDoTipo);

		Atributo[] atributosEsperadosDoTipo = new Atributo[] { Atributo.TIPO, Atributo.STRING };
		verificarAtributos(saidaAtributosDoTipo, atributosEsperadosDoTipo);

		Tipo esperadoTipo = new Tipo(TipoPrimitivo.NUMERICO);

		Tipo saidaTipo = (Tipo) saidaAtributosDoTipo.obter(Atributo.TIPO);
		assertEquals(esperadoTipo, saidaTipo);

		String saidaString = (String) saidaAtributosDoTipo.obter(Atributo.STRING);
		assertEquals(esperadoTipo.toString(), saidaString);
	}

	// --------------------------------------------------------------------------------
	// Teste 3 - com entrada nula
	// --------------------------------------------------------------------------------

	@Test
	public void outANumericoTipoTeste3() {
		try {
			analisadorDeDeclaracoes.outANumericoTipo(null);
		} catch (AssertionError e) {
			// Esperado
			return;
		} catch (Exception e) {
		} finally {
			fail("Entrada nula não tratada");
		}
	}

	// ================================================================================
	// Método outALiteralTipo()
	// ================================================================================

	// --------------------------------------------------------------------------------
	// Teste 1 - com erro semântico detectado antes da execução do método
	// --------------------------------------------------------------------------------

	@Test
	public void outALiteralTipoTeste1() {
		// Entrada: literal
		ALiteralTipo entradaTipoLiteral = new ALiteralTipo();

		simularErroSemanticoDetectadoAnteriormente();

		// Execução do método
		analisadorDeDeclaracoes.outALiteralTipo(entradaTipoLiteral);

		// Saída
		TabelaDeAtributos saidaAtributosDoTipo = analisadorSemantico.obterAtributos(entradaTipoLiteral);
		assertNull(saidaAtributosDoTipo);
	}

	// --------------------------------------------------------------------------------
	// Teste 2 - com entrada válida
	// --------------------------------------------------------------------------------

	@Test
	public void outALiteralTipoTeste2() {
		// Entrada: literal
		ALiteralTipo entradaTipoLiteral = new ALiteralTipo();

		// Execução do método
		analisadorDeDeclaracoes.outALiteralTipo(entradaTipoLiteral);

		// Saída
		TabelaDeAtributos saidaAtributosDoTipo = analisadorSemantico.obterAtributos(entradaTipoLiteral);
		assertNotNull(saidaAtributosDoTipo);

		Atributo[] atributosEsperadosDoTipo = new Atributo[] { Atributo.TIPO, Atributo.STRING };
		verificarAtributos(saidaAtributosDoTipo, atributosEsperadosDoTipo);

		Tipo esperadoTipo = new Tipo(TipoPrimitivo.LITERAL);

		Tipo saidaTipo = (Tipo) saidaAtributosDoTipo.obter(Atributo.TIPO);
		assertEquals(esperadoTipo, saidaTipo);

		String saidaString = (String) saidaAtributosDoTipo.obter(Atributo.STRING);
		assertEquals(esperadoTipo.toString(), saidaString);
	}

	// --------------------------------------------------------------------------------
	// Teste 3 - com entrada nula
	// --------------------------------------------------------------------------------

	@Test
	public void outALiteralTipoTeste3() {
		try {
			analisadorDeDeclaracoes.outALiteralTipo(null);
		} catch (AssertionError e) {
			// Esperado
			return;
		} catch (Exception e) {
		} finally {
			fail("Entrada nula não tratada");
		}
	}

	// ================================================================================
	// Método outALogicoTipo()
	// ================================================================================

	// --------------------------------------------------------------------------------
	// Teste 1 - com erro semântico detectado antes da execução do método
	// --------------------------------------------------------------------------------

	@Test
	public void outALogicoTipoTeste1() {
		// Entrada: logico
		ALogicoTipo entradaTipoLogico = new ALogicoTipo();

		simularErroSemanticoDetectadoAnteriormente();

		// Execução do método
		analisadorDeDeclaracoes.outALogicoTipo(entradaTipoLogico);

		// Saída
		TabelaDeAtributos saidaAtributosDoTipo = analisadorSemantico.obterAtributos(entradaTipoLogico);
		assertNull(saidaAtributosDoTipo);
	}

	// --------------------------------------------------------------------------------
	// Teste 2 - com entrada válida
	// --------------------------------------------------------------------------------

	@Test
	public void outALogicoTipoTeste2() {
		// Entrada: logico
		ALogicoTipo entradaTipoLogico = new ALogicoTipo();

		// Execução do método
		analisadorDeDeclaracoes.outALogicoTipo(entradaTipoLogico);

		// Saída
		TabelaDeAtributos saidaAtributosDoTipo = analisadorSemantico.obterAtributos(entradaTipoLogico);
		assertNotNull(saidaAtributosDoTipo);

		Atributo[] atributosEsperadosDoTipo = new Atributo[] { Atributo.TIPO, Atributo.STRING };
		verificarAtributos(saidaAtributosDoTipo, atributosEsperadosDoTipo);

		Tipo esperadoTipo = new Tipo(TipoPrimitivo.LOGICO);

		Tipo saidaTipo = (Tipo) saidaAtributosDoTipo.obter(Atributo.TIPO);
		assertEquals(esperadoTipo, saidaTipo);

		String saidaString = (String) saidaAtributosDoTipo.obter(Atributo.STRING);
		assertEquals(esperadoTipo.toString(), saidaString);
	}

	// --------------------------------------------------------------------------------
	// Teste 3 - com entrada nula
	// --------------------------------------------------------------------------------

	@Test
	public void outALogicoTipoTeste3() {
		try {
			analisadorDeDeclaracoes.outALogicoTipo(null);
		} catch (AssertionError e) {
			// Esperado
			return;
		} catch (Exception e) {
		} finally {
			fail("Entrada nula não tratada");
		}
	}

	// ================================================================================
	// Método outARegistroTipo()
	// ================================================================================

	// Entrada: registro ()
	// Erro sintático já capturado pelo analisador sintático

	// --------------------------------------------------------------------------------
	// Teste 1 - com erro semântico detectado antes da execução do método
	// --------------------------------------------------------------------------------

	@Test
	public void outARegistroTipoTeste1() {
		// Entrada: registro (num numerico)
		String entradaCampoNumIdentificador = "num";
		int entradaLinha = 16, entradaCampoNumColuna = 22;
		PVariavel entradaCampoNum = new ASimplesVariavel(
				new TIdentificador(entradaCampoNumIdentificador, entradaLinha, entradaCampoNumColuna));

		List<PVariavel> auxiliarListaDeCampos = new ArrayList<PVariavel>();
		auxiliarListaDeCampos.add(entradaCampoNum);

		PDeclaracao entradaDeclaracaoNumerico = new ADeclaracao(auxiliarListaDeCampos, new ANumericoTipo());

		List<PDeclaracao> entradaListaDeDeclaracoes = new ArrayList<PDeclaracao>();
		entradaListaDeDeclaracoes.add(entradaDeclaracaoNumerico);

		ARegistroTipo entradaTipoRegistro = new ARegistroTipo(entradaListaDeDeclaracoes);

		TabelaDeAtributos entradaAtributosDoCampoNum = new TabelaDeAtributos();
		entradaAtributosDoCampoNum.inserir(Atributo.ID, entradaCampoNumIdentificador.toUpperCase());
		Simbolo entradaCampoNumSimbolo = Simbolo.obter(entradaCampoNumIdentificador.toUpperCase());
		entradaAtributosDoCampoNum.inserir(Atributo.SIMBOLO, entradaCampoNumSimbolo);
		entradaAtributosDoCampoNum.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoCampoNum.inserir(Atributo.COLUNA, entradaCampoNumColuna);
		entradaAtributosDoCampoNum.inserir(Atributo.STRING, entradaCampoNumIdentificador.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaCampoNum, entradaAtributosDoCampoNum);

		simularErroSemanticoDetectadoAnteriormente();

		// Execução do método
		try {
			analisadorDeDeclaracoes.outARegistroTipo(entradaTipoRegistro);
		} catch (Exception e) {
			fail("Se havia erro semântico, a análise semântica não deveria ter sido executada");
		}

		// Saída
		TabelaDeAtributos saidaAtributosDoTipo = analisadorSemantico.obterAtributos(entradaTipoRegistro);
		assertNull(saidaAtributosDoTipo);
	}

	// --------------------------------------------------------------------------------
	// Teste 2 - com entrada válida que contém apenas uma declaração (um tipo de
	// dado) com um campo
	// --------------------------------------------------------------------------------

	final Atributo[] atributosEsperadosDoTipoRegistro = new Atributo[] { Atributo.TIPO, Atributo.STRING };

	@Test
	public void outARegistroTipoTeste2() {
		// Entrada: registro (num numerico)
		String entradaCampoNumIdentificador = "num";
		int entradaLinha = 16, entradaCampoNumColuna = 22;
		PVariavel entradaCampoNum = new ASimplesVariavel(
				new TIdentificador(entradaCampoNumIdentificador, entradaLinha, entradaCampoNumColuna));

		List<PVariavel> auxiliarListaDeCampos = new ArrayList<PVariavel>();
		auxiliarListaDeCampos.add(entradaCampoNum);

		PDeclaracao entradaDeclaracaoNumerico = new ADeclaracao(auxiliarListaDeCampos, new ANumericoTipo());

		List<PDeclaracao> entradaListaDeDeclaracoes = new ArrayList<PDeclaracao>();
		entradaListaDeDeclaracoes.add(entradaDeclaracaoNumerico);

		ARegistroTipo entradaTipoRegistro = new ARegistroTipo(entradaListaDeDeclaracoes);

		TabelaDeAtributos entradaAtributosDoCampoNum = new TabelaDeAtributos();
		entradaAtributosDoCampoNum.inserir(Atributo.ID, entradaCampoNumIdentificador.toUpperCase());
		Simbolo entradaCampoNumSimbolo = Simbolo.obter(entradaCampoNumIdentificador.toUpperCase());
		entradaAtributosDoCampoNum.inserir(Atributo.SIMBOLO, entradaCampoNumSimbolo);
		entradaAtributosDoCampoNum.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoCampoNum.inserir(Atributo.COLUNA, entradaCampoNumColuna);
		entradaAtributosDoCampoNum.inserir(Atributo.STRING, entradaCampoNumIdentificador.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaCampoNum, entradaAtributosDoCampoNum);

		// Execução do método
		analisadorDeDeclaracoes.outARegistroTipo(entradaTipoRegistro);

		// Saída
		TabelaDeAtributos saidaAtributosDoTipo = analisadorSemantico.obterAtributos(entradaTipoRegistro);
		assertNotNull(saidaAtributosDoTipo);

		verificarAtributos(saidaAtributosDoTipo, atributosEsperadosDoTipoRegistro);

		TipoRegistro esperadoTipo = new TipoRegistro();
		esperadoTipo.getCampos().inserir(entradaCampoNumSimbolo, entradaAtributosDoCampoNum);

		Tipo saidaTipo = (Tipo) saidaAtributosDoTipo.obter(Atributo.TIPO);
		// TODO Defeito - TabelaDeSimbolos.equals() não está implementado
		assertEquals(esperadoTipo, saidaTipo);

		String saidaString = (String) saidaAtributosDoTipo.obter(Atributo.STRING);
		// TODO Defeito - TipoRegistro.toString() não está implementado
		assertEquals("REGISTRO([...])", saidaString);
	}

	// --------------------------------------------------------------------------------
	// Teste 3 - com entrada válida que contém uma declaração com dois campos
	// --------------------------------------------------------------------------------

	@Test
	public void outARegistroTipoTeste3() {
		// Entrada: registro (num, saldo numerico)
		String entradaCampoNumIdentificador = "num";
		int entradaLinha = 16, entradaCampoNumColuna = 22;
		PVariavel entradaCampoNum = new ASimplesVariavel(
				new TIdentificador(entradaCampoNumIdentificador, entradaLinha, entradaCampoNumColuna));

		String entradaCampoSaldoIdentificador = "saldo";
		int entradaCampoSaldoColuna = 27;
		PVariavel entradaCampoSaldo = new ASimplesVariavel(
				new TIdentificador(entradaCampoSaldoIdentificador, entradaLinha, entradaCampoSaldoColuna));

		List<PVariavel> auxiliarListaDeCampos = new ArrayList<PVariavel>();
		auxiliarListaDeCampos.add(entradaCampoNum);
		auxiliarListaDeCampos.add(entradaCampoSaldo);

		PDeclaracao entradaDeclaracaoNumerico = new ADeclaracao(auxiliarListaDeCampos, new ANumericoTipo());

		List<PDeclaracao> entradaListaDeDeclaracoes = new ArrayList<PDeclaracao>();
		entradaListaDeDeclaracoes.add(entradaDeclaracaoNumerico);

		ARegistroTipo entradaTipoRegistro = new ARegistroTipo(entradaListaDeDeclaracoes);

		TabelaDeAtributos entradaAtributosDoCampoNum = new TabelaDeAtributos();
		entradaAtributosDoCampoNum.inserir(Atributo.ID, entradaCampoNumIdentificador.toUpperCase());
		Simbolo entradaCampoNumSimbolo = Simbolo.obter(entradaCampoNumIdentificador.toUpperCase());
		entradaAtributosDoCampoNum.inserir(Atributo.SIMBOLO, entradaCampoNumSimbolo);
		entradaAtributosDoCampoNum.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoCampoNum.inserir(Atributo.COLUNA, entradaCampoNumColuna);
		entradaAtributosDoCampoNum.inserir(Atributo.STRING, entradaCampoNumIdentificador.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaCampoNum, entradaAtributosDoCampoNum);

		TabelaDeAtributos entradaAtributosDoCampoSaldo = new TabelaDeAtributos();
		entradaAtributosDoCampoSaldo.inserir(Atributo.ID, entradaCampoSaldoIdentificador.toUpperCase());
		Simbolo entradaCampoSaldoSimbolo = Simbolo.obter(entradaCampoSaldoIdentificador.toUpperCase());
		entradaAtributosDoCampoSaldo.inserir(Atributo.SIMBOLO, entradaCampoSaldoSimbolo);
		entradaAtributosDoCampoSaldo.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoCampoSaldo.inserir(Atributo.COLUNA, entradaCampoSaldoColuna);
		entradaAtributosDoCampoSaldo.inserir(Atributo.STRING, entradaCampoSaldoIdentificador.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaCampoSaldo, entradaAtributosDoCampoSaldo);

		// Execução do método
		analisadorDeDeclaracoes.outARegistroTipo(entradaTipoRegistro);

		// Saída
		TabelaDeAtributos saidaAtributosDoTipo = analisadorSemantico.obterAtributos(entradaTipoRegistro);
		assertNotNull(saidaAtributosDoTipo);

		verificarAtributos(saidaAtributosDoTipo, atributosEsperadosDoTipoRegistro);

		TipoRegistro esperadoTipo = new TipoRegistro();
		esperadoTipo.getCampos().inserir(entradaCampoNumSimbolo, entradaAtributosDoCampoNum);
		esperadoTipo.getCampos().inserir(entradaCampoSaldoSimbolo, entradaAtributosDoCampoSaldo);

		Tipo saidaTipo = (Tipo) saidaAtributosDoTipo.obter(Atributo.TIPO);
		// TODO Defeito - TabelaDeSimbolos.equals() não está implementado
		assertEquals(esperadoTipo, saidaTipo);

		String saidaString = (String) saidaAtributosDoTipo.obter(Atributo.STRING);
		// TODO Defeito - TipoRegistro.toString() não está implementado
		assertEquals("REGISTRO([...])", saidaString);
	}

	// --------------------------------------------------------------------------------
	// Teste 4 - com entrada inválida (no sentido de gerar erro semântico) que
	// contém duas declarações com ao todo quatro campos, sendo que um desses campos
	// foi declarado repetido
	// --------------------------------------------------------------------------------

	@Test
	public void outARegistroTipoTeste4() {
		// Entrada: registro (num, saldo numerico nome, num literal)
		String entradaCampoNumIdentificador = "num";
		int entradaLinha = 16, entradaCampoNumColuna = 22;
		PVariavel entradaCampoNum = new ASimplesVariavel(
				new TIdentificador(entradaCampoNumIdentificador, entradaLinha, entradaCampoNumColuna));

		String entradaCampoSaldoIdentificador = "saldo";
		int entradaCampoSaldoColuna = 27;
		PVariavel entradaCampoSaldo = new ASimplesVariavel(
				new TIdentificador(entradaCampoSaldoIdentificador, entradaLinha, entradaCampoSaldoColuna));

		List<PVariavel> auxiliarListaDeCampos = new ArrayList<PVariavel>();
		auxiliarListaDeCampos.add(entradaCampoNum);
		auxiliarListaDeCampos.add(entradaCampoSaldo);

		PDeclaracao entradaDeclaracaoNumerico = new ADeclaracao(auxiliarListaDeCampos, new ANumericoTipo());

		String entradaCampoNomeIdentificador = "nome";
		int entradaCampoNomeColuna = 42;
		PVariavel entradaCampoNome = new ASimplesVariavel(
				new TIdentificador(entradaCampoNomeIdentificador, entradaLinha, entradaCampoNomeColuna));

		String entradaCampoRepetidoIdentificador = entradaCampoNumIdentificador;
		int entradaCampoRepetidoColuna = 48;
		PVariavel entradaCampoRepetido = new ASimplesVariavel(
				new TIdentificador(entradaCampoRepetidoIdentificador, entradaLinha, entradaCampoRepetidoColuna));

		auxiliarListaDeCampos.clear();
		auxiliarListaDeCampos.add(entradaCampoNome);
		auxiliarListaDeCampos.add(entradaCampoRepetido);

		PDeclaracao entradaDeclaracaoLiteral = new ADeclaracao(auxiliarListaDeCampos, new ALiteralTipo());

		List<PDeclaracao> entradaListaDeDeclaracoes = new ArrayList<PDeclaracao>();
		entradaListaDeDeclaracoes.add(entradaDeclaracaoNumerico);
		entradaListaDeDeclaracoes.add(entradaDeclaracaoLiteral);

		ARegistroTipo entradaTipoRegistro = new ARegistroTipo(entradaListaDeDeclaracoes);

		TabelaDeAtributos entradaAtributosDoCampoNum = new TabelaDeAtributos();
		entradaAtributosDoCampoNum.inserir(Atributo.ID, entradaCampoNumIdentificador.toUpperCase());
		Simbolo entradaCampoNumSimbolo = Simbolo.obter(entradaCampoNumIdentificador.toUpperCase());
		entradaAtributosDoCampoNum.inserir(Atributo.SIMBOLO, entradaCampoNumSimbolo);
		entradaAtributosDoCampoNum.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoCampoNum.inserir(Atributo.COLUNA, entradaCampoNumColuna);
		entradaAtributosDoCampoNum.inserir(Atributo.STRING, entradaCampoNumIdentificador.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaCampoNum, entradaAtributosDoCampoNum);

		TabelaDeAtributos entradaAtributosDoCampoSaldo = new TabelaDeAtributos();
		entradaAtributosDoCampoSaldo.inserir(Atributo.ID, entradaCampoSaldoIdentificador.toUpperCase());
		Simbolo entradaCampoSaldoSimbolo = Simbolo.obter(entradaCampoSaldoIdentificador.toUpperCase());
		entradaAtributosDoCampoSaldo.inserir(Atributo.SIMBOLO, entradaCampoSaldoSimbolo);
		entradaAtributosDoCampoSaldo.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoCampoSaldo.inserir(Atributo.COLUNA, entradaCampoSaldoColuna);
		entradaAtributosDoCampoSaldo.inserir(Atributo.STRING, entradaCampoSaldoIdentificador.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaCampoSaldo, entradaAtributosDoCampoSaldo);

		TabelaDeAtributos entradaAtributosDoCampoNome = new TabelaDeAtributos();
		entradaAtributosDoCampoNome.inserir(Atributo.ID, entradaCampoNomeIdentificador.toUpperCase());
		Simbolo entradaCampoNomeSimbolo = Simbolo.obter(entradaCampoNomeIdentificador.toUpperCase());
		entradaAtributosDoCampoNome.inserir(Atributo.SIMBOLO, entradaCampoNomeSimbolo);
		entradaAtributosDoCampoNome.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoCampoNome.inserir(Atributo.COLUNA, entradaCampoNumColuna);
		entradaAtributosDoCampoNome.inserir(Atributo.STRING, entradaCampoNomeIdentificador.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaCampoNome, entradaAtributosDoCampoNome);

		TabelaDeAtributos entradaAtributosDoCampoRepetido = new TabelaDeAtributos();
		entradaAtributosDoCampoRepetido.inserir(Atributo.ID, entradaCampoRepetidoIdentificador.toUpperCase());
		Simbolo entradaCampoRepetidoSimbolo = Simbolo.obter(entradaCampoRepetidoIdentificador.toUpperCase());
		entradaAtributosDoCampoRepetido.inserir(Atributo.SIMBOLO, entradaCampoRepetidoSimbolo);
		entradaAtributosDoCampoRepetido.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoCampoRepetido.inserir(Atributo.COLUNA, entradaCampoRepetidoColuna);
		entradaAtributosDoCampoRepetido.inserir(Atributo.STRING, entradaCampoRepetidoIdentificador.toUpperCase());
		analisadorSemantico.gravarAtributos(entradaCampoRepetido, entradaAtributosDoCampoRepetido);

		// Execução do método
		analisadorDeDeclaracoes.outARegistroTipo(entradaTipoRegistro);

		// Saída
		// TODO Defeito - Na verdade, esse erro deveria ser detectado no método
		// outADeclaracao(), há código desnecessário no método outARegistroTipo()
		TabelaDeAtributos saidaAtributosDoTipo = analisadorSemantico.obterAtributos(entradaTipoRegistro);
		assertNull(saidaAtributosDoTipo);
		assertTrue(analisadorSemantico.haErroSemantico());
		// TODO Refatoração - Não é possível testar linha e coluna do erro semântico
	}

	// --------------------------------------------------------------------------------
	// Teste 5 - com entrada nula
	// --------------------------------------------------------------------------------

	@Test
	public void outARegistroTipoTeste5() {
		try {
			analisadorDeDeclaracoes.outARegistroTipo(null);
		} catch (AssertionError e) {
			// Esperado
			return;
		} catch (Exception e) {
		} finally {
			fail("Entrada nula não tratada");
		}
	}

	// ================================================================================
	// Método caseASubRotina()
	// ================================================================================

	// --------------------------------------------------------------------------------
	// Teste 1 - com erro semântico detectado antes da execução do método
	// --------------------------------------------------------------------------------

	@Test
	public void caseASubRotinaTeste1() {
		// Entrada:
		// sub-rotina verifica()
		// fim_sub_rotina verifica

		String entradaSubRotinaIdentificadorComoString = "verifica";
		int entradaLinha = 14, entradaSubRotinaColuna = 12;
		TIdentificador entradaIdentificador = new TIdentificador(entradaSubRotinaIdentificadorComoString, entradaLinha,
				entradaSubRotinaColuna);

		List<PDeclaracao> entradaParametros = new ArrayList<PDeclaracao>();

		String entradaSubRotinaIdentificadorFimComoString = entradaSubRotinaIdentificadorComoString;
		int entradaIdentificadorFimLinha = entradaLinha + 1, entradaIdentificadorFimColuna = 16;
		TIdentificador entradaIdentificadorFim = new TIdentificador(entradaSubRotinaIdentificadorFimComoString,
				entradaIdentificadorFimLinha, entradaIdentificadorFimColuna);

		ASubRotina entradaSubRotina = new ASubRotina(entradaIdentificador, entradaParametros,
				new ArrayList<PDeclaracao>(), new ArrayList<PComando>(), entradaIdentificadorFim);

		simularErroSemanticoDetectadoAnteriormente();

		// Execução do método
		try {
			analisadorDeDeclaracoes.caseASubRotina(entradaSubRotina);
		} catch (Exception e) {
			fail("Se havia erro semântico, a análise semântica não deveria ter sido executada");
		}

		// Saída
		TabelaDeAtributos saidaAtributosDaSubRotina = analisadorSemantico.obterAtributos(entradaSubRotina);
		assertNull(saidaAtributosDaSubRotina);
	}

	// --------------------------------------------------------------------------------
	// Teste 2 - com entrada inválida em que o identificador da sub-rotina já foi
	// declarado anteriormente
	// --------------------------------------------------------------------------------

	@Test
	public void caseASubRotinaTeste2() {
		// Entrada:
		// declare verifica numerico
		// ...
		// sub-rotina verifica()
		// fim_sub_rotina verifica

		String entradaVariavelIdentificadorComoString = "verifica";
		Simbolo entradaVariavelSimbolo = Simbolo.obter(entradaVariavelIdentificadorComoString.toUpperCase());
		int entradaVariavelLinha = 5, entradaVariavelColuna = 9;

		String entradaSubRotinaIdentificadorComoString = entradaVariavelIdentificadorComoString;
		int entradaLinha = 14, entradaSubRotinaColuna = 12;
		TIdentificador entradaIdentificador = new TIdentificador(entradaSubRotinaIdentificadorComoString, entradaLinha,
				entradaSubRotinaColuna);

		List<PDeclaracao> entradaParametros = new ArrayList<PDeclaracao>();

		String entradaSubRotinaIdentificadorFimComoString = entradaSubRotinaIdentificadorComoString;
		int entradaIdentificadorFimLinha = entradaLinha + 1, entradaIdentificadorFimColuna = 16;
		TIdentificador entradaIdentificadorFim = new TIdentificador(entradaSubRotinaIdentificadorFimComoString,
				entradaIdentificadorFimLinha, entradaIdentificadorFimColuna);

		ASubRotina entradaSubRotina = new ASubRotina(entradaIdentificador, entradaParametros,
				new ArrayList<PDeclaracao>(), new ArrayList<PComando>(), entradaIdentificadorFim);

		TabelaDeAtributos entradaVariavelAtributos = new TabelaDeAtributos();
		entradaVariavelAtributos.inserir(Atributo.ID, entradaVariavelIdentificadorComoString.toUpperCase());
		entradaVariavelAtributos.inserir(Atributo.SIMBOLO, entradaVariavelSimbolo);
		entradaVariavelAtributos.inserir(Atributo.LINHA, entradaVariavelLinha);
		entradaVariavelAtributos.inserir(Atributo.COLUNA, entradaVariavelColuna);
		entradaVariavelAtributos.inserir(Atributo.STRING, entradaVariavelIdentificadorComoString.toUpperCase());

		analisadorSemantico.getTabelaDeSimbolos().inserir(entradaVariavelSimbolo, entradaVariavelAtributos);

		// Execução do método
		analisadorDeDeclaracoes.caseASubRotina(entradaSubRotina);

		// Saída
		TabelaDeAtributos saidaAtributosDaSubRotina = analisadorSemantico.obterAtributos(entradaSubRotina);
		// TODO Defeito - Não deveria gravar atributos, se há erro semântico
		assertNull(saidaAtributosDaSubRotina);
		assertTrue(analisadorSemantico.haErroSemantico());
		// TODO Refatoração - Não é possível testar linha e coluna do erro semântico
	}

	// --------------------------------------------------------------------------------
	// Teste 3 - com entrada inválida em que o identificador ao final da sub-rotina
	// não é igual ao identificador do início
	// --------------------------------------------------------------------------------

	@Test
	public void caseASubRotinaTeste3() {
		// Entrada:
		// sub-rotina verifica()
		// fim_sub_rotina verificar

		String entradaSubRotinaIdentificadorComoString = "verifica";
		int entradaLinha = 14, entradaSubRotinaColuna = 12;
		TIdentificador entradaIdentificador = new TIdentificador(entradaSubRotinaIdentificadorComoString, entradaLinha,
				entradaSubRotinaColuna);

		List<PDeclaracao> entradaParametros = new ArrayList<PDeclaracao>();

		String entradaSubRotinaIdentificadorFimComoString = "verificar";
		int entradaIdentificadorFimLinha = entradaLinha + 1, entradaIdentificadorFimColuna = 16;
		TIdentificador entradaIdentificadorFim = new TIdentificador(entradaSubRotinaIdentificadorFimComoString,
				entradaIdentificadorFimLinha, entradaIdentificadorFimColuna);

		ASubRotina entradaSubRotina = new ASubRotina(entradaIdentificador, entradaParametros,
				new ArrayList<PDeclaracao>(), new ArrayList<PComando>(), entradaIdentificadorFim);

		// Execução do método
		analisadorDeDeclaracoes.caseASubRotina(entradaSubRotina);

		// Saída
		TabelaDeAtributos saidaAtributosDaSubRotina = analisadorSemantico.obterAtributos(entradaSubRotina);
		// TODO Defeito - Não deveria gravar atributos, se há erro semântico
		assertNull(saidaAtributosDaSubRotina);
		assertTrue(analisadorSemantico.haErroSemantico());
		// TODO Refatoração - Não é possível testar linha e coluna do erro semântico

	}

	// --------------------------------------------------------------------------------
	// Teste 4 - com entrada válida que não contém declaração
	// --------------------------------------------------------------------------------

	final Atributo[] atributosEsperadosDaSubRotina = new Atributo[] { Atributo.SIMBOLO, Atributo.ID, Atributo.LINHA,
			Atributo.COLUNA, Atributo.TIPO, Atributo.STRING };

	@Test
	public void caseASubRotinaTeste4() {
		// Entrada:
		// sub-rotina verifica()
		// fim_sub_rotina verifica

		String entradaSubRotinaIdentificadorComoString = "verifica";
		int entradaLinha = 14, entradaSubRotinaColuna = 12;
		TIdentificador entradaIdentificador = new TIdentificador(entradaSubRotinaIdentificadorComoString, entradaLinha,
				entradaSubRotinaColuna);

		List<PDeclaracao> entradaParametros = new ArrayList<PDeclaracao>();

		String entradaSubRotinaIdentificadorFimComoString = entradaSubRotinaIdentificadorComoString;
		int entradaIdentificadorFimLinha = entradaLinha + 1, entradaIdentificadorFimColuna = 16;
		TIdentificador entradaIdentificadorFim = new TIdentificador(entradaSubRotinaIdentificadorFimComoString,
				entradaIdentificadorFimLinha, entradaIdentificadorFimColuna);

		ASubRotina entradaSubRotina = new ASubRotina(entradaIdentificador, entradaParametros,
				new ArrayList<PDeclaracao>(), new ArrayList<PComando>(), entradaIdentificadorFim);

		// Execução do método
		analisadorDeDeclaracoes.caseASubRotina(entradaSubRotina);

		// Saída
		TabelaDeAtributos saidaAtributosDaSubRotina = analisadorSemantico.obterAtributos(entradaSubRotina);
		assertNotNull(saidaAtributosDaSubRotina);

		verificarAtributos(saidaAtributosDaSubRotina, atributosEsperadosDaSubRotina);

		String esperadoIdentificador = entradaSubRotinaIdentificadorComoString.toUpperCase();

		String saidaIdentificador = (String) saidaAtributosDaSubRotina.obter(Atributo.ID);
		assertEquals(esperadoIdentificador, saidaIdentificador);

		Simbolo esperadoSimbolo = Simbolo.obter(entradaSubRotinaIdentificadorComoString.toUpperCase());

		Simbolo saidaSimbolo = (Simbolo) saidaAtributosDaSubRotina.obter(Atributo.SIMBOLO);
		assertEquals(esperadoSimbolo, saidaSimbolo);

		Integer saidaLinha = (Integer) saidaAtributosDaSubRotina.obter(Atributo.LINHA);
		assertEquals(entradaLinha, (int) saidaLinha);

		Integer saidaColuna = (Integer) saidaAtributosDaSubRotina.obter(Atributo.COLUNA);
		assertEquals(entradaSubRotinaColuna, (int) saidaColuna);

		TipoSubrotina esperadoTipo = new TipoSubrotina(entradaSubRotina, null);

		Tipo saidaTipo = (Tipo) saidaAtributosDaSubRotina.obter(Atributo.TIPO);
		assertEquals(esperadoTipo, saidaTipo);

		String esperadoString = "VERIFICA()";

		String saidaString = (String) saidaAtributosDaSubRotina.obter(Atributo.STRING);
		assertEquals(esperadoString, saidaString);
	}

	// --------------------------------------------------------------------------------
	// Teste 5 - com entrada válida que contém uma declaração com um parâmetro
	// --------------------------------------------------------------------------------

	@Test
	public void caseASubRotinaTeste5() {
		// Entrada:
		// sub-rotina verifica(x numerico)
		// fim_sub_rotina verifica

		String entradaSubRotinaIdentificadorComoString = "verifica";
		int entradaLinha = 14, entradaSubRotinaColuna = 12;
		TIdentificador entradaIdentificador = new TIdentificador(entradaSubRotinaIdentificadorComoString, entradaLinha,
				entradaSubRotinaColuna);

		String entradaParametroXIdentificador = "x";
		int entradaParametroXColuna = 21;
		PVariavel entradaParametroX = new ASimplesVariavel(
				new TIdentificador(entradaParametroXIdentificador, entradaLinha, entradaParametroXColuna));

		List<PVariavel> auxiliarListaDeVariaveis = new ArrayList<PVariavel>();
		auxiliarListaDeVariaveis.add(entradaParametroX);

		PDeclaracao entradaDeclaracaoNumerico = new ADeclaracao(auxiliarListaDeVariaveis, new ANumericoTipo());

		List<PDeclaracao> entradaParametros = new ArrayList<PDeclaracao>();
		entradaParametros.add(entradaDeclaracaoNumerico);

		String entradaSubRotinaIdentificadorFimComoString = entradaSubRotinaIdentificadorComoString;
		int entradaIdentificadorFimLinha = entradaLinha + 1, entradaIdentificadorFimColuna = 16;
		TIdentificador entradaIdentificadorFim = new TIdentificador(entradaSubRotinaIdentificadorFimComoString,
				entradaIdentificadorFimLinha, entradaIdentificadorFimColuna);

		ASubRotina entradaSubRotina = new ASubRotina(entradaIdentificador, entradaParametros,
				new ArrayList<PDeclaracao>(), new ArrayList<PComando>(), entradaIdentificadorFim);

		TabelaDeAtributos entradaAtributosDoParametroX = new TabelaDeAtributos();
		entradaAtributosDoParametroX.inserir(Atributo.ID, entradaParametroXIdentificador.toUpperCase());
		Simbolo entradaParametroXSimbolo = Simbolo.obter(entradaParametroXIdentificador.toUpperCase());
		entradaAtributosDoParametroX.inserir(Atributo.SIMBOLO, entradaParametroXSimbolo);
		entradaAtributosDoParametroX.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoParametroX.inserir(Atributo.COLUNA, entradaParametroXColuna);
		entradaAtributosDoParametroX.inserir(Atributo.STRING, entradaParametroXIdentificador.toUpperCase());
		entradaAtributosDoParametroX.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));
		analisadorSemantico.gravarAtributos(entradaParametroX, entradaAtributosDoParametroX);

		// Execução do método
		analisadorDeDeclaracoes.caseASubRotina(entradaSubRotina);

		// Saída
		TabelaDeAtributos saidaAtributosDaSubRotina = analisadorSemantico.obterAtributos(entradaSubRotina);
		assertNotNull(saidaAtributosDaSubRotina);

		verificarAtributos(saidaAtributosDaSubRotina, atributosEsperadosDaSubRotina);

		String esperadoIdentificador = entradaSubRotinaIdentificadorComoString.toUpperCase();

		String saidaIdentificador = (String) saidaAtributosDaSubRotina.obter(Atributo.ID);
		assertEquals(esperadoIdentificador, saidaIdentificador);

		Simbolo esperadoSimbolo = Simbolo.obter(entradaSubRotinaIdentificadorComoString.toUpperCase());

		Simbolo saidaSimbolo = (Simbolo) saidaAtributosDaSubRotina.obter(Atributo.SIMBOLO);
		assertEquals(esperadoSimbolo, saidaSimbolo);

		Integer saidaLinha = (Integer) saidaAtributosDaSubRotina.obter(Atributo.LINHA);
		assertEquals(entradaLinha, (int) saidaLinha);

		Integer saidaColuna = (Integer) saidaAtributosDaSubRotina.obter(Atributo.COLUNA);
		assertEquals(entradaSubRotinaColuna, (int) saidaColuna);

		TipoSubrotina esperadoTipo = new TipoSubrotina(entradaSubRotina, null);
		esperadoTipo.getParametros().add(entradaParametroXSimbolo);
		esperadoTipo.getTabelaDeSimbolos().inserir(entradaParametroXSimbolo, entradaAtributosDoParametroX);

		Tipo saidaTipo = (Tipo) saidaAtributosDaSubRotina.obter(Atributo.TIPO);
		assertEquals(esperadoTipo, saidaTipo);

		String esperadoString = "VERIFICA(NUMERICO)";

		String saidaString = (String) saidaAtributosDaSubRotina.obter(Atributo.STRING);
		assertEquals(esperadoString, saidaString);
	}

	// --------------------------------------------------------------------------------
	// Teste 6 - com entrada válida que contém duas declarações com ao todo três
	// parâmetros
	// --------------------------------------------------------------------------------

	@Test
	public void caseASubRotinaTeste6() {
		// Entrada:
		// sub-rotina verifica(x, y numerico z literal)
		// fim_sub_rotina verifica

		String entradaSubRotinaIdentificadorComoString = "verifica";
		int entradaLinha = 14, entradaSubRotinaColuna = 12;
		TIdentificador entradaIdentificador = new TIdentificador(entradaSubRotinaIdentificadorComoString, entradaLinha,
				entradaSubRotinaColuna);

		String entradaParametroXIdentificador = "x";
		int entradaParametroXColuna = 21;
		PVariavel entradaParametroX = new ASimplesVariavel(
				new TIdentificador(entradaParametroXIdentificador, entradaLinha, entradaParametroXColuna));

		String entradaParametroYIdentificador = "y";
		int entradaParametroYColuna = 24;
		PVariavel entradaParametroY = new ASimplesVariavel(
				new TIdentificador(entradaParametroYIdentificador, entradaLinha, entradaParametroYColuna));

		List<PVariavel> auxiliarListaDeVariaveis = new ArrayList<PVariavel>();
		auxiliarListaDeVariaveis.add(entradaParametroX);
		auxiliarListaDeVariaveis.add(entradaParametroY);

		PDeclaracao entradaDeclaracaoNumerico = new ADeclaracao(auxiliarListaDeVariaveis, new ANumericoTipo());

		String entradaParametroZIdentificador = "z";
		int entradaParametroZColuna = 35;
		PVariavel entradaParametroZ = new ASimplesVariavel(
				new TIdentificador(entradaParametroZIdentificador, entradaLinha, entradaParametroZColuna));

		auxiliarListaDeVariaveis.clear();
		auxiliarListaDeVariaveis.add(entradaParametroZ);

		PDeclaracao entradaDeclaracaoLiteral = new ADeclaracao(auxiliarListaDeVariaveis, new ALiteralTipo());

		List<PDeclaracao> entradaParametros = new ArrayList<PDeclaracao>();
		entradaParametros.add(entradaDeclaracaoNumerico);
		entradaParametros.add(entradaDeclaracaoLiteral);

		String entradaSubRotinaIdentificadorFimComoString = entradaSubRotinaIdentificadorComoString;
		int entradaIdentificadorFimLinha = entradaLinha + 1, entradaIdentificadorFimColuna = 16;
		TIdentificador entradaIdentificadorFim = new TIdentificador(entradaSubRotinaIdentificadorFimComoString,
				entradaIdentificadorFimLinha, entradaIdentificadorFimColuna);

		ASubRotina entradaSubRotina = new ASubRotina(entradaIdentificador, entradaParametros,
				new ArrayList<PDeclaracao>(), new ArrayList<PComando>(), entradaIdentificadorFim);

		TabelaDeAtributos entradaAtributosDoParametroX = new TabelaDeAtributos();
		entradaAtributosDoParametroX.inserir(Atributo.ID, entradaParametroXIdentificador.toUpperCase());
		Simbolo entradaParametroXSimbolo = Simbolo.obter(entradaParametroXIdentificador.toUpperCase());
		entradaAtributosDoParametroX.inserir(Atributo.SIMBOLO, entradaParametroXSimbolo);
		entradaAtributosDoParametroX.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoParametroX.inserir(Atributo.COLUNA, entradaParametroXColuna);
		entradaAtributosDoParametroX.inserir(Atributo.STRING, entradaParametroXIdentificador.toUpperCase());
		entradaAtributosDoParametroX.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));
		analisadorSemantico.gravarAtributos(entradaParametroX, entradaAtributosDoParametroX);

		TabelaDeAtributos entradaAtributosDoParametroY = new TabelaDeAtributos();
		entradaAtributosDoParametroY.inserir(Atributo.ID, entradaParametroYIdentificador.toUpperCase());
		Simbolo entradaParametroYSimbolo = Simbolo.obter(entradaParametroYIdentificador.toUpperCase());
		entradaAtributosDoParametroY.inserir(Atributo.SIMBOLO, entradaParametroYSimbolo);
		entradaAtributosDoParametroY.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoParametroY.inserir(Atributo.COLUNA, entradaParametroYColuna);
		entradaAtributosDoParametroY.inserir(Atributo.STRING, entradaParametroYIdentificador.toUpperCase());
		entradaAtributosDoParametroY.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));
		analisadorSemantico.gravarAtributos(entradaParametroY, entradaAtributosDoParametroY);

		TabelaDeAtributos entradaAtributosDoParametroZ = new TabelaDeAtributos();
		entradaAtributosDoParametroZ.inserir(Atributo.ID, entradaParametroZIdentificador.toUpperCase());
		Simbolo entradaParametroZSimbolo = Simbolo.obter(entradaParametroZIdentificador.toUpperCase());
		entradaAtributosDoParametroZ.inserir(Atributo.SIMBOLO, entradaParametroZSimbolo);
		entradaAtributosDoParametroZ.inserir(Atributo.LINHA, entradaLinha);
		entradaAtributosDoParametroZ.inserir(Atributo.COLUNA, entradaParametroZColuna);
		entradaAtributosDoParametroZ.inserir(Atributo.STRING, entradaParametroZIdentificador.toUpperCase());
		entradaAtributosDoParametroZ.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.LITERAL));
		analisadorSemantico.gravarAtributos(entradaParametroZ, entradaAtributosDoParametroZ);

		// Execução do método
		analisadorDeDeclaracoes.caseASubRotina(entradaSubRotina);

		// Saída
		TabelaDeAtributos saidaAtributosDaSubRotina = analisadorSemantico.obterAtributos(entradaSubRotina);
		assertNotNull(saidaAtributosDaSubRotina);

		verificarAtributos(saidaAtributosDaSubRotina, atributosEsperadosDaSubRotina);

		String esperadoIdentificador = entradaSubRotinaIdentificadorComoString.toUpperCase();

		String saidaIdentificador = (String) saidaAtributosDaSubRotina.obter(Atributo.ID);
		assertEquals(esperadoIdentificador, saidaIdentificador);

		Simbolo esperadoSimbolo = Simbolo.obter(entradaSubRotinaIdentificadorComoString.toUpperCase());

		Simbolo saidaSimbolo = (Simbolo) saidaAtributosDaSubRotina.obter(Atributo.SIMBOLO);
		assertEquals(esperadoSimbolo, saidaSimbolo);

		Integer saidaLinha = (Integer) saidaAtributosDaSubRotina.obter(Atributo.LINHA);
		assertEquals(entradaLinha, (int) saidaLinha);

		Integer saidaColuna = (Integer) saidaAtributosDaSubRotina.obter(Atributo.COLUNA);
		assertEquals(entradaSubRotinaColuna, (int) saidaColuna);

		TipoSubrotina esperadoTipo = new TipoSubrotina(entradaSubRotina, null);
		esperadoTipo.getParametros().add(entradaParametroXSimbolo);
		esperadoTipo.getTabelaDeSimbolos().inserir(entradaParametroXSimbolo, entradaAtributosDoParametroX);
		esperadoTipo.getParametros().add(entradaParametroYSimbolo);
		esperadoTipo.getTabelaDeSimbolos().inserir(entradaParametroYSimbolo, entradaAtributosDoParametroY);
		esperadoTipo.getParametros().add(entradaParametroZSimbolo);
		esperadoTipo.getTabelaDeSimbolos().inserir(entradaParametroZSimbolo, entradaAtributosDoParametroZ);

		Tipo saidaTipo = (Tipo) saidaAtributosDaSubRotina.obter(Atributo.TIPO);
		assertEquals(esperadoTipo, saidaTipo);

		String esperadoString = "VERIFICA(NUMERICO, NUMERICO, LITERAL)";

		String saidaString = (String) saidaAtributosDaSubRotina.obter(Atributo.STRING);
		assertEquals(esperadoString, saidaString);
	}

	// Entrada:
	// sub-rotina verifica(x, y numerico z, y literal)
	// fim_sub_rotina verifica
	// O parâmetro repetido é um erro semântico já detectado no método
	// outADeclaracao()

	// --------------------------------------------------------------------------------
	// Teste 7 - com entrada nula
	// --------------------------------------------------------------------------------

	@Test
	public void caseASubRotinaTeste7() {
		try {
			analisadorDeDeclaracoes.caseASubRotina(null);
		} catch (AssertionError e) {
			// Esperado
			return;
		} catch (Exception e) {
		} finally {
			fail("Entrada nula não tratada");
		}
	}
}