package attack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

public class DictionaryAttack {
	private static final String PASSWORD_FILE = Paths.get(System.getProperty("user.dir"), "src", "passwordTest.txt").toString();
	
	public static void attack() {
		readPassword();
	}
	
	private static void readPassword() {
		try(BufferedReader reader = new BufferedReader(new FileReader(PASSWORD_FILE))){
			String password = "";
			
			while((password = reader.readLine()) != null) {
				System.out.println(password);
			}
		} 
		catch(IOException e) {
			System.err.println("Erro em ler o arquivo: "+e.getMessage());
		}
	}

}
