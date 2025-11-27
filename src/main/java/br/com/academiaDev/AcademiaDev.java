/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package br.com.academiaDev;

import java.util.Scanner;

import br.com.academiaDev.application.usecases.AtualizarProgressoUseCase;
import br.com.academiaDev.application.usecases.AbrirTicketUseCase;
import br.com.academiaDev.application.usecases.GerarRelatoriosUseCase;
import br.com.academiaDev.application.usecases.MatricularAlunoUseCase;
import br.com.academiaDev.application.usecases.AtenderTicketUseCase;
import br.com.academiaDev.application.usecases.GerenciarStatusCursoUseCase;
import br.com.academiaDev.application.usecases.LoginUseCase;
import br.com.academiaDev.application.usecases.ConsultarMatriculasUseCase;
import br.com.academiaDev.application.usecases.CancelarMatriculaUseCase;
import br.com.academiaDev.application.usecases.ConsultarCatalogoUseCase;
import br.com.academiaDev.application.usecases.GerenciarPlanoAlunoUseCase;

import br.com.academiaDev.infrastructure.persistence.CourseRepositoryInMemory;
import br.com.academiaDev.infrastructure.persistence.UserRepositoryInMemory;
import br.com.academiaDev.infrastructure.persistence.EnrollmentRepositoryEmMemoria;
import br.com.academiaDev.infrastructure.persistence.SupportTicketQueueEmMemoria;
import br.com.academiaDev.infrastructure.ui.ConsoleController;
import br.com.academiaDev.infrastructure.ui.menu.AdminMenuHandler;
import br.com.academiaDev.infrastructure.ui.menu.StudentMenuHandler;

/**
 *
 * @author raul.farias
 */
public class AcademiaDev {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseRepositoryInMemory courseRepo = new CourseRepositoryInMemory();
        UserRepositoryInMemory userRepo = new UserRepositoryInMemory();
        EnrollmentRepositoryEmMemoria enrollmentRepo = new EnrollmentRepositoryEmMemoria();
        SupportTicketQueueEmMemoria ticketQueueRepo = new SupportTicketQueueEmMemoria();

        AtualizarProgressoUseCase atualizarProgressoUseCase = new AtualizarProgressoUseCase(userRepo, enrollmentRepo);
        AbrirTicketUseCase abrirTicketUseCase = new AbrirTicketUseCase(ticketQueueRepo);
        GerarRelatoriosUseCase gerarRelatoriosUseCase = new GerarRelatoriosUseCase(courseRepo, enrollmentRepo);
        MatricularAlunoUseCase matricularAlunoUseCase = new MatricularAlunoUseCase(courseRepo, userRepo, enrollmentRepo);
        AtenderTicketUseCase atenderTicketUseCase = new AtenderTicketUseCase(ticketQueueRepo);
        GerenciarStatusCursoUseCase gerenciarStatusCursoUseCase = new GerenciarStatusCursoUseCase(courseRepo);
        /*
            ConsultarMatriculasUseCase consultarMatriculasUseCase = new ConsultarMatriculasUseCase(userRepo, enrollmentRepo)
            CancelarMatriculaUseCase cancelarMatriculaUseCase = new CancelarMatriculaUseCase(userRepo, enrollmentRepo)
            ConsultarCatalogoUseCase consultarCatalogoUseCase = new ConsultarCatalogoUseCase(courseRepo)
            GerenciarPlanoAlunoUseCase gerenciarPlanoAlunoUseCase = new GerenciarPlanoAlunoUseCase(userRepo)
        */
        LoginUseCase loginUseCase = new LoginUseCase(userRepository);

        StudentMenuHandler studentMenu = new StudentMenuHandler(
            scanner, 
            matricularAlunoUseCase, 
            atualizarProgressoUseCase, 
            abrirTicketUseCase
        );

        AdminMenuHandler adminMenu = new AdminMenuHandler(
            scanner, 
            gerenciarStatusCursoUseCase, 
            atenderTicketUseCase,
            gerarRelatoriosUseCase,
            abrirTicketUseCase
        );

        ConsoleController consoleController = new ConsoleController(
            loginUseCase, 
            studentMenu, 
            adminMenu
        );
        consoleController.start();
        
        scanner.close();
    }
}
