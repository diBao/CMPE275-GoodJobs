package rest.module;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;



//import javax.xml.bind.annotation.XmlTransient;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.json.*;

@Entity
@Table(name="JobSeeker")
public class JobSeeker {
	
	public JobSeeker(){
		
	}
	
	public JobSeeker(String firstName, String lastName, String picture, String selfIntroduction, 
			String workExperience, String education, String skills, String email, String password){
		this.setFirstName(firstName);
		this.setLastName(lastName);
		this.setPicture(picture);
		this.setIntroduction(selfIntroduction);
		this.setExperience(workExperience);
		this.setEducation(education);
		this.setSkills(skills);
		this.setEmail(email);
		this.setPassword(password);
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SID")
	private long sID;
	
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "PICTURE")
	private String picture;
	
	@Column(name = "INTRODUCTION")
	private String introduction;
	
	@Column(name = "EXPERIENCE")
	private String experience;
	
	@Column(name = "EDUCATION")
	private String education;
	
	@Column(name = "SKILLS")
	private String skills;
	
	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	//@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ManyToMany(targetEntity = Position.class, fetch = FetchType.LAZY)
	private Set<Position> interestSet = new HashSet<Position>();
	
	//@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(targetEntity = Position.class, fetch = FetchType.LAZY)
	private Set<Application> applicationSet = new HashSet<Application>();

	public long getsID() {
		return sID;
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

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getExperience() {
		return experience;
	}

	public void setExperience(String experience) {
		this.experience = experience;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getSkills() {
		return skills;
	}

	public void setSkills(String skills) {
		this.skills = skills;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Position> getInterestSet() {
		return interestSet;
	}

	public void setInterestSet(Set<Position> interestSet) {
		this.interestSet = interestSet;
	}

	public Set<Application> getApplicationSet() {
		return applicationSet;
	}

	public void setApplicationSet(Set<Application> applicationSet) {
		this.applicationSet = applicationSet;
	}
	public String getJSON(){
		
		try{
			JSONObject result = new JSONObject();
			result.put("sid", getsID());
			result.put("first_name", getFirstName());
			result.put("last_name", getLastName());
			result.put("picture", getPicture());
			result.put("introduction", getIntroduction());
			result.put("experience", getExperience());
			result.put("education", getEducation());
			result.put("skill", getSkills());
			result.put("email", getEmail());
			return result.toString();
		}
		catch(JSONException e){
			return e.toString();
		}		
	}
}
