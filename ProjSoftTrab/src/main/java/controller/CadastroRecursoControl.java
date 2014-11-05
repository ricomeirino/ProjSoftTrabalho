package controller;

import java.util.List;

import modelo.Ocupacao;
import modelo.Recurso;
import util.Mensagem;
import dao.AlocacaoDAO;
import dao.OcupacaoDAO;
import dao.RecursoDAO;

public class CadastroRecursoControl {
	public List<Recurso> listaDeRecursos() {
		RecursoDAO recursoDAO = new RecursoDAO();
		List<Recurso> recursos = recursoDAO.listaDeRecursos();
		return recursos;
	}
	
	public List<Ocupacao> listaDeOcupacoes() {
		OcupacaoDAO ocupacaoDAO = new OcupacaoDAO();
		List<Ocupacao> ocupacoes = ocupacaoDAO.listaDeOcupacoes();
		return ocupacoes;
	}
	
	
	public Mensagem removeRecurso(Recurso recurso) {
		String msg = null;

		// verificar se recurso está alocado
		AlocacaoDAO alocacaoDAO = new AlocacaoDAO();
		boolean recursoAlocado = alocacaoDAO.recursoEstaAlocado(recurso);
		int codigoMsg;
		
		if (recursoAlocado){
			codigoMsg = 5;
			msg = "Recurso não pode ser excluído pois está alocoado em uma tarefa";
		}else{
			RecursoDAO recursoDAO = new RecursoDAO();

			boolean retorno = recursoDAO.removeRecurso(recurso);
			
			if (retorno) {
				codigoMsg = 0;
				msg = "Recurso excluído com sucesso";
			} else {
				codigoMsg = 3;
				msg = "Falha na exclusão do recurso. Tente novamente.";
			}
			
		}
		

		Mensagem mensagem = new Mensagem(codigoMsg, msg);

		return mensagem;
	}
		
	public Mensagem insereRecurso(Recurso recursoNovo, int idOcupacao) {
		String msg = null;
		RecursoDAO recursoDAO = new RecursoDAO();
		
		// testar se existe projeto com mesmo código
		Recurso recurso = recursoDAO.buscaRecursoPorMatricula(recursoNovo.getMatricula());
		
		int codigoMsg;
		if (recurso != null) {
			codigoMsg = 1;
			msg = "Recurso com matrícula " + recursoNovo.getMatricula()
					+ " já existe.";
		} else {
			boolean inseriu = recursoDAO.insereRecurso(recursoNovo, idOcupacao);
			if (inseriu) {
				codigoMsg = 0;
				msg = "Recurso inserido com sucesso";
				//recursoNovo = recursoDAO.buscaRecursoPorMatricula(recursoNovo.getMatricula()); 
						
			} else {
				codigoMsg = 2;
				msg = "Falha na inclusão do recurso. Tente novamente.";
			}
		}

		Mensagem mensagem = new Mensagem(codigoMsg, msg);

		return mensagem;
	}
	
	public Mensagem atualizarRecurso(Recurso recursoEditado, int idOcupacao) {
		String msg = null;
		RecursoDAO recursoDAO = new RecursoDAO();
		
		// testar se existe recurso com a mesma matricula
		Recurso recursoNaBase = recursoDAO.buscaRecursoPorMatricula(recursoEditado.getMatricula()); 
		int codigoMsg;
		
		// se for o mesmo recurso ou não tiver recurso com a mesma matricula -> pode alterar
		
		if (recursoNaBase == null || recursoNaBase.getIdRecurso() == recursoEditado.getIdRecurso()){
			boolean retorno = recursoDAO.atualizaRecurso(recursoEditado, idOcupacao); 
			if (retorno) {
				codigoMsg = 0;
				msg = "Recurso atualizado com sucesso";
			} else {
				codigoMsg = 4;
				msg = "Falha na atualização do recurso. Tente novamente.";
			}

		}else{ // se não for o mesmo recurso -> não pode alterar pois a matrícula do recurso já existe
			codigoMsg = 1;
			msg = "Recurso com matrícula " + recursoEditado.getMatricula()
					+ " já existe.";
		}
				
		Mensagem mensagem = new Mensagem(codigoMsg, msg);

		return mensagem;		
	}
}
