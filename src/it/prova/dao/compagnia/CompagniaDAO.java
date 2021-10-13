package it.prova.dao.compagnia;

import java.util.Date;
import java.util.List;

import it.prova.dao.IBaseDAO;
import it.prova.model.Compagnia;
import it.prova.model.Impiegato;

public interface CompagniaDAO extends IBaseDAO<Compagnia> {

	public List<Impiegato> findAllByDataDiAssunzioneMaggDi(Date data) throws Exception;
	public List<Compagnia> findAllByRagioneSocialeContiene(String inputRagioneSociale) throws Exception;
	
}
