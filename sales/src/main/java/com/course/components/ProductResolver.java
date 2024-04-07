package com.course.components;

import com.course.dataloader.ProductDataLoader;
import com.course.entity.SalesOrderItemsEntity;
import com.course.sales.generated.DgsConstants;
import com.course.sales.generated.types.SalesOrderItem;
import com.course.sales.generated.types.SimpleModel;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import org.dataloader.DataLoader;

import java.util.concurrent.CompletableFuture;

@DgsComponent
public class ProductResolver {

  @DgsData (
          parentType = DgsConstants.SALESORDERITEM.TYPE_NAME,
          field = DgsConstants.SALESORDERITEM.SimpleModel

  )
    public CompletableFuture<SimpleModel> loadSimpleModels(DgsDataFetchingEnvironment env){
        DataLoader<String,SimpleModel> productDataLoader = env.getDataLoader(ProductDataLoader.class);
        SalesOrderItemsEntity salesOrderItem = env.getSource();

        return productDataLoader.load(String.valueOf (salesOrderItem.getModelUuid()));
    }
}
