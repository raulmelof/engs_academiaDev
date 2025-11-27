package br.com.academiaDev.infrastructure.persistence;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;

import br.com.academiaDev.application.repositories.SupportTicketQueue;
import br.com.academiaDev.domain.entities.SupportTicket;

public class SupportTicketQueueInMemory implements SupportTicketQueue {
    private final Queue<SupportTicket> queue = new ArrayDeque<>();

    @Override
    public void add(SupportTicket ticket) {
        queue.add(ticket);
    }

    @Override
    public Optional<SupportTicket> next() {
        return Optional.ofNullable(queue.poll()); // poll remove e retorna o primeiro
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}