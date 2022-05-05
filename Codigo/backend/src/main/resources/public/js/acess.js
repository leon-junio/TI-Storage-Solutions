let usuario = {user:" ",pass:" ",token:" "}

function writeInStorage(obj){
	localStorage.setItem('usuario', JSON.stringify(obj));
}

function loginSucess(res_user,res_pass,res_token){
	usuario.user = res_user;
	usuario.pass = res_pass;
	usuario.token = res_token;
	writeInStorage(usuario);
}

function readStorage(){
	return JSON.parse(getStr());
}

function attObj(){
	usuario = readStorage();
}

function getStr(){
	return localStorage.getItem('usuario');
}

function onStart(){
	writeInStorage(usuario);
}