package it.prova.model;

import java.util.Date;

public class Impiegato {

	private Long id;
	private String nome;
	private String cognome;
	private String cf;
	private Date datadinascita;
	private Date datadiassunzione;
	private Compagnia compagnia;

	public Impiegato() {
	}

	
	public Impiegato( String nome, String cognome, String cf, Date datadinascita, Date datadiassunzione,
			Compagnia compagnia) {
		super();
	
		this.nome = nome;
		this.cognome = cognome;
		this.cf = cf;
		this.datadinascita = datadinascita;
		this.datadiassunzione = datadiassunzione;
		this.compagnia = compagnia;
	}


	public Impiegato( String nome, String cognome, String cf, Date datadinascita, Date datadiassunzione) {
		super();
		
		this.nome = nome;
		this.cognome = cognome;
		this.cf = cf;
		this.datadinascita = datadinascita;
		this.datadiassunzione = datadiassunzione;
	}

	public Impiegato( String nome, String cognome, String cf) {
		super();
		
		this.nome = nome;
		this.cognome = cognome;
		this.cf = cf;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCognome() {
		return cognome;
	}

	public void setCognome(String cognome) {
		this.cognome = cognome;
	}

	public String getCf() {
		return cf;
	}

	public void setCf(String cf) {
		this.cf = cf;
	}

	public Date getDatadinascita() {
		return datadinascita;
	}

	public void setDatadinascita(Date datadinascita) {
		this.datadinascita = datadinascita;
	}

	public Date getDatadiassunzione() {
		return datadiassunzione;
	}

	public void setDatadiassunzione(Date datadiassunzione) {
		this.datadiassunzione = datadiassunzione;
	}


	public Compagnia getCompagnia() {
		return compagnia;
	}


	public void setCompagnia(Compagnia compagnia) {
		this.compagnia = compagnia;
	}


	@Override
	public String toString() {
		return "Impiegato [id=" + id + ", nome=" + nome + ", cognome=" + cognome + ", cf=" + cf + ", datadinascita="
				+ datadinascita + ", datadiassunzione=" + datadiassunzione + ", compagnia=" + compagnia + "]";
	}

	

}
