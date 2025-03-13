package dev.poporo.sec03;

import dev.poporo.models.sec03.BodyStyle;
import dev.poporo.models.sec03.Book;
import dev.poporo.models.sec03.Car;
import dev.poporo.models.sec03.Dealer;
import dev.poporo.models.sec03.Library;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Lec06Map {

    private static final Logger log = LoggerFactory.getLogger(Lec06Map.class);

    public static void main(String[] args) {

        var car1 = Car.newBuilder()
                .setMake("honda")
                .setModel("civic")
                .setYear(2000)
                .setBodyStyle(BodyStyle.COUPE)
                .build();

        var car2 = Car.newBuilder()
                .setMake("honda")
                .setModel("accord")
                .setYear(2002)
                .setBodyStyle(BodyStyle.SEDAN)
                .build();

        var dealer = Dealer.newBuilder()
                .putInventory(car1.getYear(), car1)
                .putInventory(car2.getYear(), car2)
                .build();

        log.info("{}", dealer);

        log.info("2002 ? : {}", dealer.containsInventory(2002));
        log.info("2003 ? : {}", dealer.containsInventory(2003));

        log.info("2002 model: {}", dealer.getInventoryOrThrow(2002).getBodyStyle());
    }
}
