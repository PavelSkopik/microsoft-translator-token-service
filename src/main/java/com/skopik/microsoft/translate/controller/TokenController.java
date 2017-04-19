package com.skopik.microsoft.translate.controller;

import com.skopik.microsoft.translate.dao.TokenDao;
import com.skopik.microsoft.translate.model.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "api/translator/ms/token", produces = MediaType.APPLICATION_JSON_VALUE)
public class TokenController {

    @Autowired
    private TokenDao tokenDao;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Token> getToken(){
        return new ResponseEntity<>(tokenDao.getToken(), HttpStatus.OK);
    }

}
