package store.util.file;

import store.model.Products;
import store.model.Stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProductsFileConverter {

    public Products loadFile() {
        List<Stock> stocks = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FilePath.PRODUCTS_FILE_PATH.getFilePath()))) {
            skipHeader(reader);
            convertLine(reader, stocks);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return new Products(stocks);
    }

    private void skipHeader(BufferedReader reader) throws IOException {
        reader.readLine();
    }

    private void convertLine(BufferedReader reader, List<Stock> stocks) throws IOException {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] values = line.split(",");
            save(stocks, values);
        }
    }

    private void save(List<Stock> stocks, String[] values) {
        if (values.length == 4) {
            String name = values[0];
            String price = values[1];
            String quantity = values[2];
            String promotion = values[3];

            promotion = checkPromotion(promotion);
            stocks.add(new Stock(name, price, quantity, promotion));
        }
    }

    private String checkPromotion(String promotion) {
        if ("null".equals(promotion)) {
            promotion = null;
        }
        return promotion;
    }
}
