package servidor;

public class ServerException extends Exception {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int codigo;
	
	public ServerException(int codigo, String mensagem){
		super(mensagem);
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
