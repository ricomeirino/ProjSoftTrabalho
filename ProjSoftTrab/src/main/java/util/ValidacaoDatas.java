package util;

import java.util.Date;

public class ValidacaoDatas {


	
	public static boolean dataInicioMenorIgualDataFim(Date dataInicio, Date dataFim){ 
        
	    boolean data;  
	    if (dataInicio.before(dataFim)){  
	          
	        data = true;  
	    }  
	    else if (dataInicio.after(dataFim))  
	        data = false;  
	    else  
	        data = true;  
	      
	    return data;  
	}  	
}
