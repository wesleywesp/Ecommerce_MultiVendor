# Ecommerce Multi Vendor

## Descrição

O **Ecommerce Multi Vendor** é uma aplicação desenvolvida para permitir a interação entre clientes, vendedores e administradores em uma plataforma de e-commerce. A aplicação suporta múltiplos vendedores, possibilitando gerenciamento de produtos, pedidos, pagamentos e relatórios, com uma interface moderna e responsiva.

---

## Ferramentas Utilizadas

- **IDE**:
    - IntelliJ IDEA
    - Visual Studio Code
- **Banco de Dados**:
    - MySQL

---

## Tecnologias

### Backend
- **Spring Boot**
- **Spring Security**
- **Java Mail Sender**
- **JWT**

### Frontend
- **React** com **TypeScript**
- **Redux Toolkit**
- **Material-UI (MUI)**
- **Tailwind CSS**
- **React Chart**
- **Formik**
- **Yup**
- **React Router DOM**
- **Axios**

### Gateway de Pagamento
- **MB Way** (para  portugueses)
- **PayPal** (para  brasileiros)
- **Stripe** (para  internacionais)

---

## Funcionalidades

### Funcionalidades para Clientes
- **Chatbot para Consultas**:
    - Histórico de Pedidos
    - Itens no Carrinho
    - Detalhes de Produtos
- **Gerenciamento de Produtos**:
    - Listagem de produtos com paginação, filtros e ordenação.
    - Exibição de detalhes do produto.
- **Carrinho de Compras**:
    - Adicionar, atualizar ou remover itens.
- **Processo de Checkout**:
    - Aplicação de cupons de desconto.
    - Gerenciamento de endereços de entrega.
    - Integração com gateways de pagamento.
- **Histórico de Pedidos**:
    - Exibição de pedidos anteriores e detalhes.
    - Cancelamento de pedidos.
- **Conta de Usuário**:
    - Gerenciamento de informações pessoais.
    - Avaliações e avaliações de produtos.
    - Gerenciamento de lista de desejos.

### Funcionalidades para Vendedores
- **Dashboard de Vendas**:
    - Gráficos de ganhos (diários, semanais, anuais).
    - Relatórios de vendas, devoluções e cancelamentos.
- **Gerenciamento de Produtos e Pedidos**:
    - Criação de produtos.
    - Monitoramento e atualização de pedidos.
- **Gestão Financeira**:
    - Histórico de pagamentos e transações.
- **Gerenciamento de Perfil do Vendedor**.

### Funcionalidades para Administradores
- **Dashboard Administrativo**:
    - Gerenciamento de vendedores (aprovações, suspensões).
    - Gerenciamento de cupons e promoções.
    - Personalização da página inicial.
    - Criação e gerenciamento de ofertas promocionais.

---

## Relacionamentos das Entidades

### Principais Entidades
- **Usuário**: Relacionado com Endereço, Carrinho, Pedidos, Avaliações, Transações e Lista de Desejos.
- **Produto**: Relacionado com Categoria, Vendedor e Avaliações.
- **Pedido**: Relacionado com Usuário, Endereço, Itens do Pedido e Transações.
- **Vendedor**: Relacionado com Produtos, Transações e Relatórios de Vendas.

---

## Conexão entre Frontend e Backend

1. **Interação do Usuário**: Ações no React (ex: cliques em botões).
2. **Chamada de API**: Envio de requisições para o backend.
3. **Processamento no Backend**: Manipulação de dados e resposta.
4. **Resposta da API**: Dados retornados para o React.
5. **Armazenamento de Dados**: Uso de `useState` para gerenciar estado.
6. **Renderização**: Atualização da interface com os dados recebidos.

---

## Etapas do Projeto

1. Configurar o banco de dados MySQL.
2. Criar a primeira API.
3. Definir modelos de entidades.
4. Implementar autenticação com Spring Security (login e cadastro).
5. Criar serviços e implementações.
6. Desenvolver controladores para endpoints.
7. Testar todas as APIs.

---

Este projeto oferece uma abordagem completa para o desenvolvimento de uma plataforma de e-commerce multi-vendedor, com uma integração robusta entre backend e frontend, garantindo funcionalidades modernas e uma experiência de usuário satisfatória.
