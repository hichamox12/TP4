package ma.mundia.mvcapp.security.repo;

import ma.mundia.mvcapp.security.entities.AppRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppRoleRepository extends JpaRepository<AppRole,String> {

}
