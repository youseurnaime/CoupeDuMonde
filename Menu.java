import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Menu {

	public static void main(String[] args) {
		menuPrincipal();
	}
	
	public static void menuPrincipal(){
		System.out.println("----- MENU PRINCIPAL -----");
		System.out.println("Que souhaitez vous faire ?");
		System.out.println("1 : Gestion des équipes");
		System.out.println("2 : Nouveau tournoi");
		System.out.println("3 : Quitter");
		
		int choix = 0;
		do{
			choix = lireValeur();
		}while(choix < 1 || choix > 2);
		
		switch(choix){
		case 1: 
			gestionEquipes();
			break;
			
		case 2:
			try{
				tournoi(initTournoi());			
				
			}catch(Exception e){
				System.out.println(e.getMessage());
				attendre();
				menuPrincipal();
			}
			break;
			
		case 3:
			break;
		}
	}
	
	public static Hashtable<Integer,Equipe> initTournoi() throws PasAssezDEquipesException{
		Hashtable<Integer,Equipe> lesEquipes = Equipe.charger();
		Hashtable<Integer,Equipe> equipesTournoi = lesEquipes;
		if(lesEquipes.size() < Equipe.NB_EQUIPES) throw new PasAssezDEquipesException("Il vous manque "+(Equipe.NB_EQUIPES-lesEquipes.size())+" équipes pour pouvoir lancer un tournoi !");
		else if(lesEquipes.size() > Equipe.NB_EQUIPES){
			int choix = 0;
			do{
				choix=Equipe.selectionner(equipesTournoi);
				
				equipesTournoi.remove(choix);
				
			}while(equipesTournoi.size() != Equipe.NB_EQUIPES);
			
		}
		Equipe.sauver(lesEquipes);
		return equipesTournoi;
	}
	
	public static void tournoi(Hashtable<Integer,Equipe> lesEquipes){
		System.out.println("---Tournoi---");
		while(lesEquipes.size() != 1){
			System.out.println(lesEquipes.get(3).toString());
			lesEquipes = jouerTour(lesEquipes);
		}
	}
	
	public static Hashtable<Integer,Equipe> jouerTour(Hashtable<Integer,Equipe> lesEquipes){
		int nbEquipes = lesEquipes.size();
		Random rand = new Random();
		int r1 = 0;
		int r2 = 0;
		Equipe equipe1 = null;
		Equipe equipe2 = null;
		ArrayList<Match> lesMatchs = new ArrayList<Match>();
		
		while(!lesEquipes.isEmpty()){
			
			nbEquipes = lesEquipes.size();
			r1 = rand.nextInt(nbEquipes+1);
			System.out.println(r1);
			System.out.println(lesEquipes.get(1).toString());
			attendre();
			equipe1 = lesEquipes.get(r1);
			lesEquipes.remove(r1);
			System.out.println(equipe1.toString());
			
			nbEquipes = lesEquipes.size();
			r2 = rand.nextInt(nbEquipes+1);
			System.out.println(r2);
			attendre();
			equipe1 = lesEquipes.get(r2);
			lesEquipes.remove(r2);
			
			System.out.println(equipe2.toString());
			attendre();
			
			System.out.println("Match entre "+equipe1.toString()+" et "+equipe2.toString());
			
			attendre();
			lesMatchs.add(new Match(equipe1,equipe2,Arbitre.creerArbitre())); 
			//TODO : sauvegarde des arbitres
		}
		try{
			Tour tour = new Tour(lesMatchs);
			System.out.println(tour.toString());
			attendre();
			//TODO : sauvegarde des tours
			return(tour.getLesGagnants());
		}catch(Exception e){
			System.out.println(e.getMessage());
			attendre();
			return null;//a changer
			//menuPrincipal();
		}
	}
	
	public static void gestionEquipes(){
		int choix = 0, nbEquipes = 0;
		Hashtable<Integer,Equipe> lesEquipes = Equipe.charger();
		if(lesEquipes!=null) nbEquipes = lesEquipes.size();
		else lesEquipes = new Hashtable<Integer,Equipe>();
		
		do{
			System.out.println("----- GESTION EQUIPES -----");
			System.out.println("Que souhaitez vous faire ?");
			System.out.println("1 : Nouvelle équipe");
			System.out.println("2 : Supprimer équipe");
			System.out.println("3 : Voir les équipes");
			System.out.println("4 : Retour");
			do{
				try{
					choix = lireValeur();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}	
			}while(choix < 1 || choix > 4);
			if(lesEquipes!=null) nbEquipes = lesEquipes.size();
			switch(choix){
			case 1: 
				lesEquipes.put(nbEquipes,Equipe.creerEquipe());
				break;
			case 2:
				if(nbEquipes!=0){
					System.out.println("Sélectionnez celle que vous voulez supprimer : ");
					lesEquipes.remove(Equipe.selectionner(lesEquipes));
				}else{
					System.out.println("Pas d'équipes enregistrées");
				}
				attendre();
				break;
			case 3:
				if(nbEquipes!=0){
					Enumeration<Equipe> e = lesEquipes.elements();
					Enumeration<Integer> k = lesEquipes.keys();
					while(e.hasMoreElements()){
						System.out.println(k.nextElement()+"   :   "+e.nextElement().toString()+"\n\n");
					}
				}else{
					System.out.println("Pas d'équipes enregistrées");
				}
				attendre();
				break;
			case 4:
				Equipe.sauver(lesEquipes);
				menuPrincipal();
				break;
			}
			Equipe.sauver(lesEquipes);
		}while(true);
	}
	
	/*public static void gestionLicencies(){
		System.out.println("----- GESTION LICENCIES -----");
		System.out.println("Que souhaitez vous faire ?");
		System.out.println("1 : Gestion des joueurs");
		System.out.println("2 : Gestion des entraineurs");
		System.out.println("3 : Gestion des arbitres");
		System.out.println("4 : Retour");
		int choix = 0;
		do{
			choix = lireValeur();
		}while(choix < 1 || choix > 4);
		
		switch(choix){
		case 1: 
			gestionJoueurs();
			break;
		case 2:
			gestionEntraineurs();
			break;
		case 3:
			gestionArbitres();
			break;
		case 4:
			menuPrincipal();
			break;
		}
	}
	
	public static void gestionJoueurs(){
		int choix = 0;
		File f = new File("joueurs.txt");
		ArrayList<Licencie> lesJoueurs = Licencie.charger(f);
		
		do{
			System.out.println("----- GESTION JOUEURS -----");
			System.out.println("Que souhaitez vous faire ?");
			System.out.println("1 : Nouveau joueur");
			System.out.println("2 : Supprimer joueur");
			System.out.println("3 : Voir les joueurs");
			System.out.println("4 : Retour");
			try{
				do{
					choix = lireValeur();
				}while(choix < 1 || choix > 4);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
			
			switch(choix){
			case 1: 
				lesJoueurs.add(Joueur.creerJoueur());
				break;
			case 2:
				System.out.println("Sélectionnez celui que vous voulez supprimer : ");
				lesJoueurs.remove(Licencie.selectionner(lesJoueurs));
				break;
			case 3:
				for(Licencie j: lesJoueurs){
					System.out.println(j.toString());
				}
				attendre();
				break;
			case 4:
				Licencie.sauver(f, lesJoueurs);
				gestionLicencies();
				break;
			}
		}while(true);
	}
	
	public static void gestionEntraineurs(){
		int choix = 0;
		File f = new File("entraineurs.txt");
		ArrayList<Licencie> lesEntraineurs = Licencie.charger(f);
		
		do{
			System.out.println("----- GESTION ENTRAINEURS -----");
			System.out.println("Que souhaitez vous faire ?");
			System.out.println("1 : Nouvel entraineur");
			System.out.println("2 : Supprimer entraineur");
			System.out.println("3 : Voir les entraineurs");
			System.out.println("4 : Retour");
			try{
				do{
					choix = lireValeur();
				}while(choix < 1 || choix > 4);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
			
			switch(choix){
			case 1: 
				lesEntraineurs.add(Entraineur.creerEntraineur());
				break;
			case 2:
				System.out.println("Sélectionnez celui que vous voulez supprimer : ");
				lesEntraineurs.remove(Licencie.selectionner(lesEntraineurs));
				break;
			case 3:
				int taille = lesEntraineurs.size();
				for(int i = 0 ; i < taille ; i++){
					System.out.println(i+" : "+lesEntraineurs.get(i).toString());
				}
				attendre();
				break;
			case 4:
				Licencie.sauver(f, lesEntraineurs);
				gestionLicencies();
				break;
			}
		}while(true);
	}
	
	public static void gestionArbitres(){
		int choix = 0;
		File f = new File("arbitres.txt");
		ArrayList<Licencie> lesArbitres = Licencie.charger(f);
		
		do{
			System.out.println("----- GESTION ARBITRES -----");
			System.out.println("Que souhaitez vous faire ?");
			System.out.println("1 : Nouvel arbitre");
			System.out.println("2 : Supprimer arbitre");
			System.out.println("3 : Voir les arbitres");
			System.out.println("4 : Retour");
			try{
				do{
					choix = lireValeur();
				}while(choix < 1 || choix > 4);
			}
			catch(Exception e){
				System.out.println(e.getMessage());
			}
			
			switch(choix){
			case 1: 
				lesArbitres.add(Arbitre.creerArbitre());
				break;
			case 2:
				System.out.println("Sélectionnez celui que vous voulez supprimer : ");
				lesArbitres.remove(Licencie.selectionner(lesArbitres));
				break;
			case 3:
				int taille = lesArbitres.size();
				for(int i = 0 ; i < taille ; i++){
					System.out.println(i+" : "+lesArbitres.get(i).toString());
				}
				attendre();
				break;
			case 4:
				Licencie.sauver(f, lesArbitres);
				gestionLicencies();
				break;
			}
		}while(true);
	}*/
	
	public static void attendre(){
		System.out.println("Entrez une valeur pour continuer...");
		try{
			Scanner sc = new Scanner(System.in);
			sc.nextInt();
			sc.close();
		}catch(Exception e){}
	}
	
	public static String lireMot(){
		Scanner sc = new Scanner(System.in);
		String s = "";
		do{
			try{
				s = sc.next();
	
				return s;
			}catch(Exception e){
				System.out.println("Valeur incorrecte !");
				return lireMot();
			}
		}while(true);
	}
	
	public static int lireValeur(){
		Scanner sc = new Scanner(System.in);
		int val = 0;
		try{
			val = sc.nextInt();
			return val;
		}catch(Exception e){
			System.out.println("Valeur incorrecte !");
			return lireValeur();
		}
	}
	
	public static boolean lireON(){
		Scanner sc = new Scanner(System.in);
		String choix ="";
		do{
			try{
				choix = sc.next();
				return(choix.equals("o")||choix.equals("O"));
			}catch(Exception e){
				System.out.println("Valeur incorrecte !");
				return lireON();
			}
		}while(true);
	}
	
	public static Date lireDate(){
		Scanner sc = new Scanner(System.in);
		DateFormat dfm = new SimpleDateFormat("yyyy-MM-dd");
		StringTokenizer st;
		int jour,mois,annee;
		do{
			try{
				st = new StringTokenizer(sc.next(),"/");
	
				jour = Integer.parseInt(st.nextToken());
				mois = Integer.parseInt(st.nextToken());
				annee = Integer.parseInt(st.nextToken());
				Date date = dfm.parse(annee+"-"+mois+"-"+jour);
				return date;
			}catch(Exception e){
				System.out.println("Mauvais format de date !");
				return lireDate();
			}
		}while(true);
	}
}
