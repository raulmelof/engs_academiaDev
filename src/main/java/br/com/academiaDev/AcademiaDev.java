package br.com.academiaDev;

import java.util.Scanner;

import br.com.academiaDev.application.usecases.AbrirTicketUseCase;
import br.com.academiaDev.application.usecases.AtenderTicketUseCase;
import br.com.academiaDev.application.usecases.AtualizarProgressoUseCase;
import br.com.academiaDev.application.usecases.CancelarMatriculaUseCase;
import br.com.academiaDev.application.usecases.ConsultarCatalogoUseCase;
import br.com.academiaDev.application.usecases.ConsultarMatriculasUseCase;
import br.com.academiaDev.application.usecases.ExportarDadosUseCase;
import br.com.academiaDev.application.usecases.GerarRelatoriosUseCase;
import br.com.academiaDev.application.usecases.GerenciarPlanoAlunoUseCase;
import br.com.academiaDev.application.usecases.GerenciarStatusCursoUseCase;
import br.com.academiaDev.application.usecases.LoginUseCase;
import br.com.academiaDev.application.usecases.MatricularAlunoUseCase;
import br.com.academiaDev.domain.entities.Course;
import br.com.academiaDev.domain.enums.DifficultyLevel;
import br.com.academiaDev.infrastructure.persistence.CourseRepositoryInMemory;
import br.com.academiaDev.infrastructure.persistence.EnrollmentRepositoryInMemory;
import br.com.academiaDev.infrastructure.persistence.SupportTicketQueueInMemory;
import br.com.academiaDev.infrastructure.persistence.UserRepositoryInMemory;
import br.com.academiaDev.infrastructure.ui.ConsoleController;
import br.com.academiaDev.infrastructure.ui.menu.AdminMenuHandler;
import br.com.academiaDev.infrastructure.ui.menu.StudentMenuHandler;

public class AcademiaDev {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        CourseRepositoryInMemory courseRepo = new CourseRepositoryInMemory();
        UserRepositoryInMemory userRepo = new UserRepositoryInMemory();
        EnrollmentRepositoryInMemory enrollmentRepo = new EnrollmentRepositoryInMemory();
        SupportTicketQueueInMemory ticketQueueRepo = new SupportTicketQueueInMemory();

        inicializarDados(courseRepo);

        LoginUseCase loginUseCase = new LoginUseCase(userRepo);

        userRepo.save(new br.com.academiaDev.domain.entities.Admin("Diretor Skinner", "admin@email.com"));

        userRepo.save(new br.com.academiaDev.domain.entities.Student(
        "Bart Simpson", 
        "aluno@email.com", 
        new br.com.academiaDev.domain.entities.BasicPlan()
    ));

        MatricularAlunoUseCase matricularAlunoUseCase = new MatricularAlunoUseCase(courseRepo, enrollmentRepo, userRepo);
        AtualizarProgressoUseCase atualizarProgressoUseCase = new AtualizarProgressoUseCase(enrollmentRepo);
        ConsultarMatriculasUseCase consultarMatriculasUseCase = new ConsultarMatriculasUseCase(enrollmentRepo);
        CancelarMatriculaUseCase cancelarMatriculaUseCase = new CancelarMatriculaUseCase(enrollmentRepo);
        
        GerenciarStatusCursoUseCase gerenciarStatusCursoUseCase = new GerenciarStatusCursoUseCase(courseRepo);
        GerenciarPlanoAlunoUseCase gerenciarPlanoAlunoUseCase = new GerenciarPlanoAlunoUseCase();
        ConsultarCatalogoUseCase consultarCatalogoUseCase = new ConsultarCatalogoUseCase(courseRepo);
        
        GerarRelatoriosUseCase gerarRelatoriosUseCase = new GerarRelatoriosUseCase(courseRepo, enrollmentRepo);
        ExportarDadosUseCase exportarDadosUseCase = new ExportarDadosUseCase(courseRepo);
        
        AbrirTicketUseCase abrirTicketUseCase = new AbrirTicketUseCase(ticketQueueRepo);
        AtenderTicketUseCase atenderTicketUseCase = new AtenderTicketUseCase(ticketQueueRepo);

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

        ConsoleController consoleController = new ConsoleController(
            loginUseCase, 
            studentMenu, 
            adminMenu
        );
        
        consoleController.start();
    }

    private static void inicializarDados(CourseRepositoryInMemory courseRepo) {
        courseRepo.save(new Course("Java Clean Arch", "Curso Avançado", "Uncle Bob", 40, DifficultyLevel.ADVANCED));
        courseRepo.save(new Course("Lógica de Programação", "Iniciante", "Guanabara", 20, DifficultyLevel.BEGINNER));
        courseRepo.save(new Course("Spring Boot", "Intermediário", "Michelli Brito", 30, DifficultyLevel.INTERMEDIATE));
        System.out.println("Sistema inicializado com 3 cursos pré-cadastrados.");
    }
}