package ma.mundia.mvcapp.web;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.Model;
import lombok.AllArgsConstructor;
import ma.mundia.mvcapp.entites.Patient;
import ma.mundia.mvcapp.repositories.PatientRepositories;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@AllArgsConstructor
public class PatientController {

    private PatientRepositories patientRepositories;

    @GetMapping(path = "/index")
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

    @GetMapping("/delete")
    public String delete(@RequestParam("id") Long id) {
        patientRepositories.deleteById(id);
        return "redirect:/index";
    }
    @GetMapping ("/patients")
    @ResponseBody
    public List<Patient> getPatientList() {
        return patientRepositories.findAll();
    }
    @GetMapping("/fromPatients")
    public String fromPatients(Model model){
        model.addAttribute("patient",new Patient());
        return "fromPatients";
    }
    @PostMapping("/save")
    public String savePatient(Model model,@Valid Patient patient ,BindingResult bindingResult,@RequestParam(defaultValue = "0")int page,@RequestParam(defaultValue = "") String keyword ) {

        patientRepositories.save(patient);
        return "fromPatients";
    }

@GetMapping("/editPatient")
public String editPatient(Model model , Long id ,String keyword, int page){
    Patient patient = patientRepositories.findById(id).orElse(null);
    if (patient == null) throw new RuntimeException("Patient not found");
    model.addAttribute("patient",patient);
    model.addAttribute("pages",page);
    model.addAttribute("keyword",keyword);
    return "editPatient";
}
    @GetMapping("/")
    public String home(){
        return "redirect:/index";
    }
}
