package com.prog.kostentragerrechnung.service;

import com.prog.kostentragerrechnung.model.Auftrag;
import com.prog.kostentragerrechnung.model.Report;
import com.prog.kostentragerrechnung.model.Teil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportService {

    public static Report createReport(Teil teil, boolean recursive) {
        teil.berechneKosten(recursive);
        Report r = new Report();

        r.setAuftragNummer(teil.getAuftrag() != null ? teil.getAuftrag().getAuftragNummer() : null);
        r.setTeilNummer(teil.getTeilNummer());
        r.setAnzahl(teil.getAnzahl());
        r.setMaterialTyp(teil.getMaterial() != null ? teil.getMaterial().getMaterialNummer() : null);
        r.setMaschineNummer(teil.getArbeitsplan() != null && teil.getArbeitsplan().getMaschine() != null
                ? teil.getArbeitsplan().getMaschine().getMaschinenNummer() : null);

        r.setMaterialkosten(teil.getMaterialkosten());
        r.setMaterialgemeinkosten(teil.getMaterialgemeinkosten());
        r.setFertigungskosten(teil.getFertigungskosten());
        r.setFertigungsgemeinkosten(teil.getFertigungsgemeinkosten());
        r.setHerstellkosten(teil.getHerstellkosten());
        r.setBearbeitungsdauerMin(teil.getArbeitsplan() != null ? (int) teil.getArbeitsplan().getBearbeitungsdauerMin() : 0);
        r.setBerechnungsdatum(LocalDate.now());
        r.setIstRecursive(recursive);

        return r;
    }

    public static Map<String, Double[]> generateMaterialKostenReport() {
        Map<String, Double[]> materialMap = new HashMap<>();

        for (Teil teil : Teil.teils) {
            if (teil.getMaterial() == null) continue;

            String typ = teil.getMaterial().getMaterialNummer();
            double mat = teil.getMaterialkosten();
            double matGK = teil.getMaterialgemeinkosten();

            materialMap.putIfAbsent(typ, new Double[]{0.0, 0.0});

            Double[] values = materialMap.get(typ);
            values[0] += mat;
            values[1] += matGK;

            // округление
            values[0] = Math.round(values[0] * 100.0) / 100.0;
            values[1] = Math.round(values[1] * 100.0) / 100.0;
        }

        return materialMap;
    }

    public static List<Report> createReportsForAuftrag(Auftrag auftrag) {
        List<Report> reports = new ArrayList<>();
        for (Teil teil : auftrag.getTeil()) {
            reports.add(createReport(teil, true));
        }
        return reports;
    }
}
