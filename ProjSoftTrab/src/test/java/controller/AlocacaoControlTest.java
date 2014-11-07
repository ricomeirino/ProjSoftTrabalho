package controller;

import static org.junit.Assert.*;
import org.junit.Test;
import java.util.ArrayList;
import java.util.List;

import modelo.Alocacao;
import modelo.Atividade;
import modelo.Projeto;
import modelo.Recurso;



import util.Mensagem;
import dao.AlocacaoDAO;
import dao.AtividadeDAO;

public class AlocacaoControlTest {

	private AlocacaoControl alocacaoControl = new AlocacaoControl();

	@Test
	public void atividadesDoProjetoSemAlocacaoTest() {
		Projeto projetoSelecionado = new Projeto();
		projetoSelecionado.setIdProjeto(20);

		// Lista com os idAtividades das atividades esperadas
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(33);
		expected.add(34);

		List<Atividade> result = alocacaoControl
				.atividadesDoProjetoSemAlocacao(projetoSelecionado);

		assertEquals(expected.size(), result.size());

		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).intValue(), result.get(i)
					.getIdAtividade());
		}

	}

	@Test
	public void atividadesDoProjetoSemAlocacaoTest2() {
		Projeto projetoSelecionado = new Projeto();
		projetoSelecionado.setIdProjeto(20);

		// Lista com os idAtividades das atividades esperadas
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(33);
		expected.add(34);

		List<Atividade> result = alocacaoControl
				.atividadesDoProjetoSemAlocacao(projetoSelecionado);

		assertEquals(expected.size(), result.size());

		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).intValue(), result.get(i)
					.getIdAtividade());
		}

	}	
	
	@Test
	public void listaRecursosDesalocadosTest() {
		// Lista com os idRecurso dos recurso desalocados esperados
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(12);
		expected.add(16);
		expected.add(17);
		expected.add(20);
		expected.add(21);
		expected.add(23);
		expected.add(22);
		expected.add(6);
		expected.add(26);
		expected.add(27);

		List<Recurso> result = alocacaoControl.listaRecursosDesalocados();

		assertEquals(expected.size(), result.size());

		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).intValue(), result.get(i)
					.getIdRecurso());
		}

	}

	@Test
	public void listaAlocacoesTest() {
		Projeto projetoSelecionado = new Projeto();
		projetoSelecionado.setIdProjeto(20);
		// Lista com os idAlocacao das alocações esperadas
		List<Integer> expected = new ArrayList<Integer>();
		expected.add(24);
		expected.add(25);
		expected.add(26);

		List<Alocacao> result = alocacaoControl
				.listaAlocacoes(projetoSelecionado);

		assertEquals(expected.size(), result.size());

		for (int i = 0; i < expected.size(); i++) {
			assertEquals(expected.get(i).intValue(), result.get(i)
					.getIdAlocacao());
		}

	}

	@Test
	public void insertAlocacaoTest() {
		Atividade atividade = null;
		Recurso recurso = null;

		// Inserir alocacao sem atividade
		recurso = new Recurso();
		recurso.setIdRecurso(27);

		int codigoMsg = 2;
		String msg = "Falha na alocação do recurso. Tente novamente";

		Mensagem expected = new Mensagem(codigoMsg, msg);

		Mensagem result = alocacaoControl.insertAlocacao(atividade, recurso);

		assertEquals(expected.getCodigo(), result.getCodigo());
		
		// Inserir alocacao sem recurso
		recurso = null;
		atividade = new Atividade();
		atividade.setIdAtividade(33);

		result = alocacaoControl.insertAlocacao(atividade, recurso);

		assertEquals(expected.getCodigo(), result.getCodigo());
		
		// Inserir alocação de um recurso inexistente
		atividade = new Atividade();
		atividade.setIdAtividade(33);
		recurso = new Recurso();
		recurso.setIdRecurso(999);

		result = alocacaoControl.insertAlocacao(atividade, recurso);

		assertEquals(expected.getCodigo(), result.getCodigo());
		
		// Inserir alocação de uma atividade inexistente
		atividade = new Atividade();
		atividade.setIdAtividade(999);
		recurso = new Recurso();
		recurso.setIdRecurso(27);

		result = alocacaoControl.insertAlocacao(atividade, recurso);

		assertEquals(expected.getCodigo(), result.getCodigo());
		
		// Inserir alocação de um recurso já alocado
		atividade = new Atividade();
		atividade.setIdAtividade(34);
		recurso = new Recurso();
		recurso.setIdRecurso(24);

		result = alocacaoControl.insertAlocacao(atividade, recurso);

		assertEquals(expected.getCodigo(), result.getCodigo());
				
		// Inserir alocação de um atividade já alocada
		atividade = new Atividade();
		atividade.setIdAtividade(31);
		recurso = new Recurso();
		recurso.setIdRecurso(26);

		result = alocacaoControl.insertAlocacao(atividade, recurso);

		assertEquals(expected.getCodigo(), result.getCodigo());
						
		//Inserir um recurso não alocado a uma atividade sem recurso alocado
		atividade = new Atividade();
		atividade.setIdAtividade(34);
		recurso = new Recurso();
		recurso.setIdRecurso(26);

		expected.setCodigo(0);
		expected.setMsg("Alocação executada com sucesso");

		
		result = alocacaoControl.insertAlocacao(atividade, recurso);

		assertEquals(expected.getCodigo(), result.getCodigo());

		AlocacaoDAO alocacaoDAO = new AlocacaoDAO();
		alocacaoDAO.deleteAlocacaoAtivRec(atividade, recurso);
	}
	
	@Test
	public void deleteAlocacao(){

		// deletar alocação de atividade finalizada
		Alocacao alocacao;
		
		alocacao = new Alocacao();
		alocacao.setIdAlocacao(24);

		AtividadeDAO atividadeDAO = new AtividadeDAO();
		Atividade atividade = atividadeDAO.buscaAtividadePeloId(31);

		alocacao.setAtividade(atividade);
		
		int codigoMsg = 6;
		String msg = "Recurso não pode ser desalocado pois a atividade está finalizada.";

		Mensagem expected = new Mensagem(codigoMsg, msg);

		Mensagem result = alocacaoControl.deleteAlocacao(alocacao);
		
		assertEquals(expected.getCodigo(), result.getCodigo());

		
	}
}
