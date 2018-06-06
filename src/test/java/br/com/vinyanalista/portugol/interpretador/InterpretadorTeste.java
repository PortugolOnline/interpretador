package br.com.vinyanalista.portugol.interpretador;

import static org.junit.Assert.*;

import java.io.*;

import org.junit.*;

public class InterpretadorTeste {
	private final InputStream entradaDoSistema = System.in;
	private final PrintStream saidaDoSistema = System.out;

	private ByteArrayInputStream entradaDoTeste;
	private ByteArrayOutputStream saidaDoTeste;

	@Before
	public void capturarSaida() {
		saidaDoTeste = new ByteArrayOutputStream();
		System.setOut(new PrintStream(saidaDoTeste));
	}

	private void fornecerEntrada(String data) {
		entradaDoTeste = new ByteArrayInputStream(data.getBytes());
		System.setIn(entradaDoTeste);
	}

	private String obterSaida() {
		return saidaDoTeste.toString();
	}

	@After
	public void restaurarEntradaSaidaDoSistema() {
		System.setIn(entradaDoSistema);
		System.setOut(saidaDoSistema);
	}

	@Test
	public void out() {
		final String teste = "oi!";
		fornecerEntrada(teste);
		
		System.out.print(teste);
		
		assertEquals(teste, obterSaida());
	}
}