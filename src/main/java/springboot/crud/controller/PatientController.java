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

	private Object updatedPatient;

	@GetMapping("/patient")
	public List<Patient> getAllPatients() {
		return patientRepository.findAll();
	}

	@GetMapping("/patients/{id}")
	public ResponseEntity<Patient> getPatientById(@PathVariable(value = "id") Long patientId)
			throws ResourceNotFoundException {
		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new ResourceNotFoundException("Patient details not found for this id :: " + patientId));
		return ResponseEntity.ok().body(patient);
	}

	@PostMapping("/patients")
	public Patient createPatient(@Valid @RequestBody Patient patient) {
		patient.setId(sequenceGeneratorService.generateSequence(Patient.SEQUENCE_NAME));
		return patientRepository.save(patient);
	}

	@PutMapping("/patient/{id}")
	public ResponseEntity<Patient> updatepatient(@PathVariable(value = "id") Long patientId,
			@Valid @RequestBody Patient patientDetails) throws ResourceNotFoundException {
		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new ResourceNotFoundException("patient not found for this id :: " + patientId));

		patient.setPatientName(patientDetails.getPatientName());
		patient.setPatientContactNo(patientDetails.getPatientContactNo());
		final Patient updatedPatient = patientRepository.save(patient);
		return ResponseEntity.ok(updatedPatient);
	}

	@DeleteMapping("/patients/{id}")
	public Map<String, Boolean> deletePatient(@PathVariable(value = "id") Long patientId)
			throws ResourceNotFoundException {
		Patient patient = patientRepository.findById(patientId)
				.orElseThrow(() -> new ResourceNotFoundException("patient not found for this id :: " + patientId));

		patientRepository.delete(patient);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}
