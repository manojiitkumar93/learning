package learning.hibernate.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CollectionId;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

@Entity(name = "STUDENT_INFO")
public class StudentInfo {

	private Integer userId;
	private String userName;
	private String description;
	private Date creationDate;
	private Collection<Address> addressSet = new ArrayList();

	@ElementCollection(fetch = FetchType.LAZY)
	@CollectionTable(name = "STUDENT_ADDRESS", joinColumns = @JoinColumn(name = "STUDENT_ID"))
	@GenericGenerator(name = "sequenceGen", strategy = "sequence")
	@CollectionId(columns = { @Column(name = "ADDRESS_ID") }, generator = "sequenceGen", type = @Type(type = "long"))
	public Collection<Address> getAddressSet() {
		return addressSet;
	}

	public void setAddressSet(Collection<Address> addressSet) {
		this.addressSet = addressSet;
	}

	@Lob
	@Column(name = "DESCRIPTION")
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Temporal(TemporalType.TIME)
	@Column(name = "CREATION_DATE")
	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "USER_ID")
	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME")
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
}
