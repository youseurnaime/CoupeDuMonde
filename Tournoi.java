import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;


public class Tournoi implements Serializable{
	
	
	final private static Random RANDOM = new Random();
	
	private String id;
	private ArrayList<Tour> lesTours;
	private ArrayList<Equipe> lesEquipes;
	private Equipe vainqueur;
	
	
	
	public Tournoi() throws PasAssezDEquipesException, NombreEquipesIncorrectException, TournoiInvalideException{
		lesEquipes=init();
		
		for(int i = 0 ; i < lesEquipes.size() ; i++){
			if(lesEquipes.get(i).getNbJoueurs() != Menu.NB_JOUEURS) throw new TournoiInvalideException("Erreur : certaines équipes ne contiennent pas le nombre de joueurs imposé.");
		}
		
		Menu.attendre();
		System.out.println("Entrez le nom de ce tournois (celui-ci vous permettra de consulter les résultats plus tard)");
		this.id = Menu.lireMot();
		
		
		System.out.println("• Début du tournoi •");
		ArrayList<Equipe> lesQualifies = lesEquipes;
		int numTour = 1;
		
		Menu.attendre();
		while(lesQualifies.size() != 1){
			if(!estPuissanceDe2(lesQualifies.size())) throw new NombreEquipesIncorrectException("Le nombre d'équipes doit etre une puissance de 2 !");
			if(lesQualifies.size()==2) System.out.println("• Finale •");
			else System.out.println("• Tour n°"+numTour);
			System.out.println("Equipes qualifiées : ");
			for(int i = 0 ; i < lesQualifies.size() ; i++){
				System.out.println(lesQualifies.get(i).getClub().toString());
			}
			Menu.attendre();
			lesQualifies = jouerTour(lesQualifies);
			numTour++;
		}
		this.vainqueur = lesQualifies.get(0);
		System.out.println("Vainqueur du tournoi : "+vainqueur.getClub().toString());
		Menu.attendre();
		System.out.println(toString());
		
	}
	
	private ArrayList<Equipe> init() throws PasAssezDEquipesException, NombreEquipesIncorrectException{
		Hashtable<Integer,Equipe> toutesLesEquipes = Equipe.charger();
		this.lesEquipes = new ArrayList<Equipe>();
		Enumeration<Equipe> enumEquipes= toutesLesEquipes.elements();
		while(enumEquipes.hasMoreElements()) this.lesEquipes.add(enumEquipes.nextElement());
		
		this.lesTours = new ArrayList<Tour>();
		if(lesEquipes == null) throw new PasAssezDEquipesException("Aucune équipe enregistrée ! Créez des équipes pour pouvoir commencer un tournoi.");
		if(lesEquipes.size() < Menu.NB_EQUIPES) throw new PasAssezDEquipesException("Il vous manque "+(Menu.NB_EQUIPES-lesEquipes.size())+" équipes pour pouvoir lancer un tournoi !");
		else if(lesEquipes.size() > Menu.NB_EQUIPES){
			int choix = -1;
			System.out.println("- Séléction des participants -");
			
			do{
				
				System.out.println("Séléctionnez une équipe qui ne participera pas au tourois :");		
				for(int i = 0 ; i < lesEquipes.size() ; i++){
					System.out.println(lesEquipes.get(i).id + " : " + lesEquipes.get(i).getClub().toString());
				}
				choix = Menu.lireValeur();
				if(choix < 0 || choix >= lesEquipes.size()) System.out.println("Choix incorrect !");
				else lesEquipes.remove(choix);
			}while(lesEquipes.size() != Menu.NB_EQUIPES);
			
		}
		
		Equipe.sauver(toutesLesEquipes);
		return lesEquipes;
	}
	
	public static boolean estPuissanceDe2(int n){
		while((n%2)==0) n /= 2;
		return(n==1);
	}
	
	public String getID(){
		return this.id;
	}
	
	
	private ArrayList<Equipe> jouerTour(ArrayList<Equipe> lesQualifies){
		int nbEquipes = lesQualifies.size();
		Equipe equipe1 = null;
		Equipe equipe2 = null;
		Arbitre arb = null;
		int tirage = 0;
		ArrayList<Match> lesMatchs = new ArrayList<Match>();
		ArrayList<Arbitre> lesArbitres = null;
		
		while(!lesQualifies.isEmpty()){
			
			nbEquipes = lesQualifies.size();
			tirage = RANDOM.nextInt(nbEquipes);
			equipe1 = lesQualifies.get(tirage);
			lesQualifies.remove(tirage);
			
			nbEquipes = lesQualifies.size();
			tirage = RANDOM.nextInt(nbEquipes);
			equipe2 = lesQualifies.get(tirage);
			lesQualifies.remove(tirage);
			
			System.out.println("Match entre "+equipe1.getClub().toString()+" et "+equipe2.getClub().toString());
			
			Menu.attendre();
			
			System.out.println("Sélection de l'arbitre");
			lesArbitres = Arbitre.charger();
			if(lesArbitres!=null){
				System.out.println("Voulez-vous sélectionner un arbitre existant ? (o/n);");
				if(Menu.lireON()){
					System.out.println("Sélectionnez l'arbitre que vous souhaitez : ");
					arb = lesArbitres.get(Arbitre.selectionner(lesArbitres));
				}
				else{
					arb = Arbitre.creerArbitre();
					lesArbitres.add(arb);
					Arbitre.sauver(lesArbitres);
				}
			}
			else {
				lesArbitres = new ArrayList<Arbitre>();
				arb = Arbitre.creerArbitre();
				lesArbitres.add(arb);
				Arbitre.sauver(lesArbitres);
			}
			lesMatchs.add(new Match(equipe1,equipe2,arb));
		}
		
		try{
			Tour tour = new Tour(lesMatchs);
			this.lesTours.add(tour);
			System.out.println("Résultats du "+tour.toString());
			Menu.attendre();
			return(tour.getLesGagnants());
		}catch(Exception e){
			System.out.println(e.getMessage());
			Menu.attendre();
			Menu.menuPrincipal();
			return null;
		}
	}
	
	public String toString(){
		String s = "• Tournoi : "+id+"\n";
		s += "\nRésumé des tours : \n";
		for(int i = 0 ; i < lesTours.size() ; i++){
			s += lesTours.get(i).toString() + "\n";
		}
		s += "\nVainqueur : " + vainqueur.toString();
		return s;
	
	}
	
	public static void sauver(Hashtable<String,Tournoi> ht){
		File file = new File("tournois.txt");
		FileOutputStream fos = null;
		ObjectOutputStream sortie = null;
		try{
			fos = new FileOutputStream(file);
			sortie = new ObjectOutputStream(fos);
			sortie.writeObject(ht);
		}
		catch(FileNotFoundException e){ System.out.println("Fichier de sauvegarde introuvable \nLe fichier est créé.\n");}
		catch(IOException e){ System.out.println("Erreur de sauvegarde");}
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
	
	public static Hashtable<String,Tournoi> charger(){
		Hashtable<String,Tournoi> ht = null;
		File file = new File("tournois.txt");
		FileInputStream fis = null;
		ObjectInputStream entree = null;
		try{
			fis = new FileInputStream(file);
			entree = new ObjectInputStream(fis);
			ht = (Hashtable<String,Tournoi>) entree.readObject();
		}
		catch(FileNotFoundException e){return null;}
		catch(IOException e){ 
			System.out.println("Erreur lors de la lecture du fichier");
			return null;
			}
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
		return ht;
	}
	
	public static String selectionner(Hashtable<String,Tournoi> ht){
		if(ht == null) System.out.println("Aucun tournoi enregistré.");
		else{
			System.out.println("Tournois enregistrés : ");
			Enumeration<String> e = ht.keys();
			String s = "";
			while(e.hasMoreElements()){
				s = e.nextElement();
				System.out.println(s);
			}
			System.out.println("Entrez le nom du tournoi à charger. Nom incorrect = retour ");
			String entree = Menu.lireMot();
			if(ht.containsKey(entree)){
				return entree;
			}
			
		}
		return "";
	}
}
