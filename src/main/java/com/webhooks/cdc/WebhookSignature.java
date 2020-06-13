package com.webhooks.cdc;

import com.gigya.socialize.GSKeyNotFoundException;

import java.math.BigInteger;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;

public class WebhookSignature {
    private String token;
    private DataCdc data;
    public WebhookSignature(String token){
        this.token=token;
        this.data = new DataCdc();
    }

    public boolean validateSignature() throws GSKeyNotFoundException {
        boolean result = false;
        String nString = data.getData("n");
        String expString = data.getData("e");
        String[] splitToken = token.split("\\.");
        String headertoken = splitToken[0];
        String payload = splitToken[1];
        String signature = splitToken[2];
        String tokenData = headertoken + "." + payload;
        signature = signature.replace('-', '+');
        signature = signature.replace('_', '/');
        byte[] keySignature = Base64.getDecoder().decode(signature.getBytes());
        nString = nString.replace('-', '+');
        nString = nString.replace('_', '/');
        byte[] n = Base64.getDecoder().decode(nString.getBytes());
        byte[] e = Base64.getDecoder().decode(expString.getBytes());
        try{
            BigInteger nBigInt = new BigInteger(1, n);
            BigInteger eBigInt = new BigInteger(1, e);
            RSAPublicKeySpec rsaPubKey = new RSAPublicKeySpec(nBigInt, eBigInt);
            KeyFactory fact = KeyFactory.getInstance("RSA");
            PublicKey rsa = fact.generatePublic(rsaPubKey);
            java.security.Signature rsaSig = java.security.Signature.getInstance("SHA256withRSA");
            rsaSig.initVerify(rsa);
            rsaSig.update(tokenData.getBytes("UTF-8"));
            result =  rsaSig.verify(keySignature);
        }
        catch (Exception exception) {
            System.out.println(exception.getMessage());
        }
        return result;
    }

}
