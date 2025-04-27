# # 📚 Sistema de Biblioteca - Escola "Aprender Mais"

Este é um sistema funcional desenvolvido em **Java (JDK 21)** com **interface via console**, utilizando **JDBC** e **MySQL** para gerenciar alunos, livros, empréstimos e devoluções de forma prática e eficiente.

---

## 🎓 Projeto Acadêmico - UNIFOR

Este projeto foi desenvolvido como proposta acadêmica na Universidade de Fortaleza (UNIFOR), com o objetivo de aplicar conceitos de programação orientada a objetos, manipulação de banco de dados e organização de sistemas reais.

---

## 👥 Integrantes

- Anderson Benevides  
- André Luis  
- Francisco Camilo  
- Marcello Garcia  
- Ytalo Ravir  
- Thiago Felipe

---

## 🏫 Contexto da Atividade

A biblioteca da **Escola Municipal "Aprender Mais"** enfrenta dificuldades com seu sistema de controle de empréstimos, atualmente feito de forma **manual**, o que gera **falhas, perda de controle de estoque e dificuldade na gestão de devoluções**.

---

## 💡 Solução Proposta

A proposta consiste no desenvolvimento de um **sistema em Java com interface em console**, utilizando **banco de dados MySQL** para garantir a persistência dos dados e as funcionalidades essenciais de uma biblioteca escolar.

---

## 🔧 Funcionalidades

- ✅ Cadastro de Alunos  
- ✅ Cadastro de Livros  
- ✅ Empréstimos com validação de:
  - Disponibilidade em estoque
- ✅ Devoluções com:
  - Validação de prazo (7 dias)
  - Cálculo de multa (R$3 por dia de atraso)
- ✅ Relatórios básicos de histórico de empréstimos
- ✅ Controle automático de estoque

---

## 🛠 Tecnologias Utilizadas

- Java 21
- JDBC (Java Database Connectivity)
- MySQL
- IDE: Eclipse

---

## 📁 Estrutura do Projeto

```bash
📦 src
 ┣ 📂 application     # Classe principal com menus de interação via console
 ┣ 📂 dao             # Camada DAO (Data Access Object) para acesso ao banco
 ┣ 📂 model           # Entidades: Aluno, Livro, Emprestimo
 ┗ 📂 util            # Utilitários como conexão com banco (DB.java)
