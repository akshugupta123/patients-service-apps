package springboot.crud.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import springboot.crud.exception.ResourceNotFoundException;
import springboot.crud.model.Patient;
import springboot.crud.repository.PatientRepository;
import springboot.crud.service.SequenceGeneratorService;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1")
public class PatientController {
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private SequenceGeneratorService sequenceGeneratorService;

	@GetMapping("/patient")
	public List<Patient> getAllEmployees() {
		return patientRepository.findAll();
	}

	@GetMapping("/patients/{id}")
	public ResponseEntity<Patient> getEmployeeById(@PathVariable(value = "id") Long patientId)
			throws ResourceNotFoundException {
		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Patient details not found for this id :: " + patientId));
		return ResponseEntity.ok().body(patient);
	}

	@PostMapping("/patients")
	public Patient createEmployee(@Valid @RequestBody Patient employee) {
		employee.setId(sequenceGeneratorService.generateSequence(Patient.SEQUENCE_NAME));
		return patientRepository.save(employee);
	}

	@PutMapping("/patient/{id}")
	public ResponseEntity<Patient> updateEmployee(@PathVariable(value = "id") Long employeeId,
			@Valid @RequestBody Patient employeeDetails) throws ResourceNotFoundException {
		Patient employee = patientRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		employee.setPatientName(employeeDetails.getPatientName());
		employee.setPatientContactNo(employeeDetails.getPatientContactNo());
		final Patient updatedEmployee = patientRepository.save(employee);
		return ResponseEntity.ok(updatedEmployee);
	}

	@DeleteMapping("/patients/{id}")
	public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Long employeeId)
			throws ResourceNotFoundException {
		Patient employee = patientRepository.findById(employeeId)
				.orElseThrow(() -> new ResourceNotFoundException("Employee not found for this id :: " + employeeId));

		patientRepository.delete(employee);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
