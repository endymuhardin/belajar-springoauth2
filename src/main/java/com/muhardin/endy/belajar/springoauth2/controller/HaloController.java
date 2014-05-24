package com.muhardin.endy.belajar.springoauth2.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController @RequestMapping("/api")
public class HaloController {
    
    @RequestMapping("/halo")
    public Map<String, Object> halo(){
        Map<String, Object> hasil = new HashMap<String, Object>();
        hasil.put("sukses", Boolean.TRUE);
        hasil.put("page", "halo");
        return hasil;
    }
    
    @RequestMapping("/admin")
    public Map<String, Object> admin(){
        Map<String, Object> hasil = new HashMap<String, Object>();
        hasil.put("sukses", Boolean.TRUE);
        hasil.put("page", "admin");
        return hasil;
    }
    
    @RequestMapping("/staff")
    public Map<String, Object> staff(){
        Map<String, Object> hasil = new HashMap<String, Object>();
        hasil.put("sukses", Boolean.TRUE);
        hasil.put("page", "staff");
        return hasil;
    }
}
