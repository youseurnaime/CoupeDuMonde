import java.util.ArrayList;

public class Tour {
	private int numTour;
	private ArrayList<Match> lesMatchs;

	public Tour(int num, ArrayList<Match> matchs)
		throws NombreEquipesIncorrectExeption{
		if(!testPuissanceDe2(matchs.size())){
			throw new NombreEquipesIncorrectExeption("Le nombre d'équipes doit etre une puissance de 2 !");
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
	
}