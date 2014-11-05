package bean;

import java.io.Serializable;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import modelo.Atividade;
import modelo.Projeto;
import controller.CadastroProjetoControl;
import controller.RelatorioGanttControl;

@ManagedBean
@ViewScoped
public class RelatorioBean implements Serializable{

	private static final long serialVersionUID = -6374637327372728439L;
	
	private List<Projeto> projetos;
	private String codigoProjetoSelecionado;
	private List<Atividade> atividades;
	
	private Projeto projetoSelecionado;
	
	public RelatorioBean() {
		CadastroProjetoControl cadastroProjetoControl = new CadastroProjetoControl();
		this.projetos = cadastroProjetoControl.listaDeProjetos();
		
		// carregar lista de ativides do primeiro projeto da lista
		if (!projetos.isEmpty()) {
			projetoSelecionado = projetos.get(0);

			RelatorioGanttControl relatorioGanttControl = new RelatorioGanttControl();
			this.atividades = relatorioGanttControl.listaAtividadesAlocacoes(projetoSelecionado);
		}
		
	}
	
	
	public List<Projeto> getProjetos() {
		return projetos;
	}
	public void setProjetos(List<Projeto> projetos) {
		this.projetos = projetos;
	}
	public String getCodigoProjetoSelecionado() {
		return codigoProjetoSelecionado;
	}
	public void setCodigoProjetoSelecionado(String codigoProjetoSelecionado) {
		this.codigoProjetoSelecionado = codigoProjetoSelecionado;
	}
	public List<Atividade> getAtividades() {
		return atividades;
	}
	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}
	
	public void trocaProjeto() {
		// buscar o projeto selecionado
		CadastroProjetoControl cadastroProjetoControl = new CadastroProjetoControl();
		this.projetoSelecionado = cadastroProjetoControl
				.buscaProjetoPorCodigo(codigoProjetoSelecionado);
		RelatorioGanttControl relatorioGanttControl = new RelatorioGanttControl();
		this.atividades = relatorioGanttControl
				.listaAtividadesAlocacoes(projetoSelecionado);
	}	
	
}
