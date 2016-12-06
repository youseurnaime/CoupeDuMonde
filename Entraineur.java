import java.lang.IllegalArgumentException;
import java.util.Date;

public class Entraineur extends Licencie {
	private int niveau;
	
	public Entraineur(int licence, String nom, String prenom, Date dateValidite, Club club, int niveau) throws IllegalArgumentException{
		super(licence,nom,prenom,dateValidite,club);
		if(niveau>5 || niveau<1){
			throw new IllegalArgumentException();
		}
		else this.niveau=niveau;
	}
	
	public String toString(){
		return(super.toString()+" | Entraineur de niveau "+niveau);
	}
	
	public static Entraineur creerEntraineur(){
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
		System.out.println("Entrez le niveau de cet entraineur (entre 1 et 5)");
		int niveau = 0;
		do{
			niveau=Menu.lireValeur();
		}while(niveau>5 || niveau<1);
		return(new Entraineur(numLicence,nom,prenom,date,club,niveau));		
	}
	
	public static Entraineur creerEntraineur(Club club){
		System.out.println("Entrez le numero de licence :");
		int numLicence = Menu.lireValeur();
		System.out.println("Entrez le nom : ");
		String nom = Menu.lireMot();
		System.out.println("Entrez le prénom : ");
		String prenom = Menu.lireMot();
		System.out.println("Entrez la date de validité de la licence (JJ/MM/AAAA)");
		Date date = Menu.lireDate();	
		System.out.println("Entrez le niveau de cet entraineur (entre 1 et 5)");
		int niveau = 0;
		do{
			niveau=Menu.lireValeur();
		}while(niveau>5 || niveau<1);
		return(new Entraineur(numLicence,nom,prenom,date,club,niveau));		
	}
}
