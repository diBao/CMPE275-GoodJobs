package module;

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



@Entity
@Table(name="JobSeeker")
//@XmlRootElement
@JsonRootName(value = "JobSeeker")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class JobSeeker {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "SID")
	private Long sID;
	
	@Column(name = "FIRST_NAME", nullable = false)
	private String firstName;
	
	@Column(name = "LAST_NAME", nullable = false)
	private String lastName;
	
	@Column(name = "PICTURE")
	private String picture;
	
	@Column(name = "INTRODUCTION")
	private String introduction;
	
	@Column(name = "EXPERIENCE", nullable = false)
	private String experience;
	
	@Column(name = "EDUCATION", nullable = false)
	private String education;
	
	@Column(name = "SKILLS", nullable = false)
	private String skills;
	
	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	//@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@ManyToMany(targetEntity = Position.class, fetch = FetchType.LAZY)
	private Set<Position> interestSet;
	
	//@JsonInclude(JsonInclude.Include.NON_EMPTY)
	@OneToMany(targetEntity=Position.class, fetch = FetchType.LAZY)
	private Set<Position> applicationSet;

	public long getSID() {
		return sID;
	}

	public void setSID(long sID) {
		this.sID = sID;
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

	public Set<Position> getApplicationSet() {
		return applicationSet;
	}

	public void setApplicationSet(Set<Position> applicationSet) {
		this.applicationSet = applicationSet;
	}
}
