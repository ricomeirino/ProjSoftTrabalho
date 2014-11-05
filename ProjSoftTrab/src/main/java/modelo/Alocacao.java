package modelo;

import java.io.Serializable;

public class Alocacao implements Serializable{

	private static final long serialVersionUID = -6972513115306344650L;
	
	private int idAlocacao;
	private Recurso recurso;
	private Atividade atividade;
	public int getIdAlocacao() {
		return idAlocacao;
	}
	public void setIdAlocacao(int idAlocacao) {
		this.idAlocacao = idAlocacao;
	}
	public Recurso getRecurso() {
		return recurso;
	}
	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}
	public Atividade getAtividade() {
		return atividade;
	}
	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}
	
	
}
