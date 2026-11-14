package com.prashanth.project.staySpace.stratergy;

import com.prashanth.project.staySpace.entity.Inventory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PricingService {

    public BigDecimal calculatePrice(Inventory inventory){
        PricingStratergy pricingStratergy = new BasePricingStratergy();
        pricingStratergy = new SurgePricingStrategy(pricingStratergy);
        pricingStratergy = new OccupancyPricingStrategy(pricingStratergy);
        pricingStratergy = new UrgencyPricingStrategy(pricingStratergy);
        return pricingStratergy.calculatePrice(inventory);
    }
}
