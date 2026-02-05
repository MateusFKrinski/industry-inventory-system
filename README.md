# Industry Inventory Control System

> Sistema inteligente para controle de estoque de matérias-primas com cálculo de produção otimizada por valor.

## Descrição do Projeto

O **Industry Inventory Control System** é uma aplicação web moderna desenvolvida para otimizar o gerenciamento de inventário em ambientes de manufatura. O sistema permite o cadastro eficiente de produtos e matérias-primas, com cálculos automáticos de produção máxima baseados no estoque disponível e priorização por rentabilidade.

## Funcionalidades Principais

- **Cadastro de Produtos e Matérias-Primas** - Gestão completa com validações
- **Associação de Componentes** - Vincule matérias-primas aos produtos
- **Cálculo de Produção** - Determine a máxima produção possível baseada no estoque
- **Otimização por Valor** - Priorize produtos por rentabilidade
- **Dashboard Intuitivo** - Visualizações em tempo real
- **API RESTful Completa** - Documentação com Swagger/OpenAPI

## Arquitetura Técnica

### Stack Tecnológico

| Componente          | Tecnologia                            |
| ------------------- | ------------------------------------- |
| **Backend**         | Quarkus (Java 17+)                    |
| **Frontend**        | React 18 + Redux Toolkit + TypeScript |
| **Banco de Dados**  | PostgreSQL 14+                        |
| **Containerização** | Docker + Docker Compose               |
| **API Docs**        | OpenAPI 3.0 / Swagger UI              |

## Estrutura do Projeto

```
industry-inventory-system/
├── backend/               # API Quarkus
├── frontend/              # Aplicação
├── docker/                # Configurações Docker
│   └── scripts/
├── docs/                  # Documentação técnica
│   ├── api/
│   ├── database
├── LICENSE
└── README.md
```

## 🚀 Como Executar

### Pré-requisitos

- Docker & Docker Compose
- Git

### Instalação e Execução

```bash
# Clone o repositório
git clone https://github.com/MateusFKrinski/industry-inventory-system.git
cd industry-inventory-system

# Inicie os serviços com Docker Compose
docker-compose up -d

# Aguarde a inicialização
```

### Acessos da Aplicação

| Serviço         | URL                                |
| --------------- | ---------------------------------- |
| **Frontend**    | http://localhost:5173              |
| **Backend API** | http://localhost:8080              |
| **Swagger UI**  | http://localhost:8080/q/swagger-ui |
| **PostgreSQL**  | localhost:5432                     |

## 💻 Desenvolvimento Local

### Backend (Quarkus)

```bash
cd backend

# Build
./mvnw clean package

# Executar em modo dev
./mvnw quarkus:dev
```

### Frontend (React)

```bash
cd frontend

# Instalar dependências
npm install

# Iniciar servidor de desenvolvimento
npm run dev

# Build para produção
npm run build
```
