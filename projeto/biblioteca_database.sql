-- CRIAÇÃO BANCO DE DADOS
CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

-- TABELA ALUNOS
CREATE TABLE Alunos (
    id_aluno INT AUTO_INCREMENT PRIMARY KEY,
    nome_aluno VARCHAR(100) NOT NULL,
    matricula VARCHAR(20) UNIQUE,
    data_nascimento DATE,
    curso VARCHAR(150) NOT NULL
);

-- TABELA LIVROS
CREATE TABLE Livros (
    id_livro INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150) NOT NULL,
    autor VARCHAR(100),
    ano_publicacao INT,
    quantidade_estoque INT DEFAULT 0,
    quantidade_emprestada INT DEFAULT 0
);

-- TABELA EMPRESTIMOS
CREATE TABLE Emprestimos (
    id_emprestimo INT AUTO_INCREMENT PRIMARY KEY,
    id_aluno INT NOT NULL,
    id_livro INT NOT NULL,
    data_emprestimo DATETIME DEFAULT CURRENT_TIMESTAMP,
    data_devolucao DATE,
    data_devolucao_real DATETIME DEFAULT NULL,
    valor_multa DECIMAL(10,2),
    status VARCHAR(20) DEFAULT 'EM_ABERTO',
    FOREIGN KEY (id_aluno) REFERENCES Alunos(id_aluno),
    FOREIGN KEY (id_livro) REFERENCES Livros(id_livro),
    CONSTRAINT chk_devolucao CHECK (data_devolucao_real >= data_emprestimo) 
);

-- TABELA EMPRESTIMOS FINALIZADOS
CREATE TABLE EmprestimosFinalizados (
    id_relatorio INT AUTO_INCREMENT PRIMARY KEY, 
    id_aluno INT,
    nome_aluno VARCHAR(100),
    id_livro INT,
    titulo VARCHAR(200),
    data_emprestimo DATE,
    data_devolucao DATE,
    data_devolucao_real DATE,
    valor_multa DECIMAL(10,2),
    status VARCHAR(50),
    FOREIGN KEY (id_aluno) REFERENCES Alunos(id_aluno),
    FOREIGN KEY (id_livro) REFERENCES Livros(id_livro)
);


-- INSERÇÃO DE ALUNOS
INSERT INTO Alunos (nome_aluno, matricula, data_nascimento, curso) VALUES
('Anderson Benevides Castro', '2427265', '1998-04-15', 'Análise e Desenvolvimento de Sistemas'),
('André Luís Lima de Oliveira', '2425200', '1992-08-14', 'Análise e Desenvolvimento de Sistemas'),
('Francisco Camilo de Lima Filho', '2425148', '1996-05-18', 'Análise e Desenvolvimento de Sistemas'),
('Marcello Garcia Cosme Lima', '2425024', '2002-01-10', 'Análise e Desenvolvimento de Sistemas'),
('Thiago Felipe Lopes Rodrigues', '2425055', '2000-10-02', 'Análise e Desenvolvimento de Sistemas'),
('Ytalo Ravir Freitas Vasconcelos', '2425167', '1998-06-05', 'Análise e Desenvolvimento de Sistemas');

-- INSERÇÃO DE LIVROS
INSERT INTO Livros (titulo, autor, ano_publicacao, quantidade_estoque) VALUES
('Código Limpo', 'Robert C. Martin', 2008, 5),
('O Programador Pragmático', 'Andrew Hunt e David Thomas', 1999, 3),
('Estruturas de Dados e Algoritmos em Java', 'Robert Lafore', 2011, 4),
('Design Patterns: Padrões de Projeto', 'Erich Gamma et al.', 1994, 2),
('Introdução à Computação', 'Peter Norton', 2002, 6),
('Algoritmos: Teoria e Prática', 'Thomas H. Cormen', 2009, 3),
('Engenharia de Software', 'Ian Sommerville', 2011, 7),
('Banco de Dados - Projeto e Implementação', 'Carlos A. Heuser', 2013, 5),
('Redes de Computadores', 'Andrew S. Tanenbaum', 2010, 4),
('Arquitetura de Computadores', 'David A. Patterson', 2014, 3),
('Desenvolvimento Web com HTML, CSS e JavaScript', 'Jon Duckett', 2014, 6),
('Estrutura de Dados com Java', 'Ney Dantas', 2010, 2),
('Clean Architecture', 'Robert C. Martin', 2017, 4),
('Python Fluente', 'Luciano Ramalho', 2015, 5),
('Linux - Guia do Administrador', 'Ewan Birney', 2012, 3),
('Java: Como Programar', 'Deitel & Deitel', 2015, 6),
('Segurança da Informação', 'William Stallings', 2005, 2),
('Machine Learning', 'Tom M. Mitchell', 1997, 3),
('Internet das Coisas com Arduino', 'Marco Schwartz', 2016, 4),
('Inteligência Artificial: Fundamentos', 'Stuart Russell e Peter Norvig', 2009, 2);

-- CRIAÇÃO DE ÍNDICES
CREATE INDEX idx_id_aluno ON Emprestimos(id_aluno);
CREATE INDEX idx_id_livro ON Emprestimos(id_livro);
