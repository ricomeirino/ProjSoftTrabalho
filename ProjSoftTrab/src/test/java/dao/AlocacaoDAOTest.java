package dao;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import modelo.Alocacao;
import modelo.Atividade;
import modelo.Projeto;
import modelo.Recurso;

import org.junit.Test;

public class AlocacaoDAOTest {
	
	AlocacaoDAO alocacaoDAO = new AlocacaoDAO();

	// Teste para verificar um recurso não alocado
	@Test
	public void recursoEstaAlocadoTest() {

		Recurso recurso = new Recurso();
		recurso.setIdRecurso(6);

		assertFalse(alocacaoDAO.recursoEstaAlocado(recurso));

	}

	// Teste para verificar um recurso alocado
	@Test
	public void recursoEstaAlocadoTest2() {

		Recurso recurso = new Recurso();
		recurso.setIdRecurso(5);

		assertTrue(alocacaoDAO.recursoEstaAlocado(recurso));

	}

	// Teste para verificar atividade alocada
	@Test
	public void atividadeEstaAlocadaTest() {
		Atividade atividade = new Atividade();
		atividade.setIdAtividade(19);

		assertTrue(alocacaoDAO.atividadeEstaAlocada(atividade));
	}

	// Teste para verificar atividade não alocada
	@Test
	public void atividadeEstaAlocadaTest2() {
		Atividade atividade = new Atividade();
		atividade.setIdAtividade(33);

		assertFalse(alocacaoDAO.atividadeEstaAlocada(atividade));
	}

	// Teste de retorno da lista de alocações de um projeto
	@Test
	public void listaAlocacoes() {
		Projeto projeto = new Projeto();
		projeto.setIdProjeto(20);

		List<Alocacao> expected = new ArrayList<Alocacao>();
		Alocacao alocacao = new Alocacao();
		alocacao.setIdAlocacao(24);
		Atividade atividade = new Atividade();
		atividade.setIdAtividade(31);
		Recurso recurso = new Recurso();
		recurso.setIdRecurso(24);
		alocacao.setAtividade(atividade);
		alocacao.setRecurso(recurso);
		expected.add(alocacao);

		alocacao = new Alocacao();
		alocacao.setIdAlocacao(25);
		atividade = new Atividade();
		atividade.setIdAtividade(32);
		recurso = new Recurso();
		recurso.setIdRecurso(25);
		alocacao.setAtividade(atividade);
		alocacao.setRecurso(recurso);
		expected.add(alocacao);

		alocacao = new Alocacao();
		alocacao.setIdAlocacao(26);
		atividade = new Atividade();
		atividade.setIdAtividade(35);
		recurso = new Recurso();
		recurso.setIdRecurso(28);
		alocacao.setAtividade(atividade);
		alocacao.setRecurso(recurso);
		expected.add(alocacao);

		List<Alocacao> result = alocacaoDAO.listaAlocacoes(projeto);

		assertEquals(expected.size(), result.size());

		for (int i = 0; i < expected.size(); i++) {

			assertEquals(expected.get(i).getIdAlocacao(), result.get(i)
					.getIdAlocacao());

			assertEquals(expected.get(i).getAtividade().getIdAtividade(),
					result.get(i).getAtividade().getIdAtividade());

			assertEquals(expected.get(i).getRecurso().getIdRecurso(), result
					.get(i).getRecurso().getIdRecurso());
		}
	}

}
