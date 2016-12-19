import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public abstract class Licencie implements Serializable{
	private int numLicence;
	private String nom;
	private String prenom;
	private Date dateValidite;
	private Club club;
	
	public Licencie(int licence, String nom, String prenom, Date date, Club club){
		this.numLicence = licence;
		this.nom = nom;
		this.prenom = prenom;
		this.dateValidite = date;
		this.club = club;
	}
	
	public int getNumLicence(){
		return numLicence;
	}
	
	public String getNomPrenom(){
		return prenom+" "+nom;
	}
	
	public Club getClub(){
		return club;
	}
	
	public Date getDate(){
		return dateValidite;
	}
	
	public String toString(){
		DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateForm = formatter.format(dateValidite);
		return("Nom : "+nom+" | Prénom : "+prenom+" | Numéro de licence : "+numLicence+" | Date d'expiration : "+ dateForm + " | Club : "+club.toString());
	}
	
	public boolean equals(Licencie l){
		return(this.numLicence == l.numLicence);
	}
}