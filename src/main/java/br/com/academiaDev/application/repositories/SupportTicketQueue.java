package br.com.academiaDev.application.repositories;

import java.util.Optional;

import br.com.academiaDev.domain.entities.SupportTicket;

public interface SupportTicketQueue {
    void add(SupportTicket ticket);
    Optional<SupportTicket> next();
    boolean isEmpty();
}
