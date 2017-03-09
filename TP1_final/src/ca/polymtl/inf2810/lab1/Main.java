package ca.polymtl.inf2810.lab1;

/**
 * The Main class. Has the main method.
 * 
 * @author Sébastien Chagnon (1804702)
 *
 */
public class Main {

	/**
	 * The method that is called at the beginning of the program
	 * 
	 * @param args
	 *            the arguments passed in the launch command
	 */
	public static void main(String[] args) {
		Modele model = new Modele();
		new PokemonRoute(model);
	}

}
