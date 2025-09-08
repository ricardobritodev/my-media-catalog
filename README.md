**MyMediaCatalog**

Catálogo simples de filmes/séries em Java que consome a API OMDB e gerencia favoritos em arquivo. Desenvolvido para fins educacionais.

**Funcionalidades:**
- Busca por título via API OMDB.
- Exibe detalhes (título, ano, nota IMDb, sinopse).
- Adiciona e lista favoritos salvos em `favoritos.txt`.

**Pré-requisitos:**
- Java 11+
- Biblioteca GSON 2.10.1 (incluída em `lib/`)
- Chave da API OMDB (em `config.properties`)

**Configuração:**
1. Clone o repositório:
   ```bash  
   git clone https://github.com/seu-usuario/MyMediaCatalog.git  
   ```  
2. Adicione a chave da API em `config.properties`:
   ```properties  
   omdb.api.key=sua-chave-aqui  
   ```  
3. Baixe o GSON (se necessário) e coloque em `lib/`.

**Execução:**
```bash  
javac -cp lib/gson-2.10.1.jar src/com/mymediacatalog/*.java  
java -cp src:lib/gson-2.10.1.jar com.mymediacatalog.MyMediaCatalog  
```  
(Windows: use `;` no classpath).

**Uso:**
- Menu interativo: buscar, listar favoritos ou sair.
- Favoritos salvos em `favoritos.txt`.

**Estrutura:**
- `src/com/mymediacatalog/`: Código-fonte (Media, MyMediaCatalog, ApiClient).
- `lib/`: Biblioteca GSON.
- `config.properties`: Chave da API (não versionado).
- `favoritos.txt`: Favoritos (gerado).

