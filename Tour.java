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

	public Tour(int num, ArrayList<Match> matchs)
		throws NombreEquipesIncorrectException{
		if(!testPuissanceDe2(matchs.size())){
			throw new NombreEquipesIncorrectException("Le nombre d'équipes doit etre une puissance de 2 !");
		}
		else{
			numTour = num;
			lesMatchs = matchs;
		}
	}
	
	public static boolean testPuissanceDe2(int n){
		if((n%2)!=0) return false;
		if((n/2)==1) return true;
		else return testPuissanceDe2(n/2);
	}
	
	public ArrayList<Equipe> getLesGagnants(){
		ArrayList<Equipe> lesGagnants = new ArrayList<Equipe>();
		for(int i = 0 ; i < lesMatchs.size() ; i++){
			lesGagnants.add(lesMatchs.get(i).getGagnant());
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
	
	public static void sauver(Hashtable<Integer,Tour> lesTours){
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