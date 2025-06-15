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
        // 1. Запускаем расчет для всех Aufträge
        Auftrag.berechneAlleKosten();

        // 2. Печатаем результаты по каждому Auftrag и Teil
        for (Auftrag auftrag : Auftrag.auftrags) {
            System.out.println("📦 Auftrag: " + auftrag.getAuftragNummer());
            System.out.println("   ➤ Materialkosten: " + auftrag.getMaterialkosten() + " €");
            System.out.println("   ➤ Fertigungskosten: " + auftrag.getFertigungskosten() + " €");
            System.out.println("   ➤ Datum: " + auftrag.getDatumKostenrechnung());
            System.out.println();

            for (Teil teil : auftrag.getTeil()) {
                System.out.println("     🔹 Teil: " + teil.getTeilNummer());
                System.out.println("        ➤ Anzahl: " + teil.getAnzahl());
                System.out.println("        ➤ K_mat: " + teil.getMaterialkosten() + " €");
                System.out.println("        ➤ K_mat Zuschlag: " + teil.getMaterialgemeinkosten() + " €");
                System.out.println("        ➤ K_fert: " + teil.getFertigungskosten() + " €");
                System.out.println("        ➤ K_fert Zuschlag: " + teil.getFertigungsgemeinkosten() + " €");
                System.out.println("        ➤ Herstellkosten: " + teil.getHerstellkosten() + " €");
                System.out.println();
            }
        }
    }

    public void calculateCostsAndPrintReports() {
        List<Report> reports = new ArrayList<>();

        for (Auftrag auftrag : Auftrag.auftrags) {
            for (Teil teil : auftrag.getTeil()) {
                Report r = new Report().createReport(teil, true); // 🔁 with recursion
                reports.add(r);
            }
        }

        // 📤 Печать всех Report'ов
        for (Report r : reports) {
            System.out.println("📄 Report für Teil " + r.getTeilNummer() + ":");
            System.out.println("   ➤ Auftrag: " + r.getAuftragNummer());
            System.out.println("   ➤ Materialtyp: " + r.getMaterialTyp());
            System.out.println("   ➤ Maschine: " + r.getMaschineNummer());
            System.out.println("   ➤ Anzahl: " + r.getAnzahl());
            System.out.println("   ➤ Materialkosten: " + r.getMaterialkosten() + " €");
            System.out.println("   ➤ Materialgemeinkosten: " + r.getMaterialgemeinkosten() + " €");
            System.out.println("   ➤ Fertigungskosten: " + r.getFertigungskosten() + " €");
            System.out.println("   ➤ Fertigungsgemeinkosten: " + r.getFertigungsgemeinkosten() + " €");
            System.out.println("   ➤ Herstellkosten: " + r.getHerstellkosten() + " €");
            System.out.println("   ➤ Dauer: " + r.getBearbeitungsdauerMin() + " min");
            System.out.println("   ➤ Datum: " + r.getBerechnungsdatum());
            System.out.println("   ➤ Recursive: " + r.isIstRecursive());
            System.out.println("-------------------------------------------------------");
        }
    }
}

