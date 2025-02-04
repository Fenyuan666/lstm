package com.lstm.api.domain;

import lombok.Data;

import java.util.List;

@Data
public class PredictionResponse {
    private List<Double> predictions;
}