package br.com.academiaDev.infrastructure.ui.menu;

import br.com.academiaDev.application.usecases.GerenciarStatusCursoUseCase;
import br.com.academiaDev.application.usecases.AtenderTicketUseCase;
import br.com.academiaDev.application.usecases.GerarRelatoriosUseCase;
import br.com.academiaDev.application.usecases.AbrirTicketUseCase;
import br.com.academiaDev.application.usecases.ConsultarCatalogoUseCase;
import br.com.academiaDev.application.usecases.GerenciarPlanoAlunoUseCase;
import br.com.academiaDev.application.usecases.ExportarDadosUseCase;
import br.com.academiaDev.domain.enums.CourseStatus;
import java.util.Scanner;

public class AdminMenuHandler implements MenuHandler {
    private final Scanner scanner;
    private final GerenciarStatusCursoUseCase gerenciarStatusCurso;
    private final AtenderTicketUseCase atenderTicket;
    private final GerarRelatoriosUseCase gerarRelatorios;
    private final AbrirTicketUseCase abrirTicket;
    private final ExportarDadosUseCase exportarDados;
    private final ConsultarCatalogoUseCase consultarCatalogo;
    private final GerenciarPlanoAlunoUseCase gerenciarPlanoAluno;

    public AdminMenuHandler(
        Scanner scanner,
        GerenciarStatusCursoUseCase gerenciarStatusCurso,
        AtenderTicketUseCase atenderTicket,
        GerarRelatoriosUseCase gerarRelatorios,
        AbrirTicketUseCase abrirTicket,
        ExportarDadosUseCase exportarDados,
        ConsultarCatalogoUseCase consultarCatalogo,
        GerenciarPlanoAlunoUseCase gerenciarPlanoAluno
    ) {
        this.scanner = scanner;
        this.gerenciarStatusCurso = gerenciarStatusCurso;
        this.atenderTicket = atenderTicket;
        this.gerarRelatorios = gerarRelatorios;
        this.abrirTicket = abrirTicket;
        this.exportarDados = exportarDados;
        this.consultarCatalogo = consultarCatalogo;
        this.gerenciarPlanoAluno = gerenciarPlanoAluno;
    }

    @Override
    public void exibirMenu() {
        System.out.println("\n--- MENU ADMINISTRADOR ---");
        System.out.println("1. Gerenciar Status de Curso");
        System.out.println("2. Gerenciar Planos de Alunos");
        System.out.println("3. Atender Ticket da Fila");
        System.out.println("4. Gerar Relatórios do Sistema");
        System.out.println("5. Exportar Dados (CSV)");
        System.out.println("6. Consultar Catálogo");
        System.out.println("7. Abrir Ticket (Como Admin)");
        System.out.println("8. Sair");
        System.out.print("Sua escolha: ");
    }

    @Override
    public void handleEscolha(String escolha) {
        switch (escolha) {
            case "1" -> gerenciarStatusCurso();
            case "2" -> gerenciarPlanoAluno();
            case "3" -> atenderTicket();
            case "4" -> gerarRelatorios();
            case "5" -> exportarCSV();
            case "6" -> consultarCatalogo();
            case "7" -> abrirTicket();
            case "8" -> System.exit(0);
            default -> System.out.println("Escolha inválida!");
        }
    }

    private void gerenciarStatusCurso() {
        System.out.print("Digite o título do curso: ");
        String courseTitle = scanner.nextLine();

        System.out.print("Digite 0 para INATIVAR ou 1 para ATIVAR: ");
        String escolha = scanner.nextLine().trim();

        CourseStatus status;
        if ("1".equals(escolha)) {
            status = CourseStatus.ACTIVE;
        } else if ("0".equals(escolha)) {
            status = CourseStatus.INACTIVE;
        } else {
            System.out.println("Opção inválida!");
            return;
        }

        try {
            gerenciarStatusCurso.execute(courseTitle, status);
            System.out.println("Status atualizado com sucesso.");
        } catch (Exception e) {
            System.out.println("Erro: " + e.getMessage());
        }
    }

    private void gerenciarPlanoAluno() {
        System.out.print("Digite o email do aluno: ");
        String studentEmail = scanner.nextLine();
        System.out.print("Novo plano (BASIC ou PREMIUM): ");
        String newPlan = scanner.nextLine().trim();
        
        gerenciarPlanoAluno.execute(studentEmail, newPlan);
    }

    private void atenderTicket() {
        if (atenderTicket.execute() != null) {
            System.out.println("Ticket processado e removido da fila.");
        } else {
            System.out.println("Não há tickets na fila.");
        }
    }

    private void gerarRelatorios() {
        System.out.println("\n--- RELATÓRIOS ---");
        System.out.println("1. Cursos por Dificuldade: " + gerarRelatorios.getCoursesByDifficulty());
        System.out.println("2. Instrutores Únicos: " + gerarRelatorios.getUniqueInstructors());
        System.out.println("3. Alunos por Plano: " + gerarRelatorios.getStudentsByPlan());
    }

    private void exportarCSV() {
        exportarDados.execute();
    }

    private void consultarCatalogo() {
        consultarCatalogo.execute();
    }

    private void abrirTicket() {
        System.out.print("Email do Admin: ");
        String userEmail = scanner.nextLine();
        System.out.print("Título: ");
        String title = scanner.nextLine();
        System.out.print("Mensagem: ");
        String message = scanner.nextLine();

        abrirTicket.execute(userEmail, title, message);
    }
}