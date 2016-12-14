import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Hashtable;

public class Tour {
	private int numTour;
	private ArrayList<Match> lesMatchs;

	public Tour(ArrayList<Match> matchs)
		throws NombreEquipesIncorrectException{
		if(!testPuissanceDe2(matchs.size())){
			throw new NombreEquipesIncorrectException("Le nombre d'équipes doit etre une puissance de 2 !");
		}
		else{
			lesMatchs = matchs;
			numTour = calculerNumTour();
		}
	}
	
	public static boolean testPuissanceDe2(int n){
		if((n%2)!=0) return false;
		if((n/2)==1) return true;
		else return testPuissanceDe2(n/2);
	}
	
	private int calculerNumTour(){
		int n = 1;
		int max = Equipe.NB_EQUIPES;
		while(max!=this.lesMatchs.size()){
			max = max/2;
			n++;
		}
		return n;
	}
	
	public Hashtable<Integer,Equipe> getLesGagnants(){
		Hashtable<Integer,Equipe> lesGagnants = new Hashtable<Integer,Equipe>();
		for(int i = 0 ; i < lesMatchs.size() ; i++){
			lesGagnants.put(i+1,lesMatchs.get(i).getGagnant());
		}
		return lesGagnants;
	}
	
	public String toString(){
		String s = "Tour n°"+numTour;
		for(int i = 0 ; i < lesMatchs.size() ; i++){
			s += lesMatchs.get(i).toString();
		}
		return s+"\n";
	}
	
	public static void sauver(Hashtable<Integer,Tour> lesTours){//Les sauvegarder 1 par 1 plutot 
		try{
			ObjectOutputStream sortie = new ObjectOutputStream(new FileOutputStream("equipes.txt"));
			sortie.writeObject(lesTours);
			sortie.close();
		}
		catch(FileNotFoundException e){ System.out.println("Fichier de sauvegarde introuvable \nLe fichier est créé.\n");}
		catch(IOException e){ System.out.println("Erreur lors de la lecture du fichier");}
	}
	
	public static Hashtable<Integer,Tour> charger(){
		Hashtable<Integer,Tour> lesTours = new Hashtable<Integer,Tour>();
		try{
			ObjectInputStream entree = new ObjectInputStream(new FileInputStream("equipes.txt"));
			lesTours = (Hashtable<Integer,Tour>) entree.readObject();
			entree.close();
		}
		catch(FileNotFoundException e){ System.out.println("Fichier de sauvegarde introuvable");}
		catch(IOException e){ System.out.println("Erreur lors de la lecture du fichier");}
		catch(ClassNotFoundException e){System.out.println(e.getMessage());}
		finally{
			return lesTours;
		}
	}
	
}