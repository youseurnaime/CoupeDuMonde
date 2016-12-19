import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Menu {

	static int NB_JOUEURS = 9;
	static int NB_EQUIPES = 16;
	static boolean SIMULER_MATCHS = true;
	
	public static void main(String[] args) {
		menuPrincipal();
	}
	
	public static void menuPrincipal(){
		System.out.println("• • MENU PRINCIPAL • •");
		System.out.println("Que souhaitez vous faire ?");
		System.out.println("1 : Nouveau Tournoi");
		System.out.println("2 : Gestion des équipes");
		System.out.println("3 : Gestion des arbitres");
		System.out.println("4 : Historique des tournois");
		System.out.println("5 : Historique des matchs");
		System.out.println("6 : Paramètres");
		System.out.println("7 : Quitter");
		
		int choix = 0;
		do{
			choix = lireValeur();
		}while(choix < 1 || choix > 7);
		
		switch(choix){
		case 1: 
			try{
				Hashtable<String,Tournoi> lesTournois = Tournoi.charger();
				if(lesTournois == null) lesTournois = new Hashtable<String,Tournoi>();
				Tournoi t = new Tournoi();	
				lesTournois.put(t.getID(),t);
				Tournoi.sauver(lesTournois);
				menuPrincipal();
				
			}catch(Exception e){
				System.out.println(e.getMessage());
				attendre();
				menuPrincipal();
			}
			break;
		case 2:
			gestionEquipes();
			break;
			
		case 3:
			gestionArbitres();
			break;
		case 4:
			historiqueTournois();
			break;
		case 5:
			System.out.println(Match.getListeMatchs() + "\n");
			attendre();
			menuPrincipal();
		case 6:
			parametres();
			break;
		case 7:
			System.exit(0);
			break;
		}
	}
	
	private static void parametres(){
		do{
			System.out.println("• • PARAMETRES • •");
			System.out.println("/!\\ Attention, en modifiant ces paramètres certaines sauvegardes peuvent être perdues /!\\");
			System.out.println("Que souhaitez vous modifier ?");
			System.out.println("1 : Nombre de joueurs par équipe = "+NB_JOUEURS);
			System.out.println("2 : Nombre d'équipes par tournoi = "+NB_EQUIPES);
			if(SIMULER_MATCHS) System.out.println("3 : Simuler les matchs = Activé");
			else System.out.println("3 : Simuler les matchs = Désactivé");
			System.out.println("4 : Retour");
			
			int choix = 0;
			do{
				choix = lireValeur();
			}while(choix < 1 || choix > 4);
			
			switch(choix){
			case 1:
				System.out.println("Combien de joueurs dans une équipe, remplaçants non compris ?");
				do{
					choix = lireValeur();
				}while(choix < 1);
				NB_JOUEURS = choix;
				break;
			case 2:
				System.out.println("Combien d'équipe dans un tournoi ? (Entrez une puissance de 2)");
				do{
					choix = lireValeur();
				}while(!Tournoi.estPuissanceDe2(choix) || choix < 2);
				NB_EQUIPES = choix;
				break;
			case 3:
				if(SIMULER_MATCHS) SIMULER_MATCHS = false;
				else SIMULER_MATCHS = true;
				break;
			case 4:
				menuPrincipal();
				break;
				
			}
		}while(true);
		
	}
	
	private static void historiqueTournois(){
		Hashtable<String,Tournoi> lesTournois = Tournoi.charger();
		if(lesTournois == null){
			System.out.println("Vous n'avez pas de tournois enregistrés.");
			attendre();
			menuPrincipal();
		}
		else{
			do{
				String tournoiSelectionne = Tournoi.selectionner(lesTournois);
				if(lesTournois.containsKey(tournoiSelectionne)){
					System.out.println(lesTournois.get(tournoiSelectionne).toString()+"\n");
					attendre();
					
				}
				else menuPrincipal();
			}while(true);
		}
	}
	
	public static void gestionArbitres(){
		int choix = 0, nbArbitres = 0;
		ArrayList<Arbitre> lesArbitres = Arbitre.charger();
		if(lesArbitres!=null) nbArbitres = lesArbitres.size();
		else lesArbitres = new ArrayList<Arbitre>();
		
		do{
			System.out.println("• • GESTION DES ARBITRES • •");
			System.out.println("Que souhaitez vous faire ?");
			System.out.println("1 : Nouvel arbitre");
			System.out.println("2 : Supprimer arbitre");
			System.out.println("3 : Voir les arbitres");
			System.out.println("4 : Retour");
			do{
				try{
					choix = lireValeur();
				}
				catch(Exception e){
					System.out.println(e.getMessage());
				}	
			}while(choix < 1 || choix > 4);
			if(lesArbitres!=null) nbArbitres = lesArbitres.size();
			switch(choix){
			case 1: 
				lesArbitres.add(Arbitre.creerArbitre());
				break;
			case 2:
				if(nbArbitres!=0){
					System.out.println("Sélectionnez l'arbitre que vous voulez supprimer : ");
					lesArbitres.remove(Arbitre.selectionner(lesArbitres));
				}else{
					System.out.println("Pas d'arbitres enregistrés");
				}
				attendre();
				break;
			case 3:
				if(nbArbitres!=0){
					for(int i = 0 ; i < nbArbitres ; i++){
						System.out.println(i+"   :   "+lesArbitres.get(i).toString()+"\n");
					}
				}else{
					System.out.println("Pas d'arbitres enregistrés");
				}
				attendre();
				break;
			case 4:
				Arbitre.sauver(lesArbitres);
				menuPrincipal();
				break;
			}
			Arbitre.sauver(lesArbitres);
		}while(true);
	}
	
	public static void gestionEquipes(){
		int choix = 0, nbEquipes = 0;
		Hashtable<Integer,Equipe> lesEquipes = Equipe.charger();
		if(lesEquipes!=null) nbEquipes = lesEquipes.size();
		else lesEquipes = new Hashtable<Integer,Equipe>();
		
		do{
			System.out.println("• • GESTION EQUIPES • •");
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
				nbEquipes++;
				lesEquipes.put(nbEquipes, Equipe.creerEquipe(nbEquipes));
				break;
			case 2:
				if(nbEquipes!=0){
					System.out.println("Sélectionnez celle que vous voulez supprimer : ");
					lesEquipes.remove(Equipe.selectionner(lesEquipes));
					nbEquipes--;
				}else{
					System.out.println("Pas d'équipes enregistrées");
				}
				attendre();
				break;
			case 3:
				if(nbEquipes!=0){
					for(int i = 1 ; i <= nbEquipes ; i++){
						System.out.println(i+"   :   "+lesEquipes.get(i).toString()+"\n\n");
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
	
	public static void attendre(){
		System.out.println("Entrez une lettre pour continuer...");
		try{
			Scanner sc = new Scanner(System.in);
			sc.nextInt();
			sc.close();
		}catch(Exception e){}
		finally{
			System.out.println("\n");
		}
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
