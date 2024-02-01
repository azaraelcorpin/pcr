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

import com.spms.pcr.DataSource_PCR.service.OfficeService;
import com.spms.pcr.DataSource_PCR.service.UtilityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/office/")
@Api(value="Office Contoller", tags = " Offices")
public class OfficeController {
    
    @Autowired
    private UtilityService utilityService;

    @Autowired
    private OfficeService officeService;

     @PostMapping(path = "all")
     @ApiOperation(value = "Get List of Offices")     
    public ResponseEntity<Object> getAllOffices(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             System.out.println(userJsonObject.toString());
 
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", e.getMessage()),
                HttpStatus.UNAUTHORIZED);
        }
        return officeService.getAllOffice();
    }

    @PostMapping(path = "new")
    @ApiOperation(value = "save new Office")     
    public ResponseEntity<Object> newOffice(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "code<br>description<br>topOfficeId<br>is_sector", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("new Office by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return officeService.newOffice(params);
    }

    @PostMapping(path = "update")
    @ApiOperation(value = "save updated Office")     
    public ResponseEntity<Object> updateOffice(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "id<br>code<br>Description<br>top_office<br>is_sector", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("update Office by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return officeService.updateOffice(params);
    }    

    @PostMapping(path = "delete")
    @ApiOperation(value = "delete Office")     
    public ResponseEntity<Object> deleteOffice(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "id", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("Delete Office by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return officeService.deleteOffice(params);
    }

    @PostMapping(path = "getOfficeData")
    @ApiOperation(value = "get Office Data")     
    public ResponseEntity<Object> getOfficeData(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "id", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("Get Office data by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return officeService.getOfficeData(params);
    }

}
