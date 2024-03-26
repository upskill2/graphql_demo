package com.course.product.util;

import com.course.product.entity.CharacteristicsEntity;
import com.course.product.entity.ManufacturersEntity;
import com.course.product.entity.ModelsEntity;
import com.course.product.entity.SeriesEntity;
import com.course.product.generated.types.Manufacturer;
import com.course.product.generated.types.Model;
import com.course.product.generated.types.Series;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface DomainMapper {

    default Manufacturer toManufacturer (ManufacturersEntity manufacturersEntity) {
        return Manufacturer.newBuilder ()
                .name (manufacturersEntity.getName ())
                .uuid (manufacturersEntity.getUuid ().toString ())
                .originCountry (manufacturersEntity.getOriginCountry ())
                .series (manufacturersEntity.getSeries ().stream ().map (this::manufacturerToSeries).toList ())
                .description (manufacturersEntity.getDescription ())
                .build ();
    }

    default Series toSeries (SeriesEntity seriesEntity) {
        return Series.newBuilder ()
                .name (seriesEntity.getName ())
                .uuid (seriesEntity.getUuid ().toString ())
                .manufacturer (toManufacturer (seriesEntity.getManufacturerId ()))
                .models (seriesEntity.getModels ().stream ().map (this::toModels).toList ())
                .characteristics (seriesEntity.getCharacteristics ()
               .stream ().map (CharacteristicsEntity::getName).toList ())
                .build ();
    }

    default Series manufacturerToSeries (SeriesEntity seriesEntity) {
        return Series.newBuilder ()
                .name (seriesEntity.getName ())
                .uuid (seriesEntity.getUuid ().toString ())
                .models (seriesEntity.getModels ().stream ().map (this::toModels).toList ())
                .build ();

    }

   default Model toModels (ModelsEntity modelsEntity){

        return Model.newBuilder ()
                .name (modelsEntity.getName ())
                .uuid (modelsEntity.getUuid ().toString ())
                .airbags (modelsEntity.getAirbags ())
                .releaseYear (modelsEntity.getReleaseYear ())
                .onTheRoadPrice ((int) modelsEntity.getOnTheRoadPrice ())
                .exteriorColor (modelsEntity.getExteriorColor ())
                .interiorColor (modelsEntity.getInteriorColor ())
                .build ();
   }
}
