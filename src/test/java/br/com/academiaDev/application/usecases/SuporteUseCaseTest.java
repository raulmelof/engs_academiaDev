package br.com.academiaDev.application.usecases;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.academiaDev.application.repositories.SupportTicketQueue;
import br.com.academiaDev.domain.entities.SupportTicket;

@ExtendWith(MockitoExtension.class)
class SuporteUseCaseTest {

    @Mock private SupportTicketQueue queue;
    
    @InjectMocks private AbrirTicketUseCase abrirTicketUseCase;
    @InjectMocks private AtenderTicketUseCase atenderTicketUseCase;

    @Test
    void deveAbrirTicketCorretamente() {
        abrirTicketUseCase.execute("email@teste.com", "Titulo", "Mensagem");

        ArgumentCaptor<SupportTicket> captor = ArgumentCaptor.forClass(SupportTicket.class);
        verify(queue).add(captor.capture());

        SupportTicket ticketSalvo = captor.getValue();
        assertEquals("Titulo", ticketSalvo.getTitle());
        assertEquals("Mensagem", ticketSalvo.getMessage());
    }

    @Test
    void deveAtenderTicketDaFila() {
        SupportTicket ticket = new SupportTicket("T", "M", null);
        when(queue.next()).thenReturn(Optional.of(ticket));

        SupportTicket ticketAtendido = atenderTicketUseCase.execute();

        assertNotNull(ticketAtendido);
        assertEquals("T", ticketAtendido.getTitle());
    }

    @Test
    void deveRetornarNullSeFilaVazia() {
        when(queue.next()).thenReturn(Optional.empty());

        assertNull(atenderTicketUseCase.execute());
    }
}