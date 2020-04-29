package data;

import java.util.Date;

public class Film {
	private String titre;
	private Date dateSortie;
	private String genre;
	private String distributeur;
	private Double budget;
	private Double revenusEtatsUnis;
	private Double revenusMondiaux;
	
	public Film(String titre, Date dateSortie, String genre, String distributeur, Double budget,
			Double revenusEtatsUnis, Double revenusMondiaux) {
		this.titre = titre;
		this.dateSortie = dateSortie;
		this.genre = genre;
		this.distributeur = distributeur;
		this.budget = budget;
		this.revenusEtatsUnis = revenusEtatsUnis;
		this.revenusMondiaux = revenusMondiaux;
	}

	public String getTitre() {
		return titre;
	}

	public Date getDateSortie() {
		return dateSortie;
	}

	public String getGenre() {
		return genre;
	}

	public String getDistributeur() {
		return distributeur;
	}

	public Double getBudget() {
		return budget;
	}

	public Double getRevenusEtatsUnis() {
		return revenusEtatsUnis;
	}

	public Double getRevenusMondiaux() {
		return revenusMondiaux;
	}

	public void setTitre(String titre) {
		this.titre = titre;
	}

	public void setDateSortie(Date dateSortie) {
		this.dateSortie = dateSortie;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public void setDistributeur(String distributeur) {
		this.distributeur = distributeur;
	}

	public void setBudget(Double budget) {
		this.budget = budget;
	}

	public void setRevenusEtatsUnis(Double revenusEtatsUnis) {
		this.revenusEtatsUnis = revenusEtatsUnis;
	}

	public void setRevenusMondiaux(Double revenusMondiaux) {
		this.revenusMondiaux = revenusMondiaux;
	}

	@Override
	public String toString() {
		return "Film [titre=" + titre + ", dateSortie=" + dateSortie + ", genre=" + genre + ", distributeur="
				+ distributeur + ", budget=" + budget.intValue() + ", revenusEtatsUnis=" + revenusEtatsUnis.intValue() + ", revenusMondiaux="
				+ revenusMondiaux.intValue() + "]";
	}
	
	public String getFilmInformationForFilm() {
		return "dateSortie=" + dateSortie + ", genre=" + genre + ", distributeur="
				+ distributeur + ", budget=" + budget.intValue() + ", revenusEtatsUnis=" + revenusEtatsUnis.intValue() + ", revenusMondiaux="
				+ revenusMondiaux.intValue();
	}
	public String getFilmInformationForActor() {
		return "titre=" + titre + ", dateSortie=" + dateSortie + ", genre=" + genre + ", distributeur="
				+ distributeur;
	}
}