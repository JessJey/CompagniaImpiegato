package it.prova.dao.impiegato;

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

public class ImpiegatoDAOimpl extends AbstractMySQLDAO implements ImpiegatoDAO {

	public ImpiegatoDAOimpl(Connection connection) {
		super(connection);
	}

	public List<Impiegato> list() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;
		Compagnia compagniaTemp = null;

		try (Statement ps = connection.createStatement();
				ResultSet rs = ps.executeQuery(
						"SELECT * FROM compagniaimpiegato.impiegato i inner join compagniaimpiegato.compagnia c on i.compagnia_id=c.id ;")) {

			while (rs.next()) {
				impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("NOME"));
				impiegatoTemp.setCognome(rs.getString("COGNOME"));
				impiegatoTemp.setCf(rs.getString("cf"));
				impiegatoTemp.setDatadinascita(rs.getDate("datadinascita"));
				impiegatoTemp.setDatadiassunzione(rs.getDate("datadiassunzione"));
				impiegatoTemp.setId(rs.getLong("ID"));

				compagniaTemp = new Compagnia();
				compagniaTemp.setRagionesociale(rs.getString("ragionesociale"));
				compagniaTemp.setFatturatoannuo(rs.getInt("fatturatoannuo"));
				compagniaTemp.setDatafondazione(rs.getDate("datafondazione"));
				compagniaTemp.setId(rs.getLong("ID"));

				impiegatoTemp.setCompagnia(compagniaTemp);
				result.add(impiegatoTemp);
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public Impiegato get(Long idInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (idInput == null || idInput < 1)
			throw new Exception("Valore di input non ammesso.");

		Impiegato result = null;
		try (PreparedStatement ps = connection.prepareStatement("select * from impiegato where id=?")) {

			ps.setLong(1, idInput);
			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {
					result = new Impiegato();
					result.setNome(rs.getString("NOME"));
					result.setCognome(rs.getString("COGNOME"));
					result.setCf(rs.getString("cf"));
					result.setDatadinascita(rs.getDate("datadinascita"));
					result.setDatadiassunzione(rs.getDate("datadiassunzione"));
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

	public int update(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"UPDATE impiegato SET nome=?, cognome=?, cf=?, datadinascita=?, datadiassunzione=? where id=?;")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCf());
			ps.setDate(4, new java.sql.Date(input.getDatadinascita().getTime()));
			ps.setDate(5, new java.sql.Date(input.getDatadiassunzione().getTime()));
			ps.setLong(6, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public int insert(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement(
				"INSERT INTO impiegato (nome, cognome, cf, datadinascita, datadiassunzione, compagnia_id) VALUES (?, ?, ?, ?, ?, ?);")) {
			ps.setString(1, input.getNome());
			ps.setString(2, input.getCognome());
			ps.setString(3, input.getCf());
			ps.setDate(4, new java.sql.Date(input.getDatadinascita().getTime()));
			ps.setDate(5, new java.sql.Date(input.getDatadiassunzione().getTime()));
			ps.setLong(6, input.getCompagnia().getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public int delete(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null || input.getId() == null || input.getId() < 1)
			throw new Exception("Valore di input non ammesso.");

		int result = 0;
		try (PreparedStatement ps = connection.prepareStatement("DELETE FROM impiegato WHERE ID=?")) {
			ps.setLong(1, input.getId());
			result = ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public List<Impiegato> findByExample(Impiegato input) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (input == null)
			throw new Exception("Valore di input non ammesso.");

		ArrayList<Impiegato> result = new ArrayList<Impiegato>();
		Impiegato impiegatoTemp = null;

		String query = "select * from impiegato where 1=1 ";
		if (input.getNome() != null && !input.getNome().isEmpty()) {
			query += " and nome='" + input.getNome() + "%' ";
		}
		if (input.getCognome() != null && !input.getCognome().isEmpty()) {
			query += " and cognome='" + input.getCognome() + "%' ";
		}
		if (input.getCf() != null && !input.getCf().isEmpty()) {
			query += " and cf='" + input.getCf() + "%' ";
		}
		if (input.getDatadinascita() != null) {
			query += " and datadinascita='" + new java.sql.Date(input.getDatadinascita().getTime()) + "%' ";
		}
		if (input.getDatadiassunzione() != null) {
			query += " and datadiassunzione='" + new java.sql.Date(input.getDatadiassunzione().getTime()) + "%' ";
		}

		try (Statement ps = connection.createStatement()) {
			ResultSet rs = ps.executeQuery(query);

			while (rs.next()) {
				impiegatoTemp = new Impiegato();
				impiegatoTemp.setNome(rs.getString("NOME"));
				impiegatoTemp.setCognome(rs.getString("COGNOME"));
				impiegatoTemp.setCf(rs.getString("cf"));
				impiegatoTemp.setDatadinascita(rs.getDate("datadinascita"));
				impiegatoTemp.setDatadiassunzione(rs.getDate("datadiassunzione"));
				impiegatoTemp.setId(rs.getLong("ID"));
				result.add(impiegatoTemp);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	public List<Impiegato> findAllByCompagnia(Compagnia compagniaInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (compagniaInput == null)
			throw new Exception("Valore di input non ammesso.");

		List<Impiegato> result = new ArrayList<Impiegato>();
		;
		Impiegato impiegatoTemp;
		try (PreparedStatement ps = connection.prepareStatement(
				"SELECT * FROM compagniaimpiegato.impiegato i inner join compagniaimpiegato.compagnia c on i.compagnia_id=c.id where i.compagnia_id= ?;")) {
			ps.setLong(1, compagniaInput.getId());
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

	public int countByDataFondazioneCompagniaMaggDi(Date data) throws Exception {

		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (data == null)
			throw new Exception("Valore di input non ammesso.");
		int result = 0;
		try (PreparedStatement ps = connection
				.prepareStatement("select count(*) as mycount from compagnia where datafondazione>? ")) {
			ps.setDate(1, new java.sql.Date(data.getTime()));

			try (ResultSet rs = ps.executeQuery()) {
				if (rs.next()) {

					result = rs.getInt("mycount");
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}

			return result;
		}
	}

	public List<Impiegato> findAllByCompagniaConFatturatoMaggDi(int fatturatoInput) throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		if (fatturatoInput == 0)
			throw new Exception("Valore di input non ammesso.");

		List<Impiegato> result = new ArrayList<Impiegato>();
		;
		Impiegato impiegatoTemp;
		try (PreparedStatement ps = connection.prepareStatement(
				"SELECT * FROM compagniaimpiegato.impiegato i inner join compagniaimpiegato.compagnia c on i.compagnia_id=c.id where c.fatturatoannuo > ?;")) {
			ps.setInt(1, fatturatoInput);
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

	public List<Impiegato> findAllErroriAssunzioni() throws Exception {
		if (isNotActive())
			throw new Exception("Connessione non attiva. Impossibile effettuare operazioni DAO.");

		List<Impiegato> result = new ArrayList<Impiegato>();
		;
		Impiegato impiegatoTemp;
		try (PreparedStatement ps = connection.prepareStatement(
				"SELECT * FROM compagniaimpiegato.impiegato i inner join compagniaimpiegato.compagnia c on i.compagnia_id=c.id where i.datadiassunzione < c.datafondazione;")) {

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

}
