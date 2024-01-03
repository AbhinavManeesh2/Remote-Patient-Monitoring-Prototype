package service;

import java.io.IOException;
import java.util.StringTokenizer;

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

import java.io.*;
import org.json.JSONObject;


public class PatientAPI extends HttpServlet {

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		System.out.println("****************Inside doGet******************");
		String requestUrl = request.getRequestURI();
		System.out.println(requestUrl);
		
		
		String filterByName = this.getPatientNamefromUrl (requestUrl);
		System.out.println("patient name = " + filterByName);
		
		
		DBMgr dbm = new DBMgr();
		Patient patients[]=dbm.getPatients(filterByName);
		
		
		if(patients != null){
			String json = "";
			json +=  "{\n\t" +  "\"patients\" : [\n";
			for (int i=0; i<patients.length; i++)
			{
				Patient stu = patients[i];
				json += "\t\t{\n";
				json += "\t\t\t" + "\"firstName\": " + JSONObject.quote(stu.getfName()) + ",\n";
				json += "\t\t\t"+ "\"lastName\": " + JSONObject.quote(stu.getlName()) + ",\n";
				json += "\t\t\t" + "\"age\": " + stu.getAge() + "\n";
				//json += "\t\t\t" + "\"GPA\": " + stu.getGpa() + "\n";
				json += "\t\t}";
				if(i<patients.length-1)
				{
					json += ",\n";
				}
			}
			json += "\t\n\t]\n}";
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
		
		DBMgr Db = new DBMgr();
		String fname = request.getParameter("First_Name");
		String lname = request.getParameter("Last_Name");
		int age =  Integer.parseInt(request.getParameter("Age"));
		double gpa =  Double.parseDouble(request.getParameter("GPA"));
		int weight = Integer.parseInt(request.getParameter("weight"));
		
		Db.createPatients(fname, lname, age, gpa, weight);
		
		response.getOutputStream().println("Patient record inserted successfully");
		
		System.out.println(fname);
		System.out.println(lname);
		System.out.println(age);
		System.out.println(gpa);
		
	}
	
	
	public void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		DBMgr Db = new DBMgr();
		String fname = request.getParameter("First_Name");
		//String lname = request.getParameter("Last_Name");
		//int age =  Integer.parseInt(request.getParameter("Age"));
		//double gpa =  Double.parseDouble(request.getParameter("GPA"));
		
		Db.deletePatients(fname);
		
		response.getOutputStream().println("Patient record deleted successfully");
		
		System.out.println(fname);
		
		
	}
}

		 