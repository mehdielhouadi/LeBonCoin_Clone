package com.Clone.LeBonCoin.Entity;

import org.apache.commons.codec.BinaryEncoder;
import org.apache.commons.codec.binary.Base32;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class EncoderConfig {
    @Bean
    @Primary
    public BinaryEncoder base64Encoder() {
        return new Base64();
    }
    @Bean
    public BinaryEncoder base32Encoder() {
        return new Base32();
    }
}
