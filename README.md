# Locadora Spring Boot

📦 Projeto acadêmico para gestão de locadora de veículos com estrutura orientada a objetos, usando Java e Spring Boot.

## 🚗 Sobre o Projeto

Este projeto simula uma locadora de veículos com suporte a diferentes tipos de veículos (motos, carros, caminhões e ônibus), cada um com suas próprias regras de cálculo de seguro, depreciação e valor de aluguel.

O sistema utiliza:

- Java 17+
- Spring Boot
- Spring Data JPA
- MySQL

## 🔧 Funcionalidades

- Cadastro e consulta de veículos por tipo
- Cadastro e gerenciamento de clientes
- Aluguel e devolução de veículos
- Cálculo de aluguel com base no tipo do veículo
- Cálculo de seguro individual por tipo
- Depreciação e reajuste de diárias

## 🧱 Estrutura:

- `controller/` – Endpoints da API
- `model/` – Entidades: `Cliente`, `Veiculo`, `Aluguel`, etc.
- `repository/` – Interfaces JPA para persistência
- `dto/` – Objetos de transferência (requests/responses)
- `service/` – Regras de negócio (`*Service`)

## 🗄️ Banco de Dados

- Utiliza MySQL
- As tabelas principais incluem: `cliente`, `veiculo`, `aluguel`
