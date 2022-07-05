package cc.cc1234.dao.config;

import cc.cc1234.dao.plugin.CreateAndUpdateTimeInjectPlugin;
import org.apache.ibatis.plugin.Interceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisPluginConfig {

//    @Bean
//    public Interceptor createAndUpdateTimeInterceptor() {
//        return new CreateAndUpdateTimeInjectPlugin();
//    }
}
