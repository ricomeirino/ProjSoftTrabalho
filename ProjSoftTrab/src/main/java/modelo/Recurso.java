package modelo;

import java.io.Serializable;

/**Classe que faz o mapeamento da tabela recurso da base de dados
 * @author Ricardo
 * 
 */

public class Recurso implements Serializable{

	private static final long serialVersionUID = 850386675495205851L;
	
	private int idRecurso;
	private String nome;
	private String matricula;
	private Ocupacao ocupacao;
	
	public int getIdRecurso() {
		return idRecurso;
	}
	public void setIdRecurso(int idRecurso) {
		this.idRecurso = idRecurso;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getMatricula() {
		return matricula;
	}
	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}
	public Ocupacao getOcupacao() {
		return ocupacao;
	}
	public void setOcupacao(Ocupacao ocupacao) {
		this.ocupacao = ocupacao;
	}
	
	
	
}
