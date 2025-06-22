# 📁 Estrutura de Pastas

<details>
  <summary>📦 Projeto</summary>

  <details>
    <summary>🧰 Service</summary>
    <ul>
      <p><code>metodos usando os DAO indiretamente</code></p>
      <li>EventosService</li>
      <li>ParticipanteService</li>
      <li>PalestranteService</li>
      <li>InscricaoService</li>
    </ul>
  </details>
  
  <details> 
    <summary>💾 DAO</summary>
    <ul>
      <p><code>faz alteração no banco de dados</code></p>
      <li>EventosDao - <code>metodos CRUD para Eventos</code> </li>
      <li>ParticipanteDao - <code>metodos CRUD para Participante</code></li>
      <li>PalestranteDao - <code>metodos CRUD para Palestrante</code></li>
      <li>InscricaoDao <code>metodos CRUD para Inscricao</code></li>
    </ul>
  </details>
  <details>
 <summary>🖥️ View </summary>
    <details>
      <p></p>
      <summary>🔧 CRUD</summary>
      <ul>
        <p><code>Telas para vizualição de dados</code><br>usando os services permite as telas gerenciarem tudo de forma mais intuitiva</p>
        <li>criar - <code>pasta telas -> para cada tabela</code></li>
        <li>atualizar - <code>pasta telas -> cada tabela</code></li>
        <li>deletar - <code>pasta telas -> para cada tabela</code></li>
        <li>exibir - <code>pasta telas -> cada tabela</code></li>
      </ul>
    </details>
    <details>
      <summary>🏁 Início</summary>
      <ul>
        <li>TelaCadastrar - <code>permite o usuario se cadastrar</code></li>
        <li>TelaOrganizador - <code>permite fazer alterações livremente</code></li>
        <li>TelaUsuario - <code>nesta tela vc podera ver suas inscrições e eventos disponiveis</code></li>
      </ul>
    </details>
    <ul>
      <li>TelaInicial - inicia o projeto --> Tela de login</li>
    </ul>
    <details>
      <summary>resources</summary>
      <ul>
        <li>na pasta database voce pode encontrar o eventos.db</li>
      </ul>
    </details>
</details>

# hoje
