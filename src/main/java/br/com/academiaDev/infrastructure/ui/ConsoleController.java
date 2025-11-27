package br.com.academiaDev.infrastructure.ui;

import java.util.Scanner;

import br.com.academiaDev.application.usecases.LoginUseCase;
import br.com.academiaDev.application.usecases.UserRole;
import br.com.academiaDev.infrastructure.ui.menu.AdminMenuHandler;
import br.com.academiaDev.infrastructure.ui.menu.MenuHandler;
import br.com.academiaDev.infrastructure.ui.menu.StudentMenuHandler;

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

            if (null == role) {
                System.out.println("Acesso negado.");
                return;
            } else switch (role) {
                case ADMIN -> currentMenuStrategy = adminMenu;
                case STUDENT -> currentMenuStrategy = studentMenu;
                default -> {
                    System.out.println("Acesso negado.");
                    return;
                }
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