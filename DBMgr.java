package service;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import com.mysql.cj.exceptions.RSAException;

import model.Patient;
import model.PatientVitals;


public class DBMgr {
	
	private Connection connect = null;
    private Statement statement = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet resultSet = null;
    
    public void getConnection()
    {
    	try {
			//Class.forName("com.mysql.jdbc.Driver");
    		 Class.forName("com.mysql.cj.jdbc.Driver");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
      
    	 // Setup the connection with the DB
         try {
			connect = DriverManager
			         .getConnection("jdbc:mysql://localhost/rpm?"
			                 + "user=root&password=aarav123");
			
			statement = connect.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
         } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
         }
    }
    
    public Patient[] getPatients(String filterByName) 
    {
    	 getConnection();
    	 try {
    		 if (filterByName==null){
    			 resultSet = statement.executeQuery("select * from rpm.Patients");
    		 }else {
    			 resultSet = statement.executeQuery("select * from rpm.Patients where FirstName=\"" + filterByName + "\"");
    		 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  return convertToPatientObjects(resultSet);

    }
    
    public void createPatients(String fName, String lName, int age, double gpa, int weight)
    {
    	  getConnection();
         System.out.println(fName + " inside db.create");
			try {
				statement.executeUpdate("insert into rpm.Patients (FirstName, LastName, Age, gpa, weight)\n"
						+ "values (\"" + fName + "\",\"" + lName + "\"," + age + "," + gpa + "," + weight + ")\n"
						+ "ON DUPLICATE KEY UPDATE `weight` = " + weight);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         
    }
    
    public void createPatientVitals(String fName, String lName, String STATUS, int O2, int SYS, int DIA, int BS)
    {
    	  getConnection();
    	  //Confirmation of the user the patient is trying to create
         System.out.println(fName + " inside db.create");
         String query = "insert into rpm.patientvitals (FirstName, LastName, STATUS, O2, SYS, DIA, BS)\n"
					+ "values (\"" + fName + "\",\"" + lName + "\",\"" + STATUS + "\"," + O2 + "," + SYS + "," + DIA + "," + BS + ")\n"
					+ "ON DUPLICATE KEY UPDATE `O2` =" + O2 + ", `SYS` =" +  SYS  + ", `DIA` =" +  DIA + ", `BS` =" +  BS + ", `STATUS` =" + "\"" + STATUS + "\""; 
         
         System.out.println(query);
         //Using information stored in the variables and inserting them into the database - avoid duplicates
         	try {
         		/*
         		statement.executeUpdate("insert into rpm.patientvitals (FirstName, LastName, STATUS, O2, SYS, DIA, BS)\n"
						+ "values (\"" + fName + "\",\"" + lName + "\",\"" + STATUS + "\",\"" + O2 + "," + SYS + "," + DIA + "," + BS + ")\n"
						+ "ON DUPLICATE KEY UPDATE `O2` =" + O2 + ", `SYS` =" +  SYS  + ", `DIA` =" +  DIA + ", `BS` =" +  BS + ", `STATUS` =" +  STATUS + ",");
				
			*/
         		statement.executeUpdate(query);
         		
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         
    }
    
    public PatientVitals[] getPatientVitals(String filterByName) 
    {
    	 getConnection();
    	 try {
    		 if (filterByName==null){
    			 resultSet = statement.executeQuery("select * from rpm.PatientVitals");
    		 }else {
    			 resultSet = statement.executeQuery("select * from rpm.PatientVitals where FirstName=\"" + filterByName + "\"");
    		 }
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	  return convertToPatientVitalsObjects(resultSet);

    }
    
    public void deletePatients(String filterByName)
    {
    	  getConnection();
         System.out.println(filterByName + " inside db.delete");
			try {
				statement.executeUpdate("delete from rpm.Patients where FirstName=\"" + filterByName + "\"");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
         
    }
    
    private Patient[] convertToPatientObjects(ResultSet rs)
    {
    	
    	Patient[] patientList = null;
    	int size =0;
    	int count =0;
    	try {
    		if (rs != null) 
        	{
        	  rs.last();    // moves cursor to the last row
        	  size = rs.getRow(); // get row id 
        	  rs.beforeFirst();
        	}
    		patientList = new Patient[size];
			while (rs.next())
			{
				try {
					String fName = rs.getString("FirstName");
					String lName = rs.getString("LastName");
					int age = rs.getInt("age");
					double gpa = rs.getDouble("gpa");
					//System.out.println(fName + " " + lName + " " + age + " " + gpa);
					Patient patient = new Patient(fName, lName, age, gpa);
					patientList[count]=patient;
					count++;
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return patientList;
    }

private PatientVitals[] convertToPatientVitalsObjects(ResultSet rs)
{
	
	PatientVitals[] patientVitalsList = null;
	int size =0;
	int count =0;
	try {
		if (rs != null) 
    	{
    	  rs.last();    // moves cursor to the last row
    	  size = rs.getRow(); // get row id 
    	  rs.beforeFirst();
    	}
		patientVitalsList = new PatientVitals[size];
		while (rs.next())
		{
			try {
				String fName = rs.getString("FirstName");
				String lName = rs.getString("LastName");
				int O2 = rs.getInt("O2");
				int DIA = rs.getInt("DIA");
				int SYS = rs.getInt("SYS");
				int BS = rs.getInt("BS");
				String status = rs.getString("STATUS");
				
				//System.out.println(fName + " " + lName + " " + age + " " + gpa);
				PatientVitals patientvitals = new PatientVitals(fName, lName, O2, DIA, SYS, BS, status);
				patientVitalsList[count] = patientvitals;
				count++;
				
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			
			}
		}
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return patientVitalsList;
}

}

