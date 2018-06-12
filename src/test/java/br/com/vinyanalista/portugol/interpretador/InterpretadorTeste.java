package br.com.vinyanalista.portugol.interpretador;

import static org.junit.Assert.*;

import java.awt.Color;

import java.util.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class InterpretadorTeste {
	////////////////////////
	// Classes auxiliares //
	////////////////////////

	private static class CasoDeTeste {
		private String codigoFonte, entrada, saidaEsperada;

		public CasoDeTeste(String codigoFonte, String entrada, String saidaEsperada) {
			this.codigoFonte = codigoFonte;
			this.entrada = entrada;
			this.saidaEsperada = saidaEsperada;
		}

		public String getCodigoFonte() {
			return codigoFonte;
		}

		public String getEntrada() {
			return entrada;
		}

		public String getSaidaEsperada() {
			return saidaEsperada;
		}

		@Override
		public String toString() {
			return new StringBuilder("Código-fonte:\n\n").append(codigoFonte).append("\n\nEntrada:\n\n").append(entrada)
					.append("\nSaída esperada:\n\n").append(saidaEsperada).toString();
		}
	}

	private class TerminalDeTeste extends Terminal {
		private StringTokenizer entrada;
		private StringBuilder saida;

		public TerminalDeTeste(String entrada) {
			super();
			this.entrada = new StringTokenizer(entrada);
			saida = new StringBuilder();
		}

		@Override
		public synchronized void erro(String mensagemDeErro) {
			saida.append(mensagemDeErro).append("\n");
		}

		@Override
		protected synchronized void escrever(String mensagem) {
			saida.append(mensagem).append("\n");
		}

		@Override
		public synchronized void informacao(String mensagemDeInformacao) {
			// Este método não foi implementado porque não faz sentido no escopo deste teste
		}

		@Override
		protected synchronized String ler() {
			return (entrada.hasMoreTokens() ? entrada.nextToken() : "");
		}

		@Override
		public synchronized void limpar() {
			// Limpa o StringBuilder
			// https://stackoverflow.com/a/5192545/1657502
			saida.setLength(0);
		}

		@Override
		protected void mudarCor(Color cor) {
			// Este método não foi implementado porque não faz sentido no escopo deste teste
		}

		public synchronized String getSaida() {
			return saida.toString();
		}
	}

	////////////////////////
	// Definição do teste //
	////////////////////////

	private CasoDeTeste casoDeTeste;
	private Interpretador interpretador;
	private TerminalDeTeste terminal;

	public InterpretadorTeste(CasoDeTeste casoDeTeste) {
		this.casoDeTeste = casoDeTeste;
	}

	@Before
	public void inicializar() {
		terminal = new TerminalDeTeste(casoDeTeste.getEntrada());
		interpretador = new Interpretador(terminal);
	}

	@Test
	public void teste() {
		String saida = "";
		StringBuilder mensagemCasoFalhe = new StringBuilder(casoDeTeste.toString());
		try {
			interpretador.analisar(casoDeTeste.getCodigoFonte());
			interpretador.executar();
			// TODO Usar Thread.sleep() no JUnit?
			Thread.sleep(2000);
			saida = terminal.getSaida();
			mensagemCasoFalhe.append("\nSaída obtida:\n\n").append(saida).append("\n");
			assertEquals(mensagemCasoFalhe.toString(), casoDeTeste.getSaidaEsperada(), saida);
		} catch (Exception excecao) {
			mensagemCasoFalhe.append("\nSaída obtida:\n\n").append(saida).append("\nExceção:\n\n")
					.append(excecao.getMessage());
			fail(mensagemCasoFalhe.toString());
			excecao.printStackTrace();
		}
	}

	////////////////////
	// Casos de teste //
	////////////////////

	public static final List<CasoDeTeste> casosDeTeste = new ArrayList<CasoDeTeste>();

	public static final StringBuilder codigoFonte = new StringBuilder();
	public static final StringBuilder entrada = new StringBuilder();
	public static final StringBuilder saidaEsperada = new StringBuilder();

	public static void adicionarCasoDeTeste() {
		CasoDeTeste casoDeTeste = new CasoDeTeste(codigoFonte.toString(), entrada.toString(), saidaEsperada.toString());
		casosDeTeste.add(casoDeTeste);
	}
	
	public static void novoCasoDeTeste() {
		// Limpa o StringBuilder para reutilizá-lo
		// https://stackoverflow.com/a/5192545/1657502
		codigoFonte.setLength(0);
		entrada.setLength(0);
		saidaEsperada.setLength(0);
	}

	@Parameters
	public static Iterable<CasoDeTeste> data() {
		// Primeiro exemplo do capítulo 1
		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare n1, n2, m numerico\n");
		codigoFonte.append("escreva \"Digite dois números\"\n");
		codigoFonte.append("leia n1, n2\n");
		codigoFonte.append("m <- n1 * n2\n");
		codigoFonte.append("escreva \"Multiplicação = \", m\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("2\n");
		entrada.append("3\n");

		saidaEsperada.append("Digite dois números\n");
		saidaEsperada.append("Multiplicação = 6\n");

		adicionarCasoDeTeste();
		
		// Primeiro exemplo do capítulo 3
		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare n1, n2, n3, n4, soma numerico\n");
		codigoFonte.append("leia n1, n2, n3, n4\n");
		codigoFonte.append("soma <- n1 + n2 + n3 + n4\n");
		codigoFonte.append("escreva soma\n");
		codigoFonte.append("fim_algoritmo.");
		
		entrada.append("-1\n");
		entrada.append("0\n");
		entrada.append("1\n");
		entrada.append("2\n");

		saidaEsperada.append("2\n");
		
		adicionarCasoDeTeste();

		return casosDeTeste;
	}
}