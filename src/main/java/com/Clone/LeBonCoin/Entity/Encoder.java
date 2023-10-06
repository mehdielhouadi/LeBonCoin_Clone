package com.Clone.LeBonCoin.Entity;

import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class Encoder {
    private BinaryEncoder encoder;
    @Autowired
    public Encoder(BinaryEncoder binaryEncoder) {
        this.encoder = binaryEncoder;
    }
    public BinaryEncoder getEncoder() {
        return encoder;
    }
    public void setEncoder(BinaryEncoder encoder) {
        this.encoder = encoder;
    }
}
