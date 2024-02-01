package com.spms.pcr.DataSource_PCR.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import com.spms.pcr.DataSource_PCR.model.PcrSchedule;
import com.spms.pcr.DataSource_PCR.repository.PcrScheduleRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PcrScheduleService {
    @Autowired
    private PcrScheduleRepository pcrScheduleRepository;

    @Autowired
    private UtilityService utilityService;


    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> newSchedule(@RequestBody Map<String,Object> params){
        try{
            Date date_start = utilityService.getDate("dateStart", params);
            Date date_end = utilityService.getDate("dateEnd", params);
            PcrSchedule schedule = new PcrSchedule();
            schedule.setDateStart(date_start);
            schedule.setDateEnd(date_end);
            schedule.setStatus(PcrSchedule.STATUS_INACTIVE);
            pcrScheduleRepository.save(schedule);
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("201", "New Schedule Created"),
                HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> updateSchedule(@RequestBody Map<String,Object> params){
        try{
            Long id = utilityService.toLong(params.get("id"));
            Date date_start = utilityService.getDate("dateStart", params);
            Date date_end = utilityService.getDate("dateEnd", params);
            String status = utilityService.getString("status", params);
            PcrSchedule schedule = new PcrSchedule();
            schedule.setId(id);
            schedule.setDateStart(date_start);
            schedule.setDateEnd(date_end);
            schedule.setStatus(status);
            pcrScheduleRepository.save(schedule);
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("202", "Successfully Updated"),
                HttpStatus.OK);
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }    

    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> getAllSchedule(){
        try{
            List<PcrSchedule> list = pcrScheduleRepository.findAll();
        return new ResponseEntity<Object>(utilityService.renderJsonResponse("200", "Success","SCHEDULES",list),
            HttpStatus.OK);

        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Transactional("PcrTransactionManager")
    public ResponseEntity<Object> deleteSched(@RequestBody Map<String,Object> params){
        try{
            Long id = utilityService.toLong(params.get("id"));

                pcrScheduleRepository.deleteById(id);
            
                return new ResponseEntity<Object>(utilityService.renderJsonResponse("204", "Successfully deleted"),
                HttpStatus.OK);
            
        }catch(Exception e){
            log.error(e.getMessage());
            return new ResponseEntity<Object>(utilityService.renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }  
}
