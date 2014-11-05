package modelo;

import java.io.Serializable;

/**Classe que faz o mapeamento da tabela ocupacao da base de dados
 * @author Ricardo
 * 
 */
public class Ocupacao implements Serializable{

	private static final long serialVersionUID = 4747424433665643510L;

	private int idOcupacao;
	private String nome;
	public int getIdOcupacao() {
		return idOcupacao;
	}
	public void setIdOcupacao(int idOcupacao) {
		this.idOcupacao = idOcupacao;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
