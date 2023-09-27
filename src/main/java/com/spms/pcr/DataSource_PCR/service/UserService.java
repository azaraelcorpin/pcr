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
            String email = params.get("email").toString();
            String userName = params.get("userName").toString();
            String userType = params.get("userType").toString();
            int office_id = utilityService.toInteger(params.get("officeId").toString());
            Optional<User> optionalUser = userRepository.findByEmail(email);
            User user = optionalUser.orElse(null);
            if(user != null){
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("412", email+" already exist"),
             HttpStatus.CONFLICT);
            }else{
                user = new User();
                user.setEmail(email);
                user.setUsername(userName);
                user.setUserType(userType);
                user.setOfficeId(office_id);
                user.setStatus(User.STATUS_ACTIVE);
                userRepository.save(user);
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success"),
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
            List<User> list = userRepository.findAll();
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","USERS",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
