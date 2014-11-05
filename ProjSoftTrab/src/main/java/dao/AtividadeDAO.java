package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import modelo.Atividade;
import modelo.Ocupacao;
import modelo.Projeto;
import modelo.Recurso;
import connection.ConnectionFactory;

public class AtividadeDAO {
	private Connection con;
	
	/**
	 * Método para listar as atividades de um projeto existentes na base de dados
	 * 
	 * @param projeto
	 * @return List<Atividade> - lista com as atividades do Projeto que existem na base de dados
	 */
	public List<Atividade> atividadesDoProjeto(Projeto projetoSelecionado){
		this.con = new ConnectionFactory().getConnection();
		List<Atividade> atividades = new ArrayList<Atividade>();
		String sql = "select a.idatividade, a.codigo, a.nome, a.inicio, a.fim, a.finalizada, "
				+ " a.projetoId, p.codigo as codProj, p.nome as nomeProj"
				+ " from atividade a "
				+ " inner join projeto p on (a.projetoId = p.idProjeto) "
				+ " where p.idProjeto = ?"
				+ " order by a.inicio,a.codigo, a.nome ";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, projetoSelecionado.getIdProjeto());
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				
				Atividade atividade = new Atividade();
				atividade.setIdAtividade(rs.getInt("idatividade"));
				atividade.setCodigo(rs.getString("codigo"));
				atividade.setNome(rs.getString("nome"));
				atividade.setInicio(rs.getDate("inicio"));
				atividade.setFim(rs.getDate("fim"));
				atividade.setFinalizada(rs.getBoolean("finalizada"));
				
				
				Projeto projeto = new Projeto();
				projeto.setIdProjeto(rs.getInt("projetoId"));
				projeto.setNome(rs.getString("nomeProj"));
				projeto.setCodigo(rs.getString("codProj"));
				
				atividade.setProjeto(projeto);
				
				atividades.add(atividade);
				
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return atividades;
	}
	
	/**
	 * Método para inserir as atividades de um projeto existentes na base de dados
	 * 
	 * @param atividade
	 *            Atividade - atividade de projeto a ser inserida
	 * @param projetoId
	 *            int - identificador do projeto da atividade a ser inserida
	 * @return boolean - se true a atividade foi inserido com sucesso, se false
	 *         ocorreu falha na inclusão da atividade
	 */
	public boolean insertAtividade(Atividade atividade, int projetoId) {
		this.con = new ConnectionFactory().getConnection();

		boolean retorno = false;

		String sql = "insert into atividade (codigo, nome, inicio, fim, finalizada, projetoId) values (?,?,?,?,0,?) ";

		PreparedStatement stmt;
		try {

			stmt = con.prepareStatement(sql);

			stmt.setString(1, atividade.getCodigo());
			stmt.setString(2, atividade.getNome());
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			String inicioFormatada = dt1.format(atividade.getInicio());
			String fimFormatada = dt1.format(atividade.getFim());
			
			stmt.setString(3, inicioFormatada);
			stmt.setString(4, fimFormatada);
			stmt.setInt(5, projetoId);
			
			stmt.execute();
			stmt.close();
			con.close();
			retorno = true;
			return retorno;
		} catch (SQLException e) {

			return retorno;
		}

	}
	
	/**
	 * Método para buscar uma atividade em um projeto pelo código da atividade
	 * 
	 * @param codigo
	 *            String - código da atividade a ser buscada
	 * @param projetoId
	 *            int - identificador do projeto da atividade a ser buscada
	 * @return Atividade - atividade do projeto informado encontrada com o código informado 
	 */	
	public Atividade buscaAtividadePorCodigoMesmoProjeto(String codigo, int projetoId) {
		Atividade atividade = null;

		this.con = new ConnectionFactory().getConnection();

		String sql = "select a.idatividade, a.codigo, a.nome, a.inicio, a.fim, a.finalizada, "
				+ " a.projetoId, p.codigo as codProj, p.nome as nomeProj"
				+ " from atividade a "
				+ " inner join projeto p on (a.projetoId = p.idProjeto) "
				+ " where p.idProjeto = ? and a.codigo = ?"
				+ " order by a.nome ";

		try {
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, projetoId);
			stmt.setString(2, codigo);
			
			
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				atividade = new Atividade();
				atividade.setIdAtividade(rs.getInt("idatividade"));
				atividade.setCodigo(rs.getString("codigo"));
				atividade.setNome(rs.getString("nome"));
				atividade.setInicio(rs.getDate("inicio"));
				atividade.setFim(rs.getDate("fim"));
				atividade.setFinalizada(rs.getBoolean("finalizada"));
				
				
				Projeto projeto = new Projeto();
				projeto.setIdProjeto(rs.getInt("projetoId"));
				projeto.setNome(rs.getString("nomeProj"));
				projeto.setCodigo(rs.getString("codProj"));
				
				atividade.setProjeto(projeto);
			}

			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return atividade;
	}
	
	/**
	 * Método para buscar uma atividade pelo identificador
	 * 
	 * @param idAtividade
	 *            int - identificador da atividade a ser buscada
	 * @return Atividade - atividade do projeto informado encontrada com o código informado 
	 */	
	public Atividade buscaAtividadePeloId(int idAtividade) {
		Atividade atividade = null;

		this.con = new ConnectionFactory().getConnection();

		String sql = "select a.idatividade, a.codigo, a.nome, a.inicio, a.fim, a.finalizada, "
				+ " a.projetoId, p.codigo as codProj, p.nome as nomeProj"
				+ " from atividade a "
				+ " inner join projeto p on (a.projetoId = p.idProjeto) "
				+ " where a.idatividade = ?"
				+ " order by a.nome ";

		try {
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, idAtividade);
			
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				atividade = new Atividade();
				atividade.setIdAtividade(rs.getInt("idatividade"));
				atividade.setCodigo(rs.getString("codigo"));
				atividade.setNome(rs.getString("nome"));
				atividade.setInicio(rs.getDate("inicio"));
				atividade.setFim(rs.getDate("fim"));
				atividade.setFinalizada(rs.getBoolean("finalizada"));
				
				
				Projeto projeto = new Projeto();
				projeto.setIdProjeto(rs.getInt("projetoId"));
				projeto.setNome(rs.getString("nomeProj"));
				projeto.setCodigo(rs.getString("codProj"));
				
				atividade.setProjeto(projeto);
			}

			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return atividade;
	}

	/**
	 * Método para remover uma atividade
	 * 
	 * @param atividade
	 *            Atividade - atividade a ser removida
	 * @return boolean - se true a atividade foi removida com sucesso, se false
	 *         ocorreu falha na remoção da atividade
	 */
	public boolean deleteAtividade(Atividade atividade) {
		this.con = new ConnectionFactory().getConnection();
		boolean retorno = false;
		String sql = "delete from atividade WHERE idatividade = ?";

		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, atividade.getIdAtividade());

			stmt.execute();
			stmt.close();
			con.close();
			retorno = true;
			return retorno;
		} catch (SQLException e) {

			return retorno;
		}

	}

	/**
	 * Método para atualizar uma atividade na base de dados
	 * 
	 * @param atividade
	 *            Atividade - atividade a ser atualizado
	 * @return boolean - se true a atividade foi atualizada com sucesso, se false
	 *         ocorreu falha na atualização da atividade
	 */

	public boolean atualizaAtividade(Atividade atividade) {
		this.con = new ConnectionFactory().getConnection();
		boolean retorno = true;
		String sql = "UPDATE atividade SET codigo = ?, nome = ?, inicio = ?, fim = ?, finalizada = ? "
				+ " WHERE idatividade = ?";


		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);

			stmt.setString(1, atividade.getCodigo());
			stmt.setString(2, atividade.getNome());
			SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
			String inicioFormatada = dt1.format(atividade.getInicio());
			String fimFormatada = dt1.format(atividade.getFim());
			stmt.setString(3, inicioFormatada);
			stmt.setString(4, fimFormatada);
			

			stmt.setBoolean(5, atividade.isFinalizada());
			stmt.setInt(6, atividade.getIdAtividade());

			stmt.execute();
			stmt.close();
			con.close();
			return retorno;
		} catch (SQLException e) {
			 retorno = false;
			 return retorno;
		}
	}	

	/**
	 * Método para listar as atividades de um projeto que ainda não estão alocadas existentes na base de dados
	 * 
	 * @param projeto
	 * @return List<Atividade> - lista com as atividades do Projeto sem alocação que existem na base de dados
	 */
	public List<Atividade> atividadesDoProjetoSemAlocacao(Projeto projetoSelecionado){
		this.con = new ConnectionFactory().getConnection();
		List<Atividade> atividades = new ArrayList<Atividade>();
		String sql = "select a.idatividade, a.codigo, a.nome, a.inicio, a.fim, a.finalizada, "
				+ " a.projetoId, p.codigo as codProj, p.nome as nomeProj"
				+ " from atividade a "
				+ " inner join projeto p on (a.projetoId = p.idProjeto) "
				+ " where p.idProjeto = ? and a.idatividade not in (select atividadeId from alocacao)"
				+ " order by a.nome ";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, projetoSelecionado.getIdProjeto());
			
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				
				Atividade atividade = new Atividade();
				atividade.setIdAtividade(rs.getInt("idatividade"));
				atividade.setCodigo(rs.getString("codigo"));
				atividade.setNome(rs.getString("nome"));
				atividade.setInicio(rs.getDate("inicio"));
				atividade.setFim(rs.getDate("fim"));
				atividade.setFinalizada(rs.getBoolean("finalizada"));
				
				
				Projeto projeto = new Projeto();
				projeto.setIdProjeto(rs.getInt("projetoId"));
				projeto.setNome(rs.getString("nomeProj"));
				projeto.setCodigo(rs.getString("codProj"));
				
				atividade.setProjeto(projeto);
				
				atividades.add(atividade);
				
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return atividades;
	}	

	/**
	 * 	 Método para listar as atividades e recurso de um projeto
	 * 
	 * @return List<Alocacao> - lista com as atividades e recursos do projeto existentes na base
	 */

	public List<Atividade> listaAtividadesAlocacoes(Projeto projetoSelecionado) {
		List<Atividade> atividades = new ArrayList<Atividade>();
		this.con = new ConnectionFactory().getConnection();

		String sql = "SELECT atv.idatividade, atv.codigo, atv.nome nomeAtv, atv.inicio, atv.fim, atv.finalizada, "
				+ " r.idRecurso, r.matricula, r.nome nomeRec, o.idocupacao, o.nome nomeOcup "
				+ " FROM atividade atv "
				+ " left join alocacao al on (atv.idatividade = al.atividadeId) "
				+ " left join recurso r on (al.recursoId = r.idRecurso) "
				+ " left join ocupacao o on (r.ocupacaoId = o.idocupacao) "
				+ " where atv.projetoId = ? "
				+ " order by atv.inicio, atv.codigo";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, projetoSelecionado.getIdProjeto());
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Atividade atividade = new Atividade();
				atividade.setIdAtividade(rs.getInt("idatividade"));
				atividade.setCodigo(rs.getString("codigo"));
				atividade.setNome(rs.getString("nomeAtv"));
				atividade.setInicio(rs.getDate("inicio"));
				atividade.setFim(rs.getDate("fim"));
				atividade.setFinalizada(rs.getBoolean("finalizada"));
				
				
				Recurso recurso = null;
				if (rs.getInt("idRecurso") != 0){
					recurso = new Recurso();
					Ocupacao ocupacao = new Ocupacao();
					recurso.setIdRecurso(rs.getInt("idRecurso"));
					recurso.setNome(rs.getString("nomeRec"));
					recurso.setMatricula(rs.getString("matricula"));
					ocupacao.setIdOcupacao(rs.getInt("idocupacao"));
					ocupacao.setNome(rs.getString("nomeOcup"));
					recurso.setOcupacao(ocupacao);
				}
				atividade.setRecurso(recurso);

				
				atividades.add(atividade);
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return atividades;
	}
	
	
}
