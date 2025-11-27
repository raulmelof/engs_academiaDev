package br.com.academiaDev;

import java.util.Scanner;

import br.com.academiaDev.application.usecases.*;
import br.com.academiaDev.infrastructure.persistence.CourseRepositoryInMemory;
import br.com.academiaDev.infrastructure.persistence.EnrollmentRepositoryInMemory;
import br.com.academiaDev.infrastructure.persistence.SupportTicketQueueInMemory;
import br.com.academiaDev.infrastructure.persistence.UserRepositoryInMemory;
import br.com.academiaDev.infrastructure.ui.ConsoleController;
import br.com.academiaDev.infrastructure.ui.menu.AdminMenuHandler;
import br.com.academiaDev.infrastructure.ui.menu.StudentMenuHandler;
import br.com.academiaDev.domain.entities.Course;
import br.com.academiaDev.domain.enums.DifficultyLevel;

public class AcademiaDev {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // 1. Instanciando a Persistência (In-Memory)
        CourseRepositoryInMemory courseRepo = new CourseRepositoryInMemory();
        UserRepositoryInMemory userRepo = new UserRepositoryInMemory();
        EnrollmentRepositoryInMemory enrollmentRepo = new EnrollmentRepositoryInMemory();
        SupportTicketQueueInMemory ticketQueueRepo = new SupportTicketQueueInMemory();

        // Carga Inicial de Dados (Opcional, pra não começar vazio)
        inicializarDados(courseRepo);

        // 2. Instanciando os UseCases (Injeção de Dependência)
        // Login
        LoginUseCase loginUseCase = new LoginUseCase(userRepo);

        // Core Acadêmico
        MatricularAlunoUseCase matricularAlunoUseCase = new MatricularAlunoUseCase(courseRepo, enrollmentRepo, userRepo);
        AtualizarProgressoUseCase atualizarProgressoUseCase = new AtualizarProgressoUseCase(enrollmentRepo);
        ConsultarMatriculasUseCase consultarMatriculasUseCase = new ConsultarMatriculasUseCase(enrollmentRepo);
        CancelarMatriculaUseCase cancelarMatriculaUseCase = new CancelarMatriculaUseCase();
        
        // Gestão e Admin
        GerenciarStatusCursoUseCase gerenciarStatusCursoUseCase = new GerenciarStatusCursoUseCase(courseRepo);
        GerenciarPlanoAlunoUseCase gerenciarPlanoAlunoUseCase = new GerenciarPlanoAlunoUseCase();
        ConsultarCatalogoUseCase consultarCatalogoUseCase = new ConsultarCatalogoUseCase(courseRepo);
        
        // Relatórios e Dados
        GerarRelatoriosUseCase gerarRelatoriosUseCase = new GerarRelatoriosUseCase(courseRepo, enrollmentRepo);
        ExportarDadosUseCase exportarDadosUseCase = new ExportarDadosUseCase(courseRepo);
        
        // Suporte
        AbrirTicketUseCase abrirTicketUseCase = new AbrirTicketUseCase(ticketQueueRepo);
        AtenderTicketUseCase atenderTicketUseCase = new AtenderTicketUseCase(ticketQueueRepo);

        // 3. Configurando os Menus com os UseCases
        StudentMenuHandler studentMenu = new StudentMenuHandler(
            scanner, 
            matricularAlunoUseCase, 
            atualizarProgressoUseCase, 
            abrirTicketUseCase,
            consultarMatriculasUseCase,
            cancelarMatriculaUseCase,
            consultarCatalogoUseCase
        );

        AdminMenuHandler adminMenu = new AdminMenuHandler(
            scanner, 
            gerenciarStatusCursoUseCase, 
            atenderTicketUseCase,
            gerarRelatoriosUseCase,
            abrirTicketUseCase,
            exportarDadosUseCase,
            consultarCatalogoUseCase,
            gerenciarPlanoAlunoUseCase
        );

        // 4. Iniciando o Controller Principal
        ConsoleController consoleController = new ConsoleController(
            loginUseCase, 
            studentMenu, 
            adminMenu
        );
        
        consoleController.start();
        scanner.close();
    }

    // Método auxiliar apenas para ter algo no banco ao iniciar
    private static void inicializarDados(CourseRepositoryInMemory courseRepo) {
        courseRepo.save(new Course("Java Clean Arch", "Curso Avançado", "Uncle Bob", 40, DifficultyLevel.ADVANCED));
        courseRepo.save(new Course("Lógica de Programação", "Iniciante", "Guanabara", 20, DifficultyLevel.BEGINNER));
        courseRepo.save(new Course("Spring Boot", "Intermediário", "Michelli Brito", 30, DifficultyLevel.INTERMEDIATE));
        System.out.println("Sistema inicializado com 3 cursos pré-cadastrados.");
    }
}