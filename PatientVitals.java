package model;

public class PatientVitals {
	String First_Name;
	String Last_Name;
	int O2;
	int SYS;
	int DIA;
	int BS;
	String Status;

	
	
	public PatientVitals(String First_Name, String Last_Name, int O2, int SYS, int DIA, int BS, String status)
	{
		this.First_Name = First_Name;
		this.Last_Name = Last_Name;
		this.O2 = O2;
		this.SYS = SYS;
		this.DIA = DIA;
		this.BS = BS;
		this.Status = status;
	}

	public String getFirst_Name() {
		return First_Name;
	}
	
	public String getStatus() {
		return Status;
	}

	public void setFirst_Name(String First_Name) {
		this.First_Name = First_Name;
	}

	public String getLast_Name() {
		return Last_Name;
	}

	public void setLast_Name(String Last_Name) {
		this.Last_Name = Last_Name;
	}

	public int getO2() {
		return O2;
	}

	public int getSYS() {
		return SYS;
	}

	public int getDIA() {
		return DIA;
	}

	public int getBS() {
		return BS;
	}

}
