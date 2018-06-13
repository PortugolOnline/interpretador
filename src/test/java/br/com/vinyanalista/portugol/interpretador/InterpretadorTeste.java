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
		////////////////////////////////////
		// Capítulo 1 - Conceitos básicos //
		////////////////////////////////////

		// Primeiro exemplo

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

		// Segundo exemplo

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare n1, n2, d numerico\n");
		codigoFonte.append("escreva \"Digite dois números\"\n");
		codigoFonte.append("leia n1, n2\n");
		codigoFonte.append("se n2 = 0\n");
		codigoFonte.append("entao escreva \"Impossível dividir\"\n");
		codigoFonte.append("senao\n");
		codigoFonte.append("	inicio\n");
		codigoFonte.append("		d <- n1 / n2\n");
		codigoFonte.append("		escreva \"Divisão = \", d\n");
		codigoFonte.append("	fim\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("4\n");
		entrada.append("0\n");

		saidaEsperada.append("Digite dois números\n");
		saidaEsperada.append("Impossível dividir\n");

		adicionarCasoDeTeste();

		// Terceiro exemplo

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare n1, n2, m numerico\n");
		codigoFonte.append("escreva \"Digite as duas notas\"\n");
		codigoFonte.append("leia n1, n2\n");
		codigoFonte.append("m <- (n1 + n2)/2\n");
		codigoFonte.append("escreva \"Média = \", m\n");
		codigoFonte.append("se m >= 7\n");
		codigoFonte.append("entao escreva \"Aprovado\"\n");
		codigoFonte.append("senao escreva \"Reprovado\"\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("7\n");
		entrada.append("8\n");

		saidaEsperada.append("Digite as duas notas\n");
		saidaEsperada.append("Média = 7.5\n");
		saidaEsperada.append("Aprovado\n");

		adicionarCasoDeTeste();

		// Quarto exemplo

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare sal_atual, novo_sal numerico\n");
		codigoFonte.append("escreva \"Digite o salário atual do funcionário\"\n");
		codigoFonte.append("leia sal_atual\n");
		codigoFonte.append("se sal_atual <= 500\n");
		codigoFonte.append("entao novo_sal <- sal_atual * 1.20\n");
		codigoFonte.append("senao novo_sal <- sal_atual * 1.10\n");
		codigoFonte.append("escreva \"Novo salário = \", novo_sal\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("490\n");

		saidaEsperada.append("Digite o salário atual do funcionário\n");
		// TODO Defeito - 588.0 em vez de 588
		saidaEsperada.append("Novo salário = 588\n");

		adicionarCasoDeTeste();

		///////////////////////////////////////
		// Capítulo 3 - Estrutura sequencial //
		///////////////////////////////////////

		// Exercício resolvido 1

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

		// Exercício resolvido 6

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare sal, salreceber, grat, imp numerico\n");
		codigoFonte.append("leia sal\n");
		codigoFonte.append("grat <- sal * 5/100\n");
		codigoFonte.append("imp <- sal * 7/100\n");
		codigoFonte.append("salreceber <- sal + grat - imp\n");
		codigoFonte.append("escreva salreceber\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("1000\n");

		saidaEsperada.append("980\n");

		adicionarCasoDeTeste();

		// Exercício resolvido 10

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare area, raio numerico\n");
		codigoFonte.append("leia raio\n");
		codigoFonte.append("area <- 3.1415 * potencia(raio, 2)\n");
		codigoFonte.append("escreva area\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("30\n");

		saidaEsperada.append(String.valueOf(3.1415 * Math.pow(30, 2)) + "\n");

		adicionarCasoDeTeste();

		// Exercício resolvido 11

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare num, quad, cubo, r2, r3 numerico\n");
		codigoFonte.append("leia num\n");
		codigoFonte.append("quad <- potencia(num, 2)\n");
		codigoFonte.append("cubo <- potencia(num, 3)\n");
		codigoFonte.append("r2 <- raiz_quadrada(num)\n");
		codigoFonte.append("r3 <- raiz_enesima(3, num)\n");
		codigoFonte.append("escreva quad\n");
		codigoFonte.append("escreva cubo\n");
		codigoFonte.append("escreva r2\n");
		codigoFonte.append("escreva r3\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("64\n");

		saidaEsperada.append("49096\n");
		saidaEsperada.append("262144\n");
		saidaEsperada.append("8\n");
		// TODO Defeito - 3.9999999999999996 em vez de 4
		saidaEsperada.append("4\n");

		adicionarCasoDeTeste();

		// Exercício resolvido 20

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare ang, alt, escada, radiano numerico\n");
		codigoFonte.append("leia ang\n");
		codigoFonte.append("leia alt\n");
		codigoFonte.append("radiano <- ang * 3.14 / 180\n");
		codigoFonte.append("escada <- alt / seno(radiano)\n");
		codigoFonte.append("escreva escada\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("30\n");
		entrada.append("4\n");

		saidaEsperada.append(String.valueOf(4 / Math.sin(30 * 3.14 / 180)) + "\n");

		adicionarCasoDeTeste();

		// Exercício resolvido 23

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare num, i, f, a numerico\n");
		codigoFonte.append("leia num\n");
		codigoFonte.append("i <- parte_inteira(num)\n");
		codigoFonte.append("f <- num - i\n");
		codigoFonte.append("a <- arredonda(num)\n");
		codigoFonte.append("escreva i\n");
		codigoFonte.append("escreva f\n");
		codigoFonte.append("escreva a\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("1.1\n");

		saidaEsperada.append("1\n");
		// TODO Defeito - 0.10000000000000009 em vez de 0.1
		saidaEsperada.append("0.1\n");
		saidaEsperada.append("1\n");

		adicionarCasoDeTeste();

		////////////////////////////////////////
		// Capítulo 4 - Estrutura condicional //
		////////////////////////////////////////

		// Exercício resolvido 1 do capítulo 4

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare nota_trab, aval_sem, exame, media numerico\n");
		codigoFonte.append("escreva \"Digite a nota do trabalho de laboratório: \"\n");
		codigoFonte.append("leia nota_trab\n");
		codigoFonte.append("escreva \"Digite a nota da avaliação semestral: \"\n");
		codigoFonte.append("leia aval_sem\n");
		codigoFonte.append("escreva \"Digite a nota do exame final: \"\n");
		codigoFonte.append("leia exame\n");
		codigoFonte.append("media <- (nota_trab * 2 + aval_sem * 3 + exame * 5) / 10\n");
		codigoFonte.append("escreva \"Média ponderada: \", media\n");
		codigoFonte.append("se media >= 8 e media <= 10\n");
		codigoFonte.append("entao escreva \"Obteve conceito A\"\n");
		codigoFonte.append("se media >= 7 e media < 8\n");
		codigoFonte.append("entao escreva \"Obteve conceito B\"\n");
		codigoFonte.append("se media >= 6 e media < 7\n");
		codigoFonte.append("entao escreva \"Obteve conceito C\"\n");
		codigoFonte.append("se media >= 5 e media < 6\n");
		codigoFonte.append("entao escreva \"Obteve conceito D\"\n");
		codigoFonte.append("se media >= 0 e media < 5\n");
		codigoFonte.append("entao escreva \"Obteve conceito E\"\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("10\n");
		entrada.append("4\n");
		entrada.append("6\n");

		saidaEsperada.append("Digite a nota do trabalho de laboratório: \n");
		saidaEsperada.append("Digite a nota da avaliação semestral: \n");
		saidaEsperada.append("Digite a nota do exame final: \n");
		saidaEsperada.append("Média ponderada: 6.2\n");
		saidaEsperada.append("Obteve conceito C\n");

		adicionarCasoDeTeste();

		// Exercício resolvido 3 do capítulo 4

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare num1, num2 numerico\n");
		codigoFonte.append("escreva \"Digite o primeiro número: \"\n");
		codigoFonte.append("leia num1\n");
		codigoFonte.append("escreva \"Digite o segundo número: \"\n");
		codigoFonte.append("leia num2\n");
		codigoFonte.append("se num1 > num2\n");
		codigoFonte.append("entao escreva \"O maior número é: \", num1\n");
		codigoFonte.append("se num2 > num1\n");
		codigoFonte.append("entao escreva \"O maior número é: \", num2\n");
		codigoFonte.append("se num1 = num2\n");
		codigoFonte.append("entao escreva \"Os números são iguais\"\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("8\n");
		entrada.append("7\n");

		saidaEsperada.append("Digite o primeiro número: \n");
		saidaEsperada.append("Digite o segundo número: \n");
		saidaEsperada.append("O maior número é: 8\n");

		adicionarCasoDeTeste();

		// Exercício resolvido 6 do capítulo 4

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare num, r numerico\n");
		codigoFonte.append("escreva \"Digite um número: \"\n");
		codigoFonte.append("leia num\n");
		codigoFonte.append("r <- resto(num, 2)\n");
		codigoFonte.append("se r = 0\n");
		codigoFonte.append("entao escreva \"O número é par\"\n");
		codigoFonte.append("senao escreva \"O número é ímpar\"\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("9\n");

		saidaEsperada.append("Digite um número: \n");
		saidaEsperada.append("O número é ímpar\n");

		adicionarCasoDeTeste();

		// exercício resolvido 8 do capítulo 4

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare num1, num2, soma, raiz, op numerico\n");
		codigoFonte.append("escreva \"MENU\"\n");
		codigoFonte.append("escreva \"1 - Somar dois números\"\n");
		codigoFonte.append("escreva \"2 - Raiz quadrada de um número\"\n");
		codigoFonte.append("escreva \"Digite sua opção:\"\n");
		codigoFonte.append("leia op\n");
		codigoFonte.append("se op = 1\n");
		codigoFonte.append("entao inicio\n");
		codigoFonte.append("escreva \"Digite um valor para o primeiro número: \"\n");
		codigoFonte.append("leia num1\n");
		codigoFonte.append("escreva \"Digite um valor para o segundo número: \"\n");
		codigoFonte.append("leia num2\n");
		codigoFonte.append("soma <- num1 + num2\n");
		codigoFonte.append("escreva \"A soma de \", num1, \" e \", num2, \" é \", soma\n");
		codigoFonte.append("fim\n");
		codigoFonte.append("se op = 2\n");
		codigoFonte.append("entao inicio\n");
		codigoFonte.append("escreva \"Digite um valor: \"\n");
		codigoFonte.append("leia num1\n");
		codigoFonte.append("raiz <- raiz_quadrada(num1)\n");
		codigoFonte.append("escreva \"A raiz quadrada de \", num1, \" é \", raiz\n");
		codigoFonte.append("fim\n");
		codigoFonte.append("se op <> 1 e op <> 2\n");
		codigoFonte.append("entao escreva \"Opção inválida!\"\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("3\n");

		saidaEsperada.append("MENU\n");
		saidaEsperada.append("1 - Somar dois números\n");
		saidaEsperada.append("2 - Raiz quadrada de um número\n");
		saidaEsperada.append("Digite sua opção:\n");
		saidaEsperada.append("Opção inválida!\n");

		adicionarCasoDeTeste();

		// exercício resolvido 9 do capítulo 4
		// TODO Como testar funções com data e hora?

		// novoCasoDeTeste();
		//
		// codigoFonte.append("algoritmo\n");
		// codigoFonte.append("declare t, d, dia, mes, ano, hora, min\n");
		// codigoFonte.append("d <- obtenha_data\n");
		// codigoFonte.append("dia <- obtenha_dia(d)\n");
		// codigoFonte.append("mes <- obtenha_mês(d)\n");
		// codigoFonte.append("ano <- obtenha_ano(d)\n");
		// codigoFonte.append("escreva \"Data Atual: \", dia, \"/\", mes, \"/\",
		// ano\n");
		// codigoFonte.append("se mes = 1\n");
		// codigoFonte.append("entao escreva \"janeiro\"\n");
		// codigoFonte.append("se mes = 2\n");
		// codigoFonte.append("entao escreva \"fevereiro\"\n");
		// codigoFonte.append("se mes = 3\n");
		// codigoFonte.append("entao escreva \"março\"\n");
		// codigoFonte.append("se mes = 4\n");
		// codigoFonte.append("entao escreva \"abril\"\n");
		// codigoFonte.append("se mes = 5\n");
		// codigoFonte.append("entao escreva \"maio\"\n");
		// codigoFonte.append("se mes = 6\n");
		// codigoFonte.append("entao escreva \"junho\"\n");
		// codigoFonte.append("se mes = 7\n");
		// codigoFonte.append("entao escreva \"julho\"\n");
		// codigoFonte.append("se mes = 8\n");
		// codigoFonte.append("entao escreva \"agosto\"\n");
		// codigoFonte.append("se mes = 9\n");
		// codigoFonte.append("entao escreva \"setembro\"\n");
		// codigoFonte.append("se mes = 10\n");
		// codigoFonte.append("entao escreva \"outubro\"\n");
		// codigoFonte.append("se mes = 11\n");
		// codigoFonte.append("entao escreva \"novembro\"\n");
		// codigoFonte.append("se mes = 12\n");
		// codigoFonte.append("entao escreva \"dezembro\"\n");
		// codigoFonte.append("t <- obtenha_horário\n");
		// codigoFonte.append("hora <- obtenha_hora(t)\n");
		// codigoFonte.append("min <- obtenha_minuto(t)\n");
		// codigoFonte.append("escreva \"Hora Atual: \"\n");
		// codigoFonte.append("escreva hora, \":\", min\n");
		// codigoFonte.append("fim_algoritmo.");
		//
		// adicionarCasoDeTeste();

		// exercício resolvido 16 do capítulo 4

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare pre, venda, novo_pre numerico\n");
		codigoFonte.append("leia pre, venda\n");
		codigoFonte.append("se venda<500 ou pre<30\n");
		codigoFonte.append("entao novo_pre <- pre + 10/100 * pre\n");
		codigoFonte.append("senao se (venda>=500 e venda<1200) ou (pre>=30 e pre<80)\n");
		codigoFonte.append("entao novo_pre <- pre + 15/100 * pre\n");
		codigoFonte.append("senao se venda>=1200 ou pre>=80\n");
		codigoFonte.append("entao novo_pre <- pre - 20/100 * pre\n");
		codigoFonte.append("escreva novo_pre\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("29\n");
		entrada.append("500\n");

		saidaEsperada.append("31.9\n");

		adicionarCasoDeTeste();

		/////////////////////////////////////////
		// Capítulo 5 - Estrutura de repetição //
		/////////////////////////////////////////

		// exercício resolvido 1 do capítulo 5

		novoCasoDeTeste();

		codigoFonte.append("algoritmo\n");
		codigoFonte.append("declare i, ano_atual, salario numerico\n");
		codigoFonte.append("novo_salario, percentual numerico\n");
		codigoFonte.append("leia ano_atual\n");
		codigoFonte.append("salario <- 1000\n");
		codigoFonte.append("percentual <- 1.5/1000\n");
		codigoFonte.append("novo_salario <- salario + percentual * salario\n");
		codigoFonte.append("para i <- 2007 ate ano_atual faca\n");
		codigoFonte.append("inicio\n");
		codigoFonte.append("percentual <- 2 * percentual\n");
		codigoFonte.append("novo_salario <- novo_salario + percentual\n");
		codigoFonte.append("fim\n");
		codigoFonte.append("escreva novo_salario\n");
		codigoFonte.append("fim_algoritmo.");

		entrada.append("2009\n");

		// 2005 - 1000
		// 2006 - 1015
		// 2007 - 1045,45
		// 2008 - 1108,177
		// 2009 - 1241,15824

		// TODO Verificar - 1001.521 em vez de 1241.15824
		saidaEsperada.append("1241.15824\n");

		adicionarCasoDeTeste();

		return casosDeTeste;
	}
}