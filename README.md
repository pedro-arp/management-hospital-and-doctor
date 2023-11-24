# Management Hospital and Doctor

Este projeto consiste em um CRUD (Create, Read, Update, Delete) que utiliza Docker containers para se comunicar com um banco de dados MySQL. O sistema oferece operações de gerenciamento para hospitais e médicos.

## Instruções de Execução

Certifique-se de ter o Docker e o Docker Compose instalados em seu sistema antes de prosseguir.

### Passos para Execução

```bash
# Clone o repositório
git clone https://github.com/pedro-arp/management-hospital-and-doctor.git

# Navegue até o diretório do projeto
cd management-hospital-and-doctor

# Execute o Docker Compose para configurar os containers
docker-compose up -d

# Execute a classe `CrudTest` para interagir com o sistema
./gradlew run --args='academy.devdojo.maratonajava.javacore.globalsolution.test.CrudTest'

## Menu de Operações

1. **Hospital Management**
   - 1.1 Search for hospital
   - 1.2 Delete hospital
   - 1.3 Save hospital
   - 1.4 Update hospital
   - 1.9 Go Back

2. **Doctor Management**
   - 2.1 Search for doctor
   - 2.2 Delete doctor
   - 2.3 Save doctor
   - 2.4 Update doctor
   - 2.9 Go Back

0. **Exit**

Escolha a opção desejada digitando o número correspondente e pressionando Enter.

## Observações

- Certifique-se de parar os containers após o uso:

```bash
docker-compose down
