package org.katarsis.rentflat.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="job_log")
public class JobLog implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="operation_type")
	private String operationType;
	
	@Column(name="parameters")
	private String parameters;
	
	@Column(name="date_running")
	@Temporal(value=TemporalType.DATE)
	private Date dateRunning;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getOperationType() {
		return operationType;
	}

	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public Date getDateRunning() {
		return dateRunning;
	}

	public void setDateRunning(Date dateRunning) {
		this.dateRunning = dateRunning;
	}
	
	
}
