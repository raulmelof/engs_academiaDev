package br.com.academiaDev.domain.entities;

public class Student {
    private String name;
    private String email;
    private SubscriptionPlan subscriptionPlan;

    public Student(String name, String email, SubscriptionPlan subscriptionPlan) {
        this.name = name;
        this.email = email;
        this.subscriptionPlan = subscriptionPlan;
    }

    public String getEmail() { return email; }
    public String getName() { return name; }
    public SubscriptionPlan getSubscriptionPlan() { return subscriptionPlan; }
}