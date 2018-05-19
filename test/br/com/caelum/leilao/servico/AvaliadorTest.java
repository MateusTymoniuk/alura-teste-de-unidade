package br.com.caelum.leilao.servico;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import br.com.caelum.leilao.builder.LeilaoBuilder;
import br.com.caelum.leilao.dominio.Lance;
import br.com.caelum.leilao.dominio.Leilao;
import br.com.caelum.leilao.dominio.Usuario;
import br.com.caelum.leilao.servico.Avaliador;

public class AvaliadorTest {

	private Avaliador leiloeiro;
	private Usuario joao;
	private Usuario jose;
	private Usuario maria;

	@Before
	public void criaCenario() {
		joao = new Usuario("João");
		jose = new Usuario("José");
		maria = new Usuario("Maria");
		criaAvaliador();
	}
	
	@After
	public void finaliza() {
		System.out.println("fim");
	}
	
	@BeforeClass
	public static void testandoBeforeClass() {
		System.out.println("before class");
	}

	@AfterClass
	public static void testandoAfterClass() {
		System.out.println("after class");
	}

	@Test
	public void deveEntenderLancesEmOrdemCrescente() {
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 novo")
				.lance(maria, 250.0)
				.lance(joao, 300.0)
				.lance(jose, 400.0)
				.build();

		leiloeiro.avalia(leilao);

		assertThat(leiloeiro.getMenorLance(), equalTo(250.0));
		assertThat(leiloeiro.getMaiorLance(), equalTo(400.0));
	}

	@Test
	public void verificaValorMedioDosLances() {
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 novo").lance(maria, 250.0).lance(joao, 300.0)
				.lance(jose, 400.0).build();

		double mediaEsperada = 316.6;
		assertEquals(mediaEsperada, leiloeiro.calculaValorMedioDosLances(leilao), 0.1);
	}

	@Test
	public void deveEntenderLancesEmOrdemAleatoria() {
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 novo")
				.lance(maria, 1000.0)
				.lance(joao, 300.0)
				.lance(maria, 100.0)
				.lance(joao, 4800.0)
				.lance(maria, 1200.0)
				.lance(joao, 30.0)
				.lance(maria, 1765.0)
				.lance(joao, 250.0)
				.build();

		leiloeiro.avalia(leilao);

		double maiorEsperado = 4800.0;
		double menorEsperado = 30.0;

		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEntenderLancesEmOrdemDecrescente() {
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 novo")
				.lance(maria, 500.0)
				.lance(joao, 400.0)
				.lance(jose, 250.0)
				.build();

		leiloeiro.avalia(leilao);

		double maiorEsperado = 500;
		double menorEsperado = 250;

		assertEquals(maiorEsperado, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(menorEsperado, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEntenderLeilaoComApenasUmLance() {
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 novo")
				.lance(joao, 1000.0)
				.build();

		leiloeiro.avalia(leilao);

		assertEquals(1000.0, leiloeiro.getMaiorLance(), 0.00001);
		assertEquals(1000.0, leiloeiro.getMenorLance(), 0.00001);
	}

	@Test
	public void deveEncontrarOsTresMaioresLances() {
		
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 novo")
				.lance(joao, 100.0)
				.lance(maria, 200.0)
				.lance(joao, 300.0)
				.lance(maria, 400.0)
				.build();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertThat(maiores.size(), equalTo(3));
		assertThat(maiores, hasItems(
                new Lance(maria, 400), 
                new Lance(joao, 300),
                new Lance(maria, 200)
        ));
	}

	@Test
	public void encontraOsTresMaioresDeCincoLances() {
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 novo")
				.lance(joao, 100.0)
				.lance(maria, 2000.0)
				.lance(joao, 300.0)
				.lance(maria, 250.0)
				.lance(joao, 700.0)
				.build();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();
		assertThat(maiores.size(), equalTo(3));
		assertThat(maiores, hasItems(
                new Lance(maria, 2000.0), 
                new Lance(joao, 700.0),
                new Lance(joao, 300.0)
        ));
	}

	@Test
	public void encontraOsTresMaioresDeDoisLances() {
		
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 novo")
				.lance(joao, 100.0)
				.lance(maria, 250.0)
				.build();

		leiloeiro.avalia(leilao);

		List<Lance> maiores = leiloeiro.getTresMaiores();
		
		assertThat(maiores.size(), equalTo(2));
		assertThat(maiores, hasItems(
                new Lance(maria, 250.0), 
                new Lance(joao, 100.0)
        ));
	}

	@Test(expected=RuntimeException.class)
	public void trataLeilaoSemLance() {
		Leilao leilao = new LeilaoBuilder().para("Playstation 3 novo")
				.build();

		leiloeiro.avalia(leilao);
	}

	private void criaAvaliador() {
		this.leiloeiro = new Avaliador();
	}
}
