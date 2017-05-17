package rest.module;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;


//import javax.xml.bind.annotation.XmlTransient;
//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.json.*;

@Entity
@Table(name="Position")
//@XmlRootElement
@JsonRootName(value = "Position")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Position {
	
	public Position(){
		
	}
	
	public Position(Company company, String pTitle,String pDesciption,String responsibility,String officeLocation,Long salary){
		this.setTitle(pTitle);
		this.setCompany(company);
		this.setDescription(pDesciption);
		this.setResponsibility(responsibility);
		this.setOfficeLocation(officeLocation);
		this.setSalary(salary);
		this.setStatus("open");//initial state
		this.setInterestSet(new HashSet<JobSeeker>());
		this.setSeekerSet(new HashSet<Application>());
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "PID")
	private long pID;
	
	@Column(name = "TITLE", nullable = false)
	private String title;
	
	@Column(name = "DESCRIPTION", nullable = false)
	private String description;
	
	@Column(name = "REPOSIBILITY", nullable = false)
	private String responsibility;
	
	@Column(name = "OFFICE_LOCATION", nullable = false)
	private String officeLocation;
	
	@Column(name = "SALARY", nullable = false)
	private Long salary;
	
	@Column(name = "STATUS", nullable = false)
	private String status;
	
	@ManyToOne(targetEntity=Company.class, fetch = FetchType.LAZY)
	private Company company;
	
	// CHANGED seekerSet -> applicationSet
	@OneToMany(targetEntity=Application.class, fetch = FetchType.LAZY)
	private Set<Application> applicationSet = new HashSet<Application>();
	
	@ManyToMany(targetEntity=JobSeeker.class, fetch = FetchType.LAZY)
	private Set<JobSeeker> interestSet = new HashSet<JobSeeker>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getResponsibility() {
		return responsibility;
	}

	public void setResponsibility(String responsibility) {
		this.responsibility = responsibility;
	}

	public String getOfficeLocation() {
		return officeLocation;
	}

	public void setOfficeLocation(String officeLocation) {
		this.officeLocation = officeLocation;
	}

	public long getSalary() {
		return salary;
	}

	public void setSalary(long salary) {
		this.salary = salary;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Set<JobSeeker> getInterestSet() {
		return interestSet;
	}

	public void setInterestSet(Set<JobSeeker> interestSet) {
		this.interestSet = interestSet;
	}
	
	public Set<Application> getApplicationSet() {
		return applicationSet;
	}

	public void setSeekerSet(Set<Application> applicationSet) {
		this.applicationSet = applicationSet;
	}

	public Long getpID() {
		return pID;
	}	
	public String getJSON(){
		
		try{
			JSONObject result = new JSONObject();
			result.put("pid", getpID());
			result.put("title", getTitle());
			result.put("description", getDescription());
			result.put("responsibility", getResponsibility());
			result.put("officeLocation", getOfficeLocation());
			result.put("salary", getSalary());
			result.put("status", getStatus());
			return result.toString();
		}
		catch(JSONException e){
			return e.toString();
		}
	}

	public JSONObject getJSONObj(){
		
		try{
			JSONObject result = new JSONObject();
			result.put("pid", getpID());
			result.put("title", getTitle());
			result.put("description", getDescription());
			result.put("responsibility", getResponsibility());
			result.put("office_location", getOfficeLocation());
			result.put("salary", getSalary());
			result.put("status", getStatus());
			return result;
		}
		catch(JSONException e){
			return new JSONObject();
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	
}
