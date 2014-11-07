package bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Alocacao;
import modelo.Atividade;
import modelo.Projeto;
import modelo.Recurso;
import util.Mensagem;
import controller.AlocacaoControl;
import controller.CadastroProjetoControl;

@ManagedBean
@ViewScoped
public class AlocacaoRecursoBean implements Serializable {

	private static final long serialVersionUID = 3938437897363544592L;

	private String codigoProjetoSelecionado;
	private Projeto projetoSelecionado;
	private List<Projeto> projetos;

	private List<Atividade> atividades;
	private Atividade atividadeSelecionada;
	private List<Recurso> recursos;
	private Recurso recursoSelecionado;
	private List<Alocacao> alocacoes;

	public AlocacaoRecursoBean() {
		CadastroProjetoControl cadastroProjetoControl = new CadastroProjetoControl();
		this.projetos = cadastroProjetoControl.listaDeProjetos();

		AlocacaoControl alocacaoControl = new AlocacaoControl();
		if (!projetos.isEmpty()) {
			projetoSelecionado = projetos.get(0);
			this.atividades = alocacaoControl
					.atividadesDoProjetoSemAlocacao(projetoSelecionado);
			this.recursos = alocacaoControl.listaRecursosDesalocados();

			this.alocacoes = alocacaoControl.listaAlocacoes(projetoSelecionado);
		}
	}

	public String getCodigoProjetoSelecionado() {
		return codigoProjetoSelecionado;
	}

	public void setCodigoProjetoSelecionado(String codigoProjetoSelecionado) {
		this.codigoProjetoSelecionado = codigoProjetoSelecionado;
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public List<Recurso> getRecursos() {
		return recursos;
	}

	public void setRecursos(List<Recurso> recursos) {
		this.recursos = recursos;
	}

	public List<Alocacao> getAlocacoes() {
		return alocacoes;
	}

	public void setAlocacoes(List<Alocacao> alocacoes) {
		this.alocacoes = alocacoes;
	}

	public Atividade getAtividadeSelecionada() {
		return atividadeSelecionada;
	}

	public void setAtividadeSelecionada(Atividade atividadeSelecionada) {
		this.atividadeSelecionada = atividadeSelecionada;
	}

	public Recurso getRecursoSelecionado() {
		return recursoSelecionado;
	}

	public void setRecursoSelecionado(Recurso recursoSelecionado) {
		this.recursoSelecionado = recursoSelecionado;
	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public void trocaProjeto() {
		// buscar o projeto selecionado

		CadastroProjetoControl cadastroProjetoControl = new CadastroProjetoControl();
		this.projetoSelecionado = cadastroProjetoControl
				.buscaProjetoPorCodigo(codigoProjetoSelecionado);
		AlocacaoControl alocacaoControl = new AlocacaoControl();

		this.atividades = alocacaoControl
				.atividadesDoProjetoSemAlocacao(projetoSelecionado);
		this.recursos = alocacaoControl.listaRecursosDesalocados();

		this.alocacoes = alocacaoControl.listaAlocacoes(projetoSelecionado);

	}

	public void alocaRecurso() {
		if (atividadeSelecionada != null && recursoSelecionado != null) {
			AlocacaoControl alocacaoControl = new AlocacaoControl();
			Mensagem mensagem = alocacaoControl.insertAlocacao(
					atividadeSelecionada, recursoSelecionado);
			if (mensagem.getCodigo() == 0) {
				this.atividades = alocacaoControl
						.atividadesDoProjetoSemAlocacao(projetoSelecionado);
				this.recursos = alocacaoControl.listaRecursosDesalocados();

				this.alocacoes = alocacaoControl
						.listaAlocacoes(projetoSelecionado);
			}
		} else {
			String mensagem = null;
			mensagem = "Você precisa selecionar a atividade e o recurso que deseja alocar";
			FacesMessage msg = new FacesMessage(mensagem,
					"!!!    ATENÇÃO    !!!");

			FacesContext.getCurrentInstance().addMessage(null, msg);
		}

	}

	public void desalocar(Alocacao alocacao) {
		AlocacaoControl alocacaoControl = new AlocacaoControl();
		Mensagem mensagem = alocacaoControl.deleteAlocacao(alocacao);
		FacesMessage msg = null;
		if (mensagem.getCodigo() == 0) {
			this.atividades = alocacaoControl
					.atividadesDoProjetoSemAlocacao(projetoSelecionado);
			this.recursos = alocacaoControl.listaRecursosDesalocados();

			this.alocacoes = alocacaoControl.listaAlocacoes(projetoSelecionado);
			msg = new FacesMessage(mensagem.getMsg(), null);
		} else {
			msg = new FacesMessage(mensagem.getMsg(), "!!!    ATENÇÃO    !!!");
		}
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

}
