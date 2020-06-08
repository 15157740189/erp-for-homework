package cn.edu.hziee.springbootinterface.Config;

import cn.edu.hziee.springbootinterface.Util.AutoIdempotent;
import cn.edu.hziee.springbootinterface.Util.AutoIdempotentInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;
import java.util.ArrayList;

@Configuration
public class WebConfiguration extends WebMvcConfigurerAdapter {
    @Resource
    private AutoIdempotentInterceptor autoIdempotentInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(autoIdempotentInterceptor);
        super.addInterceptors(registry);
    }
}
