package springboot.crud.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document (collection = "Patient")
public class Patient {

	@Transient
    public static final String SEQUENCE_NAME = "patient_sequence";
	
	@Id
	private long id;
	
	@NotBlank
    @Size(max=100)
    @Indexed(unique=true)
	private String patientName;
	
	@NotBlank
    @Size(max=100)
    @Indexed(unique=true)
	private String patientContactNo;
	
	
	
	public Patient() {

	}

	public Patient(long id, @NotBlank @Size(max = 100) String patientName,
			@NotBlank @Size(max = 100) String patientContactNo) {
		super();
		this.id = id;
		this.patientName = patientName;
		this.patientContactNo = patientContactNo;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getPatientName() {
		return patientName;
	}
	public void setPatientName(String patientName) {
		this.patientName = patientName;
	}
	public String getPatientContactNo() {
		return patientContactNo;
	}
	public void setPatientContactNo(String patientContactNo) {
		this.patientContactNo = patientContactNo;
	}
	
	@Override
	public String toString() {
		return "Patient [id=" + id + ", patientName=" + patientName + ", patientContactNo=" + patientContactNo + "]";
	}
	

}
