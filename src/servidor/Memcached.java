package servidor;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.FailureMode;
import net.spy.memcached.MemcachedClient;

public class Memcached {
	
	private MemcachedClient client;
	private String endereco;
	private int porta;
	private boolean conectado;
	private boolean alive;

	public Memcached(String endereco, int porta){
		//Desativa o spam do memcache no log
		System.setProperty("net.spy.log.LoggerImpl","net.spy.memcached.compat.log.SunLogger");
		Logger.getLogger("net.spy.memcached").setLevel(Level.WARNING);
		
		definirServidorMemcached(endereco,porta);
	}
	
	public void definirServidorMemcached(String endereco, int porta){
		if(conectado){
			desconectar();
		}
		this.endereco = endereco;
		this.porta = porta;		
	}

	public String buscarDado(String chave){
		if(!conectado){
			alive = conectar();
		}
		if(alive) {
			return (String) client.get(chave);
		}else return null;

				
	}
	
	public void gravarDado(String chave, String dado){
		if(!conectado){
			alive = conectar();
		}
		if(alive) {
			client.set(chave, 0, dado);
		}
		
		return;
	}
	
	public boolean conectar() {
		if(conectado){
			return true;
		}
		try {
			//this.client = new MemcachedClient(new ConnectionFactoryBuilder().setDaemon(true).setFailureMode(FailureMode.Cancel).build(), AddrUtil.getAddresses(this.endereco + ":" + this.porta));
			this.client = new MemcachedClient(AddrUtil.getAddresses(this.endereco + ":" + this.porta));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			return conectado=false;
		}
		return conectado = this.client.isAlive();
	}
	
	public void desconectar() {
		if(!conectado){
			return;
		}
		if(alive) {
			this.client.shutdown();
		}
		
		conectado = false;
	}
}
