import com.prog.kostentragerrechnung.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class KostenberechnungTest {

    @BeforeEach
    public void setup() {
        Material.materials.clear();
        Maschine.maschines.clear();
        Teil.teils.clear();
        Auftrag.auftrags.clear();
        Arbeitsplan.arbeitsplans.clear();
        System.out.println("🔁 Alle Listen wurden geleert.\n");
    }

    @Test
    public void testKostenBerechnungMehrereFaelle() {
        System.out.println("🚀 Test startet...");

        // 1️⃣ Materialien
        Material mat1 = new Material("M001", 5.0); // 5€/Stück
        Material mat2 = new Material("M002", 7.5); // 7.5€/Stück
        Material.materials.add(mat1);
        Material.materials.add(mat2);
        System.out.println("📦 Materialien erstellt:");
        System.out.println(mat1);
        System.out.println(mat2);

        // 2️⃣ Maschinen
        Maschine maschine1 = new Maschine("MAS01", "Drehmaschine", 30.0); // 0.5€/Min
        Maschine maschine2 = new Maschine("MAS02", "Fräsmaschine", 60.0); // 1.0€/Min
        Maschine.maschines.add(maschine1);
        Maschine.maschines.add(maschine2);
        System.out.println("\n⚙️ Maschinen erstellt:");
        System.out.println(maschine1);
        System.out.println(maschine2);

        // 3️⃣ Teil 1
        Teil teil1 = new Teil(null, null, new ArrayList<>(), 0, 0, 1, null, mat1, "T001");
        Arbeitsplan ap1 = new Arbeitsplan(1, maschine1, 10); // 10 min
        teil1.setArbeitsplan(ap1);
        Teil.teils.add(teil1);
        Arbeitsplan.arbeitsplans.add(ap1);

        // 4️⃣ Teil 2
        Teil teil2 = new Teil(null, null, new ArrayList<>(), 0, 0, 2, null, mat2, "T002");
        Arbeitsplan ap2 = new Arbeitsplan(2, maschine2, 15); // 15 min
        teil2.setArbeitsplan(ap2);
        Teil.teils.add(teil2);
        Arbeitsplan.arbeitsplans.add(ap2);

        System.out.println("\n🧩 Teile und zugehörige Arbeitspläne:");
        System.out.println("Teil 1: " + teil1);
        System.out.println("Arbeitsplan 1: " + ap1);
        System.out.println("Teil 2: " + teil2);
        System.out.println("Arbeitsplan 2: " + ap2);

        // 5️⃣ Aufträge
        Auftrag auftrag1 = new Auftrag("A0001", 0, 0, null, teil1);
        Auftrag auftrag2 = new Auftrag("A0002", 0, 0, null, teil2);
        Auftrag.auftrags.add(auftrag1);
        Auftrag.auftrags.add(auftrag2);

        System.out.println("\n📄 Aufträge erstellt:");
        System.out.println(auftrag1);
        System.out.println(auftrag2);

        // 6️⃣ Kostenberechnung
        auftrag1.berechneKosten();
        auftrag2.berechneKosten();

        System.out.println("\n💰 Berechnete Kosten:");
        System.out.printf("Auftrag %s: K_mat=%.2f €, K_fert=%.2f €, Datum=%s%n",
                auftrag1.getAuftragNummer(),
                auftrag1.getMaterialkosten(),
                auftrag1.getFertigungskosten(),
                auftrag1.getDatumKostenrechnung());

        System.out.printf("Auftrag %s: K_mat=%.2f €, K_fert=%.2f €, Datum=%s%n",
                auftrag2.getAuftragNummer(),
                auftrag2.getMaterialkosten(),
                auftrag2.getFertigungskosten(),
                auftrag2.getDatumKostenrechnung());

        // 7️⃣ Assertions Auftrag 1
        assertEquals(5.0, auftrag1.getMaterialkosten(), 0.01);
        assertEquals(5.0, auftrag1.getFertigungskosten(), 0.01);
        assertEquals(LocalDate.now(), auftrag1.getDatumKostenrechnung());

        // 8️⃣ Assertions Auftrag 2
        assertEquals(7.5, auftrag2.getMaterialkosten(), 0.01);
        assertEquals(15.0, auftrag2.getFertigungskosten(), 0.01);
        assertEquals(LocalDate.now(), auftrag2.getDatumKostenrechnung());

        System.out.println("\n✅ Test erfolgreich abgeschlossen.");
    }
}
