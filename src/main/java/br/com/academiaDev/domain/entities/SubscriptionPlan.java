package br.com.academiaDev.domain.entities;

public interface SubscriptionPlan {
    boolean canEnroll(int activeEnrollments);
    
    String getPlanName();
}