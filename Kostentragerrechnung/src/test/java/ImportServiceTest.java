import com.prog.kostentragerrechnung.model.*;
import com.prog.kostentragerrechnung.service.ImportService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.*;

public class ImportServiceTest {

    @BeforeEach
    public void setup() {
        Material.materials.clear();
        Maschine.maschines.clear();
        Teil.teils.clear();
        Auftrag.auftrags.clear();
        Arbeitsplan.arbeitsplans.clear();
    }

    @Test
    public void testImportFromExcel() throws Exception {

        File excelFile = new File("src/test/resources/05_06_07_Kosten_KT_KA_KS.xlsx");
        assertTrue(excelFile.exists(), "Excel file not found!");

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite::memory:")) {

            conn.createStatement().execute("CREATE TABLE material (nr TEXT, kosten REAL)");
            conn.createStatement().execute("CREATE TABLE maschine (nr TEXT, bez TEXT, kosten REAL)");
            conn.createStatement().execute("CREATE TABLE teil (id INTEGER)");
            conn.createStatement().execute("CREATE TABLE arbeitsplan (id INTEGER)");
            conn.createStatement().execute("CREATE TABLE auftrag (id INTEGER)");

            ImportService service = new ImportService();
            service.excelToData(excelFile, conn);

            System.out.println("📦 Materials:");
            Material.materials.forEach(System.out::println);
            assertFalse(Material.materials.isEmpty());

            System.out.println("\n⚙️ Maschinen:");
            Maschine.maschines.forEach(System.out::println);
            assertFalse(Maschine.maschines.isEmpty());

            System.out.println("\n🧩 Teile:");
            Teil.teils.forEach(System.out::println);
            assertFalse(Teil.teils.isEmpty());

            System.out.println("\n📋 Arbeitspläne:");
            Arbeitsplan.arbeitsplans.forEach(System.out::println);
            assertFalse(Arbeitsplan.arbeitsplans.isEmpty());

            System.out.println("\n📄 Aufträge:");
            Auftrag.auftrags.forEach(System.out::println);
            assertFalse(Auftrag.auftrags.isEmpty());

            Material firstMaterial = Material.materials.get(0);
            assertNotNull(firstMaterial.getMaterialNummer());
            assertTrue(firstMaterial.getKostenProStueck() > 0);

            Maschine firstMaschine = Maschine.maschines.get(0);
            assertNotNull(firstMaschine.getMaschinenNummer());
            assertTrue(firstMaschine.getKostensatzProStunde() > 0);

            for (Auftrag a : Auftrag.auftrags) {
                System.out.printf("→ Auftrag %s: K_mat=%.2f €, K_fert=%.2f €%n",
                        a.getAuftragNummer(), a.getMaterialkosten(), a.getFertigungskosten());
            }

        }
    }
}
