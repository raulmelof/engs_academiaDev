package br.com.academiaDev.application.usecases;

import br.com.academiaDev.application.repositories.SupportTicketQueue;
import br.com.academiaDev.domain.entities.SupportTicket;
import br.com.academiaDev.domain.entities.User;

public class AbrirTicketUseCase {
    private final SupportTicketQueue ticketQueue;

    public AbrirTicketUseCase(SupportTicketQueue ticketQueue) {
        this.ticketQueue = ticketQueue;
    }

    public void execute(String userEmail, String title, String message) {
        User user = new br.com.academiaDev.domain.entities.Student("User", userEmail, null);
        
        SupportTicket ticket = new SupportTicket(title, message, user);
        ticketQueue.add(ticket);
        System.out.println("Ticket criado com sucesso!");
    }
}