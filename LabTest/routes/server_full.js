var app = require('express')();
var http = require('http').Server(app);
var io = require('socket.io')(http);
var mongodb = require('mongodb');

var MongoClient = mongodb.MongoClient;
var url = 'mongodb://localhost:27017/QLSV';


MongoClient.connect(url, function(err, client) {
    if (err) {
        console.log('Unable to connect to the mongoDB server. Error:', err);
    } else {
        //HURRAY!! We are connected. :)
        let db = client.db('QLSV');
        console.log('Connection established to', url);
        collection = db.collection('users');
        collection_pro = db.collection('students');
    }
});


app.get('/', function(req, res) {
    res.sendfile('qlsv.html');
});


io.on('connection', function(socket) {
    socket.on('login', function(email, password) {
        console.log(email + "login");

        var cursor = collection.find({ email: email });
        cursor.each(function(err, doc) {
            if (err) {
                console.log(err);
                socket.emit('login', false);
            } else {
                if (doc != null) {
                    if (doc.password == password) {
                        socket.emit('login', true);
                    } else {
                        socket.emit('login', false);
                    }

                }
            }
        });

    });

    socket.on('register', function(name, password, email) {
        console.log(name + "register");

        var user = { name: name, password: password, email: email };

        collection.insert(user, function(err, result) {
            if (err) {
                console.log(err);
                socket.emit('register', false);
            } else {
                console.log('Inserted new user ok');
                socket.emit('register', true);
            }
        });
    });

    socket.on('getStudent', function(msg) {
        console.log("Nhan lenh getStudent tu Client " + msg);
        var cursor = collection_pro.find();
        cursor.each(function(err, doc) {
            if (doc != null) {
                var strings = JSON.parse(JSON.stringify(doc));
                console.log(strings);
                socket.emit('getStudent', strings)
            } else if (doc == null) {
                console.log("Ket thuc getStudent");
            }
        });
    });

    socket.on('insertStudent', function(id, name, email) {
        console.log(name + "register");

        var student = { id: id, name: name, email: email };

        collection_pro.insert(student, function(err, result) {
            if (err) {
                console.log(err);
                socket.emit('insertStudent', false);
            } else {
                console.log('Inserted new user ok');
                socket.emit('insertStudent', true);
            }
        });
    });

    socket.on('deleteStudent', function(id) {
        console.log(id + " deleteStudent");
        var o = { _id: new mongodb.ObjectID(id) };
        collection_pro.remove(o, function(err, result) {
            if (err) {
                console.log(err);
                socket.emit('deleteStudent', false);
            } else {
                console.log('Delete product ok');
                socket.emit('deleteStudent', true);
            }
        });
    });

    socket.on('updateStudent', function(_id, name, studentcode, studentclass, nganh) {
        console.log(name + " updateStudent");
        collection_pro.update({ _id: new mongodb.ObjectID(_id) }, { $set: { name: name, studentcode: studentcode, studentclass: studentclass, nganh: nganh } }, function(err, result) {
            if (err) {
                console.log(err);
                socket.emit('updateStudent', false);
            } else {
                socket.emit('updateStudent', true);

            }
        });
    });

 });

http.listen(3001, function() {
    console.log('listening on *:3001');
});