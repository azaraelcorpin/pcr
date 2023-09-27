package com.spms.pcr.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spms.pcr.DataSource_PCR.service.UserService;
import com.spms.pcr.DataSource_PCR.service.UtilityService;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/user/")
public class UserController {
    
    @Autowired
    private UtilityService utilityService;

    @Autowired
    private UserService userService;

     @PostMapping(path = "all")
    public ResponseEntity<Object> getAllUser(@RequestHeader("Authorization") String authorization,@RequestHeader("X-IV") String ivHeader){
        //insert here for authentication from request header! value=authorization.
        String param = "";
        try{
             param = utilityService.decryptDataAuth(authorization,ivHeader);

             JSONObject obj =  new JSONObject(param);
             System.out.println(obj.toString());
             return new ResponseEntity<Object>(utilityService.renderJsonResponse("200","Auth","Auth",obj),HttpStatus.OK);
            //  if(!param.contains("@msugensan.edu.ph"))
            //     throw new Exception("Unauthorize");
            //  if(!obj.getBoolean("AdminUser"))
            //     throw new Exception("Unauthorize");
        }catch(Exception e){
            System.out.println(e);
           return new ResponseEntity<Object>(utilityService.renderJsonResponse("401", "Unauthorized"),
                HttpStatus.UNAUTHORIZED);
        }
        // return userService.getAllUser();
    }

}
