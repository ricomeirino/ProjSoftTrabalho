package controller;

import java.util.List;

import modelo.Atividade;
import modelo.Projeto;
import util.Mensagem;
import util.ValidacaoDatas;
import dao.AlocacaoDAO;
import dao.AtividadeDAO;

public class CadastroAtividadesControl {

	public List<Atividade> atividadesDoProjeto(Projeto projetoSelecionado) {
		AtividadeDAO atividadeDAO = new AtividadeDAO();
		return atividadeDAO.atividadesDoProjeto(projetoSelecionado);
	}

	public Mensagem insereAtividade(Atividade atividadeNova, int projetoId) {
		String msg = null;
		AtividadeDAO atividadeDAO = new AtividadeDAO();

		// testar se existe atividade com mesmo código no projeto
		Atividade atividade = atividadeDAO.buscaAtividadePorCodigoMesmoProjeto(
				atividadeNova.getCodigo(), projetoId);

		int codigoMsg;
		Mensagem mensagem = null;
		
		if (!ValidacaoDatas.dataInicioMenorIgualDataFim(
				atividadeNova.getInicio(), atividadeNova.getFim())) {
			codigoMsg = 1;
			msg = "Atividade com código "
					+ atividadeNova.getCodigo()
					+ " não pode ser inserida pois a data de inicio é posterior a data de fim.";
			mensagem = new Mensagem(codigoMsg, msg);
			return mensagem;
		}		
		
		if (atividade != null) {
			codigoMsg = 1;
			msg = "Atividade com Código " + atividadeNova.getCodigo()
					+ " já existe no projeto.";
		} else {
			boolean inseriu = atividadeDAO.insertAtividade(atividadeNova,
					projetoId);

			if (inseriu) {
				codigoMsg = 0;
				msg = "Atividade inserido com sucesso";
				// atividadeNova =
				// atividadeDAO.buscaProjetoPorCodigoMesmoProjeto(atividadeNova.getCodigo(),
				// projetoId);

			} else {
				codigoMsg = 2;
				msg = "Falha na inclusão da atividade. Tente novamente.";
			}
		}

		mensagem = new Mensagem(codigoMsg, msg);

		return mensagem;
	}

	public Mensagem removeAtividade(Atividade atividadeRemovida) {
		String msg = null;
		AtividadeDAO atividadeDAO = new AtividadeDAO();
		int codigoMsg;

		// testar se atividade está finalizada - se estiver não pode remover
		if (atividadeRemovida.isFinalizada()) {
			codigoMsg = 5;
			msg = "Atividade com Código " + atividadeRemovida.getCodigo()
					+ " não pode ser removida pois já está finalizada.";

		} else {
			// testar se atividade está alocada - se estiver não pode remover

			AlocacaoDAO alocacaoDAO = new AlocacaoDAO();
			boolean recursoAlocado = alocacaoDAO
					.atividadeEstaAlocada(atividadeRemovida);

			if (recursoAlocado) {
				codigoMsg = 5;
				msg = "Atividade com Código " + atividadeRemovida.getCodigo()
						+ " não pode ser removida pois possui recurso alocado.";
			} else {
				// remover atividade
				boolean retorno = atividadeDAO
						.deleteAtividade(atividadeRemovida);
				if (retorno) {
					codigoMsg = 0;
					msg = "Atividade excluída com sucesso";
				} else {
					codigoMsg = 3;
					msg = "Falha na exclusão da atividade. Tente novamente.";
				}

			}
		}

		Mensagem mensagem = new Mensagem(codigoMsg, msg);

		return mensagem;
	}

	public Mensagem atualizaAtividade(Atividade atividadeEditada) {
		String msg = null;
		Mensagem mensagem = null;
		int codigoMsg;
		AtividadeDAO atividadeDAO = new AtividadeDAO();

		AlocacaoDAO alocacaoDAO = new AlocacaoDAO();
		boolean retorno = alocacaoDAO.atividadeEstaAlocada(atividadeEditada);
		if (!retorno && atividadeEditada.isFinalizada()) {
			codigoMsg = 1;
			msg = "Atividade com código "
					+ atividadeEditada.getCodigo()
					+ " não pode ser finalizada pois não possui recurso alocado.";
			mensagem = new Mensagem(codigoMsg, msg);
			return mensagem;
		}
		// TODO testar dadas na atualização

		if (!ValidacaoDatas.dataInicioMenorIgualDataFim(
				atividadeEditada.getInicio(), atividadeEditada.getFim())) {
			codigoMsg = 1;
			msg = "Atividade com código "
					+ atividadeEditada.getCodigo()
					+ " não pode ser alterada pois a data de inicio é posterior a data de fim.";
			mensagem = new Mensagem(codigoMsg, msg);
			return mensagem;
		}

		// testar se existe atividade com mesmo código no projeto
		Atividade atividade = atividadeDAO.buscaAtividadePorCodigoMesmoProjeto(
				atividadeEditada.getCodigo(), atividadeEditada.getProjeto()
						.getIdProjeto());

		if (atividade == null
				|| atividade.getIdAtividade() == atividadeEditada
						.getIdAtividade()) {
			// pode alterar
			retorno = atividadeDAO.atualizaAtividade(atividadeEditada);

			if (retorno) {
				codigoMsg = 0;
				msg = "Atividade atualizada com sucesso";
			} else {
				codigoMsg = 4;
				msg = "Falha na atualização da atividade. Tente novamente.";
			}

		} else { // se não for a mesma atividade -> não pode alterar pois o
					// código já existe
			codigoMsg = 1;
			msg = "Atividade com código " + atividadeEditada.getCodigo()
					+ " já existe no projeto.";
		}

		mensagem = new Mensagem(codigoMsg, msg);

		return mensagem;
	}

}
