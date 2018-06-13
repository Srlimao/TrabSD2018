package servidor;

import java.net.Socket;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.impl.DefaultBHttpClientConnection;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.protocol.HttpCoreContext;
import org.apache.http.protocol.HttpProcessor;
import org.apache.http.protocol.HttpProcessorBuilder;
import org.apache.http.protocol.HttpRequestExecutor;
import org.apache.http.protocol.RequestConnControl;
import org.apache.http.protocol.RequestContent;
import org.apache.http.protocol.RequestExpectContinue;
import org.apache.http.protocol.RequestTargetHost;
import org.apache.http.protocol.RequestUserAgent;
import org.apache.http.util.EntityUtils;

public class Cliente {
	
	String server;
	int port;

	public Cliente(String server, int port){
		this.server=server;
		this.port = port;
	}
	
    public String GetData(String periodo, String club, String player) throws Exception {
        HttpProcessor httpproc = HttpProcessorBuilder.create()
            .add(new RequestContent())
            .add(new RequestTargetHost())
            .add(new RequestConnControl())
            .add(new RequestUserAgent("Test/1.1"))
            .add(new RequestExpectContinue(true)).build();

        HttpRequestExecutor httpexecutor = new HttpRequestExecutor();
        HttpCoreContext coreContext = HttpCoreContext.create();
        HttpHost host = new HttpHost(server, port);
        coreContext.setTargetHost(host);

        DefaultBHttpClientConnection conn = new DefaultBHttpClientConnection(8 * 1024);

        try {    
                if (!conn.isOpen()) {
                    Socket socket;
					try {
						socket = new Socket(host.getHostName(), host.getPort());
						conn.bind(socket);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						throw new Exception("Servidor Indisponível");
					}
                    
                }
                String monta = "";
                if(!player.equals("")) {
                	monta="playerName="+player;
                }
                if(!club.equals("")) {
                	if(!monta.equals("")) {
                    	monta = monta+"&";
                    }
                	monta = monta+"clubName="+club;
                }
                
                BasicHttpRequest request = new BasicHttpRequest("GET","/getData/"+periodo+"?"+monta);
                try {
                	httpexecutor.preProcess(request, httpproc, coreContext);
                    HttpResponse response = httpexecutor.execute(request, conn, coreContext);
                    httpexecutor.postProcess(response, httpproc, coreContext);
                    monta = EntityUtils.toString(response.getEntity());
                    conn.close();
                } catch (Exception e) {
                	throw new Exception("Servidor Indisponível");
                }
                
                return monta;

                

        } finally {
            try {
				conn.close();
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
        }
    }

}