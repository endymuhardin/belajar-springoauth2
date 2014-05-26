package com.muhardin.endy.belajar.springoauth2.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
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
    public Map<String, Object> admin(@AuthenticationPrincipal User user){
        Map<String, Object> hasil = new HashMap<String, Object>();
        hasil.put("sukses", Boolean.TRUE);
        hasil.put("page", "admin");
        hasil.put("user", user.getUsername());
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
