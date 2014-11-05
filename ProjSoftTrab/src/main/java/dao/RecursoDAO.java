package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Ocupacao;
import modelo.Recurso;
import connection.ConnectionFactory;

/**
 * @author Ricardo
 *
 */
public class RecursoDAO {
	private Connection con;

	/**
	 * Método para inserir um recurso na base de dados
	 * 
	 * @param recurso
	 *            Recurso - recurso a ser inserido
	 * @param idOcupacao
	 *            int - identificador da ocupação do recurso a ser inserido
	 * @return boolean - se true o recurso foi inserido com sucesso, se false
	 *         ocorreu falha na inclusão do recurso
	 */
	public boolean insereRecurso(Recurso recurso, int idOcupacao) {
		boolean retorno = false;
		this.con = new ConnectionFactory().getConnection();

		String sql = "insert into recurso (nome, matricula, ocupacaoId) values (?,?,?) ";

		PreparedStatement stmt;
		try {

			stmt = con.prepareStatement(sql);

			stmt.setString(1, recurso.getNome());
			stmt.setString(2, recurso.getMatricula());
			stmt.setInt(3, idOcupacao);

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
	 * Método para remover um recurso na base de dados
	 * 
	 * @param recurso
	 *            Recurso - recurso a ser removido
	 * @return boolean - se true o recurso foi removido com sucesso, se false
	 *         ocorreu falha na remoção do recurso
	 */

	public boolean removeRecurso(Recurso recurso) {
		boolean retorno = false;
		this.con = new ConnectionFactory().getConnection();

		String sql = "delete from recurso WHERE idRecurso = ?";

		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, recurso.getIdRecurso());

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
	 * Método para atualizar um recurso na base de dados
	 * 
	 * @param recurso
	 *            Recurso - recurso a ser atualizado
	 * @return boolean - se true o recurso foi atualizado com sucesso, se false
	 *         ocorreu falha na atualização do recurso
	 */

	public boolean atualizaRecurso(Recurso recurso, int idOcupacao) {
		this.con = new ConnectionFactory().getConnection();
		boolean retorno = true;
		String sql = "UPDATE recurso SET matricula = ?, nome = ?, ocupacaoId = ? "
				+ " WHERE idRecurso = ?";


		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);

			stmt.setString(1, recurso.getMatricula());
			stmt.setString(2, recurso.getNome());
			stmt.setInt(3, idOcupacao);
			stmt.setInt(4, recurso.getIdRecurso());

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
	 * Método para listar os recursos existentes na base de dados
	 * 
	 * @return List<Recurso> - lista com os recurso que existem na base de dados
	 */

	public List<Recurso> listaDeRecursos() {
		List<Recurso> recursos = new ArrayList<Recurso>();
		this.con = new ConnectionFactory().getConnection();

		String sql = "SELECT r.idRecurso, r.nome, r.matricula, o.idocupacao, o.nome as nomeOcup "
				+ " FROM recurso r "
				+ " INNER JOIN ocupacao o on (r.ocupacaoId=o.idocupacao) "
				+ " order by r.nome ";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Recurso recurso = new Recurso();
				Ocupacao ocupacao = new Ocupacao();

				recurso.setIdRecurso(rs.getInt("idRecurso"));
				recurso.setNome(rs.getString("nome"));
				recurso.setMatricula(rs.getString("matricula"));
				ocupacao.setIdOcupacao(rs.getInt("idocupacao"));
				ocupacao.setNome(rs.getString("nomeOcup"));
				recurso.setOcupacao(ocupacao);

				recursos.add(recurso);
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return recursos;
	}

	/**
	 * Método para listar os recursos sem alocação existentes na base de dados
	 * 
	 * @return List<Recurso> - lista com os recurso sem alocação que existem na
	 *         base de dados
	 */

	public List<Recurso> listaRecursosDesalocados() {
		List<Recurso> recursos = new ArrayList<Recurso>();
		this.con = new ConnectionFactory().getConnection();

		String sql = "SELECT r.idRecurso, r.nome, r.matricula, o.idocupacao, o.nome as nomeOcup "
				+ " FROM recurso r "
				+ " INNER JOIN ocupacao o on (r.ocupacaoId=o.idocupacao) "
				+ " where r.idrecurso not in (select recursoId from alocacao)"
				+ " order by r.nome ";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Recurso recurso = new Recurso();
				Ocupacao ocupacao = new Ocupacao();

				recurso.setIdRecurso(rs.getInt("idRecurso"));
				recurso.setNome(rs.getString("nome"));
				recurso.setMatricula(rs.getString("matricula"));
				ocupacao.setIdOcupacao(rs.getInt("idocupacao"));
				ocupacao.setNome(rs.getString("nomeOcup"));
				recurso.setOcupacao(ocupacao);

				recursos.add(recurso);
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return recursos;
	}

	
	public Recurso buscaRecursoPorMatricula(String matricula) {
		Recurso recurso = null;

		this.con = new ConnectionFactory().getConnection();

		String sql = "select idrecurso, matricula, nome  from recurso "
				+ " where matricula = ?";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, matricula);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				recurso = new Recurso();
				recurso.setIdRecurso(rs.getInt("idrecurso"));
				recurso.setMatricula(rs.getString("matricula"));
				recurso.setNome(rs.getString("nome"));
			}

			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return recurso;
	}

}
