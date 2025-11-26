package br.com.academiaDev.domain.entities;

public class PremiumPlan implements SubscriptionPlan {

    @Override
    public boolean canEnroll(int activeEnrollments) {
        return true;
    }

    @Override
    public String getPlanName() {
        return "Premium Plan";
    }
}