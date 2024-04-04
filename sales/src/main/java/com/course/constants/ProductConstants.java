package com.course.constants;

import lombok.NoArgsConstructor;

@NoArgsConstructor (access = lombok.AccessLevel.PRIVATE)
public class ProductConstants {

    public static String prepareQuery (String modelUuid) {

        return String.format (

                """
                                query FindSimpleModel {
                                    findSimpleModel(
                                        modelInput: { modelUuid: "%s" }
                                    ) {
                                        uuid
                                        name
                                        onTheRoadPrice
                                        transmission
                                        exteriorColor
                                        interiorColor
                                        releaseYear
                                        bodyType
                                        fuel
                                    }
                                }
                        """, modelUuid);

    }
    public static final String OPERATION_NAME_SIMPLE_MODELS = "FindSimpleModel";
    public static final String VARIABLE_NAME_MODEL_UUID = "modelUuid";

}
