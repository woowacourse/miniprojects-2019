package techcourse.w3.woostagram.common.support;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "spring.aws")
@Getter
@Setter
public class AwsS3Properties {
    private String region;
    private String bucket;
    private String url;
}
