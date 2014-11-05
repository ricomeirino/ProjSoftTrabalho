package bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Ocupacao;
import modelo.Recurso;

import org.primefaces.event.RowEditEvent;

import util.Mensagem;
import controller.CadastroRecursoControl;

@ManagedBean
@ViewScoped
public class CadastroRecursoBean implements Serializable {

	private static final long serialVersionUID = 6902351400500019365L;

	private String matriculaNovo;
	private String nomeNovo;
	private int ocupacaoNovo;
	private List<Recurso> recursos;

	private List<Recurso> recursosFiltrados;

	private List<Ocupacao> ocupacoes;

	public CadastroRecursoBean() {
		CadastroRecursoControl cadastroRecursoControl = new CadastroRecursoControl();
		this.recursos = cadastroRecursoControl.listaDeRecursos();
		this.ocupacoes = cadastroRecursoControl.listaDeOcupacoes();
	}

	public String getMatriculaNovo() {
		return matriculaNovo;
	}

	public void setMatriculaNovo(String matriculaNovo) {
		this.matriculaNovo = matriculaNovo;
	}

	public String getNomeNovo() {
		return nomeNovo;
	}

	public void setNomeNovo(String nomeNovo) {
		this.nomeNovo = nomeNovo;
	}

	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	public List<Recurso> getRecursosFiltrados() {
		return recursosFiltrados;
	}

	public void setRecursosFiltrados(List<Recurso> recursosFiltrados) {
		this.recursosFiltrados = recursosFiltrados;
	}

	public List<Ocupacao> getOcupacoes() {
		return ocupacoes;
	}

	public void setOcupacoes(List<Ocupacao> ocupacoes) {
		this.ocupacoes = ocupacoes;
	}

	public int getOcupacaoNovo() {
		return ocupacaoNovo;
	}

	public void setOcupacaoNovo(int ocupacaoNovo) {
		this.ocupacaoNovo = ocupacaoNovo;
	}

	public void onEdit(RowEditEvent event) {
		Recurso recursoEditado = (Recurso) event.getObject();

		CadastroRecursoControl cadastroRecursoControl = new CadastroRecursoControl();
		
		Mensagem mensagem = cadastroRecursoControl.atualizarRecurso(
				recursoEditado, recursoEditado.getOcupacao().getIdOcupacao());
		
		this.recursos = cadastroRecursoControl.listaDeRecursos();
		
		FacesMessage msg = new FacesMessage(mensagem.getMsg(),	((Recurso) event.getObject()).getNome());

		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edição de Recurso Cancelada",
				((Recurso) event.getObject()).getNome());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onDelete(Recurso recurso) {

		CadastroRecursoControl cadastroRecursoControl = new CadastroRecursoControl();
		Mensagem mensagem = cadastroRecursoControl.removeRecurso(recurso);

		if (mensagem.getCodigo() == 0) {
			this.recursos.remove(recurso);
			if (recursosFiltrados != null){
				recursosFiltrados.remove(recurso);
			}
		}

		FacesMessage msg = new FacesMessage(mensagem.getMsg(),
				recurso.getNome());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String insereRecurso() {

		CadastroRecursoControl cadastroRecursoControl = new CadastroRecursoControl();
		Recurso recursoNovo = new Recurso();
		recursoNovo.setMatricula(matriculaNovo);
		recursoNovo.setNome(nomeNovo);

		Mensagem mensagem = cadastroRecursoControl.insereRecurso(recursoNovo,
				ocupacaoNovo);
		this.recursos = cadastroRecursoControl.listaDeRecursos();

		if (mensagem.getCodigo() == 0) {
			this.matriculaNovo = "";
			this.nomeNovo = "";
		}

		FacesMessage msg = new FacesMessage(mensagem.getMsg(),
				recursoNovo.getNome());

		FacesContext.getCurrentInstance().addMessage(null, msg);

		return null;
	}

}
