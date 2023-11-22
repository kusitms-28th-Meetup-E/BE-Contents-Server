package gwangjang.server.global.config;

import gwangjang.server.domain.contents.application.mapper.ContentsMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ContentsMapper contentsMapper() {
        return ContentsMapper.INSTANCE;
    }
}
