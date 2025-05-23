package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import util.DB;

public class EmprestimoDAO {

    // Método para registrar um novo empréstimo
    public static void registrarEmprestimo(int idAluno, int idLivro) throws SQLException {
        String verificaAluno = "SELECT id_aluno FROM Alunos WHERE id_aluno = ?";
        String verificaLivro = "SELECT quantidade_estoque FROM Livros WHERE id_livro = ?";
        String atualizaEstoque = "UPDATE Livros SET quantidade_estoque = quantidade_estoque - 1, quantidade_emprestada = quantidade_emprestada + 1 WHERE id_livro = ?";
        String insereEmprestimo = "INSERT INTO Emprestimos (id_aluno, id_livro, data_emprestimo, data_devolucao, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // Verifica se o aluno existe
                try (PreparedStatement stmt = conn.prepareStatement(verificaAluno)) {
                    stmt.setInt(1, idAluno);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (!rs.next()) {
                            throw new SQLException("Aluno não encontrado.");
                        }
                    }
                }

                // Verifica se o livro existe e se há estoque
                int estoque;
                try (PreparedStatement stmt = conn.prepareStatement(verificaLivro)) {
                    stmt.setInt(1, idLivro);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            estoque = rs.getInt("quantidade_estoque");
                            if (estoque <= 0) {
                                throw new SQLException("Livro sem estoque disponível.");
                            }
                        } else {
                            throw new SQLException("Livro não encontrado.");
                        }
                    }
                }

                // Atualiza estoque
                try (PreparedStatement stmt = conn.prepareStatement(atualizaEstoque)) {
                    stmt.setInt(1, idLivro);
                    stmt.executeUpdate();
                }

                // Insere empréstimo
                try (PreparedStatement stmt = conn.prepareStatement(insereEmprestimo)) {
                    stmt.setInt(1, idAluno);
                    stmt.setInt(2, idLivro);
                    stmt.setDate(3, Date.valueOf(LocalDate.now()));
                    stmt.setDate(4, Date.valueOf(LocalDate.now().plusDays(7)));
                    stmt.setString(5, "EM_ABERTO");
                    stmt.executeUpdate();
                }

                conn.commit();
                System.out.println("Empréstimo registrado com sucesso!");

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Método para registrar a devolução de um empréstimo
    public static void registrarDevolucao(int idEmprestimo) throws SQLException {
        String buscaEmprestimo = """
            SELECT e.id_livro, e.data_emprestimo, e.data_devolucao, e.status, a.id_aluno, a.nome_aluno, l.titulo
            FROM Emprestimos e
            INNER JOIN Alunos a ON e.id_aluno = a.id_aluno
            INNER JOIN Livros l ON e.id_livro = l.id_livro
            WHERE e.id_emprestimo = ?
        """;
        String atualizaEstoque = "UPDATE Livros SET quantidade_estoque = quantidade_estoque + 1, quantidade_emprestada = quantidade_emprestada - 1 WHERE id_livro = ?";
        String insereEmprestimoFinalizado = """
            INSERT INTO EmprestimosFinalizados 
            (id_aluno, nome_aluno, id_livro, titulo, data_emprestimo, data_devolucao, data_devolucao_real, valor_multa, status) 
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;
        String atualizaEmprestimo = """
            UPDATE Emprestimos 
            SET valor_multa = ?, status = 'DEVOLVIDO', data_devolucao_real = ? 
            WHERE id_emprestimo = ?
        """;

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            try {
                int idLivro;
                int idAluno;
                String nomeAluno;
                String tituloLivro;
                LocalDate dataEmprestimo;
                LocalDate dataDevolucao;
                String statusEmprestimo;

                // Busca os dados do empréstimo
                try (PreparedStatement stmt = conn.prepareStatement(buscaEmprestimo)) {
                    stmt.setInt(1, idEmprestimo);
                    try (ResultSet rs = stmt.executeQuery()) {
                        if (rs.next()) {
                            idLivro = rs.getInt("id_livro");
                            idAluno = rs.getInt("id_aluno");
                            nomeAluno = rs.getString("nome_aluno");
                            tituloLivro = rs.getString("titulo");
                            dataEmprestimo = rs.getDate("data_emprestimo").toLocalDate();
                            dataDevolucao = rs.getDate("data_devolucao").toLocalDate();
                            statusEmprestimo = rs.getString("status");

                            // Verifica se já foi devolvido
                            if ("DEVOLVIDO".equalsIgnoreCase(statusEmprestimo)) {
                                throw new SQLException("Este empréstimo já foi devolvido.");
                            }
                        } else {
                            throw new SQLException("Empréstimo não encontrado.");
                        }
                    }
                }

                // Atualiza estoque
                try (PreparedStatement stmt = conn.prepareStatement(atualizaEstoque)) {
                    stmt.setInt(1, idLivro);
                    stmt.executeUpdate();
                }

                // Define data de devolução real
                LocalDate dataDevolvida = LocalDate.now();
                double multa = calcularMulta(dataDevolucao, dataDevolvida);

                // Insere no relatório de empréstimos finalizados
                try (PreparedStatement stmt = conn.prepareStatement(insereEmprestimoFinalizado)) {
                    stmt.setInt(1, idAluno);
                    stmt.setString(2, nomeAluno);
                    stmt.setInt(3, idLivro);
                    stmt.setString(4, tituloLivro);
                    stmt.setDate(5, Date.valueOf(dataEmprestimo));
                    stmt.setDate(6, Date.valueOf(dataDevolucao));
                    stmt.setDate(7, Date.valueOf(dataDevolvida));
                    stmt.setDouble(8, multa);
                    stmt.setString(9, "DEVOLVIDO");
                    stmt.executeUpdate();
                }

                // Atualiza a multa, status e data de devolução real no empréstimo original
                try (PreparedStatement stmt = conn.prepareStatement(atualizaEmprestimo)) {
                    stmt.setDouble(1, multa);
                    stmt.setDate(2, Date.valueOf(dataDevolvida));
                    stmt.setInt(3, idEmprestimo);
                    stmt.executeUpdate();
                }

                conn.commit();

                if (multa > 0) {
                    System.out.println("Devolução atrasada. Multa aplicada: R$ " + multa);
                } else {
                    System.out.println("Devolução realizada no prazo. Sem multa.");
                }

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }
        }
    }

    // Método para calcular multa por atraso
    private static double calcularMulta(LocalDate dataPrevista, LocalDate dataDevolvida) {
        if (dataDevolvida.isAfter(dataPrevista)) {
            long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(dataPrevista, dataDevolvida);
            return diasAtraso * 3.0;
        }
        return 0.0;
    }

    // Método para listar alunos que têm multas pendentes
    public static void listarAlunosComMulta() throws SQLException {
        String sql = """
            SELECT DISTINCT a.nome_aluno, e.valor_multa
            FROM Emprestimos e
            INNER JOIN Alunos a ON e.id_aluno = a.id_aluno
            WHERE e.valor_multa > 0
        """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== Alunos com Multa ===");

            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                String nome = rs.getString("nome_aluno");
                double multa = rs.getDouble("valor_multa");
                System.out.printf("Aluno: %s | Multa: R$ %.2f%n", nome, multa);
            }

            if (!encontrou) {
                System.out.println("Nenhum aluno com multa pendente.");
            }
        }
    }

    // Método para listar relatório de empréstimos concluídos
    public static void listarRelatorioEmprestimosConcluidos() throws SQLException {
        String sql = """
            SELECT nome_aluno, titulo, data_emprestimo, data_devolucao, data_devolucao_real, valor_multa
            FROM EmprestimosFinalizados
            WHERE status = 'DEVOLVIDO'
        """;

        try (Connection conn = DB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            System.out.println("\n=== Relatório de Empréstimos Concluídos ===");

            boolean encontrou = false;
            while (rs.next()) {
                encontrou = true;
                String nomeAluno = rs.getString("nome_aluno");
                String tituloLivro = rs.getString("titulo");
                LocalDate dataEmprestimo = rs.getDate("data_emprestimo").toLocalDate();
                LocalDate dataDevolucao = rs.getDate("data_devolucao").toLocalDate();
                LocalDate dataDevolucaoReal = rs.getDate("data_devolucao_real").toLocalDate();
                double multa = rs.getDouble("valor_multa");

                System.out.printf("Aluno: %s | Livro: %s | Empréstimo: %s | Devolução Prevista: %s | Devolução Real: %s | Multa: R$ %.2f%n",
                        nomeAluno, tituloLivro, dataEmprestimo, dataDevolucao, dataDevolucaoReal, multa);
            }

            if (!encontrou) {
                System.out.println("Nenhum empréstimo concluído encontrado.");
            }
        }
    }
}
