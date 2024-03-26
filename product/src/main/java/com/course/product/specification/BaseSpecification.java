package com.course.product.specification;

import java.text.MessageFormat;
import java.util.List;


public abstract class BaseSpecification {

    protected static String contains (String keyword) {
        return MessageFormat.format ("%{0}%", keyword);
    }

    protected static String in (List<String> keywords){
        return MessageFormat.format ("%{0}%", keywords);
    }
}
