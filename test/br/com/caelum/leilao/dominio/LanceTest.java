package br.com.caelum.leilao.dominio;

import org.junit.Test;

public class LanceTest {

	@Test(expected=IllegalArgumentException.class)
	public void deveLAncarExccecaoParaLanceVazio() {
		new Lance(new Usuario("João"), 0);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void deveLAncarExccecaoParaLanceNegativo() {
		new Lance(new Usuario("João"), -100.0);
	}
}
