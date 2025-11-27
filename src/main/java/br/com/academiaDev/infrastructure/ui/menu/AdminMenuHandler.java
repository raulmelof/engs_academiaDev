package br.com.academiaDev.infrastructure.ui.menu;

import br.com.academiaDev.application.usecases.GerenciarStatusCursoUseCase;
import br.com.academiaDev.application.usecases.AtenderTicketUseCase;
import br.com.academiaDev.application.usecases.GerarRelatoriosUseCase;
import br.com.academiaDev.application.usecases.AbrirTicketUseCase;
/*import br.com.academiaDev.application.usecases.ConsultarCatalogoUseCase;
import br.com.academiaDev.application.usecases.GerenciarPlanoAlunoUseCase;*/
import java.util.Scanner;

public class AdminMenuHandler implements MenuHandler {
    private final Scanner scanner;
    private final GerenciarStatusCursoUseCase gerenciarStatusCurso;
    private final AtenderTicketUseCase atenderTicket;
    private final GerarRelatoriosUseCase gerarRelatorios;
    private final AbrirTicketUseCase abrirTicket;

    public AdminMenuHandler(
        Scanner scanner,
        GerenciarStatusCursoUseCase gerenciarStatusCurso,
        AtenderTicketUseCase atenderTicket,
        GerarRelatoriosUseCase gerarRelatorios,
        AbrirTicketUseCase abrirTicket
    ) {
        this.scanner = scanner;
        this.gerenciarStatusCurso = gerenciarStatusCurso;
        this.atenderTicket = atenderTicket;
        this.gerarRelatorios = gerarRelatorios;
        this.abrirTicket = abrirTicket;
    }

    @Override
    public void exibirMenu() {
        System.out.println("\nDigite:");
        System.out.println("1 para Gerenciar Status de Curso,");
        System.out.println("2 para Gerenciar Planos de Alunos,");
        System.out.println("3 para Atender Ticket,");
        System.out.println("4 para Gerar Relatórios,");
        System.out.println("5 para Exportar Dados (CSV),");
        System.out.println("6 para Consultar Catálogo de Cursos,");
        System.out.println("7 para Abrir Ticket de Suporte,");
        System.out.println("8 para Sair.");
        System.out.print("Sua escolha: ");
    }

    @Override
    public void handleEscolha(String escolha) {
        switch (escolha) {
            case "1": gerenciarStatusCurso(); break;
            case "2": gerenciarPlanoAluno(); break;
            case "3": atenderTicket(); break;
            case "4": gerarRelatorios(); break;
            case "5": exportarCSV(); break;
            case "6": consultarCatalogo(); break;
            case "7": abrirTicket(); break;
            case "8": System.exit(0);
            default: System.out.println("Escolha inválida!");
        }
    }

    private void gerenciarStatusCurso() {
        System.out.print("Digite o título do curso: ");
        String courseTitle = scanner.nextLine();

        System.out.print("Digite 0 para INACTIVE ou 1 para ACTIVE: ");
        String escolha = scanner.nextLine().trim();

        boolean active;
        if ("1".equals(escolha)) {
            active = true;
        } else if ("0".equals(escolha)) {
            active = false;
        } else {
            System.out.println("Opção inválida!");
            return;
        }

        gerenciarStatusCurso.execute(courseTitle, active);
        System.out.println("Status atualizado com sucesso.");
    }

    private void gerenciarPlanoAluno() {
        System.out.print("Digite o email do aluno: ");
        String studentEmail = scanner.nextLine();

        System.out.print("Digite 0 para Básico ou 1 para Premium: ");
        String escolha = scanner.nextLine().trim();

        String newPlan;
        if ("0".equals(escolha)) {
            newPlan = "Básico";
        } else if ("1".equals(escolha)) {
            newPlan = "Premium";
        } else {
            System.out.println("Opção inválida!");
            return;
        }
        //Precisaria do seu próprio UseCase
        gerenciarPlanoAluno.execute(studentEmail, newPlan);
        System.out.println("Plano do aluno atualizado para " + newPlan + " com sucesso.");
    }

    private void atenderTicket() {
        if (atenderTicket.execute() != null) {
            System.out.println("Ticket atendido com sucesso.");
        } else {
            System.out.println("Não há tickets na fila.");
        }
    }

    private void gerarRelatorios() {
        System.out.println("\nCursos por Dificuldade: ");
        System.out.println(gerarRelatorios.getCoursesByDifficulty());
        System.out.println("\nInstrutores Únicos: ");
        System.out.println(gerarRelatorios.getUniqueInstructors());
        System.out.println("\nAlunos por Plano: ");
        System.out.println(gerarRelatorios.getStudentsByPlan());
    }

    private void exportarCSV() {
        exportarCSV.execute();
        System.out.println("Dados exportados para CSV com sucesso.");
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