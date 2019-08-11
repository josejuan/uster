package com.uster.server.rest;

import com.uster.business.Uster;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class UsterService {

    Uster uster;

    @Autowired
    private Config config;

    @PostConstruct
    public void initialize() {
        uster = config.makeUster();
    }
}
