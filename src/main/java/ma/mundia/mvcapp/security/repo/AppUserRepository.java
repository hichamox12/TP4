package ma.mundia.mvcapp.security.repo;

import ma.mundia.mvcapp.security.entities.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  AppUserRepository extends JpaRepository<AppUser,String> {
    AppUser findByUsername(String username);

}
