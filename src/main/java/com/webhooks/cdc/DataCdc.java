package com.webhooks.cdc;

import com.gigya.socialize.GSKeyNotFoundException;
import com.gigya.socialize.GSObject;
import com.gigya.socialize.GSRequest;
import com.gigya.socialize.GSResponse;

public class DataCdc {

     private String apiKey = "3_66LVUn7x41zk1TADj-iHWllMzmk09zEi4Q8ZDthzIZpGG-koMlBFr8J-eDvS69bx";
     private String method = "accounts.getJWTPublicKey";

     public String getData(String param) throws GSKeyNotFoundException {
         GSRequest request = new GSRequest(apiKey, method);
         GSResponse response = request.send();
         if(response.getErrorCode()==0){
             GSObject data =response.getData();
             return data.getString(param);
         }
         return null;
     }
}
