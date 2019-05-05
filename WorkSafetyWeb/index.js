var config = {
    apiKey: "AIzaSyA2zgFq2fZaGsrLHwAeZFt-4AoakoGgfMc",
    authDomain: "worksafety-f033a.firebaseapp.com",
    databaseURL: "https://worksafety-f033a.firebaseio.com",
    projectId: "worksafety-f033a",
    storageBucket: "worksafety-f033a.appspot.com",
    messagingSenderId: "548433572381"
};
firebase.initializeApp(config);

var d = new Date();
var t = d.getTime();

document.getElementById("form").addEventListener("submit", (e) => {
    var name = document.getElementById("name").value;
    e.preventDefault();
    createFunc(name);
    form.reset();
});

function createFunc(name) {
  
   // let db = firebase.database().ref("funcionarios/" + id);

    let db = firebase.database().ref("funcionarios");
    db.push().then(function (data) {
        var func = {
            id: data.key,
            name: name
        }
        firebase.database().ref("funcionarios/" + data.key).set(func);
    }).catch(function (error) {
        alert(error);
        console.error(error);
    });

    //db.set(func);
    document.getElementById("cardSection").innerHTML = '';
    readFunc();
}

function readFunc() {
    var func = firebase.database().ref("funcionarios/");
    func.on("child_added", function (data) {
        var funcValue = data.val();
        document.getElementById("cardSection").innerHTML += `

        <div class="card text-center">
            <div class="card-header">
                ${data.key}
            </div>
            <div class="card-body">
                <p class="card-text">${funcValue.name}</p>
                <button type="submit" style="color:white" class="btn btn-warning" onclick="updateFunc('${data.key}','${funcValue.name}')"><i class="far fa-edit"></i> Editar</button>
                <button type="submit" class="btn btn-danger" onclick="deleteFunc('${data.key}')"><i class="fas fa-trash-alt"></i> Deletar</button>
            </div>
        </div>       
        <br />
        `
    });

}

function reset() {
    document.getElementById("firstSection").innerHTML = `
    <form class="p-4 mb-4" id="form">
        <div class="form-group">
            <input type="text" class="form-control" id="name" placeholder="Nome">
        </div>

        <button type="submit" id="button1" class="btn btn-primary"><i class="fas fa-plus-square"></i> Novo</button>
        <button style="display: none" id="button2" class="btn btn-success">Alterar</button>
        <button style="display: none" id="button3" class="btn btn-danger">Cancelar</button>
    </form>
    `;

    document.getElementById("form").addEventListener("submit", (e) => {
        var name = document.getElementById("name").value;
        e.preventDefault();
        createFunc(name);
        form.reset();
    });
}

function updateFunc(id, name) {
    document.getElementById("firstSection").innerHTML = `
    <form class="p-4 mb-4" id="form2">
    <div class="form-group">
        <input type="text" class="form-control" id="name" placeholder="Nome">
    </div>

    <button style="display: none" id="button1" class="btn btn-primary"><i class="fas fa-plus-square"></i> Novo</button>
    <button type="submit" style="display: inline-block" id="button2" class="btn btn-success"><i class="far fa-check-circle"></i> Sim</button>
    <button style="display: inline-block" id="button3" class="btn btn-danger"><i class="fas fa-times"></i> Não</button>
    </form>
    `;
    document.getElementById("form2").addEventListener("submit", (e) => {
        e.preventDefault();
    });
    document.getElementById("button2").addEventListener("click", (e) => {
        updateFunc2(id, document.getElementById("name").value);
    });
    document.getElementById("button3").addEventListener("click", (e) => {
        reset();
    });
    document.getElementById("name").value = name;
}

function updateFunc2(id, name) {
    var funcUpdated = {
        id: id,
        name: name
    }
    let db = firebase.database().ref("funcionarios/" + id);
    db.set(funcUpdated);

    document.getElementById("cardSection").innerHTML = '';
    readFunc();
    reset();
}

function deleteFunc(id) {
    var func = firebase.database().ref("funcionarios/" + id);
    func.remove();
    reset();
    document.getElementById("cardSection").innerHTML = '';
    readFunc();
}