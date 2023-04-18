package com.shop.config;

import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Configuration
@PropertySource("classpath:application.properties")
public class JasyptAdvancedConfig {
    @Value("${encryption.password.path}")
    private String encryptionPath;

    @Bean(name = "jasyptStringEncryptor")
    public StringEncryptor getPasswordEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();
        String encPassword = "";
        try {
            encPassword = readEncryptionPasswordFromFile();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        config.setPassword(encPassword);
        config.setAlgorithm("PBEWithMD5AndDES");
        config.setKeyObtentionIterations("1000");
        config.setPoolSize("1");
        config.setProviderName("SunJCE");
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setStringOutputType("base64");

        encryptor.setConfig(config);
        return encryptor;
    }

    private String readEncryptionPasswordFromFile() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(encryptionPath));
        String line = reader.readLine();
        reader.close();
        return line;
    }
}
