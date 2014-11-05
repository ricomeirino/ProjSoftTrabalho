package modelo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
/**Classe que faz o mapeamento da tabela projeto da base de dados
 * @author Ricardo
 * 
 */
public class Atividade implements Serializable {

	private static final long serialVersionUID = -3649194778475658434L;

	private int idAtividade;
	private String codigo;
	private String nome;
	private Date inicio;
	private Date fim;
	private boolean finalizada;
	private Projeto projeto;
	
	private String inicioFormatada;
	private String fimFormatada;
	private String finalizadaFormatada;
	
	private Recurso recurso;
	
	public int getIdAtividade() {
		return idAtividade;
	}

	public void setIdAtividade(int idAtividade) {
		this.idAtividade = idAtividade;
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

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
		SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
		this.inicioFormatada = dt1.format(inicio);
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
		SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
		this.fimFormatada = dt1.format(fim);
	}

	public boolean isFinalizada() {
		return finalizada;
	}

	public void setFinalizada(boolean finalizada) {
		this.finalizada = finalizada;
		if (this.finalizada){
			this.finalizadaFormatada = "Sim";
		}else{
			this.finalizadaFormatada = "Não";
		}
	}

	public Projeto getProjeto() {
		return projeto;
	}

	public void setProjeto(Projeto projeto) {
		this.projeto = projeto;
	}

	public String getInicioFormatada() {
		return inicioFormatada;
	}

	public void setInicioFormatada(String inicioFormatada) {
		this.inicioFormatada = inicioFormatada;
	}

	public String getFimFormatada() {
		return fimFormatada;
	}

	public void setFimFormatada(String fimFormatada) {
		this.fimFormatada = fimFormatada;
	}

	public String getFinalizadaFormatada() {
		return finalizadaFormatada;
	}

	public void setFinalizadaFormatada(String finalizadaFormatada) {
		this.finalizadaFormatada = finalizadaFormatada;
		if (this.finalizadaFormatada.equals("Sim")){
			this.finalizada = true;
		}else{
			this.finalizada = false;
		}
	}

	public Recurso getRecurso() {
		return recurso;
	}

	public void setRecurso(Recurso recurso) {
		this.recurso = recurso;
	}
	
	

}
