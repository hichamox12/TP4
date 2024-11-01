package ma.mundia.mvcapp.repositories;

import ma.mundia.mvcapp.entites.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepositories extends JpaRepository<Patient,Long> {

    Page<Patient> findByNomContains(String kw,Pageable pageable);
}
