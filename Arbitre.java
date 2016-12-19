import java.util.ArrayList;
import java.util.Date;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
		Arbitre arb = new Arbitre(numLicence,nom,prenom,date,club,categorie);
		return(arb);		
	}
	
	public static void sauver(ArrayList<Arbitre> al){
		File file = new File("arbitres.txt");
		FileOutputStream fos = null;
		ObjectOutputStream sortie = null;
		try{
			fos = new FileOutputStream(file);
			sortie = new ObjectOutputStream(fos);
			sortie.writeObject(al);
		}
		catch(FileNotFoundException e){ System.out.println("Fichier de sauvegarde introuvable \nLe fichier est créé.\n");}
		catch(IOException e){ System.out.println("Erreur lors de la lecture du fichier");}
		finally{
			if(fos!=null){
				try{
					fos.flush();
					fos.close();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
		}
	}
	
	public static ArrayList<Arbitre> charger(){
		ArrayList<Arbitre> al = null;
		File file = new File("arbitres.txt");
		FileInputStream fis = null;
		ObjectInputStream entree = null;
		try{
			fis = new FileInputStream(file);
			entree = new ObjectInputStream(fis);
			al = (ArrayList<Arbitre>) entree.readObject();
		}
		catch(FileNotFoundException e){ System.out.println("Fichier de sauvegarde introuvable");}
		catch(IOException e){ System.out.println("Erreur lors de la lecture du fichier");}
		catch(ClassNotFoundException e){System.out.println(e.getMessage());}
		finally{
			if(fis!=null){
				try{
					fis.close();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}
			}
			
		}
		return al;
	}
	
	public static int selectionner(ArrayList<Arbitre> al){
		int taille = al.size();
		
		
		if(taille!=0){
			
			for(int i = 0 ; i < taille ; i++){
				System.out.println(i+"   :   "+al.get(i).toString()+"\n");
			}
		}else{
			System.out.println("Pas d'arbitres enregistrés");
		}
		
		int choix = -1;
		do{
			choix = Menu.lireValeur();
			if(choix < 0 || choix >= taille) System.out.println("Choix incorrect");
		}while(choix < 0 || choix >= taille);
		return(choix);
	}
	
	
}



