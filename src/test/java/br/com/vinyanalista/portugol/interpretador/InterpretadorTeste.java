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
            // TODO Usar Thread.sleep() no JUnit - há solução melhor?
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
        // Limpa o StringBuilder PARA reutilizá-lo
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

        // Produções cobertas:
        
        // AAlgoritmo
        // ADeclaracao
        // ASimplesVariavel
        // ANumericoTipo
        // AAtribuicaoComando
        // AEntradaComando
        // ASaidaComando
        // AVariavelPosicaoDeMemoria
        // AMultiplicacaoExpressao
        // APosicaoDeMemoriaExpressao
        // ALiteralValor

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE n1, n2, m NUMERICO\n");
        codigoFonte.append("ESCREVA \"Digite dois números\"\n");
        codigoFonte.append("LEIA n1, n2\n");
        codigoFonte.append("m <- n1 * n2\n");
        codigoFonte.append("ESCREVA \"Multiplicação = \", m\n");
        codigoFonte.append("FIM_ALGORITMO.");

        saidaEsperada.append("Digite dois números\n");
        entrada.append("2\n");
        entrada.append("3\n");
        saidaEsperada.append("Multiplicação = 6\n");

        adicionarCasoDeTeste();

        // Segundo exemplo

        // ABlocoComando
        // ACondicionalComando
        // AIgualdadeExpressao
        // ADivisaoExpressao
        // AValorExpressao
        // AInteiroValor

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE n1, n2, d NUMERICO\n");
        codigoFonte.append("ESCREVA \"Digite dois números\"\n");
        codigoFonte.append("LEIA n1, n2\n");
        codigoFonte.append("SE n2 = 0\n");
        codigoFonte.append("ENTAO ESCREVA \"Impossível dividir\"\n");
        codigoFonte.append("SENAO INICIO\n");
        codigoFonte.append("      d <- n1 / n2\n");
        codigoFonte.append("      ESCREVA \"Divisão = \", d\n");
        codigoFonte.append("      FIM\n");
        codigoFonte.append("FIM_ALGORITMO.");

        saidaEsperada.append("Digite dois números\n");
        entrada.append("4\n");
        entrada.append("0\n");
        saidaEsperada.append("Impossível dividir\n");

        adicionarCasoDeTeste();

        // Terceiro exemplo

        // AMaiorOuIgualExpressao
        // ASomaExpressao

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE n1, n2, m NUMERICO\n");
        codigoFonte.append("ESCREVA \"Digite as duas notas\"\n");
        codigoFonte.append("LEIA n1, n2\n");
        codigoFonte.append("m <- (n1 + n2)/2\n");
        codigoFonte.append("ESCREVA \"Média = \", m\n");
        codigoFonte.append("se m >= 7\n");
        codigoFonte.append("ENTAO ESCREVA \"Aprovado\"\n");
        codigoFonte.append("SENAO ESCREVA \"Reprovado\"\n");
        codigoFonte.append("FIM_ALGORITMO.");

        saidaEsperada.append("Digite as duas notas\n");
        entrada.append("7\n");
        entrada.append("8\n");
        saidaEsperada.append("Média = 7.5\n");
        saidaEsperada.append("Aprovado\n");

        adicionarCasoDeTeste();

        // Quarto exemplo

        // AMenorOuIgualExpressao

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE sal_atual, novo_sal NUMERICO\n");
        codigoFonte.append("ESCREVA \"Digite o salário atual do funcionário\"\n");
        codigoFonte.append("LEIA sal_atual\n");
        codigoFonte.append("SE sal_atual <= 500\n");
        codigoFonte.append("ENTAO novo_sal <- sal_atual * 1.20\n");
        codigoFonte.append("SENAO novo_sal <- sal_atual * 1.10\n");
        codigoFonte.append("ESCREVA \"Novo salário = \", novo_sal\n");
        codigoFonte.append("FIM_ALGORITMO.");

        saidaEsperada.append("Digite o salário atual do funcionário\n");
        entrada.append("490\n");
        // TODO Defeito - 588.0 em vez de 588
        saidaEsperada.append("Novo salário = 588\n");

        adicionarCasoDeTeste();

        ///////////////////////////////////////
        // Capítulo 3 - Estrutura sequencial //
        ///////////////////////////////////////

        // Exercício resolvido 6

        // ASubtracaoExpressao

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("    DECLARE sal, salreceber, grat, imp NUMERICO\n");
        codigoFonte.append("    LEIA sal\n");
        codigoFonte.append("    grat <- sal * 5/100\n");
        codigoFonte.append("    imp <- sal * 7/100\n");
        codigoFonte.append("    salreceber <- sal + grat - imp\n");
        codigoFonte.append("    ESCREVA salreceber\n");
        codigoFonte.append("FIM_ALGORITMO.");

        entrada.append("1000\n");

        saidaEsperada.append("980\n");

        adicionarCasoDeTeste();

        // Exercício resolvido 10

        // ARealValor

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("    DECLARE area, raio NUMERICO\n");
        codigoFonte.append("    LEIA raio\n");
        codigoFonte.append("    area <- 3.1415 * potencia(raio, 2)\n");
        codigoFonte.append("    ESCREVA area\n");
        codigoFonte.append("FIM_ALGORITMO.");

        entrada.append("30\n");

        saidaEsperada.append(String.valueOf(3.1415 * Math.pow(30, 2)) + "\n");

        adicionarCasoDeTeste();

        ////////////////////////////////////////
        // Capítulo 4 - Estrutura condicional //
        ////////////////////////////////////////

        // Exercício resolvido 1

        // AConjuncaoExpressao
        // AMenorExpressao

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE nota_trab, aval_sem, exame, media NUMERICO\n");
        codigoFonte.append("ESCREVA \"Digite a nota do trabalho de laboratório: \"\n");
        codigoFonte.append("LEIA nota_trab\n");
        codigoFonte.append("ESCREVA \"Digite a nota da avaliação semestral: \"\n");
        codigoFonte.append("LEIA aval_sem\n");
        codigoFonte.append("ESCREVA \"Digite a nota do exame final: \"\n");
        codigoFonte.append("LEIA exame\n");
        codigoFonte.append("media <- (nota_trab * 2 + aval_sem * 3 + exame * 5) / 10\n");
        codigoFonte.append("ESCREVA \"Média ponderada: \", media\n");
        codigoFonte.append("SE media >= 8 E media <= 10\n");
        codigoFonte.append("    ENTAO ESCREVA \"Obteve conceito A\"\n");
        codigoFonte.append("SE media >= 7 E media < 8\n");
        codigoFonte.append("    ENTAO ESCREVA \"Obteve conceito B\"\n");
        codigoFonte.append("SE media >= 6 E media < 7\n");
        codigoFonte.append("    ENTAO ESCREVA \"Obteve conceito C\"\n");
        codigoFonte.append("SE media >= 5 E media < 6\n");
        codigoFonte.append("    ENTAO ESCREVA \"Obteve conceito D\"\n");
        codigoFonte.append("SE media >= 0 E media < 5\n");
        codigoFonte.append("    ENTAO ESCREVA \"Obteve conceito E\"\n");
        codigoFonte.append("FIM_ALGORITMO.");

        saidaEsperada.append("Digite a nota do trabalho de laboratório: \n");
        entrada.append("10\n");
        saidaEsperada.append("Digite a nota da avaliação semestral: \n");
        entrada.append("4\n");
        saidaEsperada.append("Digite a nota do exame final: \n");
        entrada.append("6\n");
        saidaEsperada.append("Média ponderada: 6.2\n");
        saidaEsperada.append("Obteve conceito C\n");

        adicionarCasoDeTeste();

        // Exercício resolvido 3

        // AMaiorExpressao

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE num1, num2 NUMERICO\n");
        codigoFonte.append("ESCREVA \"Digite o primeiro número: \"\n");
        codigoFonte.append("LEIA num1\n");
        codigoFonte.append("ESCREVA \"Digite o segundo número: \"\n");
        codigoFonte.append("LEIA num2\n");
        codigoFonte.append("SE num1 > num2\n");
        codigoFonte.append("    ENTAO ESCREVA \"O maior número é: \", num1\n");
        codigoFonte.append("SE num2 > num1\n");
        codigoFonte.append("    ENTAO ESCREVA \"O maior número é: \", num2\n");
        codigoFonte.append("SE num1 = num2\n");
        codigoFonte.append("    ENTAO ESCREVA \"Os números são iguais\"\n");
        codigoFonte.append("FIM_ALGORITMO.");

        saidaEsperada.append("Digite o primeiro número: \n");
        entrada.append("8\n");
        saidaEsperada.append("Digite o segundo número: \n");
        entrada.append("7\n");
        saidaEsperada.append("O maior número é: 8\n");

        adicionarCasoDeTeste();

        // Exercício resolvido 8

        // ADiferencaExpressao

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE num1, num2, soma, raiz, op NUMERICO\n");
        codigoFonte.append("ESCREVA \"MENU\"\n");
        codigoFonte.append("ESCREVA \"1 - Somar dois números\"\n");
        codigoFonte.append("ESCREVA \"2 - Raiz quadrada de um número\"\n");
        codigoFonte.append("ESCREVA \"Digite sua opção:\"\n");
        codigoFonte.append("LEIA op\n");
        codigoFonte.append("SE op = 1\n");
        codigoFonte.append("    ENTAO INICIO\n");
        codigoFonte.append("            ESCREVA \"Digite um valor PARA o primeiro número: \"\n");
        codigoFonte.append("            LEIA num1\n");
        codigoFonte.append("            ESCREVA \"Digite um valor PARA o segundo número: \"\n");
        codigoFonte.append("            LEIA num2\n");
        codigoFonte.append("            soma <- num1 + num2\n");
        codigoFonte.append("            ESCREVA \"A soma de \", num1, \" e \", num2, \" é \", soma\n");
        codigoFonte.append("          FIM\n");
        codigoFonte.append("SE op = 2\n");
        codigoFonte.append("    ENTAO INICIO\n");
        codigoFonte.append("            ESCREVA \"Digite um valor: \"\n");
        codigoFonte.append("            LEIA num1\n");
        codigoFonte.append("            raiz <- raiz_quadrada(num1)\n");
        codigoFonte.append("            ESCREVA \"A raiz quadrada de \", num1, \" é \", raiz\n");
        codigoFonte.append("          FIM\n");
        codigoFonte.append("SE op <> 1 E op <> 2\n");
        codigoFonte.append("    ENTAO ESCREVA \"Opção inválida!\"\n");
        codigoFonte.append("FIM_ALGORITMO.");

        saidaEsperada.append("MENU\n");
        saidaEsperada.append("1 - Somar dois números\n");
        saidaEsperada.append("2 - Raiz quadrada de um número\n");
        saidaEsperada.append("Digite sua opção:\n");
        entrada.append("3\n");
        saidaEsperada.append("Opção inválida!\n");

        adicionarCasoDeTeste();

        // Exercício resolvido 16

        // ADisjuncaoExpressao

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE pre, venda, novo_pre NUMERICO\n");
        codigoFonte.append("LEIA pre, venda\n");
        codigoFonte.append("SE venda<500 OU pre<30\n");
        codigoFonte.append("ENTAO novo_pre <- pre + 10/100 * pre\n");
        codigoFonte.append("SENAO SE (venda>=500 E venda<1200) OU (pre>=30 E pre<80)\n");
        codigoFonte.append("        ENTAO novo_pre <- pre + 15/100 * pre\n");
        codigoFonte.append("        SENAO SE venda>=1200 OU pre>=80\n");
        codigoFonte.append("            ENTAO novo_pre <- pre - 20/100 * pre\n");
        codigoFonte.append("ESCREVA novo_pre\n");
        codigoFonte.append("FIM_ALGORITMO.");

        entrada.append("29\n");
        entrada.append("500\n");

        saidaEsperada.append("31.9\n");

        adicionarCasoDeTeste();

        /////////////////////////////////////////
        // Capítulo 5 - Estrutura de repetição //
        /////////////////////////////////////////

        // Exercício resolvido 1

        // ARepeticaoParaComando

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE i, ano_atual, salario NUMERICO\n");
        codigoFonte.append("        novo_salario, percentual NUMERICO\n");
        codigoFonte.append("LEIA ano_atual\n");
        codigoFonte.append("salario <- 1000\n");
        codigoFonte.append("percentual <- 1.5/100\n");
        codigoFonte.append("novo_salario <- salario + percentual * salario\n");
        codigoFonte.append("PARA i <- 2007 ATE ano_atual FACA\n");
        codigoFonte.append("INICIO\n");
        codigoFonte.append("percentual <- 2 * percentual\n");
        codigoFonte.append("novo_salario <- novo_salario + percentual * novo_salario\n");
        codigoFonte.append("FIM\n");
        codigoFonte.append("ESCREVA novo_salario\n");
        codigoFonte.append("FIM_ALGORITMO.");

        entrada.append("2009\n");

        // TODO Defeito - 1241.1582400000002 em vez de 1241.15824
        saidaEsperada.append("1241.15824\n");

        adicionarCasoDeTeste();

        // Exercício resolvido 8

        // ARepeticaoEnquantoComando

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE i, num_termos, num1, num2, num3 NUMERICO\n");
        codigoFonte.append("LEIA num_termos\n");
        codigoFonte.append("num1 <- 2\n");
        codigoFonte.append("num2 <- 7\n");
        codigoFonte.append("num3 <- 3\n");
        codigoFonte.append("ESCREVA num1\n");
        codigoFonte.append("ESCREVA num2\n");
        codigoFonte.append("ESCREVA num3\n");
        codigoFonte.append("i <- 4\n");
        codigoFonte.append("enquanto i <= num_termos FACA\n");
        codigoFonte.append("INICIO\n");
        codigoFonte.append("    num1 <- num1 * 2\n");
        codigoFonte.append("    ESCREVA num1\n");
        codigoFonte.append("    i <- i + 1\n");
        codigoFonte.append("    SE i <= num_termos\n");
        codigoFonte.append("    ENTAO INICIO\n");
        codigoFonte.append("          num2 <- num2 * 3\n");
        codigoFonte.append("          ESCREVA num2\n");
        codigoFonte.append("          i <- i + 1\n");
        codigoFonte.append("          SE i <= num_termos\n");
        codigoFonte.append("          ENTAO INICIO\n");
        codigoFonte.append("                num3 <- num3 * 4\n");
        codigoFonte.append("                ESCREVA num3\n");
        codigoFonte.append("                i <- i + 1\n");
        codigoFonte.append("                FIM\n");
        codigoFonte.append("          FIM\n");
        codigoFonte.append("FIM\n");
        codigoFonte.append("FIM_ALGORITMO.");

        entrada.append("9\n");

        saidaEsperada.append("2\n");
        saidaEsperada.append("7\n");
        saidaEsperada.append("3\n");
        saidaEsperada.append("4\n");
        saidaEsperada.append("21\n");
        saidaEsperada.append("12\n");
        saidaEsperada.append("8\n");
        saidaEsperada.append("63\n");
        saidaEsperada.append("48\n");

        adicionarCasoDeTeste();

        // Exercício resolvido 16

        // ARepeticaoRepitaComando

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE base, altura, area NUMERICO\n");
        codigoFonte.append("REPITA\n");
        codigoFonte.append("    LEIA base\n");
        codigoFonte.append("ATE base > 0\n");
        codigoFonte.append("REPITA\n");
        codigoFonte.append("    LEIA altura\n");
        codigoFonte.append("ATE altura > 0\n");
        codigoFonte.append("area <- base * altura / 2\n");
        codigoFonte.append("ESCREVA area\n");
        codigoFonte.append("FIM_ALGORITMO.");

        entrada.append("0\n");
        entrada.append("4\n");
        entrada.append("0\n");
        entrada.append("0\n");
        entrada.append("0\n");
        entrada.append("3\n");

        saidaEsperada.append("6\n");

        adicionarCasoDeTeste();

        // Exercício resolvido 18 (como na 2ª edição do livro)

        // AChamadaASubRotinaComando

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE linhas, num, quad, cubo, raiz NUMERICO\n");
        codigoFonte.append("LEIA num\n");
        codigoFonte.append("ESCREVA \"Valor Quadrado Cubo Raiz\"\n");
        codigoFonte.append("linhas <- 1\n");
        codigoFonte.append("ENQUANTO num > 0 FACA\n");
        codigoFonte.append("INICIO\n");
        codigoFonte.append("quad <- num * num\n");
        codigoFonte.append("cubo <- num * num * num\n");
        codigoFonte.append("raiz <- raiz_quadrada(num)\n");
        codigoFonte.append("SE linhas < 20\n");
        codigoFonte.append("    ENTAO INICIO\n");
        codigoFonte.append("            linhas <- linhas + 1\n");
        codigoFonte.append("            ESCREVA num, \" \", quad, \" \", cubo, \" \", raiz\n");
        codigoFonte.append("          FIM\n");
        codigoFonte.append("    SENAO INICIO\n");
        codigoFonte.append("            LIMPAR_TELA()\n");
        codigoFonte.append("            linhas <- 1\n");
        codigoFonte.append("            ESCREVA \"Valor Quadrado Cubo Raiz\"\n");
        codigoFonte.append("            linhas <- linhas + 1\n");
        codigoFonte.append("            ESCREVA num, \" \", quad, \" \", cubo, \" \", raiz\n");
        codigoFonte.append("          FIM\n");
        codigoFonte.append("LEIA num\n");
        codigoFonte.append("FIM\n");
        codigoFonte.append("FIM_ALGORITMO.");

        for (int linhas = 1; linhas < 21; linhas++) {
            entrada.append("4\n");
        }
        entrada.append("0\n");

        saidaEsperada.append("Valor Quadrado Cubo Raiz\n");
        saidaEsperada.append("4 16 64 2\n");

        adicionarCasoDeTeste();

        ////////////////////////
        // Capítulo 6 - Vetor //
        ////////////////////////

        // Exercício resolvido 1

        // AVetorOuMatrizVariavel (vetor)

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE num[9] NUMERICO\n");
        codigoFonte.append("        i, j, cont NUMERICO\n");
        codigoFonte.append("PARA i <- 1 ATE 9 FACA\n");
        codigoFonte.append("    INICIO\n");
        codigoFonte.append("        LEIA num[i]\n");
        codigoFonte.append("    FIM\n");
        codigoFonte.append("PARA i <- 1 ATE 9 FACA\n");
        codigoFonte.append("    INICIO\n");
        codigoFonte.append("        cont <- 0\n");
        codigoFonte.append("        PARA j <- 1 ATE num[i] FACA\n");
        codigoFonte.append("            INICIO\n");
        codigoFonte.append("                SE resto(num[i], j) = 0\n");
        codigoFonte.append("                ENTAO cont <- cont + 1\n");
        codigoFonte.append("            FIM\n");
        codigoFonte.append("        SE cont <= 2\n");
        codigoFonte.append("        ENTAO INICIO\n");
        codigoFonte.append("              ESCREVA num[i]\n");
        codigoFonte.append("              ESCREVA i\n");
        codigoFonte.append("              FIM\n");
        codigoFonte.append("    FIM\n");
        codigoFonte.append("FIM_ALGORITMO.");

        entrada.append("2\n");
        entrada.append("17\n");
        entrada.append("10\n");
        entrada.append("161\n");
        entrada.append("113\n");
        entrada.append("3\n");
        entrada.append("467\n");
        entrada.append("4\n");
        entrada.append("293\n");

        saidaEsperada.append("2\n");
        saidaEsperada.append("1\n");
        saidaEsperada.append("17\n");
        saidaEsperada.append("2\n");
        saidaEsperada.append("113\n");
        saidaEsperada.append("5\n");
        saidaEsperada.append("3\n");
        saidaEsperada.append("6\n");
        saidaEsperada.append("467\n");
        saidaEsperada.append("7\n");
        saidaEsperada.append("293\n");
        saidaEsperada.append("9\n");

        adicionarCasoDeTeste();

        // Exercício resolvido 10

        // ALiteralTipo

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE gabarito[8], resposta[8] LITERAL\n");
        codigoFonte.append("        num, pontos, tot_ap, perc_ap, i, j NUMERICO\n");
        codigoFonte.append("PARA i <- 1 ATE 8 FACA\n");
        codigoFonte.append("    INICIO\n");
        codigoFonte.append("        ESCREVA \"Digite a resposta da questão \", i\n");
        codigoFonte.append("        LEIA gabarito[i]\n");
        codigoFonte.append("    FIM\n");
        codigoFonte.append("tot_ap <- 0\n");
        codigoFonte.append("PARA i <- 1 ATE 10 FACA\n");
        codigoFonte.append("    INICIO\n");
        codigoFonte.append("        ESCREVA \"Digite o número do \", i, \"º aluno\"\n");
        codigoFonte.append("        LEIA num\n");
        codigoFonte.append("        pontos <- 0\n");
        codigoFonte.append("        PARA j <- 1 ATE 8 FACA\n");
        codigoFonte.append("            INICIO\n");
        codigoFonte.append(
                "                ESCREVA \"Digite a resposta dada pelo aluno \", num, \" à \", j, \"a questão\"\n");
        codigoFonte.append("                LEIA resposta[j]\n");
        codigoFonte.append("                SE resposta[j] = gabarito[j]\n");
        codigoFonte.append("                ENTAO pontos <- pontos + 1\n");
        codigoFonte.append("            FIM\n");
        codigoFonte.append("        ESCREVA \"A nota do aluno \", num, \" foi \", pontos\n");
        codigoFonte.append("        SE pontos >= 6\n");
        codigoFonte.append("            ENTAO tot_ap <- tot_ap + 1\n");
        codigoFonte.append("    FIM\n");
        codigoFonte.append("perc_ap <- tot_ap * 100 / 10\n");
        codigoFonte.append("ESCREVA \"O percentual de alunos aprovados é \", perc_ap\n");
        codigoFonte.append("FIM_ALGORITMO.\n");

        entrada.append("d\n").append("c\n").append("d\n").append("d\n").append("c\n").append("a\n").append("e\n")
                .append("c\n");
        entrada.append("1\n").append("b\n").append("b\n").append("e\n").append("c\n").append("d\n").append("c\n")
                .append("d\n").append("e\n");
        entrada.append("2\n").append("d\n").append("c\n").append("d\n").append("d\n").append("c\n").append("b\n")
                .append("e\n").append("b\n");
        entrada.append("3\n").append("b\n").append("e\n").append("c\n").append("c\n").append("d\n").append("b\n")
                .append("d\n").append("b\n");
        entrada.append("4\n").append("a\n").append("a\n").append("c\n").append("e\n").append("b\n").append("e\n")
                .append("a\n").append("c\n");
        entrada.append("5\n").append("d\n").append("d\n").append("d\n").append("d\n").append("c\n").append("c\n")
                .append("c\n").append("c\n");
        entrada.append("6\n").append("b\n").append("e\n").append("b\n").append("a\n").append("c\n").append("e\n")
                .append("a\n").append("b\n");
        entrada.append("7\n").append("c\n").append("a\n").append("d\n").append("d\n").append("d\n").append("e\n")
                .append("d\n").append("d\n");
        entrada.append("8\n").append("e\n").append("d\n").append("c\n").append("b\n").append("a\n").append("b\n")
                .append("c\n").append("d\n");
        entrada.append("9\n").append("d\n").append("c\n").append("d\n").append("d\n").append("c\n").append("a\n")
                .append("e\n").append("c\n");
        entrada.append("10\n").append("d\n").append("c\n").append("d\n").append("d\n").append("a\n").append("b\n")
                .append("c\n").append("d\n");

        for (int q = 1; q < 9; q++) {
            saidaEsperada.append("Digite a resposta da questão ").append(q).append("\n");
        }
        for (int a = 1; a < 11; a++) {
            saidaEsperada.append("Digite o número do ").append(a).append("º aluno\n");
            for (int q = 1; q < 9; q++) {
                saidaEsperada.append("Digite a resposta dada pelo aluno ").append(a).append(" à ").append(q)
                        .append("a questão\n");
            }
            saidaEsperada.append("A nota do aluno ").append(a).append(" foi ");
            int nota = -1;
            switch (a) {
            case 1:
                nota = 0;
                break;
            case 2:
                nota = 6;
                break;
            case 3:
                nota = 0;
                break;
            case 4:
                nota = 1;
                break;
            case 5:
                nota = 5;
                break;
            case 6:
                nota = 1;
                break;
            case 7:
                nota = 2;
                break;
            case 8:
                nota = 0;
                break;
            case 9:
                nota = 8;
                break;
            case 10:
                nota = 4;
                break;
            default:
                break;
            }
            saidaEsperada.append(nota).append("\n");
        }
        saidaEsperada.append("O percentual de alunos aprovados é 20\n");

        adicionarCasoDeTeste();

        // Exercício resolvido 13

        // ALogicoTipo
        // ALogicoValor

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("    DECLARE vet[10] NUMERICO\n");
        codigoFonte.append("            achou logico\n");
        codigoFonte.append("            i NUMERICO\n");
        codigoFonte.append("PARA i <- 1 ATE 10 FACA\n");
        codigoFonte.append("    INICIO\n");
        codigoFonte.append("    LEIA vet[i]\n");
        codigoFonte.append("    FIM\n");
        codigoFonte.append("achou <- falso\n");
        codigoFonte.append("PARA i <- 1 ATE 10 FACA\n");
        codigoFonte.append("    INICIO\n");
        codigoFonte.append("    SE vet[i] > 50\n");
        codigoFonte.append("    ENTAO INICIO\n");
        codigoFonte.append("            ESCREVA vet[i], \" \", i\n");
        codigoFonte.append("            achou <- verdadeiro\n");
        codigoFonte.append("          FIM\n");
        codigoFonte.append("    FIM\n");
        codigoFonte.append("SE achou = falso\n");
        codigoFonte.append("ENTAO ESCREVA \"Não existem números superiores a 50 no vetor\"\n");
        codigoFonte.append("FIM_ALGORITMO.");

        entrada.append("95\n");
        entrada.append("80\n");
        entrada.append("40\n");
        entrada.append("5\n");
        entrada.append("77\n");
        entrada.append("41\n");
        entrada.append("57\n");
        entrada.append("42\n");
        entrada.append("26\n");
        entrada.append("32\n");

        saidaEsperada.append("95 1\n");
        saidaEsperada.append("80 2\n");
        saidaEsperada.append("77 5\n");
        saidaEsperada.append("57 7\n");

        adicionarCasoDeTeste();

        // Exercício resolvido 20

        // ANegativoExpressao

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("    DECLARE vet[5], i, cod NUMERICO\n");
        codigoFonte.append("PARA i <- 1 ATE 5 FACA\n");
        codigoFonte.append("    INICIO\n");
        codigoFonte.append("    LEIA vet[i]\n");
        codigoFonte.append("    FIM\n");
        codigoFonte.append("LEIA cod\n");
        codigoFonte.append("SE cod = 0\n");
        codigoFonte.append("ENTAO ESCREVA \"FIM\"\n");
        codigoFonte.append("SE cod = 1\n");
        codigoFonte.append("ENTAO INICIO\n");
        codigoFonte.append("      PARA i <- 1 ATE 5 FACA\n");
        codigoFonte.append("            INICIO\n");
        codigoFonte.append("            ESCREVA vet[i]\n");
        codigoFonte.append("            FIM\n");
        codigoFonte.append("      FIM\n");
        codigoFonte.append("SE cod = 2\n");
        codigoFonte.append("ENTAO INICIO\n");
        codigoFonte.append("      PARA i <- 5 ATE 1 FACA passo -1\n");
        codigoFonte.append("            INICIO\n");
        codigoFonte.append("            ESCREVA vet[i]\n");
        codigoFonte.append("            FIM\n");
        codigoFonte.append("      FIM\n");
        codigoFonte.append("SE (cod < 0) OU (cod > 2)\n");
        codigoFonte.append("ENTAO ESCREVA \"Código inválido\"\n");
        codigoFonte.append("FIM_ALGORITMO.");

        entrada.append("23.83\n");
        entrada.append("21.9\n");
        entrada.append("9.95\n");
        entrada.append("41.52\n");
        entrada.append("40.47\n");
        entrada.append("2\n");

        saidaEsperada.append("40.47\n");
        saidaEsperada.append("41.52\n");
        saidaEsperada.append("9.95\n");
        saidaEsperada.append("21.9\n");
        saidaEsperada.append("23.83\n");

        adicionarCasoDeTeste();

        /////////////////////////
        // Capítulo 7 - Matriz //
        /////////////////////////

        // Exercício resolvido 1

        // AVetorOuMatrizVariavel (matriz)

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("    DECLARE mat[2,2], resultado[2,2], i, j, maior NUMERICO\n");
        codigoFonte.append("PARA i <- 1 ATE 2 FACA\n");
        codigoFonte.append("        INICIO\n");
        codigoFonte.append("        PARA j <- 1 ATE 2 FACA\n");
        codigoFonte.append("                INICIO\n");
        codigoFonte.append("                    LEIA mat[i,j]\n");
        codigoFonte.append("                FIM\n");
        codigoFonte.append("        FIM\n");
        codigoFonte.append("maior <- mat[1,1]\n");
        codigoFonte.append("PARA i <- 1 ATE 2 FACA\n");
        codigoFonte.append("        INICIO\n");
        codigoFonte.append("        PARA j <- 1 ATE 2 FACA\n");
        codigoFonte.append("                INICIO\n");
        codigoFonte.append("                SE mat[i,j] > maior\n");
        codigoFonte.append("                ENTAO maior <- mat[i,j]\n");
        codigoFonte.append("                FIM\n");
        codigoFonte.append("        FIM\n");
        codigoFonte.append("PARA i <- 1 ATE 2 FACA\n");
        codigoFonte.append("    INICIO\n");
        codigoFonte.append("    PARA j <- 1 ATE 2 FACA\n");
        codigoFonte.append("        INICIO\n");
        codigoFonte.append("            resultado[i,j] <- maior * mat[i,j]\n");
        codigoFonte.append("        FIM\n");
        codigoFonte.append("    FIM\n");
        codigoFonte.append("PARA i <- 1 ATE 2 FACA\n");
        codigoFonte.append("    INICIO\n");
        codigoFonte.append("    PARA j <- 1 ATE 2 FACA\n");
        codigoFonte.append("        INICIO\n");
        codigoFonte.append("            ESCREVA resultado[i,j]\n");
        codigoFonte.append("        FIM\n");
        codigoFonte.append("    FIM\n");
        codigoFonte.append("FIM_ALGORITMO.");

        entrada.append("1\n");
        entrada.append("2\n");
        entrada.append("4\n");
        entrada.append("3\n");

        saidaEsperada.append("4\n");
        saidaEsperada.append("8\n");
        saidaEsperada.append("16\n");
        saidaEsperada.append("12\n");

        adicionarCasoDeTeste();

        /////////////////////////////
        // Capítulo 8 - Sub-rotina //
        /////////////////////////////

        // Exercício resolvido 1

        // ARetorneComando
        // AChamadaASubRotinaExpressao
        // AChamadaASubRotina
        // ASubRotina

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE num, x NUMERICO\n");
        codigoFonte.append("LEIA num\n");
        codigoFonte.append("x <- verifica(num)\n");
        codigoFonte.append("SE x = 1\n");
        codigoFonte.append("ENTAO ESCREVA \"Número positivo\"\n");
        codigoFonte.append("SENAO ESCREVA \"Número negativo\"\n");
        codigoFonte.append("FIM_ALGORITMO\n");
        codigoFonte.append("\n");
        codigoFonte.append("SUB-ROTINA verifica(num NUMERICO)\n");
        codigoFonte.append("    DECLARE res NUMERICO\n");
        codigoFonte.append("    SE num >= 0\n");
        codigoFonte.append("    ENTAO res <- 1\n");
        codigoFonte.append("    SENAO res <- 0\n");
        codigoFonte.append("    RETORNE res\n");
        codigoFonte.append("FIM_SUB_ROTINA verifica\n");

        entrada.append("-0.1\n");

        saidaEsperada.append("Número negativo\n");

        adicionarCasoDeTeste();

        ////////////////////////////
        // Capítulo 10 - Registro //
        ////////////////////////////

        // Exercício resolvido 1

        // ARegistroTipo
        // ACampoPosicaoDeMemoria

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE\n");
        codigoFonte.append("    conta[15] registro (num, saldo NUMERICO nome LITERAL)\n");
        codigoFonte.append("    i, op, posi, achou, num_conta, menor_saldo NUMERICO\n");
        codigoFonte.append("    nome_cliente LITERAL\n");
        codigoFonte.append("PARA i <- 1 ATE 15 FACA INICIO\n");
        codigoFonte.append("    conta[i].num <- 0\n");
        codigoFonte.append("    conta[i].nome <- \"\"\n");
        codigoFonte.append("    conta[i].saldo <- 0\n");
        codigoFonte.append("FIM\n");
        codigoFonte.append("posi <- 1\n");
        codigoFonte.append("REPITA\n");
        codigoFonte.append("    ESCREVA \"Menu de Opções\"\n");
        codigoFonte.append("    ESCREVA \"1 - Cadastrar contas\"\n");
        codigoFonte.append("    ESCREVA \"2 - Visualizar todas as contas de determinado cliente\"\n");
        codigoFonte.append("    ESCREVA \"3 - Excluir conta de menor saldo\"\n");
        codigoFonte.append("    ESCREVA \"4 - Sair\"\n");
        codigoFonte.append("    ESCREVA \"Digite sua opção\"\n");
        codigoFonte.append("    LEIA op\n");
        codigoFonte.append("    SE op < 1 OU op > 4\n");
        codigoFonte.append("        ENTAO ESCREVA \"Opção inválida\"\n");
        codigoFonte.append("    SE op = 1\n");
        codigoFonte.append("    ENTAO INICIO\n");
        codigoFonte.append("            SE posi > 15 ENTAO\n");
        codigoFonte.append("                ESCREVA \"Todas as contas já foram cadastradas!\"\n");
        codigoFonte.append("                SENAO INICIO\n");
        codigoFonte.append("                        achou <- 0\n");
        codigoFonte.append("                        ESCREVA \"Digite o número da conta a ser incluída\"\n");
        codigoFonte.append("                        LEIA num_conta\n");
        codigoFonte.append("                        PARA i <- 1 ATE posi - 1 FACA\n");
        codigoFonte.append("                            INICIO\n");
        codigoFonte.append("                                SE num_conta = conta[i].num\n");
        codigoFonte.append("                                    ENTAO achou <- 1\n");
        codigoFonte.append("                            FIM\n");
        codigoFonte.append("                        SE achou = 1\n");
        codigoFonte
                .append("                            ENTAO ESCREVA \"Já existe conta cadastrada com esse número\"\n");
        codigoFonte.append("                            SENAO INICIO\n");
        codigoFonte.append("                                    conta[posi].num <- num_conta\n");
        codigoFonte.append("                                    ESCREVA \"Digite o nome do cliente\"\n");
        codigoFonte.append("                                    LEIA conta[posi].nome\n");
        codigoFonte.append("                                    ESCREVA \"Digite o saldo do cliente\"\n");
        codigoFonte.append("                                    LEIA conta[posi].saldo\n");
        codigoFonte.append("                                    ESCREVA \"Conta cadastrada com sucesso\"\n");
        codigoFonte.append("                                    posi <- posi + 1\n");
        codigoFonte.append("                                  FIM\n");
        codigoFonte.append("                      FIM\n");
        codigoFonte.append("          FIM\n");
        codigoFonte.append("    SE op = 2\n");
        codigoFonte.append("        ENTAO INICIO\n");
        codigoFonte.append("                ESCREVA \"Digite o nome do cliente a ser consultado\"\n");
        codigoFonte.append("                LEIA nome_cliente\n");
        codigoFonte.append("                achou <- 0\n");
        codigoFonte.append("                PARA i <- 1 ATE posi - 1 FACA\n");
        codigoFonte.append("                    INICIO\n");
        codigoFonte.append("                        SE conta[i].nome = nome_cliente\n");
        codigoFonte.append("                        ENTAO INICIO\n");
        codigoFonte.append("                                ESCREVA conta[i].num, \" - \", conta[i].saldo\n");
        codigoFonte.append("                                achou <- 1\n");
        codigoFonte.append("                              FIM\n");
        codigoFonte.append("                    FIM\n");
        codigoFonte.append("                SE achou = 0\n");
        codigoFonte.append("                    ENTAO ESCREVA \"Não existe conta cadastrada PARA este cliente\"\n");
        codigoFonte.append("              FIM\n");
        codigoFonte.append("    SE op = 3\n");
        codigoFonte.append("        ENTAO INICIO\n");
        codigoFonte.append("                SE posi = 1\n");
        codigoFonte.append("                    ENTAO ESCREVA \"Nenhuma conta foi cadastrada\"\n");
        codigoFonte.append("                    SENAO INICIO\n");
        codigoFonte.append("                            menor_saldo <- conta[1].saldo\n");
        codigoFonte.append("                            achou <- 1\n");
        codigoFonte.append("                            i <- 2\n");
        codigoFonte.append("                            ENQUANTO i < posi FACA\n");
        codigoFonte.append("                                INICIO\n");
        codigoFonte.append("                                    SE conta[i].saldo < menor_saldo\n");
        codigoFonte.append("                                        ENTAO INICIO\n");
        codigoFonte.append("                                                menor_saldo <- conta[i].saldo\n");
        codigoFonte.append("                                                achou <- i\n");
        codigoFonte.append("                                              FIM\n");
        codigoFonte.append("                                    i <- i + 1\n");
        codigoFonte.append("                                FIM\n");
        codigoFonte.append("                            SE achou <> posi - 1 ENTAO\n");
        codigoFonte.append("                            PARA i <- achou + 1 ATE posi - 1 FACA\n");
        codigoFonte.append("                                INICIO\n");
        codigoFonte.append("                                    conta[i - 1].num <- conta[i].num\n");
        codigoFonte.append("                                    conta[i - 1].nome <- conta[i].nome\n");
        codigoFonte.append("                                    conta[i - 1].saldo <- conta[i].saldo\n");
        codigoFonte.append("                                FIM\n");
        codigoFonte.append("                            ESCREVA \"Conta excluída com sucesso\"\n");
        codigoFonte.append("                            posi <- posi - 1\n");
        codigoFonte.append("                          FIM\n");
        codigoFonte.append("              FIM\n");
        codigoFonte.append("ATE op = 4\n");
        codigoFonte.append("FIM_ALGORITMO.\n");

        saidaEsperada.append("Menu de Opções\n");
        saidaEsperada.append("1 - Cadastrar contas\n");
        saidaEsperada.append("2 - Visualizar todas as contas de determinado cliente\n");
        saidaEsperada.append("3 - Excluir conta de menor saldo\n");
        saidaEsperada.append("4 - Sair\n");
        saidaEsperada.append("Digite sua opção\n");

        entrada.append("1\n");

        saidaEsperada.append("Digite o número da conta a ser incluída\n");
        entrada.append("24647\n");
        saidaEsperada.append("Digite o nome do cliente\n");
        entrada.append("Antonio\n");
        saidaEsperada.append("Digite o saldo do cliente\n");
        entrada.append("99\n");
        saidaEsperada.append("Conta cadastrada com sucesso\n");

        saidaEsperada.append("Menu de Opções\n");
        saidaEsperada.append("1 - Cadastrar contas\n");
        saidaEsperada.append("2 - Visualizar todas as contas de determinado cliente\n");
        saidaEsperada.append("3 - Excluir conta de menor saldo\n");
        saidaEsperada.append("4 - Sair\n");
        saidaEsperada.append("Digite sua opção\n");

        entrada.append("1\n");

        saidaEsperada.append("Digite o número da conta a ser incluída\n");
        entrada.append("29505\n");
        saidaEsperada.append("Digite o nome do cliente\n");
        entrada.append("Vinicius\n");
        saidaEsperada.append("Digite o saldo do cliente\n");
        entrada.append("100\n");
        saidaEsperada.append("Conta cadastrada com sucesso\n");

        saidaEsperada.append("Menu de Opções\n");
        saidaEsperada.append("1 - Cadastrar contas\n");
        saidaEsperada.append("2 - Visualizar todas as contas de determinado cliente\n");
        saidaEsperada.append("3 - Excluir conta de menor saldo\n");
        saidaEsperada.append("4 - Sair\n");
        saidaEsperada.append("Digite sua opção\n");

        entrada.append("2\n");

        saidaEsperada.append("Digite o nome do cliente a ser consultado\n");
        entrada.append("Antonio\n");
        saidaEsperada.append("24647 - 99\n");

        saidaEsperada.append("Menu de Opções\n");
        saidaEsperada.append("1 - Cadastrar contas\n");
        saidaEsperada.append("2 - Visualizar todas as contas de determinado cliente\n");
        saidaEsperada.append("3 - Excluir conta de menor saldo\n");
        saidaEsperada.append("4 - Sair\n");
        saidaEsperada.append("Digite sua opção\n");

        entrada.append("2\n");

        saidaEsperada.append("Digite o nome do cliente a ser consultado\n");
        entrada.append("Vinicius\n");
        saidaEsperada.append("29505 - 100\n");

        saidaEsperada.append("Menu de Opções\n");
        saidaEsperada.append("1 - Cadastrar contas\n");
        saidaEsperada.append("2 - Visualizar todas as contas de determinado cliente\n");
        saidaEsperada.append("3 - Excluir conta de menor saldo\n");
        saidaEsperada.append("4 - Sair\n");
        saidaEsperada.append("Digite sua opção\n");

        entrada.append("3\n");

        saidaEsperada.append("Conta excluída com sucesso\n");

        saidaEsperada.append("Menu de Opções\n");
        saidaEsperada.append("1 - Cadastrar contas\n");
        saidaEsperada.append("2 - Visualizar todas as contas de determinado cliente\n");
        saidaEsperada.append("3 - Excluir conta de menor saldo\n");
        saidaEsperada.append("4 - Sair\n");
        saidaEsperada.append("Digite sua opção\n");

        entrada.append("2\n");

        saidaEsperada.append("Digite o nome do cliente a ser consultado\n");
        entrada.append("Antonio\n");
        saidaEsperada.append("Não existe conta cadastrada PARA este cliente\n");

        saidaEsperada.append("Menu de Opções\n");
        saidaEsperada.append("1 - Cadastrar contas\n");
        saidaEsperada.append("2 - Visualizar todas as contas de determinado cliente\n");
        saidaEsperada.append("3 - Excluir conta de menor saldo\n");
        saidaEsperada.append("4 - Sair\n");
        saidaEsperada.append("Digite sua opção\n");

        entrada.append("2\n");

        saidaEsperada.append("Digite o nome do cliente a ser consultado\n");
        entrada.append("Vinicius\n");
        saidaEsperada.append("29505 - 100\n");

        saidaEsperada.append("Menu de Opções\n");
        saidaEsperada.append("1 - Cadastrar contas\n");
        saidaEsperada.append("2 - Visualizar todas as contas de determinado cliente\n");
        saidaEsperada.append("3 - Excluir conta de menor saldo\n");
        saidaEsperada.append("4 - Sair\n");
        saidaEsperada.append("Digite sua opção\n");

        entrada.append("4\n");

        adicionarCasoDeTeste();

        /////////////////////
        // Autoria própria //
        /////////////////////

        // APositivoExpressao
        // ANegacaoExpressao

        novoCasoDeTeste();

        codigoFonte.append("ALGORITMO\n");
        codigoFonte.append("DECLARE numero, positivo NUMERICO mudou logico\n");
        codigoFonte.append("LEIA numero\n");
        codigoFonte.append("positivo <- +numero\n");
        codigoFonte.append("ESCREVA positivo\n");
        codigoFonte.append("mudou <- numero <> positivo\n");
        codigoFonte.append("SE NAO mudou\n");
        codigoFonte.append("ENTAO ESCREVA \"A operação positivo NÃO MUDOU o valor do número\"\n");
        codigoFonte.append("SENAO ESCREVA \"A operação positivo MUDOU o valor do número\"\n");
        codigoFonte.append("FIM_ALGORITMO");

        entrada.append("3\n");

        saidaEsperada.append("3\n");
        saidaEsperada.append("A operação positivo NÃO MUDOU o valor do número\n");

        adicionarCasoDeTeste();

        return casosDeTeste;
    }
}