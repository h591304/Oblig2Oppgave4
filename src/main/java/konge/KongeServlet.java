package konge;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@SuppressWarnings("serial")
@WebServlet("/processInput")
public class KongeServlet extends HttpServlet {

	PrintWriter out;
	
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		String aarString = request.getParameter("aar");
		
		//Valider årstall og gi alternativ respons hvis ugyldig.
		if(erGyldigHeltall(aarString)) {
			int aar = Integer.parseInt(aarString);
		
			//Finn et passende Konge-objekt fra listen Konger.norske
			//ved å bruke streams og lambda-uttrykk.
			
			Konge konge = Konger.norske.stream()
					.filter(k -> k.getKongeFraAar() <= aar && aar <= k.getKongeTilAar())
					.findFirst()
					.orElse(null);
		
			response.setContentType("text/html; charset=ISO-8859-1");
			
			out = response.getWriter();
			
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
		    out.println("<meta charset=\"ISO-8859-1\">");
		    out.println("<title> Konge </title>");
		    out.println("</head>");
		    out.println("<body>");
			
		    if(aar >= 1448 && aar <= 1588) {
		    	//Lag respons-siden.
			    out.println("<p><img src=" + konge.getBilde()
			    		+ " alt=Image height=350px width=300px</p>");
			    out.println("<h1>Konge i år " + aar + " var " + konge.toString() + "</h1>");		 
		    }
			
			else {
				    
			    out.println("<h4> - For Christian I: Velg årstall mellom 1448 og 1482</h4>");   
			    out.println("<h4> - For Hans: Velg årstall mellom 1482 og 1513</h4>");   
			    out.println("<h4> - For Christian II: Velg årstall mellom 1513 og 1523</h4>");
			    out.println("<h4> - For Frederik I: Velg årstall mellom 1523 og 1535</h4>");
			    out.println("<h4> - For Christian III: Velg årstall mellom 1535 og 1559</h4>");   
			    out.println("<h4> - For Frederik II: Velg årstall mellom 1559 og 1588</h4>");   
			}
			
			out.println("<a href=Konge.html>Nytt søk</a>");
		    out.println("</body>");
		    out.println("</html>");
		}
		else {
			
			out = response.getWriter();
			
			//Respons når oppgitt årstall er ugyldig
			out.println("<!DOCTYPE html>");
			out.println("<html>");
			out.println("<head>");
			out.println("<meta charset=\"ISO-8859-1\">");
			out.println("<title> Konge </title>");
			out.println("</head>");
			out.println("<body>");
			out.println("<h3> Ugyldig årstall!!! </h3>");
			out.println("<a href=Konge.html>Nytt søk</a>");
			out.println("</body>");
			out.println("</html>");
		}
	}
	
	private boolean erGyldigHeltall(String str) {
		return str.matches("^\\d{4}$");
	}
}
