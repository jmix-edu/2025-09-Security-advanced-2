package com.company.samplesalessecurity.app;

import com.company.samplesalessecurity.entity.Order;
import io.jmix.core.DataManager;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("sales_SpecialProductCount")
public class SpecialProductCount {
    private final DataManager dataManager;

    public SpecialProductCount(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    public int getSpecialProductsNumber(Order order){
        long count = order.getLines().stream()
                .filter(orderLine -> {
                    Optional<Boolean> special = Optional.ofNullable(orderLine.getProduct().getSpecial());
                    return special.orElse(false);
                })
                .count();

        return (int) count;
    }
}