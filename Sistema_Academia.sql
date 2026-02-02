use sistema_academia;

create table fornecedores(
id_empresa int auto_increment primary key,
nome_empresa varchar(100) not null,
cnpj varchar(20) not null,
endereco varchar(255),
telefone varchar(20),
contato_principal varchar(50)
);
insert into fornecedores (nome_empresa, cnpj, endereco, telefone, contato_principal)
values ('Academia Manutenção', '00.000.000/0001-00', 'Rua das Flores, 123 - Centro', '11 9999-9999', 'João Silva'),
		('Max Suplementos Alimentares', '11.222.333/0001-44', 'Av. Industrial, 500 - São Paulo/SP', '(11) 4002-8922', 'Marcos Oliveira'),
		('Sport Wear Confecções', '22.333.444/0001-55', 'Rua da Moda, 88 - Brusque/SC', '(47) 3355-1010', 'Ana Julia'),
		('TecnoGym Manutenção', '33.444.555/0001-66', 'Rua das Oficinas, 12 - Curitiba/PR', '(41) 98765-4321', 'Eng. Roberto');