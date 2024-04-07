package com.course.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import static com.course.constants.DataLoaderConstant.THREAD_POOL_EXECUTOR_NAME;

@Configuration
public class DataLoaderConfig {

    @Bean(name = THREAD_POOL_EXECUTOR_NAME)
    Executor dataLoaderThreadPoolExecutor() {
        return Executors.newCachedThreadPool ();
    }

}
