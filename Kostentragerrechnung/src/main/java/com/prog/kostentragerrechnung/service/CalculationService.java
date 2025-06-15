package com.prog.kostentragerrechnung.service;

import com.prog.kostentragerrechnung.model.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


import java.util.List;

public class CalculationService {

    public void calculateCosts() {
        Auftrag.berechneAlleKosten();
    }

}
