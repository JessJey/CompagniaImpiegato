package it.prova.test;

import java.sql.Connection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import it.prova.connection.MyConnection;
import it.prova.dao.Constants;
import it.prova.dao.compagnia.CompagniaDAO;
import it.prova.dao.compagnia.CompagniaDAOimpl;
import it.prova.dao.impiegato.ImpiegatoDAO;
import it.prova.dao.impiegato.ImpiegatoDAOimpl;
import it.prova.model.Compagnia;
import it.prova.model.Impiegato;

public class TestImpiegatoCompagnia {

	public static void main(String[] args) throws ParseException {

		try (Connection connection = MyConnection.getConnection(Constants.DRIVER_NAME, Constants.CONNECTION_URL)) {
			ImpiegatoDAO impiegatoDAOinstance = null;
			impiegatoDAOinstance = new ImpiegatoDAOimpl(connection);

			CompagniaDAO compagniaDAOinstance = null;
			compagniaDAOinstance = new CompagniaDAOimpl(connection);

			Compagnia azienda = new Compagnia("Apple", 2000);
			String dataFondazioneAzienda = "1976-04-1";
			Date dataDaInserireAzienda = new SimpleDateFormat("yyyy-MM-dd").parse(dataFondazioneAzienda);
			azienda.setDatafondazione(dataDaInserireAzienda);

			Impiegato impiegato1 = new Impiegato("mario", "rossi", "mrrrss85kkk99");
			String dataNascitaImpiegato1 = "1995-08-26";
			String dataAssunzioneImpiegato1 = "2020-07-26";
			Date dataDaInserireAlNuovoUser = new SimpleDateFormat("yyyy-MM-dd").parse(dataNascitaImpiegato1);
			Date dataAssunzioneDaInser = new SimpleDateFormat("yyyy-MM-dd").parse(dataAssunzioneImpiegato1);
			impiegato1.setDatadinascita(dataDaInserireAlNuovoUser);
			impiegato1.setDatadiassunzione(dataAssunzioneDaInser);;
			
			impiegato1.setCompagnia(azienda);
			compagniaDAOinstance.insert(azienda);
			impiegatoDAOinstance.insert(impiegato1);

			System.out.println("############################## LISTA IMPIEGATO #######################");

			for (Impiegato impiegatoItem : impiegatoDAOinstance.list()) {
				System.out.println(impiegatoItem);
			}

			for (Compagnia compagniaItem : compagniaDAOinstance.list()) {
				System.out.println(compagniaItem);
			}

			System.out.println("############################## LISTA IMPIEGATO #######################");
			for (Impiegato compagniaItem : compagniaDAOinstance.findAllByDataDiAssunzioneMaggDi(new SimpleDateFormat("yyyy-MM-dd").parse(dataAssunzioneImpiegato1))) {
				System.out.println(compagniaItem);
			}
			
			
			
			;

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
