package springboot.crud.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import springboot.crud.model.Patient;

@Repository
public interface PatientRepository extends MongoRepository<Patient, Long>{

}
