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
        String atualizaEstoque = "UPDATE Livros SET quantidade_estoque = quantidade_estoque - 1 WHERE id_livro = ?";
        String insereEmprestimo = "INSERT INTO Emprestimos (id_aluno, id_livro, data_emprestimo, data_devolucao, status) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            try (PreparedStatement stmt = conn.prepareStatement(verificaAluno)) {
                stmt.setInt(1, idAluno);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        throw new SQLException("Aluno não encontrado.");
                    }
                }
            }

            int estoque = 0;
            try (PreparedStatement stmt = conn.prepareStatement(verificaLivro)) {
                stmt.setInt(1, idLivro);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        estoque = rs.getInt("quantidade_estoque");
                    } else {
                        conn.rollback();
                        throw new SQLException("Livro não encontrado.");
                    }
                }
            }

            if (estoque <= 0) {
                conn.rollback();
                throw new SQLException("Livro sem estoque disponível.");
            }

            try (PreparedStatement stmt = conn.prepareStatement(atualizaEstoque)) {
                stmt.setInt(1, idLivro);
                stmt.executeUpdate();
            }

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
        }
    }

    // Método para registrar a devolução de um empréstimo
    public static void registrarDevolucao(int idEmprestimo) throws SQLException {
        String buscaEmprestimo = "SELECT id_livro, data_devolucao FROM Emprestimos WHERE id_emprestimo = ?";
        String atualizaEstoque = "UPDATE Livros SET quantidade_estoque = quantidade_estoque + 1 WHERE id_livro = ?";
        String atualizaEmprestimo = "UPDATE Emprestimos SET data_devolucao_real = ?, valor_multa = ?, status = 'DEVOLVIDO' WHERE id_emprestimo = ?";

        try (Connection conn = DB.getConnection()) {
            conn.setAutoCommit(false);

            int idLivro = -1;
            LocalDate dataPrevista = null;

            try (PreparedStatement stmt = conn.prepareStatement(buscaEmprestimo)) {
                stmt.setInt(1, idEmprestimo);
                try (ResultSet rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        idLivro = rs.getInt("id_livro");
                        dataPrevista = rs.getDate("data_devolucao").toLocalDate();
                    } else {
                        conn.rollback();
                        throw new SQLException("Empréstimo não encontrado.");
                    }
                }
            }

            try (PreparedStatement stmt = conn.prepareStatement(atualizaEstoque)) {
                stmt.setInt(1, idLivro);
                stmt.executeUpdate();
            }

            LocalDate dataDevolvida = LocalDate.now();
            double multa = calcularMulta(dataPrevista, dataDevolvida);

            try (PreparedStatement stmt = conn.prepareStatement(atualizaEmprestimo)) {
                stmt.setDate(1, Date.valueOf(dataDevolvida));
                stmt.setDouble(2, multa);
                stmt.setInt(3, idEmprestimo);
                stmt.executeUpdate();
            }

            conn.commit();

            if (multa > 0) {
                System.out.println("Devolução atrasada. Multa aplicada: R$ " + multa);
            } else {
                System.out.println("Devolução realizada no prazo. Sem multa.");
            }
        }
    }

    private static double calcularMulta(LocalDate dataPrevista, LocalDate dataDevolvida) {
        if (dataDevolvida.isAfter(dataPrevista)) {
            long diasAtraso = java.time.temporal.ChronoUnit.DAYS.between(dataPrevista, dataDevolvida);
            return diasAtraso * 3.0;
        }
        return 0.0;
    }

    public static void listarAlunosComMulta() throws SQLException {
        String sql = """
            SELECT a.nome_aluno, e.valor_multa
            FROM Emprestimos e
            JOIN Alunos a ON e.id_aluno = a.id_aluno
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
                System.out.println("Nenhum aluno com multa.");
            }
        }
    }

    public static void listarRelatorioEmprestimosConcluidos() throws SQLException {
        String sql = """
            SELECT nome_aluno, titulo, data_emprestimo, data_devolucao, data_devolucao_real, valor_multa
            FROM RelatorioEmprestimos
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
                System.out.println("Nenhum relatório de empréstimo concluído encontrado.");
            }
        }
    }
    
}
