package com.spms.pcr.DataSource_PCR.service;

import java.util.Base64;
import java.util.Date;
import java.util.List;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.commons.lang3.RandomStringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import com.google.gson.Gson;
import com.spms.pcr.DataSource_PCR.model.Session;
import com.spms.pcr.DataSource_PCR.repository.SessionRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UtilityService {

    public String renderJsonResponse(String statusCode,String message){
        JSONObject resp = new JSONObject();
        try {
            resp.put("statusCode", statusCode);
            resp.put("message", message);
            return resp.toString();
        } catch (JSONException e) {                       
           return null;
        }
    }

    public String renderJsonResponse(String statusCode,String message,String identifier, JSONObject object){
        JSONObject resp = new JSONObject();
        try {
            resp.put("statusCode", statusCode);
            resp.put("message", message);
            resp.put(identifier, object);
            return resp.toString();
        } catch (JSONException e) {                       
           return null;
        }
    }


    public String renderJsonResponse(String statusCode,String message,String ObjectName,String entityIdentifier) throws JSONException{
        JSONObject resp = new JSONObject();

            resp.put("statusCode", statusCode);
            resp.put("message", message);
            resp.put(ObjectName,entityIdentifier);
            log.info(resp.toString());
            return resp.toString();

    }

    public String renderJsonResponse(String statusCode,String message,Object obj) throws JSONException{
        JSONObject resp = new JSONObject();

            resp.put("statusCode", statusCode);
            resp.put("message", message);
            if(obj == null)
                resp.put("Object","null value");
            else
                resp.put(obj.getClass().getSimpleName(),new JSONObject(new Gson().toJson(obj)));
            log.info(resp.toString());
            return resp.toString();

    }

    public String renderJsonResponse(String statusCode,String message,String Identifier,Object obj) throws JSONException{
        JSONObject resp = new JSONObject();

            resp.put("statusCode", statusCode);
            resp.put("message", message);
            if(obj == null)
                resp.put("Object","null value");
            else
                resp.put(Identifier,new JSONObject(new Gson().toJson(obj)));
            log.info(resp.toString());
            return resp.toString();

    }

    public String renderJsonResponse(String statusCode,String message,String itemsName,List iterable) throws JSONException{
        JSONObject resp = new JSONObject();
            
            resp.put("statusCode", statusCode);
            resp.put("message", message);
            resp.put(itemsName,toJSONArray(iterable));
 
            return resp.toString();

    }

    public String renderJsonResponse(String statusCode,String message,String itemsName1,List iterable1, String itemsName2,List iterable2) throws JSONException{
        JSONObject resp = new JSONObject();
            
            resp.put("statusCode", statusCode);
            resp.put("message", message);
            resp.put(itemsName1,toJSONArray(iterable1));
            resp.put(itemsName2,toJSONArray(iterable2));
 
            return resp.toString();

    }
    public String renderJsonResponse(String statusCode,String message,String itemsName,Page page) throws JSONException{
        JSONObject resp = new JSONObject();
            
            resp.put("statusCode", statusCode);
            resp.put("message", message);
            resp.put(itemsName,toJSONArray(page.toList()));
            resp.put("totalPages",page.getTotalPages());
            resp.put("numberOfElements",page.getNumberOfElements());
            resp.put("totalElements",page.getTotalElements());

            return resp.toString();

    }
    public JSONArray toJSONArray(List iterable) throws JSONException{
        JSONArray list = new JSONArray();
        for (Object o : iterable) {
               try{
                list.put(new JSONObject(new Gson().toJson(o)));
               }
               catch(Exception e){
                list.put(o);
               }
        }
        return list;
    }

    public JSONObject toJSONObject(Object obj) throws JSONException{
        return new JSONObject(new Gson().toJson(obj));
    } 
    
    public String getString(Object value){
        return value!=null?value.toString().replace(" ", "").isEmpty()?null:value.toString():null;
    }

    public Integer toInteger(String value){
        return value!=null?value.isEmpty()?null:(Integer.parseInt(value)):null;
    }

    public Integer toInteger(Object value){
        return value!=null?value.toString().isEmpty()?null:(Integer.parseInt(value.toString())):null;
    }

    public Long toLong(Object value){
        return value!=null?value.toString().isEmpty()?null:(Long.valueOf(value.toString())):null;
    }

    @Value("${APP_PCR_KEY}")
    private String key;

    public String decryptDataAuth(String _data,String ivHeader) throws Exception{
        String encryptedData = _data.replace("Bearer ", "");
        String keyString = key;
        
        byte[] keyBytes = keyString.getBytes();
        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
        byte[] ivBytes = Base64.getDecoder().decode(ivHeader);
        IvParameterSpec ivSpec = new IvParameterSpec(ivBytes);

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

        byte[] encryptedBytes = Base64.getDecoder().decode(encryptedData);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        String decryptedData = new String(decryptedBytes);

        return (decryptedData);
    }

    @Autowired
    private SessionRepository sessionRepository;
    public String generateSessionId(String _data,String ivHeader) throws Exception{
        String sessionId = "123456";
        sessionId = RandomStringUtils.randomAlphanumeric(45);

        String id = decryptDataAuth(_data, ivHeader);
        JSONObject obj =  new JSONObject(id);

        Session session = new Session();
        session.setDate(new Date());
        session.setEmail(obj.get("email").toString());
        session.setSessionId(sessionId);
        sessionRepository.save(session);

        return sessionId;
    }

    public Date getDate(Object value){
        if(value == null)
            return null;
        String dateString = value.toString();
        if(dateString.isEmpty())
            return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date;
        try {
            date = dateFormat.parse(dateString);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            log.error(dateString, e);
            return null;
        }
        return date;
    }

    // @Autowired
    // private RegistrationRepository generalRepo;

    // @Transactional("TerTransactionManager")
    // public ResponseEntity<Object> TestCOR(){
    //     try{
                        
    //         List<Map<String,Object>> items = generalRepo.TestCOR();
    //         ArrayList<Registration> regList = new ArrayList<>();
    //         JSONArray list = toJSONArray(items);
    //         for (int i = 0; i<list.length();i++) {
    //             JSONObject o = list.getJSONObject(i);
    //             Registration registration = new Gson().fromJson(o.toString(), Registration.class);
    //             regList.add(registration);
    //         }
    //         for (Registration registration : regList) {
    //             System.out.println(registration.getId());
    //             System.out.println(registration.getFacultyid());
    //         }
    //         // for (Registration registration : items) {
    //         //     registration.setId(null);
    //         // }
    //         return new ResponseEntity<Object>(renderJsonResponse("200", "Success","leaveApplications",items),
    //         HttpStatus.OK);

    //     }catch(Exception e){
    //         log.error(e.getMessage());
    //         return new ResponseEntity<Object>(renderJsonResponse("500", e.getMessage()),HttpStatus.INTERNAL_SERVER_ERROR);
    //     }
    // }
}
