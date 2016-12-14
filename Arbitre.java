import java.util.Date;
import java.lang.IllegalArgumentException;

public class Arbitre extends Licencie {
	private int categorie;
	
	public Arbitre(int licence, String nom, String prenom, Date dateValidite,Club club, int categorie) throws IllegalArgumentException{
			super(licence,nom,prenom,dateValidite,club);
			if(categorie>3 || categorie<1){
				throw new IllegalArgumentException();
			}
			else this.categorie=categorie;
	}
	
	public String toString(){
		return(super.toString()+" | Arbitre de catégorie "+categorie);
	}
	
	
	public static Arbitre creerArbitre(){
		System.out.println("Création d'arbitre");
		System.out.println("Entrez le numero de licence :");
		int numLicence = Menu.lireValeur();
		System.out.println("Entrez le nom : ");
		String nom = Menu.lireMot();
		System.out.println("Entrez le prénom : ");
		String prenom = Menu.lireMot();
		System.out.println("Entrez la date de validité de la licence (JJ/MM/AAAA)");
		Date date = Menu.lireDate();
		System.out.println("Entrez le nom du club : ");
		String equipe = Menu.lireMot();
		System.out.println("Entrez la ville du club : ");
		String ville = Menu.lireMot();
		Club club = new Club(equipe,ville);	
		System.out.println("Entrez la catégorie de cet arbitre (entre 1 et 3) :");
		int categorie = 0;
		do{
			categorie=Menu.lireValeur();
		}while(categorie>3 || categorie<1);
		return(new Arbitre(numLicence,nom,prenom,date,club,categorie));		
	}
}
