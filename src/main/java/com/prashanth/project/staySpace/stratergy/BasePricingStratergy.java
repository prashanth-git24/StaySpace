package com.prashanth.project.staySpace.stratergy;

import com.prashanth.project.staySpace.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class BasePricingStratergy implements PricingStratergy{
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        return inventory.getRoom().getPrice();
    }
}
