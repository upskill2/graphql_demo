package com.course.graphql.component.fake;

import com.netflix.graphql.dgs.DgsQueryExecutor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FakeHelloDataResolverTest {

    @Autowired
    private DgsQueryExecutor queryExecutor;

    @Test
    void testOneHello () {

        var graphQLQuery = """
                {
                hello {
                           message
                           number
                    
                       }
                }
                                
                """;

        String message = queryExecutor.executeAndExtractJsonPath (graphQLQuery, "data.hello.message");
        assertFalse (message.isEmpty ());
        Integer number = queryExecutor.executeAndExtractJsonPath (graphQLQuery, "data.hello.number");
        assertNotNull (number);
    }

    @Test
    void testAllHellos () {

        var graphQLQuery = """
                {
                allHellos {
                           message
                           number
                    
                       }
                }
                                
                """;

        List<String> messageList = queryExecutor.executeAndExtractJsonPath (graphQLQuery, "data.allHellos[*].message");
        assertEquals (messageList.size (), 20);
        List<Integer> numberList = queryExecutor.executeAndExtractJsonPath (graphQLQuery, "data.allHellos[*].number");
        assertEquals (numberList.size (), 20);
    }

}