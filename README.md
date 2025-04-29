# 📚 Sistema de Biblioteca - Escola "Aprender Mais"

Sistema funcional desenvolvido em **Java (JDK 21)** com interface via **console**, utilizando **JDBC** e **MySQL** para gerenciar alunos, livros, empréstimos e devoluções de forma prática e eficiente.

---

## 🎓 Projeto Acadêmico - UNIFOR

Este projeto foi desenvolvido como proposta acadêmica na **Universidade de Fortaleza (UNIFOR)**, com o objetivo de aplicar conceitos de **programação orientada a objetos**, **manipulação de banco de dados** e **estruturação de sistemas reais**.

---

## 🏫 Contexto da Atividade

A biblioteca da **Escola Municipal "Aprender Mais"** enfrenta dificuldades com seu sistema de controle de empréstimos, atualmente feito de forma **manual**, o que ocasiona **falhas, perda de controle do acervo e dificuldades na gestão de devoluções**.

---

## 💡 Solução Proposta

Desenvolver um **sistema em Java com interface via console**, utilizando um **banco de dados MySQL**, garantindo a **persistência dos dados** e oferecendo as funcionalidades essenciais para a rotina de uma biblioteca escolar.

---

## 🔧 Funcionalidades

- ✅ CRUD de Alunos  
- ✅ CRUD de Livros  
- ✅ Empréstimos com validações de:
  - Existência do aluno e do livro
  - Disponibilidade em estoque
- ✅ Devoluções com:
  - Validação de prazo (7 dias)
  - Cálculo de multa (R$3 por dia de atraso)
- ✅ Relatórios simples com histórico de empréstimos
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
 ┗ 📂 util            # Utilitários, como conexão com o banco (DB.java)
