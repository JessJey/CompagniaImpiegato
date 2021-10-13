package it.prova.model;

import java.util.Date;

public class Compagnia {

	private Long id;
	private String ragionesociale;
	private int fatturatoannuo;
	private Date datafondazione;

	public Compagnia() {
	}

	public Compagnia( String ragionesociale, int fatturatoannuo, Date datafondazione) {
		super();
		
		this.ragionesociale = ragionesociale;
		this.fatturatoannuo = fatturatoannuo;
		this.datafondazione = datafondazione;
	}

	public Compagnia( String ragionesociale, int fatturatoannuo) {
		super();
		
		this.ragionesociale = ragionesociale;
		this.fatturatoannuo = fatturatoannuo;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRagionesociale() {
		return ragionesociale;
	}

	public void setRagionesociale(String ragionesociale) {
		this.ragionesociale = ragionesociale;
	}

	public int getFatturatoannuo() {
		return fatturatoannuo;
	}

	public void setFatturatoannuo(int fatturatoannuo) {
		this.fatturatoannuo = fatturatoannuo;
	}

	public Date getDatafondazione() {
		return datafondazione;
	}

	public void setDatafondazione(Date datafondazione) {
		this.datafondazione = datafondazione;
	}

	@Override
	public String toString() {
		return "Compagnia [id=" + id + ", ragionesociale=" + ragionesociale + ", fatturatoannuo=" + fatturatoannuo
				+ ", datafondazione=" + datafondazione + "]";
	}

}
