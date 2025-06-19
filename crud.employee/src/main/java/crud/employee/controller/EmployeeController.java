package crud.employee.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import crud.employee.entities.Employee;
import crud.employee.services.EmployeeService;

@Controller
public class EmployeeController {
	
	@Autowired
	EmployeeService service;
	
	@GetMapping("/add")
	public String add() {
		return "add_employee";
	}
	
	@PostMapping("/register")
	public String register(@ModelAttribute Employee emp) {
		service.addEmployee(emp);
		return "index";
	}
	
	@GetMapping("/openSearch")
	public String openSearch() {
		return "search_emp";
	}
	
	@PostMapping("/search")
	public String search(@RequestParam int id, Model m) {
		Employee emp = service.searchEmployee(id);
		m.addAttribute("employee", emp);
		return "show_employee";
		
	}
	
	@GetMapping("/openUpdate")
	public String openUpdate() {
		return "update_emp";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute Employee emp) {
		service.updateEmployee(emp);
		return "index";
	}
	
	@GetMapping("/openDelete")
	public String openDelete() {
		return "delete";
	}
	
	@PostMapping("/delete")
	public String delete(@ModelAttribute Employee emp) {
		service.deleteEmployee(emp);
		return "index";
	}
	
	@GetMapping("/showAll")
	public String showAll(Model m) {
		List<Employee> empList = service.showAll();
		m.addAttribute("employeeList", empList);
		return "showAll";
	}
	
	@GetMapping("/openLogin")
	public String openLogin() {
		return "login";
	}
	
	@PostMapping("/login") 
	public String login(@RequestParam int id, String name) {
		Employee emp = service.searchEmployee(id);
		if (emp.getName().equals(name)) {
			return "success";
		}  
		return "fail";
	}

}
