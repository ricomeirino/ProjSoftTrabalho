package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Alocacao;
import modelo.Atividade;
import modelo.Ocupacao;
import modelo.Projeto;
import modelo.Recurso;
import connection.ConnectionFactory;

/**
 * @author Ricardo
 *
 */
public class AlocacaoDAO {

	private Connection con;

	/**
	 * Método para verificar se um recurso está alocado a uma atividade
	 * 
	 * @param recurso
	 *            Recurso - recurso a ser verificado
	 * @return boolean - se true indica que o recurso está alocado, se false
	 *         indica que o recurso NÃO está alocado
	 */
	public boolean recursoEstaAlocado(Recurso recurso) {
		boolean retorno = false;

		this.con = new ConnectionFactory().getConnection();

		String sql = "select idalocacao from alocacao "
				+ " where recursoId = ?";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, recurso.getIdRecurso());
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				retorno = true;
			}

			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {

			return retorno;
		}

		return retorno;
	}
	
	/**
	 * Método para verificar se uma atividade está com recurso alocado
	 * 
	 * @param atividade
	 *            Atividade - atividade a ser verificada
	 * @return boolean - se true indica que a atividade está com recurso alocado, se false
	 *         indica que a atividade NÃO está com recurso alocado
	 */
	public boolean atividadeEstaAlocada(Atividade atividade) {
		boolean retorno = false;

		this.con = new ConnectionFactory().getConnection();

		String sql = "select idalocacao from alocacao "
				+ " where atividadeId = ?";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, atividade.getIdAtividade());
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				retorno = true;
			}

			rs.close();
			stmt.close();
			con.close();

		} catch (Exception e) {

			return retorno;
		}

		return retorno;
	}	
	
	/**
	 * Método para listar as alocações de um projeto
	 * 
	 * @return List<Alocacao> - lista com as alocações das atividades do projeto existentes na base
	 */

	public List<Alocacao> listaAlocacoes(Projeto projetoSelecionado) {
		List<Alocacao> alocacoes = new ArrayList<Alocacao>();
		this.con = new ConnectionFactory().getConnection();

		String sql = "select al.idalocacao, at.idatividade, at.codigo, at.nome nomeAtv, at.inicio, at.fim, at.finalizada,"
				+ " r.idRecurso, r.matricula, r.nome nomeRec, o.idocupacao, o.nome nomeOcup "
				+ " from alocacao al "
				+ " inner join atividade at on (al.atividadeId = at.idatividade) "
				+ " inner join recurso r on (al.recursoId = r.idRecurso) "
				+ " inner join ocupacao o on (r.ocupacaoId = o.idocupacao) "
				+ " where at.projetoId = ? "
				+ " order by at.codigo";

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
				
				
				Recurso recurso = new Recurso();
				Ocupacao ocupacao = new Ocupacao();
				recurso.setIdRecurso(rs.getInt("idRecurso"));
				recurso.setNome(rs.getString("nomeRec"));
				recurso.setMatricula(rs.getString("matricula"));
				ocupacao.setIdOcupacao(rs.getInt("idocupacao"));
				ocupacao.setNome(rs.getString("nomeOcup"));
				recurso.setOcupacao(ocupacao);

				Alocacao alocacao = new Alocacao();
				alocacao.setIdAlocacao(rs.getInt("idalocacao"));
				
				alocacao.setAtividade(atividade);
				alocacao.setRecurso(recurso);
				
				alocacoes.add(alocacao);
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return alocacoes;
	}
	
	
	/**
	 * Método para incluir a alocacao de um recurso a uma atividade de um projeto
	 * 
	 * @param atividade
	 *            Atividade - atividade a ter recurso alocado 
	 * @param recurso
	 * 			  Recurso - recurso a ser alocado
	 * @return boolean - se true indica que a alocação foi realizada, se false
	 *         indica que a alocação NÃO foi realizada
	 */
	public boolean insertAlocacao(Atividade atividade, Recurso recurso) {
		this.con = new ConnectionFactory().getConnection();

		String sql = "insert into alocacao (atividadeId, recursoId) values (?,?) ";

		PreparedStatement stmt;
		try {

			stmt = con.prepareStatement(sql);

			stmt.setInt(1, atividade.getIdAtividade());
			stmt.setInt(2, recurso.getIdRecurso());

			stmt.execute();
			stmt.close();
			con.close();

			return true;
		} catch (Exception e) {

			return false;
		}

	}

	/**
	 * Método para excluir a alocacao de um recurso a uma atividade de um projeto
	 * 
	 * @param alocacao
	 *            Alocacao - alocação a ser excluída
	 *             
	 * @return boolean - se true indica que a alocação foi excluída, se false
	 *         indica que exclusão da alocação NÃO foi realizada
	 */
	public boolean deleteAlocacao(Alocacao alocacao) {
		this.con = new ConnectionFactory().getConnection();
		String sql = "delete from alocacao where idalocacao= ? ";

		PreparedStatement stmt;
		try {

			stmt = con.prepareStatement(sql);

			stmt.setInt(1, alocacao.getIdAlocacao());


			stmt.execute();
			stmt.close();
			con.close();

			return true;
		} catch (SQLException e) {

			return false;
		}

	}

	/**
	 * Método para excluir a alocacao de um recurso a uma atividade de um projeto
	 * 
	 * @param alocacao
	 *            Alocacao - alocação a ser excluída
	 *             
	 * @return boolean - se true indica que a alocação foi excluída, se false
	 *         indica que exclusão da alocação NÃO foi realizada
	 */
	public boolean deleteAlocacaoAtivRec(Atividade atividade, Recurso recurso) {
		this.con = new ConnectionFactory().getConnection();
		String sql = "delete from alocacao where atividadeId= ?  and recursoId = ?";

		PreparedStatement stmt;
		try {

			stmt = con.prepareStatement(sql);

			stmt.setInt(1, atividade.getIdAtividade());
			stmt.setInt(2, recurso.getIdRecurso());


			stmt.execute();
			stmt.close();
			con.close();

			return true;
		} catch (SQLException e) {

			return false;
		}

	}
		
}
