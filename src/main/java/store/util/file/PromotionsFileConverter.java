package store.util.file;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import store.model.Discount;
import store.model.Promotions;

public class PromotionsFileConverter {

    public Promotions loadFile() {
        List<Discount> discounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FilePath.PROMOTIONS_FILE_PATH.getFilePath()))) {
            skipHeader(reader);
            convertLine(reader, discounts);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new Promotions(discounts);
    }

    private void skipHeader(BufferedReader reader) throws IOException {
        reader.readLine();
    }

    private void convertLine(BufferedReader reader, List<Discount> discounts) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            save(discounts, values);
        }
    }

    private void save(List<Discount> discounts, String[] valuse) {
        if (valuse.length == 5) {
            String name = valuse[0];
            String buy = valuse[1];
            String get = valuse[2];
            String startDate = valuse[3];
            String endDate = valuse[4];
            discounts.add(new Discount(name, buy, get, startDate, endDate));
        }
    }
}
