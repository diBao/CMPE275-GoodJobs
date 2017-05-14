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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.json.*;

@Entity
@Table(name="Company")
public class Company {
	
	public Company(){
		
	}
	
	public Company(String name, String website, String logoImageUrl, 
			String address, String email, String description, String password){
		this.setCompanyName(name);
		this.setWebsite(website);
		this.setLogoImageUrl(logoImageUrl);
		this.setAddress(address);
		this.setEmail(email);
		this.setDescription(description);
		this.setPassword(password);
		this.setPositionSet(new HashSet<Position>());
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CID")
	private long cID;
	
	@Column(name = "COMPANY_NAME", nullable = false)
	private String companyName;
	
	@Column(name = "WEBSITE")
	private String website;
	
	@Column(name = "LOGO_IMAGE_URL")
	private String logoImageUrl;
	
	@Column(name = "ADDRESS")
	private String address;
	
	@Column(name = "DESCROPTION")
	private String description;
	
	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "company")
	private Set<Position> positionSet = new HashSet<Position>();

	public long getcID() {
		return cID;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getLogoImageUrl() {
		return logoImageUrl;
	}

	public void setLogoImageUrl(String logoImageUrl) {
		this.logoImageUrl = logoImageUrl;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
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

	public Set<Position> getPositionSet() {
		return positionSet;
	}

	public void setPositionSet(Set<Position> positionSet) {
		this.positionSet = positionSet;
	}
	
	public String getJSON(){
		
		try{
			JSONObject result = new JSONObject();
			result.put("cid", getcID());
			result.put("name", getCompanyName());
			result.put("website", getWebsite());
			result.put("image_url", getLogoImageUrl());
			result.put("address", getAddress());
			result.put("description", getDescription());
			result.put("email", getEmail());
			
			JSONObject positions = new JSONObject();
			Set<Position> positionsObj = getPositionSet();
			
			JSONObject[] positionArray = new JSONObject[positionsObj.size()];
			int count = 0;
			for(Position i : positionsObj){
				JSONObject position = new JSONObject();
				position.put("pid", i.getpID());
				position.put("title", i.getTitle());
				position.put("description", i.getDescription());
				position.put("responsibility",  i.getResponsibility());
				position.put("office",  i.getOfficeLocation());
				
				positionArray[count] = position;
				count++;
			}
			positions.put("position",  positionArray);
			
			result.put("positions", positions);
			return result.toString();
		}
		catch(JSONException e){
			return e.toString();
		}		
	}
}
