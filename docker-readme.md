# 🐳 Docker - sabIA API

Documentação para desenvolvedores **frontend/mobile** que precisam da API rodando localmente.

---

## 📋 Pré-requisitos

1. Docker instalado
2. Arquivo `.env` na raiz do projeto com as variáveis:

```env
POSTGRES_USER=sabia_user
POSTGRES_PASSWORD=sabia_password
POSTGRES_DB=sabia_db
GEMINI_API_KEY=sua_chave_api
GEMINI_API_MODEL=gemini-2.0-flash-exp
```

Para ter acesso aos valores necessários das variáveis de ambiente, entre em contato com Yohans Nascimento ou Pedro Paulo Durand.

---

## 🚀 Comandos Principais

### Iniciar a API completa (banco + aplicação)

```bash
# 1. Compilar o projeto
./mvnw clean package -DskipTests

# 2. Subir tudo
docker-compose -f docker-compose.prod.yml up --build -d
```

### Ver logs da aplicação

```bash
docker-compose -f docker-compose.prod.yml logs -f app
```

### Parar tudo

```bash
docker-compose -f docker-compose.prod.yml down
```

### Parar e remover dados do banco

```bash
docker-compose -f docker-compose.prod.yml down -v
```

### Verificar status

```bash
docker-compose -f docker-compose.prod.yml ps
```

---

## 🔌 Informações de Conexão

- **API:** `http://localhost:8080`
- **Banco de Dados:** `localhost:5432`
  - Database: valor de `POSTGRES_DB` no `.env`
  - User: valor de `POSTGRES_USER` no `.env`
  - Password: valor de `POSTGRES_PASSWORD` no `.env`

---

## ⚠️ Problemas Comuns

**Porta 5432 já em uso:**
```bash
docker-compose -f docker-compose.prod.yml down
```

**API não conecta no banco:**
```bash
# Aguardar alguns segundos após subir
docker-compose -f docker-compose.prod.yml logs app
```

**Reconstruir após mudanças no código:**
```bash
./mvnw clean package -DskipTests
docker-compose -f docker-compose.prod.yml up --build
```
