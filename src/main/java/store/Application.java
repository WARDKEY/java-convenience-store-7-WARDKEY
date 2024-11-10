package store;

import store.controller.StoreController;
import store.view.InputView;
import store.view.OutputView;

public class Application {
    public static void main(String[] args) {
        OutputView outputView = new OutputView();
        InputView inputView = new InputView();
        StoreController storeController = new StoreController(outputView, inputView);
        storeController.run();
    }
}
