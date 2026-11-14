package com.prashanth.project.staySpace.stratergy;

import com.prashanth.project.staySpace.entity.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UrgencyPricingStrategy implements PricingStratergy{
    private final PricingStratergy wrapped;
    @Override
    public BigDecimal calculatePrice(Inventory inventory) {
        BigDecimal price = wrapped.calculatePrice(inventory);
        LocalDate today = LocalDate.now();
        LocalDate checkInDate = inventory.getDate();
        long daysUntilCheckIn = checkInDate.toEpochDay() - today.toEpochDay();
        if(daysUntilCheckIn < 3){
            price = price.multiply( BigDecimal.valueOf(1.2));
        }
        return price;
    }
}
