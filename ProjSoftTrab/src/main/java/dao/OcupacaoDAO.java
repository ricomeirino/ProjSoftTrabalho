package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import modelo.Ocupacao;
import connection.ConnectionFactory;

public class OcupacaoDAO {

	private Connection con;
	
	/**
	 * Método para listar as ocupaçoes existentes na base de dados
	 * 
	 * @return List<Ocupacao> - lista com as ocupações que existem na base de dados
	 */

	public List<Ocupacao> listaDeOcupacoes() {
		List<Ocupacao> ocupacoes = new ArrayList<Ocupacao>();
		this.con = new ConnectionFactory().getConnection();

		String sql = "SELECT idocupacao, nome "
				+ " FROM ocupacao"
				+ " order by nome ";

		try {

			PreparedStatement stmt = con.prepareStatement(sql);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {

				Ocupacao ocupacao = new Ocupacao();
				
				ocupacao.setIdOcupacao(rs.getInt("idocupacao"));
				ocupacao.setNome(rs.getString("nome"));
				
				ocupacoes.add(ocupacao);
			}
			rs.close();
			stmt.close();
			con.close();

		} catch (SQLException e) {

			e.printStackTrace();
		}

		return ocupacoes;
	}

}
