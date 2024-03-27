package com.course.product.specification;

import com.course.product.generated.types.SortDirection;
import com.course.product.generated.types.SortInput;
import org.springframework.data.domain.Sort;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public abstract class BaseSpecification {

    protected static String contains (String keyword) {
        return MessageFormat.format ("%{0}%", keyword);
    }

    public static List<Sort.Order> sortOrdersFrom (List<SortInput> sorts) {

        if (sorts.isEmpty ()) {
            return Collections.emptyList ();
        }
        List<Sort.Order> order = new ArrayList<> ();

        for (SortInput sort : sorts) {
            Sort.Order sortOrder = sort.getDirection () == SortDirection.ASCENDING
                    ? Sort.Order.asc (sort.getField ()) : Sort.Order.desc (sort.getField ());
            order.add (sortOrder);
        }
        return order;
    }
}
