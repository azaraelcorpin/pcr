package com.spms.pcr.DataSource_PCR.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.spms.pcr.DataSource_PCR.model.Office;
import com.spms.pcr.DataSource_PCR.model.OfficeEmployee;
import com.spms.pcr.DataSource_PCR.model.User;
import com.spms.pcr.DataSource_PCR.repository.OfficeEmployeeRepository;
import com.spms.pcr.DataSource_PCR.repository.OfficeRepository;
import com.spms.pcr.DataSource_PCR.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class OfficeService {
    @Autowired
    private OfficeRepository officeRepository;

    @Autowired
    private UtilityService utilityService;


    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> newOffice(@RequestBody Map<String,Object> params){
        try{
            String code = utilityService.getString("code", params);
            String description = utilityService.getString("description", params);            
            Long top_office_id = utilityService.toLong(params.get("topOfficeId"));
            Boolean is_sector = utilityService.toBoolean(params.get("is_sector"));
            Optional<Office> optionalOffice = officeRepository.findByCode(code);
            Office office = optionalOffice.orElse(null);
            if(office != null){
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("409", code+" already exist"),
             HttpStatus.CONFLICT);
            }else{
                office = new Office();
                office.setCode(code);
                office.setDescription(description);
                office.setIsSector(is_sector);
                office.setTopOffice(top_office_id);
                officeRepository.save(office);
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("201", "New Office Created"),
                HttpStatus.OK);
            }
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> updateOffice(@RequestBody Map<String,Object> params){
        try{
            Long id = utilityService.toLong(params.get("id"));
            String code = utilityService.getString("code", params);
            String description = utilityService.getString("description", params);            
            Long top_office_id = utilityService.toLong(params.get("topOfficeId"));
            Boolean is_sector = utilityService.toBoolean(params.get("is_sector"));
            Optional<Office> optionalOffice = officeRepository.findByCode(code);
            Office office = optionalOffice.orElse(null);
            if(office != null ){
                if(office.getId() != id)
                    return new ResponseEntity<Object>(utilityService.renderJsonResponse("409", code+" already exist"),
                        HttpStatus.CONFLICT);
            }
                office = new Office();
                office.setId(id);
                office.setCode(code);
                office.setDescription(description);
                office.setIsSector(is_sector);
                office.setTopOffice(top_office_id);
                officeRepository.save(office);
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("202", "Successfully updated"),
                HttpStatus.OK);
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> getAllOffice(){
        try{
            List<Map<String,Object>> list = officeRepository.getAll();
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","OFFICES",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Autowired 
    private OfficeEmployeeRepository officeEmployeeRepository;
    @Autowired
    private UserRepository userRepository;
    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> deleteOffice(@RequestBody Map<String,Object> params){
        try{
            Long id = utilityService.toLong(params.get("id"));
            Optional <OfficeEmployee> office = officeEmployeeRepository.findTopByOfficeId(id);
            if(office.isPresent()){
                throw new Exception("Cannot delete. Office has current Employee. Please Check Office Management");
            }
            Optional <User> user = userRepository.findToByOfficeId(id);
            if(user.isPresent()){
                throw new Exception("Cannot delete. Office has current Staff. Please Check Office Management");
            }            
                officeRepository.deleteById(id);
            
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("204", "Successfully deleted"),
                HttpStatus.OK);
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    } 

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> getOfficeData(@RequestBody Map<String,Object> params){
        try{
            Long id = utilityService.toLong(params.get("id"));
            Map<String, Object> optional_office = officeRepository.getOfficeInfo(id);
            System.out.println(optional_office.isEmpty());
            if(optional_office.isEmpty())
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("404", "Office ID not Found!"),HttpStatus.INTERNAL_SERVER_ERROR);
            
            List<Map<String,Object>> list = officeEmployeeRepository.getEmployeesByOffice(id);            
            JSONObject office = new JSONObject();
            
            office.put("info", utilityService.toJSONObject( optional_office));
            office.put("employees", utilityService.toJSONArray(list));
            
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","OFFICE",office),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
