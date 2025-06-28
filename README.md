# 🏥 Plataforma de Saúde Integrada

Sistema completo para conectar pacientes com nutricionistas e educadores físicos, oferecendo acompanhamento personalizado de saúde.

## 🎯 Objetivo

Plataforma que conecta pacientes com profissionais de saúde para:
- Planos alimentares e treinos individualizados
- Registro dinâmico de métricas corporais
- Conteúdo educativo (artigos, treinos, receitas)
- Relatórios e gráficos de evolução
- Liberdade para o paciente e autonomia para o profissional

## 🏗️ Arquitetura

```
saude-plus/
├── backend/          # API Spring Boot
├── frontend/         # Interface Angular
├── docs/            # Documentação
└── docker/          # Configurações Docker
```

## 👥 Papéis de Usuários

- **Administrador**: Gerencia profissionais, conteúdos públicos, modera
- **Profissional**: Nutricionista, educador físico com especialidade
- **Paciente**: Cadastro livre, registro de métricas, consumo de conteúdo

## 🚀 Tecnologias

- **Backend**: Spring Boot (Java)
- **Frontend**: Angular
- **Banco**: PostgreSQL
- **Autenticação**: Email/Senha
- **Infraestrutura**: Docker

## 📦 Módulos Principais

1. **Autenticação e Gestão de Usuários**
2. **Cadastro de Profissionais e Especialidades**
3. **Relacionamento Profissional ↔ Paciente**
4. **Métricas Dinâmicas (Avaliações Corporais)**
5. **Gráficos e Relatórios**
6. **Plano Alimentar Personalizado**
7. **Registro Alimentar Diário**
8. **Plano de Treino**
9. **Condições de Saúde**
10. **Anotações Privadas**
11. **Conteúdo Público e Educativo**

## 🛠️ Setup do Projeto

### Pré-requisitos
- Java 17+
- Node.js 18+
- PostgreSQL 14+
- Docker (opcional)

### Backend
```bash
cd backend
./mvnw spring-boot:run
```

### Frontend
```bash
cd frontend
npm install
ng serve
```

## 📊 Modelo de Dados

O sistema utiliza um modelo ER flexível que permite:
- Métricas dinâmicas e customizáveis
- Templates de avaliação por profissional
- Relacionamentos flexíveis entre usuários
- Conteúdo com diferentes níveis de visibilidade

## 🔐 Segurança

- Autenticação por email e senha
- Controle de acesso baseado em roles
- Validação de permissões por ação
- Dados sensíveis criptografados

## 📈 Roadmap

- [ ] Teleconsulta e chat
- [ ] Avaliação automática por IA
- [ ] Marketplace de consultas
- [ ] App mobile
- [ ] Integração com wearables
