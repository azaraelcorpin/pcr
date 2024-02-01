package com.spms.pcr.DataSource_PCR.service;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.spms.pcr.DataSource_PCR.model.Employee;
import com.spms.pcr.DataSource_PCR.model.OfficeEmployee;
import com.spms.pcr.DataSource_PCR.repository.EmployeeRepository;
import com.spms.pcr.DataSource_PCR.repository.OfficeEmployeeRepository;

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
            Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
            Employee employee = optionalEmployee.orElse(null);
            if(employee != null){
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("409", email+" already exist"),
             HttpStatus.CONFLICT);
            }
                employee = new Employee();
                employee.setEmail(email);
                employee.setFname(fname);
                employee.setLname(lname);
                employee.setMname(mname);
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
            Optional<Employee> optionalEmployee = employeeRepository.findByEmail(email);
            Employee employee = optionalEmployee.orElse(null);
            if(employee != null){
                if(employee.getId() != id)
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("409", email+" already exist"),
             HttpStatus.CONFLICT);
            }
                employee = new Employee();
                employee.setId(id);
                employee.setEmail(email);
                employee.setFname(fname);
                employee.setLname(lname);
                employee.setMname(mname);
                employeeRepository.save(employee);
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("202", "Successfully updated"),
                HttpStatus.OK);
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> getAllEmployee(){
        try{
            List<Map<String,Object>> list = employeeRepository.getAll();
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","EMPLOYEES",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> searchEmployee(@RequestBody Map<String,Object> params){
        try{
            String empName = utilityService.getString("empName", params);
            //Paging
            // int pageNo = utilityService.toInteger(params.get("pageNo"));
            // int pageSize = utilityService.toInteger(params.get("pageSize"));
            
            // Pageable limit = pageSize == 0?Pageable.unpaged():PageRequest.of(pageNo, pageSize);
            // Page<Map<String,Object>> list = employeeRepository.searchEmployeePage(empName, limit);

            List<Map<String,Object>> list = employeeRepository.searchEmployee(empName);

        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","EMPLOYEES",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @Autowired
    private OfficeEmployeeRepository officeEmployeeRepository;
    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> deleteEmployee(@RequestBody Map<String,Object> params){
        try{
            
            Long id = utilityService.toLong(params.get("id"));
            Optional<OfficeEmployee> emp = officeEmployeeRepository.findTopByEmpId(id);
            if(emp.isPresent()){
                throw new Exception("Cannot delete. Employee is currently as "+emp.get().getPosition()+". Please Check Office Management");
            }
                employeeRepository.deleteById(id);       
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("204", "Successfully deleted"),
                HttpStatus.OK);
        }catch(Exception e ){      
            log.error(e.getMessage()); 
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);         
        }        
    }   
}
