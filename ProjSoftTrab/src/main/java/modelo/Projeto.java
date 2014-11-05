package modelo;

import java.io.Serializable;
import java.util.List;

public class Projeto implements Serializable{

	private static final long serialVersionUID = 57307761264967236L;

	private int idProjeto;
	private String codigo;
	private String nome;
	private List<Atividade> atividades;
	public int getIdProjeto() {
		return idProjeto;
	}
	public void setIdProjeto(int idProjeto) {
		this.idProjeto = idProjeto;
	}
	public String getCodigo() {
		return codigo;
	}
	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public List<Atividade> getAtividades() {
		return atividades;
	}
	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}
	
	
}
