-- Adicione esta coluna se as tabelas já existirem no Oracle:
ALTER TABLE EQUIPE_MANUTENCAO ADD DIRECAO VARCHAR2(10) DEFAULT 'NORTE' NOT NULL;

-- Se você recriar a tabela, use DIRECAO na definição:
-- DIRECAO VARCHAR2(10) NOT NULL
