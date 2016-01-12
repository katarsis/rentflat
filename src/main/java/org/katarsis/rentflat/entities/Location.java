package org.katarsis.rentflat.entities;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;


@Entity
@Table(name="location")
public class Location implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private Integer xpoint;
	private Integer ypoint;
	private String description;
	
	//private Set<Flat> flatRecords = new HashSet<Flat>();
	
	public Location() {
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getXpoint() {
		return xpoint;
	}

	public void setXpoint(Integer xpoint) {
		this.xpoint = xpoint;
	}

	public Integer getYpoint() {
		return ypoint;
	}

	public void setYpoint(Integer ypoint) {
		this.ypoint = ypoint;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	/*@OneToMany(fetch=FetchType.LAZY, mappedBy="location")
	public Set<Flat> getFlatRecords() {
		return flatRecords;
	}

	public void setFlatRecords(Set<Flat> flatRecords) {
		this.flatRecords = flatRecords;
	}

	*/
}
