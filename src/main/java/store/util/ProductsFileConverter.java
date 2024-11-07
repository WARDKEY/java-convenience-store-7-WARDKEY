package store.util;

import store.model.Products;
import store.model.Stock;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ProductsFileConverter {

    public Products loadProducts() {
        List<Stock> stocks = new ArrayList<>();

        try (BufferedReader buffer = new BufferedReader(new FileReader(FilePath.PRODUCTS_FILE_PATH.getFilePath()))) {
            skipHeader(buffer);
            convertProductLine(buffer, stocks);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return new Products(stocks);
    }

    private void skipHeader(BufferedReader buffer) throws IOException {
        buffer.readLine();
    }

    private void convertProductLine(BufferedReader br, List<Stock> stocks) throws IOException {
        String line;
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            saveStocks(stocks, values);
        }
    }

    private void saveStocks(List<Stock> stocks, String[] values) {
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
