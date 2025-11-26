package br.com.academiaDev.domain.entities;

public class BasicPlan implements SubscriptionPlan {
    
    private static final int MAX_ACTIVE_COURSES = 3;

    @Override
    public boolean canEnroll(int activeEnrollments) {
        return activeEnrollments < MAX_ACTIVE_COURSES;
    }

    @Override
    public String getPlanName() {
        return "Basic Plan";
    }
}