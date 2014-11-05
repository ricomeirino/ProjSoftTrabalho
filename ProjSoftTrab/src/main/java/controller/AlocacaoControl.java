package controller;

import java.util.List;

import util.Mensagem;
import modelo.Alocacao;
import modelo.Atividade;
import modelo.Projeto;
import modelo.Recurso;
import dao.AlocacaoDAO;
import dao.AtividadeDAO;
import dao.RecursoDAO;

public class AlocacaoControl {


	public List<Atividade> atividadesDoProjetoSemAlocacao(Projeto projetoSelecionado){
		AtividadeDAO atividadeDAO = new AtividadeDAO();
		return atividadeDAO.atividadesDoProjetoSemAlocacao(projetoSelecionado);
	}
	
	public List<Recurso> listaRecursosDesalocados(){
		RecursoDAO recursoDAO = new RecursoDAO();
		return recursoDAO.listaRecursosDesalocados();
	}
	
	public List<Alocacao> listaAlocacoes(Projeto projetoSelecionado){
		AlocacaoDAO alocacaoDAO = new AlocacaoDAO();
		return alocacaoDAO.listaAlocacoes(projetoSelecionado);
	}
	
	public Mensagem insertAlocacao(Atividade atividade, Recurso recurso){
		String msg = null;
		int codigoMsg;
		AlocacaoDAO alocacaoDAO = new AlocacaoDAO();
		boolean inseriu = alocacaoDAO.insertAlocacao(atividade, recurso);
		if (inseriu){
			codigoMsg = 0;
			msg = "Alocação executada com sucesso";	
		}else{
			codigoMsg = 2;
			msg = "Falha na alocação do recurso. Tente novamente";
		}
		
		Mensagem mensagem = new Mensagem(codigoMsg, msg);

		return mensagem;
	}
	
	public Mensagem deleteAlocacao(Alocacao alocacao){
		String msg = null;
		int codigoMsg;
		
		// verificar se a atividade está encerrada, se estiver não pode desalocar
		if(alocacao.getAtividade().isFinalizada()){
			codigoMsg = 6;
			msg = "Recurso não pode ser desalocado pois a atividade está finalizada.";
		} else {
			AlocacaoDAO alocacaoDAO = new AlocacaoDAO();
			boolean excluiu = alocacaoDAO.deleteAlocacao(alocacao);
			if (excluiu){
				codigoMsg = 0;
				msg = "Alocação desfeita com sucesso";	
			}else{
				codigoMsg = 2;
				msg = "Falha na dasalocação do recurso. Tente novamente";
			}

		}
		
		Mensagem mensagem = new Mensagem(codigoMsg, msg);

		return mensagem;
	}	
}
