package com.course.service;

import com.course.constants.ProductConstants;
import com.course.mapper.SalesMapper;
import com.course.rest.SimpleModelClient;
import com.course.sales.generated.types.SimpleModel;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.graphql.dgs.client.GraphQLResponse;
import graphql.com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
public class ProductService {

    @Autowired
    private SalesMapper salesMapper;
    @Autowired
    private SimpleModelClient simpleModelClient;

    @Autowired
    private ObjectMapper objectMapper;

    public Map<String, SimpleModel> loadSimpleModel (String modelUUIDs) {


        Map<String, Set<String>> variableMap = Map.ofEntries (
                Map.entry (ProductConstants.VARIABLE_NAME_MODEL_UUID, Set.of(modelUUIDs))
        );

        final GraphQLResponse graphQLResponse = simpleModelClient.fetchGraphQLResponse (
                ProductConstants.prepareQuery(modelUUIDs),
                ProductConstants.OPERATION_NAME_SIMPLE_MODELS,
                variableMap
        );

        try {
            final JsonNode jsonNode = objectMapper.readTree (graphQLResponse.getJson ());
            final String simpleModelJson = jsonNode.get ("data").get ("findSimpleModel").toString ();
            final List<SimpleModel> simpleModels = objectMapper.readValue (simpleModelJson,
                    new TypeReference<List<SimpleModel>> () {
                    });
            return Maps.uniqueIndex (simpleModels, SimpleModel::getUuid);
        } catch (JsonProcessingException e) {
            return Collections.EMPTY_MAP;
        }


    }
}
