import java.io.*;

public class Club implements Serializable{
	private String nom;
	private String ville;
	
	public Club(String n, String v){
		this.nom = n;
		this.ville = v;
	}
	
	public String toString(){
		return(nom+" de "+ville);
	}
}
