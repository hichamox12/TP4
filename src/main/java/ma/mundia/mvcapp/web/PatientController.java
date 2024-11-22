package ma.mundia.mvcapp.web;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import ma.mundia.mvcapp.entites.Patient;
import ma.mundia.mvcapp.repositories.PatientRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;


@Controller
@AllArgsConstructor
public class PatientController {
    @Autowired

    private PatientRepositories patientRepositories;

    @GetMapping(path = "/user/index")
    public String patients(Model model,
                           @RequestParam(name="page", defaultValue="0") int page,
                           @RequestParam(name="size", defaultValue="5") int size,
                           @RequestParam(name="keyword", defaultValue="") String keyword) {



        Page<Patient> pagePatients = patientRepositories.findByNomContains(keyword, PageRequest.of(page, size));
        model.addAttribute("listePatients", pagePatients.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagePatients.getTotalPages());
        model.addAttribute("totalItems", pagePatients.getTotalElements());
        model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
        model.addAttribute("keyword", keyword);
        return "patients";
    }

    @GetMapping("/admin/delete")
    public String delete(@RequestParam(name="id") Long id) {
        patientRepositories.deleteById(id);
        return "redirect:/user/index";
    }

    @GetMapping("/admin/fromPatients")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public String fromPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "fromPatients";
    }
    @PostMapping("/admin/save")
    @PreAuthorize("hasRole('ROLE_ADMIN')")

    public String savePatient(Model model,@Valid Patient patient ,BindingResult bindingResult,@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "") String keyword ) {
        if (bindingResult.hasErrors()) return "formPatient";
        patientRepositories.save(patient);
        return "redirect:/user/index";
    }

@GetMapping("/admin/editPatient")
@PreAuthorize("hasRole('ROLE_ADMIN')")

public String editPatient( Model model,@RequestParam(name = "id") Long id){
    Patient patient=patientRepositories.findById(id).orElse(null);
    model.addAttribute("patient",patient);
    return "editPatient";

}

    @GetMapping("/")
    public String home(){
        return "redirect:/user/index";
    }
}
