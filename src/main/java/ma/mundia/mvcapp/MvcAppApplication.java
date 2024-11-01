package ma.mundia.mvcapp;

import ma.mundia.mvcapp.entites.Patient;
import ma.mundia.mvcapp.repositories.PatientRepositories;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class MvcAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvcAppApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunner(PatientRepositories patientRepositories) {
        return arg -> {
            patientRepositories.save(new Patient(null, "hicham", new Date(), false, 12));
            patientRepositories.save(new Patient(null, "oussama", new Date(), false, 190));
            patientRepositories.save(new Patient(null, "ahmed", new Date(), false, 18));
            patientRepositories.save(new Patient(null, "achraf", new Date(), false, 128));
            patientRepositories.findAll().forEach(p -> {
                System.out.println(p.getNom());
            });
        };

    }

}
