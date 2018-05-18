package br.com.caelum.matematica;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class MatematicaMalucaTest {

	@Test
	public void testaNumeroMaiorQueTrinta() {
		MatematicaMaluca matematica = new MatematicaMaluca();
		
		int retornoEsperado = 160;
		
		assertEquals(retornoEsperado, matematica.contaMaluca(40));
	}
	
	@Test
	public void testaNumeroMaiorQueDezMenorQueTrinta() {
		MatematicaMaluca matematica = new MatematicaMaluca();
		
		int retornoEsperado = 60;
		
		assertEquals(retornoEsperado, matematica.contaMaluca(20));
	}
	
	@Test
	public void testaNumeroMenorQueDez() {
		MatematicaMaluca matematica = new MatematicaMaluca();
		
		int retornoEsperado = 10;
		
		assertEquals(retornoEsperado, matematica.contaMaluca(5));
	}
	
	@Test
	public void testaNumeroDez() {
		MatematicaMaluca matematica = new MatematicaMaluca();
		
		int retornoEsperado = 20;
		
		assertEquals(retornoEsperado, matematica.contaMaluca(10));
	}
	
	@Test
	public void testaNumeroTrinta() {
		MatematicaMaluca matematica = new MatematicaMaluca();
		
		int retornoEsperado = 90;
		
		assertEquals(retornoEsperado, matematica.contaMaluca(30));
	}
}
