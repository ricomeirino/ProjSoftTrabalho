package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Projeto;
import connection.ConnectionFactory;

public class ProjetoDAO {
	private Connection con;

	public List<Projeto> getProjetos() {
		this.con = new ConnectionFactory().getConnection();
		List<Projeto> projetos = new ArrayList<Projeto>();
		String sql = "select idprojeto, codigo, nome " + " from projeto "
				+ " order by nome ";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Projeto projeto = new Projeto();
				projeto.setIdProjeto(rs.getInt("idprojeto"));
				projeto.setNome(rs.getString("nome"));
				projeto.setCodigo(rs.getString("codigo"));
				projetos.add(projeto);
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return projetos;
	}

	public boolean insertProjeto(Projeto projeto) {
		this.con = new ConnectionFactory().getConnection();

		boolean retorno = false;

		String sql = "insert into projeto (codigo, nome) values (?,?) ";

		PreparedStatement stmt;
		try {

			stmt = con.prepareStatement(sql);

			stmt.setString(1, projeto.getCodigo());
			stmt.setString(2, projeto.getNome());

			stmt.execute();
			stmt.close();
			con.close();
			retorno = true;
			return retorno;
		} catch (SQLException e) {

			return retorno;
		}

	}

	public Projeto buscaProjetoPorCodigo(String codigo) {
		Projeto projeto = null;

		this.con = new ConnectionFactory().getConnection();

		String sql = "select idprojeto, codigo, nome " + " from projeto "
				+ " where codigo = ?";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setString(1, codigo);
			ResultSet rs = stmt.executeQuery();

			if (rs.next()) {
				projeto = new Projeto();
				projeto.setIdProjeto(rs.getInt("idprojeto"));
				projeto.setCodigo(rs.getString("codigo"));
				projeto.setNome(rs.getString("nome"));
			}

			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return projeto;
	}

	public boolean deleteProjeto(Projeto projeto) {
		this.con = new ConnectionFactory().getConnection();
		boolean retorno = false;
		String sql = "delete from projeto WHERE idProjeto = ?";

		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);
			stmt.setInt(1, projeto.getIdProjeto());

			stmt.execute();
			stmt.close();
			con.close();
			retorno = true;
			return retorno;
		} catch (SQLException e) {

			return retorno;
		}

	}


	public boolean updateProjeto(Projeto projeto) {
		this.con = new ConnectionFactory().getConnection();
		boolean retorno = true;
		String sql = "UPDATE projeto SET codigo = ?, nome = ? "
				+ " WHERE idprojeto = ?";


		PreparedStatement stmt;
		try {
			stmt = con.prepareStatement(sql);

			stmt.setString(1, projeto.getCodigo());
			stmt.setString(2, projeto.getNome());
			stmt.setInt(3, projeto.getIdProjeto());

			stmt.execute();
			stmt.close();
			con.close();
			return retorno;
		} catch (SQLException e) {
			 retorno = false;
			 return retorno;
		}

	}

}
