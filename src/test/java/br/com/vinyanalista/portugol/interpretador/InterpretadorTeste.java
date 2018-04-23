package br.com.vinyanalista.portugol.interpretador;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.*;

import br.com.vinyanalista.portugol.base.lexer.LexerException;
import br.com.vinyanalista.portugol.base.parser.ParserException;
import br.com.vinyanalista.portugol.interpretador.analise.ErroSemantico;

public class InterpretadorTeste {
	Interpretador interpretador;

	@Test
	public void analisarExecucaoDoExemplo1() throws IOException, LexerException, ParserException, ErroSemantico {
		String programaFonte = Exemplo.ESTRUTURA_SEQUENCIAL.getProgramaFonte();
		String entrada = "1\n2\n";
		TerminalDeTeste terminalDeTeste = new TerminalDeTeste(entrada);
		Interpretador interpretador = new Interpretador(terminalDeTeste);
		interpretador.analisar(programaFonte);
		interpretador.executar();
		System.out.println(terminalDeTeste.getSaida());
		assertNotNull(terminalDeTeste.getSaida());
	}

}