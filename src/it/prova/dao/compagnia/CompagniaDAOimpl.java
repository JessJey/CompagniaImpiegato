package it.prova.dao.compagnia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import it.prova.dao.AbstractMySQLDAO;
import it.prova.model.Compagnia;
import it.prova.model.Impiegato;

public class CompagniaDAOimpl extends AbstractMySQLDAO implements CompagniaDAO {

	public CompagniaDAOimpl(Connection connection) {
		super(connection);
	}

	public List<Compagnia> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (Statement ps = connection.createStatement(); ResultSet rs = ps.executeQuery("select * from compagnia")) {

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagionesociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoannuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDatafondazione(rs.getDate("datafondazione"));
				compagniaTemp.setId(rs.getLong("ID"));
				result.add(compagniaTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public Compagnia get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Compagnia result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from compagnia where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Compagnia();
					result.setRagionesociale(rs.getString("ragionesociale"));
					result.setFatturatoannuo(rs.getInt("fatturatoannuo"));
					result.setDatafondazione(rs.getDate("datafondazione"));
					result.setId(rs.getLong("ID"));
				} else {
					result = null;
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public int update(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE compagnia SET ragionesociale=?, fatturatoannuo=?, datafondazione=? where id=?;")) {
			ps.setString(1, input.getRagionesociale());
			ps.setInt(2, input.getFatturatoannuo());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(3, new java.sql.Date(input.getDatafondazione().getTime()));
			ps.setLong(4, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public int insert(Compagnia input) throws Exception {
		// prima di tutto cerchiamo di capire se possiamo effettuare le operazioni
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO compagnia (ragionesociale, fatturatoannuo, datafondazione) VALUES (?, ?, ?);")) {
			ps.setString(1, input.getRagionesociale());
			ps.setInt(2, input.getFatturatoannuo());
			// quando si fa il setDate serve un tipo java.sql.Date
			ps.setDate(3, new java.sql.Date(input.getDatafondazione().getTime()));
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public int delete(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM compagnia WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public List<Compagnia> findByExample(Compagnia input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		String query = "select * from user where 1=1 ";
		if (input.getRagionesociale() != null && !input.getRagionesociale().isEmpty()) {
			query += " and ragionesociale='" + input.getRagionesociale() + "%' ";
		}
		if (input.getFatturatoannuo() != 0) {
			query += " and fatturatoannuo='" + input.getFatturatoannuo() + "%' ";
		}
		if (input.getDatafondazione() != null) {
			query += " and datafondazione='" + new java.sql.Date(input.getDatafondazione().getTime()) + "%' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				compagniaTemp = new Compagnia();
				compagniaTemp.setRagionesociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoannuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDatafondazione(rs.getDate("datafondazione"));
				input.setId(rs.getLong("ID"));
				result.add(compagniaTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public List<Impiegato> findAllByDataDiAssunzioneMaggDi(Date data) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (data == null)
			throw new Exception("Valore di input non ammesso.");

		List<Impiegato> result = new ArrayList<Impiegato>();
		;
		Impiegato impiegatoTemp;
		try (PreparedStatement ps = connection.prepareStatement(
				"SELECT * FROM compagniaimpiegato.impiegato i inner join compagniaimpiegato.compagnia c on i.compagnia_id=c.id where i.datadiassunzione > ?;")) {

			ps.setDate(1, new java.sql.Date(data.getTime()));
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					impiegatoTemp = new Impiegato();
					impiegatoTemp.setNome(rs.getString("NOME"));
					impiegatoTemp.setCognome(rs.getString("COGNOME"));
					impiegatoTemp.setCf(rs.getString("cf"));
					impiegatoTemp.setDatadinascita(rs.getDate("datadinascita"));
					impiegatoTemp.setDatadiassunzione(rs.getDate("datadiassunzione"));
					impiegatoTemp.setId(rs.getLong("ID"));

					Compagnia compagniaTemp = new Compagnia();
					compagniaTemp = new Compagnia();
					compagniaTemp.setRagionesociale(rs.getString("ragionesociale"));
					compagniaTemp.setFatturatoannuo(rs.getInt("fatturatoannuo"));
					compagniaTemp.setDatafondazione(rs.getDate("datafondazione"));
					compagniaTemp.setId(rs.getLong("ID"));

					impiegatoTemp.setCompagnia(compagniaTemp);
					result.add(impiegatoTemp);

				} else {
					result = null;
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;

	}

	public List<Compagnia> findAllByRagioneSocialeContiene(String inputRagioneSociale) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (inputRagioneSociale == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Compagnia> result = new ArrayList<Compagnia>();
		Compagnia compagniaTemp = null;

		try (PreparedStatement ps = connection.prepareStatement(
				"select * from compagnia where ragionesociale like '%" + inputRagioneSociale + "%' ;")) {

			try (ResultSet rs = ps.executeQuery();) {
				while (rs.next()) {
					compagniaTemp = new Compagnia();
					compagniaTemp.setRagionesociale(rs.getString("ragionesociale"));
					compagniaTemp.setFatturatoannuo(rs.getInt("fatturatoannuo"));
					compagniaTemp.setDatafondazione(rs.getDate("datafondazione"));
					compagniaTemp.setId(rs.getLong("ID"));
					result.add(compagniaTemp);
				}
			} // niente catch qui

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

}
