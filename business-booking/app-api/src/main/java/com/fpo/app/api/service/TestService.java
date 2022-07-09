package com.fpo.app.api.service;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
public class TestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String testMethod(String page, String perPage) throws Exception {
        Integer pageInt = Integer.parseInt(page) - 1;
        Integer perPageInt = Integer.parseInt(perPage);
        Integer offset = pageInt*perPageInt;
        ObjectMapper mapper = new ObjectMapper();
        String sql = "SELECT * FROM Customers ORDER BY username OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
        String returnObj = mapper.writeValueAsString(jdbcTemplate.queryForList(sql, new Object[] {offset, perPageInt}));
        return returnObj;
    }

    public String hashedPassword(String password, String password2){
        String pword = passwordEncoder.encode(password);
        Boolean bool = passwordEncoder.matches((CharSequence) password2, pword);
        return "Hashed password: " + pword + " | Matches: " + bool;
    }

    public String crackPass(String str){
        String[] easyPassCrack = new String[]{"password", "Password", "123Pass", "Password123", "123pass", ""};
        for(int i = 0; i < easyPassCrack.length; i++){
            Boolean match = passwordEncoder.matches((CharSequence) easyPassCrack[i], str);
            if(match == true){
                return "The password matches " + easyPassCrack[i];
            }
        }
        return "no matches found";
    }

}
