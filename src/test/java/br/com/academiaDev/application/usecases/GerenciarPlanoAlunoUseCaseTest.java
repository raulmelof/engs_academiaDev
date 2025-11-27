package br.com.academiaDev.application.usecases;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import org.junit.jupiter.api.Test;

class GerenciarPlanoAlunoUseCaseTest {
    
    @Test
    void deveExecutarSemLancarErros() {
        GerenciarPlanoAlunoUseCase useCase = new GerenciarPlanoAlunoUseCase();
        
        assertDoesNotThrow(() -> useCase.execute("email@teste.com", "Premium"));
    }
}