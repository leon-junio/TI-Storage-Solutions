function alerta(txt) {
    alert(txt);
}

function getData() {
    console.log(user + " " + pass + " " + token);
}

function confirme(txt) {
    return confirm(txt);
}

function pergunta(string, href) {
    if (confirm(string)) {
        window.location.href = href;
    }
}

//Sistema do MODAL
function modal() {
    var modal = document.getElementById("myModal");
    var btn = document.getElementById("myBtn");
    var span = document.getElementsByClassName("close")[0];
    btn.onclick = function() {
        modal.style.display = "block";
    }
    span.onclick = function() {
        modal.style.display = "none";
    }
    window.onclick = function(event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}