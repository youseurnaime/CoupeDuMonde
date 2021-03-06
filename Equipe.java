import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class Equipe implements Serializable{
	public int id;
	private Hashtable<Integer,Joueur> lesJoueurs;
	private Hashtable<Integer,Joueur>  lesRemplacants;
	private Club club;
	private Entraineur entraineur;
	private int nbVictoires = 0;
	private int nbDefaites = 0;
	
	public Equipe(Club club, int id){
		this.club = club;
		this.id = id;
		lesJoueurs = new Hashtable<Integer,Joueur>();
		lesRemplacants = new Hashtable<Integer,Joueur>();
		
	}
	
	public Equipe(Hashtable<Integer,Joueur> lesJoueurs, Hashtable<Integer,Joueur> lesRemplacants, Club club, Entraineur entraineur){
		this.lesJoueurs = lesJoueurs;
		this.lesRemplacants = lesRemplacants;
		this.club = club;
		this.entraineur = entraineur;
	}
	
	public boolean equals(Equipe e){
		return(e.id==id);
	}
	
	public String getVictoiresDefaites(){
		return ("Victoires : "+nbVictoires+" Défaites : "+nbDefaites);
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
	
	public int getNbJoueurs(){
		return lesJoueurs.size();
	}
	
	private void trierJoueurs(){
		//TODO
	}
	
	public Joueur getGardien(){
		Joueur j = null;
		Enumeration<Integer> k = lesJoueurs.keys();
		while(k.hasMoreElements()){
			j = lesJoueurs.get(k.nextElement());
			if(j.estGardien()) return j;
		}
		return null;
	}
	
	private void ajouterEntraineur() 
			throws ClubIncorrectException, LicencePerimeeException{
		Date d=new Date();
		Entraineur ent;
		System.out.println("Création de l'entraineur :");
		ent = Entraineur.creerEntraineur(club);
		
		try{
			if(!club.equals(ent.getClub())) throw(new ClubIncorrectException("Erreur : l'entraineur appartient à un autre club !"));
			if(ent.getDate().compareTo(d)<0) throw(new LicencePerimeeException("Erreur : la licence de l'entraineur n'est plus valide !"));
			this.entraineur = ent;
		}catch(Exception ex){
			System.out.println(ex.getMessage());
			ajouterEntraineur();
		}
	}
	
	private void ajouterJoueur(int indice) 
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

	
	private void ajouterGardien() 
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
	
	private void ajouterRemplacant(int indice) 
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
	
	public static Equipe creerEquipe(int id){
		System.out.println("-----Créateur d'équipe-----");
		
		System.out.println("Entrez la ville de l'équipe : ");
		String ville = Menu.lireMot();
		System.out.println("Entrez le nom de l'équipe : ");
		String nom = Menu.lireMot();
		Equipe equipe = new Equipe(new Club(nom,ville),id);
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
			
		for(int i = 1 ; i < Menu.NB_JOUEURS ; i++){
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
			
		for(int i = 1 ; i <= nbRemplacants ; i++){
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
		System.out.println("Equipe créée !");
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
	
	public static void sauver(Hashtable<Integer,Equipe> ht){
		File file = new File("equipes.txt");
		FileOutputStream fos = null;
		ObjectOutputStream sortie = null;
		try{
			fos = new FileOutputStream(file);
			sortie = new ObjectOutputStream(fos);
			sortie.writeObject(ht);
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
	
	public static Hashtable<Integer,Equipe> charger(){
		Hashtable<Integer,Equipe> ht = null;
		File file = new File("equipes.txt");
		FileInputStream fis = null;
		ObjectInputStream entree = null;
		try{
			fis = new FileInputStream(file);
			entree = new ObjectInputStream(fis);
			ht = (Hashtable<Integer,Equipe>) entree.readObject();
		}
		catch(FileNotFoundException e){}
		catch(IOException e){ System.out.println("Suite à une fermeture innatendue, les équipes sauvegardées ont été perdues. Veuillez recréer des équipes.");}
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
	
	public static int selectionner(Hashtable<Integer,Equipe> ht){
		Enumeration<Integer> enumId = ht.keys();
		Enumeration<Equipe> enumEquipe = ht.elements();
		
		if(ht == null) System.out.println("Pas d'équipes enregistrées");
		else{
			while(enumId.hasMoreElements()){
				System.out.println(enumId.nextElement()+"   :   "+enumEquipe.nextElement().toString()+"\n\n");
			}
		}
		
		int choix = -1;
		do{
			choix = Menu.lireValeur();
			if(ht.containsKey(choix)) System.out.println("Choix incorrect");
		}while(ht.containsKey(choix));
		return(choix);
	}
	
	public static void ajouterVictoire(int id){
		try{
			Hashtable<Integer,Equipe> lesEquipes = charger();
			Equipe equipe = lesEquipes.get(id);
			equipe.victoire();
			lesEquipes.replace(id, equipe);
			sauver(lesEquipes);
		}catch(Exception e){
			System.out.println("Ajout de victoire impossible !");
		}
	}
	
	public static void ajouterDefaite(int id){
		try{
			Hashtable<Integer,Equipe> lesEquipes = charger();
			Equipe equipe = lesEquipes.get(id);
			equipe.defaite();
			lesEquipes.replace(id, equipe);
			sauver(lesEquipes);
		}catch(Exception e){
			System.out.println("Ajout de défaite impossible !");
		}
	}
	
}
