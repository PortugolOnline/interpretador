package br.com.vinyanalista.portugol.interpretador.analise;

import static org.junit.Assert.*;

import java.util.*;

import org.junit.*;

import br.com.vinyanalista.portugol.base.node.*;
import br.com.vinyanalista.portugol.base.parser.Parser;
import br.com.vinyanalista.portugol.interpretador.simbolo.*;
import br.com.vinyanalista.portugol.interpretador.tipo.*;

public class AnalisadorSemanticoTeste {
	AnalisadorSemantico analisadorSemantico;
	AnalisadorDeComandos analisadorDeComandos;
	AnalisadorDeDeclaracoes analisadorDeDeclaracoes;
	AnalisadorDeExpressoes analisadorDeExpressoes;

	@Before
	public void setUp() {
		analisadorSemantico = new AnalisadorSemantico((Parser) null);
		analisadorDeComandos = new AnalisadorDeComandos(analisadorSemantico);
		analisadorDeDeclaracoes = new AnalisadorDeDeclaracoes(analisadorSemantico);
		analisadorDeExpressoes = new AnalisadorDeExpressoes(analisadorSemantico);
	}

	@Test
	public void atributosACampoPosicaoDeMemoriaTeste1() {
		// Par DU (21,9)
		// AnalisadorDeExpressoes.outACampoPosicaoDeMemoria()
		// AnalisadorDeComandos.caseAAtribuicaoComando()

		// Entrada: declare conta registro (num numerico)
		TabelaDeAtributos atributosDaDeclaracaoDoRegistro = new TabelaDeAtributos();
		TabelaDeAtributos atributosDaDeclaracaoDoCampo = new TabelaDeAtributos();

		String identificadorDoRegistro = "CONTA";
		String identificadorDoCampo = "NUM";

		Simbolo simboloDoRegistro = Simbolo.obter(identificadorDoRegistro);
		Simbolo simboloDoCampo = Simbolo.obter(identificadorDoCampo);

		atributosDaDeclaracaoDoCampo.inserir(Atributo.SIMBOLO, simboloDoCampo);
		atributosDaDeclaracaoDoCampo.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));

		TipoRegistro tipoDoRegistro = new TipoRegistro();
		tipoDoRegistro.getCampos().inserir(simboloDoCampo, atributosDaDeclaracaoDoCampo);

		atributosDaDeclaracaoDoRegistro.inserir(Atributo.TIPO, tipoDoRegistro);
		analisadorSemantico.getTabelaDeSimbolos().inserir(simboloDoRegistro, atributosDaDeclaracaoDoRegistro);

		// Entrada: conta.num <- 0
		PVariavel registro = new ASimplesVariavel(new TIdentificador(identificadorDoRegistro));
		PVariavel campo = new ASimplesVariavel(new TIdentificador(identificadorDoCampo));
		ACampoPosicaoDeMemoria posicaoDeMemoria = new ACampoPosicaoDeMemoria(registro, campo);

		String stringDaExpressao = "0";
		PExpressao expressao = new AValorExpressao(new AInteiroValor(new TNumeroInteiro(stringDaExpressao)));

		AAtribuicaoComando comando = new AAtribuicaoComando(posicaoDeMemoria, expressao);

		TabelaDeAtributos atributosDoRegistro = new TabelaDeAtributos();
		atributosDoRegistro.inserir(Atributo.ID, identificadorDoRegistro);
		atributosDoRegistro.inserir(Atributo.SIMBOLO, simboloDoRegistro);
		int linhaDoRegistro = 3, colunaDoRegistro = 1;
		atributosDoRegistro.inserir(Atributo.LINHA, linhaDoRegistro);
		atributosDoRegistro.inserir(Atributo.COLUNA, colunaDoRegistro);
		atributosDoRegistro.inserir(Atributo.STRING, identificadorDoRegistro);
		analisadorSemantico.gravarAtributos(registro, atributosDoRegistro);

		TabelaDeAtributos atributosDoCampo = new TabelaDeAtributos();
		atributosDoCampo.inserir(Atributo.ID, identificadorDoCampo);
		atributosDoCampo.inserir(Atributo.SIMBOLO, simboloDoCampo);
		atributosDoCampo.inserir(Atributo.STRING, identificadorDoCampo);
		analisadorSemantico.gravarAtributos(campo, atributosDoCampo);

		TabelaDeAtributos atributosDaExpressao = new TabelaDeAtributos();
		atributosDaExpressao.inserir(Atributo.STRING, stringDaExpressao);
		atributosDaExpressao.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));
		analisadorSemantico.gravarAtributos(expressao, atributosDaExpressao);

		// Execução
		analisadorDeExpressoes.outACampoPosicaoDeMemoria(posicaoDeMemoria);
		analisadorDeComandos.caseAAtribuicaoComando(comando);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());
		TabelaDeAtributos atributosDoComando = analisadorSemantico.obterAtributos(comando);
		assertNotNull(atributosDoComando);
		assertEquals(linhaDoRegistro, atributosDoComando.obter(Atributo.LINHA));
		assertEquals(colunaDoRegistro, atributosDoComando.obter(Atributo.COLUNA));
		String esperadaStringDoComando = identificadorDoRegistro + "." + identificadorDoCampo + " <- "
				+ stringDaExpressao;
		assertEquals(esperadaStringDoComando, atributosDoComando.obter(Atributo.STRING));
	}

	@Test
	public void atributosACampoPosicaoDeMemoriaTeste2() {
		// Par DU (21,10)
		// AnalisadorDeExpressoes.outACampoPosicaoDeMemoria
		// AnalisadorDeComandos.caseAEntradaComando

		// Entrada: declare conta registro (num numerico)
		TabelaDeAtributos atributosDaDeclaracaoDoRegistro = new TabelaDeAtributos();
		TabelaDeAtributos atributosDaDeclaracaoDoCampo = new TabelaDeAtributos();

		String identificadorDoRegistro = "CONTA";
		String identificadorDoCampo = "NUM";

		Simbolo simboloDoRegistro = Simbolo.obter(identificadorDoRegistro);
		Simbolo simboloDoCampo = Simbolo.obter(identificadorDoCampo);

		atributosDaDeclaracaoDoCampo.inserir(Atributo.SIMBOLO, simboloDoCampo);
		atributosDaDeclaracaoDoCampo.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));

		TipoRegistro tipoDoRegistro = new TipoRegistro();
		tipoDoRegistro.getCampos().inserir(simboloDoCampo, atributosDaDeclaracaoDoCampo);

		atributosDaDeclaracaoDoRegistro.inserir(Atributo.TIPO, tipoDoRegistro);
		analisadorSemantico.getTabelaDeSimbolos().inserir(simboloDoRegistro, atributosDaDeclaracaoDoRegistro);

		// Entrada: leia conta.num
		PVariavel registro = new ASimplesVariavel(new TIdentificador(identificadorDoRegistro));
		PVariavel campo = new ASimplesVariavel(new TIdentificador(identificadorDoCampo));
		ACampoPosicaoDeMemoria posicaoDeMemoria = new ACampoPosicaoDeMemoria(registro, campo);

		List<PPosicaoDeMemoria> listaDePosicoesDeMemoria = new ArrayList<PPosicaoDeMemoria>();
		listaDePosicoesDeMemoria.add(posicaoDeMemoria);
		AEntradaComando comando = new AEntradaComando(listaDePosicoesDeMemoria);

		TabelaDeAtributos atributosDoRegistro = new TabelaDeAtributos();
		atributosDoRegistro.inserir(Atributo.ID, identificadorDoRegistro);
		atributosDoRegistro.inserir(Atributo.SIMBOLO, simboloDoRegistro);
		int linhaDoRegistro = 3, colunaDoRegistro = 6;
		atributosDoRegistro.inserir(Atributo.LINHA, linhaDoRegistro);
		atributosDoRegistro.inserir(Atributo.COLUNA, colunaDoRegistro);
		atributosDoRegistro.inserir(Atributo.STRING, identificadorDoRegistro);
		analisadorSemantico.gravarAtributos(registro, atributosDoRegistro);

		TabelaDeAtributos atributosDoCampo = new TabelaDeAtributos();
		atributosDoCampo.inserir(Atributo.ID, identificadorDoCampo);
		atributosDoCampo.inserir(Atributo.SIMBOLO, simboloDoCampo);
		atributosDoCampo.inserir(Atributo.STRING, identificadorDoCampo);
		analisadorSemantico.gravarAtributos(campo, atributosDoCampo);

		// Execução
		analisadorDeExpressoes.outACampoPosicaoDeMemoria(posicaoDeMemoria);
		analisadorDeComandos.caseAEntradaComando(comando);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());
		TabelaDeAtributos atributosDoComando = analisadorSemantico.obterAtributos(comando);
		assertNotNull(atributosDoComando);
		String esperadaStringDoComando = "LEIA [...]";
		assertEquals(esperadaStringDoComando, atributosDoComando.obter(Atributo.STRING));
	}

	@Test
	public void atributosACampoPosicaoDeMemoriaTeste3() {
		// Par DU (21,37)
		// AnalisadorDeExpressoes.outACampoPosicaoDeMemoria
		// AnalisadorDeExpressoes.outAPosicaoDeMemoriaExpressao

		// Entrada: declare conta registro (num numerico)
		TabelaDeAtributos atributosDaDeclaracaoDoRegistro = new TabelaDeAtributos();
		TabelaDeAtributos atributosDaDeclaracaoDoCampo = new TabelaDeAtributos();

		String identificadorDoRegistro = "CONTA";
		String identificadorDoCampo = "NUM";

		Simbolo simboloDoRegistro = Simbolo.obter(identificadorDoRegistro);
		Simbolo simboloDoCampo = Simbolo.obter(identificadorDoCampo);

		atributosDaDeclaracaoDoCampo.inserir(Atributo.SIMBOLO, simboloDoCampo);
		atributosDaDeclaracaoDoCampo.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));

		TipoRegistro tipoDoRegistro = new TipoRegistro();
		tipoDoRegistro.getCampos().inserir(simboloDoCampo, atributosDaDeclaracaoDoCampo);

		atributosDaDeclaracaoDoRegistro.inserir(Atributo.TIPO, tipoDoRegistro);
		analisadorSemantico.getTabelaDeSimbolos().inserir(simboloDoRegistro, atributosDaDeclaracaoDoRegistro);

		// Entrada: conta.num
		PVariavel registro = new ASimplesVariavel(new TIdentificador(identificadorDoRegistro));
		PVariavel campo = new ASimplesVariavel(new TIdentificador(identificadorDoCampo));
		ACampoPosicaoDeMemoria posicaoDeMemoria = new ACampoPosicaoDeMemoria(registro, campo);
		APosicaoDeMemoriaExpressao expressao = new APosicaoDeMemoriaExpressao(posicaoDeMemoria);

		TabelaDeAtributos atributosDoRegistro = new TabelaDeAtributos();
		atributosDoRegistro.inserir(Atributo.ID, identificadorDoRegistro);
		atributosDoRegistro.inserir(Atributo.SIMBOLO, simboloDoRegistro);
		int linhaDoRegistro = 3, colunaDoRegistro = 1;
		atributosDoRegistro.inserir(Atributo.LINHA, linhaDoRegistro);
		atributosDoRegistro.inserir(Atributo.COLUNA, colunaDoRegistro);
		atributosDoRegistro.inserir(Atributo.STRING, identificadorDoRegistro);
		analisadorSemantico.gravarAtributos(registro, atributosDoRegistro);

		TabelaDeAtributos atributosDoCampo = new TabelaDeAtributos();
		atributosDoCampo.inserir(Atributo.ID, identificadorDoCampo);
		atributosDoCampo.inserir(Atributo.SIMBOLO, simboloDoCampo);
		atributosDoCampo.inserir(Atributo.STRING, identificadorDoCampo);
		analisadorSemantico.gravarAtributos(campo, atributosDoCampo);

		// Execução
		analisadorDeExpressoes.outACampoPosicaoDeMemoria(posicaoDeMemoria);
		analisadorDeExpressoes.outAPosicaoDeMemoriaExpressao(expressao);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());
		TabelaDeAtributos atributosDaPosicaoDeMemoria = analisadorSemantico.obterAtributos(posicaoDeMemoria);
		assertNotNull(atributosDaPosicaoDeMemoria);
		TabelaDeAtributos atributosDaExpressao = analisadorSemantico.obterAtributos(expressao);
		assertNotNull(atributosDaPosicaoDeMemoria);
		// TODO Defeito - TabelaDeAtributos.equals() não implementado
		assertEquals(atributosDaPosicaoDeMemoria, atributosDaExpressao);
	}

	@Test
	public void atributosAChamadaASubRotinaTeste1() {
		// Par DU (40,16)
		// AnalisadorDeExpressoes.outAChamadaASubRotina()
		// AnalisadorDeComandos.caseAChamadaASubRotinaComando()

		// Entrada: limpar_tela()
		String identificador = "LIMPAR_TELA";
		int linha = 3, coluna = 1;

		AChamadaASubRotina chamada = new AChamadaASubRotina(new TIdentificador(identificador, linha, coluna),
				new ArrayList<PExpressao>());

		AChamadaASubRotinaComando comando = new AChamadaASubRotinaComando(chamada);

		// Execução
		analisadorDeExpressoes.outAChamadaASubRotina(chamada);
		analisadorDeComandos.caseAChamadaASubRotinaComando(comando);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDaChamada = analisadorSemantico.obterAtributos(chamada);
		assertNotNull(atributosDaChamada);

		TabelaDeAtributos atributosDoComando = analisadorSemantico.obterAtributos(comando);
		assertNotNull(atributosDoComando);
		// TODO Defeito - TabelaDeAtributos.equals() não implementado
		assertEquals(atributosDaChamada, atributosDoComando);

		assertEquals(identificador, atributosDoComando.obter(Atributo.ID));
		Simbolo esperadoSimbolo = Simbolo.obter(identificador);
		assertEquals(esperadoSimbolo, atributosDoComando.obter(Atributo.SIMBOLO));
		assertEquals(linha, atributosDoComando.obter(Atributo.LINHA));
		assertEquals(coluna, atributosDoComando.obter(Atributo.COLUNA));
		String esperadaString = identificador + "()";
		assertEquals(esperadaString, atributosDoComando.obter(Atributo.STRING));
		Tipo tipoEsperado = new Tipo(TipoPrimitivo.DETERMINADO_EM_TEMPO_DE_EXECUCAO);
		assertEquals(tipoEsperado, atributosDoComando.obter(Atributo.TIPO));
	}

	@Test
	public void atributosAChamadaASubRotinaTeste2() {
		// TODO Testar Par DU (40,38)
		// AnalisadorDeExpressoes.outAChamadaASubRotina()
		// AnalisadorDeExpressoes.outAChamadaASubRotinaExpressao()
	}

	@Test
	public void atributosAInteiroValorTeste() {
		// Par DU (41,39)
		// AnalisadorDeExpressoes.outAInteiroValor()
		// AnalisadorDeExpressoes.outAValorExpressao()

		// Entrada: 1
		AInteiroValor valor = new AInteiroValor(new TNumeroInteiro("1", 3, 14));
		AValorExpressao expressao = new AValorExpressao(valor);

		// Execução
		analisadorDeExpressoes.outAInteiroValor(valor);
		analisadorDeExpressoes.outAValorExpressao(expressao);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());
		TabelaDeAtributos atributosDoValor = analisadorSemantico.obterAtributos(valor);
		assertNotNull(atributosDoValor);
		TabelaDeAtributos atributosDaExpressao = analisadorSemantico.obterAtributos(expressao);
		assertNotNull(atributosDaExpressao);
		// TODO Defeito - TabelaDeAtributos.equals() não implementado
		assertEquals(atributosDoValor, atributosDaExpressao);
	}

	@Test
	public void atributosALiteralTipoTeste() {
		// Par DU (4,8)
		// AnalisadorDeDeclaracoes.outALiteralTipo()
		// AnalisadorDeDeclaracoes.outADeclaracao()

		// Entrada: declare teste literal
		ALiteralTipo tipoLiteral = new ALiteralTipo();

		String identificadorDaVariavelTeste = "TESTE";
		int linha = 2, colunaDaVariavelTeste = 9;
		PVariavel variavelTeste = new ASimplesVariavel(
				new TIdentificador(identificadorDaVariavelTeste, linha, colunaDaVariavelTeste));

		List<PVariavel> listaDeVariaveis = new ArrayList<PVariavel>();
		listaDeVariaveis.add(variavelTeste);

		ADeclaracao declaracao = new ADeclaracao(listaDeVariaveis, tipoLiteral);

		TabelaDeAtributos entradaAtributosDaVariavelTeste = new TabelaDeAtributos();
		entradaAtributosDaVariavelTeste.inserir(Atributo.ID, identificadorDaVariavelTeste);
		Simbolo simboloDaVariavelTeste = Simbolo.obter(identificadorDaVariavelTeste);
		entradaAtributosDaVariavelTeste.inserir(Atributo.SIMBOLO, simboloDaVariavelTeste);
		entradaAtributosDaVariavelTeste.inserir(Atributo.LINHA, linha);
		entradaAtributosDaVariavelTeste.inserir(Atributo.COLUNA, colunaDaVariavelTeste);
		analisadorSemantico.gravarAtributos(variavelTeste, entradaAtributosDaVariavelTeste);

		// Execução
		analisadorDeDeclaracoes.outALiteralTipo(tipoLiteral);
		analisadorDeDeclaracoes.outADeclaracao(declaracao);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDaDeclaracao = analisadorSemantico.obterAtributos(declaracao);
		assertNotNull(atributosDaDeclaracao);

		AnalisadorDeDeclaracoesTeste.verificarAtributos(atributosDaDeclaracao,
				AnalisadorDeDeclaracoesTeste.atributosEsperadosDaDeclaracao);

		TabelaDeAtributos saidaAtributosDaVariavelTeste = analisadorSemantico.obterAtributos(variavelTeste);
		assertNotNull(saidaAtributosDaVariavelTeste);

		Tipo saidaTipoDaVariavelTeste = (Tipo) saidaAtributosDaVariavelTeste.obter(Atributo.TIPO);
		assertNotNull(saidaTipoDaVariavelTeste);
		Tipo esperadoTipo = new Tipo(TipoPrimitivo.LITERAL);
		assertEquals(esperadoTipo, saidaTipoDaVariavelTeste);

		assertNotNull(analisadorSemantico.getTabelaDeSimbolos().obter(simboloDaVariavelTeste));

		Tipo saidaTipoDaDeclaracao = (Tipo) atributosDaDeclaracao.obter(Atributo.TIPO);
		assertNotNull(saidaTipoDaDeclaracao);
		assertEquals(esperadoTipo, saidaTipoDaDeclaracao);

		String saidaStringDaDeclaracao = (String) atributosDaDeclaracao.obter(Atributo.STRING);
		assertEquals("[...] LITERAL", saidaStringDaDeclaracao);
	}

	@Test
	public void atributosALiteralValorTeste() {
		// Par DU (44,39)
		// AnalisadorDeExpressoes.outALiteralValor()
		// AnalisadorDeExpressoes.outAValorExpressao()

		// Entrada: "um teste"
		ALiteralValor valor = new ALiteralValor(new TCadeiaDeCaracteres("um teste", 3, 14));
		AValorExpressao expressao = new AValorExpressao(valor);

		// Execução
		analisadorDeExpressoes.outALiteralValor(valor);
		analisadorDeExpressoes.outAValorExpressao(expressao);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());
		TabelaDeAtributos atributosDoValor = analisadorSemantico.obterAtributos(valor);
		assertNotNull(atributosDoValor);
		TabelaDeAtributos atributosDaExpressao = analisadorSemantico.obterAtributos(expressao);
		assertNotNull(atributosDaExpressao);
		// TODO Defeito - TabelaDeAtributos.equals() não implementado
		assertEquals(atributosDoValor, atributosDaExpressao);
	}

	@Test
	public void atributosALogicoTipoTeste() {
		// Par DU (5,8)
		// AnalisadorDeDeclaracoes.outALogicoTipo()
		// AnalisadorDeDeclaracoes.outADeclaracao()

		// Entrada: declare teste logico
		ALogicoTipo tipoLogico = new ALogicoTipo();

		String identificadorDaVariavelTeste = "TESTE";
		int linha = 2, colunaDaVariavelTeste = 9;
		PVariavel variavelTeste = new ASimplesVariavel(
				new TIdentificador(identificadorDaVariavelTeste, linha, colunaDaVariavelTeste));

		List<PVariavel> listaDeVariaveis = new ArrayList<PVariavel>();
		listaDeVariaveis.add(variavelTeste);

		ADeclaracao declaracao = new ADeclaracao(listaDeVariaveis, tipoLogico);

		TabelaDeAtributos entradaAtributosDaVariavelTeste = new TabelaDeAtributos();
		entradaAtributosDaVariavelTeste.inserir(Atributo.ID, identificadorDaVariavelTeste);
		Simbolo simboloDaVariavelTeste = Simbolo.obter(identificadorDaVariavelTeste);
		entradaAtributosDaVariavelTeste.inserir(Atributo.SIMBOLO, simboloDaVariavelTeste);
		entradaAtributosDaVariavelTeste.inserir(Atributo.LINHA, linha);
		entradaAtributosDaVariavelTeste.inserir(Atributo.COLUNA, colunaDaVariavelTeste);
		analisadorSemantico.gravarAtributos(variavelTeste, entradaAtributosDaVariavelTeste);

		// Execução
		analisadorDeDeclaracoes.outALogicoTipo(tipoLogico);
		analisadorDeDeclaracoes.outADeclaracao(declaracao);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDaDeclaracao = analisadorSemantico.obterAtributos(declaracao);
		assertNotNull(atributosDaDeclaracao);

		AnalisadorDeDeclaracoesTeste.verificarAtributos(atributosDaDeclaracao,
				AnalisadorDeDeclaracoesTeste.atributosEsperadosDaDeclaracao);

		TabelaDeAtributos saidaAtributosDaVariavelTeste = analisadorSemantico.obterAtributos(variavelTeste);
		assertNotNull(saidaAtributosDaVariavelTeste);

		Tipo saidaTipoDaVariavelTeste = (Tipo) saidaAtributosDaVariavelTeste.obter(Atributo.TIPO);
		assertNotNull(saidaTipoDaVariavelTeste);
		Tipo esperadoTipo = new Tipo(TipoPrimitivo.LOGICO);
		assertEquals(esperadoTipo, saidaTipoDaVariavelTeste);

		assertNotNull(analisadorSemantico.getTabelaDeSimbolos().obter(simboloDaVariavelTeste));

		Tipo saidaTipoDaDeclaracao = (Tipo) atributosDaDeclaracao.obter(Atributo.TIPO);
		assertNotNull(saidaTipoDaDeclaracao);
		assertEquals(esperadoTipo, saidaTipoDaDeclaracao);

		String saidaStringDaDeclaracao = (String) atributosDaDeclaracao.obter(Atributo.STRING);
		assertEquals("[...] LOGICO", saidaStringDaDeclaracao);
	}

	@Test
	public void atributosALogicoValorTeste() {
		// Par DU (43,39)
		// AnalisadorDeExpressoes.outALogicoValor()
		// AnalisadorDeExpressoes.outAValorExpressao()

		// Entrada: verdadeiro
		ALogicoValor valor = new ALogicoValor(new TValorLogico("verdadeiro", 3, 14));
		AValorExpressao expressao = new AValorExpressao(valor);

		// Execução
		analisadorDeExpressoes.outALogicoValor(valor);
		analisadorDeExpressoes.outAValorExpressao(expressao);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());
		TabelaDeAtributos atributosDoValor = analisadorSemantico.obterAtributos(valor);
		assertNotNull(atributosDoValor);
		TabelaDeAtributos atributosDaExpressao = analisadorSemantico.obterAtributos(expressao);
		assertNotNull(atributosDaExpressao);
		// TODO Defeito - TabelaDeAtributos.equals() não implementado
		assertEquals(atributosDoValor, atributosDaExpressao);
	}

	@Test
	public void atributosANegacaoExpressaoTeste() {
		// TODO Testar Par DU (36,9)
		// AnalisadorDeExpressoes.outANegacaoExpressao()
		// AnalisadorDeComandos.caseAAtribuicaoComando()
	}

	@Test
	public void atributosANumericoTipoTeste() {
		// Par DU (3,8)
		// AnalisadorDeDeclaracoes.outANumericoTipo()
		// AnalisadorDeDeclaracoes.outADeclaracao()

		// Entrada: declare teste numerico
		ANumericoTipo tipoNumerico = new ANumericoTipo();

		String identificadorDaVariavelTeste = "TESTE";
		int linha = 2, colunaDaVariavelTeste = 9;
		PVariavel variavelTeste = new ASimplesVariavel(
				new TIdentificador(identificadorDaVariavelTeste, linha, colunaDaVariavelTeste));

		List<PVariavel> listaDeVariaveis = new ArrayList<PVariavel>();
		listaDeVariaveis.add(variavelTeste);

		ADeclaracao declaracao = new ADeclaracao(listaDeVariaveis, tipoNumerico);

		TabelaDeAtributos entradaAtributosDaVariavelTeste = new TabelaDeAtributos();
		entradaAtributosDaVariavelTeste.inserir(Atributo.ID, identificadorDaVariavelTeste);
		Simbolo simboloDaVariavelTeste = Simbolo.obter(identificadorDaVariavelTeste);
		entradaAtributosDaVariavelTeste.inserir(Atributo.SIMBOLO, simboloDaVariavelTeste);
		entradaAtributosDaVariavelTeste.inserir(Atributo.LINHA, linha);
		entradaAtributosDaVariavelTeste.inserir(Atributo.COLUNA, colunaDaVariavelTeste);
		analisadorSemantico.gravarAtributos(variavelTeste, entradaAtributosDaVariavelTeste);

		// Execução
		analisadorDeDeclaracoes.outANumericoTipo(tipoNumerico);
		analisadorDeDeclaracoes.outADeclaracao(declaracao);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDaDeclaracao = analisadorSemantico.obterAtributos(declaracao);
		assertNotNull(atributosDaDeclaracao);

		AnalisadorDeDeclaracoesTeste.verificarAtributos(atributosDaDeclaracao,
				AnalisadorDeDeclaracoesTeste.atributosEsperadosDaDeclaracao);

		TabelaDeAtributos saidaAtributosDaVariavelTeste = analisadorSemantico.obterAtributos(variavelTeste);
		assertNotNull(saidaAtributosDaVariavelTeste);

		Tipo saidaTipoDaVariavelTeste = (Tipo) saidaAtributosDaVariavelTeste.obter(Atributo.TIPO);
		assertNotNull(saidaTipoDaVariavelTeste);
		Tipo esperadoTipo = new Tipo(TipoPrimitivo.NUMERICO);
		assertEquals(esperadoTipo, saidaTipoDaVariavelTeste);

		assertNotNull(analisadorSemantico.getTabelaDeSimbolos().obter(simboloDaVariavelTeste));

		Tipo saidaTipoDaDeclaracao = (Tipo) atributosDaDeclaracao.obter(Atributo.TIPO);
		assertNotNull(saidaTipoDaDeclaracao);
		assertEquals(esperadoTipo, saidaTipoDaDeclaracao);

		String saidaStringDaDeclaracao = (String) atributosDaDeclaracao.obter(Atributo.STRING);
		assertEquals("[...] NUMERICO", saidaStringDaDeclaracao);
	}

	@Test
	public void atributosARealValorTeste() {
		// Par DU (42,39)
		// AnalisadorDeExpressoes.outARealValor()
		// AnalisadorDeExpressoes.outAValorExpressao()

		// Entrada: 9.5
		ARealValor valor = new ARealValor(new TNumeroReal("9.5", 3, 14));
		AValorExpressao expressao = new AValorExpressao(valor);

		// Execução
		analisadorDeExpressoes.outARealValor(valor);
		analisadorDeExpressoes.outAValorExpressao(expressao);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());
		TabelaDeAtributos atributosDoValor = analisadorSemantico.obterAtributos(valor);
		assertNotNull(atributosDoValor);
		TabelaDeAtributos atributosDaExpressao = analisadorSemantico.obterAtributos(expressao);
		assertNotNull(atributosDaExpressao);
		// TODO Defeito - TabelaDeAtributos.equals() não implementado
		assertEquals(atributosDoValor, atributosDaExpressao);
	}

	@Test
	public void atributosARegistroTipoTeste() {
		// Par DU (6,8)
		// AnalisadorDeDeclaracoes.outARegistroTipo()
		// AnalisadorDeDeclaracoes.outADeclaracao()

		// Entrada: declare conta registro (num numerico)
		String identificadorDaVariavel = "CONTA", identificadorDoCampo = "NUM";
		int linha = 2, colunaDaVariavel = 9, colunaDoCampo = 25;
		ASimplesVariavel variavel = new ASimplesVariavel(
				new TIdentificador(identificadorDaVariavel, linha, colunaDaVariavel));

		ASimplesVariavel campo = new ASimplesVariavel(new TIdentificador(identificadorDoCampo, linha, colunaDoCampo));

		List<PVariavel> listaDeCampos = new ArrayList<PVariavel>();
		listaDeCampos.add(campo);

		PDeclaracao declaracaoNumerico = new ADeclaracao(listaDeCampos, new ANumericoTipo());

		List<PDeclaracao> listaDeDeclaracoes = new ArrayList<PDeclaracao>();
		listaDeDeclaracoes.add(declaracaoNumerico);

		ARegistroTipo tipoRegistro = new ARegistroTipo(listaDeDeclaracoes);

		List<PVariavel> listaDeVariaveis = new ArrayList<PVariavel>();
		listaDeVariaveis.add(variavel);

		ADeclaracao declaracao = new ADeclaracao(listaDeVariaveis, tipoRegistro);

		TabelaDeAtributos entradaAtributosDaVariavel = new TabelaDeAtributos();
		entradaAtributosDaVariavel.inserir(Atributo.ID, identificadorDaVariavel);
		Simbolo simboloDaVariavel = Simbolo.obter(identificadorDaVariavel);
		entradaAtributosDaVariavel.inserir(Atributo.SIMBOLO, simboloDaVariavel);
		entradaAtributosDaVariavel.inserir(Atributo.LINHA, linha);
		entradaAtributosDaVariavel.inserir(Atributo.COLUNA, colunaDaVariavel);
		entradaAtributosDaVariavel.inserir(Atributo.STRING, identificadorDaVariavel);
		analisadorSemantico.gravarAtributos(variavel, entradaAtributosDaVariavel);

		TabelaDeAtributos atributosDoCampo = new TabelaDeAtributos();
		Simbolo simboloDoCampo = Simbolo.obter(identificadorDoCampo);
		atributosDoCampo.inserir(Atributo.SIMBOLO, simboloDoCampo);
		analisadorSemantico.gravarAtributos(campo, atributosDoCampo);

		// Execução
		analisadorDeDeclaracoes.outARegistroTipo(tipoRegistro);
		analisadorDeDeclaracoes.outADeclaracao(declaracao);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());
		TabelaDeAtributos atributosDaDeclaracao = analisadorSemantico.obterAtributos(declaracao);
		assertNotNull(atributosDaDeclaracao);

		AnalisadorDeDeclaracoesTeste.verificarAtributos(atributosDaDeclaracao,
				AnalisadorDeDeclaracoesTeste.atributosEsperadosDaDeclaracao);

		TabelaDeAtributos atributosDoTipoRegistro = analisadorSemantico.obterAtributos(tipoRegistro);
		assertNotNull(atributosDoTipoRegistro);

		Tipo tipoDoRegistro = (Tipo) atributosDoTipoRegistro.obter(Atributo.TIPO);
		assertNotNull(tipoDoRegistro);

		TabelaDeAtributos saidaAtributosDaVariavel = analisadorSemantico.obterAtributos(variavel);
		assertNotNull(saidaAtributosDaVariavel);

		Tipo tipoDaVariavel = (Tipo) saidaAtributosDaVariavel.obter(Atributo.TIPO);
		assertNotNull(tipoDaVariavel);
		// TODO Defeito - TabelaDeSimbolos.equals() não está implementado
		assertEquals(tipoDoRegistro, tipoDaVariavel);

		assertNotNull(analisadorSemantico.getTabelaDeSimbolos().obter(simboloDaVariavel));

		Tipo tipoDaDeclaracao = (Tipo) atributosDaDeclaracao.obter(Atributo.TIPO);
		assertNotNull(tipoDaDeclaracao);
		// TODO Defeito - TabelaDeSimbolos.equals() não está implementado
		assertEquals(tipoDoRegistro, tipoDaDeclaracao);

		String stringDaDeclaracao = (String) atributosDaDeclaracao.obter(Atributo.STRING);
		// TODO Defeito - TipoRegistro.toString() não está implementado
		assertEquals("[...] REGISTRO([...])", stringDaDeclaracao);
	}

	@Test
	public void atributosASimplesVariavelTeste1() {
		// Par DU (1,6)
		// AnalisadorDeDeclaracoes.outASimplesVariavel()
		// AnalisadorDeDeclaracoes.outARegistroTipo()

		// Entrada: registro (num numerico)
		String identificadorDoCampoNum = "NUM";
		int linha = 3, colunaDoCampoNum = 17;
		ASimplesVariavel campo = new ASimplesVariavel(
				new TIdentificador(identificadorDoCampoNum, linha, colunaDoCampoNum));

		List<PVariavel> listaDeCampos = new ArrayList<PVariavel>();
		listaDeCampos.add(campo);

		PDeclaracao declaracaoNumerico = new ADeclaracao(listaDeCampos, new ANumericoTipo());

		List<PDeclaracao> listaDeDeclaracoes = new ArrayList<PDeclaracao>();
		listaDeDeclaracoes.add(declaracaoNumerico);

		ARegistroTipo tipoRegistro = new ARegistroTipo(listaDeDeclaracoes);

		// Execução
		analisadorDeDeclaracoes.outASimplesVariavel(campo);
		analisadorDeDeclaracoes.outARegistroTipo(tipoRegistro);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDoTipo = analisadorSemantico.obterAtributos(tipoRegistro);
		assertNotNull(atributosDoTipo);

		AnalisadorDeDeclaracoesTeste.verificarAtributos(atributosDoTipo,
				AnalisadorDeDeclaracoesTeste.atributosEsperadosDoTipoRegistro);

		TabelaDeAtributos atributosDoCampoNum = analisadorSemantico.obterAtributos(campo);
		assertNotNull(atributosDoTipo);

		Simbolo simboloDoCampoNum = (Simbolo) atributosDoCampoNum.obter(Atributo.SIMBOLO);
		assertNotNull(simboloDoCampoNum);

		TipoRegistro esperadoTipo = new TipoRegistro();
		esperadoTipo.getCampos().inserir(simboloDoCampoNum, atributosDoCampoNum);

		Tipo saidaTipo = (Tipo) atributosDoTipo.obter(Atributo.TIPO);
		assertNotNull(saidaTipo);
		// TODO Defeito - TabelaDeSimbolos.equals() não está implementado
		assertEquals(esperadoTipo, saidaTipo);

		String stringDoTipoRegistro = (String) atributosDoTipo.obter(Atributo.STRING);
		assertNotNull(stringDoTipoRegistro);
		// TODO Defeito - TipoRegistro.toString() não está implementado
		assertEquals("REGISTRO([...])", stringDoTipoRegistro);
	}

	@Test
	public void atributosASimplesVariavelTeste2() {
		// TODO Testar Par DU (1,7)
		// AnalisadorDeDeclaracoes.outASimplesVariavel()
		// AnalisadorDeDeclaracoes.caseASubRotina()
	}

	@Test
	public void atributosASimplesVariavelTeste3() {
		// TODO Testar Par DU (1,8)
		// AnalisadorDeDeclaracoes.outASimplesVariavel()
		// AnalisadorDeDeclaracoes.outADeclaracao()
	}

	@Test
	public void atributosASimplesVariavelTeste4() {
		// TODO Testar Par DU (18,13)
		// AnalisadorDeExpressoes.outASimplesVariavel()
		// AnalisadorDeComandos.caseARepeticaoParaComando()
	}

	@Test
	public void atributosASimplesVariavelTeste5() {
		// TODO Testar Par DU (18,20)
		// AnalisadorDeExpressoes.outASimplesVariavel()
		// AnalisadorDeExpressoes.outAVariavelPosicaoDeMemoria()
	}

	@Test
	public void atributosASimplesVariavelTeste6() {
		// TODO Testar Par DU (18,21)
		// AnalisadorDeExpressoes.outASimplesVariavel()
		// AnalisadorDeComandos.outACampoPosicaoDeMemoria()
	}

	@Test
	public void atributosASubRotinaTeste() {
		// TODO Testar Par DU (7,46)
		// AnalisadorDeDeclaracoes.caseASubRotina()
		// AnalisadorSemantico.caseASubRotina()
	}

	@Test
	public void atributosAValorExpressaoTeste() {
		// TODO Testar Par DU (39,13)
		// AnalisadorDeExpressoes.outAValorExpressao()
		// AnalisadorDeComandos.caseARepeticaoParaComando()
	}

	@Test
	public void atributosAVariavelPosicaoDeMemoriaTeste1() {
		// Par DU (20,9)
		// AnalisadorDeExpressoes.outAVariavelPosicaoDeMemoria()
		// AnalisadorDeComandos.caseAAtribuicaoComando()

		// Entrada: x <- 2
		String identificadorDaVariavel = "X";
		int linha = 3, colunaDaVariavel = 1;
		PVariavel variavel = new ASimplesVariavel(new TIdentificador(identificadorDaVariavel, linha, colunaDaVariavel));

		AVariavelPosicaoDeMemoria posicaoDeMemoria = new AVariavelPosicaoDeMemoria(variavel);

		String stringDaExpressao = "2";
		PExpressao expressao = new AValorExpressao(new AInteiroValor(new TNumeroInteiro(stringDaExpressao)));

		AAtribuicaoComando comando = new AAtribuicaoComando(posicaoDeMemoria, expressao);

		TabelaDeAtributos atributosDaDeclaracaoDaVariavel = new TabelaDeAtributos();
		Simbolo simboloDaVariavel = Simbolo.obter(identificadorDaVariavel);
		atributosDaDeclaracaoDaVariavel.inserir(Atributo.SIMBOLO, simboloDaVariavel);
		atributosDaDeclaracaoDaVariavel.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));
		analisadorSemantico.getTabelaDeSimbolos().inserir(simboloDaVariavel, atributosDaDeclaracaoDaVariavel);

		TabelaDeAtributos atributosDaVariavel = new TabelaDeAtributos();
		atributosDaVariavel.inserir(Atributo.LINHA, linha);
		atributosDaVariavel.inserir(Atributo.COLUNA, colunaDaVariavel);
		atributosDaVariavel.inserir(Atributo.STRING, identificadorDaVariavel);
		atributosDaVariavel.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));
		analisadorSemantico.gravarAtributos(variavel, atributosDaVariavel);

		TabelaDeAtributos atributosDaExpressao = new TabelaDeAtributos();
		atributosDaExpressao.inserir(Atributo.STRING, stringDaExpressao);
		atributosDaExpressao.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));
		analisadorSemantico.gravarAtributos(expressao, atributosDaExpressao);

		// Execução
		analisadorDeExpressoes.outAVariavelPosicaoDeMemoria(posicaoDeMemoria);
		analisadorDeComandos.caseAAtribuicaoComando(comando);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDoComando = analisadorSemantico.obterAtributos(comando);
		assertNotNull(atributosDoComando);

		assertEquals(linha, atributosDoComando.obter(Atributo.LINHA));
		assertEquals(colunaDaVariavel, atributosDoComando.obter(Atributo.COLUNA));
		String esperadaString = identificadorDaVariavel + " <- " + stringDaExpressao;
		assertEquals(esperadaString, atributosDoComando.obter(Atributo.STRING));
	}

	@Test
	public void atributosAVariavelPosicaoDeMemoriaTeste2() {
		// TODO Testar Par DU (20,10)
		// AnalisadorDeExpressoes.outAVariavelPosicaoDeMemoria()
		// AnalisadorDeComandos.caseAEntradaComando()
	}

	@Test
	public void atributosAVariavelPosicaoDeMemoriaTeste3() {
		// TODO Testar Par DU (20,37)
		// AnalisadorDeExpressoes.outAVariavelPosicaoDeMemoria()
		// AnalisadorDeComandos.outAPosicaoDeMemoriaExpressao()
	}

	@Test
	public void atributosAVetorOuMatrizVariavelTeste1() {
		// TODO Testar Par DU (2,6)
		// AnalisadorDeDeclaracoes.outAVetorOuMatrizVariavel()
		// AnalisadorDeDeclaracoes.outARegistroTipo()
	}

	@Test
	public void atributosAVetorOuMatrizVariavelTeste2() {
		// TODO Testar Par DU (2,7)
		// AnalisadorDeDeclaracoes.outAVetorOuMatrizVariavel()
		// AnalisadorDeDeclaracoes.caseASubRotina()
	}

	@Test
	public void atributosAVetorOuMatrizVariavelTeste3() {
		// TODO Testar Par DU (2,8)
		// AnalisadorDeDeclaracoes.outAVetorOuMatrizVariavel()
		// AnalisadorDeDeclaracoes.outADeclaracao()
	}

	@Test
	public void atributosAVetorOuMatrizVariavelTeste4() {
		// TODO Testar Par DU (19,13)
		// AnalisadorDeExpressoes.outAVetorOuMatrizVariavel()
		// AnalisadorDeComandos.caseARepeticaoParaComando()
	}

	@Test
	public void atributosAVetorOuMatrizVariavelTeste5() {
		// TODO Testar Par DU (19,20)
		// AnalisadorDeExpressoes.outAVetorOuMatrizVariavel()
		// AnalisadorDeExpressoes.outAVariavelPosicaoDeMemoria()
	}

	@Test
	public void atributosAVetorOuMatrizVariavelTeste6() {
		// TODO Testar Par DU (19,21)
		// AnalisadorDeExpressoes.outAVetorOuMatrizVariavel()
		// AnalisadorDeExpressoes.outACampoPosicaoDeMemoria()
	}

	@Test
	public void simboloASimplesVariavelTeste1() {
		// Par DU (8,18)
		// AnalisadorDeDeclaracoes.outADeclaracao()
		// AnalisadorDeExpressoes.outASimplesVariavel()

		// Entrada: declare x numerico
		String identificadorDaVariavel = "X";
		int linhaDaDeclaracao = 2, colunaDaVariavelNaDeclaracao = 9;
		ASimplesVariavel variavelNaDeclaracao = new ASimplesVariavel(
				new TIdentificador(identificadorDaVariavel, linhaDaDeclaracao, colunaDaVariavelNaDeclaracao));

		List<PVariavel> listaDeVariaveis = new ArrayList<PVariavel>();
		listaDeVariaveis.add(variavelNaDeclaracao);

		PTipo tipo = new ANumericoTipo();

		ADeclaracao declaracao = new ADeclaracao(listaDeVariaveis, tipo);

		TabelaDeAtributos atributosDaDeclaracaoDaVariavel = new TabelaDeAtributos();
		atributosDaDeclaracaoDaVariavel.inserir(Atributo.ID, identificadorDaVariavel);
		Simbolo simboloDaVariavel = Simbolo.obter(identificadorDaVariavel);
		atributosDaDeclaracaoDaVariavel.inserir(Atributo.SIMBOLO, simboloDaVariavel);
		atributosDaDeclaracaoDaVariavel.inserir(Atributo.LINHA, linhaDaDeclaracao);
		atributosDaDeclaracaoDaVariavel.inserir(Atributo.COLUNA, colunaDaVariavelNaDeclaracao);
		atributosDaDeclaracaoDaVariavel.inserir(Atributo.STRING, identificadorDaVariavel);
		analisadorSemantico.gravarAtributos(variavelNaDeclaracao, atributosDaDeclaracaoDaVariavel);

		Tipo tipoNumerico = new Tipo(TipoPrimitivo.NUMERICO);
		TabelaDeAtributos atributosDoTipo = new TabelaDeAtributos();
		atributosDoTipo.inserir(Atributo.TIPO, tipoNumerico);
		atributosDoTipo.inserir(Atributo.STRING, tipoNumerico.toString());
		analisadorSemantico.gravarAtributos(tipo, atributosDoTipo);

		// Entrada: x
		int linhaDaExpressao = 3, colunaDaVariavelNaExpressao = 1;
		ASimplesVariavel variavelNaExpressao = new ASimplesVariavel(
				new TIdentificador(identificadorDaVariavel, linhaDaExpressao, colunaDaVariavelNaExpressao));

		// Execução
		analisadorDeDeclaracoes.outADeclaracao(declaracao);
		analisadorDeExpressoes.outASimplesVariavel(variavelNaExpressao);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDaVariavel = analisadorSemantico.obterAtributos(variavelNaExpressao);
		assertNotNull(atributosDaVariavel);

		assertEquals(identificadorDaVariavel, atributosDaVariavel.obter(Atributo.ID));
		assertEquals(simboloDaVariavel, atributosDaVariavel.obter(Atributo.SIMBOLO));
		assertEquals(linhaDaExpressao, atributosDaVariavel.obter(Atributo.LINHA));
		assertEquals(colunaDaVariavelNaExpressao, atributosDaVariavel.obter(Atributo.COLUNA));
		assertEquals(identificadorDaVariavel, atributosDaVariavel.obter(Atributo.STRING));
		assertEquals(tipoNumerico, atributosDaVariavel.obter(Atributo.TIPO));
	}

	@Test
	public void simboloASimplesVariavelTeste2() {
		// TODO Testar Par DU (8,21)
		// AnalisadorDeDeclaracoes.outADeclaracao()
		// AnalisadorDeExpressoes.outACampoPosicaoDeMemoria()
	}

	@Test
	public void simboloASubRotinaTeste() {
		// Par DU (7,40)
		// AnalisadorDeDeclaracoes.caseASubRotina()
		// AnalisadorDeExpressoes.outAChamadaASubRotina()

		// Entrada:
		// sub-rotina par(x numerico)
		// fim_sub_rotina par
		String identificadorDaSubRotina = "PAR", identificadorDoParametro = "X";
		int linhaDaDeclaracao = 14, colunaDaDeclaracaoDaSubRotina = 12, colunaDaDeclaracaoDoParametro = 14;
		PVariavel parametro = new ASimplesVariavel(
				new TIdentificador(identificadorDoParametro, linhaDaDeclaracao, colunaDaDeclaracaoDoParametro));

		List<PVariavel> listaDeParametros = new ArrayList<PVariavel>();
		listaDeParametros.add(parametro);
		PTipo tipoDoParametro = new ANumericoTipo();
		PDeclaracao declaracaoDoParametro = new ADeclaracao(listaDeParametros, tipoDoParametro);

		List<PDeclaracao> listaDeDeclaracoesDeParametros = new ArrayList<PDeclaracao>();
		listaDeDeclaracoesDeParametros.add(declaracaoDoParametro);
		ASubRotina subrotina = new ASubRotina(
				new TIdentificador(identificadorDaSubRotina, linhaDaDeclaracao, colunaDaDeclaracaoDaSubRotina),
				listaDeDeclaracoesDeParametros, new ArrayList<PDeclaracao>(), new ArrayList<PComando>(),
				new TIdentificador(identificadorDaSubRotina, linhaDaDeclaracao + 1, colunaDaDeclaracaoDaSubRotina + 4));

		// Entrada: par(5)
		int linhaDaChamada = 3, colunaDaChamada = 1;
		String stringDoInteiro = "5";
		PExpressao argumento = new AValorExpressao(
				new AInteiroValor(new TNumeroInteiro(stringDoInteiro, linhaDaChamada, colunaDaChamada + 4)));

		List<PExpressao> listaDeArgumentos = new ArrayList<PExpressao>();
		listaDeArgumentos.add(argumento);
		AChamadaASubRotina chamada = new AChamadaASubRotina(
				new TIdentificador(identificadorDaSubRotina, linhaDaChamada, colunaDaChamada), listaDeArgumentos);

		TabelaDeAtributos atributosDoParametro = new TabelaDeAtributos();
		Simbolo simboloDoParametro = Simbolo.obter(identificadorDoParametro);
		atributosDoParametro.inserir(Atributo.SIMBOLO, simboloDoParametro);
		atributosDoParametro.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));
		analisadorSemantico.gravarAtributos(parametro, atributosDoParametro);

		TabelaDeAtributos atributosDoArgumento = new TabelaDeAtributos();
		atributosDoArgumento.inserir(Atributo.TIPO, new Tipo(TipoPrimitivo.NUMERICO));
		atributosDoArgumento.inserir(Atributo.STRING, stringDoInteiro);
		analisadorSemantico.gravarAtributos(argumento, atributosDoArgumento);

		// Execução
		analisadorDeDeclaracoes.caseASubRotina(subrotina);
		analisadorDeExpressoes.outAChamadaASubRotina(chamada);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDaChamada = analisadorSemantico.obterAtributos(chamada);
		assertNotNull(atributosDaChamada);

		assertEquals(identificadorDaSubRotina, atributosDaChamada.obter(Atributo.ID));
		Simbolo esperadoSimbolo = Simbolo.obter(identificadorDaSubRotina);
		assertEquals(esperadoSimbolo, atributosDaChamada.obter(Atributo.SIMBOLO));
		assertEquals(linhaDaChamada, atributosDaChamada.obter(Atributo.LINHA));
		assertEquals(colunaDaChamada, atributosDaChamada.obter(Atributo.COLUNA));
		String esperadaString = identificadorDaSubRotina + "(" + stringDoInteiro + ")";
		assertEquals(esperadaString, atributosDaChamada.obter(Atributo.STRING));
		Tipo tipoEsperado = new Tipo(TipoPrimitivo.DETERMINADO_EM_TEMPO_DE_EXECUCAO);
		assertEquals(tipoEsperado, atributosDaChamada.obter(Atributo.TIPO));
	}

	@Test
	public void simboloAVetorOuMatrizVariavelTeste1() {
		// Par DU (8,19)
		// AnalisadorDeDeclaracoes.outADeclaracao()
		// AnalisadorDeExpressoes.outAVetorOuMatrizVariavel()

		// Entrada: declare num[9] numerico
		String identificadorDaVariavel = "NUM";
		int linhaDaDeclaracao = 2, colunaDaVariavelNaDeclaracao = 9;
		PExpressao capacidade = new AValorExpressao(
				new AInteiroValor(new TNumeroInteiro("9", linhaDaDeclaracao, colunaDaVariavelNaDeclaracao + 4)));

		List<PExpressao> dimensoes = new ArrayList<PExpressao>();
		dimensoes.add(capacidade);
		AVetorOuMatrizVariavel variavelNaDeclaracao = new AVetorOuMatrizVariavel(
				new TIdentificador(identificadorDaVariavel, linhaDaDeclaracao, colunaDaVariavelNaDeclaracao),
				dimensoes);

		List<PVariavel> listaDeVariaveis = new ArrayList<PVariavel>();
		listaDeVariaveis.add(variavelNaDeclaracao);

		PTipo tipo = new ANumericoTipo();

		ADeclaracao declaracao = new ADeclaracao(listaDeVariaveis, tipo);

		// Entrada: num[i]
		int linhaDaExpressao = 9, colunaDaExpressao = 7;
		String stringDaPosicao = "I";
		PExpressao posicao = new APosicaoDeMemoriaExpressao(new AVariavelPosicaoDeMemoria(
				new ASimplesVariavel(new TIdentificador(stringDaPosicao, linhaDaExpressao, colunaDaExpressao + 4))));

		List<PExpressao> posicoes = new ArrayList<PExpressao>();
		posicoes.add(posicao);
		AVetorOuMatrizVariavel variavelNaExpressao = new AVetorOuMatrizVariavel(
				new TIdentificador(identificadorDaVariavel, linhaDaExpressao, colunaDaExpressao), posicoes);

		TabelaDeAtributos atributosDaDeclaracaoDaVariavel = new TabelaDeAtributos();
		atributosDaDeclaracaoDaVariavel.inserir(Atributo.ID, identificadorDaVariavel);
		Simbolo simboloDaVariavel = Simbolo.obter(identificadorDaVariavel);
		atributosDaDeclaracaoDaVariavel.inserir(Atributo.SIMBOLO, simboloDaVariavel);
		atributosDaDeclaracaoDaVariavel.inserir(Atributo.LINHA, linhaDaDeclaracao);
		atributosDaDeclaracaoDaVariavel.inserir(Atributo.COLUNA, colunaDaVariavelNaDeclaracao);
		analisadorSemantico.gravarAtributos(variavelNaDeclaracao, atributosDaDeclaracaoDaVariavel);

		Tipo tipoNumerico = new Tipo(TipoPrimitivo.NUMERICO);
		TabelaDeAtributos atributosDoTipo = new TabelaDeAtributos();
		atributosDoTipo.inserir(Atributo.TIPO, tipoNumerico);
		atributosDoTipo.inserir(Atributo.STRING, tipoNumerico.toString());
		analisadorSemantico.gravarAtributos(tipo, atributosDoTipo);

		TabelaDeAtributos atributosDaPosicao = new TabelaDeAtributos();
		atributosDaPosicao.inserir(Atributo.TIPO, tipoNumerico);
		atributosDaPosicao.inserir(Atributo.STRING, stringDaPosicao);
		analisadorSemantico.gravarAtributos(posicao, atributosDaPosicao);

		// Execução
		analisadorDeDeclaracoes.outADeclaracao(declaracao);
		analisadorDeExpressoes.outAVetorOuMatrizVariavel(variavelNaExpressao);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDaVariavel = analisadorSemantico.obterAtributos(variavelNaExpressao);
		assertNotNull(atributosDaVariavel);

		assertEquals(identificadorDaVariavel, atributosDaVariavel.obter(Atributo.ID));
		assertEquals(simboloDaVariavel, atributosDaVariavel.obter(Atributo.SIMBOLO));
		assertEquals(linhaDaExpressao, atributosDaVariavel.obter(Atributo.LINHA));
		assertEquals(colunaDaExpressao, atributosDaVariavel.obter(Atributo.COLUNA));
		assertEquals(identificadorDaVariavel + "[" + stringDaPosicao + "]", atributosDaVariavel.obter(Atributo.STRING));
		assertEquals(tipoNumerico, atributosDaVariavel.obter(Atributo.TIPO));
	}

	@Test
	public void simboloAVetorOuMatrizVariavelTeste2() {
		// TODO Testar Par DU (8,21)
		// AnalisadorDeDeclaracoes.outADeclaracao()
		// AnalisadorDeExpressoes.outACampoPosicaoDeMemoria()
	}

	@Test
	public void tabelaDeSimbolosExisteTeste1() {
		// TODO Testar Par DU (7,8)
		// AnalisadorDeDeclaracoes.caseASubRotina()
		// AnalisadorDeDeclaracoes.outADeclaracao()
	}

	@Test
	public void tabelaDeSimbolosExisteTeste2() {
		// TODO Testar Par DU (7,18)
		// AnalisadorDeDeclaracoes.caseASubRotina()
		// AnalisadorDeExpressoes.outASimplesVariavel()
	}

	@Test
	public void tabelaDeSimbolosExisteTeste3() {
		// TODO Testar Par DU (7,19)
		// AnalisadorDeDeclaracoes.caseASubRotina()
		// AnalisadorDeExpressoes.outAVetorOuMatrizVariavel()
	}

	@Test
	public void tabelaDeSimbolosExisteTeste4() {
		// TODO Testar Par DU (7,40)
		// AnalisadorDeDeclaracoes.caseASubRotina()
		// AnalisadorDeExpressoes.outAChamadaASubRotina()
	}

	@Test
	public void tabelaDeSimbolosExisteTeste5() {
		// TODO Testar Par DU (8,7)
		// AnalisadorDeDeclaracoes.outADeclaracao()
		// AnalisadorSemantico.caseASubRotina()
	}

	@Test
	public void tabelaDeSimbolosExisteTeste6() {
		// TODO Testar Par DU (8,18)
		// AnalisadorDeDeclaracoes.outADeclaracao()
		// AnalisadorSemantico.outASimplesVariavel()
	}

	@Test
	public void tabelaDeSimbolosExisteTeste7() {
		// TODO Testar Par DU (8,19)
		// AnalisadorDeDeclaracoes.outADeclaracao()
		// AnalisadorSemantico.outAVetorOuMatrizVariavel()
	}

	@Test
	public void tabelaDeSimbolosExisteTeste8() {
		// TODO Testar Par DU (8,40)
		// AnalisadorDeDeclaracoes.outADeclaracao()
		// AnalisadorSemantico.outAChamadaASubRotina()
	}

	@Test
	public void tabelaDeSimbolosTeste1() {
		// TODO Testar Par DU (45,7)
		// AnalisadorSemantico.caseAAlgoritmo()
		// AnalisadorDeDeclaracoes.caseASubRotina()
	}

	@Test
	public void tabelaDeSimbolosTeste2() {
		// TODO Testar Par DU (45,46)
		// AnalisadorSemantico.caseAAlgoritmo()
		// AnalisadorSemantico.caseASubRotina()
	}

	@Test
	public void testeEmCascata1() {
		// Pares DU (35, 25) e (25, 12)
		// AnalisadorDeExpressoes.outANegativoExpressao()
		// AnalisadorDeExpressoes.outADiferencaExpressao()
		// AnalisadorDeComandos.caseACondicionalComando()

		// Entrada:
		// se -x <> -1 entao
		String identificadorDaVariavelX = "X";
		int linha = 5, colunaDaVariavelX = 5;
		PExpressao variavelX = new APosicaoDeMemoriaExpressao(new AVariavelPosicaoDeMemoria(
				new ASimplesVariavel(new TIdentificador(identificadorDaVariavelX, linha, colunaDaVariavelX))));

		ANegativoExpressao expressaoNegativo = new ANegativoExpressao(variavelX);

		String stringDoValorInteiro = "-1";
		PExpressao valorInteiro = new AValorExpressao(
				new AInteiroValor(new TNumeroInteiro(stringDoValorInteiro, linha, colunaDaVariavelX + 5)));

		ADiferencaExpressao expressaoDiferenca = new ADiferencaExpressao(expressaoNegativo, valorInteiro);

		ACondicionalComando comandoCondicional = new ACondicionalComando(expressaoDiferenca, null, null);

		TabelaDeAtributos atributosDaVariavelX = new TabelaDeAtributos();
		atributosDaVariavelX.inserir(Atributo.ID, identificadorDaVariavelX);
		atributosDaVariavelX.inserir(Atributo.SIMBOLO, Simbolo.obter(identificadorDaVariavelX));
		atributosDaVariavelX.inserir(Atributo.LINHA, linha);
		atributosDaVariavelX.inserir(Atributo.COLUNA, colunaDaVariavelX);
		atributosDaVariavelX.inserir(Atributo.STRING, identificadorDaVariavelX);
		Tipo tipoNumerico = new Tipo(TipoPrimitivo.NUMERICO);
		atributosDaVariavelX.inserir(Atributo.TIPO, tipoNumerico);
		analisadorSemantico.gravarAtributos(variavelX, atributosDaVariavelX);

		TabelaDeAtributos atributosDoValorInteiro = new TabelaDeAtributos();
		atributosDoValorInteiro.inserir(Atributo.TIPO, tipoNumerico);
		atributosDoValorInteiro.inserir(Atributo.STRING, stringDoValorInteiro);
		analisadorSemantico.gravarAtributos(valorInteiro, atributosDoValorInteiro);

		// Execução
		analisadorDeExpressoes.outANegativoExpressao(expressaoNegativo);
		analisadorDeExpressoes.outADiferencaExpressao(expressaoDiferenca);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDaExpressao = analisadorSemantico.obterAtributos(expressaoDiferenca);
		assertNotNull(atributosDaExpressao);

		assertEquals(linha, atributosDaExpressao.obter(Atributo.LINHA));
		assertEquals(colunaDaVariavelX, atributosDaExpressao.obter(Atributo.COLUNA));
		Tipo esperadoTipoDaExpressao = new Tipo(TipoPrimitivo.LOGICO);
		assertEquals(esperadoTipoDaExpressao, atributosDaExpressao.obter(Atributo.TIPO));
		// TODO Defeito - o parêntese da string da expressão deveria estar por fora, a
		// semelhança da string de outras expressões: (-X)
		String esperadaStringDaExpressao = "(-(" + identificadorDaVariavelX + ") <> " + stringDoValorInteiro + ")";
		assertEquals(esperadaStringDaExpressao, atributosDaExpressao.obter(Atributo.STRING));

		// Execução
		analisadorDeComandos.caseACondicionalComando(comandoCondicional);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDoComando = analisadorSemantico.obterAtributos(comandoCondicional);
		assertNotNull(atributosDoComando);

		String esperadaStringDoComando = "SE " + esperadaStringDaExpressao + " ENTAO [...]";
		assertEquals(esperadaStringDoComando, atributosDoComando.obter(Atributo.STRING));
	}

	@Test
	public void testeEmCascata2() {
		// Testar Pares DU (34, 24) e (24, 12)
		// AnalisadorDeExpressoes.outAPositivoExpressao()
		// AnalisadorDeExpressoes.outAIgualdadeExpressao()
		// AnalisadorDeComandos.caseACondicionalComando()

		// Entrada:
		// se +y = +1 entao
		String identificadorDaVariavelY = "Y";
		int linha = 5, colunaDaVariavelY = 5;
		PExpressao variavelY = new APosicaoDeMemoriaExpressao(new AVariavelPosicaoDeMemoria(
				new ASimplesVariavel(new TIdentificador(identificadorDaVariavelY, linha, colunaDaVariavelY))));

		APositivoExpressao expressaoPositivo = new APositivoExpressao(variavelY);

		String stringDoValorInteiro = "+1";
		PExpressao valorInteiro = new AValorExpressao(
				new AInteiroValor(new TNumeroInteiro(stringDoValorInteiro, linha, colunaDaVariavelY + 4)));

		AIgualdadeExpressao expressaoIgualdade = new AIgualdadeExpressao(expressaoPositivo, valorInteiro);

		ACondicionalComando comandoCondicional = new ACondicionalComando(expressaoIgualdade, null, null);

		TabelaDeAtributos atributosDaVariavelY = new TabelaDeAtributos();
		atributosDaVariavelY.inserir(Atributo.ID, identificadorDaVariavelY);
		atributosDaVariavelY.inserir(Atributo.SIMBOLO, Simbolo.obter(identificadorDaVariavelY));
		atributosDaVariavelY.inserir(Atributo.LINHA, linha);
		atributosDaVariavelY.inserir(Atributo.COLUNA, colunaDaVariavelY);
		atributosDaVariavelY.inserir(Atributo.STRING, identificadorDaVariavelY);
		Tipo tipoNumerico = new Tipo(TipoPrimitivo.NUMERICO);
		atributosDaVariavelY.inserir(Atributo.TIPO, tipoNumerico);
		analisadorSemantico.gravarAtributos(variavelY, atributosDaVariavelY);

		TabelaDeAtributos atributosDoValorInteiro = new TabelaDeAtributos();
		atributosDoValorInteiro.inserir(Atributo.TIPO, tipoNumerico);
		atributosDoValorInteiro.inserir(Atributo.STRING, stringDoValorInteiro);
		analisadorSemantico.gravarAtributos(valorInteiro, atributosDoValorInteiro);

		// Execução
		analisadorDeExpressoes.outAPositivoExpressao(expressaoPositivo);
		analisadorDeExpressoes.outAIgualdadeExpressao(expressaoIgualdade);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDaExpressao = analisadorSemantico.obterAtributos(expressaoIgualdade);
		assertNotNull(atributosDaExpressao);

		assertEquals(linha, atributosDaExpressao.obter(Atributo.LINHA));
		assertEquals(colunaDaVariavelY, atributosDaExpressao.obter(Atributo.COLUNA));
		Tipo esperadoTipoDaExpressao = new Tipo(TipoPrimitivo.LOGICO);
		assertEquals(esperadoTipoDaExpressao, atributosDaExpressao.obter(Atributo.TIPO));
		// TODO Defeito - o parêntese da string da expressão deveria estar por fora, a
		// semelhança da string de outras expressões: (+Y)
		String esperadaStringDaExpressao = "(+(" + identificadorDaVariavelY + ") = " + stringDoValorInteiro + ")";
		assertEquals(esperadaStringDaExpressao, atributosDaExpressao.obter(Atributo.STRING));

		// Execução
		analisadorDeComandos.caseACondicionalComando(comandoCondicional);

		// Saída
		assertFalse(analisadorSemantico.haErroSemantico());

		TabelaDeAtributos atributosDoComando = analisadorSemantico.obterAtributos(comandoCondicional);
		assertNotNull(atributosDoComando);

		String esperadaStringDoComando = "SE " + esperadaStringDaExpressao + " ENTAO [...]";
		assertEquals(esperadaStringDoComando, atributosDoComando.obter(Atributo.STRING));
	}

	@Test
	public void testeEmCascata3() {
		// TODO Testar Pares DU (37, 27), (38, 27) e (27, 15)
		// AnalisadorDeExpressoes.outAPosicaoDeMemoriaExpressao()
		// AnalisadorDeExpressoes.outAChamadaASubRotinaExpressao()
		// AnalisadorDeExpressoes.outAMenorOuIgualExpressao()
		// AnalisadorDeComandos.caseARepeticaoRepitaComando()
	}

	@Test
	public void testeEmCascata4() {
		// TODO Testar Pares DU (32, 30), (30, 29), (29, 23) e (23, 14)
		// AnalisadorDeExpressoes.outAMultiplicacaoExpressao()
		// AnalisadorDeExpressoes.outASomaExpressao()
		// AnalisadorDeExpressoes.outAMaiorOuIgualExpressao()
		// AnalisadorDeExpressoes.outAConjuncaoExpressao()
		// AnalisadorDeComandos.caseARepeticaoEnquantoComando()
	}

	@Test
	public void testeEmCascata5() {
		// TODO Testar Pares DU (33, 31), (31, 26), (26, 22), (28, 22) e (22, 12)
		// AnalisadorDeExpressoes.outADivisaoExpressao()
		// AnalisadorDeExpressoes.outASubtracaoExpressao()
		// AnalisadorDeExpressoes.outAMenorExpressao()
		// AnalisadorDeExpressoes.outADisjuncaoExpressao()
		// AnalisadorDeExpressoes.caseACondicionalComando()
	}

}