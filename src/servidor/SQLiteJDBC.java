package servidor;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;




public class SQLiteJDBC {
	Connection c = null;
	Memcached memcached;
	Config config;
	String databasePath = "files/trabDB.sqlite";
	
	
	public SQLiteJDBC(Config config) throws Exception {
		this.config = config;
		memcached = new Memcached(config.getMemServidor(),config.getMemPorta());
		Class.forName("org.sqlite.JDBC");
		if(!Util.fileExists(databasePath)){
			throw new Exception("Database file '"+databasePath+"' not found");
		}
		c = DriverManager.getConnection("jdbc:sqlite:"+databasePath);
		c.setAutoCommit(false);
		
		atualizarListaServidores(true);
		
	}
	public void close() throws Exception{
		try {
			c.close();
			atualizarListaServidores(false);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			throw new Exception("Servidor Indisponível");
		}
	}
	
	public String GetData(String periodo, String playerName, String clubName, boolean isPretty) throws ServerException {
		
		String output = "";
		try {
			if(periodo.equals("") && playerName.equals("") && clubName.equals("")) {
				output = GetAnos();
			}else if(playerName.equals("") && clubName.equals("")) {
				output = GetDadosByAno(periodo, isPretty);
			}else if(clubName.equals("")) {
				output = GetDadosByPlayer(periodo,playerName, isPretty);
			}else if(playerName.equals("")) {
				output = GetDadosByClube(periodo,clubName, isPretty);
			}else {
				output = GetDados(periodo,playerName,clubName, isPretty);
			}
		}catch(ServerException e1) {
			throw e1;
		}catch (Exception e) {
			throw new ServerException(0, e.getMessage());
		}
		return output;
	}
	
	private String GetAnos() throws Exception {
		return buscarAnosDisponiveis();
	}
	
	
	private String GetDadosByAno(String periodo, boolean isPretty) throws Exception {
		Statement stmt = null;
		String output = "";
		try {
			stmt = c.createStatement();
			ResultSet rs = stmt.executeQuery( "SELECT SUM(Wins), SUM(Losses) FROM Times WHERE Ano = '"+periodo+"';" );
			while ( rs.next() ) {
				if(rs.getInt(1) == 0 && rs.getInt(2)==0) {
					throw new Exception("Dados Inexistentes");
				}
				output = returnJSON(rs.getInt(1),rs.getInt(2),isPretty);
				
		      }
			
			rs.close();
		    stmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			throw new Exception(e.getMessage());
		}
		
		return output;
	}
	
	private String GetDadosByPlayer(String periodo, String playerName, boolean isPretty) throws Exception {
		String output = "";		
		output = GetMemcached(periodo,playerName,"");
		try {
			if(output == null) {
				JSONObject json = buscarServidorAno(Integer.parseInt(periodo));
				String enderecoServidor = json.getString("location").split(":")[0];
				int portaServidor = Integer.parseInt(json.getString("location").split(":")[1]);
				if(!enderecoServidor.equals(config.getEndereco()) ) {
					try {
						Cliente client = new Cliente(enderecoServidor,portaServidor);
						output = client.GetData(periodo, "", playerName);						
					}catch(Exception e){
						desativaServidorExterno(json.getString("location"));
						throw new Exception("Servidor Indisponível");
					}
					
				}else {
					Statement stmt = null;		
					stmt = c.createStatement();
					ResultSet rs = stmt.executeQuery( "SELECT SUM(Wins) Wins,SUM(Losses) Losses FROM Players WHERE Ano = '"+periodo+"' AND player_name like '"+playerName+"%';" );
					while ( rs.next() ) {
						if(rs.getInt(1) == 0 && rs.getInt(2)==0) {
							throw new Exception("Dados Inexistentes");
						}
						output = returnJSON(rs.getInt(1),rs.getInt(2),isPretty);				
				      }
					rs.close();
				    stmt.close();
				    SetMemcached(periodo, playerName, "", output);
				}
				
				
			}
		}catch(Exception e) {
			throw new Exception(e.getMessage());
		}
		
		
		return output;
	}
	
	private String GetDadosByClube(String periodo, String clubName, boolean isPretty) throws Exception {
		String output = "";
		output = GetMemcached(periodo,"",clubName);
		if(output == null) {
			
			JSONObject json = buscarServidorAno(Integer.parseInt(periodo));
			String enderecoServidor = json.getString("location").split(":")[0];
			int portaServidor = Integer.parseInt(json.getString("location").split(":")[1]);
			if(!enderecoServidor.equals(config.getEndereco())) {
				try {
					Cliente client = new Cliente(enderecoServidor,portaServidor);
					output = client.GetData(periodo, clubName, "");
				}catch(Exception e){
					desativaServidorExterno(json.getString("location"));
					throw new Exception("Servidor Indisponível");
				}
				
				
			}else {
				Statement stmt = null;		
				stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery( "SELECT SUM(Wins),SUM(Losses) FROM Times WHERE Ano = '"+periodo+"' AND team_long_name like '"+clubName+"%';" );
				while ( rs.next() ) {
					if(rs.getInt(1) == 0 && rs.getInt(2)==0) {
						throw new Exception("Dados Inexistentes");
					}
					output = returnJSON(rs.getInt(1),rs.getInt(2),isPretty);		
			      }		
				rs.close();
			    stmt.close();
			    SetMemcached(periodo, "", clubName, output);
			}
		}
		
		
		return output;
	}	
	
	
	private String GetDados(String periodo, String playerName, String clubName, boolean isPretty) throws Exception {
		String output = "";
		output = GetMemcached(periodo,playerName,clubName);
		if(output == null) {
			JSONObject json = buscarServidorAno(Integer.parseInt(periodo));
			String enderecoServidor = json.getString("location").split(":")[0];
			int portaServidor = Integer.parseInt(json.getString("location").split(":")[1]);
			if(!enderecoServidor.equals(config.getEndereco())) {
				try {
					Cliente client = new Cliente(enderecoServidor,portaServidor);
					output = client.GetData(periodo, clubName, playerName);
				}catch(Exception e){
					desativaServidorExterno(json.getString("location"));
					throw new Exception("Servidor Indisponível");
				}
				
			}else {
				Statement stmt = null;		
				stmt = c.createStatement();
				ResultSet rs = stmt.executeQuery( "SELECT SUM(Wins),SUM(Losses) FROM Players WHERE Ano = '"+periodo+"' AND team_long_name like '"+clubName+"%'  AND player_name like '"+playerName+"%';" );
				while ( rs.next() ) {
					if(rs.getInt(1) == 0 && rs.getInt(2)==0) {
						throw new Exception("Dados Inexistentes");
					}
					output = returnJSON(rs.getInt(1),rs.getInt(2),isPretty);
			      }		
				rs.close();
			    stmt.close();
			    SetMemcached(periodo, playerName, clubName, output);
			}
		}
		
		return output;
	}
	
	private String GetMemcached(String periodo, String playerName, String clubName)throws Exception {
		String output = "";
		periodo = periodo.replace(" ", "");
		playerName = playerName.replace(" ", "+");
		clubName = clubName.replace(" ", "+");
		output = memcached.buscarDado("SD_Data_"+periodo+"_"+clubName+"_"+playerName);		
		return output;
	}
	
	private void SetMemcached(String periodo, String playerName, String clubName, String dados) throws Exception{
		String chave = "";
		periodo = periodo.replace(" ", "");
		playerName = playerName.replace(" ", "+");
		clubName = clubName.replace(" ", "+");
		chave = "SD_Data_"+periodo+"_"+clubName+"_"+playerName;
		memcached.gravarDado(chave, dados);
	}
	
	
	
	private JSONObject buscarServidorAno(int ano) throws ServerException {

		JSONObject retorno = null;
		JSONObject listaServidores = null;
		JSONArray array;
			
		try {
			listaServidores = new JSONObject(memcached.buscarDado("SD_ListServers"));
			array = listaServidores.getJSONArray("servers");
			
			int iLenght = array.length();
			for(int i = 0; i<iLenght; i++){ //para cada servidor
				if(!array.getJSONObject(i).getBoolean("active")){
					continue;
				}
				int jLenght = array.getJSONObject(i).getJSONArray("year").length();
				for(int j = 0; j<jLenght; j++){ //para cada ano
					if(array.getJSONObject(i).getJSONArray("year").getInt(j) == ano){
						retorno = array.getJSONObject(i);
						break;
					}
				}
				if(retorno != null){
					break;
				}
			}	
		} catch(Exception e) {
			
		}
		if(retorno == null){
			throw new ServerException(1, "Servidor Indisponível");
		}
		
		return retorno;
		
	}
	
	private String buscarAnosDisponiveis() throws Exception {

		JSONObject retorno = new JSONObject();

		JSONArray array = (new JSONObject(memcached.buscarDado("SD_ListServers")).getJSONArray("servers"));

		int iLenght = array.length();
		List<Integer> temp = new ArrayList<>();
		for(int i = 0; i<iLenght; i++){
			if(array.getJSONObject(i).getBoolean("active")) {
				int jLenght = array.getJSONObject(i).getJSONArray("year").length();
				for(int j = 0; j<jLenght; j++){
					temp.add((Integer) array.getJSONObject(i).getJSONArray("year").get(j));
				}
			}
			
			
		}
		HashSet<Integer> set = new HashSet<Integer>(temp);
		temp = new ArrayList<Integer>(set);
		Collections.sort(temp);
		
		for (Integer i : temp) {
			retorno.append("years", i );
		}
		
		return retorno.toString();
		
	}
	
	private String returnJSON(int wins, int losses,boolean isPretty) {
		if(isPretty) {
			return "{\r\n" + 
					"    \"wins\": "+wins+" ,\r\n" + 
					"    \"losses\": "+losses+" \r\n" + 
					"}";
		}else {
			return "{\"wins\": "+wins+",\"losses\": "+losses+"}";
		}
		
		
		
	}
	
	private void desativaServidorExterno(String location) throws Exception {
		JSONObject listaServidores = null; 
		String dado = memcached.buscarDado("SD_ListServers");
		if(dado == null) {
			return;
		}
		listaServidores = new JSONObject(dado);
		JSONArray array = listaServidores.getJSONArray("servers");
		int lenght = array.length();
		for(int i = 0; i<lenght; i++){
			if(array.getJSONObject(i).get("location").equals(location)){
				array.getJSONObject(i).remove("active");
				array.getJSONObject(i).put("active", false);
				break;
			}
		}
		memcached.gravarDado("SD_ListServers", listaServidores.toString());
		
	}

	
	private void atualizarListaServidores(boolean isActive) throws Exception{

		JSONObject listaServidores = null; 
		String dado = memcached.buscarDado("SD_ListServers");
		
		JSONObject servidor = new JSONObject();
		servidor.put("name", config.getNome());
		servidor.put("location", config.getEndereco() + ":" + config.getPorta());
		servidor.put("year", config.getAnos());
		servidor.put("active",isActive);
		
		if(dado == null) {
			listaServidores = new JSONObject();
			
		} else {
			listaServidores = new JSONObject(dado);
			JSONArray array = listaServidores.getJSONArray("servers");
			int lenght = array.length();
			for(int i = 0; i<lenght; i++){
				if(array.getJSONObject(i).get("name").equals(config.getNome())){
					array.remove(i);
					break;
				}
			}
			listaServidores.remove("servers");
			listaServidores.put("servers", array);
			
		}
		listaServidores.append("servers", servidor);
		
		memcached.gravarDado("SD_ListServers", listaServidores.toString());
	}
}