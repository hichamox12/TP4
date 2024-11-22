package ma.mundia.mvcapp.security.service;

import ma.mundia.mvcapp.security.entities.AppRole;
import ma.mundia.mvcapp.security.entities.AppUser;

public interface AccountService {
    AppUser addNewUser(String usename, String password,String email,String confirmPassword);
    AppRole addNewRole(String role);
    void addRoleToUser(String username, String role);
    void removeRoleFormUser(String username, String role);
    AppUser loadUserByUsername(String username);
}
