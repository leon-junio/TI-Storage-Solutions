function alerta(txt) {
    alert(txt);
}

function getData() {
    console.log(user + " " + pass + " " + token);
}

function confirme(txt) {
    return confirm(txt);
}

const axiosReq = axios.create({
    baseURL: 'https://localhost:6789'
});