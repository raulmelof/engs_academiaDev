package br.com.academiaDev.infrastructure.ui.menu;

import br.com.academiaDev.application.usecases.MatricularAlunoUseCase;
import br.com.academiaDev.application.usecases.AtualizarProgressoUseCase;
import br.com.academiaDev.application.usecases.AbrirTicketUseCase;
/*import br.com.academiaDev.application.usecases.CancelarMatriculaUseCase;
import br.com.academiaDev.application.usecases.ConsultarCatalogoUseCase;
import br.com.academiaDev.application.usecases.ConsultarMatriculasUseCase;*/
import java.util.Scanner;

public class StudentMenuHandler implements MenuHandler {
    private final Scanner scanner;
    private final MatricularAlunoUseCase matricularAluno;
    private final AtualizarProgressoUseCase atualizarProgresso;
    private final AbrirTicketUseCase abrirTicket;

    public StudentMenuHandler(
        Scanner scanner,
        MatricularAlunoUseCase matricularAluno,
        AtualizarProgressoUseCase atualizarProgresso,
        AbrirTicketUseCase abrirTicket
    ) {
        this.scanner = scanner;
        this.matricularAluno = matricularAluno;
        this.atualizarProgresso = atualizarProgresso;
        this.abrirTicket = abrirTicket;
    }

    @Override
    public void exibirMenu() {
        System.out.println("\nDigite:");
        System.out.println("1 para Matricular-se em Curso,");
        System.out.println("2 para Consultar Matrículas,");
        System.out.println("3 para Atualizar Progresso,");
        System.out.println("4 para Cancelar Matrícula,");
        System.out.println("5 para Consultar Catálogo de Cursos,");
        System.out.println("6 para Abrir Ticket de Suporte,");
        System.out.println("7 para Sair.");
        System.out.print("Sua escolha: ");
    }

    @Override
    public void handleEscolha(String escolha) {
        switch (escolha) {
            case "1": matricularAluno(); break;
            case "2": consultarMatriculas(); break;
            case "3": atualizarProgresso(); break;
            case "4": cancelarMatricula(); break;
            case "5": consultarCatalogo(); break;
            case "6": abrirTicket(); break;
            case "7": System.exit(0);
            default: System.out.println("Escolha inválida!");
        }
    }

    private void matricularAluno() {
        System.out.print("Digite o email do aluno: ");
        String emailStudent = scanner.nextLine();
        System.out.print("Digite o título do curso: ");
        String courseTitle = scanner.nextLine();

        matricularAluno.execute(emailStudent, courseTitle);
    }

    private void consultarMatriculas() {
        System.out.print("Digite o email do aluno: ");
        String emailStudent = scanner.nextLine();
        //Precisaria do seu próprio UseCase
        consultarMatriculas.execute(emailStudent);
    }

    private void atualizarProgresso() {
        System.out.print("Digite o email do aluno: ");
        String emailStudent = scanner.nextLine();
        System.out.print("Digite o título do curso: ");
        String courseTitle = scanner.nextLine();
        System.out.print("Digite a nova quantidade de progresso: ");
        String newProgress = scanner.nextLine();

        atualizarProgresso.execute(emailStudent, courseTitle, newProgress);
    }

    private void cancelarMatricula() {
        System.out.print("Digite o email do aluno: ");
        String emailStudent = scanner.nextLine();
        System.out.print("Digite o título do curso: ");
        String courseTitle = scanner.nextLine();
        //Precisaria do seu próprio UseCase
        cancelarMatricula.execute(emailStudent, courseTitle);
    }

    private void consultarCatalogo() {
        //Precisaria do seu próprio UseCase
        consultarCatalogo.execute()
    }

    private void abrirTicket() {
        System.out.print("Digite o email do usuário: ");
        String userEmail = scanner.nextLine();
        System.out.print("Digite o título do ticket: ");
        String title = scanner.nextLine();
        System.out.print("Digite a mensagem do ticket: ");
        String message = scanner.nextLine();

        abrirTicket.execute(userEmail, title, message);
    }
}