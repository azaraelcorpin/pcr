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

import com.spms.pcr.DataSource_PCR.service.PcrScheduleService;
import com.spms.pcr.DataSource_PCR.service.UtilityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/pcr_schedule/")
@Api(value="Pcr Schedule Contoller", tags = " PCR Schedules")
public class PcrScheduleController {
    
    @Autowired
    private UtilityService utilityService;

    @Autowired
    private PcrScheduleService pcrScheduleService ;

     @PostMapping(path = "all")
     @ApiOperation(value = "Get List of Schedules")     
    public ResponseEntity<Object> getAllSchedule(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             System.out.println(userJsonObject.toString());
 
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", e.getMessage()),
                HttpStatus.UNAUTHORIZED);
        }
        return pcrScheduleService.getAllSchedule();
    }

    @PostMapping(path = "new")
    @ApiOperation(value = "save new Schedule")     
    public ResponseEntity<Object> newSched(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "date_start<br>date_end", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("new PCR_Schedule by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return pcrScheduleService.newSchedule(params);
    }

    @PostMapping(path = "update")
    @ApiOperation(value = "save updated Scheduled")     
    public ResponseEntity<Object> updateShed(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "date_start<br>date_end<br>id<br>status", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("update PCR_Schedule by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        System.out.print(params.toString());
        return pcrScheduleService.updateSchedule(params);
    }    


    @PostMapping(path = "delete")
    @ApiOperation(value = "delete Schedule")     
    public ResponseEntity<Object> deleteSched(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "id", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("Delete PCR_ Schedule by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return pcrScheduleService.deleteSched(params);
    }

}
