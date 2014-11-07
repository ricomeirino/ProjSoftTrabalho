package controller;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import modelo.Atividade;
import modelo.Projeto;
import modelo.Recurso;

import org.junit.Test;
public class RelatorioGanttControlTest {

	private RelatorioGanttControl relatorioGanttControl = new RelatorioGanttControl();
	@Test
	public void listaAtividadesAlocacoes(){
		Projeto projeto = new Projeto();
		projeto.setIdProjeto(20);
		
		List<Atividade> expected = new ArrayList<Atividade>();

		Atividade atividade = new Atividade();
		atividade.setIdAtividade(31);
		Recurso recurso = new Recurso();
		recurso.setIdRecurso(24);
		atividade.setRecurso(recurso);
		expected.add(atividade);

		atividade = new Atividade();
		atividade.setIdAtividade(32);
		recurso = new Recurso();
		recurso.setIdRecurso(25);
		atividade.setRecurso(recurso);
		expected.add(atividade);

		atividade = new Atividade();
		atividade.setIdAtividade(33);
		recurso = null;
		atividade.setRecurso(recurso);
		expected.add(atividade);
		
		atividade = new Atividade();
		atividade.setIdAtividade(34);
		recurso = null;
		atividade.setRecurso(recurso);
		expected.add(atividade);
		
		atividade = new Atividade();
		atividade.setIdAtividade(35);
		recurso = new Recurso();
		recurso.setIdRecurso(28);
		atividade.setRecurso(recurso);
		expected.add(atividade);

		List<Atividade> result = relatorioGanttControl.listaAtividadesAlocacoes(projeto);

		assertEquals(expected.size(), result.size());

		for (int i = 0; i < expected.size(); i++) {

			
			assertEquals(expected.get(i).getIdAtividade(), result.get(i)
					.getIdAtividade());

			if (expected.get(i).getRecurso() != null){
				assertEquals(expected.get(i).getRecurso().getIdRecurso(),
						result.get(i).getRecurso().getIdRecurso());	
			}else{
				assertTrue(result.get(i).getRecurso() == null);	
			}
			

			
		}
		
	}
}
