import java.io.Serializable;
import java.util.ArrayList;

public class Tour implements Serializable {
	private int numTour;
	private ArrayList<Match> lesMatchs;

	public Tour(ArrayList<Match> matchs){
		lesMatchs = matchs;
		numTour = calculerNumTour();
	}
	
	private int calculerNumTour(){
		int n = 1;
		int max = Menu.NB_EQUIPES/2;
		while(max!=this.lesMatchs.size()){
			max = max/2;
			n++;
		}
		return n;
	}
	
	public ArrayList<Equipe> getLesGagnants(){
		ArrayList<Equipe> lesGagnants = new ArrayList<Equipe>();
		for(int i = 0 ; i < lesMatchs.size() ; i++){
			lesGagnants.add(lesMatchs.get(i).getGagnant());
		}
		return lesGagnants;
	}
	
	public String toString(){
		String s = "Tour n°"+numTour+"\n\n";
		s += "Equipes : \n";
		for(int i = 0 ; i < lesMatchs.size() ; i++){
			s+= lesMatchs.get(i).getEquipeA().getClub().toString() + "\n";
			s+= lesMatchs.get(i).getEquipeB().getClub().toString() + "\n";
		}
		s += "\nRencontres : \n";
		for(int i = 0 ; i < lesMatchs.size() ; i++){
			s += lesMatchs.get(i).toString()+"\n\n";
		}
		return s+"\n";
	}
	
	
}