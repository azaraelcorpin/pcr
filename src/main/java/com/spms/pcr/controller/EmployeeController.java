package com.spms.pcr.controller;

import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spms.pcr.DataSource_PCR.service.EmployeeService;
import com.spms.pcr.DataSource_PCR.service.UtilityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/employee/")
@Api(value="Employee Contoller", tags = " employee")
public class EmployeeController {
    
    @Autowired
    private UtilityService utilityService;

    @Autowired
    private EmployeeService employeeService;

     @PostMapping(path = "all")
     @ApiOperation(value = "Get List of employees")     
    public ResponseEntity<Object> getAllEmployees(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             System.out.println(userJsonObject.toString());
 
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return employeeService.getAllEmployee();
    }

    @PostMapping(path = "new")
    @ApiOperation(value = "save new Employee")     
    public ResponseEntity<Object> newEmployee(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "lname<br>fname<br>mname<br>email<br>position<br>officeId<br>pcrType<br>status", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("new Employee by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return employeeService.newEmployee(params);
    }

    @PostMapping(path = "update")
    @ApiOperation(value = "save updated Employee")     
    public ResponseEntity<Object> updateOffice(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "id<br>lname<br>fname<br>mname<br>email<br>position<br>officeId<br>pcrType<br>status", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("update Employee by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return employeeService.updateEmployee(params);
    }    
}
