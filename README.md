# AcademiaDev - Protótipo Clean Architecture
# Alunos: Raul Melo Farias GU3046923 & Rodrigo Quadrante Freitas GU3042073

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