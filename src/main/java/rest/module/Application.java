package rest.module;

//import java.util.HashSet;
//import java.util.Iterator;
//import java.util.Set;

//import javax.xml.bind.annotation.XmlRootElement;

//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
//import javax.persistence.JoinColumn;
//import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
//import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.json.*;

@Entity
@Table(name="Application")
//@XmlRootElement
@JsonRootName(value = "Application")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Application {
	public Application(JobSeeker jobSeeker,String sEmail,String sFirstName,String sLastName, Position position, String resumeURL){
		this.setJobSeeker(jobSeeker);
		this.setEmail(sEmail);
		this.setFirstName(sFirstName);
		this.setLastName(sLastName);
		this.setPosition(position);
		this.setStatus("pending");//initial status(pending)
		this.setResumeUrl(resumeURL);
	}
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "AID")
	private Long aID;
	
	@ManyToOne(targetEntity = JobSeeker.class, fetch = FetchType.LAZY)
	private JobSeeker jobSeeker;
	
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@ManyToOne(targetEntity = Position.class, fetch=FetchType.LAZY)
	private Position position;
	
	@Column(name = "EMAIL", nullable = false)
	private String email;
	
	@Column(name = "STATUS", nullable = false)
	private String status;
	
	@Column(name = "RESUMEURL")
	private String resumeUrl;
	
	public long getaID() {
		return aID;
	}

	public JobSeeker getJobSeeker() {
		return jobSeeker;
	}

	public void setJobSeeker(JobSeeker jobSeeker) {
		this.jobSeeker = jobSeeker;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public Position getPosition() {
		return position;
	}

	public void setPosition(Position position) {
		this.position = position;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getResumeUrl() {
		return resumeUrl;
	}

	public void setResumeUrl(String resumeUrl) {
		this.resumeUrl = resumeUrl;
	}
	
	public String getJSON(){
		
		try{
			JSONObject result = new JSONObject();
			result.put("aid", getaID());
			result.put("first name", getFirstName());
			result.put("last name", getLastName());
			result.put("email", getEmail());
			result.put("status", getStatus());
			

			JobSeeker jobseekerObj = getJobSeeker();
			JSONObject jobseeker = new JSONObject();
			jobseeker.put("capacity", jobseekerObj.getSID());
			jobseeker.put("first name", jobseekerObj.getFirstName());
			jobseeker.put("last name", jobseekerObj.getLastName());
			jobseeker.put("picture", jobseekerObj.getPicture());
			jobseeker.put("introduction", jobseekerObj.getIntroduction());
			jobseeker.put("experience", jobseekerObj.getExperience());
			jobseeker.put("education", jobseekerObj.getEducation());
			jobseeker.put("skill", jobseekerObj.getSkills());
			jobseeker.put("email", jobseekerObj.getEmail());
			
			result.put("jobseeker", jobseeker);
			return result.toString();
		}
		catch(JSONException e){
			return e.toString();
		}
	}
}
