package com.spms.pcr.DataSource_PCR.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.spms.pcr.DataSource_PCR.model.Employee;
import com.spms.pcr.DataSource_PCR.model.Office;
import com.spms.pcr.DataSource_PCR.repository.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EmployeeService {
    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private UtilityService utilityService;


    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> newEmployee(@RequestBody Map<String,Object> params){
        try{
            String lname = utilityService.getString("lname", params);
            String fname = utilityService.getString("fname", params); 
            String mname = utilityService.getString("mname", params);
            String email = utilityService.getString("email", params); 
            Long office_id = utilityService.toLong(params.get("officeId"));
            String position = utilityService.getString("position", params);
            String pcrType = utilityService.getString("pcrType", params);
            String status = utilityService.getString("status", params);   
            Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
            Employee employee = optionalEmployee.orElse(null);
            if(employee != null){
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("412", email+" already exist"),
             HttpStatus.CONFLICT);
            }
                employee = new Employee();
                employee.setEmail(email);
                employee.setFname(fname);
                employee.setLname(lname);
                employee.setMname(mname);
                employee.setOfficeId(office_id);
                employee.setPcrType(pcrType);
                employee.setPosition(position);
                employee.setStatus(status);
                employeeRepository.save(employee);
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("201", "New Employee Created"),
                HttpStatus.OK);
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> updateEmployee(@RequestBody Map<String,Object> params){
        try{
            Long id = utilityService.toLong(params.get("id"));
            String lname = utilityService.getString("lname", params);
            String fname = utilityService.getString("fname", params); 
            String mname = utilityService.getString("mname", params);
            String email = utilityService.getString("email", params); 
            Long office_id = utilityService.toLong(params.get("officeId"));
            String position = utilityService.getString("position", params);
            String pcrType = utilityService.getString("pcrType", params);
            String status = utilityService.getString("status", params);   
            Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
            Employee employee = optionalEmployee.orElse(null);
            if(employee != null){
                if(employee.getId() != id)
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("412", email+" already exist"),
             HttpStatus.CONFLICT);
            }
                employee = new Employee();
                employee.setEmail(email);
                employee.setFname(fname);
                employee.setLname(lname);
                employee.setMname(mname);
                employee.setOfficeId(office_id);
                employee.setPcrType(pcrType);
                employee.setPosition(position);
                employee.setStatus(status);
                employeeRepository.save(employee);
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("202", "Updated"),
                HttpStatus.OK);
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> getAllEmployee(){
        try{
            List<Employee> list = employeeRepository.findAll();
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","EMPLOYEES",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  
}
