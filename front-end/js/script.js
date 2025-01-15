document.addEventListener("DOMContentLoaded", function () {
    const petsContainer = document.getElementById('pets-container');
    const adotantesContainer = document.getElementById('adotante-container');
    const formCadastrarAdotante = document.querySelector("#form-cadastrar-adotante");
    const formCadastrarPet = document.querySelector("#form-cadastrar-pet");

  // Função para exibir a lista de pets
const modal = document.getElementById("modal-pet-adocao");
const closeModal = document.querySelector(".close-modal-adocao");
const audio = document.getElementById("audio-music-adocao"); // Obtém o elemento de áudio

// Seleciona o botão e o elemento de áudio
const pauseMusicBtn = document.getElementById("pause-music-btn-adocao");
const music = document.getElementById("audio-music-adocao");

// Verifica se o elemento de áudio existe
if (music) {
    // Adiciona o evento de clique no botão para pausar a música
    pauseMusicBtn.addEventListener("click", () => {
        if (music.paused) {
            music.play();  // Toca a música se estiver pausada
            pauseMusicBtn.textContent = "Pausar Música"; // Altera o texto do botão
        } else {
            music.pause(); // Pausa a música se estiver tocando
            pauseMusicBtn.textContent = "Continuar Música"; // Altera o texto do botão
        }
    });
} else {
    console.error("Elemento de áudio não encontrado.");
}

// Define o volume da música para 50% (0.5)
audio.volume = 1;

const preencherModal = (pet) => {
    // Preenchendo o campo oculto com o ID do pet
    const petIdField = document.getElementById("pet-id");
    if (petIdField) {
        petIdField.value = pet.id;
    } else {
        console.error("Campo de ID do pet não encontrado!");
    }

    // Preenchendo as demais informações do modal
    document.getElementById("modal-pet-nome-adocao").textContent = pet.nome || "Nome não disponível";
    document.getElementById("modal-pet-foto-adocao").src = pet.foto
        ? `data:image/jpeg;base64,${pet.foto}`
        : "placeholder.jpg"; // Caminho para uma imagem padrão caso não tenha foto
    document.getElementById("modal-pet-idade-adocao").textContent = `Idade: ${pet.idade || "Idade não disponível"} anos`;
    document.getElementById("modal-pet-especie-adocao").textContent = `Espécie: ${pet.especie || "Espécie não disponível"}`;
    document.getElementById("modal-pet-raca-adocao").textContent = `Raça: ${pet.raca || "Raça não disponível"}`;
    document.getElementById("modal-pet-descricao-adocao").textContent = `Descrição: ${pet.descricao || "Sem descrição"}`;
};

const listarAdotantes = () => {
    const btnDoacao = document.getElementById('btn-fazer-doacao-adocao');
    const adotanteSelect = document.getElementById('adotante-select-adocao');
    
    // Inicializa a variável adotantes
    let adotantes = [];
    
    // Função para buscar o nome do adotante pelo ID
    const buscarNomeAdotante = (adotanteId) => {
        const adotante = adotantes.find(a => a.id == adotanteId);
        return adotante ? adotante.nome : "Adotante não encontrado";
    };

    // Verifique se o elemento existe antes de adicionar o evento
    if (btnDoacao && adotanteSelect) {
        btnDoacao.addEventListener('click', () => {
            const adotanteId = adotanteSelect ? adotanteSelect.value : '';
            
            if (adotanteId) {
                const nomeAdotante = buscarNomeAdotante(adotanteId);
        
                // Criação do objeto AdocaoDTO com dataAdocao
                const adocaoDTO = {
                    pet_id: parseInt(document.getElementById('pet-id').value, 10),  // Alteração aqui
                    adotante_id: parseInt(adotanteId, 10),  // Alteração aqui
                    dataAdocao: new Date().toISOString().split('.')[0]
                };                
                // Envio para a API
                fetch('http://localhost:8080/adocoes/registrar', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(adocaoDTO)  // Converte o objeto para JSON
                })
                .then(response => response.json())
                .then(data => {
                    if (data.status === 'sucesso') {
        
                        // Criação da mensagem de sucesso
                        const mensagemSucesso = document.createElement('div');
                        mensagemSucesso.textContent = 'Adoção realizada com sucesso!';
                        mensagemSucesso.style.color = 'green';
                        mensagemSucesso.style.fontWeight = 'bold';
                        mensagemSucesso.style.marginTop = '10px';
                        mensagemSucesso.style.textAlign = 'center';  // Centraliza a mensagem
                        mensagemSucesso.style.padding = '10px';
                        mensagemSucesso.style.backgroundColor = '#e0ffe0';  // Cor de fundo verde claro
                        mensagemSucesso.style.border = '1px solid green';
                        mensagemSucesso.style.borderRadius = '5px';
                        mensagemSucesso.style.position = 'fixed';  // Fixa a mensagem no topo
                        mensagemSucesso.style.top = '20px';  // 20px abaixo do topo da página
                        mensagemSucesso.style.left = '50%';  // Centraliza a mensagem horizontalmente
                        mensagemSucesso.style.transform = 'translateX(-50%)';  // Ajusta para o centro
        
                        // Adiciona a mensagem ao corpo da página
                        document.body.appendChild(mensagemSucesso);
        
                        // Limpa o campo do adotante
                        adotanteSelect.value = '';  // Limpa a seleção do adotante
        
                        // Pause a música antes de fechar o modal
                        const audio = document.getElementById("audio-music-adocao");
                        if (audio) {
                            audio.pause();  // Pausa a música
                            audio.currentTime = 0;  // Reseta o tempo da música para o início
                        }
        
                        // Fecha o modal
                        const modal = document.getElementById('modal-pet-adocao');
                        if (modal) {
                            modal.style.display = 'none';  // Fecha o modal
                        }
        
                        // Remover o pet da lista sem recarregar a página
                        const petId = document.getElementById('pet-id').value;
                        const petSelect = document.getElementById('pet-select');  // Verifica se pet-select existe
                        if (petSelect) {  // Verifique se o petSelect existe antes de tentar acessar
                            const petOption = petSelect.querySelector(`option[value='${petId}']`);
                            if (petOption) {
                                petOption.remove();  // Remove a opção do pet da lista
                            }
                        }
        
                        // Chama a função carregarPets() para atualizar a lista de pets
                        carregarPets();  // Atualiza a lista de pets
        
                        // Chama a função para carregar a lista de adotados após a adoção
                        carregarAdotados();  // Atualiza a lista de adotados
        
                        // Remove a mensagem de sucesso após 3 segundos
                        setTimeout(() => {
                            mensagemSucesso.remove();
                        }, 3000);
                    } else {
                        console.error('Erro ao registrar adoção:', data.mensagem);
                    }
                })
                .catch(error => {
                    console.error('Erro na requisição:', error);
                });
            } else {
                console.error('Nenhum adotante selecionado!');
            }
        });
        
    } else {
        console.error('Elementos não encontrados!');
    }

    // Fazendo a requisição para a API que retorna a lista de adotantes
    fetch('http://localhost:8080/adotantes/listar') // URL da API que retorna a lista de adotantes
        .then(response => response.json())  // Converte a resposta para JSON
        .then(data => {
            adotantes = data;  // Armazena os adotantes para buscar o nome depois
            data.forEach(adotante => {
                const option = document.createElement("option");
                option.value = adotante.id;
                option.textContent = adotante.nome;
                adotanteSelect.appendChild(option);
            });
        })
        .catch(error => {
            console.error("Erro ao carregar adotantes:", error);
        });

};

async function carregarAdotados() {
    try {
        const response = await fetch('http://localhost:8080/adocoes/listar-adotados');

        if (!response.ok) {
            console.error('Erro na requisição:', response.statusText);
            throw new Error('Erro ao carregar a lista de adotados');
        }

        const adocoes = await response.json();

        const container = document.getElementById('adotado-container');
        container.innerHTML = '';

        if (adocoes.length === 0) {
            container.innerHTML = '<p>Nenhum adotado encontrado.</p>';
            return;
        }

        adocoes.forEach(adocao => {
            // Verificar se as propriedades 'pet_nome' e 'adotante_nome' existem antes de acessar
            if (adocao.pet_nome && adocao.adotante_nome) {
                const divAdocao = document.createElement('div');
                divAdocao.classList.add('adocao-card');
                divAdocao.innerHTML = `
                    <div class="adocao-header">
                        <h3>${adocao.pet_nome}</h3>
                        <p><strong>Espécie:</strong> ${adocao.pet_especie}</p>
                        <p><strong>Raça:</strong> ${adocao.pet_raca || 'Não disponível'}</p>
                        <p><strong>Idade:</strong> ${adocao.pet_idade} anos</p>
                        <p><strong>Descrição:</strong> ${adocao.pet_descricao || 'Não disponível'}</p>
                        <p><strong>Status:</strong> ${adocao.pet_status}</p>
                    </div>
                    <div class="adocao-footer">
                        <h4>Adotante: ${adocao.adotante_nome}</h4>
                        <p><strong>Endereço:</strong> ${adocao.adotante_endereco || 'Não disponível'}</p>
                        <p><strong>Data da Adoção:</strong> ${new Date(adocao.dataAdocao).toLocaleString()}</p>
                    </div>
                    <div class="adocao-image">
                        <img src="data:image/jpeg;base64,${adocao.pet_foto}" alt="Foto do Pet" class="pet-foto" />
                    </div>
                `;
                container.appendChild(divAdocao);
            } else {
                console.error('Erro: Pet ou Adotante não encontrado na adoção', adocao);
            }
        });
    } catch (error) {
        console.error('Erro ao carregar adotados:', error);
        const container = document.getElementById('adotado-container');
        container.innerHTML = '<p>Ocorreu um erro ao carregar a lista de adotados. Tente novamente mais tarde.</p>';
    }
}

carregarAdotados();
// Certifique-se de que a função é chamada após o carregamento do DOM
document.addEventListener('DOMContentLoaded', listarAdotantes);

const exibirModal = (pet) => {
    preencherModal(pet);
    listarAdotantes();
    modal.style.display = "block";

    // Tocar a música e reiniciá-la
    audio.currentTime = 0; // Reinicia a música para o início
    audio.play(); // Começa a tocar a música
};

// Fecha o modal e reinicia a música
closeModal.addEventListener("click", () => {
    modal.style.display = "none";
    audio.currentTime = 0; // Reinicia a música
    audio.pause(); // Pausa a música quando o modal for fechado
});

    const exibirPets = (pets) => {
        petsContainer.innerHTML = ""; // Limpa o contêiner antes de adicionar os novos pets
    
        pets.forEach(pet => {
            const petDiv = document.createElement("div");
            petDiv.classList.add("pet");
    
            const petHTML = `
                <div class="pet-card">
                    ${pet.foto ? `<img src="data:image/jpeg;base64,${pet.foto}" alt="${pet.nome}" class="pet-foto" />` : ""}
                    <div class="pet-info">
                        <h3>${pet.nome}</h3>
                        <p>Idade: ${pet.idade} anos</p>
                        <p>Espécie: ${pet.especie}</p>
                        <p>Raça: ${pet.raca}</p>
                        <p>Descrição: ${pet.descricao}</p>
                    </div>
                    <button class="adote-me-btn">Adote-me</button>
                </div>
            `;
            petDiv.innerHTML = petHTML;
    
            const botaoAdoteMe = petDiv.querySelector(".adote-me-btn");
            botaoAdoteMe.addEventListener("click", () => {
                exibirModal(pet); // Abre o modal com informações do pet
            });
    
            petsContainer.appendChild(petDiv);
        });
    };

    // Função para exibir a lista de adotantes
    const exibirAdotantes = (adotantes) => {
        adotantesContainer.innerHTML = ""; // Limpa o contêiner antes de adicionar os novos adotantes

        adotantes.forEach(adotante => {
            // Criação do HTML para cada adotante
            const adotanteDiv = document.createElement('div');
            adotanteDiv.classList.add('adotante'); // Classe CSS para estilizar cada adotante

            const adotanteHTML = `
                <div class="adotante-card">
                    <div class="adotante-info">
                        <h3>${adotante.nome}</h3>
                        <p>Telefone: ${adotante.telefone}</p>
                        <p>Email: ${adotante.email}</p>
                        <p>Endereço: ${adotante.endereco}</p>
                    </div>
                </div>
            `;
            adotanteDiv.innerHTML = adotanteHTML;
            adotantesContainer.appendChild(adotanteDiv);
        });
    };

    // Função para carregar a lista de pets da API
    const carregarPets = () => {
        fetch("http://localhost:8080/pets/listar")
            .then(response => response.json())
            .then(data => {
                exibirPets(data); // Chama a função para exibir os pets
            })
            .catch(error => {
                console.error("Erro ao carregar pets:", error);
                alert("Erro ao carregar a lista de pets.");
            });
    };

    // Função para carregar a lista de adotantes da API
    const carregarAdotantes = () => {
        fetch("http://localhost:8080/adotantes/listar")
            .then(response => response.json())
            .then(data => {
                exibirAdotantes(data); // Chama a função para exibir os adotantes
            })
            .catch(error => {
                console.error("Erro ao carregar adotantes:", error);
                alert("Erro ao carregar a lista de adotantes.");
            });
    };

    // Função para cadastrar adotantes
    formCadastrarAdotante.addEventListener("submit", function (event) {
        event.preventDefault(); // Impede o envio padrão do formulário

        // Captura os dados do formulário
        const nomeAdotante = document.getElementById("nome-adotante").value;
        const telefoneAdotante = document.getElementById("telefone-adotante").value;
        const emailAdotante = document.getElementById("email-adotante").value;
        const enderecoAdotante = document.getElementById("endereco-adotante").value;

        // Validação básica
        if (!nomeAdotante || !telefoneAdotante || !emailAdotante || !enderecoAdotante) {
            alert("Por favor, preencha os campos obrigatórios corretamente.");
            return;
        }

        const dadosAdotante = new URLSearchParams();
        dadosAdotante.append("nome", nomeAdotante);
        dadosAdotante.append("telefone", telefoneAdotante);
        dadosAdotante.append("email", emailAdotante);
        dadosAdotante.append("endereco", enderecoAdotante);

        fetch("http://localhost:8080/adotantes/cadastro", {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: dadosAdotante.toString(),
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(err => {
                        throw new Error(err || "Erro ao salvar os dados do adotante.");
                    });
                }
                return response.json();
            })
            .then(data => {
                alert("Adotante cadastrado com sucesso!");
                formCadastrarAdotante.reset();
                document.getElementById('modal-cadastrar-adotante').style.display = 'none';
                carregarAdotantes(); // Atualiza a lista de adotantes após o cadastro
            })
            .catch(error => {
                console.error("Erro:", error.message);
                alert("Erro ao cadastrar o adotante: " + error.message);
            });
    });

    // Função para cadastrar pets
    formCadastrarPet.addEventListener("submit", function (event) {
        event.preventDefault(); // Impede o envio padrão do formulário
    
        // Captura os dados do formulário
        const nomePet = document.getElementById("nome-pet").value;
        const idadePet = document.getElementById("idade-pet").value;
        const especiePet = document.getElementById("especie-pet").value;
        const racaPet = document.getElementById("raca-pet").value;
        const descricaoPet = document.getElementById("descricao-pet").value;
        const fotoPet = document.getElementById("foto-pet").files[0];
    
        // Validação básica
        if (!nomePet || !idadePet || !especiePet || !racaPet || !descricaoPet) {
            alert("Por favor, preencha todos os campos obrigatórios.");
            return;
        }
    
        const formData = new FormData();
        formData.append("nome", nomePet);
        formData.append("idade", idadePet);
        formData.append("especie", especiePet);
        formData.append("raca", racaPet);
        formData.append("descricao", descricaoPet);
        formData.append("status", "Disponível"); // Adiciona o status como "Disponível"
        if (fotoPet) {
            formData.append("foto", fotoPet);
        }
    
        fetch("http://localhost:8080/pets/cadastro", {
            method: "POST",
            body: formData,
        })
            .then(response => {
                if (!response.ok) {
                    return response.text().then(err => {
                        throw new Error(err || "Erro ao salvar os dados do pet.");
                    });
                }
                return response.json();
            })
            .then(data => {
                alert("Pet cadastrado com sucesso!");
                formCadastrarPet.reset();
                document.getElementById('modal-cadastrar-pet').style.display = 'none';
                carregarPets(); // Atualiza a lista de pets após o cadastro
            })
            .catch(error => {
                console.error("Erro:", error.message);
                alert("Erro ao cadastrar o pet: " + error.message);
            });
    });

    // Carrega os pets e adotantes assim que a página for carregada
    carregarPets();
    carregarAdotantes();
});