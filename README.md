# CineMatch - API REST de Recomendação de Filmes 🎬

Um recomendador de filmes inspirado em Netflix e Letterboxd, com algoritmo simples de recomendação baseado em gêneros preferidos e similaridade entre usuários.

## 📋 Funcionalidades

### Usuários
- ✅ Criar novo usuário com validação de email único
- ✅ Autenticação via login (email e senha)
- ✅ Obter informações do usuário

### Filmes
- ✅ Criar novos filmes
- ✅ Listar todos os filmes
- ✅ Buscar filmes por gênero
- ✅ Buscar filmes por título
- ✅ Obter informações detalhadas de um filme

### Avaliações
- ✅ Avaliar filmes (escala 1-5)
- ✅ Visualizar avaliações de um usuário
- ✅ Visualizar avaliações de um filme
- ✅ Evitar avaliações duplicadas do mesmo usuário

### Recomendações
- ✅ Algoritmo inteligente de recomendação
- ✅ Recomendações baseadas em gêneros preferidos
- ✅ Fallback para filmes com alta avaliação
- ✅ Recomendações filtradas por gênero

## 🛠️ Tecnologias Utilizadas

- **Java 21** - Linguagem principal
- **Spring Boot 4.0.3** - Framework principal
- **Spring Data JPA** - Persistência de dados
- **PostgreSQL** - Banco de dados
- **Lombok** - Redução de boilerplate
- **Jakarta Validation** - Validação de dados
- **Maven** - Gerenciamento de dependências

## 📦 Pré-requisitos

- Java 21+
- Maven 3.6+
- PostgreSQL 12+
- Docker (opcional, para executar PostgreSQL)

## 🚀 Como Executar

### 1. Configurar Banco de Dados

Com Docker:
```bash
docker run --name postgres-cinematch \
  -e POSTGRES_USER=postgres \
  -e POSTGRES_PASSWORD=postgres \
  -e POSTGRES_DB=dbcinematch \
  -p 5433:5432 \
  -d postgres:15
```

Ou crie manualmente no PostgreSQL:
```sql
CREATE DATABASE dbcinematch;
```

### 2. Clonar e Configurar o Projeto

```bash
cd /home/lucas/IdeaProjects/CineMatch-back-end
```

### 3. Executar a Aplicação

```bash
./mvnw spring-boot:run
```

Ou:
```bash
mvn spring-boot:run
```

A API estará disponível em: `http://localhost:8080/api`

## 📚 Estrutura do Projeto

```
src/main/java/com/cinematch/lucasximenes30/
├── CineMatchApplication.java          # Classe principal
├── config/
│   └── WebConfig.java                 # Configurações de CORS
├── controller/
│   ├── UserController.java            # API de usuários
│   ├── MovieController.java           # API de filmes
│   ├── ReviewController.java          # API de avaliações
│   └── RecommendationController.java  # API de recomendações
├── service/
│   ├── UserService.java               # Lógica de usuários
│   ├── MovieService.java              # Lógica de filmes
│   ├── ReviewService.java             # Lógica de avaliações
│   └── RecommendationService.java     # Algoritmo de recomendações
├── repository/
│   ├── UserRepository.java            # Persistência de usuários
│   ├── MovieRepository.java           # Persistência de filmes
│   └── ReviewRepository.java          # Persistência de avaliações
├── model/
│   ├── User.java                      # Entidade de usuário
│   ├── Movie.java                     # Entidade de filme
│   └── Review.java                    # Entidade de avaliação
├── dto/
│   ├── UserCreateDto.java             # DTO de criação de usuário
│   ├── UserResponseDto.java           # DTO de resposta de usuário
│   ├── LoginDto.java                  # DTO de login
│   ├── LoginResponseDto.java          # DTO de resposta de login
│   ├── MovieCreateDto.java            # DTO de criação de filme
│   ├── MovieResponseDto.java          # DTO de resposta de filme
│   ├── ReviewCreateDto.java           # DTO de criação de avaliação
│   ├── ReviewResponseDto.java         # DTO de resposta de avaliação
│   └── RecommendationDto.java         # DTO de recomendação
└── exception/
    ├── ResourceNotFoundException.java  # Exceção de recurso não encontrado
    ├── BusinessException.java         # Exceção de negócio
    ├── ErrorResponse.java             # Resposta de erro
    └── GlobalExceptionHandler.java    # Manipulador global de exceções
```

## 🔌 Endpoints da API

### Usuários

#### Criar Usuário
```http
POST /api/users
Content-Type: application/json

{
  "name": "João Silva",
  "email": "joao@example.com",
  "password": "senha123"
}

Response: 201 Created
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "João Silva",
  "email": "joao@example.com",
  "createdAt": "2024-03-13T10:30:00",
  "updatedAt": "2024-03-13T10:30:00"
}
```

#### Login
```http
POST /api/users/login
Content-Type: application/json

{
  "email": "joao@example.com",
  "password": "senha123"
}

Response: 200 OK
{
  "message": "Login realizado com sucesso",
  "user": {
    "id": "550e8400-e29b-41d4-a716-446655440000",
    "name": "João Silva",
    "email": "joao@example.com",
    "createdAt": "2024-03-13T10:30:00",
    "updatedAt": "2024-03-13T10:30:00"
  }
}
```

#### Obter Usuário
```http
GET /api/users/{userId}

Response: 200 OK
{
  "id": "550e8400-e29b-41d4-a716-446655440000",
  "name": "João Silva",
  "email": "joao@example.com",
  "createdAt": "2024-03-13T10:30:00",
  "updatedAt": "2024-03-13T10:30:00"
}
```

### Filmes

#### Criar Filme
```http
POST /api/movies
Content-Type: application/json

{
  "title": "Inception",
  "genre": "Ficção Científica",
  "releaseYear": "2010-07-16",
  "description": "Um filme sobre sonhos e realidade"
}

Response: 201 Created
{
  "id": "550e8400-e29b-41d4-a716-446655440001",
  "title": "Inception",
  "genre": "Ficção Científica",
  "releaseYear": "2010-07-16",
  "ratingAverage": 0.0,
  "description": "Um filme sobre sonhos e realidade",
  "createdAt": "2024-03-13T10:30:00",
  "updatedAt": "2024-03-13T10:30:00"
}
```

#### Listar Filmes
```http
GET /api/movies

Response: 200 OK
[
  {
    "id": "550e8400-e29b-41d4-a716-446655440001",
    "title": "Inception",
    "genre": "Ficção Científica",
    ...
  }
]
```

#### Buscar por Gênero
```http
GET /api/movies?genre=Ficção Científica
```

#### Buscar por Título
```http
GET /api/movies?title=Inception
```

#### Obter Filme
```http
GET /api/movies/{movieId}
```

### Avaliações

#### Criar Avaliação
```http
POST /api/reviews
Content-Type: application/json

{
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "movieId": "550e8400-e29b-41d4-a716-446655440001",
  "rating": 5
}

Response: 201 Created
{
  "id": "550e8400-e29b-41d4-a716-446655440002",
  "userId": "550e8400-e29b-41d4-a716-446655440000",
  "userName": "João Silva",
  "movieId": "550e8400-e29b-41d4-a716-446655440001",
  "movieTitle": "Inception",
  "rating": 5,
  "createdAt": "2024-03-13T10:30:00"
}
```

#### Obter Avaliações de Usuário
```http
GET /api/reviews/users/{userId}
```

#### Obter Avaliações de Filme
```http
GET /api/reviews/movies/{movieId}
```

### Recomendações

#### Obter Recomendações
```http
GET /api/recommendations/{userId}

Response: 200 OK
[
  {
    "movieId": "550e8400-e29b-41d4-a716-446655440003",
    "title": "The Matrix",
    "genre": "Ficção Científica",
    "ratingAverage": 8.7,
    "description": "Um clássico de ficção científica",
    "reason": "Baseado em seus gêneros favoritos"
  }
]
```

#### Obter Recomendações por Gênero
```http
GET /api/recommendations/{userId}?genre=Ficção Científica
```

## 🧠 Algoritmo de Recomendação

O algoritmo de recomendação funciona da seguinte forma:

1. **Coleta de Preferências**: Analisa todos os filmes avaliados pelo usuário
2. **Extração de Gêneros**: Identifica os gêneros preferidos baseado nas avaliações
3. **Filtragem**: Remove filmes já avaliados
4. **Recomendação**: Sugere filmes dos gêneros preferidos ordenados por avaliação
5. **Fallback**: Se não houver filmes nos gêneros preferidos, sugere filmes com alta avaliação geral

## ✅ Validações

- ✅ Email único e válido
- ✅ Senha com mínimo de 6 caracteres
- ✅ Avaliações de 1 a 5
- ✅ Evita avaliações duplicadas
- ✅ Validação de campos obrigatórios
- ✅ Tamanhos máximos de strings

## 📊 Modelos de Dados

### User
```
- id: UUID
- name: String (3-100 caracteres)
- email: String (único)
- password: String (mín. 6 caracteres)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
- reviews: List<Review>
```

### Movie
```
- id: UUID
- title: String (1-255 caracteres)
- genre: String (2-50 caracteres)
- releaseYear: LocalDate
- description: String
- ratingAverage: Double (0-10)
- createdAt: LocalDateTime
- updatedAt: LocalDateTime
- reviews: List<Review>
```

### Review
```
- id: UUID
- user: User
- movie: Movie
- rating: Integer (1-5)
- createdAt: LocalDateTime
```

## 🔐 Segurança

⚠️ **Nota**: Este é um projeto de exemplo educacional. Para produção:
- Implementar JWT para autenticação
- Hash de senhas com BCrypt
- HTTPS obrigatório
- Rate limiting
- Validação de CORS mais restritiva

## 📝 Exemplos de Uso

### Com cURL
```bash
# Criar usuário
curl -X POST http://localhost:8080/api/users \
  -H "Content-Type: application/json" \
  -d '{"name":"João","email":"joao@test.com","password":"senha123"}'

# Criar filme
curl -X POST http://localhost:8080/api/movies \
  -H "Content-Type: application/json" \
  -d '{"title":"Inception","genre":"Ficção Científica"}'

# Avaliar filme
curl -X POST http://localhost:8080/api/reviews \
  -H "Content-Type: application/json" \
  -d '{"userId":"...","movieId":"...","rating":5}'

# Obter recomendações
curl -X GET http://localhost:8080/api/recommendations/{userId}
```

### Com Postman
Importe a coleção de exemplo `examples.sh` ou crie manualmente as requisições baseado na documentação acima.

## 🐛 Troubleshooting

### Erro de Conexão com Banco
- Verifique se PostgreSQL está rodando
- Confirme as credenciais em `application.properties`
- Verifique a porta (padrão: 5433)

### Port Já em Uso
```bash
# Mude a porta em application.properties
server.port=8081
```

### Permissão Negada no Maven
```bash
chmod +x mvnw
```

## 📄 Licença

Este projeto é de código aberto e está disponível sob a Licença MIT.

## 👨‍💻 Autor

Lucas Ximenes
- Email: lucasximenes30@example.com
- GitHub: @lucasximenes30

## 🤝 Contribuindo

Contribuições são bem-vindas! Por favor:
1. Faça um Fork do projeto
2. Crie uma branch para sua feature (`git checkout -b feature/AmazingFeature`)
3. Commit suas mudanças (`git commit -m 'Add some AmazingFeature'`)
4. Push para a branch (`git push origin feature/AmazingFeature`)
5. Abra um Pull Request

---

**Desenvolvido com ❤️ para CineMatch**

