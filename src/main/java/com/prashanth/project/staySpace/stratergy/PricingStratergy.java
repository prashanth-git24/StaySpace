package com.prashanth.project.staySpace.stratergy;

import com.prashanth.project.staySpace.entity.Inventory;

import java.math.BigDecimal;

public interface PricingStratergy {
    BigDecimal calculatePrice(Inventory inventory);
}
