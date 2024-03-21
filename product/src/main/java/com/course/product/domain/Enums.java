package com.course.product.domain;

public class Enums {
    public enum Transmission {

        AUTOMATIC,
        MANUAL
    }

    public enum BodyType {
        SEDAN,
        SUV,
        MPV,
        PICKUP,
        CROSSOVER
    }

    public enum Fuel {
        DIESEL,
        GAS,
        ELECTRIC,
        HYBRID
    }

    public enum NumericComparison {
        GREATER_THAN_EQUALS,
        LESS_THAN_EQUALS,
                BETWEEN_INCLUSIVE
        }

  public  enum SortDirection {
        ASCENDING,
        DESCENDING
        }

}
