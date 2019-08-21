package com.woowacourse.sunbook.support;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.modelmapper.config.Configuration.AccessLevel.PRIVATE;

@Configuration
public class ModelConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setFieldAccessLevel(PRIVATE)
                .setFieldMatchingEnabled(true);

        return modelMapper;
    }

//    @Bean
//    AmazonS3 amazonS3() {
//        return  AmazonS3ClientBuilder.standard()
//                .withRegion(Regions.AP_NORTHEAST_2)
//                .build();
//    }
}
