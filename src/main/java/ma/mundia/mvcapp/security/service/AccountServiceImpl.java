package ma.mundia.mvcapp.security.service;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.mundia.mvcapp.security.entities.AppRole;
import ma.mundia.mvcapp.security.entities.AppUser;
import ma.mundia.mvcapp.security.repo.AppRoleRepository;
import ma.mundia.mvcapp.security.repo.AppUserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Transactional
@AllArgsConstructor
public class AccountServiceImpl implements AccountService {
    private AppUserRepository appUserRepository ;
    private AppRoleRepository appRoleRepository;
    private PasswordEncoder passwordEncoder;


    @Override
    public AppUser addNewUser(String username, String password, String email, String confirmPassword) {
        AppUser appUser=appUserRepository.findByUsername(username);
        if(appUser!=null) throw new RuntimeException("this user already exist");
        if(!password.equals(confirmPassword)) throw new RuntimeException("Password not match ");
        appUser= AppUser.builder()
                .userId(UUID.randomUUID().toString())
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .build();
        AppUser savedAppUser = appUserRepository.save(appUser);
        return savedAppUser;
    }

    @Override
    public AppRole addNewRole(String role) {
        System.out.println("Searching for role: " + role);
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole != null) {
            System.out.println("Role already exists: " + role);
            return appRole; // Return existing role
        }
        System.out.println("Creating new role: " + role);
        appRole = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findById(role).get();
        appUser.getRoles().add(appRole);
        //appUserRepository.save(appUser);

    }

    @Override
    public void removeRoleFormUser(String username, String role) {
        AppUser appUser = appUserRepository.findByUsername(username);
        AppRole appRole=appRoleRepository.findById(role).get();
        appUser.getRoles().remove(appRole);
    }

    @Override
    public AppUser loadUserByUsername(String username) {
       return appUserRepository.findByUsername(username);

    }
}
