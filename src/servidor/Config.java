package servidor;
import org.json.JSONArray;
import org.json.JSONObject;


public class Config {
	
	private String serverName;
	private String serverIP;
	private int portListen;
	private String memcachedServer;
	private int memcachedPort;
	private int[] yearData;

	public Config() throws Exception{
<<<<<<< HEAD
		JSONObject jsonConfig = new JSONObject(Util.fileToString("config.json"));
=======

		JSONObject jsonConfig = new JSONObject(Util.fileToString("onfig.json"));
>>>>>>> 0ac53c8f4bbf03487b1c3d56aff42f0a92bd1a08

		setNome(jsonConfig.getString("serverName"));
		setPorta(jsonConfig.getInt("portListen"));
		setEndereco(jsonConfig.getString("serverIP"));

		setMemServidor(jsonConfig.getString("memcachedServer"));
		setMemPorta(jsonConfig.getInt("memcachedPort"));

		JSONArray array = jsonConfig.getJSONArray("yearData");
		int arrCount = array.length();
		yearData = new int[arrCount];
		for(int i=0;i<arrCount;i++){
			this.yearData[i] = array.getInt(i);
		}
	}

	public String getNome() {
		return serverName;
	}

	public void setNome(String nome) {
		this.serverName = nome;
	}

	public int getPorta() {
		return portListen;
	}

	public void setPorta(int porta) {
		this.portListen = porta;
	}

	public String getMemServidor() {
		return memcachedServer;
	}

	public void setMemServidor(String memServidor) {
		this.memcachedServer = memServidor;
	}

	public int getMemPorta() {
		return memcachedPort;
	}

	public void setMemPorta(int memPorta) {
		this.memcachedPort = memPorta;
	}

	public int[] getAnos() {
		return yearData;
	}

	public void setAnos(int[] anos) {
		this.yearData = anos;
	}

	public String getEndereco() {
		return serverIP;
	}

	public void setEndereco(String endereco) {
		this.serverIP = endereco;
	}
	
	public String toString() {
		return serverIP+":"+portListen;
	}
	
}
