import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class Match implements Serializable{
	private Equipe equipeA;
	private Equipe equipeB;
	private int scoreA;
	private int scoreB;
	private String heureDebut;
	private Arbitre arbitre;
	private boolean aVainqueur;
	final private static Random RANDOM = new Random();
	
	public Match(Equipe eA, Equipe eB, Arbitre arb){
		equipeA = eA;
		equipeB = eB;
		arbitre = arb;
		scoreA = 0;
		scoreB = 0;
		Date dateDebut = new Date();
		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy' 'HH'h'mm");
		heureDebut = dateFormat.format(dateDebut);
		aVainqueur = simulerMatch();
		sauver();
	}
	
	public Equipe getGagnant(){
		if(aVainqueur) return equipeA;
		else return equipeB;
	}
	
	public boolean simulerMatch(){
		int event = 0;
		System.out.println("- "+equipeA.getClub().toString()+" vs "+equipeB.getClub().toString()+" -");
		System.out.println("Début du match : "+heureDebut);
		System.out.println("Arbitre : "+arbitre.getNomPrenom());
		
		System.out.println("Entrez une lettre pour donner le coup d'envoi...");
		try{
			Scanner sc = new Scanner(System.in);
			sc.nextInt();
			sc.close();
		}catch(Exception e){}
		
		for(int i = 0 ; i < 91 ; i++){
			event = RANDOM.nextInt(100);
			if(event<5){
				System.out.println(i+"' : but des "+equipeA.getClub().toString());
				scoreA++;
				System.out.println("\t\t"+scoreA+" - "+scoreB);
			}
			if(event>95){
				System.out.println(i+"' : but des "+equipeB.getClub().toString());
				scoreB++;
				System.out.println("\t\t"+scoreA+" - "+scoreB);
			}		
		}
		System.out.println("Score à la fin du temps réglementaire : ");
		System.out.println(equipeA.getClub().toString()+" : "+scoreA);
		System.out.println(equipeB.getClub().toString()+" : "+scoreB);
		if(scoreA>scoreB){
			System.out.println(equipeA.getClub().toString()+" remporte le match !");
			Equipe.ajouterVictoire(equipeA.id);
			Equipe.ajouterDefaite(equipeB.id);
			Menu.attendre();
			return true;
		}
		else if(scoreA<scoreB){
			System.out.println(equipeB.getClub().toString()+" remporte le match !");
			Equipe.ajouterVictoire(equipeB.id);
			Equipe.ajouterDefaite(equipeA.id);
			Menu.attendre();
			return false;
		}
		else{
			if(drible()){
				System.out.println(equipeA.getClub().toString()+" remporte le match !");
				Equipe.ajouterVictoire(equipeA.id);
				Equipe.ajouterDefaite(equipeB.id);
				Menu.attendre();
				return true;
			}
			else{
				System.out.println(equipeB.getClub().toString()+" remporte le match !");
				Equipe.ajouterVictoire(equipeB.id);
				Equipe.ajouterDefaite(equipeA.id);
				Menu.attendre();
				return false;
			}
		}
	}
	
	public Equipe getEquipeA(){
		return equipeA;
	}
	
	public Equipe getEquipeB(){
		return equipeB;
	}
	
	private boolean drible(){
		System.out.println("Le score est nul, les équipes vont être départagées aux jongles");
		System.out.println("Entrez 1 pour "+equipeA.getClub().toString());
		System.out.println("Entrez 2 pour "+equipeB.getClub().toString());
		System.out.println("Entrez une autre valeur pour laisser le hasard décider.");
		int choix = Menu.lireValeur();
		if(choix==1) return true;
		else if(choix==2) return false;
		else{
			Random rand = new Random();
			if(rand.nextBoolean()) return true;
			else return false;
		}
	}
	
	public String toString(){
		String s = "- "+equipeA.getClub().toString()+" vs "+equipeB.getClub().toString()+" -";
		s += "\nDate : "+heureDebut;
		s += "\nScore : "+scoreA+" - "+scoreB;
		s += "\nArbitre : "+arbitre.getNomPrenom();
		s += "\nGagnant: "+getGagnant().getClub().toString();
		return s;
	}
	
	
	private void sauver(){
		String s = getListeMatchs() + "\n" + toString();
		File file = new File("listeMatchs.txt");
		FileOutputStream fos = null;
		ObjectOutputStream sortie = null;
		try{
			fos = new FileOutputStream(file);
			sortie = new ObjectOutputStream(fos);
			sortie.writeObject(s);
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
	
	public static String getListeMatchs(){
		String s = "";
		File file = new File("listeMatchs.txt");
			FileInputStream fis = null;
			ObjectInputStream entree = null;
			try{
				fis = new FileInputStream(file);
				entree = new ObjectInputStream(fis);
				s = (String) entree.readObject();
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
			return s;
		
	}
}
