package br.com.academiaDev.infrastructure.ui.menu;

import br.com.academiaDev.application.usecases.MatricularAlunoUseCase;
import br.com.academiaDev.application.usecases.AtualizarProgressoUseCase;
import br.com.academiaDev.application.usecases.AbrirTicketUseCase;
import br.com.academiaDev.application.usecases.CancelarMatriculaUseCase;
import br.com.academiaDev.application.usecases.ConsultarCatalogoUseCase;
import br.com.academiaDev.application.usecases.ConsultarMatriculasUseCase;
import java.util.Scanner;

public class StudentMenuHandler implements MenuHandler {
    private final Scanner scanner;
    private final MatricularAlunoUseCase matricularAluno;
    private final AtualizarProgressoUseCase atualizarProgresso;
    private final AbrirTicketUseCase abrirTicket;
    private final ConsultarMatriculasUseCase consultarMatriculas;
    private final CancelarMatriculaUseCase cancelarMatricula;
    private final ConsultarCatalogoUseCase consultarCatalogo;

    public StudentMenuHandler(
        Scanner scanner,
        MatricularAlunoUseCase matricularAluno,
        AtualizarProgressoUseCase atualizarProgresso,
        AbrirTicketUseCase abrirTicket,
        ConsultarMatriculasUseCase consultarMatriculas,
        CancelarMatriculaUseCase cancelarMatricula,
        ConsultarCatalogoUseCase consultarCatalogo
    ) {
        this.scanner = scanner;
        this.matricularAluno = matricularAluno;
        this.atualizarProgresso = atualizarProgresso;
        this.abrirTicket = abrirTicket;
        this.consultarMatriculas = consultarMatriculas;
        this.cancelarMatricula = cancelarMatricula;
        this.consultarCatalogo = consultarCatalogo;
    }

    @Override
    public void exibirMenu() {
        System.out.println("\n--- MENU DO ALUNO ---");
        System.out.println("1. Matricular-se em Curso");
        System.out.println("2. Consultar Minhas Matrículas");
        System.out.println("3. Atualizar Progresso");
        System.out.println("4. Cancelar Matrícula");
        System.out.println("5. Consultar Catálogo de Cursos");
        System.out.println("6. Abrir Ticket de Suporte");
        System.out.println("7. Sair");
        System.out.print("Sua escolha: ");
    }

    @Override
    public void handleEscolha(String escolha) {
        switch (escolha) {
            case "1" -> matricularAluno();
            case "2" -> consultarMatriculas();
            case "3" -> atualizarProgresso();
            case "4" -> cancelarMatricula();
            case "5" -> consultarCatalogo();
            case "6" -> abrirTicket();
            case "7" -> System.exit(0);
            default -> System.out.println("Escolha inválida!");
        }
    }

    private void matricularAluno() {
        System.out.print("Digite o email do aluno: ");
        String emailStudent = scanner.nextLine();
        System.out.print("Digite o título do curso: ");
        String courseTitle = scanner.nextLine();

        try {
            matricularAluno.execute(emailStudent, courseTitle);
            System.out.println("Matrícula realizada com sucesso!");
        } catch (Exception e) {
            System.out.println("Erro ao matricular: " + e.getMessage());
        }
    }

    private void consultarMatriculas() {
        System.out.print("Digite o email do aluno: ");
        String emailStudent = scanner.nextLine();
        consultarMatriculas.execute(emailStudent);
    }

    private void atualizarProgresso() {
        System.out.print("Digite o email do aluno: ");
        String emailStudent = scanner.nextLine();
        System.out.print("Digite o título do curso: ");
        String courseTitle = scanner.nextLine();
        System.out.print("Digite a nova porcentagem (0-100): ");
        String progressStr = scanner.nextLine();

        try {
            int newProgress = Integer.parseInt(progressStr);
            atualizarProgresso.execute(emailStudent, courseTitle, newProgress);
            System.out.println("Progresso atualizado!");
        } catch (NumberFormatException e) {
            System.out.println("Por favor, digite um número válido.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void cancelarMatricula() {
        System.out.print("Digite o email do aluno: ");
        String emailStudent = scanner.nextLine();
        System.out.print("Digite o título do curso: ");
        String courseTitle = scanner.nextLine();
        cancelarMatricula.execute(emailStudent, courseTitle);
    }

    private void consultarCatalogo() {
        consultarCatalogo.execute();
    }

    private void abrirTicket() {
        System.out.print("Digite seu email: ");
        String userEmail = scanner.nextLine();
        System.out.print("Título do problema: ");
        String title = scanner.nextLine();
        System.out.print("Descrição detalhada: ");
        String message = scanner.nextLine();

        abrirTicket.execute(userEmail, title, message);
    }
}