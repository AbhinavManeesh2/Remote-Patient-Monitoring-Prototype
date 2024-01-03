package model;

public class Patient {
	
	
	String fName;
	String lName;
	int age;
	double gpa;
	
	public Patient(String fName, String lName, int age, double gpa)
	{
		this.fName = fName;
		this.lName = lName;
		this.age = age;
		this.gpa = gpa;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public double getGpa() {
		return gpa;
	}

	public void setGpa(double gpa) {
		this.gpa = gpa;
	}

}
