package controller;

import java.util.List;

import dao.AtividadeDAO;
import modelo.Atividade;
import modelo.Projeto;

public class RelatorioGanttControl {

	public List<Atividade> listaAtividadesAlocacoes(Projeto projetoSelecionado){
		AtividadeDAO atividadeDAO = new AtividadeDAO();
		List<Atividade> atividades = atividadeDAO.listaAtividadesAlocacoes(projetoSelecionado);
		return atividades;
	}
}
