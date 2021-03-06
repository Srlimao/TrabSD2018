package servidor;

public class ServerException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int codigo;
	
	public static String getMessage(int codigo) {
		String output = null;
		if(codigo == 1) {
			output = "Servidor Indisponível";
		}
		if(codigo == 2){
			output = "Dados Inexistentes";
		}
		return output;
	}
	
	public ServerException(int codigo, String message){
		super(message);
		this.setCodigo(codigo);
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
	
	public String getJSONString(){
		return "{\n" + 
				"    \"errorCode\": "+this.getCodigo()+",\n" + 
				"    \"errorDescription\": \""+this.getMessage()+"\"\n" + 
				"}";
	}
	
	
	
}
