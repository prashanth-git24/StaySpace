package com.prashanth.project.staySpace.stratergy;

import com.prashanth.project.staySpace.entity.Inventory;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class OccupancyPricingStrategy implements PricingStratergy{
    private final PricingStratergy wrapped;
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal Price = wrapped.calculatePrice(inventory);
        double occupancyFactor = (double)inventory.getBookedCount() /  inventory.getTotalCount();
        if(occupancyFactor > 0.8){
            Price= Price.multiply(new BigDecimal(1.2));
        }
        return Price;
    }
}
