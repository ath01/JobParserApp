package pro.asfert.jobparser.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pro.asfert.jobparser.domain.Vacancy;
import pro.asfert.jobparser.service.VacancyService;
import pro.asfert.jobparser.service.VacancyServiceImpl;

import java.util.List;
import java.util.Map;

@Controller
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @RequestMapping("/index")
    public String listContacts(Map<String, Object> map) {

        map.put("vacancy", new Vacancy());
        map.put("vacancyList", vacancyService.listVacancy());

        return "vacancy";
    }

    @RequestMapping("/")
    public String home() {
        return "redirect:/index";
    }


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String addVacancy(@ModelAttribute("vacancy") Vacancy vacancy,
                             BindingResult result) {

        vacancyService.addVacancy(vacancy);

        return "redirect:/index";
    }

    @RequestMapping("/delete/{vacancyId}")
    public String deleteContact(@PathVariable("vacancyId") Integer vacancyId) {

        vacancyService.removeVacancy(vacancyId);

        return "redirect:/index";
    }


    /*@RequestMapping(value = "/FindVacancy",method = RequestMethod.POST)
    public void FindVacancy(@PathVariable("query") String query) {
        vacancyService.FindVacancy(query);
    }*/


}
