# AcademiaDev - Protótipo Clean Architecture

Este projeto implementa o protótipo da plataforma de cursos "AcademiaDev", focando estritamente nos princípios da **Clean Architecture** e **SOLID**.

## 🏗️ Decisões de Arquitetura

O sistema foi dividido em 4 camadas concêntricas, respeitando rigorosamente a **Regra da Dependência**: o código das camadas internas não conhece nada das camadas externas.

### 1. Domain (Núcleo)
* **Responsabilidade:** Contém as Entidades (`Course`, `Student`, `Enrollment`) e regras de negócio puras (ex: validação se um plano permite matrícula).
* **Pureza:** Este pacote não possui dependências de bibliotecas externas, frameworks ou camadas superiores.
* **Decisão de Design:** Utilizamos `Enums` para `DifficultyLevel` e `CourseStatus` para garantir integridade dos dados sem validações complexas de strings.

### 2. Application (Casos de Uso)
* **Responsabilidade:** Orquestra o fluxo de dados entre o usuário e o domínio.
* **Abstração:** Define interfaces de Repositórios (`CourseRepository`, `UserRepository`) mas **não os implementa**.
* **Decisão de Design:** Utilizamos o padrão **Command** para os UseCases (ex: `MatricularAlunoUseCase`), onde cada classe tem uma única responsabilidade pública. A lógica de relatórios complexos com **Java Streams** reside aqui.

### 3. Infrastructure (Detalhes)
* **Responsabilidade:** Implementa as interfaces definidas na camada `Application` e lida com I/O.
* **Persistência:** Implementada em memória (`persistence`) utilizando `Map` para garantir unicidade (O(1) para busca) e `Queue` para a fila de suporte (FIFO), conforme requisitos.
* **Interface (UI):** O `ConsoleController` interage com o usuário via CLI.
* **Utils (Reflection):** O `GenericCsvExporter` utiliza **Java Reflection** para gerar CSVs de qualquer lista de objetos dinamicamente, isolando esse "detalhe técnico" da lógica de negócio.

### 4. Main (Injeção de Dependência)
* **Responsabilidade:** Ponto de entrada ("Entry Point").
* **Injeção de Dependência:** É a única classe que "conhece tudo". Ela instancia as implementações concretas de `Infrastructure` e as injeta nos construtores dos `UseCases`.

---

## recados

seguinte, ja adiantei a configuração do projeto, to usando **Maven** e **Java 21**, então certifica que tá rodando com essa versão.

### Divisão do Trabalho

**Minha Parte (O que eu tô fazendo):**
*  Tô criando as regras de `Course`, `Enrollment` e a lógica dos `SubscriptionPlans` (aquela parte de limitar matrícula no plano básico).
*  vo fazer tambem a parte de Relatórios usando **Streams** e a exportação dinâmica de CSV usando **Reflection** (pra não precisar ficar mexendo em string na mão).
*  Já defini as interfaces `CourseRepository` e `EnrollmentRepository`.

**Sua Parte (O que precisa ser feito):**
* **Gestão de Usuários:** Cria as entidades `User`, `Admin` e `Student`.
* **Suporte:** Precisamos da Fila de Tickets (`SupportTicket`). Lembra que tem que ser FIFO (Fila mesmo).
* **O Menu (UI):** Faz o `ConsoleController`. É aquele `switch/case` pro usuário navegar. Você vai chamar os meus UseCases lá.
* **Main:** No final, você cria o `Main.java`. Você vai instanciar os repositórios em memória e passar pro Controller.

Qualquer dúvida olha o diagrama UML que tá na raiz do projeto (`diagrama.plantuml`). Pra rodar ele precisa da extensão plantuml ai so dar Alt + D.

Criei uma entidade Student mas essa parte é sua, criei so o basico pra meu cosigo nao quebrar