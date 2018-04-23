package br.com.vinyanalista.portugol.interpretador;

import java.awt.Color;
import java.io.IOException;
import java.util.StringTokenizer;

import br.com.vinyanalista.portugol.base.lexer.LexerException;
import br.com.vinyanalista.portugol.base.parser.ParserException;
import br.com.vinyanalista.portugol.interpretador.analise.ErroSemantico;

public class TerminalDeTeste extends Terminal {
	private StringTokenizer entrada;
	private StringBuilder saida;
	
	public TerminalDeTeste(String entrada) {
		this.entrada = new StringTokenizer(entrada, "\n");
		saida = new StringBuilder();
	}

	@Override
	public void erro(String mensagemDeErro) {
		saida.append(mensagemDeErro).append("\n");
	}

	@Override
	protected void escrever(String mensagem) {
		saida.append(mensagem).append("\n");
	}

	@Override
	public void informacao(String mensagemDeInformacao) {
		saida.append(mensagemDeInformacao).append("\n");
	}

	@Override
	protected String ler() {
		String dadoLido = entrada.nextToken(); 
		return dadoLido;
	}

	@Override
	public void limpar() {
		//saida = new StringBuilder();
	}

	@Override
	protected void mudarCor(Color cor) {
	}
	
	public String getSaida() {
		return saida.toString();
	}
	
	public static void main(String[] args) throws IOException, LexerException, ParserException, ErroSemantico {
		String programaFonte = Exemplo.ESTRUTURA_SEQUENCIAL.getProgramaFonte();
		String entrada = "1\n2\n";
		TerminalDeTeste terminalDeTeste = new TerminalDeTeste(entrada);
		Interpretador interpretador = new Interpretador(terminalDeTeste);
		interpretador.analisar(programaFonte);
		interpretador.executar();
		String saida = terminalDeTeste.getSaida();
		System.out.println(saida);
	}

}