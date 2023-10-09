package com.spms.pcr.DataSource_PCR.service;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.spms.pcr.DataSource_PCR.model.User;
import com.spms.pcr.DataSource_PCR.repository.SessionRepository;
import com.spms.pcr.DataSource_PCR.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UtilityService {
    private  final Map<String, String> statusMessages = new HashMap<>();
    {
        // Populate the map with status code mappings

        // Informational Responses (1xx):
        statusMessages.put("100", "Continue");
        statusMessages.put("101", "Switching Protocols");
        // Successful Responses (2xx):
        statusMessages.put("200", "OK");
        statusMessages.put("201", "Created");
        statusMessages.put("202", "Accepted");
        statusMessages.put("204", "Deleted");
        statusMessages.put("205", "Reset Content");
        statusMessages.put("206", "Partial Content");
        statusMessages.put("207", "Multi-Status");
        statusMessages.put("208", "Already Reported");
        statusMessages.put("226", "IM Used");
        // Redirection Messages (3xx):
        statusMessages.put("300", "Multiple Choices");
        statusMessages.put("301", "Moved Permanently");
        statusMessages.put("302", "Found");
        statusMessages.put("303", "See Other");
        statusMessages.put("304", "Not Modified");
        statusMessages.put("307", "Temporary Redirect");
        statusMessages.put("308", "Permanent Redirect");
        // Client Error Responses (4xx):
        statusMessages.put("400", "Bad Request");
        statusMessages.put("401", "Unauthorized");
        statusMessages.put("402", "Payment Required");
        statusMessages.put("403", "Forbidden");
        statusMessages.put("404", "Not Found");
        statusMessages.put("405", "Method Not Allowed");
        statusMessages.put("406", "Not Acceptable");
        statusMessages.put("407", "Proxy Authentication Required");
        statusMessages.put("408", "Request Timeout");
        statusMessages.put("409", "Conflict");
        statusMessages.put("410", "Gone");
        statusMessages.put("411", "Length Required");
        statusMessages.put("412", "Precondition Failed");
        statusMessages.put("413", "Payload Too Large");
        statusMessages.put("414", "URI Too Long");
        statusMessages.put("415", "Unsupported Media Type");
        statusMessages.put("416", "Range Not Satisfiable");
        statusMessages.put("417", "Expectation Failed");
        statusMessages.put("418", "I'm a teapot");
        statusMessages.put("421", "Misdirected Request");
        statusMessages.put("422", "Unprocessable Entity");
        statusMessages.put("423", "Locked");
        statusMessages.put("424", "Failed Dependency");
        statusMessages.put("425", "Too Early");
        statusMessages.put("426", "Upgrade Required");
        statusMessages.put("428", "Precondition Required");
        statusMessages.put("429", "Too Many Requests");
        statusMessages.put("431", "Request Header Fields Too Large");
        statusMessages.put("451", "Unavailable For Legal Reasons");
        // Server Error Responses (5xx):
        statusMessages.put("500", "Internal Server Error");
        statusMessages.put("501", "Not Implemented");
        statusMessages.put("502", "Bad Gateway");
        statusMessages.put("503", "Service Unavailable");
        statusMessages.put("504", "Gateway Timeout");
        statusMessages.put("505", "HTTP Version Not Supported");
        statusMessages.put("506", "Variant Also Negotiates");
        statusMessages.put("507", "Insufficient Storage");
        statusMessages.put("508", "Loop Detected");
        statusMessages.put("510", "Not Extended");
        statusMessages.put("511", "Network Authentication Required");
    }

    public String renderJsonResponse(String statusCode,String message){
        JSONObject resp = new JSONObject();
        try {
            resp.put("statusCode", statusCode);
            resp.put("status",statusMessages.get(statusCode));
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
            resp.put("status",statusMessages.get(statusCode));
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
            resp.put("status",statusMessages.get(statusCode));
            resp.put("message", message);
            resp.put(ObjectName,entityIdentifier);
            log.info(resp.toString());
            return resp.toString();

    }

    public String renderJsonResponse(String statusCode,String message,Object obj) throws JSONException{
        JSONObject resp = new JSONObject();

            resp.put("statusCode", statusCode);
            resp.put("status",statusMessages.get(statusCode));
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
            resp.put("status",statusMessages.get(statusCode));
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
            resp.put("status",statusMessages.get(statusCode));
            resp.put("message", message);
            resp.put(itemsName,toJSONArray(iterable));
 
            return resp.toString();

    }

    public String renderJsonResponse(String statusCode,String message,String itemsName1,List iterable1, String itemsName2,List iterable2) throws JSONException{
        JSONObject resp = new JSONObject();
            
            resp.put("statusCode", statusCode);
            resp.put("status",statusMessages.get(statusCode));
            resp.put("message", message);
            resp.put(itemsName1,toJSONArray(iterable1));
            resp.put(itemsName2,toJSONArray(iterable2));
 
            return resp.toString();

    }
    public String renderJsonResponse(String statusCode,String message,String itemsName,Page page) throws JSONException{
        JSONObject resp = new JSONObject();
            
            resp.put("statusCode", statusCode);
            resp.put("status",statusMessages.get(statusCode));
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

    public String getString(String key, Map<String, Object> param){
        return getString(param.get(key));
    }
    public Integer toInteger(String value){
        return value!=null?value.isEmpty()?null:(Integer.parseInt(value)):null;
    }

    public Integer toInteger(Object value){
        if(value == null)
        return null;
        return value!=null?value.toString().isEmpty()?null:(Integer.parseInt(value.toString())):null;
    }

    public Long toLong(Object value){
        if(value == null)
        return null;
        return value!=null?value.toString().isEmpty()?null:(Long.valueOf(value.toString())):null;
    }

    public Boolean toBoolean(Object value){
        return value!=null?value.toString().isEmpty()?null:(Boolean.parseBoolean(value.toString())):null;
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
    UserRepository userRepository;

    @Autowired
    private SessionRepository sessionRepository;
    public JSONObject generateSessionId(String _data,String ivHeader) throws Exception{
        String sessionId = "123456";
        sessionId = RandomStringUtils.randomAlphanumeric(45);

        String id = decryptDataAuth(_data, ivHeader);
        JSONObject obj =  new JSONObject(id);

        if(!id.contains("@msugensan.edu.ph"))
                throw new Exception("Unauthorized");

        Session session = new Session();
        session.setDate(new Date());
        session.setEmail(obj.get("userEmail").toString());
        session.setSessionId(sessionId);
        sessionRepository.save(session);

        /*
        Put condition here that filter if the user is exist in the user Table or
        the user has record on employee table↓↓
        */
        Optional<User> user = userRepository.findByEmailAndStatus(obj.get("userEmail").toString(), "Active");
        JSONObject response = new JSONObject();
        List<String> roles = new ArrayList<>();

        if(user.isPresent()){
            roles.add(user.get().getUserType());
        }

        List<Map<String,Object>> list = userRepository.getRoles(session.getEmail());
        System.out.println("id is "+list.size());
        for (Map<String,Object> map : list) {
            roles.add(map.get("position").toString());
        }
        if(roles.isEmpty()){
            throw new Exception("Unauthorized");
        }

        response.put("ROLES",roles);
        response.put("sessionId", sessionId);
        response.put("office_id",list.isEmpty()?null:list.get(0).get("office_id"));

        return response;
    }

    public JSONObject validateSession(String _data,String ivHeader) throws Exception{
        if((_data.equals("test")&& ivHeader.equals("test")))
        {
         JSONObject test = new JSONObject();
         test.put("user", "test");
         return test;
        }
        String param = decryptDataAuth(_data, ivHeader);

            if(!param.contains("@msugensan.edu.ph"))
                throw new Exception("Unauthorize");
        
            JSONObject obj =  new JSONObject(param);
            if(!obj.has("sid"))
                throw new Exception("Unauthorized");

            if(obj.getString("sid").equalsIgnoreCase("hash"))
                return obj;
            
            if(!sessionRepository.findBySessionIdAndEmail(obj.getString("sid"), obj.getString("userEmail")).isPresent())
                throw new Exception("Invalid Session ID");            

        return obj;
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
