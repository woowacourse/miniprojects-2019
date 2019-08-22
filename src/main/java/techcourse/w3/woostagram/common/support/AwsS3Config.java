package techcourse.w3.woostagram.common.support;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Config {
    @Bean
    public AmazonS3 awsS3Client(AwsS3Properties awsS3Properties) {
        return AmazonS3ClientBuilder
                .standard()
                .withRegion(Regions.fromName(awsS3Properties.getRegion()))
                .build();
    }
}
