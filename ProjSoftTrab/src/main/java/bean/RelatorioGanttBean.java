package bean;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;

import modelo.Atividade;
import modelo.Projeto;

import org.primefaces.extensions.event.timeline.TimelineSelectEvent;
import org.primefaces.extensions.model.timeline.TimelineEvent;
import org.primefaces.extensions.model.timeline.TimelineModel;

import controller.CadastroProjetoControl;
import controller.RelatorioGanttControl;

@ManagedBean
@ViewScoped
public class RelatorioGanttBean implements Serializable {

	private static final long serialVersionUID = 4265156485497741425L;

	private Projeto projetoSelecionado;
	private String codigoProjetoSelecionado;
	private List<Projeto> projetos;
	private List<Atividade> atividades;

	private TimelineModel modelGantt; // model of the second timeline

	public RelatorioGanttBean() {
		// carregar lista de projetos
		CadastroProjetoControl cadastroProjetoControl = new CadastroProjetoControl();
		projetos = cadastroProjetoControl.listaDeProjetos();
		// carregar lista de ativides do primeiro projeto da lista
		if (!projetos.isEmpty()) {
			projetoSelecionado = projetos.get(0);
			RelatorioGanttControl relatorioGanttControl = new RelatorioGanttControl();
			this.atividades = relatorioGanttControl
					.listaAtividadesAlocacoes(projetoSelecionado);
		}
		createGantt();
	}

	private void createGantt() {
		modelGantt = new TimelineModel();
		String cor = null;
		Date dataAtual = new Date();
					
		for (Iterator<Atividade> iterator = atividades.iterator(); iterator
				.hasNext();) {
			Atividade atividade = (Atividade) iterator.next();
			Date startAtiv = atividade.getInicio();
			Date endAtiv = atividade.getFim();
			String labelBarra = "-";
			if (atividade.getRecurso() != null) {
				labelBarra = atividade.getRecurso().getNome() + " - "
						+ atividade.getRecurso().getOcupacao().getNome();
			}

			
			if (atividade.getRecurso() == null){
				cor = "naoAlocada";
			}else{
				if (atividade.isFinalizada()){
					cor = "finalizada";
				}else if (endAtiv.before(dataAtual)){
					cor = "atrasada";
				}else{
					cor = "naoFinalizada";					
				}
				
			}
			
			TimelineEvent te = new TimelineEvent(labelBarra, startAtiv,
					endAtiv, false, atividade.getCodigo() + " - " + atividade.getNome(), cor);
			modelGantt.add(te);

		}

	}

	public Projeto getProjetoSelecionado() {
		return projetoSelecionado;
	}

	public void setProjetoSelecionado(Projeto projetoSelecionado) {
		this.projetoSelecionado = projetoSelecionado;
	}

	public List<Atividade> getAtividades() {
		return atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public TimelineModel getModelGantt() {
		return modelGantt;
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

	public void trocaProjeto() {
		// buscar o projeto selecionado
		CadastroProjetoControl cadastroProjetoControl = new CadastroProjetoControl();
		this.projetoSelecionado = cadastroProjetoControl
				.buscaProjetoPorCodigo(codigoProjetoSelecionado);
		RelatorioGanttControl relatorioGanttControl = new RelatorioGanttControl();
		this.atividades = relatorioGanttControl
				.listaAtividadesAlocacoes(projetoSelecionado);

		createGantt();

	}

	public void onSelect(TimelineSelectEvent e){
		
		TimelineEvent timelineEvent = e.getTimelineEvent();
		
		String recurso = timelineEvent.getData().toString() ;
		SimpleDateFormat dt1 = new SimpleDateFormat("dd/MM/yyyy");
		String atividadeNome = timelineEvent.getGroup()+ " <br /> ( " + dt1.format(timelineEvent.getStartDate())+ " - " + dt1.format(timelineEvent.getEndDate()) + " )";
		
		FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, atividadeNome, recurso);

		FacesContext.getCurrentInstance().addMessage("Teste", msg);
	}
	
}
