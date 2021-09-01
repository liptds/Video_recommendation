package com.myproject.jupiter.recommendation;

public class RecommendationException extends RuntimeException{
    public RecommendationException(String errorMessage) {
        super(errorMessage);
    }
}
