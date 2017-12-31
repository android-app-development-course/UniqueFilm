package com.example.lyz.uniquefilm.Analysis;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by lyz on 17-12-28.
 */

public class StreamUtils {

    public static String getString(InputStream in){
        StringBuffer sb=new StringBuffer();
        try {
            byte[] b=new byte[1024];
            int len=0;
            while((len=in.read(b))!=-1){
                sb.append(new String(b,0,len));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }


    public static String getDecodeString(InputStream in, String decode){
        InputStreamReader inputStreamReader=null;
        BufferedReader bufferedReader=null;
        StringBuffer sb=new StringBuffer();
        try{
            inputStreamReader=new InputStreamReader(in,decode);
            bufferedReader=new BufferedReader(inputStreamReader);
            String str;
            while((str=bufferedReader.readLine())!=null){
                sb.append(str);
            }
            return sb.toString();
        }catch (IOException e){
            e.printStackTrace();
        }
        return sb.toString();
    }
}
