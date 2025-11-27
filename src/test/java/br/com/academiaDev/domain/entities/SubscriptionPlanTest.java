package br.com.academiaDev.domain.entities;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class SubscriptionPlanTest {

    @Test
    void basicPlanDeveBloquearQuartaMatricula() {
        SubscriptionPlan plan = new BasicPlan();
        
        assertTrue(plan.canEnroll(0));
        assertTrue(plan.canEnroll(2));
        assertFalse(plan.canEnroll(3));
        assertFalse(plan.canEnroll(10));
    }

    @Test
    void premiumPlanDeveAceitarTudo() {
        SubscriptionPlan plan = new PremiumPlan();
        
        assertTrue(plan.canEnroll(0));
        assertTrue(plan.canEnroll(3));
        assertTrue(plan.canEnroll(999));
    }
}