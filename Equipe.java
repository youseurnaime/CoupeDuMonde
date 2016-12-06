import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Scanner;
import java.io.*;

public class Equipe {
	final static int NB_EQUIPES = 16;
	final static int NB_JOUEURS = 9;
	private Hashtable<Integer,Joueur> lesJoueurs;
	private Hashtable<Integer,Joueur>  lesRemplacants;
	private Club club;
	private Entraineur entraineur;
	private int nbVictoires = 0;
	private int nbDefaites = 0;
	
	public Equipe(Club club){
		this.club = club;
		lesJoueurs = new Hashtable<Integer,Joueur>();
		lesRemplacants = new Hashtable<Integer,Joueur>();
		
	}
	
	public Equipe(Hashtable<Integer,Joueur> lesJoueurs, Hashtable<Integer,Joueur> lesRemplacants, Club club, Entraineur entraineur){
		this.lesJoueurs = lesJoueurs;
		this.lesRemplacants = lesRemplacants;
		this.club = club;
		this.entraineur = entraineur;
	}
	
	public void victoire(){
		nbVictoires++;
	}
	
	public void defaite(){
		nbDefaites++;
	}
	
	public Club getClub(){
		return club;
	}
	
	private void trierJoueurs(){
		//TODO
	}
	
	public void ajouterEntraineur() 
			throws ClubIncorrectException, LicencePerimeeException{
		Date d=new Date();
		Entraineur ent;
		System.out.println("Création de l'entraineur :");
		ent = Entraineur.creerEntraineur(club);
		
		//System.out.println(ent.getDate().toString());
		//Menu.attendre();
		
		try{
			if(!club.equals(ent.getClub())) throw(new ClubIncorrectException("Erreur : l'entraineur appartient à un autre club !"));
			if(ent.getDate().compareTo(d)<0) throw(new LicencePerimeeException("Erreur : la licence de l'entraineur n'est plus valide !"));
			this.entraineur = ent;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ajouterEntraineur();
		}
	}
	
	public void ajouterJoueur(int indice) 
			throws ClubIncorrectException, JoueurEnDoubleException, LicencePerimeeException, PlusieursGardiensException{
		
		Date d=new Date();
		Joueur joueurAjoute;
	
		System.out.println("Création du joueur de champ numéro "+indice+" : ");
		joueurAjoute = Joueur.creerJoueur(club,false);		
		
		
		
		if(!club.equals(joueurAjoute.getClub())) throw(new ClubIncorrectException("Erreur : le joueur joue pour un autre club !"));
		if(lesJoueurs.containsKey(joueurAjoute.getNumLicence()) || lesRemplacants.containsKey(joueurAjoute.getNumLicence())) throw(new JoueurEnDoubleException("Erreur : le joueur est déjà dans l'équipe !"));
		if(joueurAjoute.getDate().compareTo(d)<0) throw(new LicencePerimeeException("Erreur : la licence du joueur n'est plus valide !"));
		if(joueurAjoute.estGardien()) throw(new PlusieursGardiensException("Erreur : un seul gardien par équipe !"));
		this.lesJoueurs.put(joueurAjoute.getNumLicence(),joueurAjoute);
	}

	
	public void ajouterGardien() 
			throws ClubIncorrectException, JoueurEnDoubleException, LicencePerimeeException, PlusieursGardiensException{
		Joueur joueurAjoute;
		Date d=new Date();
		
		System.out.println("Création du gardien titulaire :");
		joueurAjoute = Joueur.creerJoueur(club,true);
	
		if(!club.equals(joueurAjoute.getClub())) throw(new ClubIncorrectException("Erreur : le joueur joue pour un autre club !"));
		if(lesJoueurs.containsKey(joueurAjoute.getNumLicence()) || lesRemplacants.containsKey(joueurAjoute.getNumLicence())) throw(new JoueurEnDoubleException("Erreur : le joueur est déjà dans l'équipe !"));
		if(joueurAjoute.getDate().compareTo(d)<0) throw(new LicencePerimeeException("Erreur : la licence du joueur n'est plus valide !"));
		if(!joueurAjoute.estGardien()) throw(new PlusieursGardiensException("Erreur : veuillez sélectionner un gardien !"));
		this.lesJoueurs.put(joueurAjoute.getNumLicence(),joueurAjoute);
	}
	
	public void ajouterRemplacant(int indice) 
			throws ClubIncorrectException, JoueurEnDoubleException, LicencePerimeeException{
		Joueur joueurAjoute;
		Date d=new Date();
	
		System.out.println("Création du remplaçant numéro "+indice+" : ");
		joueurAjoute = Joueur.creerJoueur(club);
		
		if(!club.equals(joueurAjoute.getClub())) throw(new ClubIncorrectException("Erreur : le joueur joue pour un autre club !"));
		if(lesJoueurs.containsKey(joueurAjoute.getNumLicence()) || lesRemplacants.containsKey(joueurAjoute.getNumLicence())) throw(new JoueurEnDoubleException("Erreur : le joueur est déjà dans l'équipe !"));
		if(joueurAjoute.getDate().compareTo(d)<0) throw(new LicencePerimeeException("Erreur : la licence du joueur n'est plus valide !"));
		lesRemplacants.put(joueurAjoute.getNumLicence(),joueurAjoute);
	}
	
	public static Equipe creerEquipe(){
		System.out.println("-----Créateur d'équipe-----");
		
		System.out.println("Entrez la ville de l'équipe : ");
		String ville = Menu.lireMot();
		System.out.println("Entrez le nom de l'équipe : ");
		String nom = Menu.lireMot();
		Equipe equipe = new Equipe(new Club(nom,ville));
		boolean ok = false;
		
		do{
			try{
				equipe.ajouterEntraineur();
				ok=true;
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}while(!ok);
		
		ok=false;
		do{
			try{
				equipe.ajouterGardien();
				ok=true;
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}while(!ok);
			
		for(int i = 1 ; i < NB_JOUEURS ; i++){
			ok=false;
			do{
				try{
					equipe.ajouterJoueur(i);
					ok=true;
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}while(!ok);
		}
			
			
		int nbRemplacants = 0;
		do{
			System.out.println("Combien souhaitez-vous ajouter de remplaçants ? (le maximum est 5)");
			nbRemplacants = Menu.lireValeur();
		}while(nbRemplacants > 5);
			
		for(int i = 1 ; i < nbRemplacants ; i++){
			ok=false;
			do{
				try{
					equipe.ajouterRemplacant(i);
					ok=true;
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
			}while(!ok);
		}
		
		System.out.println(equipe.toString());
		Menu.attendre();
		return(equipe);
	}
	
	
	
	public String toString(){
		String s = ("\t\t\t"+club.toString()+"\nEntraineur : "+entraineur.toString()+"\nTitulaires :\n");
		Enumeration<Joueur> e = lesJoueurs.elements();
		while(e.hasMoreElements()){
			s += (e.nextElement().toString()+"\n");
		}
		s += ("Remplaçants :\n");
		e = lesRemplacants.elements();
		while(e.hasMoreElements()){
			s += (e.nextElement().toString()+"\n");
		}
		s+=("\nVictoires : "+nbVictoires+" Défaites : "+nbDefaites+"\n");
		return s;
	}
	
	public static void sauver(Hashtable<Integer,Equipe> al){
		try{
			ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream("equipes.txt"));
			sortie.writeObject(al);
			sortie.close();
		}
		catch(FileNotFoundException e){ System.out.println("Fichier de sauvegarde introuvable \nLe fichier est créé.\n");}
		catch(IOException e){ System.out.println("Erreur lors de la lecture du fichier");}
	}
	
	public static Hashtable<Integer,Equipe> charger(){
		Hashtable<Integer,Equipe> al = new Hashtable<Integer,Equipe>();
		try{
			ObjectInputStream entree = new ObjectInputStream(new FileInputStream("equipes.txt"));
			al = (Hashtable<Integer,Equipe>) entree.readObject();
			entree.close();
		}
		catch(FileNotFoundException e){ System.out.println("Fichier de sauvegarde introuvable");}
		catch(IOException e){ System.out.println("Erreur lors de la lecture du fichier");}
		catch(ClassNotFoundException e){System.out.println(e.getMessage());}
		finally{
			return al;
		}
	}
	
	public static Equipe selectionner(Hashtable<Integer,Equipe> al){
		int taille = al.size();
		for(int i = 0 ; i < taille ; i++){
			System.out.println(i+" : \n "+al.get(i).toString()+"\n");
		}
		int choix = 0;
		do{
			choix = Menu.lireValeur();
			if(choix < 0 || choix > taille) System.out.println("Choix incorrect");
		}while(choix < 0 && choix > taille);
		Equipe e = al.get(choix);
		return(e);
	}
	
	
}
