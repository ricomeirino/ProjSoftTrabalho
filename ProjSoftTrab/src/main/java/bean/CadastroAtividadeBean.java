package bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Atividade;
import modelo.Projeto;

import org.primefaces.event.RowEditEvent;

import util.Mensagem;
import controller.CadastroAtividadesControl;
import controller.CadastroProjetoControl;
import dao.AtividadeDAO;

@ManagedBean
@ViewScoped
public class CadastroAtividadeBean implements Serializable {

	private static final long serialVersionUID = 3139384308260462284L;

	private List<Projeto> projetos;

	private String codigoNovo;
	private String nomeNovo;
	private Date inicioNovo;
	private Date fimNovo;
	private String codigoProjetoSelecionado;

	private Projeto projetoSelecionado;

	private List<Atividade> atividades;
	private List<Atividade> atividadesFiltradas;


	
	public CadastroAtividadeBean() {
		// carregar lista de projetos
		CadastroProjetoControl cadastroProjetoControl = new CadastroProjetoControl();
		projetos = cadastroProjetoControl.listaDeProjetos();
		// carregar lista de ativides do primeiro projeto da lista
		if (!projetos.isEmpty()) {
			projetoSelecionado = projetos.get(0);
			CadastroAtividadesControl cadastroAtividadesControl = new CadastroAtividadesControl();
			this.atividades = cadastroAtividadesControl
					.atividadesDoProjeto(projetoSelecionado);
		}
	}

	public List<Projeto> getProjetos() {
		return projetos;
	}

	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
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

	public Date getInicioNovo() {
		return inicioNovo;
	}

	public void setInicioNovo(Date inicioNovo) {
		this.inicioNovo = inicioNovo;
	}

	public Date getFimNovo() {
		return fimNovo;
	}

	public void setFimNovo(Date fimNovo) {
		this.fimNovo = fimNovo;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public List<Atividade> getAtividadesFiltradas() {
		return atividadesFiltradas;
	}

	public void setAtividadesFiltradas(List<Atividade> atividadesFiltradas) {
		this.atividadesFiltradas = atividadesFiltradas;
	}

	public String getCodigoProjetoSelecionado() {
		return codigoProjetoSelecionado;
	}

	public void setCodigoProjetoSelecionado(String codigoProjetoSelecionado) {
		this.codigoProjetoSelecionado = codigoProjetoSelecionado;
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
		AtividadeDAO atividadeDAO = new AtividadeDAO();
		this.atividades = atividadeDAO.atividadesDoProjeto(projetoSelecionado);
		if (atividadesFiltradas != null) {
			this.atividadesFiltradas.clear();
		}

	}

	public String insereAtividade() {

		CadastroAtividadesControl cadastroAtividadesControl = new CadastroAtividadesControl();

		Atividade atividadeNova = new Atividade();
		atividadeNova.setCodigo(codigoNovo);
		atividadeNova.setNome(nomeNovo);
		atividadeNova.setInicio(inicioNovo);
		atividadeNova.setFim(fimNovo);

		Mensagem mensagem = cadastroAtividadesControl.insereAtividade(
				atividadeNova, projetoSelecionado.getIdProjeto());
		this.atividades = cadastroAtividadesControl
				.atividadesDoProjeto(projetoSelecionado);

		if (mensagem.getCodigo() == 0) {
			this.codigoNovo = "";
			this.nomeNovo = "";
			this.inicioNovo = null;
			this.fimNovo = null;
		}

		FacesMessage msg = new FacesMessage(mensagem.getMsg(),
				atividadeNova.getNome());

		FacesContext.getCurrentInstance().addMessage(null, msg);

		return null;
	}

	public void onDelete(Atividade atividadeRemovida) {

		CadastroAtividadesControl cadastroAtividadesControl = new CadastroAtividadesControl();

		Mensagem mensagem = cadastroAtividadesControl
				.removeAtividade(atividadeRemovida);

		if (mensagem.getCodigo() == 0) {
			this.atividades.remove(atividadeRemovida);
			if (atividadesFiltradas != null){
				atividadesFiltradas.remove(atividadeRemovida);
			}
		}

		FacesMessage msg = new FacesMessage(mensagem.getMsg(),
				atividadeRemovida.getCodigo());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onEdit(RowEditEvent event) {
		Atividade atividadeEditada = (Atividade) event.getObject();
		CadastroAtividadesControl cadastroAtividadesControl = new CadastroAtividadesControl();

		Mensagem mensagem = cadastroAtividadesControl
				.atualizaAtividade(atividadeEditada);

		this.atividades = cadastroAtividadesControl
				.atividadesDoProjeto(projetoSelecionado);

		FacesMessage msg = new FacesMessage(mensagem.getMsg(),
				((Atividade) event.getObject()).getCodigo());

		FacesContext.getCurrentInstance().addMessage(null, msg);

	}

	public void onCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edição de Atividade Cancelada",
				((Atividade) event.getObject()).getCodigo());

		FacesContext.getCurrentInstance().addMessage(null, msg);
	}



}
