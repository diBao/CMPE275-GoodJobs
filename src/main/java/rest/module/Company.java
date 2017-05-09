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
//@XmlRootElement
@JsonRootName(value = "Company")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Company {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "CID")
	private Long cID;
	
	@Column(name = "COMPANY_NAME", nullable = false)
	private String companyName;
	
	@Column(name = "WEBSITE", nullable = false)
	private String website;
	
	@Column(name = "LOGO_IMAGE_URL", nullable = false)
	private String logoImageUrl;
	
	@Column(name = "ADDRESS", nullable = false)
	private String address;
	
	@Column(name = "DESCROPTION", nullable = false)
	private String description;
	
	@Column(name = "EMAIL", nullable = false, unique = true)
	private String email;
	
	@Column(name = "PASSWORD", nullable = false)
	private String password;
	
	@OneToMany(mappedBy = "pID")
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
	
	public JSONObject getJSON() throws JSONException{
		
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
		positions.put("passenger",  positionArray);
		
		result.put("positions", positions);
		return result;
	}
}
