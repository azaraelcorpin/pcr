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
import com.spms.pcr.DataSource_PCR.model.PreApproveEmp;
import com.spms.pcr.DataSource_PCR.repository.EmployeeRepository;
import com.spms.pcr.DataSource_PCR.repository.PreApproveEmpRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PreApproveEmpService {
    @Autowired
    private PreApproveEmpRepository preApproveEmpRepository;

    @Autowired
    private UtilityService utilityService;


    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> newEmployee(@RequestBody Map<String,Object> params){
        try{
            Long empId = utilityService.toLong(params.get("empId"));
            String lname = utilityService.getString("lname", params);
            String fname = utilityService.getString("fname", params); 
            String mname = utilityService.getString("mname", params);
            String email = utilityService.getString("email", params); 
            Long office_id = utilityService.toLong(params.get("officeId"));
            String position = utilityService.getString("position", params);
            String pcrType = utilityService.getString("pcrType", params);
            String status = utilityService.getString("status", params);   
            Optional<PreApproveEmp> optionalPreApproveEmp = preApproveEmpRepository.findByEmail(email);
            PreApproveEmp preApproveEmp = optionalPreApproveEmp.orElse(null);
            if(preApproveEmp != null){
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("412", email+" already exist"),
             HttpStatus.CONFLICT);
            }
                preApproveEmp = new PreApproveEmp();
                preApproveEmp.setEmail(email);
                preApproveEmp.setFname(fname);
                preApproveEmp.setLname(lname);
                preApproveEmp.setMname(mname);
                preApproveEmp.setOfficeId(office_id);
                preApproveEmp.setPcrType(pcrType);
                preApproveEmp.setPosition(position);
                preApproveEmp.setStatus(status);
                preApproveEmp.setEmployeeId(empId);
                preApproveEmpRepository.save(preApproveEmp);
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("201", "New Pre Approve Employee Created"),
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
            Long empId = utilityService.toLong(params.get("empId"));
            String lname = utilityService.getString("lname", params);
            String fname = utilityService.getString("fname", params); 
            String mname = utilityService.getString("mname", params);
            String email = utilityService.getString("email", params); 
            Long office_id = utilityService.toLong(params.get("officeId"));
            String position = utilityService.getString("position", params);
            String pcrType = utilityService.getString("pcrType", params);
            String status = utilityService.getString("status", params);   
            Optional<PreApproveEmp> optionalEmployee = preApproveEmpRepository.findByEmail(email);
            PreApproveEmp employee = optionalEmployee.orElse(null);
            if(employee != null){
                if(employee.getId() != id)
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("412", email+" already exist"),
             HttpStatus.CONFLICT);
            }
                employee = new PreApproveEmp();
                employee.setEmail(email);
                employee.setFname(fname);
                employee.setLname(lname);
                employee.setMname(mname);
                employee.setOfficeId(office_id);
                employee.setPcrType(pcrType);
                employee.setPosition(position);
                employee.setStatus(status);
                employee.setEmployeeId(empId);
                preApproveEmpRepository.save(employee);
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
            List<PreApproveEmp> list = preApproveEmpRepository.findAll();
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","PRE_APPROVED_EMPLOYEES",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  
}
