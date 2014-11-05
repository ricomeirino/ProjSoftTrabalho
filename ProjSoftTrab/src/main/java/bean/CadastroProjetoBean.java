package bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Projeto;

import org.primefaces.event.RowEditEvent;

import util.Mensagem;
import controller.CadastroProjetoControl;

@ManagedBean
@ViewScoped
public class CadastroProjetoBean implements Serializable {

	private static final long serialVersionUID = -5121649751277121189L;

	private String codigoNovo;
	private String nomeNovo;

	private List<Projeto> projetos;

	private List<Projeto> projetosFiltrados;

	public CadastroProjetoBean() {
		CadastroProjetoControl cadastroProjetoControl = new CadastroProjetoControl();
		projetos = cadastroProjetoControl.listaDeProjetos();

	}

	public String getCodigoNovo() {
		return codigoNovo;
	}

	public void setCodigoNovo(String codigoNovo) {
		this.codigoNovo = codigoNovo;
	}

	public String getNomeNovo() {
		return nomeNovo;
	}

	public void setNomeNovo(String nomeNovo) {
		this.nomeNovo = nomeNovo;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public List<Projeto> getProjetosFiltrados() {
		return projetosFiltrados;
	}

	public void setProjetosFiltrados(List<Projeto> projetosFiltrados) {
		this.projetosFiltrados = projetosFiltrados;
	}

	public void onEdit(RowEditEvent event) {
		Projeto projetoEditado = (Projeto) event.getObject();

		CadastroProjetoControl cadastroProjetoControl = new CadastroProjetoControl();
		
		Mensagem mensagem = cadastroProjetoControl.atualizaProjeto(projetoEditado);
		
		FacesMessage msg = new FacesMessage(mensagem.getMsg(),
				((Projeto) event.getObject()).getNome());

		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edição de Projeto Cancelada",
				((Projeto) event.getObject()).getNome());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}


	public void onDelete(Projeto projeto) {

		CadastroProjetoControl cadastroProjetoControl = new CadastroProjetoControl();
		Mensagem mensagem = cadastroProjetoControl.deleteProjeto(projeto);

		if (mensagem.getCodigo() == 0) {
			this.projetos.remove(projeto);
			if (projetosFiltrados != null){
				this.projetosFiltrados.remove(projeto);
			}
			
		}

		FacesMessage msg = new FacesMessage(mensagem.getMsg(),
				projeto.getNome());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public String insereProjeto() {

		CadastroProjetoControl cadastroProjetoControl = new CadastroProjetoControl();
		Projeto projetoNovo = new Projeto();
		projetoNovo.setCodigo(codigoNovo);
		projetoNovo.setNome(nomeNovo);

		Mensagem mensagem = cadastroProjetoControl.insereProjeto(projetoNovo);

		this.projetos = cadastroProjetoControl.listaDeProjetos();

		if (mensagem.getCodigo() == 0) {
			this.codigoNovo = "";
			this.nomeNovo = "";
		}

		FacesMessage msg = new FacesMessage(mensagem.getMsg(),
				projetoNovo.getNome());

		FacesContext.getCurrentInstance().addMessage(null, msg);

		return null;
	}
}
