package Main;

import Model.TrechoRodovia;
import dao.EquipeManutencaoDAO;
import dao.IntervencaoOperacionalDAO;
import dao.RelatorioPrioridadeDAO;
import dao.TrechoRodoviaDAO;
import database.ConexaoBanco;
import exception.CredenciaisInvalidasException;
import model.EquipeManutencao;
import model.IntervencaoRegistro;
import service.GeradorRelatorio;

import java.sql.Connection;
import java.util.List;
import java.util.Scanner;

/** Menu interativo para validar os dados antes de persistir no Oracle. */
public class Main {
    private static final Scanner SCANNER = new Scanner(System.in);
    private static final EquipeManutencaoDAO EQUIPES = new EquipeManutencaoDAO();
    private static final TrechoRodoviaDAO TRECHOS = new TrechoRodoviaDAO();
    private static final IntervencaoOperacionalDAO INTERVENCOES = new IntervencaoOperacionalDAO();

    public static void main(String[] args) {
        try (Connection ignored = ConexaoBanco.getConexao()) {
            executarMenu();
        } catch (CredenciaisInvalidasException e) {
            System.err.println(e.getMessage());
        } catch (RuntimeException e) {
            System.err.println("Erro ao iniciar o sistema: " + e.getMessage());
        }
    }

    private static void executarMenu() {
        int opcao;
        do {
            System.out.println("\n===== MOTIVA - MENU =====");
            System.out.println("1 - Cadastrar equipe");
            System.out.println("2 - Listar equipes");
            System.out.println("3 - Cadastrar trecho");
            System.out.println("4 - Listar trechos");
            System.out.println("5 - Cadastrar intervenção");
            System.out.println("6 - Listar intervenções");
            System.out.println("7 - Gerar e salvar relatório");
            System.out.println("0 - Sair");
            opcao = lerInt("Escolha uma opção: ");
            try {
                switch (opcao) {
                    case 1 -> cadastrarEquipe();
                    case 2 -> EQUIPES.listarTodas().forEach(System.out::println);
                    case 3 -> cadastrarTrecho();
                    case 4 -> TRECHOS.listarTodas().forEach(System.out::println);
                    case 5 -> cadastrarIntervencao();
                    case 6 -> INTERVENCOES.listarTodas().forEach(System.out::println);
                    case 7 -> gerarRelatorio();
                    case 0 -> System.out.println("Sistema encerrado.");
                    default -> System.out.println("Opção inválida.");
                }
            } catch (RuntimeException e) {
                System.err.println("Não foi possível concluir a operação: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static void cadastrarEquipe() {
        String nome = lerTexto("Nome da equipe: ");
        String especialidade = lerTexto("Especialidade (ROCADA, PODA ou PULVERIZACAO): ");
        String direcao = lerOpcao("Direção", "NORTE", "SUL", "LESTE", "OESTE");
        int id = EQUIPES.inserir(new EquipeManutencao(nome, especialidade, direcao));
        System.out.println("Equipe cadastrada com ID " + id + ".");
    }

    private static void cadastrarTrecho() {
        int km = lerIntNaoNegativo("KM do trecho: ");
        double altura = lerDoubleNaoNegativo("Altura da vegetação (cm): ");
        String terreno = lerOpcao("Tipo de terreno", "UMIDO", "SECO").toLowerCase();
        List<EquipeManutencao> equipes = EQUIPES.listarTodas();
        Integer equipeId = null;
        if (!equipes.isEmpty()) {
            equipes.forEach(System.out::println);
            int escolha = lerInt("ID da equipe (0 para sem equipe): ");
            if (escolha != 0) { EQUIPES.buscarPorId(escolha); equipeId = escolha; }
        }
        TRECHOS.inserir(new TrechoRodovia(km, altura, terreno, equipeId));
        System.out.println("Trecho cadastrado com sucesso.");
    }

    private static void cadastrarIntervencao() {
        int km = lerIntNaoNegativo("KM do trecho: ");
        TRECHOS.buscarPorId(km);
        String tipo = lerOpcao("Tipo", "ROCADA_MANUAL", "ROCADA_MECANIZADA", "PULVERIZACAO");
        String responsavel = lerTexto("Responsável: ");
        int id = INTERVENCOES.inserir(new IntervencaoRegistro(km, tipo, responsavel));
        System.out.println("Intervenção cadastrada com ID " + id + ".");
    }

    private static void gerarRelatorio() {
        TrechoRodovia[] trechos = TRECHOS.listarTodas().toArray(new TrechoRodovia[0]);
        if (trechos.length == 0) { System.out.println("Cadastre ao menos um trecho."); return; }
        new GeradorRelatorio().gerarRelatorio(trechos);
        System.out.println("Relatório salvo no banco.");
        new RelatorioPrioridadeDAO().listarTodas().forEach(System.out::println);
    }

    private static String lerOpcao(String campo, String... opcoes) {
        while (true) {
            String valor = lerTexto(campo + " [" + String.join(", ", opcoes) + "]: ").toUpperCase();
            for (String opcao : opcoes) if (opcao.equals(valor)) return valor;
            System.out.println("Valor inválido. Escolha uma das opções permitidas.");
        }
    }

    private static String lerTexto(String mensagem) {
        while (true) { System.out.print(mensagem); String valor = SCANNER.nextLine().trim(); if (!valor.isBlank()) return valor; System.out.println("Campo obrigatório."); }
    }
    private static int lerInt(String mensagem) { while (true) { try { System.out.print(mensagem); return Integer.parseInt(SCANNER.nextLine().trim()); } catch (NumberFormatException e) { System.out.println("Digite um número inteiro válido."); } } }
    private static int lerIntNaoNegativo(String mensagem) { while (true) { int v = lerInt(mensagem); if (v >= 0) return v; System.out.println("O valor não pode ser negativo."); } }
    private static double lerDoubleNaoNegativo(String mensagem) { while (true) { try { System.out.print(mensagem); double v = Double.parseDouble(SCANNER.nextLine().trim().replace(',', '.')); if (v >= 0) return v; } catch (NumberFormatException ignored) { } System.out.println("Digite um número decimal não negativo."); } }
}
