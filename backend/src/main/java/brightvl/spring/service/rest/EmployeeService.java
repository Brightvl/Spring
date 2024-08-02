package brightvl.spring.service.rest;

import brightvl.spring.config.aspect.Recover;
import brightvl.spring.model.Employee;
import brightvl.spring.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Recover
    public Optional<Employee> findById(Long id) {
        return employeeRepository.findById(id);
    }
    @Recover
    public List<Employee> findAll() {
        return employeeRepository.findAll();
    }
    @Recover(noRecoverFor = {HttpMessageNotReadableException.class})
    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
    @Recover
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }
}
