package com.spms.pcr.DataSource_PCR.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.spms.pcr.DataSource_PCR.model.User;
import com.spms.pcr.DataSource_PCR.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UtilityService utilityService;


    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> newUser(@RequestBody Map<String,Object> params){
        try{
            String email = utilityService.getString("email", params);
            String userName = utilityService.getString("userName", params);
            String userType = utilityService.getString("userType", params);
            Long office_id = utilityService.toLong(params.get("officeId"));
            String privileges = utilityService.getString("privileges", params);
            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user = optionalUser.orElse(null);
            if(user != null){
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("409", email+" already exist"),
             HttpStatus.CONFLICT);
            }else{
                user = new User();
                user.setEmail(email);
                user.setUsername(userName);
                user.setUserType(userType);
                user.setOfficeId(office_id);
                user.setStatus(User.STATUS_ACTIVE);
                user.setPrivileges(privileges);
                userRepository.save(user);
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("201", "New User Created"),
                HttpStatus.OK);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> getAllUser(){
        try{
            List<Map<String,Object>> list = userRepository.getAll();
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","USERS",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> updateUser(@RequestBody Map<String,Object> params){
        try{
            String email_old = utilityService.getString("email_old", params);
            String email = utilityService.getString("email", params);
            String userName = utilityService.getString("userName", params);
            String userType = utilityService.getString("userType", params);
            Long office_id = utilityService.toLong(params.get("officeId"));
            String privileges = utilityService.getString("privileges", params);
            String status = utilityService.getString("status", params);
            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user = optionalUser.orElse(null);
            if(user != null){
                if(!user.getEmail().equalsIgnoreCase(email_old))
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("409", email+" already exist"),
             HttpStatus.CONFLICT);
            }
                user = new User();
                user.setEmail(email);
                user.setUsername(userName);
                user.setUserType(userType);
                user.setOfficeId(office_id);
                user.setStatus(status);
                user.setPrivileges(privileges);
                userRepository.save(user);

            if(!email.equalsIgnoreCase(email_old))
                userRepository.deleteByEmail(email_old);
            
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("202", "Successfully updated"),
                HttpStatus.OK);
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> deleteUser(@RequestBody Map<String,Object> params){
        try{
            String email = utilityService.getString("email", params);

                userRepository.deleteByEmail(email);
            
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("204", "Successfully deleted"),
                HttpStatus.OK);
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  
    
}
