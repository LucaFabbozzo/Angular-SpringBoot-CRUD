/*
L'annotazione @CrossOrigin consente le richieste di origine incrociata da un'origine specifica (in questo caso http://localhost:4200). Le richieste di origine incrociata sono tipicamente limitate dai browser web per motivi di sicurezza, quindi questa annotazione abilita le richieste dall'origine specificata.

L'annotazione @RestController indica che questa classe è un controller RESTful. Combina le annotazioni @Controller e @ResponseBody in una singola annotazione comoda.

L'annotazione @RequestMapping mappa tutti gli endpoint di questo controller a /api/v1/. Quindi, l'URL di base per tutti gli endpoint in questa classe sarà /api/v1/.

L'annotazione @Autowired viene utilizzata per iniettare un'istanza dell'interfaccia EmployeeRepository nel controller. Ciò consente al controller di interagire con il database e eseguire operazioni CRUD sull'entità Employee.

L'annotazione @GetMapping("/employees") mappa il metodo getAllEmployees per gestire le richieste HTTP GET all'endpoint /api/v1/employees. Recupera tutti gli employee dal database utilizzando il metodo employeeRepository.findAll() e li restituisce come lista.

L'annotazione @PostMapping("/employees") mappa il metodo createEmployee per gestire le richieste HTTP POST all'endpoint /api/v1/employees. Riceve una rappresentazione JSON di un oggetto Employee nel corpo della richiesta (@RequestBody Employee employee), salva l'employee nel database utilizzando il metodo employeeRepository.save(employee) e restituisce l'employee salvato come risposta.
 */


package com.fabbluca.springbackend.controller;

import com.fabbluca.springbackend.exception.ResourceNotFoundException;
import com.fabbluca.springbackend.model.Employee;
import com.fabbluca.springbackend.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api/v1/")
public class EmployeeController {

    @Autowired
    private EmployeeRepository employeeRepository;

    //get all employees
    @GetMapping("/employees")
    public List<Employee> getAllEmployees(){
        return employeeRepository.findAll();
    }

    //create employee rest api
    @PostMapping("/employees")
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeRepository.save(employee);
    }

    //get employee by id rest api
    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id: " + id));
        return ResponseEntity.ok(employee);
    }

    //update employee rest api
    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable Long id, @RequestBody Employee employeeDetails){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id: " + id));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());

        Employee updatedEmployee = employeeRepository.save(employee);
        return ResponseEntity.ok(updatedEmployee);
    }

    //delete employee rest api
    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Map<String, Boolean>> deleteEmployee(@PathVariable Long id){
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee does not exist with id: " + id));

        employeeRepository.delete(employee);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);
    }
}