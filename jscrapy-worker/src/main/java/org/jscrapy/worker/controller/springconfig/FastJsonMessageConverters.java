package org.jscrapy.worker.controller.springconfig;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by cxu on 2015/10/31.
 */
@Configuration
public class FastJsonMessageConverters {
    @Bean
    public FastJsonHttpMessageConverter mappingJackson2HttpMessageConverter() {
        FastJsonHttpMessageConverter jsonConverter = new FastJsonHttpMessageConverter();
        jsonConverter.setFeatures(SerializerFeature.PrettyFormat);
        return jsonConverter;
    }
}
