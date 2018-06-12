package servidor;

public class TrataRequisicao {
	
	static String[] RetornoUrl (String periodo, String playerName, String clubName,String IsError){
		
		String[] arrReturn = new String[4];
		
   		arrReturn[0]=periodo;
    	arrReturn[1]=playerName;
    	arrReturn[2]=clubName;
    	arrReturn[3]=IsError;
		
		return arrReturn;	
	}
	
	static String[] TrataRequest (String target){
	

			String clubName="",playerName="",periodo = "";
	
            String auxSeparaBarras[]=target.split("/");
            if(auxSeparaBarras.length>1){
            	
            	if(auxSeparaBarras[1].equals("getData")){

                    if(auxSeparaBarras.length>2){
            		
                    	if(!auxSeparaBarras[2].contains("?")){
                        	return RetornoUrl("","","","Error");
                    	}
                    		
                    	String auxSeparaInterg[]=auxSeparaBarras[2].split("\\?");
                    	periodo=auxSeparaInterg[0];
            		
                    	if(auxSeparaInterg[1].contains("&")){
            			
                    		String auxSeparaEcom[] = auxSeparaInterg[1].split("&");
                    		
                    	  	if(!auxSeparaEcom[0].contains("=")){
                    	  		return RetornoUrl("","","","Error");
                        	}
                        	
                    		String auxSeparaIgualIn[]=auxSeparaEcom[0].split("=");
            			
                    		if(auxSeparaIgualIn.length!=2){
                    			return RetornoUrl("","","","Error");
                    		}
                    			
                    		
                    		if(auxSeparaIgualIn[0].equals("playerName")){
                    			playerName=auxSeparaIgualIn[1].replace("+", " ");
                    		}else if(auxSeparaIgualIn[0].equals("clubName")){
                    			clubName=auxSeparaIgualIn[1].replace("+", " ");
                    		}
                    		
                    	  	if(!auxSeparaEcom[1].contains("=")){
                    	  		return RetornoUrl("","","","Error");
                        	}
                	
                    		String auxSeparaIgualTw[]=auxSeparaEcom[1].split("=");
                    		
                    		if(auxSeparaIgualTw.length!=2){
                    			return RetornoUrl("","","","Error");
                    		}
                    		
                    		if(auxSeparaIgualTw[0].equals("playerName")){
                    			playerName=auxSeparaIgualTw[1].replace("+", " ");
                    		}else if(auxSeparaIgualTw[0].equals("clubName")){
                    			clubName=auxSeparaIgualTw[1].replace("+", " ");
                    		}
                    	}
                    	else{
                    		
                    	  	if(!auxSeparaInterg[1].contains("=")){
                    	  		return RetornoUrl("","","","Error");
                        	}
                	
                    		String auxSeparaIgual[]=auxSeparaInterg[1].split("=");
                    		
                    		if(auxSeparaIgual.length!=2){
                    			return RetornoUrl("","","","Error");
                    		}
                    		
                    		if(auxSeparaIgual[0].equals("playerName")){
            					playerName=auxSeparaIgual[1].replace("+", " ");
                    		}
                    		if(auxSeparaIgual[0].equals("clubName")){
                    			clubName=auxSeparaIgual[1].replace("+", " ");
                    		}
                    	}
                    		return RetornoUrl(periodo,playerName,clubName,"Correct");
       	
                    }else{
              
                    	return RetornoUrl("","","","Error");
             	
                    }
            	}else if(auxSeparaBarras[1].equals("getAvailabeYears")){
            		
            		return RetornoUrl("","","","Correct");

            	}else{
            	
            		return RetornoUrl("","","","Error");
      	
            	}
           }
            return RetornoUrl("","","","Error");   
    }

}

