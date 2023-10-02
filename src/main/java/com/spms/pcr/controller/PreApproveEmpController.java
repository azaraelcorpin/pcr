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

import com.spms.pcr.DataSource_PCR.service.PreApproveEmpService;
import com.spms.pcr.DataSource_PCR.service.UtilityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/preApproveEmp/")
@Api(value="PreApproveEmp Contoller", tags = " preApproveEmp")
public class PreApproveEmpController {
    
    @Autowired
    private UtilityService utilityService;

    @Autowired
    private PreApproveEmpService preApproveEmpService;

     @PostMapping(path = "all")
     @ApiOperation(value = "Get List of preApproveEmps")     
    public ResponseEntity<Object> getAllPreApproveEmps(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             System.out.println(userJsonObject.toString());
 
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return preApproveEmpService.getAllEmployee();
    }

    @PostMapping(path = "new")
    @ApiOperation(value = "save new PreApproveEmp")     
    public ResponseEntity<Object> newPreApproveEmp(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "lname<br>fname<br>mname<br>email<br>position<br>officeId<br>pcrType<br>status<br>empId", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("new PreApproveEmp by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return preApproveEmpService.newEmployee(params);
    }

    @PostMapping(path = "update")
    @ApiOperation(value = "save updated PreApproveEmp")     
    public ResponseEntity<Object> updateOffice(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "id<br>lname<br>fname<br>mname<br>email<br>position<br>officeId<br>pcrType<br>status<br>empId", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("update PreApproveEmp by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return preApproveEmpService.updateEmployee(params);
    }    
}
