# Saúde+ API - Documentação

## Visão Geral

A API da Plataforma de Saúde Integrada (Saúde+) é uma API RESTful que conecta pacientes, nutricionistas e educadores físicos, oferecendo funcionalidades completas para gestão de saúde e bem-estar.

## Base URL

- **Desenvolvimento**: `http://localhost:8080`
- **Produção**: `https://api.saudeplus.com`

## Documentação Interativa

Acesse a documentação interativa da API através do Swagger UI:
- **Desenvolvimento**: `http://localhost:8080/swagger-ui.html`
- **Produção**: `https://api.saudeplus.com/swagger-ui.html`

## Autenticação

A API utiliza autenticação básica por email e senha. Para acessar endpoints protegidos, faça login primeiro através do endpoint `/auth/login`.

## Endpoints Principais

### 1. Autenticação (`/auth`)

#### Login
```http
POST /auth/login
Content-Type: application/json

{
  "email": "usuario@exemplo.com",
  "password": "senha123"
}
```

**Resposta:**
```json
{
  "user": {
    "id": 1,
    "name": "João Silva",
    "email": "usuario@exemplo.com",
    "role": "PATIENT"
  },
  "message": "Login realizado com sucesso"
}
```

#### Registro
```http
POST /auth/register
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@exemplo.com",
  "password": "senha123",
  "role": "PATIENT"
}
```

**Resposta:**
```json
{
  "user": {
    "id": 1,
    "name": "João Silva",
    "email": "joao@exemplo.com",
    "role": "PATIENT"
  },
  "message": "Usuário registrado com sucesso"
}
```

### 2. Usuários (`/users`)

#### Listar usuários ativos
```http
GET /users
```

#### Buscar usuário por ID
```http
GET /users/{id}
```

### 3. Profissionais (`/professionals`)

#### Listar profissionais ativos
```http
GET /professionals
```

#### Buscar profissional por especialidade
```http
GET /professionals/specialty/nutricionista
```

### 4. Pacientes (`/patients`)

#### Criar perfil de paciente
```http
POST /patients
Content-Type: application/json

{
  "user": {
    "id": 1
  },
  "birthDate": "1990-05-15",
  "gender": "MALE",
  "height": 175.0,
  "weight": 70.0
}
```

### 5. Solicitações de Acompanhamento (`/follow-requests`)

#### Criar solicitação
```http
POST /follow-requests
Content-Type: application/json

{
  "sender": {
    "id": 1
  },
  "receiver": {
    "id": 2
  },
  "message": "Gostaria de solicitar acompanhamento nutricional"
}
```

### 6. Métricas Corporais (`/body-metrics`)

#### Criar sessão de métricas
```http
POST /body-metrics/sessions
Content-Type: application/json

{
  "patient": {
    "id": 1
  },
  "date": "2024-01-15",
  "notes": "Avaliação inicial"
}
```

#### Adicionar entrada à sessão
```http
POST /body-metrics/sessions/{sessionId}/entries
Content-Type: application/json

{
  "metricName": "Peso",
  "value": 70.5,
  "unit": "kg"
}
```

### 7. Nutrição (`/nutrition`)

#### Criar plano alimentar
```http
POST /nutrition/meal-plans
Content-Type: application/json

{
  "patient": {
    "id": 1
  },
  "professional": {
    "id": 2
  },
  "title": "Plano para emagrecimento",
  "description": "Plano alimentar focado em redução de peso",
  "startDate": "2024-01-15",
  "endDate": "2024-02-15"
}
```

#### Buscar alimentos
```http
GET /nutrition/foods/search?name=frango
```

#### Calcular valores nutricionais
```http
GET /nutrition/calculate/{foodId}?quantityG=100
```

### 8. Treinos (`/training`)

#### Criar plano de treino
```http
POST /training/plans
Content-Type: application/json

{
  "patient": {
    "id": 1
  },
  "professional": {
    "id": 3
  },
  "title": "Treino para hipertrofia",
  "goal": "Ganho de massa muscular",
  "startDate": "2024-01-15",
  "endDate": "2024-02-15"
}
```

#### Buscar exercícios
```http
GET /training/exercises/search?name=supino
```

### 9. Conteúdo (`/content`)

#### Criar conteúdo educativo
```http
POST /content
Content-Type: application/json

{
  "title": "Benefícios da alimentação saudável",
  "description": "Artigo sobre nutrição",
  "content": "Conteúdo do artigo...",
  "type": "ARTICLE",
  "visibility": "PUBLIC",
  "author": {
    "id": 2
  }
}
```

#### Listar conteúdos públicos
```http
GET /content/public
```

### 10. Condições de Saúde (`/health-conditions`)

#### Criar condição de saúde
```http
POST /health-conditions
Content-Type: application/json

{
  "patient": {
    "id": 1
  },
  "type": "ALLERGY",
  "title": "Alergia a amendoim",
  "description": "Alergia severa a amendoim",
  "startDate": "2020-01-01",
  "active": true
}
```

### 11. Notas Profissionais (`/professional-notes`)

#### Criar nota profissional
```http
POST /professional-notes
Content-Type: application/json

{
  "patient": {
    "id": 1
  },
  "professional": {
    "id": 2
  },
  "content": "Paciente apresentou melhora significativa..."
}
```

## Códigos de Status HTTP

- `200 OK` - Requisição bem-sucedida
- `201 Created` - Recurso criado com sucesso
- `400 Bad Request` - Dados inválidos na requisição
- `401 Unauthorized` - Token inválido ou ausente
- `403 Forbidden` - Acesso negado
- `404 Not Found` - Recurso não encontrado
- `500 Internal Server Error` - Erro interno do servidor

## Exemplos de Resposta

### Sucesso
```json
{
  "id": 1,
  "name": "João Silva",
  "email": "joao@exemplo.com",
  "role": "PATIENT",
  "active": true,
  "createdAt": "2024-01-15T10:30:00"
}
```

### Erro
```json
{
  "error": "Usuário não encontrado",
  "timestamp": "2024-01-15T10:30:00",
  "path": "/users/999"
}
```

## Rate Limiting

A API implementa rate limiting para proteger contra abuso:
- **Limite**: 1000 requisições por hora por IP
- **Header de resposta**: `X-RateLimit-Remaining`

## Suporte

Para suporte técnico ou dúvidas sobre a API:
- **Email**: api@saudeplus.com
- **Documentação**: https://docs.saudeplus.com
- **Status**: https://status.saudeplus.com 