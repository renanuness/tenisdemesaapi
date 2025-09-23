Aqui está a documentação em formato README para os endpoints do `MatchController`:

# 👤 User Controller

Esta controller gerencia o cadastro, autenticação e gestão de usuários do sistema.

## 📋 Índice

- [Autenticação](#autenticação)
- [Endpoints](#endpoints)
  - [Registrar Usuário](#registrar-usuário)
  - [Login](#login)
  - [Obter Usuário por ID](#obter-usuário-por-id)
  - [Listar Usuários para Convite](#listar-usuários-para-convite)
  - [Atualizar Email](#atualizar-email)

---

## 🔐 Autenticação

A maioria dos endpoints requer autenticação via ID do usuário no header:

```http
userId: Long
```

*Exceto para registro e login que são públicos.*

---

## 📡 Endpoints

### 1. Registrar Usuário

**POST** `/api/user/register`

Cria uma nova conta de usuário no sistema.

#### Body

```json
{
  "name": "João Silva",
  "email": "joao@email.com",
  "password": "senhaSegura123"
}
```

#### Parâmetros

- `name` (String): Nome completo do usuário
- `email` (String): Email único para login
- `password` (String): Senha para autenticação

#### Respostas

- **201 Created**: Usuário criado com sucesso

```json
{
  "id": 12345,
  "name": "João Silva",
  "email": "joao@email.com"
}
```

- **400 Bad Request**: Dados inválidos ou email já existente

#### Headers de Resposta

- `Location`: `/api/user/12345` (URL do novo usuário criado)

---

### 2. Login

**POST** `/api/user/login`

Autentica um usuário no sistema.

#### Body

```json
{
  "email": "joao@email.com",
  "password": "senhaSegura123"
}
```

#### Parâmetros

- `email` (String): Email cadastrado
- `password` (String): Senha do usuário

#### Respostas

- **200 OK**: Login bem-sucedido

```json
{
  "success": true,
  "user": {
    "id": 12345,
    "name": "João Silva",
    "email": "joao@email.com"
  }
}
```

- **400 Bad Request**: Credenciais inválidas

```json
{
  "success": false,
  "user": null
}
```

---

### 3. Obter Usuário por ID

**GET** `/api/user/{id}`

Retorna os dados públicos de um usuário específico.

#### Path Variables

- `id` (Long): ID do usuário desejado

#### Respostas

- **200 OK**: Dados do usuário

```json
{
  "id": 12345,
  "name": "João Silva",
  "email": "joao@email.com"
}
```

- **404 Not Found**: Usuário não encontrado

---

### 4. Listar Usuários para Convite

**GET** `/api/user/listUsersToInvite`

Lista usuários disponíveis para receber convites de partida (excluindo o próprio usuário).

#### Headers

```http
userId: Long (obrigatório)
```

#### Resposta

- **200 OK**: Lista de usuários disponíveis

```json
[
  {
    "id": 12346,
    "name": "Maria Santos",
    "email": "maria@email.com"
  },
  {
    "id": 12347,
    "name": "Pedro Oliveira",
    "email": "pedro@email.com"
  }
]
```

---

### 5. Atualizar Email

**PUT** `/api/user/updateEmail`

Atualiza o endereço de email do usuário autenticado.

#### Headers

```http
userId: Long (obrigatório)
```

#### Body

```json
{
  "email": "novo.email@exemplo.com"
}
```

#### Parâmetros

- `email` (String): Novo endereço de email (deve ser único)

#### Respostas

- **200 OK**: Email atualizado com sucesso

```json
{
  "id": 12345,
  "name": "João Silva",
  "email": "novo.email@exemplo.com"
}
```

- **400 Bad Request**: Email já em uso ou inválido
- **404 Not Found**: Usuário não encontrado

---

## 🏷️ Códigos de Status HTTP

| Código | Descrição                              | Endpoints                                   |
| ------- | ---------------------------------------- | ------------------------------------------- |
| 200     | OK - Requisição bem-sucedida           | login, user, listUsersToInvite, updateEmail |
| 201     | Created - Recurso criado                 | register                                    |
| 400     | Bad Request - Dados inválidos           | todos                                       |
| 404     | Not Found - Recurso não encontrado      | user, updateEmail                           |
| 500     | Internal Server Error - Erro no servidor | todos                                       |

---

## 📊 DTOs Utilizados

### RegisterDTO

```json
{
  "name": "string",
  "email": "string",
  "password": "string"
}
```

### LoginDTO

```json
{
  "email": "string",
  "password": "string"
}
```

### LoginResponseDTO

```json
{
  "success": "boolean",
  "user": "UserDTO ou null"
}
```

### UserDTO

```json
{
  "id": "long",
  "name": "string",
  "email": "string"
}
```

### UpdateEmailDTO

```json
{
  "email": "string"
}
```

---

**Base Path**: `/api/user`

# 🎾 Match Controller

Esta controller gerencia partidas entre jogadores, incluindo convites, respostas e resultados.

## 📋 Índice

- [Autenticação](#autenticação)
- [Endpoints](#endpoints)
  - [Convidar Jogador](#convidar-jogador)
  - [Cancelar Convite](#cancelar-convite)
  - [Listar Convites](#listar-convites)
  - [Responder Convite](#responder-convite)
  - [Adicionar Resultado do Jogo](#adicionar-resultado-do-jogo)

---

## 🔐 Autenticação

Todos os endpoints requerem o ID do usuário no header da requisição, em uma aplicação real seria um JTW token validando autenticação e autorização:

```http
userId: Long
```

---

## 📡 Endpoints

### 1. Convidar Jogador

**POST** `/api/match/invitePlayer`

Envia um convite para outro jogador participar de uma partida.

#### Headers

```http
userId: Long (obrigatório)
```

#### Body

```json
{
  "playerId": 12345,
  "bestOf": 3
}
```

#### Parâmetros

- `playerId` (Long): ID do jogador convidado
- `bestOf` (Short): Número máximo de games (ex: 3 = melhor de 3)

#### Resposta

- **200 OK**: `"Convite enviado"`

---

### 2. Cancelar Convite

**DELETE** `/api/match/cancelInvite/{matchId}`

Cancela um convite de partida pendente.

#### Headers

```http
userId: Long (obrigatório)
```

#### Path Variables

- `matchId` (Long): ID da partida/convite

#### Resposta

- **200 OK**: `"Convite cancelado com sucesso"`

---

### 3. Listar Convites

**GET** `/api/match/listInvites`

Lista todos os convites de partida recebidos pelo usuário.

#### Headers

```http
userId: Long (obrigatório)
```

#### Resposta

- **200 OK**: Array de objetos `Match`

```json
[
  {
    "id": 1,
    "playerA": { ... },
    "playerB": { ... },
    "status": "PENDING",
    "bestOf": 3,
    "createdAt": "2024-01-15T10:30:00Z"
  }
]
```

---

### 4. Responder Convite

**POST** `/api/match/replyInvite`

Aceita ou recusa um convite de partida.

#### Headers

```http
userId: Long (obrigatório)
```

#### Body

```json
{
  "matchId": 12345,
  "accepted": true
}
```

#### Parâmetros

- `matchId` (Long): ID da partida/convite
- `accepted` (Boolean): `true` para aceitar, `false` para recusar

#### Resposta

- **200 OK**: `"Resposta enviada"`

---

### 5. Adicionar Resultado do Jogo

**POST** `/api/match/addGameResult`

Registra o resultado de uma partida em andamento.

```http
userId: Long (obrigatório)
```

#### Body (Estrutura Correta)

```json
{
  "matchId": 12345,
  "playerAScore": 6,
  "playerBScore": 4
}
```

#### Parâmetros

- `matchId` (Long): ID da partida
- `playerAScore` (Short): Pontuação do jogador A
- `playerBScore` (Short): Pontuação do jogador B

#### Resposta

- **200 OK**: Objeto `Match` atualizado

```json
{
  "id": 12345,
  "status": "COMPLETED",
  "winner": { ... },
  "games": [
    {
      "playerAScore": 6,
      "playerBScore": 4
    }
  ]
}
```

---

## 🏷️ Códigos de Status HTTP

| Código | Descrição                              |
| ------- | ---------------------------------------- |
| 200     | OK - Requisição bem-sucedida           |
| 400     | Bad Request - Dados inválidos           |
| 404     | Not Found - Recurso não encontrado      |
| 500     | Internal Server Error - Erro no servidor |

---

**Versão da API**: 1.0
**Base Path**: `/api/match`
