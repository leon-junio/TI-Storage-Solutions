-- MySQL Script generated by MySQL Workbench
-- Thu Apr  7 20:25:26 2022
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `mydb` ;

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `mydb` DEFAULT CHARACTER SET utf8 ;
USE `mydb` ;

-- -----------------------------------------------------
-- Table `mydb`.`Cliente`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Cliente` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Cliente` (
  `idCliente` INT NOT NULL,
  `nome` VARCHAR(500) NOT NULL,
  `email` VARCHAR(225) NOT NULL,
  `usuario` VARCHAR(85) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  PRIMARY KEY (`idCliente`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Endereco`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Endereco` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Endereco` (
  `idEndereco` INT NOT NULL,
  `rua` VARCHAR(255) NOT NULL,
  `bairro` VARCHAR(125) NOT NULL,
  `cidade` VARCHAR(125) NOT NULL,
  `cep` CHAR(8) NULL,
  `estado` CHAR(2) NULL,
  `pais` VARCHAR(55) NOT NULL,
  `numero` INT NOT NULL,
  PRIMARY KEY (`idEndereco`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Endereco_Estoque`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Endereco_Estoque` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Endereco_Estoque` (
  `estoque` INT NOT NULL,
  `endereco` INT NOT NULL,
  PRIMARY KEY (`estoque`, `endereco`),
  INDEX `fk_Estoque_has_Endereco_Endereco1_idx` (`endereco` ASC) VISIBLE,
  INDEX `fk_Estoque_has_Endereco_Estoque1_idx` (`estoque` ASC) VISIBLE,
  CONSTRAINT `fk_Estoque_has_Endereco_Estoque1`
    FOREIGN KEY (`estoque`)
    REFERENCES `mydb`.`Estoque` (`idEstoque`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Estoque_has_Endereco_Endereco1`
    FOREIGN KEY (`endereco`)
    REFERENCES `mydb`.`Endereco` (`idEndereco`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Endereco_Fornecedor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Endereco_Fornecedor` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Endereco_Fornecedor` (
  `endereco` INT NOT NULL,
  `fornecedor` INT NOT NULL,
  PRIMARY KEY (`endereco`, `fornecedor`),
  INDEX `fk_Endereco_has_Fornecedor_Fornecedor1_idx` (`fornecedor` ASC) VISIBLE,
  INDEX `fk_Endereco_has_Fornecedor_Endereco1_idx` (`endereco` ASC) VISIBLE,
  CONSTRAINT `fk_Endereco_has_Fornecedor_Endereco1`
    FOREIGN KEY (`endereco`)
    REFERENCES `mydb`.`Endereco` (`idEndereco`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Endereco_has_Fornecedor_Fornecedor1`
    FOREIGN KEY (`fornecedor`)
    REFERENCES `mydb`.`Fornecedor` (`idFornecedor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Entrada`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Entrada` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Entrada` (
  `idEntrada` INT NOT NULL,
  `data_entrada` DATE NOT NULL,
  `quantidade` INT NOT NULL,
  `estoque` INT NOT NULL,
  `produto` INT NOT NULL,
  PRIMARY KEY (`idEntrada`),
  INDEX `fk_Entrada_Estoque1_idx` (`estoque` ASC) VISIBLE,
  INDEX `fk_Entrada_Produto1_idx` (`produto` ASC) VISIBLE,
  CONSTRAINT `fk_Entrada_Estoque1`
    FOREIGN KEY (`estoque`)
    REFERENCES `mydb`.`Estoque` (`idEstoque`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Entrada_Produto1`
    FOREIGN KEY (`produto`)
    REFERENCES `mydb`.`Produto` (`idProduto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Estoque`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Estoque` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Estoque` (
  `idEstoque` INT NOT NULL,
  `nome` VARCHAR(125) NOT NULL,
  `capacidade` INT NOT NULL,
  `cliente` INT NOT NULL,
  `fornecedor` INT NOT NULL,
  PRIMARY KEY (`idEstoque`),
  INDEX `fk_Estoque_Cliente_idx` (`cliente` ASC) VISIBLE,
  INDEX `fk_Estoque_Fornecedor1_idx` (`fornecedor` ASC) VISIBLE,
  CONSTRAINT `fk_Estoque_Cliente`
    FOREIGN KEY (`cliente`)
    REFERENCES `mydb`.`Cliente` (`idCliente`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Estoque_Fornecedor1`
    FOREIGN KEY (`fornecedor`)
    REFERENCES `mydb`.`Fornecedor` (`idFornecedor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Fornecedor`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Fornecedor` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Fornecedor` (
  `idFornecedor` INT NOT NULL,
  `nome` VARCHAR(255) NOT NULL,
  `usuario` VARCHAR(85) NOT NULL,
  `senha` VARCHAR(255) NOT NULL,
  `tipoProduto` VARCHAR(85) NOT NULL,
  PRIMARY KEY (`idFornecedor`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Fornecimento_Produto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Fornecimento_Produto` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Fornecimento_Produto` (
  `produto` INT NOT NULL,
  `fornecedor` INT NOT NULL,
  `fornecimento` DATE NOT NULL,
  `quantidade` INT NOT NULL,
  PRIMARY KEY (`produto`, `fornecedor`),
  INDEX `fk_Produto_has_Fornecedor_Fornecedor1_idx` (`fornecedor` ASC) VISIBLE,
  INDEX `fk_Produto_has_Fornecedor_Produto1_idx` (`produto` ASC) VISIBLE,
  CONSTRAINT `fk_Produto_has_Fornecedor_Produto1`
    FOREIGN KEY (`produto`)
    REFERENCES `mydb`.`Produto` (`idProduto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Produto_has_Fornecedor_Fornecedor1`
    FOREIGN KEY (`fornecedor`)
    REFERENCES `mydb`.`Fornecedor` (`idFornecedor`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Itens_Retirada`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Itens_Retirada` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Itens_Retirada` (
  `produto` INT NOT NULL,
  `retirada` INT NOT NULL,
  `Quantidade` INT NOT NULL,
  INDEX `fk_Produto_has_Retirada_Retirada1_idx` (`retirada` ASC) VISIBLE,
  INDEX `fk_Produto_has_Retirada_Produto1_idx` (`produto` ASC) VISIBLE,
  PRIMARY KEY (`retirada`, `produto`),
  CONSTRAINT `fk_Produto_has_Retirada_Produto1`
    FOREIGN KEY (`produto`)
    REFERENCES `mydb`.`Produto` (`idProduto`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Produto_has_Retirada_Retirada1`
    FOREIGN KEY (`retirada`)
    REFERENCES `mydb`.`Retirada` (`idRetirada`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Produto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Produto` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Produto` (
  `idProduto` INT NOT NULL,
  `nome` VARCHAR(355) NOT NULL,
  `descricao` VARCHAR(1225) NULL,
  `quantidade` INT NOT NULL,
  `codigoBarras` VARCHAR(64) NULL,
  `unidade` VARCHAR(45) NOT NULL,
  `fabricacao` DATE NULL,
  `validade` DATE NULL,
  `marca` VARCHAR(45) NULL,
  `peso` FLOAT NULL,
  `estoque` INT NOT NULL,
  PRIMARY KEY (`idProduto`),
  INDEX `fk_Produto_Estoque1_idx` (`estoque` ASC) VISIBLE,
  CONSTRAINT `fk_Produto_Estoque1`
    FOREIGN KEY (`estoque`)
    REFERENCES `mydb`.`Estoque` (`idEstoque`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `mydb`.`Retirada`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `mydb`.`Retirada` ;

CREATE TABLE IF NOT EXISTS `mydb`.`Retirada` (
  `idRetirada` INT NOT NULL,
  `data_retirada` DATE NOT NULL,
  `observacao` VARCHAR(2000) NULL,
  `estoque` INT NOT NULL,
  PRIMARY KEY (`idRetirada`),
  INDEX `fk_Retirada_Estoque1_idx` (`estoque` ASC) VISIBLE,
  CONSTRAINT `fk_Retirada_Estoque1`
    FOREIGN KEY (`estoque`)
    REFERENCES `mydb`.`Estoque` (`idEstoque`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
