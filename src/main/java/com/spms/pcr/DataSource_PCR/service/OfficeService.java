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

import com.spms.pcr.DataSource_PCR.model.Office;
import com.spms.pcr.DataSource_PCR.repository.OfficeRepository;
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
            Long top_office_id = utilityService.toLong(params.get("officeId"));
            Boolean is_sector = utilityService.toBoolean(params.get("is_sector"));
            Optional<Office> optionalOffice = officeRepository.findByCode(code);
            Office office = optionalOffice.orElse(null);
            if(office != null){
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("412", code+" already exist"),
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
            Long top_office_id = utilityService.toLong(params.get("officeId"));
            Boolean is_sector = utilityService.toBoolean(params.get("is_sector"));
            Optional<Office> optionalOffice = officeRepository.findByCode(code);
            Office office = optionalOffice.orElse(null);
            if(office != null ){
                if(office.getId() != id)
                    return new ResponseEntity<Object>(utilityService.renderJsonResponse("412", code+" already exist"),
                        HttpStatus.CONFLICT);
            }
                office = new Office();
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
            List<Office> list = officeRepository.findAll();
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","OFFICES",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

  
}
