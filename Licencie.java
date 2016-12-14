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
		return("Numéro de licence : "+numLicence+" | Nom : "+nom+" | Prénom : "+prenom+" | Date d'expiration : "+ dateForm + " | Club : "+club.toString());
	}
	
	public boolean equals(Licencie l){
		return(this.numLicence == l.numLicence);
	}
	
/*	public static void sauver(File f, ArrayList<Licencie> al){
		try{
			ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream(f));
			sortie.writeObject(al);
			sortie.close();
		}
		catch(FileNotFoundException e){ System.out.println("Fichier de sauvegarde introuvable \nLe fichier est créé.\n");}
		catch(IOException e){ System.out.println("Erreur lors de la lecture du fichier");}
	}
	
	public static ArrayList<Licencie> charger(File f){
		ArrayList<Licencie> al = new ArrayList<Licencie>();
		try{
			ObjectInputStream entree = new ObjectInputStream(new FileInputStream(f));
			al = (ArrayList<Licencie>) entree.readObject();
			entree.close();
		}
		catch(FileNotFoundException e){ System.out.println("Fichier de sauvegarde introuvable");}
		catch(IOException e){ System.out.println("Erreur lors de la lecture du fichier");}
		catch(ClassNotFoundException e){System.out.println(e.getMessage());}
		finally{
			return al;
		}
	}
	
	public static Licencie selectionner(ArrayList<Licencie> al){
		int taille = al.size();
		for(int i = 0 ; i < taille ; i++){
			System.out.println(i+" : "+al.get(i).toString());
		}
		int choix = 0;
		do{
			choix = Menu.lireValeur();
			if(choix < 0 || choix > taille) System.out.println("Choix incorrect");
		}while(choix < 0 && choix > taille);
		Licencie l = al.get(choix);
		return(l);
	}*/

}