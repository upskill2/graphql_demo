package com.course.dataloader;

import com.course.sales.generated.types.SimpleModel;
import com.course.service.ProductService;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.MappedBatchLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

import static com.course.constants.DataLoaderConstant.THREAD_POOL_EXECUTOR_NAME;

@DgsDataLoader (name = "productDataLoader")
public class ProductDataLoader implements MappedBatchLoader<String, SimpleModel> {

    @Autowired
    private ProductService productService;

    @Autowired
    @Qualifier (THREAD_POOL_EXECUTOR_NAME)
    private Executor dataLoaderThreadPoolExecutor;

    @Value ("${use.RestTemplate}")
    private boolean useRestTemplate;

    @Override
    public CompletionStage<Map<String, SimpleModel>> load (final Set<String> keys) {

        if (useRestTemplate) {
            return CompletableFuture.supplyAsync (
                    () -> productService.loadSimpleModelRest (keys), dataLoaderThreadPoolExecutor);
        } else
            return CompletableFuture.supplyAsync (
                    () -> productService.loadSimpleModel (keys), dataLoaderThreadPoolExecutor);
    }
}
