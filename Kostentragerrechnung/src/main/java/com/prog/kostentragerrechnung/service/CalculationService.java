package com.prog.kostentragerrechnung.service;

import com.prog.kostentragerrechnung.model.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


import java.util.List;




/**
 * <h2>CalculationService – v2</h2>
 * <p>
 * Calculates cost columns (<code>k_mat</code> and <code>k_fert</code>) for both
 * {@link Teil} and {@link Auftrag} objects and returns <em>the same collections</em>
 * (now containing the filled values) wrapped in a single immutable record
 * {@link CalculationResult}.
 * <p>
 */
public class CalculationService {

    /* ───────────────── configuration ───────────────── */

//    /** 10 % material overhead */
//    private static final BigDecimal MGK_RATE = BigDecimal.valueOf(0.10);
//
//    /** 80 % fabrication overhead */
//    private static final BigDecimal FGK_RATE = BigDecimal.valueOf(0.80);
//
//    private static final MathContext MC = new MathContext(12, RoundingMode.HALF_UP);
//
//    /* ───────────────── public API ───────────────── */
//
//    /**
//     * Calculates all costs and returns the <strong>same lists</strong> with their
//     * objects mutated so that {@code k_mat} and {@code k_fert} are populated.
//     *
//     * @return a {@link CalculationResult} wrapper containing the supplied lists.
//     */
    public void calculateCosts(
            List<Arbeitsplan> arbeitsplans,
            List<Auftrag>     auftrags,
            List<Maschine>    maschines,
            List<Material>    materials,
            List<Teil>        teils) {}
//
//        /* 1. Index master data */
//        Map<String, Material> materialByNo = materials.stream()
//                .collect(Collectors.toMap(Material::getMaterialNummer, Function.identity()));
//
//        Map<String, Maschine> machineByNo = maschines.stream()
//                .collect(Collectors.toMap(Maschine::getMaschinenNummer, Function.identity()));
//
//        Map<String, List<Arbeitsplan>> opsByTeil = arbeitsplans.stream()
//                .collect(Collectors.groupingBy(Arbeitsplan::getTeilId));
//
//        Map<Integer, Teil> teilByPos = teils.stream()
//                .collect(Collectors.toMap(Teil::getUnterstrukturPosition, Function.identity()));
//
//        /* 2. Build children map (parentPos → kids) */
//        Map<Integer, List<Teil>> childrenByPos = new HashMap<>();
//        for (Teil t : teils) {
//            Integer parentPos = findParentPos(t.getUnterstrukturPosition(), teilByPos);
//            if (parentPos != null) {
//                childrenByPos.computeIfAbsent(parentPos, k -> new ArrayList<>()).add(t);
//            }
//        }
//
//        /* 3. Roots (= Teile without parent) */
//        List<Teil> roots = teils.stream()
//                .filter(t -> findParentPos(t.getUnterstrukturPosition(), teilByPos) == null)
//                .sorted(Comparator.comparingInt(Teil::getUnterstrukturPosition))
//                .toList();
//
//        /* 4. Roll‑up with memoisation */
//        Map<String, Cost> cache = new HashMap<>();
//
//        System.out.println("================ Herstellkosten ================");
//        System.out.printf("%-12s %-15s %15s%n", "Pos", "Teil‑Nr.", "HK €");
//
//        for (Teil root : roots) {
//            Cost costRoot = computeTeilCost(root, materialByNo, machineByNo, opsByTeil, childrenByPos, cache);
//            System.out.printf("%-12d %-15s %15.2f%n", root.getUnterstrukturPosition(), root.getTeilNummer(), costRoot.hk());
//        }
//
//        System.out.println("(MGK=" + MGK_RATE + ", FGK=" + FGK_RATE + ")");
//        System.out.println("================================================\n");
//
//        /* 5. Fill Auftrag columns (k_mat, k_fert) */
//        for (Auftrag auftrag : auftrags) {
//            // Assuming Auftrags reference the root Teil via auftrag.getTeilId()
//            Cost cost = cache.get(auftrag.getTeilId());
//            if (cost == null) {
//                System.err.println("⚠️  Teil " + auftrag.getTeilId() + " fehlt (Auftrag " + auftrag.getAuftragId() + ")");
//                continue;
//            }
//            auftrag.setKMat(cost.kMat());
//            auftrag.setKFert(cost.kFert());
//        }
//
//        /* 6. Return mutated lists */
//        return new CalculationResult(arbeitsplans, auftrags, maschines, materials, teils);
//    }
//
//    /* ─────────────── internal recursion ─────────────── */
//
//    private Cost computeTeilCost(
//            Teil teil,
//            Map<String, Material> materialByNo,
//            Map<String, Maschine> machineByNo,
//            Map<String, List<Arbeitsplan>> opsByTeil,
//            Map<Integer, List<Teil>> childrenByPos,
//            Map<String, Cost> cache) {
//
//        Cost cached = cache.get(teil.getTeilId());
//        if (cached != null) return cached;
//
//        /* 1. Children */
//        BigDecimal childrenHK    = BigDecimal.ZERO;
//        BigDecimal childrenKMat  = BigDecimal.ZERO;
//        BigDecimal childrenKFert = BigDecimal.ZERO;
//
//        for (Teil child : childrenByPos.getOrDefault(teil.getUnterstrukturPosition(), List.of())) {
//            Cost c = computeTeilCost(child, materialByNo, machineByNo, opsByTeil, childrenByPos, cache);
//            BigDecimal qty = BigDecimal.valueOf(child.getAnzahl());
//
//            childrenHK    = childrenHK.add(c.hk().multiply(qty, MC), MC);
//            childrenKMat  = childrenKMat.add(c.kMat().multiply(qty, MC), MC);
//            childrenKFert = childrenKFert.add(c.kFert().multiply(qty, MC), MC);
//        }
//
//        /* 2. Direct material + MGK */
//        BigDecimal directMat = BigDecimal.ZERO;
//        if (teil.getMaterialNummer() != null && !teil.getMaterialNummer().isBlank()) {
//            Material mat = materialByNo.get(teil.getMaterialNummer());
//            if (mat == null) {
//                System.err.println("⚠️  Material " + teil.getMaterialNummer() + " fehlt (Teil " + teil.getTeilNummer() + ")");
//            } else {
//                directMat = BigDecimal.valueOf(mat.getKostenProStueck())
//                        .multiply(BigDecimal.valueOf(teil.getAnzahl()), MC);
//            }
//        }
//        BigDecimal mgk      = directMat.multiply(MGK_RATE, MC);
//        BigDecimal kMatThis = directMat.add(mgk, MC);
//
//        /* 3. Direct fabrication + FGK */
//        BigDecimal directFab = BigDecimal.ZERO;
//        for (Arbeitsplan op : opsByTeil.getOrDefault(teil.getTeilId(), List.of())) {
//            Maschine mach = machineByNo.get(op.getMaschinenNummer());
//            if (mach == null) {
//                System.err.println("⚠️  Maschine " + op.getMaschinenNummer() + " fehlt (Arbeitsplan " + op.getArbeitsplanId() + ")");
//                continue;
//            }
//            BigDecimal stunden = BigDecimal.valueOf(op.getBearbeitungsdauerMin()).divide(BigDecimal.valueOf(60), MC);
//            directFab = directFab.add(stunden.multiply(BigDecimal.valueOf(mach.getKostensatzProStunde()), MC), MC);
//        }
//        BigDecimal fgk       = directFab.multiply(FGK_RATE, MC);
//        BigDecimal kFertThis = directFab.add(fgk, MC);
//
//        /* 4. Summation */
//        BigDecimal hk = childrenHK.add(kMatThis, MC).add(kFertThis, MC)
//                .setScale(2, RoundingMode.HALF_UP);
//
//        /* 5. Persist direct values into Teil */
//        teil.setKMat(kMatThis.setScale(2, RoundingMode.HALF_UP));
//        teil.setKFert(kFertThis.setScale(2, RoundingMode.HALF_UP));
//
//        /* 6. Cache */
//        Cost cost = new Cost(kMatThis, kFertThis, hk);
//        cache.put(teil.getTeilId(), cost);
//        return cost;
//    }
//
//    /* ─────────────── helper ─────────────── */
//
//    private Integer findParentPos(int pos, Map<Integer, Teil> teilByPos) {
//        int probe = pos;
//        while (probe > 0) {
//            probe /= 10;
//            if (teilByPos.containsKey(probe)) return probe;
//        }
//        return null;
//    }
//
//    /* ─────────────── local records ─────────────── */
//
//    private record Cost(BigDecimal kMat, BigDecimal kFert, BigDecimal hk) {}
//
//    /** Wrapper for all lists so the caller gets them back in one object. */
//    public record CalculationResult(
//            List<Arbeitsplan> arbeitsplans,
//            List<Auftrag>     auftrags,
//            List<Maschine>    maschines,
//            List<Material>    materials,
//            List<Teil>        teils) {}
}
