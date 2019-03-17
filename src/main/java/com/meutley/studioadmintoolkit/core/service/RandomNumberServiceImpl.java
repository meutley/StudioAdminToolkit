package com.meutley.studioadmintoolkit.core.service;

import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class RandomNumberServiceImpl implements RandomNumberService {

    @Override
    public int generate(int min, int max) {
        Random random = new Random();
        return random.nextInt(max) + min;
    }

}