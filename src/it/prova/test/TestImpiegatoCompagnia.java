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

			System.out.println("test inserimento nuova compagnia");
			Compagnia azienda = new Compagnia("Apple", 2000);
			String dataFondazioneAzienda = "1976-04-1";
			Date dataDaInserireAzienda = new SimpleDateFormat("yyyy-MM-dd").parse(dataFondazioneAzienda);
			azienda.setDatafondazione(dataDaInserireAzienda);
			compagniaDAOinstance.insert(azienda);
			System.out.println("test inserimento nuova compagnia END");

			System.out.println("test inserimento nuovo impiegato");
			Impiegato impiegatoMarioRossi = new Impiegato("mario", "rossi", "mrrrss85kkk99");
			String dataNascitaImpiegato1 = "1995-08-26";
			String dataAssunzioneImpiegato1 = "2020-07-26";
			Date dataDaInserireAlNuovoUser = new SimpleDateFormat("yyyy-MM-dd").parse(dataNascitaImpiegato1);
			Date dataAssunzioneDaInser = new SimpleDateFormat("yyyy-MM-dd").parse(dataAssunzioneImpiegato1);
			impiegatoMarioRossi.setDatadinascita(dataDaInserireAlNuovoUser);
			impiegatoMarioRossi.setDatadiassunzione(dataAssunzioneDaInser);
			;
			// quello che manca ora è solo la compagnia da associare ma la devo caricare dal
			// db....
			// AD OCCHIO
			impiegatoMarioRossi.setCompagnia(compagniaDAOinstance.get(29L));
			impiegatoDAOinstance.insert(impiegatoMarioRossi);
			System.out.println("test inserimento nuovo impiegato END");

			// compagniaDAOinstance.insertCompleto(impiegatoMarioRossi);

			System.out.println("############################## LISTA IMPIEGATO #######################");

			for (Impiegato impiegatoItem : impiegatoDAOinstance.list()) {
				System.out.println(impiegatoItem);
			}

			for (Compagnia compagniaItem : compagniaDAOinstance.list()) {
				System.out.println(compagniaItem);
			}

			System.out.println("TEST RAGIONE SOCIALE CONTIENE");
			for (Compagnia compagniaItem : compagniaDAOinstance.findAllByRagioneSocialeContiene("cola")) {
				System.out.println(compagniaItem);
			}
			System.out.println("************ END TEST************");

			System.out.println("TEST data assunzione maggiore di ");
			String dataDaControllare = "1980-04-04";
			Date dataDaControl = new SimpleDateFormat("yyyy-MM-dd").parse(dataDaControllare);

			for (Impiegato compagniaItem : compagniaDAOinstance.findAllByDataDiAssunzioneMaggDi(dataDaControl)) {
				System.out.println(compagniaItem);
			}
			System.out.println("************ END TEST************");

			System.out.println("TEST COUNT BY DATA FONDAZIONE");
			String dataDaControllarePerFondazione = "1976-03-30";
			Date dataDaControlPerDataFond = new SimpleDateFormat("yyyy-MM-dd").parse(dataDaControllarePerFondazione);
			System.out.println(impiegatoDAOinstance.countByDataFondazioneCompagniaMaggDi(dataDaControlPerDataFond));
			System.out.println("************ END TEST************");

			System.out.println("TEST COMPAGNIA CON FATTURATO MAGGIORE DI ");
			for (Impiegato compagniaItem : impiegatoDAOinstance.findAllByCompagniaConFatturatoMaggDi(1500)) {
				System.out.println(compagniaItem);
			}
			System.out.println("************ END TEST************");
			
			System.out.println("TEST errori assunzioni ");
			//creazione nuovo impiegato
			Impiegato impiegatoGuidoVerde = new Impiegato("Guido", "Verde", "gdvrd87dji22m");
			String dataNascitaGuido = "1990-05-08";
			String dataAssunzioneGuido = "1950-07-26";
			Date dataNascitaDaInserireAGuido = new SimpleDateFormat("yyyy-MM-dd").parse(dataNascitaGuido);
			Date dataAssunzioneDaInserAGuido = new SimpleDateFormat("yyyy-MM-dd").parse(dataAssunzioneGuido);
			impiegatoGuidoVerde.setDatadinascita(dataNascitaDaInserireAGuido);
			impiegatoGuidoVerde.setDatadiassunzione(dataAssunzioneDaInserAGuido);
			impiegatoGuidoVerde.setCompagnia(compagniaDAOinstance.get(3L));
			impiegatoDAOinstance.insert(impiegatoGuidoVerde);
			//test metodo 
			for (Impiegato compagniaItem : impiegatoDAOinstance.findAllErroriAssunzioni()) {
				System.out.println(compagniaItem);
			}
			System.out.println("************ END TEST************");
			
			System.out.println("TEST find all by compagnia ");
			for (Impiegato compagniaItem : impiegatoDAOinstance.findAllByCompagnia(compagniaDAOinstance.get(29L))) {
				System.out.println(compagniaItem);
			}
			System.out.println("************ END TEST************");
			
			
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
