package pro.asfert.jobparser.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import pro.asfert.jobparser.domain.Parser;

@Controller
@RequestMapping("/")
public class HelloController extends MultiActionController {
	@RequestMapping(method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {
        model.addAttribute("message", "Hello world!");
        return "hello";

    }
}