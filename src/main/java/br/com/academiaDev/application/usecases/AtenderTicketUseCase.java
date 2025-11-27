package br.com.academiaDev.application.usecases;

import br.com.academiaDev.application.repositories.SupportTicketQueue;
import br.com.academiaDev.domain.entities.SupportTicket;

public class AtenderTicketUseCase {
    private final SupportTicketQueue ticketQueue;

    public AtenderTicketUseCase(SupportTicketQueue ticketQueue) {
        this.ticketQueue = ticketQueue;
    }

    public SupportTicket execute() {
        return ticketQueue.next().orElse(null);
    }
}