import com.prog.kostentragerrechnung.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
        Material mat1 = new Material("M001", 12);
        Material mat2 = new Material("M002", 13);
        Material mat3 = new Material("M003", 14);
        Material mat4 = new Material("M004", 15);
        System.out.println("📦 Materialien erstellt:");
        System.out.println(mat1);
        System.out.println(mat2);
        System.out.println(mat3);
        System.out.println(mat4);

        // 2️⃣ Maschinen
        Maschine maschine1 = new Maschine("MAS01", "Maschine", 60.0);
        System.out.println("\n⚙️ Maschinen erstellt:");
        System.out.println(maschine1);

        // 5️⃣ Aufträge
        Auftrag auftrag1 = new Auftrag("A0001", 0, 0, null, null);

        Teil teil1 = new Teil(new ArrayList<>(), 0, 0, 1, null, null, "Stuhl");
        Arbeitsplan ap1 = new Arbeitsplan(1, maschine1, 30);
        teil1.setArbeitsplan(ap1);
        Arbeitsplan.arbeitsplans.add(ap1);

        auftrag1.setTeil((List<Teil>) teil1);

        // 3️⃣ Teil 2
        Teil teil2 = new Teil(new ArrayList<>(), 0, 0, 1, null, mat1, "Lehne");
        Arbeitsplan ap2 = new Arbeitsplan(1, maschine1, 30);
        teil1.setArbeitsplan(ap2);

        // 3️⃣ Teil 3
        Teil teil3 = new Teil(new ArrayList<>(), 0, 0, 1, null, mat2, "Sitz");
        Arbeitsplan ap3 = new Arbeitsplan(1, maschine1, 30);
        teil1.setArbeitsplan(ap3);

        // 3️⃣ Teil 4
        Teil teil4 = new Teil(new ArrayList<>(), 0, 0, 4, null, null, "Stuhlbeine");
        Arbeitsplan ap4 = new Arbeitsplan(1, maschine1, 10);
        teil1.setArbeitsplan(ap4);

        // 3️⃣ Teil 5
        Teil teil5 = new Teil(new ArrayList<>(), 0, 0, 1, null, mat3, "Fuss");
        Arbeitsplan ap5 = new Arbeitsplan(1, maschine1, 30);
        teil1.setArbeitsplan(ap5);

        // 3️⃣ Teil 6
        Teil teil6 = new Teil(new ArrayList<>(), 0, 0, 1, null, mat4, "Grunchk");
        Arbeitsplan ap6 = new Arbeitsplan(1, maschine1, 60);
        teil1.setArbeitsplan(ap6);

        System.out.println("\n🧩 Teile und zugehörige Arbeitspläne:");
        System.out.println("Teil 1: " + teil1);
        System.out.println("Arbeitsplan 1: " + ap1);
        System.out.println("Teil 2: " + teil2);
        System.out.println("Arbeitsplan 2: " + ap2);
        System.out.println("Teil 3: " + teil3);
        System.out.println("Arbeitsplan 3: " + ap3);
        System.out.println("Teil 4: " + teil4);
        System.out.println("Arbeitsplan 4: " + ap4);
        System.out.println("Teil 5: " + teil5);
        System.out.println("Arbeitsplan 5: " + ap5);
        System.out.println("Teil 6: " + teil6);
        System.out.println("Arbeitsplan 6: " + ap6);

        System.out.println("\n💰 Berechnete Kosten:");
        System.out.printf("Auftrag %s: K_mat=%.2f €, K_fert=%.2f €, Datum=%s%n",
                auftrag1.getAuftragNummer(),
                auftrag1.getMaterialkosten(),
                auftrag1.getFertigungskosten(),
                auftrag1.getDatumKostenrechnung());

        System.out.println("\n✅ Test erfolgreich abgeschlossen.");
    }
}
