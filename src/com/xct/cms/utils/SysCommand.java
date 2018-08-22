package com.xct.cms.utils;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.util.Vector;

public class SysCommand {
	Process p; 
    public Vector execute(String cmd) 
    { 
      try 
      { 
        Start(cmd); 
        Vector vResult=new Vector(); 
        DataInputStream in=new DataInputStream(p.getInputStream()); 
        BufferedReader myReader=new BufferedReader(new InputStreamReader(in)); 
        String line; 
        do 
        { 
          line=myReader.readLine(); 
          if(line==null) 
          { 
            break; 
          } 
          else 
          { 
            vResult.addElement(line); 
          } 
        }while(true); 
        myReader.close(); 
        return vResult; 
      } 
      catch(Exception e) 
      { 
        return null; 
          
      } 
    
    } 
    public void Start(String cmd) 
    { 
      try 
      { 
        if(p!=null) 
        { 
          kill(); 
        } 
        Runtime sys=Runtime.getRuntime(); 
        p=sys.exec(cmd); 
          
      } 
      catch(Exception e) 
      { 
          
      } 
    } 
    public void kill() 
    { 
      if(p!=null) 
      { 
        p.destroy (); 
        p=null; 
      } 
    } 

}
