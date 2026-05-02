# hotel-management-system
Este é um sistema de gestão hoteleira desenvolvido em Java com interface gráfica JavaFX. O projeto foi desenhado para gerir as operações fundamentais de um hotel, desde o registo de clientes e quartos até ao controlo de reservas e fluxos de check-in/out.

O desenvolvimento deste projeto focou-se na aplicação prática de conceitos avançados de Programação Orientada a Objetos (POO) e arquitetura de software explorados em contexto académico.

 Funcionalidades Principais
Gestão de Clientes: Registo completo de hóspedes com validação de dados (NIF, Email, Telefone).

Gestão de Quartos: Suporte para diferentes tipos de alojamento (Simples, Double, Suite) através de herança e classes abstratas.

Sistema de Reservas: Motor de reservas que verifica a disponibilidade de quartos e valida datas de entrada/saída.

Fluxo de Operações: Controlo de estado de reserva, permitindo realizar Check-in, Check-out e processamento de pagamentos.

Interface Gráfica: UI moderna e intuitiva construída com JavaFX, utilizando layouts dinâmicos como BorderPane e VBox.

Conceitos de Programação Aplicados
Este projeto serve como demonstração prática dos seguintes tópicos:

Herança e Polimorfismo: Utilização de classes abstratas (Pessoa, Quarto) e especializações para reutilização de código e comportamento polimórfico.

Tratamento de Exceções: Implementação de uma hierarquia de exceções customizadas para gerir erros de negócio (ex: QuartoIndisponivelException).

Coleções (ArrayList): Gestão dinâmica de dados em memória utilizando a API de Collections do Java.

Encapsulamento e Validação: Proteção de dados através de modificadores de acesso e lógica de validação centralizada.

 Tecnologias Utilizadas
Java 17+: Linguagem base.

JavaFX: Framework para a interface visual.

Maven: Gestão de dependências e construção do projeto.

 Estrutura do Projeto
O código está organizado seguindo uma separação clara de responsabilidades:

hotel.model: Classes de dados (Entidades).

hotel.service: Lógica de negócio e regras do sistema.

hotel.ui: Componentes da interface gráfica.

hotel.exception: Definições de erros específicos.

hotel.util: Ferramentas auxiliares e validadores.

