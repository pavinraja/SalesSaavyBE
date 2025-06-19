package crud.employee.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import crud.employee.entities.Employee;
import crud.employee.repository.EmployeeRepository;

@Service
public class EmployeeServiceImplementation implements EmployeeService {
	
	@Autowired
	EmployeeRepository repo;
	
	public void addEmployee(Employee emp) {
		repo.save(emp);
	}

	@Override
	public void updateEmployee(Employee emp) {
		repo.save(emp);
		
	}

	@Override
	public void deleteEmployee(Employee emp) {
		repo.delete(emp);
		
	}

	@Override
	public Employee searchEmployee(int id) {
		return repo.findById(id).get();
	}

	@Override
	public List<Employee> showAll() {
		return repo.findAll();
	}

}
