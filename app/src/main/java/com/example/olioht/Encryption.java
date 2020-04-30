package com.example.olioht;

import java.security.*;

public class Encryption {

    public static String getSHA512(String toHash, String salt)
    {
        for (int i = 0; i < 100000; i++)
        {
            toHash = SHA512once(toHash+salt);
        }
        return SHA512once(toHash);
    }

    private static String SHA512once(String toHash)
    {

        MessageDigest md;
        String message = toHash;
        try
        {
            md= MessageDigest.getInstance("SHA-512");

            md.update(message.getBytes());
            byte[] mb = md.digest();
            String out = "";
            for (int i = 0; i < mb.length; i++)
            {
                byte temp = mb[i];
                String s = Integer.toHexString(new Byte(temp));
                while (s.length() < 2)
                {
                    s = "0" + s;
                }
                s = s.substring(s.length() - 2);
                out += s;
            }
            return(out);

        } catch (Exception e)
        {
            System.out.println("ERROR: SHA512 failing");
        }
        return "error";
    }

}
