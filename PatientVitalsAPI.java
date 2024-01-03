package service;

import java.io.IOException;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

/*
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
*/
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Patient;
import model.PatientVitals;

import java.io.*;
import org.json.JSONObject;
import org.json.JSONTokener;



public class PatientVitalsAPI extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		//Assigning requested information into a string
		System.out.println("****************Inside doGet******************");
		String requestUrl = request.getRequestURI();
		System.out.println(requestUrl);
		
		//Using tokenization function from PatientAPI class to extract name of patient
		String filterByName = this.getPatientNamefromUrl (requestUrl);
		System.out.println("patient name = " + filterByName);
		
		//Calls function in DBMGR which creates a "result set" of the results after executing the requested query
		DBMgr dbm = new DBMgr();
		PatientVitals patientVitals[]=dbm.getPatientVitals(filterByName);
		
		//Transferring information from postman into a JSON structure which is then sent to postman
		if(patientVitals != null){
			String json = "";
			json +=  "{\n\t" +  "\"patientvitals\" : [\n";
			for (int i=0; i<patientVitals.length; i++)
			{
				PatientVitals PV = patientVitals[i];
				json += "\t\t{\n";
				json += "\t\t\t" + "\"firstName\": " + JSONObject.quote(PV.getFirst_Name()) + ",\n";
				json += "\t\t\t"+ "\"lastName\": " + JSONObject.quote(PV.getLast_Name()) + ",\n";
				json += "\t\t\t" + "\"O2\": " + PV.getO2() + ",\n";
				json += "\t\t\t" + "\"SYS\": " + PV.getSYS() + ",\n";
				json += "\t\t\t" + "\"DIA\": " + PV.getDIA() + ",\n";
				json += "\t\t\t" + "\"BS\": " + PV.getBS() + ",\n";
				json += "\t\t\t" + "\"Status\": " + JSONObject.quote(PV.getStatus()) + "\n";
				
				json += "\t\t}";
				if(i<patientVitals.length-1)
				{
					json += ",\n";
				}
			}
			json += "\t\n\t]\n}";
			System.out.println(json);
			response.getOutputStream().println(json);
		}
		else{
			//That person wasn't found, so return an empty JSON object. We could also return an error.
			response.getOutputStream().println("{}");
		}
	}
	
	private String getPatientNamefromUrl (String requestUrl) {
		StringTokenizer token = new StringTokenizer(requestUrl,"/");
		System.out.println(token.countTokens());
		System.out.println(token.toString());
		if (token.countTokens()<3)
		{
			return null;
		}
		
		int x = 0;
		
		String TokenizationContents[];
		TokenizationContents = new String[token.countTokens()];
		
		while (token.hasMoreTokens())
		{
			TokenizationContents[x] = token.nextToken();
			x++;
		}
		
		System.out.println(token.countTokens());
		return TokenizationContents[TokenizationContents.length-1];
	}
	

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		//Letting user know that the program is running
		System.out.println("Inside PatientVitalsAPI doPost");
		
		
		String result = request.getReader().lines().collect(Collectors.joining("\n"));
		System.out.println("RESULT = ");
		System.out.println(result);
		
		//JSONParser parser = new JSONParser();
        JSONTokener tokener = new JSONTokener(result);
        JSONObject jsonObject = new JSONObject(tokener);                

		
		//JSONObject jsonObject = (JSONObject) parser.parse(result);
		//System.out.println(result);
		
        //parsing information from JSON and assigning each piece to a separate variable
		String fName = (String) jsonObject.get("First Name");
		System.out.println(fName);
		
		String lName = (String) jsonObject.get("Last Name");
		System.out.println(lName);
		
		int O2 = (int) jsonObject.get("O2");
		System.out.println(O2);
		
		int DIA = (int) jsonObject.get("DIA");
		System.out.println(DIA);
		
		int SYS = (int) jsonObject.get("SYS");
		System.out.println(SYS);
		
		int BS = (int) jsonObject.get("BS");
		System.out.println(BS);
		
		DBMgr Db = new DBMgr();
		
		String STATUS = "N/A";
		
		if (O2 < 91 || SYS < 90 || SYS > 120 || DIA < 60 || DIA > 90) {
    	   STATUS = "Critical";
		}
       
		else if (O2 >= 91 && 95 >= O2) {
			STATUS = "Abnormal";
		}
	   else {
		   STATUS = "Normal";
	   }
    	   
       
		 	
		//String fname = request.getParameter("First_Name");
		//String lname = request.getParameter("Last_Name");
		//int O2 =  Integer.parseInt(request.getParameter("O2"));
		//int DIA = Integer.parseInt(request.getParameter("DIA"));
		//int SYS = Integer.parseInt(request.getParameter("SYS"));
		//int BS = Integer.parseInt(request.getParameter("BS"));
		


		
		
		Db.createPatientVitals(fName, lName, STATUS, O2, DIA, SYS, BS);
		
		response.getOutputStream().println("Patient vitals inserted successfully");
	}
	
	private void printJSON(HttpServletRequest req)
	{
		/*	
		BufferedReader rd = null;
			try {
				rd = req.getReader();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    String line = null;
		    String message = new String();
		    final StringBuffer buffer = new StringBuffer(2048);
		    try {
				while ((line = rd.readLine()) != null) {
				    // buffer.append(line);
				    message += line;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    System.out.println(message);
		    */
		    //JsonObject json = new JsonObject(message);
	}
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
		
		
	}
}


		 