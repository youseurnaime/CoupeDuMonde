import java.util.*;

public class Match {
	private Equipe equipeA;
	private Equipe equipeB;
	private int scoreA;
	private int scoreB;
	//TODO Heure de début
	private Arbitre arbitre;
	private boolean aVainqueur;
	
	public Match(Equipe eA, Equipe eB, Arbitre arb){
		equipeA = eA;
		equipeB = eB;
		arbitre = arb;
		scoreA = entrerScore(equipeA.getClub().toString());
		scoreB = entrerScore(equipeB.getClub().toString());
		aVainqueur = jouerMatch();
	}
	
	public Equipe getGagnant(){
		if(aVainqueur) return equipeA;
		else return equipeB;
	}
	
	private static int entrerScore(String nomEquipe){
		Scanner sc = new Scanner(System.in);
		System.out.println("Entrez le score pour "+nomEquipe+"(Entrez une lettre pour un score aléatoire)");
		String s = sc.next();
		int score = 0;
		try{
			score = Integer.parseInt(s);
		}catch(Exception e){
			Random rand = new Random();
			score = rand.nextInt(9);
		}
		return score;
	}
	
	private boolean jouerMatch(){
		Equipe gagnant = null;
		if(scoreA==scoreB) gagnant = drible();
		else if(scoreA>scoreB) gagnant = equipeA;
		else if(scoreA<scoreB) gagnant = equipeB;
		if(gagnant.equals(equipeA)){
			equipeA.victoire();
			equipeB.defaite();
			return(true);
		}else{
			equipeA.defaite();
			equipeB.victoire();
			return(false);
		}
	}
	
	private Equipe drible(){
		System.out.println("Le score est nul, les équipes vont être départagées aux jongles");
		System.out.println("Entrez 1 pour "+equipeA.getClub().toString());
		System.out.println("Entrez 2 pour "+equipeB.getClub().toString());
		System.out.println("Entrez une autre valeur pour laisser le hasard décider.");
		int choix = Menu.lireValeur();
		if(choix==1) return equipeA;
		else if(choix==2) return equipeB;
		else{
			Random rand = new Random();
			if(rand.nextBoolean()) return equipeA;
			else return equipeB;
		}
	}
	
	public String toString(){
		String s = "- "+equipeA.getClub().toString()+" vs "+equipeA.getClub().toString();
		//Heure
		s += "\nScore : "+scoreA+" - "+scoreB;
		s += "\nArbitre : "+arbitre.getNomPrenom();
		s += "\nGagnant: "+getGagnant().getClub().toString();
		return s;
	}
}
