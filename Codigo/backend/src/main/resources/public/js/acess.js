let usuario = { user: "null", pass: "null", token: "null" }
let estoque = { id: "null", nome: "null", capacidade: "null" }

function writeInStorage(obj) {
    localStorage.setItem('usuario', JSON.stringify(obj));
}

function setEstoque(obj) {
    localStorage.setItem('estoque', JSON.stringify(obj));
}

function loginSucess(res_user, res_pass, res_token) {
    usuario.user = res_user;
    usuario.pass = res_pass;
    usuario.token = res_token;
    writeInStorage(usuario);
}

function showEstoque(id, nome, capacidade) {
    estoque.id = id;
    estoque.nome = nome;
    estoque.capacidade = capacidade;
    setEstoque(estoque);
}

function readStorage() {
    return JSON.parse(getStr());
}

function readStorageEst() {
    return JSON.parse(getStorage());
}

function attObj() {
    usuario = readStorage();
}

function attEst() {
    estoque = readStorage();
}

function getStr() {
    return localStorage.getItem('usuario');
}

function getStorage() {
    return localStorage.getItem('estoque');
}

function onStart() {
    writeInStorage(usuario);
    setEstoque(estoque);
}

function logout() {
    onStart();
    window.location.href = "../index.html";
}