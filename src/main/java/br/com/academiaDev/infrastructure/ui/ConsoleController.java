package br.com.academiaDev.infrastructure.ui;

import br.com.academiaDev.application.usecases.LoginUseCase;
import br.com.academiaDev.application.usecases.UserRole;
import br.com.academiaDev.infrastructure.ui.menu.AdminMenuHandler;
import br.com.academiaDev.infrastructure.ui.menu.StudentMenuHandler;
import br.com.academiaDev.infrastructure.ui.menu.MenuHandler;
import java.util.Scanner;

public class ConsoleController {
    private final Scanner scanner = new Scanner(System.in);
    
    private final LoginUseCase loginUseCase;
    private final StudentMenuHandler studentMenu;
    private final AdminMenuHandler adminMenu;

    public ConsoleController(
        LoginUseCase loginUseCase,
        StudentMenuHandler studentMenu, 
        AdminMenuHandler adminMenu
    ) {
        this.loginUseCase = loginUseCase;
        this.studentMenu = studentMenu;
        this.adminMenu = adminMenu;
    }

    public void start() {
        System.out.println("Bem-vindo ao Sistema!");
        System.out.print("Digite seu email para login: ");
        String email = scanner.nextLine();

        try {
            UserRole role = loginUseCase.execute(email);

            MenuHandler currentMenuStrategy;

            if (role == UserRole.ADMIN) {
                currentMenuStrategy = adminMenu;
            } else if (role == UserRole.STUDENT) {
                currentMenuStrategy = studentMenu;
            } else {
                System.out.println("Acesso negado.");
                return;
            }

            runMenuLoop(currentMenuStrategy);

        } catch (Exception e) {
            System.out.println("Erro no login: " + e.getMessage());
        }
    }

    private void runMenuLoop(MenuHandler strategy) {
        while (true) {
            strategy.exibirMenu();
            String escolha = scanner.nextLine();
            strategy.handleEscolha(escolha);
        }
    }
}