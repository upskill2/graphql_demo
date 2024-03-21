package com.course.product.util;

import com.course.product.entity.ManufacturersEntity;
import com.course.product.entity.ModelsEntity;
import com.course.product.entity.SeriesEntity;
import com.course.product.generated.types.Manufacturer;
import com.course.product.generated.types.Model;
import com.course.product.generated.types.Series;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface DomainMapper {

    Manufacturer toManufacturer (ManufacturersEntity manufacturersEntity);

    default Series toSeries (SeriesEntity seriesEntity) {
        return Series.newBuilder ()
                .name (seriesEntity.getName ())
                .uuid (seriesEntity.getId ().toString ())
                .manufacturer (toManufacturer (seriesEntity.getManufacturerId ()))
                .build ();

    }
    Model toModels (ModelsEntity modelsEntity);
}
