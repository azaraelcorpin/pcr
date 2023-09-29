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

import com.spms.pcr.DataSource_PCR.service.UserService;
import com.spms.pcr.DataSource_PCR.service.UtilityService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/user/")
@Api(value="User Contoller", tags = " Users")
public class UserController {
    
    @Autowired
    private UtilityService utilityService;

    @Autowired
    private UserService userService;

     @PostMapping(path = "all")
     @ApiOperation(value = "Get List of Users")     
    public ResponseEntity<Object> getAllUser(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             System.out.println(userJsonObject.toString());
 
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return userService.getAllUser();
    }

    @PostMapping(path = "getSessionId")
    @ApiOperation(value = "Get user's session Id")     
    public ResponseEntity<Object> getSessionId(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader){
        //insert here for authentication from request header! value=authorization.
        String param = "";
        try{
            String id = utilityService.decryptDataAuth(authorization, ivHeader);
            JSONObject obj =  new JSONObject(id);

             param = utilityService.generateSessionId(authorization,ivHeader);
             log.info("getSessionId by:"+obj.toString());

             System.out.println(param);
             return new ResponseEntity<Object>(utilityService.renderJsonResponse("200","Auth","sessionId",param),HttpStatus.OK);
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping(path = "new")
    @ApiOperation(value = "save new User")     
    public ResponseEntity<Object> newUser(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader, 
                                        @ApiParam(value = "email<br>userName<br>userType<br>officeId<br>privileges", required = true) @RequestBody Map<String, Object> params){
        //insert here for authentication from request header! value=authorization.
        try{
             JSONObject userJsonObject =  utilityService.validateSession(authorization, ivHeader);
             log.info("new User by:"+userJsonObject.toString());
             
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        return userService.newUser(params);
    }
}
