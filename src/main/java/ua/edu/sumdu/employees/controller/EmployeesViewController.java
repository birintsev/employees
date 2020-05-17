package ua.edu.sumdu.employees.controller;

import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import ua.edu.sumdu.employees.model.Employee;
import ua.edu.sumdu.employees.repository.EmployeeRepository;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@AllArgsConstructor
public class EmployeesViewController {
    private final EmployeeRepository employeeRepository;

    // todo pagination
    @RequestMapping(method = RequestMethod.GET, path = "/employees", params = {"page", "size"})
    public ModelAndView showAllEmployees(ModelAndView modelAndView, Pageable pageable) {
        Page<Employee> employeesPage = employeeRepository.findAll(pageable);

        int totalPagesCount = employeesPage.getTotalPages();
        modelAndView.getModelMap().addAttribute("pageSize", employeesPage.getSize());
        modelAndView.getModelMap().addAttribute("totalPagesCount", totalPagesCount);
        modelAndView.getModelMap().addAttribute("pagesNumbers", IntStream.rangeClosed(0, totalPagesCount - 1).boxed().collect(Collectors.toList()));
        modelAndView.getModelMap().addAttribute("currentPage", employeesPage.getNumber());
        modelAndView.setViewName("employees");
        modelAndView.getModelMap().addAttribute("employeesPage", employeesPage);
        return modelAndView;
    }

    @RequestMapping(method = RequestMethod.GET, path = "/employees", params = {"!page", "!size"})
    public RedirectView redirectWithParameters() {
        return new RedirectView("/employees?page=0&size=5");
    }
}
