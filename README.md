# Santander Bootcamp 2023 - Fullstack Java+Angular

## Aluno
- [Ágryo DIO](https://www.dio.me/users/agryo)
- [Ágryo LinkedIn](https://www.linkedin.com/in/agryo/)


## Diagrama de Classes (Domínio da API)
```mermaid
---
title: Diagrama de Classes
---
classDiagram
  direction LR

  class Negocio {
    - id: int (PK)
    - nome: string
    - descricao: string
    - usuario_id: int (FK)
    - endereco_id: int (FK)
    - telefone_id: int (FK)
  }

  class Telefone {
    - id: int (PK)
    - numero: string
    - usuario_id: int (FK)
  }

  class Usuario {
    - id: int (PK)
    - nome: string
    - cpf: string (unique)
    - email: string (unique)
    - endereco_id: int (FK)
    - telefone_id: int (FK)
  }

  class Endereco {
    - id: int (PK)
    - rua: string
    - numero: string
    - bairro: string
    - cidade: string
    - estado: string
    - cep: string
    - usuario_id: int (FK)
    - negocio_id: int (FK)
  }

  class Coordenadas {
    - id: int (PK)
    - latitude: double
    - longitude: double
    - endereco_id: int (FK)
  }

Usuario "1" o-- "*" Telefone : possuiTelefones
Negocio "1" o-- "*" Telefone : possuiTelefones
Usuario "1" o-- "*" Endereco : possuiEnereços
Negocio "1" o-- "1" Endereco : possuiEndereços
Endereco "1" o-- "1" Coordenadas : possuiCoordenadas
```
## Links Úteis
- [Mermaid - Diagramas de Classe](https://mermaid.js.org/)